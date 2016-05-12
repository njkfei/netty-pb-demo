package com.example.tutorial;

import com.example.tutorial.AddressBookProtos.*;
import com.example.tutorial.AddressBookProtos.AddressBook;
import com.example.tutorial.AddressBookProtos.Person;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;

/**
 * Created by njp on 2016/5/11.
 */
public class AppWrite {
    public static void main(String[] args ) throws Exception{
        Person.PhoneNumber.Builder phone  = Person.PhoneNumber.newBuilder().setNumber("15910534204");

        Person.Builder person = Person.newBuilder().addPhone(phone).setId(1).setEmail("njp@sanhao.com")
                .setName("njp");
        AddressBook.Builder ab = AddressBook.newBuilder().addPerson(person);

        FileOutputStream fo = new FileOutputStream("hello");
        ab.build().writeTo(fo);
        fo.close();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ab.build().writeTo(byteArrayOutputStream);

        System.out.println(byteArrayOutputStream.toByteArray().length);

        AddressBook nab = AddressBook.parseFrom(byteArrayOutputStream.toByteArray());

        Person ps = nab.getPerson(0);

        System.out.println(ps.getId());
        System.out.println(ps.getName());
        System.out.println(ps.getEmail());

        Person.PhoneNumber phoneNumber = ps.getPhone(0);

        System.out.println(phoneNumber.getNumber());

        byteArrayOutputStream.close();
    }
}
