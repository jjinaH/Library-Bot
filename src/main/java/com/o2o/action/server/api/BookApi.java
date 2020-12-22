package com.o2o.action.server.api;

import com.o2o.action.server.model.BookInfo;
import com.o2o.action.server.model.Isbn;
import com.o2o.action.server.util.CommonUtil;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.awt.print.Book;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BookApi {

    private String address = "http://data4library.kr/api/itemSrch?type=ALL&pageNo=1&pageSize=1&format=json";
    private String naruKey = "1aec8d2a03c26a7a0593f4c8afd9caadc0b303e53b7774d107e63b0b75093670";

    private AvailableApi availableApi = new AvailableApi();

    public BookInfo requestSearch(int libCode, String isbn) throws Exception{
        try {

            String request = address +
                    "&libCode=" + libCode +
                    "&isbn13=" + isbn +
                    "&authKey=" + naruKey;

            BufferedReader bufferedReader;
            URL url = new URL(request);

            String line = "";
            String result = "";


            bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));

            while ((line = bufferedReader.readLine()) != null) {
                result = result.concat(line);
            }

            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(result);

            JSONObject response = (JSONObject) obj.get("response");
            if(response == null) return null;
//            System.out.println(response);
            JSONArray docs = (JSONArray) response.get("docs");
            if(docs == null) return null;
            JSONObject doc = (JSONObject) docs.get(0);
            JSONObject book = (JSONObject) doc.get("doc");
//            System.out.println(book);

            BookInfo bookInfo =
                    new BookInfo(
                            CommonUtil.makeSafeString(book.get("bookname")),
                            CommonUtil.makeSafeString(book.get("authors")),
                            CommonUtil.makeSafeString(book.get("publisher")),
                            CommonUtil.makeSafeString(book.get("publication_year")),
                            CommonUtil.makeSafeString(book.get("class_no")),
                            CommonUtil.makeSafeString(book.get("book_code")),
                            CommonUtil.makeSafeString(book.get("bookImageURL"))
                    )
            ;


            bookInfo.setBookKey(bookInfo.getName() + "_KEY");
            bookInfo.setIsbn(isbn);


            return bookInfo;

//                      System.out.println(book.get("authors"));


//            //null point
//            JSONArray parseList = (JSONArray) docs.get("docs");
//
//            if(parseList == null) System.out.println("널값입니다.");
//
//            for (Object o : parseList) {
//                JSONObject book = (JSONObject) o;
//                if(!CommonUtil.isEmptyString(book.get("authors")))
//                    System.out.println(book.get("authors"));
//            }
//
//            if(!CommonUtil.isEmptyString(parseList))

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public ArrayList<BookInfo> getResultList(String category, String word, int libCode) throws Exception {

        IsbnApi isbnApi = new IsbnApi();

        ArrayList<BookInfo> result = new ArrayList<>();

        JSONArray isbnList = isbnApi.requestIsbn(category, word);

        int isbnNo = 0;
        for(Object o : isbnList) {
            JSONObject book = (JSONObject) o;
            String isbn = CommonUtil.makeSafeString(book.get("EA_ISBN"));
            System.out.println("isbn >>> " + ++isbnNo);
            if(!CommonUtil.isEmptyString(isbn) && availableApi.hasBook(libCode, isbn)) {
                //test
                System.out.println("넘어왔음");
                System.out.println(isbn);
                result.add(requestSearch(libCode, isbn));
            }
        }

        System.out.println(result);

        return result;
    }



}
