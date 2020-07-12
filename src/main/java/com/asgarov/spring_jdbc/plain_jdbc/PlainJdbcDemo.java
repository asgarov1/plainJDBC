package com.asgarov.spring_jdbc.plain_jdbc;

import com.asgarov.spring_jdbc.plain_jdbc.dao.PlainSingerDao;
import com.asgarov.spring_jdbc.plain_jdbc.dao.SingerDao;
import com.asgarov.spring_jdbc.plain_jdbc.entity.Singer;

import java.sql.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class PlainJdbcDemo {
    private static SingerDao singerDao = new PlainSingerDao();

    public static void main(String[] args) {
        System.out.println("Listing initial singere data: ");

        listAllSingers();

        System.out.println("-------------");
        System.out.println("Insert a  new singer");
        Singer singer = new Singer();
        singer.setFirstName("Ed");
        singer.setLastName("Sheeran");
        singer.setBirthDate(new Date
                ((new GregorianCalendar(1991, 2, 1991)).getTime().getTime()));
        singerDao.insert(singer);
        System.out.println("Listing singer data after new singer created:");

        listAllSingers();
        System.out.println("-------------");
        System.out.println("Deleting the previous created singer");
        singerDao.delete(singer.getId());
        System.out.println("Listing singer data after new singer deleted:");
        listAllSingers();
    }

    private static void listAllSingers() {
        List<Singer> singers = singerDao.findAll();
        for (Singer singer : singers) {
            System.out.println(singer.toString());
        }
    }
}
