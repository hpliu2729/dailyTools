package component.proxy;

import component.proxy.impl.Concrete;
import component.proxy.impl.CountImpl;
import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class ProxyTest {

    @Test
    public void testProxy() {
        // 元对象(被代理对象)
        Concrete c = new Concrete();
        // 代理实例的调用处理程序。
        InvocationHandler ih = new ProxyHandler(c);
        
        // 创建一个实现业务接口的代理类,用于访问业务类(见代理模式)。
        // 返回一个指定接口的代理类实例，该接口可以将方法调用指派到指定的调用处理程序，如ProxyHandler。
        TargetInterFace targetInterface = (TargetInterFace) Proxy
                .newProxyInstance(c.getClass().getClassLoader(), c.getClass()
                        .getInterfaces(), ih);
        
        // 调用代理类方法,Java执行InvocationHandler接口的方法.
        int i = targetInterface.methodA(5);
        System.out.println(i);
        System.out.println();
        int j = targetInterface.methodB(15);
        System.out.println(j);
    }
    
    @Test
    public void testProxyCount(){
        CountProxy proxy = new CountProxy();
        Count count = (Count)(proxy.bind(new CountImpl()));
        count.queryCount();
        count.updateCount();
    }

}
