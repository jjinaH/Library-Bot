package com.o2o.action.server.api;

import com.o2o.action.server.util.CommonUtil;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class AvailableApi {

    private String address = "http://data4library.kr/api/bookExist?format=json";
    private String naruKey = "1aec8d2a03c26a7a0593f4c8afd9caadc0b303e53b7774d107e63b0b75093670";

    public String getAvail(int libCode, String isbn) throws Exception {

        String request = address +
                "&libCode=" + libCode +
                "&isbn13=" + isbn +
                "&authKey=" + naruKey
        ;

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
        System.out.println(response);
        JSONObject book = (JSONObject) response.get("result");
        String avail = (String) book.get("loanAvailable");
        String have = (String) book.get("hasBook");

        if(avail.equals("Y"))
            return "대출 가능";
        else
            return "대출 불가능(대출 중)";

    }

    public boolean hasBook(int libCode, String isbn) throws Exception {

        String request = address +
                "&libCode=" + libCode +
                "&isbn13=" + isbn +
                "&authKey=" + naruKey
                ;

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
        JSONObject book = (JSONObject) response.get("result");
        String have = (String) book.get("hasBook");

        if(have.equals("Y"))
            return true;
        else
            return false;

    }
}
