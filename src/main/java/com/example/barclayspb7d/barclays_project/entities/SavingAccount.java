package com.example.barclayspb7d.barclays_project.entities;

import javax.persistence.*;

@Entity
@Table(name = "SavingAccount")
public class SavingAccount {
    // Seq ID
    // Account Number
    // Name,
    // Email Address
    // Current Balance

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long SeqID;

    @Column(nullable = false, unique = false, length = 12)
    private String AccNo;

    @Column(nullable = false, unique = false, length = 255)
    private String Name;

    @Column(nullable = false, unique = false, length = 255)
    private String emailID;

    @Column(nullable = false, unique = false)
    private Long currBal;

    public Long getSeqID() {
        return this.SeqID;
    }

    public void setSeqID(Long SeqID) {
        this.SeqID = SeqID;
    }

    public String getAccNo() {
        return this.AccNo;
    }

    public void setAccNo(String AccNo) {
        this.AccNo = AccNo;
    }

    public String getName() {
        return this.Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getEmailID() {
        return this.emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    public Long getCurrBal() {
        return this.currBal;
    }

    public void setCurrBal(Long currBal) {
        this.currBal = currBal;
    }

  
}