package study.extend;

import org.junit.Test;

/**
 * @author XuYexin
 * @Description :
 * @date 2016/3/14
 */
public class InnerClassTest {

    @Test
    public void testChangeString(){
        InnerClass innerClass = new InnerClass();
        innerClass.createSon();
        System.out.println(innerClass.out);
    }


}
