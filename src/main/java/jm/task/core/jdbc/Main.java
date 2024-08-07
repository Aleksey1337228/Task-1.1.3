package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {

        UserDao userDao = new UserDaoHibernateImpl();


        UserService userService = new UserServiceImpl(userDao);


        userService.createUsersTable();


        userService.saveUser("Leha", "Bulshet", (byte) 25);
        System.out.println("User с именем Leha добавлен в базу данных");

        userService.saveUser("Cat", "Pit", (byte) 30);
        System.out.println("User с именем Cat добавлен в базу данных");

        userService.saveUser("Para", "Bara", (byte) 35);
        System.out.println("User с именем Para добавлен в базу данных");

        userService.saveUser("Lava", "Cata", (byte) 28);
        System.out.println("User с именем Lava добавлен в базу данных");


        System.out.println("Список всех пользователей:");
        for (User user : userService.getAllUsers()) {
            System.out.println(user);
        }


        userService.cleanUsersTable();


        userService.dropUsersTable();
    }
}
