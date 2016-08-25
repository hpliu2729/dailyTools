package component.proxy;

import org.junit.Test;

public class StaticProxyTest {
    
    @Test
    public void testStaticProxy(){
        CountStaticProxy cp = new CountStaticProxy();
        cp.queryCount();
        cp.updateCount();
    }

}
