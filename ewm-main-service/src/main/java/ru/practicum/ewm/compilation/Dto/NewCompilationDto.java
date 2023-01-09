package ru.practicum.ewm.compilation.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.practicum.ewm.common.Dto;
import ru.practicum.ewm.compilation.Compilation;

import javax.validation.constraints.NotBlank;
import java.util.Set;

@Getter
@AllArgsConstructor
public class NewCompilationDto implements Dto<Compilation> {

    private final Set<Long> events;

    private final boolean pinned;

    @NotBlank(message = "Compilation title shouldn't be empty")
    private final String title;

    @Override
    public Compilation makeEntity() {
        Compilation compilation = new Compilation();
        compilation.setTitle(title);
        compilation.setPinned(pinned);

        return compilation;
    }
}
