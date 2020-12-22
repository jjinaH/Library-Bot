package com.o2o.action.server.rest;

import com.o2o.action.server.api.BookApi;
import com.o2o.action.server.model.Isbn;
import com.o2o.action.server.model.LibraryInfo;
//import com.o2o.action.server.repository.LibraryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LibraryController {

//    @Autowired
//    LibraryRepository libraryRepository;
//
//    @Autowired
//    BookApi bookApi;
//
//    @RequestMapping("/findlibrarybyname/{name}")
//    public LibraryInfo findByName(@PathVariable String name){
//        return libraryRepository.findByName(name);
//    }


//    @GetMapping("/isbn/title/{title}")
//    public List<Isbn> getIsbnByTitle(@RequestParam String title) {
//        return bookApi.getIsbnByTitle(title);
//    }

//    @GetMapping("/isbn/title/{title}")
//    public List<Isbn> getIsbn(@PathVariable String title) throws JsonProcessingException {
//
//        Isbn result = new Isbn();
//
//        try {
//            RestTemplate restTemplate = new RestTemplate();
//
//            HttpHeaders header = new HttpHeaders();
//            HttpEntity<?> entity = new HttpEntity<>(header);
//
//            String url = "http://seoji.nl.go.kr/landingPage/SearchApi.do?result_style=json&page_no=1&page_size=1&title={title}";
//
//            UriComponents uri = UriComponentsBuilder.fromHttpUrl(url+"?"+"cert_key=941971b353d6dec58dfc050a26ec0409307b5be8e705849f825f17d895463eee");
//
//            ResponseEntity<Isbn> resultIsbn = restTemplate.exchange(uri.toString(), HttpMethod.GET, entity, Isbn.class);
//
//
//        } catch (HttpCli)
//    }

//    @GetMapping("/search/books/{word}")
//    public List<BookInfo> getResult(@PathVariable String word) {
//        List<BookInfo> bookInfoList
//    }



}
