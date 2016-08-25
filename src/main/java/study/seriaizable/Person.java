package study.seriaizable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Person implements Serializable {

    private static final long serialVersionUID = 1L;
    public static String type = "human";

    private String name;
    transient private  int age;
    private List<String> pets;


    public Person() {
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
