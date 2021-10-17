package com.example.java_lab_3.data.userrepository;

import com.example.java_lab_3.data.userrepository.IUserRepository;
import com.example.java_lab_3.models.UserProfile;

import javax.servlet.http.HttpSession;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class H2UserRepositoryImpl implements IUserRepository {
    private static Connection c;
    private static final String DB_URL =
            "jdbc:h2:./db";
    private static final String H2_JAR_PATH =
            "file://./src/main/webapp/WEB-INF/lib/h2-1.4.200.jar";

    public H2UserRepositoryImpl() {
        try {
            init();
        } catch (Exception e) {
            System.out.printf("Failed to init User Repository:\n\t\t\t%s\n", e.getLocalizedMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void addUser(UserProfile user) {
        execute(String.format(
                "insert into user values('%s', '%s', '%s', '%s')",
                user.getId(),
                user.getLogin(),
                user.getPassword(),
                user.getEmail()
        ));
    }

    @Override
    public void addSession(String userId, HttpSession session) {
        execute(String.format(
                "insert into session values('%s', '%s')",
                userId,
                session.getId()
        ));
    }

    @Override
    public UserProfile getUserBySessionId(String sessionId) {
        List<Object[]> sessionRes = executeForResult(
                String.format("select * from session where sessionId = '%s'", sessionId)
        );
        if (!hasResult(sessionRes))
            return null;

        List<Object[]> userRes =executeForResult(
                String.format(
                        "select * from user where userId = '%s'",
                        sessionRes.get(0)[0]
                )
        );
        return hasResult(userRes) ? mapToUser(userRes.get(0)) : null;
    }

    @Override
    public UserProfile getUserByLogin(String login) {
        List<Object[]> list = executeForResult(
                String.format("select * from user where login = '%s'", login)
        );
        return hasResult(list) ? mapToUser(list.get(0)) : null;
    }

    @Override
    public UserProfile getUserByEmail(String email) {
        List<Object[]> list = executeForResult(
                String.format("select * from user where email = '%s'", email)
        );
        return hasResult(list) ? mapToUser(list.get(0)) : null;
    }

    @Override
    public boolean isSessionRegistered(String sessionId) {
        return hasResult(
                executeForResult(String.format(
                        "select * from session where sessionId = '%s'", sessionId)
                )
        );
    }

    @Override
    public void cleanUserSession(String userId) {
        execute(String.format(
                "delete from session where userId = '%s'", userId)
        );
    }

    private void init() throws SQLException, ClassNotFoundException, MalformedURLException {
        URLClassLoader child = new URLClassLoader(
                new URL[] {new URL(H2_JAR_PATH)},
                this.getClass().getClassLoader()
        );
        Class.forName("org.h2.Driver", true, child);
        c = DriverManager.getConnection(DB_URL);

        createTables();
    }

    private void createTables() {
        execute(
                "create table if not exists user (" +
                "    userId          varchar2(250) primary key," +
                "    login           varchar2(250) not null," +
                "    password        varchar2(250) not null," +
                "    email           varchar2(250) not null" +
                ");"
        );
        execute(
                "create table if not exists session (" +
                "    userId          varchar2(250) primary key," +
                "    sessionId       varchar2(500) not null" +
                ");"
        );
        execute(
                "ALTER TABLE session ADD FOREIGN KEY (userId)\n" +
                "    REFERENCES user(login);"
        );

        cleanupSessions();
    }

    private void cleanupUsers() {
        execute("delete from user");
    }

    private void cleanupSessions() {
        execute("delete from session");
    }

    private void execute(String query) {
        try {
            c.createStatement().execute(query);
            c.commit();
            System.out.println("Executed successfully, query:\n\t" + query + "\nTable 'user':");
            printResult(
                    c.createStatement().executeQuery("select * from user"),
                    o -> System.out.println("\t" + o.toString())
            );
            System.out.println("Table 'session':");
            printResult(
                    c.createStatement().executeQuery("select  * from session"),
                    o -> System.out.println("\t" + o.toString())
            );
        } catch (SQLException e) {
            System.out.printf("Failed to execute query '%s':\n\t\t\t%s\n", query, e.getLocalizedMessage());
            e.printStackTrace();
        }
    }

    private boolean hasResult(List<Object[]> result) {
        return result != null && result.size() > 0;
    }

    private List<Object[]> executeForResult(String query) {
        try {
            ResultSet set = c.createStatement().executeQuery(query);

            List<Object[]> result = new ArrayList<>();
            int tupleSize = set.getMetaData().getColumnCount();
            while (set.next())
                result.add(extractTuple(set, tupleSize));

            System.out.println("Executed successfully, query:\n\t" + query + "\nResult:");
            printResult(
                    c.createStatement().executeQuery(query),
                    o -> System.out.println("\t" + o.toString())
            );

            return result;
        } catch (SQLException e) {
            System.out.printf("Failed to execute query '%s':\n\t\t\t%s\n", query, e.getLocalizedMessage());
            e.printStackTrace();
            return null;
        }
    }

    private static Object[] extractTuple(ResultSet set, int tupleSize) throws SQLException {
        Object[] tuple = new Object[tupleSize];
        for (int i = 1; i <= tupleSize; i++) {
            tuple[i - 1] = set.getObject(set.getMetaData().getColumnLabel(i));
        }
        return tuple;
    }

    private UserProfile mapToUser(Object[] tuple) {
        return new UserProfile(
                (String) tuple[0],
                (String) tuple[1],
                (String) tuple[2],
                (String) tuple[3]
        );
    }

    private static void printResult(ResultSet res, Consumer<Object> out) throws SQLException {
        StringBuilder columns = new StringBuilder();
        for (int i=1; i<=res.getMetaData().getColumnCount(); i++)
            columns.append("[").append(res.getMetaData().getColumnLabel(i)).append("];\t\t");
        out.accept(columns.toString());
        while (res.next()) {
            StringBuilder str = new StringBuilder();
            for (int i=1; i<=res.getMetaData().getColumnCount(); i++)
                str.append(res.getObject(res.getMetaData().getColumnLabel(i))).append(";\t\t\t\t");
            out.accept(str.toString());
        }
        out.accept("\n");
    }
}
