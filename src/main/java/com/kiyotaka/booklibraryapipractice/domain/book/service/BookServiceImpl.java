package com.kiyotaka.booklibraryapipractice.domain.book.service;

import com.kiyotaka.booklibraryapipractice.domain.book.entity.BookEntity;
import com.kiyotaka.booklibraryapipractice.domain.book.repository.BookRepository;
import com.kiyotaka.booklibraryapipractice.domain.book.web.model.CreateBookRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public BookEntity create(CreateBookRequest createBookRequest) {
        final BookEntity bookEntity = new BookEntity(createBookRequest.getTitle(),
                createBookRequest.getAuthors(),
                createBookRequest.getGenres());

        return bookRepository.save(bookEntity);
    }

    @Override
    public Optional<BookEntity> findBy(Long id) {
        return bookRepository.findById(id);
    }

    @Override
    public BookEntity findByOrThrow(Long id) {
        return findBy(id).orElseThrow(() -> new RuntimeException("Book not found."));
    }

    @Override
    public List<BookEntity> findMany() {
        return bookRepository.findAll();
    }

    @Override
    public List<BookEntity> findMany(Pageable pageable) {
        return bookRepository.findAll(pageable).getContent();
    }

    @Override
    public void delete(Long id) {
        bookRepository.deleteById(id);
    }
}
