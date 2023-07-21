package com.rainbowacehardware.paintsalescompetitionmanager.Objects;

import java.time.LocalDate;

public class PaintSale {
    private int id;
    private String receipt;
    private LocalDate date;
    private String employee;
    private String quantity;

    public PaintSale(int id, String receipt, LocalDate date, String employee, String quantity) {
        this.id = id;
        this.receipt = receipt;
        this.date = date;
        this.employee = employee;
        this.quantity = quantity;
    }

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getReceipt() {
        return receipt;
    }

    public void setReceipt(String receipt) {
        this.receipt = receipt;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}

