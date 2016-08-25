package study.seriaizable;

import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;

import java.io.*;

public class PersonTest {

    public static final Logger log = Logger.getLogger(PersonTest.class);
    private Person person = new Person();
    private Person2 person2 = new Person2();

    @Test
    @Ignore
    @SuppressWarnings("static-access")
    public void seriaizableTest() {

        person.setAge(22);
        person.setName("xuyexin");
        person.type = "大猩猩";
        person.getPets().add("dog");
        person.getPets().add("dog");
        person.getPets().add("cat");

        File file = new File("/Users/xuyexin/xuyexin.txt");
        try {
            if (!file.exists()) {
                file.createNewFile();
            }

            FileOutputStream out = new FileOutputStream(file);
            ObjectOutputStream oout = new ObjectOutputStream(out);
            oout.writeObject(person);
            oout.close();
            out.close();

            FileInputStream in = new FileInputStream(file);
            ObjectInputStream oin = new ObjectInputStream(in);
            Object newPerson = oin.readObject();
            oin.close();

            log.info(newPerson);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test
    @SuppressWarnings("static-access")
    public void seriaizableTest2() {

        person2.setAge(22);
        person2.setName("xuyexin");
        person2.type = "大猩猩";
        person2.getPets().add("dog");
        person2.getPets().add("dog");
        person2.getPets().add("cat");

        File file = new File("/Users/xuyexin/xuyexin.txt");
        try {
            if (!file.exists()) {
                file.createNewFile();
            }

            FileOutputStream out = new FileOutputStream(file);
            ObjectOutputStream oout = new ObjectOutputStream(out);
            oout.writeObject(person2);
            oout.close();
            out.close();

            FileInputStream in = new FileInputStream(file);
            ObjectInputStream oin = new ObjectInputStream(in);
            Object newPerson = oin.readObject();
            oin.close();

            log.info(newPerson);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
