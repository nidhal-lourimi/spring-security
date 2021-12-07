package com.nidhallourimi.springsecurity.student;

public class Student {

    private final Long studentId;
    private final String studentName;

    public Student(Long studentId, String studentName) {
        this.studentId = studentId;
        this.studentName = studentName;
    }

    public Long getStudentId() {
        return studentId;
    }

    public String getStudentName() {
        return studentName;
    }
}
