package util.ldap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.ConfigDriver;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import java.util.*;

/**
 * @author XuYexin
 * @Description :
 * @date 2015/11/3
 */
public class LdapDriver {
	private static final Logger logger = LoggerFactory.getLogger(LdapDriver.class);
	public String URL;
	public String BASE_DN;
	public String MANAGER_DN;
	public String MANAGER_PASS;
	public String[] ATTR;

	public LdapDriver(){
		this.URL = ConfigDriver.getString("LDAP.URL");
		this.BASE_DN = ConfigDriver.getString("LDAP.BASE_DN");
		this.MANAGER_DN = ConfigDriver.getString("LDAP.MANAGER_DN");
		this.MANAGER_PASS = ConfigDriver.getString("LDAP.MANAGER_PASS");
		this.ATTR = (ConfigDriver.getString("LDAP.ATTR")).split(",");
	}


	/**
	 * 获取连接
	 *
	 * @param url
	 * @param DN
	 * @param password
	 * @return
	 */
	public LdapContext getCTX(String url, String DN, String password) {
		Hashtable<String, String> env = new Hashtable<String, String>();
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory"); // LDAP 工厂
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(Context.PROVIDER_URL, url);
		env.put(Context.SECURITY_PRINCIPAL, DN); //  填DN
		env.put(Context.SECURITY_CREDENTIALS, password); // AD Password
		env.put("java.naming.ldap.attributes.binary", "objectSid objectGUID");
		LdapContext ldapCtx;
		try {
			ldapCtx = new InitialLdapContext(env, null);
			logger.info("Auth SUCCESS : " + DN + " , " + password);
			return ldapCtx;
		} catch (NamingException e) {
			logger.error("Auth FAIL : " + DN + " , " + password + " :" + e);
			return null;
		}
	}

	/**
	 * 关闭连接
	 *
	 * @param ctx
	 * @return
	 */
	public boolean closeCTX(LdapContext ctx) {
		if (ctx != null) {
			try {
				ctx.close();
				return true;
			} catch (NamingException e) {
				logger.info("ctx close error :", e);
				return false;
			}
		}
		return true;
	}


	/**
	 * 条件查询User
	 *
	 * @param ldapCtx
	 * @param baseDN
	 * @param filter
	 * @param attr
	 * @return
	 * @throws javax.naming.NamingException
	 */
	public List<LdapUser> queryUser(LdapContext ldapCtx, String baseDN, String filter, String[] attr) throws
		NamingException {
		ArrayList<LdapUser> users = new ArrayList<LdapUser>();
		SearchControls searchCtls = new SearchControls();
		searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
		searchCtls.setReturningAttributes(attr);
		NamingEnumeration<SearchResult> answer = ldapCtx.search(baseDN, filter, searchCtls);
		while (answer.hasMoreElements()) {
			Map<String, String> map = new HashMap<String, String>();
			SearchResult sr = answer.next();
			Attributes Attrs = sr.getAttributes();
			if (Attrs != null) {
				NamingEnumeration<?> ne = Attrs.getAll();
				while (ne.hasMore()) {
					Attribute Attr = (Attribute) ne.next();
					String name = Attr.getID();
					Enumeration<?> values = Attr.getAll();
					if (values != null) { // 迭代
						while (values.hasMoreElements()) {
							String value = "";
							if ("objectGUID".equals(name)) {
								value = UUID.nameUUIDFromBytes((byte[]) values.nextElement()).toString();
							} else if ("memberOf".equals(name) && map.get("memberOf") != null) {
								value = map.get("memberOf") + ";" + (String) values.nextElement();
							} else {
								value = (String) values.nextElement();
							}
							map.put(name, value);
						}
					}
				}
			}

			users.add(new LdapUser(map));
		}
		return users;
	}

	/**
	 * 用户登录
	 * @param jobNum 工号
	 * @param pass 密码
	 * @return 用户
	 */
	public static LdapUser ldapLogin(String jobNum, String domain, String pass) {
		LdapDriver ld = new LdapDriver();
		LdapContext ctx = ld.getCTX(ld.URL, ld.MANAGER_DN, ld.MANAGER_PASS);
		String userPrincipalName = jobNum + domain;
		try {
			List<LdapUser> users = ld.queryUser(ctx, ld.BASE_DN, "userPrincipalName=" + userPrincipalName, ld
                .ATTR);
			ld.closeCTX(ctx);

			if (null == users || users.size() == 0) {
				logger.error("User " + userPrincipalName + " 未找到");
				return null;
			}

			if (users.size() > 1) {
				logger.error("LDAP User " + userPrincipalName + " 重复");
				return null;
			}

			LdapUser user = users.get(0);
			LdapContext userCtx = ld.getCTX(ld.URL, user.getDn(), pass);
			if (null == userCtx) {
				logger.error("LDAP USER " + userPrincipalName + " 登录失败");
			} else {
				ld.closeCTX(userCtx);
				user.setPass(pass);
				logger.info("LDAP USER " + userPrincipalName + " 登录成功");
				return user;
			}
		} catch (Exception e) {
			logger.error("LDAP USER " + userPrincipalName +  " 登录错误", e);
		}

		return null;
	}

}
