package com.kiyotaka.booklibraryapipractice.domain.book.service;

import com.kiyotaka.booklibraryapipractice.core.exception.BookLibraryException;
import com.kiyotaka.booklibraryapipractice.domain.book.entity.BookEntity;
import com.kiyotaka.booklibraryapipractice.domain.book.repository.BookRepository;
import com.kiyotaka.booklibraryapipractice.domain.book.web.model.CreateBookRequest;
import com.kiyotaka.booklibraryapipractice.domain.user.entity.UserEntity;
import com.kiyotaka.booklibraryapipractice.domain.user.repository.UserRepository;
import jakarta.persistence.EntityManager;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.PrintWriter;
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
        return findBy(id).orElseThrow(() -> new BookLibraryException("Book not found.", HttpStatus.NOT_FOUND));
    }

    @Override
    public List<BookEntity> findMany() {
        return bookRepository.findAll();
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

    @Override
    public void loadToCSV(PrintWriter printWriter) throws IOException {
        final List<BookEntity> books = findMany();
        final CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                .setHeader(new String[]{"Id", "Titile", "Authors", "Genres"})
                .build();

        try (final CSVPrinter csvPrinter = new CSVPrinter(printWriter, csvFormat)) {
            books.forEach(bookEntity -> {
                try {
                    csvPrinter.printRecord(bookEntity.getId(), bookEntity.getTitle(), bookEntity.getAuthors(), bookEntity.getGenres());
                } catch (IOException e) {
                    throw new BookLibraryException(e.getMessage(), HttpStatus.BAD_REQUEST);
                }
            });


        }
    }
}
