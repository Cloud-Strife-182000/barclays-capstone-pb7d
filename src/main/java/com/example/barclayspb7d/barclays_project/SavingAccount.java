package com.example.barclayspb7d.barclays_project;

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
  
}