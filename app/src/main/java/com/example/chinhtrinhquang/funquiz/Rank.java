package com.example.chinhtrinhquang.funquiz;

public class Rank {
    public String username;
    public int score;
    public int rankpoint;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getRankpoint() {
        return rankpoint;
    }

    public void setRankpoint(int rankpoint) {
        this.rankpoint = rankpoint;
    }

    Rank(){}

    Rank(String username, int score, int rankpoint)
    {
        this.username = username;
        this.score = score;
        this.rankpoint = rankpoint;
    }
}
