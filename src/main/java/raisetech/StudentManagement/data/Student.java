package raisetech.StudentManagement.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import  lombok.Getter;
import  lombok.Setter;

@Schema(description = "受講生")
@Getter
@Setter

public class Student {

  private  int id;
  private  String name;

  @NotBlank
  @Pattern(regexp = "^[ァ-ヶー]+$", message = "カタカナのみ入力するようにして下さい。")
  private String kanaName;

  private  String nickname;
  private  String email;
  private  String area;
  private  int age;
  private  String sex;
  private String remark;
  private boolean isDeleted;
}
