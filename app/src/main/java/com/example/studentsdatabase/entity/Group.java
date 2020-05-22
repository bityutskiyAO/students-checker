package com.example.studentsdatabase.entity;

import java.io.Serializable;

public class Group implements Serializable {
    public String faculty;
    public String groupNumber;

    public Group () {

    }

    public Group(String faculty, String groupNumber) {
        this.faculty = faculty;
        this.groupNumber = groupNumber;
    }
}