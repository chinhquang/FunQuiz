package com.example.chinhtrinhquang.funquiz;

public class Question {

    String answer1;
    String answer2;
    String answer3;
    String answer4;
    int correct;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    int id;
    String question;

    public Question(String q, String ans1, String ans2, String ans3, String ans4, int key, int id) {
        this.answer1 = ans1;
        this.answer2 = ans2;
        this.answer3 = ans3;
        this.answer4 = ans4;
        this.correct = key;
        this.id = id;
        this.question = q;
    }

    public Question() {}

    public int getCorrect() {
        return correct;
    }

    public void setCorrect(int correct) {
        this.correct = correct;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }


    public String getAnswer1() {
        return answer1;
    }

    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    public String getAnswer2() {
        return answer2;
    }

    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }

    public String getAnswer3() {
        return answer3;
    }

    public void setAnswer3(String answer3) {
        this.answer3 = answer3;
    }

    public String getAnswer4() {
        return answer4;
    }

    public void setAnswer4(String answer4) {
        this.answer4 = answer4;
    }


    public String ToString() {
        return question + " " + correct + " " + id + " " + getAnswer1() + " " + getAnswer2();
    }

}
