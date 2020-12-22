package com.o2o.action.server.repository;

import com.o2o.action.server.model.LibraryInfo;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAO {

    private Connection con;
    private LibraryInfo libraryInfo = new LibraryInfo();

    public DAO() {

        try{

            Class.forName("org.postgresql.Driver");

        }

        catch(ClassNotFoundException e)
        {
            System.out.println("error class not found exception");
            e.printStackTrace();

        }

        con = null;
        Statement st = null;
        ResultSet rs = null;

        String url = "jdbc:postgresql://172.30.1.43:5432/librarydb";
        String user = "library_ja";
        String password = "jahwang";

        try {
            con = DriverManager.getConnection(url, user, password);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public LibraryInfo getLibraryInfo(String name) { //도서관 이름 도서관 정보 찾기
        String sql = "select * from library_info where name = ?";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                libraryInfo.setAddress(resultSet.getString("address"));
                libraryInfo.setCode(resultSet.getInt("code"));
                libraryInfo.setTel(resultSet.getString("tel"));
                libraryInfo.setHomepage(resultSet.getString("homepage"));
                libraryInfo.setName(resultSet.getString("name"));
                libraryInfo.setOpen(resultSet.getString("open"));
                libraryInfo.setClosed(resultSet.getString("closed"));

                return libraryInfo;
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null && !preparedStatement.isClosed())
                    preparedStatement.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return null;
    }

//    public String selectTel(String name) { //도서관 이름으로 주소 찾기
//        String sql = "select * from library_info where name = ?";
//        PreparedStatement preparedStatement = null;
//        try {
//            preparedStatement = con.prepareStatement(sql);
//            preparedStatement.setString(1, name);
//            ResultSet resultSet = preparedStatement.executeQuery();
//            if (resultSet.next())
//                return resultSet.getString("tel");
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } finally {
//            try {
//                if (preparedStatement != null && !preparedStatement.isClosed())
//                    preparedStatement.close();
//            } catch (SQLException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }
//
//        return "해당하는 전화번호를 찾지 못했습니다.";
//    }
//
//    public String selectPage(String name) { //도서관 이름으로 주소 찾기
//        String sql = "select * from library_info where name = ?";
//        PreparedStatement preparedStatement = null;
//        try {
//            preparedStatement = con.prepareStatement(sql);
//            preparedStatement.setString(1, name);
//            ResultSet resultSet = preparedStatement.executeQuery();
//            if (resultSet.next())
//                return resultSet.getString("homepage");
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } finally {
//            try {
//                if (preparedStatement != null && !preparedStatement.isClosed())
//                    preparedStatement.close();
//            } catch (SQLException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }
//
//        return "해당하는 홈페이지를 찾지 못했습니다.";
//    }
}
