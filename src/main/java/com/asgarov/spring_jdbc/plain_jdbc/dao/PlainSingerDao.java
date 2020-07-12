package com.asgarov.spring_jdbc.plain_jdbc.dao;

import com.asgarov.spring_jdbc.plain_jdbc.entity.Singer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlainSingerDao implements SingerDao {

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Problem loading DB dDriver!");
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/musicDB?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
                "asgarov", "password");
    }

    @Override
    public List<Singer> findAll() {
        List<Singer> result = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("select * from SINGER");) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Singer singer = new Singer();
                singer.setId(resultSet.getLong("id"));
                singer.setFirstName(resultSet.getString("first_name"));
                singer.setLastName(resultSet.getString("last_name"));
                singer.setBirthDate(resultSet.getDate("birth_date"));
                result.add(singer);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return result;
    }

    @Override
    public void insert(Singer singer) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "insert into SINGER (first_name,  last_name, birth_date) values (?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, singer.getFirstName());
            statement.setString(2, singer.getLastName());
            statement.setDate(3, singer.getBirthDate());
            statement.execute();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                singer.setId(generatedKeys.getLong(1));
            }
        } catch (SQLException ex) {
            System.out.println("Problem  executing INSERT");
        }
    }

    @Override
    public void delete(Long singerId) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement
                     ("delete from SINGER where id=?")) {
            statement.setLong(1, singerId);
            statement.execute();
        } catch (SQLException ex) {
            System.out.println("Problem executing DELETE");
        }
    }
}
