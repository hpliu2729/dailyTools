package study.thread;

public class SingleTon {
    
    private static SingleTon singleTon;
    private long createTime;
    public SingleTon(){
        this.createTime = System.currentTimeMillis();
    }
    
    public long getTime(){
        return createTime;
    }

    public static SingleTon getInstance() {
        // TODO Auto-generated method stub
        if(singleTon == null){
            singleTon = new SingleTon();
        }
        return singleTon;
    }
    

}
