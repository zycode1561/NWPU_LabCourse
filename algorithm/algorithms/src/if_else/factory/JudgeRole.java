package if_else.factory;

import if_else.RoleOperation;

public class JudgeRole {
    public String judge(String roleName){
        return RoleFactory.getOp(roleName).op();
    }

    public String judge(RoleOperation operation){
        RoleContext roleContext = new RoleContext(operation);
        return roleContext.execute();
    }
}
