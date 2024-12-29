package com.kiyotaka.booklibraryapipractice.domain.book.repository;

import com.kiyotaka.booklibraryapipractice.domain.book.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<BookEntity, Long> {
}
