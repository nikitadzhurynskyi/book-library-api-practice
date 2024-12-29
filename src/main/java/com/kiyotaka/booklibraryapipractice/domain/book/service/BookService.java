package com.kiyotaka.booklibraryapipractice.domain.book.service;

import com.kiyotaka.booklibraryapipractice.domain.book.entity.BookEntity;
import com.kiyotaka.booklibraryapipractice.domain.book.web.model.CreateBookRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BookService {
    BookEntity create(CreateBookRequest createBookRequest);

    Optional<BookEntity> findBy(Long id);

    BookEntity findByOrThrow(Long id);

    List<BookEntity> findMany();

    List<BookEntity> findMany(Pageable pageable);

    void delete(Long id);
}
