package com.kiyotaka.booklibraryapipractice.domain.book.entity;

import com.kiyotaka.booklibraryapipractice.domain.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "book_entity")
public class BookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "authors", joinColumns = @JoinColumn(name = "book_id"))
    @Column(nullable = false)
    private List<String> authors;


    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "genres", joinColumns = @JoinColumn(name = "book_id"))
    @Column(nullable = false)
    private List<String> genres;

    @ManyToMany(mappedBy = "favoriteBooks")
    private List<UserEntity> userEntities;

    public BookEntity(String title, List<String> authors, List<String> genres) {
        this.title = title;
        this.authors = authors;
        this.genres = genres;
    }
}