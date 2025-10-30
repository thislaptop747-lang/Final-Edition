package com.tracker;

import java.time.LocalDate;

public class Expense {
    private Long id;
    private String project;
    private String category;
    private double amount;
    private String description;
    private LocalDate date;

    public Expense() {}

    public Expense(Long id, String project, String category, double amount, String description, LocalDate date) {
        this.id = id;
        this.project = project;
        this.category = category;
        this.amount = amount;
        this.description = description;
        this.date = date;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getProject() { return project; }
    public void setProject(String project) { this.project = project; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
}
