package component.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ProxyHandler implements InvocationHandler {

    private Object concrete;

    public ProxyHandler(Object concrete) {
        this.concrete = concrete;
    }

    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable {
        // TODO Auto-generated method stub
        System.out.println("proxy:" + proxy.getClass().getName());
        System.out.println("method:" + method.getName());
        System.out.println("args:" + args[0].getClass().getName());

        System.out.println("Before invoke method...");
        // 普通的Java反射代码,通过反射执行某个类的某方法
        Object object = method.invoke(concrete, args);
        // System.out.println(((ConcreteClass)concreteClass).targetMethod(10)+(Integer)args[0]);
        System.out.println("After invoke method...");
        return object;
    }

}
