package com.o2o.action.server.repository;

import java.sql.*;

public class JDBCTest {

    private Connection conn;

    public JDBCTest() {

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
    

}
