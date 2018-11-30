package com.example.chinhtrinhquang.funquiz;


public class TrueFalse extends Object {


    private int mQuestion;
    private int mTrueQuestion;

    public TrueFalse(int question, int number) {
        mQuestion = question;
        mTrueQuestion = number;
    }

    public TrueFalse(String question, int key) {

    }

    public int getQuestion() {
        return mQuestion;
    }

    public void setQuestion(int question) {
        mQuestion = question;
    }

    public int isTrueQuestion() {
        return mTrueQuestion;
    }

    public void setTrueQuestion(int trueQuestion) {
        mTrueQuestion = trueQuestion;
    }
}
