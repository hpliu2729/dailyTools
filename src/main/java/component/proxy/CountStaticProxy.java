package component.proxy;

import component.proxy.impl.CountImpl;

public class CountStaticProxy implements Count {
    
    private CountImpl countImpl = new CountImpl();
    
    public void queryCount() {
        // TODO Auto-generated method stub
        System.out.println("执行查看方法之前的操作");
        countImpl.queryCount();
        System.out.println("执行查看方法之后的操作");

    }

    public void updateCount() {
        // TODO Auto-generated method stub
        System.out.println("执行更新方法之前的操作");
        countImpl.updateCount();
        System.out.println("执行更新方法之后的操作");

    }

}
