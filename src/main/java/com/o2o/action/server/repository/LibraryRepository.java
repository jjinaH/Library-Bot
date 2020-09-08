package com.o2o.action.server.repository;

import java.sql.*;



public class LibraryRepository {

    private Connection conn;

    public LibraryRepository() {

        try {

            Statement st = null;
            ResultSet rs = null;

            String url = "jdbc:postgresql://localhost:5432/";
            String user = "postgres";
            String password = "jahwang";

            conn = DriverManager.getConnection(url, user, password);
            st = conn.createStatement();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String selectAddress(String name) { //도서관 이름으로 주소 찾기
        String sql = "select address from library where name = ?;";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.getString("address");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "null";
    }

    public String selectTel(String name) { //도서관 이름으로 전화번호 찾기
        String sql = "select tel from library where name = ?;";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.getString("tel");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String selectHomepage(String name) { //도서관 이름으로 홈페이지 찾기
        String sql = "select page from library where name = ?;";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.getString("page");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
