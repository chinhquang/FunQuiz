package com.example.chinhtrinhquang.funquiz;

import android.app.Activity;
import android.app.ProgressDialog;
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

public class QuizAcitivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private Button mButton1;
    private Button mButton2;
    private Button mButton3;
    private Button mButton4;
    private Button mNextButton;

    private Question example = new Question("What's the joke","lake1","lake2","lake3","lake4",2,123);
    private List<TrueFalse> mQuestionBank = new ArrayList<TrueFalse>();


    private TextView mQuestionTextView;

    private int mCurrentIndex = 0;

    private void updateQuestion() {
        String question = mQuestionBank.get(mCurrentIndex).getQuestion();
        mQuestionTextView.setText(question);
    }

    private void checkAnswer(int userPressedTrue) {
        int answerIsTrue = mQuestionBank.get(mCurrentIndex).isTrueQuestion();
        int messageResId = 0;

        if (userPressedTrue == answerIsTrue) {
            messageResId = R.string.correct_toast;
        } else {
            messageResId = R.string.incorrect_toast;
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_quiz);

        final ProgressDialog progress = new ProgressDialog(this);
        progress.setMessage("Loading data... ");
        progress.show();

        mQuestionBank.add(new TrueFalse(example, example.correct));

        mDatabase = FirebaseDatabase.getInstance().getReference("questions");
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
       /* mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                for (DataSnapshot qSnapshot : dataSnapshot.getChildren()) {
                    try {
                        Question question = qSnapshot.getValue(Question.class);
                        Log.d(TAG, question.ToString());
                        mQuestionBank.add(new TrueFalse(question, question.correct));
                        Log.d(TAG, "SIZE: " + mQuestionBank.size());
                    }
                    catch (Exception ex) {
                        Log.d(TAG, ex.getMessage());
                    }
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
        }

        updateQuestion();


        mButton1 = (Button) findViewById(R.id.no1);
        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), R.string.correct_toast, Toast.LENGTH_SHORT).show();
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
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.size();
                updateQuestion();
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
