package com.rainbowacehardware.paintsalescompetitionmanager.Objects;

public class CurrentLeader {
    private int id;
    private String associate;
    private int quantitySold;
    private int ranking;
    private int transactionCount;

    public CurrentLeader(int id, String associate, int quantitySold, int ranking, int transactionCount) {
        this.id = id;
        this.associate = associate;
        this.quantitySold = quantitySold;
        this.ranking = ranking;
        this.transactionCount = transactionCount;
    }

    // Getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAssociate() {
        return associate;
    }

    public void setAssociate(String associate) {
        this.associate = associate;
    }

    public int getQuantitySold() {
        return quantitySold;
    }

    public void setQuantitySold(int quantitySold) {
        this.quantitySold = quantitySold;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public int getTransactionCount() {
        return transactionCount;
    }

    public void setTransactionCount(int transactionCount) {
        this.transactionCount = transactionCount;
    }
}

