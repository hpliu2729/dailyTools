package util.ldap;

import java.util.Map;

/**
 * @author XuYexin
 * @Description :
 * @date 2015/11/5
 */
public class LdapUser {

    /**
     * 认证名
     */
    private String principalName;
    /**
     * 认证密码
     */
    private String pass;
    /**
     * 用户名
     */
    private String name;
    /**
     * 邮箱
     */
    private String email;
    /**
     * DN
     */
    private String dn;
    /**
     * 组
     */
    private String[] groups = {};
    /**
     * 显示名
     */
    private String displayName;

    public LdapUser() {
    }

    public LdapUser(Map map) {
        this.principalName = (String) map.get("userPrincipalName");
        this.email = (String) map.get("mail");
        this.dn = (String) map.get("distinguishedName");
        this.name = (String) map.get("name");
        String memberOf = (String) (map.get("memberOf"));
        if (null == memberOf || "".equals(memberOf)) {
            this.groups = new String[0];
        } else
            this.groups = memberOf.split(";");
    }

    public String getPrincipalName() {
        return principalName;
    }

    public void setPrincipalName(String principalName) {
        this.principalName = principalName;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDn() {
        return dn;
    }

    public void setDn(String dn) {
        this.dn = dn;
    }

    public String[] getGroups() {
        return groups;
    }

    public void setGroups(String[] groups) {
        this.groups = groups;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String toString() {
        String str = "principalName:" + principalName
                + " pass: " + pass + " , "
                + " name: " + name + " , "
                + " email: " + email + " , "
                + " dn: " + dn + " , "
                + "  groups: ";
        if (null != groups) {
            for (String group : groups) {
                str += group + " ; ";
            }
        }
        return str;
    }

}
