package com.nidhallourimi.springsecurity.student;


import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("management/api/v1/student")
public class StudentManagementController
{

    private static final List<Student> STUDENTS = Arrays.asList(
            new Student(1L,"nidhal lourimi"),
            new Student(2L,"akrim ben miled" ),
            new Student(3L,"ana smith")
    );

    @GetMapping
    public List<Student> getStudents(){
        return STUDENTS;
    }

    @PostMapping
    public void registerNewStudent(@RequestBody Student student){
        System.out.println(student);

    }

    @DeleteMapping(path="{studentId}")
    public void deleteStudent(@PathVariable("studentId") Long studentId){
        System.out.println(studentId);
    }
    @PutMapping(path="{studentId}")
    public void updateStudent(@PathVariable("studentId")Long studentId,@RequestBody Student student){
        System.out.println("update Student");
        System.out.println(String.format("%s with id %s ben updated",student.getStudentName() ,studentId));

    }
}
