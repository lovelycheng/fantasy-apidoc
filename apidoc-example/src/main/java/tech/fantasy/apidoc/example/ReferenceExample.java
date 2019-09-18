package tech.fantasy.apidoc.example;

import tech.fantasy.apidoc.example.model.User;

import java.util.List;
import java.util.Map;

/**
 * @author chengtong
 * @date 2019-09-18 08:40
 */
public interface ReferenceExample {

     User getById(Integer id);

     void deleteUsers(List<User> users);

     Map<Integer,User> viewIndexedUsers();

     Map<String,List<User>> groupByFirstName();

}
