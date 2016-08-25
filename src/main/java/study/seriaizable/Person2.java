package study.seriaizable;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.List;

public class Person2 implements Externalizable {

    private static final long serialVersionUID = 1L;
    public static String type = "human";

    private String name;
    transient private  int age;
    private List<String> pets;


    public Person2() {
        this.pets = new ArrayList<String>();
        System.out.println("this is constructor method");
    }

    @Override
    public String toString() {
        String temp="";
        temp += "name:" + name + ", age:" + age + ", type:" + type;
        for(String pet : pets){
            temp += "-"+pet;
        }
        return temp;
    }
    
    public void writeExternal(ObjectOutput out) throws IOException {
        // TODO Auto-generated method stub
        out.writeInt(age);
        out.writeObject(name);
        out.writeObject(pets);
        
    }

    @SuppressWarnings("unchecked")
    public void readExternal(ObjectInput in) throws IOException,
            ClassNotFoundException {
        // TODO Auto-generated method stub
        age = in.readInt();
        name = (String)in.readObject();
        pets = (List<String>) in.readObject();
        
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
    
    public List<String> getPets() {
        return pets;
    }

    public void setPets(List<String> pets) {
        this.pets = pets;
    }

    

}
