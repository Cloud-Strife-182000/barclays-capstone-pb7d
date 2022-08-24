package com.example.barclayspb7d.barclays_project.entities;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;

public class ScheduleEntry {
    
    @CsvBindByPosition(position = 0)
    private long month;

    @CsvBindByPosition(position = 1)
    private double emi;

    @CsvBindByPosition(position = 2) 
    private double principal;

    @CsvBindByPosition(position = 3) 
    private double interest;

    @CsvBindByPosition(position = 4) 
    private double balance;


    public ScheduleEntry() {
    }    

    public ScheduleEntry(long month, double emi, double principal, double interest, double balance) {
        this.month = month;
        this.emi = emi;
        this.principal = principal;
        this.interest = interest;
        this.balance = balance;
    }

    public long getMonth() {
        return this.month;
    }

    public void setMonth(long month) {
        this.month = month;
    }

    public double getEmi() {
        return this.emi;
    }

    public void setEmi(double emi) {
        this.emi = emi;
    }

    public double getPrincipal() {
        return this.principal;
    }

    public void setPrincipal(double principal) {
        this.principal = principal;
    }

    public double getInterest() {
        return this.interest;
    }

    public void setInterest(double interest) {
        this.interest = interest;
    }

    public double getBalance() {
        return this.balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

}
