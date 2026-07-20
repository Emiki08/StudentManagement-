package raisetech.StudentManagement.repository;


import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import raisetech.StudentManagement.domain.StudentDetail;


@Mapper
public interface StudentRepository {

  @Select("""
SELECT *
FROM students
WHERE is_deleted = false
""")
  List<Student> search();

  @Select("SELECT * FROM students_courses")
  List<StudentCourse> searchStudentCourses();

  @Select("SELECT * FROM students WHERE id = #{id}")
  Student searchStudent(int id);

  @Select("SELECT * FROM students_courses WHERE student_id = #{studentId}")
  List<StudentCourse> searchStudentCourse(int studentId);


  @Insert("INSERT INTO students(name, kana_name, nickname, email, area, age, sex, remark, is_deleted) "
      + "VALUES(#{name}, #{kanaName}, #{nickname}, #{email},#{area}, #{age}, #{sex}, #{remark}, false)")
  @Options(useGeneratedKeys = true,keyProperty = "id")
  void registerStudent(Student student);

  @Insert("INSERT INTO students_courses(student_id, course_name, course_start_at, course_end_at)"
      + " VALUES(#{studentId}, #{courseName}, #{courseStartAt}, #{courseEndAt})")

  @Options(useGeneratedKeys = true,keyProperty = "id")
  void registerStudentCourses(StudentCourse studentCourse);


  @Update("""
UPDATE students
SET name = #{name},
    kana_name = #{kanaName},
    nickname = #{nickname},
    email = #{email},
    area = #{area},
    age = #{age},
    sex = #{sex},
    remark = #{remark},
    is_deleted = #{isDeleted}
WHERE id = #{id}
""")
  void updateStudent(Student student);

  @Update("""
UPDATE students_courses
SET course_name = #{courseName}
WHERE id = #{id}
""")
  void updateStudentCourses(StudentCourse studentCourse);

}
