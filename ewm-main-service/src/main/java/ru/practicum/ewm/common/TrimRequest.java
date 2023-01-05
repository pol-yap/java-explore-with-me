package ru.practicum.ewm.common;


import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Pageable;

@AllArgsConstructor

public class TrimRequest implements Pageable {

    private final long offset;
    private final int size;
    private final Sort sort;

    @Override
    public int getPageNumber() { return 0; }

    @Override
    public int getPageSize() { return size; }

    @Override
    public long getOffset() { return offset; }

    @Override
    @NonNull
    public Sort getSort() { return this.sort; }

    @Override
    @NonNull
    public Pageable next() { return this; }

    @Override
    @NonNull
    public Pageable previousOrFirst() { return this; }

    @Override
    @NonNull
    public Pageable first() { return this; }

    @Override
    @NonNull
    public Pageable withPage(int pageNumber) { return this; }

    @Override
    public boolean hasPrevious() { return false; }
}
