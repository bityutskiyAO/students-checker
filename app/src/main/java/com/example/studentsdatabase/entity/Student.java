package com.example.studentsdatabase.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Student implements Serializable {
    public String name;
    public String surname;
    public String middleName;
    public String group;

    public Student(){

    }
    public Student(String name, String surname, String middleName, String group) {
        this.name = name;
        this.surname = surname;
        this.middleName = middleName;
        this.group = group;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", middleName='" + middleName + '\'' +
                ", group='" + group + '\'' +
                '}';
    }
}
