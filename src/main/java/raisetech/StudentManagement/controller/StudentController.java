package raisetech.StudentManagement.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import raisetech.StudentManagement.Student;
import raisetech.StudentManagement.service.StudentService;

@RestController
public class StudentController {

  private StudentService service;

  @Autowired
  public StudentController(StudentService service) {
    this.service = service;
  }

  @GetMapping("/studentList")
  public List<Student> getStudentList() {
    return service.searchStudentList();
  }

  @GetMapping("/studentCourseList")
  public List<raisetech.StudentManagement.StudentCourse> getStudentCourseList() {
    return service.searchStudentCourseList();
  }

  @GetMapping("/studentList/30s")
  public List<Student> getStudentsIn30s() {
    return service.searchStudentsIn30s();
  }

  @GetMapping("/studentCourseList/java")
  public List<raisetech.StudentManagement.StudentCourse> getJavaCourse() {
    return service.searchJavaCourse();
  }

}
