package raisetech.StudentManagement.data;

import  lombok.Getter;
import  lombok.Setter;

@Getter
@Setter

public class Student {

  private  int id;
  private  String name;
  private  String furigana;
  private  String nickname;
  private  String email;
  private  String area;
  private  int age;
  private  String sex;
  // 追加
  private String remark;
  private boolean isDeleted;
}
