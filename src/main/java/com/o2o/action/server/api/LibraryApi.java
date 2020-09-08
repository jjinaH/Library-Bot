package com.o2o.action.server.api;

import com.o2o.action.server.domain.BookIsbn;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.asList;

@Service
public class LibraryApi {

    private RestTemplate restTemplate;

    private final String nationalApiKey = "";

    private static final String TITLE_ISBN_REQUEST_URI = "http://seoji.nl.go.kr/landingPage/SearchApi.do?" +
            "cert_key={nationalApiKey}&result_style=json&page_no=1&page_size=10&title={title}";

    //제목으로 isbn 검색 요청 메서드
    public List<BookIsbn> getIsbnByTitle(String title){
        BookIsbn[] response = restTemplate.getForObject(TITLE_ISBN_REQUEST_URI, BookIsbn[].class, nationalApiKey, title);
        List<BookIsbn> isbnByTitleList = asList(response);
        return isbnByTitleList;
    }

}
