package raisetech.StudentManagement.domain;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import jakarta.validation.Valid;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class StudentDetail {

  @Valid
  private Student student;
  @Valid
  private List<StudentCourse> studentCourseList;

}
