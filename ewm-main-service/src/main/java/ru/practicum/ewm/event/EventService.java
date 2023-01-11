package ru.practicum.ewm.event;

import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.category.Category;
import ru.practicum.ewm.category.CategoryService;
import ru.practicum.ewm.common.TrimRequest;
import ru.practicum.ewm.common.errors.DataCheckException;
import ru.practicum.ewm.common.errors.NotFoundException;
import ru.practicum.ewm.event.dto.AdminUpdateEventRequest;
import ru.practicum.ewm.event.dto.PrivateUpdateEventRequest;
import ru.practicum.ewm.event.dto.UpdateEventRequest;
import ru.practicum.ewm.user.UserService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class EventService {

    private final EventRepository repository;
    private final UserService userService;
    private final CategoryService categoryService;

    @Value("${ewm.event.max-title-size}")
    private int maxTitleSize;

    @Value("${ewm.event.min-title-size}")
    private int minTitleSize;

    @Value("${ewm.event.max-annotation-size}")
    private int maxAnnotationSize;

    @Value("${ewm.event.max-description-size}")
    private int maxDescriptionSize;

    @Value("${ewm.event.min-hours-after-creation}")
    private int minHoursAfterCreation;

    @Value("${ewm.event.min-hours-after-publish}")
    private int minHoursAfterPublish;

    @Autowired
    public EventService(EventRepository repository, UserService userService, CategoryService categoryService) {
        this.repository = repository;
        this.userService = userService;
        this.categoryService = categoryService;
    }

    public Event create(Event event, Long userId, Long categoryId) {
        event.setInitiator(userService.getById(userId));
        event.setCategory(categoryService.getById(categoryId));
        this.checkDataOrThrow(event);
        repository.save(event);
        log.info("Created event: {}", event);

        return event;
    }

    public Event privateGetByIdAndUserId(Long eventId, Long userId) {
        return repository.findByIdAndInitiatorId(eventId, userId)
                         .orElseThrow(() -> new NotFoundException(eventId, "Event"));
    }

    public Event adminGetById(Long eventId) {
        return repository.findById(eventId)
                         .orElseThrow(() -> new NotFoundException(eventId, "Event"));
    }

    public Event publicGetById(Long eventId) {
        Event event = repository.findByIdAndState(eventId, EventState.PUBLISHED)
                                .orElseThrow(() -> new NotFoundException(eventId, "Event"));
        event.setViews(event.getViews() + 1);
        repository.saveAndFlush(event);
        log.info("Event with id {} was viewed", eventId);

        return event;
    }

    public Event privateUpdate(PrivateUpdateEventRequest data, Long userId) {
        Event event = this.privateGetByIdAndUserId(data.getEventId(), userId);
        this.checkStatusForUpdateOrThrow(event);
        this.updateEventData(event, data);
        this.checkDataOrThrow(event);
        repository.save(event);
        log.info("Event updated by user {}:\n{}", userId, event);

        return event;
    }

    public Event adminUpdate(AdminUpdateEventRequest data, Long eventId) {
        Event event = this.adminGetById(eventId);
        this.updateEventData(event, data);

        Location location = data.getLocation();
        if (location != null) {
            event.setLatitude(location.getLat());
            event.setLongitude(location.getLon());
        }

        Boolean requestModeration = data.getRequestModeration();
        if (requestModeration != null) {
            event.setRequestModeration(requestModeration);
        }

        repository.save(event);
        log.info("Event updated by admin:\n{}", event);

        return event;
    }

    public Event adminPublish(Long eventId) {
        Event event = this.adminGetById(eventId);
        this.checkStatusForPublishOrThrow(event);
        event.setPublishedOn(LocalDateTime.now());
        this.checkPublishDateOrThrow(event);
        event.setState(EventState.PUBLISHED);
        repository.save(event);

        return event;
    }

    public Event adminCancel(Long eventId) {
        Event event = this.adminGetById(eventId);
        this.checkStatusForCancelOrThrow(event);
        event.setState(EventState.CANCELED);
        repository.save(event);

        return event;
    }

    public Event privateCancel(Long eventId, Long userId) {
        Event event = this.privateGetByIdAndUserId(eventId, userId);
        this.checkStatusForCancelOrThrow(event);
        event.setState(EventState.CANCELED);
        repository.save(event);

        return event;
    }

    public void confirmRequest(Long eventId) {
        Event event = this.adminGetById(eventId);
        int confirmedRequests = event.getConfirmedRequests() + 1;
        if (event.getParticipantLimit() != null && confirmedRequests >= event.getParticipantLimit()) {
            event.setAvailable(false);
        }
        event.setConfirmedRequests(confirmedRequests);
        repository.saveAndFlush(event);
    }

    public List<Event> getAllByInitiator(Long userId, Long from, Integer size) {
        Pageable pageable = new TrimRequest(from, size, Sort.by("id").ascending());
        return repository.findByInitiatorId(userId, pageable);
    }

    public List<Event> search(EventSearchCriteria criteria, Long from, Integer size) {
        List<BooleanExpression> conditions = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        conditions.add(QEvent.event.state.eq(EventState.PUBLISHED));

        String text = criteria.getText();
        if (text != null) {
            List<BooleanExpression> textConditions = new ArrayList<>();
            textConditions.add(QEvent.event.annotation.containsIgnoreCase(text));
            textConditions.add(QEvent.event.description.containsIgnoreCase(text));
            textConditions.add(QEvent.event.title.containsIgnoreCase(text));
            conditions.add(textConditions.stream()
                                         .reduce(BooleanExpression::or)
                                         .get()
            );
        }

        if (criteria.getCategories() != null) {
            conditions.add(QEvent.event.category.id.in(criteria.getCategories()));
        }

        if (criteria.getUsers() != null) {
            conditions.add(QEvent.event.initiator.id.in(criteria.getUsers()));
        }

        if (criteria.getStates() != null) {
            List<EventState> states = this.convertStatesOrThrow(criteria.getStates());
            conditions.add(QEvent.event.state.in(states));
        }

        if (criteria.getPaid() != null) {
            conditions.add(QEvent.event.paid.eq(criteria.getPaid()));
        }

        LocalDateTime rangeStart = (criteria.getRangeStart() == null) ?
                LocalDateTime.now() :
                LocalDateTime.parse(criteria.getRangeStart(), formatter);
        conditions.add(QEvent.event.eventDate.after(rangeStart));

        if (criteria.getRangeEnd() != null) {
            LocalDateTime rangeEnd = LocalDateTime.parse(criteria.getRangeEnd(), formatter);
            conditions.add(QEvent.event.eventDate.before(rangeEnd));
        }

        if (criteria.getOnlyAvailable() != null && criteria.getOnlyAvailable()) {
            conditions.add(QEvent.event.available.isTrue());
        }

        Sort sort;
        switch (EventSort.from(criteria.getSort())) {
            case VIEWS:
                sort = Sort.by("views").descending();
                break;

            case EVENT_DATE:
                sort = Sort.by("eventDate").ascending();
                break;

            default:
                sort = Sort.by("id").ascending();
        }

        BooleanExpression finalCondition = conditions.stream()
                                                     .reduce(BooleanExpression::and)
                                                     .get();
        Pageable pageable = new TrimRequest(from, size, sort);

        return repository.findAll(finalCondition, pageable).getContent();
    }

    private List<EventState> convertStatesOrThrow(String[] stateNames) {
        return Arrays.stream(stateNames)
                     .map(s -> EventState.from(s).orElseThrow(
                             () -> new DataCheckException("Wrong event state: " + s)
                     ))
                     .collect(Collectors.toList());
    }

    private void checkDataOrThrow(Event event) {
        this.checkEventDateOrThrow(event);
        this.checkAnnotationOrThrow(event);
        this.checkDescriptionOrThrow(event);
        this.checkTitleOrThrow(event);
    }

    private void checkEventDateOrThrow(Event event) {
        LocalDateTime eventDate = event.getEventDate();
        LocalDateTime creationDate  = event.getCreatedOn();
        if (eventDate.isBefore(creationDate.plusHours(minHoursAfterCreation))) {
            throw new DataCheckException(String.format(
                    "Event date (%s) shouldn't be earlier %d hours since event creation moment (%s)",
                    eventDate,
                    minHoursAfterCreation,
                    creationDate));
        }
    }

    public boolean isExists(Long id) {
        return repository.existsById(id);
    }

    private void checkPublishDateOrThrow(Event event) {
        LocalDateTime eventDate = event.getEventDate();
        LocalDateTime publishDate  = event.getPublishedOn();
        if (eventDate.isBefore(publishDate.plusHours(minHoursAfterPublish))) {
            throw new DataCheckException(String.format(
                    "Event date (%s) shouldn't be earlier %d hours since publish date (%s)",
                    eventDate,
                    minHoursAfterPublish,
                    publishDate));
        }
    }

    private void checkTitleOrThrow(Event event) {
        long titleLength = event.getTitle().length();
        if (titleLength < minTitleSize || titleLength > maxTitleSize) {
            throw new DataCheckException(String.format(
                    "Event title size should be between %d and %d characters (now %d)",
                    minTitleSize,
                    maxTitleSize,
                    titleLength));
        }
    }

    private void checkAnnotationOrThrow(Event event) {
        long annotationLength = event.getAnnotation().length();
        if (annotationLength > maxAnnotationSize) {
            throw new DataCheckException(String.format(
                    "Event annotation size shouldn't exceed %d characters (now %d)",
                    maxAnnotationSize,
                    annotationLength));
        }
    }

    private void checkDescriptionOrThrow(Event event) {
        long descriptionLength = event.getDescription().length();
        if (descriptionLength > maxDescriptionSize) {
            throw new DataCheckException(String.format(
                    "Event description size shouldn't exceed %d characters (now %d)",
                    maxDescriptionSize,
                    descriptionLength));
        }
    }

    private void checkStatusForUpdateOrThrow(Event event) {
        EventState state = event.getState();
        if (state.equals(EventState.PUBLISHED)) {
            throw new DataCheckException(String.format(
                    "Only pending or canceled event can be changed (now %s)",
                    state));
        }
    }

    private void checkStatusForPublishOrThrow(Event event) {
        EventState state = event.getState();
        if (!state.equals(EventState.PENDING)) {
            throw new DataCheckException(String.format(
                    "Only pending event can be published (now %s)",
                    state));
        }
    }

    private void checkStatusForCancelOrThrow(Event event) {
        EventState state = event.getState();
        if (!state.equals(EventState.PENDING)) {
            throw new DataCheckException("Published event can't be canceled");
        }
    }

    private void updateEventData(Event event, UpdateEventRequest data) {
        boolean isChanged = false;

        String annotation = data.getAnnotation();
        if (annotation != null && !annotation.isBlank()) {
            event.setAnnotation(annotation);
            isChanged = true;
        }

        Long categoryId = data.getCategory();
        if (categoryId != null) {
            Category category = categoryService.getById(categoryId);
            event.setCategory(category);
            isChanged = true;
        }

        String description = data.getDescription();
        if (description != null && !description.isBlank()) {
            event.setDescription(description);
            isChanged = true;
        }

        String eventDate = data.getEventDate();
        if (eventDate != null && !eventDate.isBlank()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            event.setEventDate(LocalDateTime.parse(eventDate, formatter));
            isChanged = true;
        }

        Boolean paid = data.getPaid();
        if (paid != null) {
            event.setPaid(paid);
            isChanged = true;
        }

        Integer participantLimit = data.getParticipantLimit();
        if (participantLimit != null) {
            event.setParticipantLimit(participantLimit);
            isChanged = true;
        }

        String title = data.getTitle();
        if (title != null && !title.isBlank()) {
            event.setTitle(title);
            isChanged = true;
        }

        if (isChanged) {
            event.setState(EventState.PENDING);
        }
    }
}
