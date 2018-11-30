package com.example.chinhtrinhquang.funquiz;

import android.app.Activity;
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
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class QuizAcitivity extends AppCompatActivity {

    private DatabaseReference mDatabase;

    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private Button mButton1;
    private Button mButton2;
    private Button mButton3;
    private Button mButton4;
    private Button mNextButton;

    private TrueFalse[] mQuestionBank = new TrueFalse[]{
            new TrueFalse(R.string.question_oceans, 1),
            new TrueFalse(R.string.question_mideast, 2),
            new TrueFalse(R.string.question_africa, 2),
            new TrueFalse(R.string.question_americas, 3),
            new TrueFalse(R.string.question_asias, 4),
    };

    private TextView mQuestionTextView;

    private List<Question> questions;

    private int mCurrentIndex = 0;

    private void updateQuestion() {
        int question = mQuestionBank[mCurrentIndex].getQuestion();
        mQuestionTextView.setText(question);
    }

    private void checkAnswer(int userPressedTrue) {
        int answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();

        int messageResId = 0;


        if (userPressedTrue == answerIsTrue) {
            messageResId = R.string.correct_toast;
        } else {
            messageResId = R.string.incorrect_toast;
        }


        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }

    public void retrieveDataFromFirebase(DatabaseReference mRef) {
        //final CountDownLatch done = new CountDownLatch(1);
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot qSnapshot : dataSnapshot.getChildren()) {
                    try {
                        Question question = qSnapshot.getValue(Question.class);
                        Log.d(TAG, question.ToString());
                        questions.add(question);
                        Log.d(TAG, "SIZE: " + questions.size());
                    }
                    catch (Exception ex) {
                        Log.d(TAG, ex.getMessage());
                    }
                }
                //done.countDown();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_quiz);

        questions = new ArrayList<>();

        mDatabase = FirebaseDatabase.getInstance().getReference("questions");

        retrieveDataFromFirebase(mDatabase);
        Log.d(TAG, "GETTED: " + questions.size());

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
        }

        updateQuestion();


        mButton1 = (Button) findViewById(R.id.no1);
        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), R.string.correct_toast, Toast.LENGTH_SHORT).show();
                checkAnswer(1);
            }
        });

        mButton2 = (Button) findViewById(R.id.no2);
        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(QuizActivity.this, R.string.incorrect_toast, Toast.LENGTH_SHORT).show();
                checkAnswer(2);
            }
        });

        mButton3 = (Button) findViewById(R.id.no3);
        mButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(QuizActivity.this, R.string.incorrect_toast, Toast.LENGTH_SHORT).show();
                checkAnswer(3);
            }
        });

        mButton4 = (Button) findViewById(R.id.no4);
        mButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(QuizActivity.this, R.string.incorrect_toast, Toast.LENGTH_SHORT).show();
                checkAnswer(4);
            }
        });

        mNextButton = (Button) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        Log.d(TAG, "ENDED: " + questions.size());
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState)
    {
        super.onSaveInstanceState(savedInstanceState);
        Log.d(TAG, "ENDED: " + questions.size());
        Log.i(TAG, "onSaveInstantState");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
    }
}
