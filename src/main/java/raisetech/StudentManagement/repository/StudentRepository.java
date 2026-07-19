package raisetech.StudentManagement.repository;


import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;



@Mapper
public interface StudentRepository {

  @Select("SELECT * FROM students")
  List<Student> search();

  @Select("SELECT * FROM students_courses")
  List<StudentCourse> searchStudentCourses();

  @Options(useGeneratedKeys = true, keyProperty = "id")
  @Insert("""
    INSERT INTO students
    (name, kana_name, nickname, email, area, age, sex, remark, is_deleted)
    VALUES
    (#{name}, #{kanaName}, #{nickname}, #{email},
     #{area}, #{age}, #{sex}, #{remark}, #{isDeleted})
    """)
  void registerStudent(Student student);

}
