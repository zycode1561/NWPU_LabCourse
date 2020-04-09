package if_else.factory;

import if_else.RoleOperation;

public class NormalRole implements RoleOperation {

    private String roleName;

    public NormalRole(String roleName){
        this.roleName = roleName;
    }

    @Override
    public String op(){
        return roleName + "has C permission";
    }
}
