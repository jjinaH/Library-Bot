//package com.o2o.action.server.repository;
//
//import com.o2o.action.server.model.LibraryInfo;
//
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.CrudRepository;
//import org.springframework.stereotype.Repository;
//
//import java.sql.*;
//
//@Repository
//public interface LibraryRepository extends CrudRepository<LibraryInfo, Integer> {
//
//    @Query("select a from LibraryInfo a where a.name = ?1")
//    LibraryInfo findByName(String name);
//}
