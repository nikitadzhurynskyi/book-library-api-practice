package com.kiyotaka.booklibraryapipractice.domain.book.service;

import com.kiyotaka.booklibraryapipractice.domain.book.entity.BookEntity;
import com.kiyotaka.booklibraryapipractice.domain.book.web.model.CreateBookRequest;
import com.kiyotaka.booklibraryapipractice.domain.user.entity.UserEntity;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

public interface BookService {
    BookEntity create(CreateBookRequest createBookRequest);

    Optional<BookEntity> findBy(Long id);

    BookEntity findByOrThrow(Long id);

    List<BookEntity> findMany();

    void delete(Long id);

    List<BookEntity> getFavoriteBooks(UserEntity user);

    void removeFavorite(UserEntity user, Long bookId);

    void addFavorite(UserEntity user, Long bookId);

    void loadToCSV(PrintWriter writer) throws IOException;
}
