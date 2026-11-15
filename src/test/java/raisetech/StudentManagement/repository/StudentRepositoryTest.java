package raisetech.StudentManagement.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@MybatisTest
class StudentRepositoryTest {

  @Autowired
  private StudentRepository studentRepository;

  @Test
  void 受講生の全件検索ができること() {

    // 受講生を全件検索する
    List<Student> students = studentRepository.search();

    // 取得した件数を確認する
    assertEquals(2, students.size());
  }

  @Test
  void 受講生コース情報の全件検索ができること() {

    // コース情報を全件検索する
    List<StudentCourse> studentCourses =
        studentRepository.searchStudentCourseList();

    // 取得した件数を確認する
    assertEquals(2, studentCourses.size());
  }

  @Test
  void 指定したIDの受講生を検索できること() {

    // IDを指定して受講生を検索する
    Student student = studentRepository.searchStudent(1);

    // 取得した受講生の情報を確認する
    assertNotNull(student);
    assertEquals(1, student.getId());
    assertEquals("山田太郎", student.getName());
  }

  @Test
  void 指定した受講生IDのコース情報を検索できること() {

    // 受講生IDを指定してコース情報を検索する
    List<StudentCourse> studentCourses =
        studentRepository.searchStudentCourse(1);

    // 取得したコース情報の件数を確認する
    assertEquals(1, studentCourses.size());

    // コース情報の内容を確認する
    assertEquals(1, studentCourses.get(0).getStudentId());
    assertEquals("Javaコース", studentCourses.get(0).getCourseName());
  }

  @Test
  void 受講生の登録ができること() {

    // 登録する受講生を作成する
    Student student = new Student();
    student.setName("テスト太郎");
    student.setKanaName("テストタロウ");
    student.setNickname("テスト");
    student.setEmail("test-register@example.com");
    student.setArea("東京");
    student.setAge(25);
    student.setSex("男性");
    student.setRemark("登録テスト");

    // 受講生を登録する
    studentRepository.registerStudent(student);

    // 登録時にIDが自動設定されたことを確認する
    assertTrue(student.getId() > 0);

    // 登録した受講生をIDで検索する
    Student registeredStudent =
        studentRepository.searchStudent(student.getId());

    // 登録した内容を確認する
    assertNotNull(registeredStudent);
    assertEquals("テスト太郎", registeredStudent.getName());
    assertEquals("テストタロウ", registeredStudent.getKanaName());
    assertEquals("test-register@example.com", registeredStudent.getEmail());
  }

  @Test
  void 受講生コース情報の登録ができること() {

    // 登録するコース情報を作成する
    StudentCourse studentCourse = new StudentCourse();
    studentCourse.setStudentId(1);
    studentCourse.setCourseName("テストコース");
    studentCourse.setCourseStartAt(
        LocalDateTime.of(2026, 11, 15, 10, 0));
    studentCourse.setCourseEndAt(
        LocalDateTime.of(2027, 11, 15, 10, 0));

    // コース情報を登録する
    studentRepository.registerStudentCourse(studentCourse);

    // 登録時にIDが自動設定されたことを確認する
    assertTrue(studentCourse.getId() > 0);

    // 受講生IDを指定してコース情報を再検索する
    List<StudentCourse> studentCourses =
        studentRepository.searchStudentCourse(1);

    // 登録したコース情報をIDで探す
    StudentCourse registeredCourse = studentCourses.stream()
        .filter(course -> course.getId() == studentCourse.getId())
        .findFirst()
        .orElse(null);

    // 登録した内容を確認する
    assertNotNull(registeredCourse);
    assertEquals("テストコース", registeredCourse.getCourseName());
    assertEquals(1, registeredCourse.getStudentId());
  }

  @Test
  void 受講生情報の更新ができること() {

    // 更新する受講生を検索する
    Student student = studentRepository.searchStudent(1);

    // 更新する情報を設定する
    student.setNickname("更新テスト");
    student.setRemark("更新処理のテスト");

    // 受講生情報を更新する
    studentRepository.updateStudent(student);

    // 更新後の受講生情報を再検索する
    Student updatedStudent = studentRepository.searchStudent(1);

    // 更新した内容を確認する
    assertNotNull(updatedStudent);
    assertEquals("更新テスト", updatedStudent.getNickname());
    assertEquals("更新処理のテスト", updatedStudent.getRemark());
  }

  @Test
  void 受講生コース情報の更新ができること() {

    // 受講生IDが1のコース情報を検索する
    List<StudentCourse> studentCourses =
        studentRepository.searchStudentCourse(1);

    // 更新対象のコース情報を取得する
    StudentCourse studentCourse = studentCourses.get(0);

    // コース名を変更する
    studentCourse.setCourseName("更新テストコース");

    // コース情報を更新する
    studentRepository.updateStudentCourse(studentCourse);

    // 更新後のコース情報を再検索する
    List<StudentCourse> updatedCourses =
        studentRepository.searchStudentCourse(1);

    // 更新したコース情報をIDで探す
    StudentCourse updatedCourse = updatedCourses.stream()
        .filter(course -> course.getId() == studentCourse.getId())
        .findFirst()
        .orElse(null);

    // 更新内容を確認する
    assertNotNull(updatedCourse);
    assertEquals("更新テストコース", updatedCourse.getCourseName());
  }
}