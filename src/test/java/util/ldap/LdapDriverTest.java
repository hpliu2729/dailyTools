package util.ldap;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.ConfigDriver;

import javax.naming.ldap.LdapContext;
import java.util.List;

/**
 * @author XuYexin
 * @Description :
 * @date 2015/11/10
 */
public class LdapDriverTest {

    private final static Logger logger = LoggerFactory.getLogger(LdapDriverTest.class);

    @Test
    /**
     * 测试登陆
     */
    public void TestLdap() {
        long startTime = System.currentTimeMillis();
        logger.info("结果为空？" + LdapDriver.ldapLogin("123", "@oneplus.local", "mima"));
        long endTime = System.currentTimeMillis();
        logger.info("-------------------TIME : " + (endTime - startTime) + "---------------------");

    }

    @Test
    @Ignore
    /**
     * 测试查找
     */
    public void ListLdap() {
        long startTime = System.currentTimeMillis();

        logger.info("参数："
                + ConfigDriver.getString("LDAP.URL")
                + " || "
                + ConfigDriver.getString("LDAP.MANAGER_DN")
                + " || "
                + ConfigDriver.getString("LDAP.MANAGER_PASS"));

        LdapDriver ld = new LdapDriver();
        LdapContext ctx = ld.getCTX(ConfigDriver.getString("LDAP.URL"),
                ConfigDriver.getString("LDAP.MANAGER_DN"), ConfigDriver.getString("LDAP.MANAGER_PASS"));
        if (ctx == null) {
            logger.info("连接失败1："
                    + ConfigDriver.getString("LDAP.URL")
                    + " || "
                    + ConfigDriver.getString("LDAP.MANAGER_DN")
                    + " || "
                    + ConfigDriver.getString("LDAP.MANAGER_PASS"));
            return;
        } else {
            logger.info("连接成功："
                    + ConfigDriver.getString("LDAP.URL")
                    + " || "
                    + ConfigDriver.getString("LDAP.MANAGER_DN")
                    + " || "
                    + ConfigDriver.getString("LDAP.MANAGER_PASS"));
        }


        List<LdapUser> users = null;
        int number = 0;
        try {
            users = ld.queryUser(
                    ctx,
                    ConfigDriver.getString("LDAP.BASE_DN"),
                    "objectClass=person",
                    ConfigDriver.getString("LDAP.ATTR").split(",")
            );

            for (LdapUser user : users) {
                logger.info("UUUUUUU : " + user.toString());
                number++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        long endTime = System.currentTimeMillis();
        logger.info("-------------------TIME : " + (endTime - startTime) + "---------------------");
        logger.info("-------------------COUNT : " + number + "---------------------");
    }

    @Test
    @Ignore
    /**
     * 测试连接
     */
    public void ConnectLdap() {
        long startTime = System.currentTimeMillis();
        LdapDriver ld = new LdapDriver();
        LdapContext ctx = ld.getCTX(
                ConfigDriver.getString("LDAP.URL"),
                "666666@oneplus.test",
                "123!@#qwe"
        );
        logger.info("结果为空？" + ctx);
        long endTime = System.currentTimeMillis();
        logger.info("-------------------TIME : " + (endTime - startTime) + "---------------------");

    }


}
