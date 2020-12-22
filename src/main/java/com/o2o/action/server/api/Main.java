package com.o2o.action.server.api;

import com.o2o.action.server.model.BookInfo;
import com.o2o.action.server.model.LibraryInfo;
import com.o2o.action.server.repository.DAO;
import com.o2o.action.server.util.CommonUtil;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {

//    IsbnApi isbnApi = new IsbnApi();
//    BookApi bookApi = new BookApi();
//
//    JSONArray isbnList = isbnApi.requestIsbn("title", "나미야 잡화점의 기적");
//    for(Object o : isbnList) {
//        JSONObject book = (JSONObject) o;
//        String isbn = CommonUtil.makeSafeString(book.get("EA_ISBN"));
//        if(!CommonUtil.isEmptyString(isbn))
//            bookApi.requestSearch(111003, isbn);
//    }

        DAO reposit = new DAO();
        BookApi bookApi = new BookApi();
        AvailableApi availableApi = new AvailableApi();
        LibraryInfo libraryInfo = reposit.getLibraryInfo("서초구립반포도서관");

        String address = libraryInfo.getAddress();
        String tel = libraryInfo.getTel();
        String homepage = libraryInfo.getHomepage();

        List<BookInfo> bookInfos = bookApi.getResultList("title", "미움받을 용기", 111456);

        for(BookInfo book : bookInfos)
            System.out.println(book);


//        for(BookInfo book : bookInfos){
//
//        }

//        bookApi.requestSearch(111377, "9788925530949");



//        RTtest rTtest = new RTtest();
//
//        rTtest.getIsbnByTitle("피프티피플");
    }
}
