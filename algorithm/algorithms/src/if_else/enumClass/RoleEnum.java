package if_else.enumClass;

import if_else.RoleOperation;

public enum RoleEnum implements RoleOperation {
    //系统管理员，A权限
    ROLE_ROOT_ADMIN{
        @Override
        public String op(){
            return "ROLE_ROOT_ADMIN" + "has A permission!";
        }
    },
    //订单管理员，B权限
    ROLE_ORDER_ADMIN{
        @Override
        public String op(){
            return "ROLE_ORDER_ADMIN" + "has B permission!";
        }
    },
    //用户，C权限
    ROLE_NORMAL{
        @Override
        public String op(){
            return "ROLE_NORMAL" + "has C permission!";
        }
    };
}
