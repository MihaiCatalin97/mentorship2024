package org.java.mentorship;

public class Main {

    public static void main(String[] args) {
        PojoClass pojo = new PojoClass();

        pojo.setStringField("value");
        pojo.setIntField(0);
        pojo.setIntegerField(0);

        System.out.println(pojo);
    }
}