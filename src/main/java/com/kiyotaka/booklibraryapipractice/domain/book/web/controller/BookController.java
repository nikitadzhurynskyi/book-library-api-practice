package com.kiyotaka.booklibraryapipractice.domain.book.web.controller;

import com.kiyotaka.booklibraryapipractice.domain.book.entity.BookEntity;
import com.kiyotaka.booklibraryapipractice.domain.book.mapper.BookMapper;
import com.kiyotaka.booklibraryapipractice.domain.book.service.BookService;
import com.kiyotaka.booklibraryapipractice.domain.book.web.model.BookResponse;
import com.kiyotaka.booklibraryapipractice.domain.book.web.model.CreateBookRequest;
import com.kiyotaka.booklibraryapipractice.domain.user.entity.UserEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/books")
@RestController
public class BookController {
    private final BookService bookService;
    private final BookMapper bookMapper;

    public BookController(BookService bookService, BookMapper bookMapper) {
        this.bookService = bookService;
        this.bookMapper = bookMapper;
    }

    @PostMapping
    public ResponseEntity<BookEntity> createBook(@RequestBody CreateBookRequest createBookRequest) {
        final BookEntity bookEntity = bookService.create(createBookRequest);
        return new ResponseEntity<>(bookEntity, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public BookResponse getBookById(@PathVariable Long id) {
        final BookEntity bookEntity = bookService.findByOrThrow(id);
        return bookMapper.mapEntityToResponse(bookEntity);
    }

    @GetMapping
    public List<BookResponse> getBooks() {
        final List<BookEntity> bookEntities = bookService.findMany();
        return bookMapper.mapEntitiesToResponses(bookEntities);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/favorites/{id}")
    public ResponseEntity<Void> addFavorite(@PathVariable Long id, @AuthenticationPrincipal UserEntity user) {
        bookService.addFavorite(user, id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/favorites/{id}")
    public ResponseEntity<Void> removeFavorite(@PathVariable Long id, @AuthenticationPrincipal UserEntity user) {
        bookService.removeFavorite(user, id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/favorites")
    public List<BookResponse> getFavorites(@AuthenticationPrincipal UserEntity user) {
        final List<BookEntity> favoriteBooks = bookService.getFavoriteBooks(user);
        return bookMapper.mapEntitiesToResponses(favoriteBooks);
    }
}
