package com.o2o.action.server.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Data
public class BookInfo {

    @NonNull
    private String name;
    @NonNull
    private String authors;
    @NonNull
    private String publisher;
    @NonNull
    private String publication_year;
    @NonNull
    private String class_no;
    @NonNull
    private String book_code;
    @NonNull
    private String bookImageURL;
    private String bookKey;
    private String isbn;

}
