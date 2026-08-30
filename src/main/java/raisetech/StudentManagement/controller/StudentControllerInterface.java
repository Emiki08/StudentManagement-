package raisetech.StudentManagement.controller;

import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import org.springframework.http.ResponseEntity;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.exception.TestException;


public interface StudentControllerInterface {

  @Operation(
      summary = "受講生一覧検索",
      description = "受講生の一覧を検索します。"
  )
  List<StudentDetail> getStudentList() throws TestException;

  @Operation(
      summary = "受講生コース一覧検索",
      description = "受講生のコース一覧を検索します。"
  )
  List<StudentCourse> getStudentCourseList();

  @Operation(
      summary = "受講生詳細検索",
      description = "IDに基づいて受講生の詳細を検索します。"
  )
  StudentDetail getStudent(int id);

  @Operation(
      summary = "受講生情報更新",
      description = "受講生の情報を更新します。"
  )
  ResponseEntity<String> updateStudent(StudentDetail studentDetail);

  @Operation(
      summary = "受講生登録",
      description = "受講生を登録します。"
  )
  ResponseEntity<StudentDetail> registerStudent(StudentDetail studentDetail);
}