package com.example.chinhtrinhquang.funquiz;


public class TrueFalse extends Object {


    private Question mQuestion;
    private int mTrueQuestion;

    public TrueFalse(Question question, int number) {
        mQuestion = question;
        mTrueQuestion = number;
    }

    public TrueFalse(String question, int key) {

    }

    public String getQuestion() {
        return mQuestion.question;
    }

    public void setQuestion(Question question) {
        mQuestion = question;
    }

    public int isTrueQuestion() {
        return mTrueQuestion;
    }

    public void setTrueQuestion(int trueQuestion) {
        mTrueQuestion = trueQuestion;
    }
}
