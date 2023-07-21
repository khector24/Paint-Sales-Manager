package com.rainbowacehardware.paintsalescompetitionmanager.Objects;

public class PaintEmployee {

    private int id;
    private String name;
    private String phoneNumber;
    private String role;

    public PaintEmployee(int id, String name, String phoneNumber, String schedule) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.role = schedule;
    }

    public PaintEmployee(int id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
