package com.example.barclayspb7d.barclays_project.entities;


import javax.persistence.*;

@Entity
@Table(name = "LoanAccount")
public class LoanAccount {
    // Loan Account ID
    // Savings Account
    // Total Loan Amount
    // Interest Rate
    // Tenure
    // Status ( Approved / Ongoing / Closed).

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long loanAccID;

    @Column(nullable = false, unique = false, length = 12)
    private String savingAccId;

    @Column(nullable = false, unique = false)
    private Long totalLoanAount;

    @Column(nullable = false, unique = false)
    private Integer interestRate;

    @Column(nullable = false, unique = false)
    private Integer tenure;

    enum StatusEnum {
        APPROVED,
        ONGOING,
        CLOSED
    }

    @Column(nullable = false, unique = false)
    private StatusEnum status;
}
