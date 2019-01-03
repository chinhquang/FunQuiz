package com.example.chinhtrinhquang.funquiz;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.nfc.Tag;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.TextView;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.UUID;

public class QuizAcitivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private DatabaseReference mCandidate;

    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    static final String STATE_SCORE = "playerScore";
    static final String STATE_LEVEL = "playerLevel";
    static final String USER_NAME = "username";
    static final String USER_PASS = "password";
    static final String USER_ID = "id";

    private Button mButton1;
    private Button mButton2;
    private Button mButton3;
    private Button mButton4;
    private Button mNextButton;
    private Button mPauseButton;

    public  int score;
    private int current;
    private boolean correct = false;
    private String userID;

    private int count = 1;
    private Question example = new Question("What's the joke?","You love me","I love you","Choose me","Don't choose me",3,123);
    private List<TrueFalse> mQuestionBank = new ArrayList<TrueFalse>();


    private TextView mQuestionTextView;
    private TextView mQuestion;

    private int mCurrentIndex = 0;
    Bundle saved;
    int random = new Random().nextInt(5); // used for generate new test

    // set answer color
    boolean one = false;
    boolean two = false;
    boolean three = false;
    boolean four = false;

    private void updateQuestion() {
        String question = mQuestionBank.get(mCurrentIndex).getQuestion();
        mQuestionTextView.setText(question);
    }

    private boolean checkAnswer(int userPressedTrue) {
        int answerIsTrue = mQuestionBank.get(mCurrentIndex).isTrueQuestion();
        //int messageResId = 0;

        if (userPressedTrue == answerIsTrue) {
            //messageResId = R.string.correct_toast;
            return true;
        } else {
            //messageResId = R.string.incorrect_toast;
            return false;
        }

        //Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
        //return messageResId;
    }

    private void writeNewCandidate(String id, String username, String password, int score, int passed)
    {
        Candidate candidate = new Candidate(id, username, password, score, passed);
        mCandidate.child(id).setValue(candidate);
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Toast.makeText(this,"username : "+SignInActivity.username + "\npassword :" + SignInActivity.password,Toast.LENGTH_LONG).show();
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_quiz);


        final ProgressDialog progress = new ProgressDialog(this);
        progress.setMessage("Loading data... ");
        progress.show();

        mQuestionBank.add(new TrueFalse(example, example.correct));

        mDatabase = FirebaseDatabase.getInstance().getReference("questions");
        mCandidate = FirebaseDatabase.getInstance().getReference("candidates");

        userID = UUID.randomUUID().toString();
        //writeNewCandidate(userID, SignInActivity.username, SignInActivity.password, 0, 0);
        if (savedInstanceState != null) {saved = savedInstanceState; }

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                progress.dismiss();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot qSnapshot : dataSnapshot.getChildren()) {
                        try {
                            Question question = qSnapshot.getValue(Question.class);
                            Log.d(TAG, question.ToString());

                            mQuestionBank.add(new TrueFalse(question, question.correct));
                            Log.d(TAG, "SIZE: " + mQuestionBank.size());
                        } catch (Exception ex) {
                            Log.d(TAG, ex.getMessage());
                        }
                    }
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
        }


        mQuestion = (TextView) findViewById(R.id.nQuestion);
        mQuestion.setText("" + count);

        updateQuestion();


        mButton1 = (Button) findViewById(R.id.no1);
        mButton1.setText(mQuestionBank.get(mCurrentIndex).getQuestionObj().answer1);
        Log.d(TAG,"ANSWER1: " +  (mQuestionBank.get(mCurrentIndex).getQuestionObj().getAnswer1()));
        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), R.string.correct_toast, Toast.LENGTH_SHORT).show();
                one = true;
                mButton1.setBackgroundColor(Color.GRAY);
                correct = checkAnswer(1);

                if (two == true) {mButton2.setBackgroundColor(Color.DKGRAY); two = false;}
                if (three == true) {mButton3.setBackgroundColor(Color.DKGRAY); three = false;}
                if (four == true) {mButton4.setBackgroundColor(Color.DKGRAY); four = false;}

            }
        });

        mButton2 = (Button) findViewById(R.id.no2);
        mButton2.setText(mQuestionBank.get(mCurrentIndex).getQuestionObj().answer2);
        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(QuizActivity.this, R.string.incorrect_toast, Toast.LENGTH_SHORT).show();
                two = true;
                mButton2.setBackgroundColor(Color.GRAY);
                correct = checkAnswer(2);

                if (one == true) {mButton1.setBackgroundColor(Color.DKGRAY); one = false;}
                if (three == true) {mButton3.setBackgroundColor(Color.DKGRAY); three = false;}
                if (four == true) {mButton4.setBackgroundColor(Color.DKGRAY); four = false;}
            }
        });

        mButton3 = (Button) findViewById(R.id.no3);
        mButton3.setText(mQuestionBank.get(mCurrentIndex).getQuestionObj().answer3);
        mButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(QuizActivity.this, R.string.incorrect_toast, Toast.LENGTH_SHORT).show();
                three = true;
                mButton3.setBackgroundColor(Color.GRAY);
                correct = checkAnswer(3);

                if (two == true) {mButton2.setBackgroundColor(Color.DKGRAY); two = false;}
                if (one == true) {mButton1.setBackgroundColor(Color.DKGRAY); one = false;}
                if (four == true) {mButton4.setBackgroundColor(Color.DKGRAY); four = false;}
            }
        });

        mButton4 = (Button) findViewById(R.id.no4);
        mButton4.setText(mQuestionBank.get(mCurrentIndex).getQuestionObj().answer4);
        mButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(QuizActivity.this, R.string.incorrect_toast, Toast.LENGTH_SHORT).show();
                four = true;
                mButton4.setBackgroundColor(Color.GRAY);
                correct = checkAnswer(4);

                if (two == true) {mButton2.setBackgroundColor(Color.DKGRAY); two = false;}
                if (three == true) {mButton3.setBackgroundColor(Color.DKGRAY); three = false;}
                if (one == true) {mButton1.setBackgroundColor(Color.DKGRAY); one = false;}
            }
        });

        mNextButton = (Button) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // check the answer
                if (correct) {
                    score += 5;
                    Toast.makeText(getApplicationContext(), R.string.correct_toast, Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), R.string.incorrect_toast, Toast.LENGTH_SHORT).show();
                }

                // turn to next question
                if (count  <  20) {
                    mCurrentIndex = (mCurrentIndex + 1 + random) % mQuestionBank.size();
                    current = mCurrentIndex;
                    count++;


                    if (mCurrentIndex < mQuestionBank.size()) {
                        mQuestion.setText(""+count);
                        updateQuestion();
                        mButton1.setText(mQuestionBank.get(mCurrentIndex).getQuestionObj().getAnswer1());
                        mButton2.setText(mQuestionBank.get(mCurrentIndex).getQuestionObj().getAnswer2());
                        mButton3.setText(mQuestionBank.get(mCurrentIndex).getQuestionObj().getAnswer3());
                        mButton4.setText(mQuestionBank.get(mCurrentIndex).getQuestionObj().getAnswer4());
                    }
                } else  {
                    writeNewCandidate(userID, SignInActivity.username, SignInActivity.password, score, current);
                    //Intent intent = new Intent(getApplication(), Result.class);
                    //startActivity(intent);
                    String yourScore = "Your score is: " + score;
                    Toast.makeText(getApplicationContext(), yourScore, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplication(), OptionsActivity.class);
                    startActivity(intent);
                }


               mButton2.setBackgroundColor(Color.DKGRAY); two = false;
               mButton3.setBackgroundColor(Color.DKGRAY); three = false;
               mButton4.setBackgroundColor(Color.DKGRAY); four = false;
               mButton1.setBackgroundColor(Color.DKGRAY); one = false;
            }
        });

        mPauseButton = (Button) findViewById(R.id.pause);
        mPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //onSaveInstanceState(savedInstanceState);
                Intent startMain = new Intent(Intent.ACTION_MAIN);
                startMain.addCategory(Intent.CATEGORY_HOME);
                startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(startMain);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart called");
        Log.d(TAG, "SCORE saved: " + score);
        TackerActivity.activityStarted();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState)
    {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstantState");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);

        // Save the user's current game state
        savedInstanceState.putInt(STATE_SCORE, score);
        savedInstanceState.putInt(STATE_LEVEL, current);
        savedInstanceState.putString(USER_NAME, SignInActivity.username);
        savedInstanceState.putString(USER_PASS, SignInActivity.password);
        savedInstanceState.putString(USER_ID, userID);
    }

    @Override
    public  void onStop() {
        super.onStop();
        Log.d(TAG, "onStop called");
        TackerActivity.activityStopped();
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause called");
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i(TAG, "onRestoreInstantState");
    }
}

/*
    static final String STATE_SCORE = "playerScore";
    static final String STATE_LEVEL = "playerLevel";

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current game state
        savedInstanceState.putInt(STATE_SCORE, mCurrentScore);
        savedInstanceState.putInt(STATE_LEVEL, mCurrentLevel);

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    And when your activity is recreated, you can recover your state from the Bundle:

public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState);

        // Restore state members from saved instance
        mCurrentScore = savedInstanceState.getInt(STATE_SCORE);
        mCurrentLevel = savedInstanceState.getInt(STATE_LEVEL);
        }
*/