package raisetech.StudentManagement;


import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;


@Mapper
public interface StudentRepository {

  @Select("SELECT * FROM students")
  List<raisetech.StudentManagement.Student> search();

  @Select("SELECT * FROM students_courses")
  List<raisetech.StudentManagement.StudentCourse> searchStudentCourses();

  @Select("SELECT * FROM students WHERE age BETWEEN 30 AND 39")
  List<raisetech.StudentManagement.Student> searchStudentsIn30s();

  @Select("SELECT * FROM students_courses WHERE course_name = 'Javaコース'")
  List<raisetech.StudentManagement.StudentCourse> searchJavaCourse();
}
