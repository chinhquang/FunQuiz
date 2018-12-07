package com.example.chinhtrinhquang.funquiz;

public class Candidate {
    public String id;
    public String username;
    public String password;
    public int score;
    public int cquiz;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getCquiz() {
        return cquiz;
    }

    public void setCquiz(int cquiz) {
        this.cquiz = cquiz;
    }



    Candidate() {}
    Candidate(String id, String uname, String pw, int score, int current)
    {
        this.id = id;
        this.username = uname;
        this.password = pw;
        this.score = score;
        this.cquiz = current;
    }


}
