package raisetech.StudentManagement.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import raisetech.StudentManagement.controller.converter.StudentConverter;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.service.StudentService;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 受講生の検索や登録、更新などを行うREST APIとして受け付けるControllerです。
 */

@RestController
public class StudentController {

  private StudentService service;

  @Autowired
  public StudentController(StudentService service) {
    this.service = service;
  }

  /**
   * 受講生一覧検索です。
   * 全体検索を行うので、条件指定は行いません。
   *
   * @return　受講生一覧（全件）
   */
  @GetMapping("/studentList")
  public List<StudentDetail> getStudentList() {
    return  service.searchStudentList();
  }


  @GetMapping("/studentCourseList")
  public List<StudentCourse> getStudentCourseList() {
    return service.searchStudentCourseList();
  }

  /**
   * 受講生検索です。
   * IDに基づく任意の受講生の情報を取得します。
   *
   * @param id　受講生ID
   * @return 受講生
   */
  @GetMapping("/student/{id}")
  public StudentDetail getStudent(@PathVariable int id) {
    return service.searchStudent(id);
  }

  @PostMapping("/updateStudent")
  public ResponseEntity<String> updateStudent(
      @RequestBody StudentDetail studentDetail) {
    service.updateStudent(studentDetail);

    return ResponseEntity.ok("更新処理が成功しました。");
  }


  // 登録処理
  @PostMapping("/registerStudent")
  public ResponseEntity<StudentDetail> registerStudent(
      @RequestBody StudentDetail studentDetail) {
    StudentDetail responseStudentDetail = service.registerStudent(studentDetail);
    return ResponseEntity.ok(responseStudentDetail);
  }

}