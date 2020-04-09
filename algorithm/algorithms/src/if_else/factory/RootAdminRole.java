package if_else.factory;

import if_else.RoleOperation;

public class RootAdminRole implements RoleOperation {

    private String roleName;

    public RootAdminRole(String roleName){
        this.roleName = roleName;
    }

    @Override
    public String op(){
        return roleName + "has A permission";
    }
}
