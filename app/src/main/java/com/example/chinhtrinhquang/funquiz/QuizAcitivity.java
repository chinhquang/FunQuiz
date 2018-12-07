package com.example.chinhtrinhquang.funquiz;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
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
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.UUID;

public class QuizAcitivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private DatabaseReference mCandidate;
    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private Button mButton1;
    private Button mButton2;
    private Button mButton3;
    private Button mButton4;
    private Button mNextButton;
    private Button mPauseButton;

    public  int score;
    private int current;
    private boolean correct = false;

    private Question example = new Question("What's the joke","lake1","lake2","lake3","lake4",2,123);
    private List<TrueFalse> mQuestionBank = new ArrayList<TrueFalse>();


    private TextView mQuestionTextView;

    private int mCurrentIndex = 0;

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
        Toast.makeText(this,"username : "+SignInActivity.username + "\npassword :" + SignInActivity.password,Toast.LENGTH_LONG).show();
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_quiz);


        final ProgressDialog progress = new ProgressDialog(this);
        progress.setMessage("Loading data... ");
        progress.show();

        mQuestionBank.add(new TrueFalse(example, example.correct));

        mDatabase = FirebaseDatabase.getInstance().getReference("questions");
        mCandidate = FirebaseDatabase.getInstance().getReference("candidates");

        final String userID = UUID.randomUUID().toString();
        //writeNewCandidate(userID, SignInActivity.username, SignInActivity.password, 0, 0);

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

        updateQuestion();


        mButton1 = (Button) findViewById(R.id.no1);
        mButton1.setText(mQuestionBank.get(mCurrentIndex).getQuestionObj().answer1);
        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), R.string.correct_toast, Toast.LENGTH_SHORT).show();
                correct = checkAnswer(1);


            }
        });

        mButton2 = (Button) findViewById(R.id.no2);
        mButton2.setText(mQuestionBank.get(mCurrentIndex).getQuestionObj().answer2);
        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(QuizActivity.this, R.string.incorrect_toast, Toast.LENGTH_SHORT).show();
                correct = checkAnswer(2);

            }
        });

        mButton3 = (Button) findViewById(R.id.no3);
        mButton3.setText(mQuestionBank.get(mCurrentIndex).getQuestionObj().answer3);
        mButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(QuizActivity.this, R.string.incorrect_toast, Toast.LENGTH_SHORT).show();
                correct = checkAnswer(3);

            }
        });

        mButton4 = (Button) findViewById(R.id.no4);
        mButton4.setText(mQuestionBank.get(mCurrentIndex).getQuestionObj().answer4);
        mButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(QuizActivity.this, R.string.incorrect_toast, Toast.LENGTH_SHORT).show();
                correct = checkAnswer(4);

            }
        });

        mNextButton = (Button) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentIndex  < mQuestionBank.size()) {
                    mCurrentIndex = (mCurrentIndex + 1);
                    current = mCurrentIndex;

                    if (correct) {
                        score += 5;
                        Toast.makeText(getApplicationContext(), R.string.correct_toast, Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), R.string.incorrect_toast, Toast.LENGTH_SHORT).show();
                    }

                    if (mCurrentIndex < mQuestionBank.size()) { updateQuestion(); }
                } else  {
                    writeNewCandidate(userID, SignInActivity.username, SignInActivity.password, score, current);
                    //Intent intent = new Intent(getApplication(), Result.class);
                    //startActivity(intent);
                    String yourScore = "Your score is: " + score;
                    Toast.makeText(getApplicationContext(), yourScore, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplication(), OptionsActivity.class);
                    startActivity(intent);
                }
            }
        });

        mPauseButton = (Button) findViewById(R.id.pause);
        mPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeNewCandidate(userID, SignInActivity.username, SignInActivity.password, score, current);

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState)
    {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstantState");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
    }
}
