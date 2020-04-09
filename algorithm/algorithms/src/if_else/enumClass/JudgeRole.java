package if_else.enumClass;

public class JudgeRole {
    public String judge(String roleName){
        //一行代码搞定
        return RoleEnum.valueOf(roleName).op();
    }
}
