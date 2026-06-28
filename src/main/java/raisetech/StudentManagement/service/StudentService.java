package raisetech.StudentManagement.service;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import raisetech.StudentManagement.Student;
import raisetech.StudentManagement.StudentCourse;
import raisetech.StudentManagement.StudentRepository;

@Service
public class StudentService {

  private StudentRepository repository;


  @Autowired
  public StudentService(StudentRepository repository) {
    this.repository = repository;
  }

  public List<Student> searchStudentList() {
    return repository.search();
  }

  public List<StudentCourse> searchStudentCourseList() {
    return repository.searchStudentCourses();

  }
  public List<Student> searchStudentsIn30s() {
    return repository.searchStudentsIn30s();
  }

  public List<StudentCourse> searchJavaCourse() {
    return repository.searchJavaCourse();
  }

}