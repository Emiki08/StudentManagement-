package raisetech.StudentManagement.controller;

import org.springframework.ui.Model;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import raisetech.StudentManagement.controller.converter.StudentConverter;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.service.StudentService;
import org.springframework.web.bind.annotation.PathVariable;





@Controller
public class StudentController {

  private StudentService service;
  private StudentConverter converter;

  @Autowired
  public StudentController(StudentService service, StudentConverter converter) {
    this.service = service;
    this.converter = converter;
  }


  @GetMapping("/studentList")
  public String getStudentList(Model model) {

    List<Student> students = service.searchStudentList();
    List<StudentCourse> studentCourses = service.searchStudentCourseList();

    model.addAttribute(
        "studentList",
        converter.convertStudentDetails(students, studentCourses)
    );

    return "studentList";
  }


  @GetMapping("/studentCourseList")
  public List<StudentCourse> getStudentCourseList() {
    return service.searchStudentCourseList();
  }


  // 更新画面表示
  @GetMapping("/student/{id}")
  public String updateStudent(@PathVariable int id, Model model) {

    StudentDetail studentDetail = service.searchStudent(id);

    model.addAttribute("studentDetail", studentDetail);

    return "updateStudent";
  }
  @PostMapping("/updateStudent")
  public String updateStudent(
      @ModelAttribute StudentDetail studentDetail,
      BindingResult result) {

    if (result.hasErrors()) {
      return "updateStudent";
    }

    service.updateStudent(studentDetail);

    return "redirect:/studentList";
  }


  // 登録画面表示
  @GetMapping("/newStudent")
  public String newStudent(Model model) {

    StudentDetail studentDetail = new StudentDetail();

    studentDetail.setStudentsCourses(
        List.of(new StudentCourse())
    );

    model.addAttribute("studentDetail", studentDetail);

    return "registerStudent";
  }


  // 登録処理
  @PostMapping("/registerStudent")
  public String registerStudent(
      @ModelAttribute StudentDetail studentDetail,
      BindingResult result) {

    if (result.hasErrors()) {
      return "registerStudent";
    }

    service.registerStudent(studentDetail);

    return "redirect:/studentList";
  }

}