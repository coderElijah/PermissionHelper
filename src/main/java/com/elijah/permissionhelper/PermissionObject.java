package com.elijah.permissionhelper;

/**
 * Description:
 *
 * @author elijahliu
 * @Note Talk is cheap,just show me ur code.- -!
 * ProjectName:permissionhelper
 * PackageName: com.elijah.permissionhelper
 * Date: 2019/12/9 15:39
 */
public class PermissionObject {
    public PermissionObject(){
        this.isPermission = Boolean.FALSE;
        this.permissionKey = null;
        this.permissionValue = null;
    }
    private Object permissionKey;
    private Object permissionValue;
    private Boolean isPermission;

    public Object getPermissionKey() {
        return permissionKey;
    }

    public void setPermissionKey(Object permissionKey) {
        this.permissionKey = permissionKey;
    }

    public Object getPermissionValue() {
        return permissionValue;
    }

    public void setPermissionValue(Object permissionValue) {
        this.permissionValue = permissionValue;
    }

    public Boolean getPermission() {
        return isPermission;
    }

    public void setPermission(Boolean permission) {
        isPermission = permission;
    }
}
