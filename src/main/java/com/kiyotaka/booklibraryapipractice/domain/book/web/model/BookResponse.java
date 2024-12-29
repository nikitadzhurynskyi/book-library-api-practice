package com.kiyotaka.booklibraryapipractice.domain.book.web.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class BookResponse {
    private Long id;

    private String title;

    private List<String> authors;

    private List<String> genres;
}
