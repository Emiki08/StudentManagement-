package raisetech.StudentManagement.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.service.StudentService;


@WebMvcTest(StudentController.class)
class StudentControllerTest {

  @Autowired
  private MockMvc mockMvc;

  private ObjectMapper objectMapper = new ObjectMapper();

  @MockitoBean
  private StudentService service;

  private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();


  @Test
  void 受講生詳細の一覧検索が実行できて空のリストが返ってきてくること() throws Exception{
    mockMvc.perform(get("/studentList"))
        .andExpect(status().isOk());

    verify(service,times(1)).searchStudentList();
  }

  @Test
  void 受講生詳細の受講生で適切な値を入力した時に入力チェックに異常が発生しないこと(){
      Student student = new Student();
      student.setId(1);
      student.setName("江並公史");
      student.setKanaName("エナミコウジ");
      student.setNickname("エナミ");
      student.setEmail("test@example.com");
      student.setArea("奈良県");
      student.setSex("男性");
      
      Set<ConstraintViolation<Student>> violations = validator.validate(student);

      assertThat(violations.size()).isEqualTo(0);

    }


  @Test
  void 受講生詳細の検索_IDを指定して受講生情報が取得できること() throws Exception {
    // 受講生情報を準備
    Student student = new Student();
    student.setId(1);
    student.setName("江並公史");
    student.setKanaName("エナミコウジ");
    student.setNickname("エナミ");
    student.setEmail("test@example.com");
    student.setArea("奈良県");
    student.setAge(36);
    student.setSex("男性");

    // 受講生コース情報は空のリストにする
    StudentDetail studentDetail = new StudentDetail(student, List.of());

    // searchStudent(1) が呼ばれたら、上記のデータを返す
    when(service.searchStudent(1)).thenReturn(studentDetail);

    // APIを呼び出して、レスポンスを確認
    mockMvc.perform(get("/student/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.student.id").value(1))
        .andExpect(jsonPath("$.student.name").value("江並公史"))
        .andExpect(jsonPath("$.student.kanaName").value("エナミコウジ"));

    verify(service, times(1)).searchStudent(1);
  }
  @Test
  void 受講生コース情報の一覧検索が実行できてコース情報が返ってくること() throws Exception {

    // 受講生コース情報を準備
    StudentCourse studentCourse = new StudentCourse();
    studentCourse.setId(1);
    studentCourse.setStudentId(1);
    studentCourse.setCourseName("Javaコース");

    // 検索されたら、準備したコース情報を返す
    when(service.searchStudentCourseList()).thenReturn(List.of(studentCourse));

    // APIを呼び出して、レスポンスを確認
    mockMvc.perform(get("/studentCourseList"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(1))
        .andExpect(jsonPath("$[0].studentId").value(1))
        .andExpect(jsonPath("$[0].courseName").value("Javaコース"));

    verify(service, times(1)).searchStudentCourseList();
  }
  @Test
  void 受講生詳細の登録_APIが正常に動作すること() throws Exception {
    // 事前準備
    Student student = new Student();
    student.setId(1);
    student.setName("江並公史");
    student.setKanaName("エナミコウジ");

    StudentDetail studentDetail = new StudentDetail(student, List.of());

    when(service.registerStudent(any(StudentDetail.class)))
        .thenReturn(studentDetail);

    // テスト実行・検証
    mockMvc.perform(post("/registerStudent")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(studentDetail)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.student.id").value(1))
        .andExpect(jsonPath("$.student.name").value("江並公史"));

    verify(service, times(1)).registerStudent(any(StudentDetail.class));
  }

  @Test
  void 受講生詳細の更新_APIが正常に動作すること() throws Exception {
    // 事前準備
    Student student = new Student();
    student.setId(1);
    student.setName("江並公史");
    student.setKanaName("エナミコウジ");

    StudentDetail studentDetail = new StudentDetail(student, List.of());

    // テスト実行・検証
    mockMvc.perform(put("/updateStudent")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(studentDetail)))
        .andExpect(status().isOk())
        .andExpect(content().string("更新処理が成功しました。"));

    verify(service, times(1)).updateStudent(any(StudentDetail.class));
  }
}
