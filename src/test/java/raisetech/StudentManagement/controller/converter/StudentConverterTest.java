
package raisetech.StudentManagement.controller.converter;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import raisetech.StudentManagement.controller.converter.StudentConverter;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.domain.StudentDetail;

class StudentConverterTest {

  @Test
  void 受講生とコース情報が正しく紐付けられること() {
    // 事前準備
    Student student1 = new Student();
    student1.setId(1);
    student1.setName("山田太郎");

    Student student2 = new Student();
    student2.setId(2);
    student2.setName("伊藤花");

    StudentCourse course1 = new StudentCourse();
    course1.setStudentId(1);
    course1.setCourseName("Javaコース");

    StudentCourse course2 = new StudentCourse();
    course2.setStudentId(1);
    course2.setCourseName("AWSコース");

    StudentCourse course3 = new StudentCourse();
    course3.setStudentId(2);
    course3.setCourseName("デザインコース");

    List<Student> studentList = List.of(student1, student2);
    List<StudentCourse> studentCourseList =
        List.of(course1, course2, course3);

    // テスト対象の実行
    StudentConverter sut = new StudentConverter();

    List<StudentDetail> actual =
        sut.convertStudentDetails(studentList, studentCourseList);

    // 検証
    assertThat(actual).hasSize(2);

    assertThat(actual.get(0).getStudent().getName())
        .isEqualTo("山田太郎");

    assertThat(actual.get(0).getStudentCourseList())
        .hasSize(2);

    assertThat(actual.get(0).getStudentCourseList())
        .extracting(StudentCourse::getCourseName)
        .containsExactly("Javaコース", "AWSコース");

    assertThat(actual.get(1).getStudent().getName())
        .isEqualTo("伊藤花");

    assertThat(actual.get(1).getStudentCourseList())
        .hasSize(1);

    assertThat(actual.get(1).getStudentCourseList())
        .extracting(StudentCourse::getCourseName)
        .containsExactly("デザインコース");
  }

  @Test
  void 該当するコース情報がない場合は空のリストになること() {
    // 事前準備
    Student student = new Student();
    student.setId(1);
    student.setName("山田太郎");

    List<Student> studentList = List.of(student);
    List<StudentCourse> studentCourseList = List.of();

    // テスト対象の実行
    StudentConverter sut = new StudentConverter();

    List<StudentDetail> actual =
        sut.convertStudentDetails(studentList, studentCourseList);

    // 検証
    assertThat(actual).hasSize(1);

    assertThat(actual.get(0).getStudent().getName())
        .isEqualTo("山田太郎");

    assertThat(actual.get(0).getStudentCourseList())
        .isEmpty();
  }
}
