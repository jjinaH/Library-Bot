package com.o2o.action.server.api;

import com.o2o.action.server.model.Isbn;
import com.o2o.action.server.util.CommonUtil;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class IsbnApi {

    private String address = "http://seoji.nl.go.kr/landingPage/SearchApi.do?result_style=json&page_no=1&page_size=10";
    private String nationalKey = "941971b353d6dec58dfc050a26ec0409307b5be8e705849f825f17d895463eee";

    public JSONArray requestIsbn(String category, String word) throws Exception{

//        System.out.println("ISBN api 작동중");

        word = word.replaceAll(" ", "");
        
        try {

            String request = address +
                    "&" + category + "=" + word +
                    "&cert_key=" + nationalKey;

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

//            for (Object o : (JSONArray) obj.get("docs")) {
//                JSONObject book = (JSONObject) o;
//                if(!CommonUtil.isEmptyString(book.get("EA_ISBN")))
//                    System.out.println("isbn api 작동 \n"+book.get("EA_ISBN"));
//            }


            return (JSONArray) obj.get("docs");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

}



