package com.o2o.action.server.model;

import lombok.Data;
//import javax.persistence.*;
//
//@Entity
//@Table(name = "Library_info")
@Data
public class LibraryInfo {

//    @Id
//    @GeneratedValue(strategy=GenerationType.AUTO)
//    @Column(name = "code")
    private Integer code;
    private String name;
    private String address;
    private String tel;
    private String homepage;
    private String open;
    private String closed;
}
