package  raisetech.StudentManagement.service;


import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.StudentManagement.controller.converter.StudentConverter;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.repository.StudentRepository;

/**
 * 受講生情報を取り扱うサービスです。
 * 受講生の検索や登録・更新処理を行います。
 */
@Service
public class StudentService {

  private StudentRepository repository;
  private StudentConverter converter;


  @Autowired
  public StudentService(StudentRepository repository, StudentConverter converter ) {
    this.repository = repository;
    this.converter = converter;
  }

  /**
   * 受講生一覧検索です。
   * 全体検索を行うので、条件指定は行いません
   *
   * @return 受講生一覧（全件）
   */
  public List<StudentDetail> searchStudentList() {
    List<Student> studentList = repository.search();
    List<StudentCourse> studentCoursesList = repository.searchStudentCourses();
    return converter.convertStudentDetails(studentList, studentCoursesList);
  }
  public List<StudentCourse> searchStudentCourseList() {
    return repository.searchStudentCourses();
  }

  @Transactional
  public StudentDetail registerStudent(StudentDetail studentDetail) {
    repository.registerStudent(studentDetail.getStudent());
    for (StudentCourse studentCourse:studentDetail.getStudentsCourses()){
      studentCourse.setStudentId(studentDetail.getStudent().getId());
      studentCourse.setCourseStartAt(LocalDateTime.now());
      studentCourse.setCourseEndAt(LocalDateTime.now().plusYears(1));
      repository.registerStudentCourses(studentCourse);
    }
    return  studentDetail;
  }

  @Transactional
  public void updateStudent(StudentDetail studentDetail) {
    repository.updateStudent(studentDetail.getStudent());
    for (StudentCourse studentCourse:studentDetail.getStudentsCourses()){
    repository.updateStudentCourses(studentCourse);
    }
  }

  /**
   * 受講生検索です。
   * IDに基づく受講生情報を取得したあと、その受講生に紐づく受講生コース情報を取得して設定します。
   *
   * @param id　受講生ID
   * @return 受講生
   */
  public StudentDetail searchStudent(int id) {
    Student student = repository.searchStudent(id);
    List<StudentCourse> studentCourses = repository.searchStudentCourse(id);
    return new StudentDetail(student,studentCourses);
  }
}