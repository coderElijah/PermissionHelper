package com.elijah.permissionhelper;

import java.security.Permission;

/**
 * Description:
 *
 * @author elijahliu
 * @Note Talk is cheap,just show me ur code.- -!
 * ProjectName:permissionhelper
 * PackageName: com.elijah.permissionhelper
 * Date: 2019/12/9 15:36
 */
public class PermissionHelper {
    private static final ThreadLocal<PermissionObject> permissionObjectThreadLocal = new ThreadLocal<>();

    public static void doPermissionCheck(Object permissionKey,Object permissionValue){
        PermissionObject permissionObject = new PermissionObject();
        permissionObject.setPermissionKey(permissionKey);
        permissionObject.setPermissionValue(permissionValue);
        permissionObject.setPermission(Boolean.TRUE);
        permissionObjectThreadLocal.set(permissionObject);
    }

    public static PermissionObject getLocalPermission() {
        if (permissionObjectThreadLocal.get() == null) {
            permissionObjectThreadLocal.set(new PermissionObject());
        }
        return permissionObjectThreadLocal.get();
    }
    public static void reset(){
        permissionObjectThreadLocal.set(null);
    }

}
