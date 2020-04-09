package if_else.factory;

import if_else.RoleOperation;

public class OrderAdminRole implements RoleOperation {

    private String roleName;

    public OrderAdminRole(String roleName){
        this.roleName = roleName;
    }

    @Override
    public String op(){
        return roleName + "has B permission";
    }
}
