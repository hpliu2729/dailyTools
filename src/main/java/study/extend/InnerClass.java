package study.extend;

public class InnerClass {
    
    public Integer out = new Integer(0);

    public Ancd ancd;

    public void createSon(){
        this.ancd = new Ancd(out);
    }

    class Ancd {
        Integer outStr;
        public Ancd(Integer outStr){
            this.outStr = outStr;
            this.print1();
            this.change();
        }
        void print1(){
            System.out.println(outStr);
        }
         void change(){
//             outStr.delete(0,outStr.length()).append("changed");
             outStr = outStr.parseInt("2");
         }
    }

}
