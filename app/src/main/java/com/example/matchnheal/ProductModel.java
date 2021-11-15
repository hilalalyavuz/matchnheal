package com.example.matchnheal;

public class ProductModel {
    String usermail;
    String score;

    private ProductModel(){

    }
    private ProductModel(String usermail, String score){
        this.score = score;
        this.usermail = usermail;
    }

    public String getUsermail() {
        return usermail;
    }

    public void setUsermail(String usermail) {
        this.usermail = usermail;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
