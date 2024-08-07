package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Collections;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.openSession()) {

            transaction = session.beginTransaction();


            String createTableQuery = "CREATE TABLE IF NOT EXISTS users (" +
                    "id BIGSERIAL PRIMARY KEY, " +
                    "name VARCHAR(255) NOT NULL, " +
                    "lastName VARCHAR(255) NOT NULL, " +
                    "age SMALLINT NOT NULL)";


            session.createSQLQuery(createTableQuery).executeUpdate();


            transaction.commit();
        } catch (Exception e) {

            if (transaction != null) {
                transaction.rollback();
            }

            System.out.println("Error creating table: " + e.getMessage());

        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.openSession()) {
            Transaction transaction = session.beginTransaction();

            String dropTableQuery = "DROP TABLE IF EXISTS users";

            session.createSQLQuery(dropTableQuery).executeUpdate();

            transaction.commit();
        } catch (Exception e) {
            System.out.println("Error dropping table: " + e.getMessage());
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.openSession()) {
            Transaction transaction = session.beginTransaction();

            User user = new User(name, lastName, age);
            session.save(user);

            transaction.commit();
        } catch (Exception e) {
            System.out.println("Error saving user: " + e.getMessage());
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.openSession()) {
            Transaction transaction = session.beginTransaction();

            User user = session.get(User.class, id);
            if (user != null) {
                session.delete(user);
            }

            transaction.commit();
        } catch (Exception e) {
            System.out.println("Error removing user by ID: " + e.getMessage());
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = Util.openSession()) {
            return session.createQuery("FROM User", User.class).list();
        } catch (Exception e) {
            System.out.println("Error getting all users: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = Util.openSession()) {
            Transaction transaction = session.beginTransaction();

            session.createQuery("DELETE FROM User").executeUpdate();

            transaction.commit();
        } catch (Exception e) {
            System.out.println("Error cleaning users table: " + e.getMessage());
        }
    }
}

