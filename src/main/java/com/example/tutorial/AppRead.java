package com.example.tutorial;

import com.example.tutorial.AddressBookProtos.AddressBook;
import com.example.tutorial.AddressBookProtos.Person;

import java.io.FileInputStream;

/**
 * Created by njp on 2016/5/11.
 */
public class AppRead {
    public static void main(String[] args ) throws Exception{
        FileInputStream fi = new FileInputStream("hello");
        AddressBook ab = AddressBook.parseFrom(fi);

        Person person = ab.getPerson(0);

        System.out.println(person.getId());
        System.out.println(person.getName());
        System.out.println(person.getEmail());

        Person.PhoneNumber phoneNumber = person.getPhone(0);

        System.out.println(phoneNumber.getNumber());


        fi.close();
    }
}
