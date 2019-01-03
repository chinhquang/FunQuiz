package com.example.chinhtrinhquang.funquiz;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Rank implements Comparable<Rank> {
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
    @Override
    public int compareTo(Rank f) {

        if (this.score > f.score) {
            return 1;
        }
        else if (this.score <  score) {
            return -1;
        }
        else {
            return 0;
        }

    }

    @Override
    public String toString(){
        return this.username;
    }
}
