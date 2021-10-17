package com.example.java_lab_3.data.datasets;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user_data")
@SuppressWarnings("UnusedDeclaration")
public class UserDataSet implements Serializable {
    private static final long serialVersionUID = -8706689714326132798L;

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "login", nullable = false)
    private String login;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "sessionId")
    private String sessionId;

    public UserDataSet() {}

    public UserDataSet(String id, String login, String password, String email) {
        this(id, login, password, email, null);
    }

    public UserDataSet(String id, String login, String password, String email, String sessionId) {
        this.setId(id);
        this.setLogin(login);
        this.setPassword(password);
        this.setEmail(email);
        this.setSessionId(sessionId);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public String toString() {
        return String.format("UserDataSet{%s, %s, ***, %s, %s}", id, login, email, sessionId);
    }
}
