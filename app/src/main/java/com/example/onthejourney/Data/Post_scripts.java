package com.example.onthejourney.Data;

public class Post_scripts {
    private String customer_id;
    private String buddy_id;
    private int grade;
    private String comments;

    public Post_scripts(String customer_id, String buddy_id, int grade, String comments) {
        this.customer_id = customer_id;
        this.buddy_id = buddy_id;
        this.grade = grade;
        this.comments = comments;
    }

    public String getCustomer_id() { return customer_id; }

    public String getBuddy_id() { return buddy_id; }

    public int getGrade() { return grade; }

    public String getComments() { return comments; }

    public void setCustomer_id(String customer_id) { this.customer_id = customer_id; }

    public void setBuddy_id(String buddy_id) { this.buddy_id = buddy_id; }

    public void setGrade(int grade) { this.grade = grade; }

    public void setComments(String comments) { this.comments = comments; }
}
