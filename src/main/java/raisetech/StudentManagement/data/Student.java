package raisetech.StudentManagement.data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import  lombok.Getter;
import  lombok.Setter;

@Getter
@Setter

public class Student {

  private  int id;
  private  String name;

  @NotBlank
  @Pattern(regexp = "^[ァ-ヶー]+$")
  private String kanaName;

  private  String nickname;
  private  String email;
  private  String area;
  private  int age;
  private  String sex;
  private String remark;
  private boolean isDeleted;
}
