package if_else.factory;

import if_else.RoleOperation;

import java.util.HashMap;
import java.util.Map;

public class RoleFactory{
    static Map<String, RoleOperation> roleOperationMap = new HashMap<>();

    //在静态块中先把初始化工作做完
    static {
        roleOperationMap.put("ROLE_ROOT_ADMIN",new RootAdminRole("ROLE_ROOT_ADMIN"));
        roleOperationMap.put("ROLE_ORDER_ADMIN",new RootAdminRole("ROLE_ORDER_ADMIN"));
        roleOperationMap.put("ROLE_NORMAL",new RootAdminRole("ROLE_NORMAL"));
    }
    public static RoleOperation getOp(String roleName){
        return roleOperationMap.get(roleName);
    }
}
