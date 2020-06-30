package com.demo.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


class Student {
    int id;
    String name;
    int age;

    public Student(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }
}

public class JavaStreamsDemo {
    public static void main(String[] args) {
        getAgeGt22(sampleData());
    }

    public static List<Student> sampleData() {
        List<Student> studentlist = new ArrayList<Student>();
        //Adding Students
        studentlist.add(new Student(11, "Jon", 22));
        studentlist.add(new Student(22, "Steve", 18));
        studentlist.add(new Student(33, "Lucy", 22));
        studentlist.add(new Student(44, "Sansa", 23));
        studentlist.add(new Student(55, "Maggie", 18));
        //Fetching student data as a Set
        return studentlist;
    }

    public static void getAgeGt22(List<Student> studentlist) {

        Set<Student> students = studentlist.stream()
                .filter(n -> n.id > 22)
                .collect(Collectors.toSet());
        //Iterating Set
        for (Student stu : students) {
            System.out.println(stu.id + " " + stu.name + " " + stu.age);
        }
    }
}