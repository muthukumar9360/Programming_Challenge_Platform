package com.coding.Programming_Platform.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name="userinfo")
public class Userinfo {

    private String firstname;
    private String lastname;
    private String phno;
    private String email;

    @Id
    private String username;
    private String password;
    @Column(name= "coding_medals")
    private int codingmedals;
    @Column(name = "last_login")
    private LocalDateTime lastlogin;
    @Column(name = "sign_up_time")
    private LocalDateTime signuptime = LocalDateTime.now();

    public Userinfo() {}

    public Userinfo(String firstname, String lastname, String phno, String email, String username, String password, int codingmedals, LocalDateTime lastlogin) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.phno = phno;
        this.email = email;
        this.username = username;
        this.password = password;
        this.codingmedals = codingmedals;
        this.lastlogin = lastlogin;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPhno() {
        return phno;
    }

    public void setPhno(String phno) {
        this.phno = phno;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getCodingmedals() {
        return codingmedals;
    }

    public void setCodingmedals(int codingmedals) {
        this.codingmedals = codingmedals;
    }

    public LocalDateTime getLastlogin() {
        return lastlogin;
    }

    public void setLastlogin(LocalDateTime lastlogin) {
        this.lastlogin = lastlogin;
    }

    public LocalDateTime getSignuptime() {
        return signuptime;
    }

    public void setSignuptime(LocalDateTime signuptime) {
        this.signuptime = signuptime;
    }

}
