package com.kiyotaka.booklibraryapipractice.domain.book.service;

import com.kiyotaka.booklibraryapipractice.domain.book.entity.BookEntity;
import com.kiyotaka.booklibraryapipractice.domain.book.repository.BookRepository;
import com.kiyotaka.booklibraryapipractice.domain.book.web.model.CreateBookRequest;
import com.kiyotaka.booklibraryapipractice.domain.user.entity.UserEntity;
import com.kiyotaka.booklibraryapipractice.domain.user.repository.UserRepository;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final EntityManager entityManager;

    public BookServiceImpl(BookRepository bookRepository,
                           UserRepository userRepository,
                           EntityManager entityManager) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.entityManager = entityManager;
    }

    @Transactional
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

    @Transactional
    @Override
    public List<BookEntity> getFavoriteBooks(UserEntity user) {
        final UserEntity mergedUserToSession = entityManager.merge(user);
        return mergedUserToSession.getFavoriteBooks();
    }

    @Transactional
    @Override
    public void removeFavorite(UserEntity user, Long bookId) {
        final BookEntity bookEntity = findByOrThrow(bookId);
        final UserEntity mergedUserToSession = entityManager.merge(user);
        mergedUserToSession.getFavoriteBooks().remove(bookEntity);
        userRepository.save(mergedUserToSession);
    }

    @Transactional
    @Override
    public void addFavorite(UserEntity user, Long bookId) {
        final BookEntity bookEntity = findByOrThrow(bookId);
        final UserEntity mergedUserToSession = entityManager.merge(user);
        mergedUserToSession.getFavoriteBooks().add(bookEntity);
        userRepository.save(mergedUserToSession);
    }
}
