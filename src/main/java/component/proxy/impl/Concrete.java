package component.proxy.impl;


import component.proxy.TargetInterFace;

public class Concrete implements TargetInterFace {

    public int methodA(int num) {
        // TODO Auto-generated method stub
        System.out.println("调用MethodA方法");
        System.out.println("打印数字："+ num);
        System.out.println("打印结束");
        return num;
    }

    public int methodB(int num) {
        // TODO Auto-generated method stub
        System.out.println("调用MethodB方法");
        System.out.println("打印数字："+ num);
        System.out.println("打印结束");
        return num;
    }

}
