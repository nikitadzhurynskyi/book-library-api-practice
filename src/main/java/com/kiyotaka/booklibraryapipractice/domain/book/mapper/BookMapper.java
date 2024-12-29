package com.kiyotaka.booklibraryapipractice.domain.book.mapper;

import com.kiyotaka.booklibraryapipractice.domain.book.entity.BookEntity;
import com.kiyotaka.booklibraryapipractice.domain.book.web.model.BookResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookMapper {
    public BookResponse mapEntityToResponse(BookEntity entity) {
        return new BookResponse(entity.getId(), entity.getTitle(), entity.getAuthors(), entity.getGenres());
    }

    public List<BookResponse> mapEntitiesToResponses(List<BookEntity> entities) {
        return entities.stream().map(this::mapEntityToResponse).toList();
    }
}
