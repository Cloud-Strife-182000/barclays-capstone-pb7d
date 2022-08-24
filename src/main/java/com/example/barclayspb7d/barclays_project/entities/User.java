package com.example.barclayspb7d.barclays_project.entities;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long ID;

    @Column(nullable = false, unique = true, length = 255)
    private String mailID;

    @Column(nullable = false, unique = true, length = 255)
    private String password;

    @Column(nullable = false, unique = false, length = 255)
    private String Name;

    @Column(nullable = false, unique = false, length = 12)
    private String AccNo;

    @Column(nullable = false, unique = false)
    private Long currBal;

    public String getAccNo() {
        return this.AccNo;
    }

    public void setAccNo(String AccNo) {
        this.AccNo = AccNo;
    }

    public Long getCurrBal() {
        return this.currBal;
    }

    public void setCurrBal(Long currBal) {
        this.currBal = currBal;
    }

    public String getMailID() {
        return this.mailID;
    }

    public void setMailID(String mailID) {
        this.mailID = mailID;
    }

    public Long getID() {
        return this.ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
