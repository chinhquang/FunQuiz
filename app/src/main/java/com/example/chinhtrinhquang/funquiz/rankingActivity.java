package com.example.chinhtrinhquang.funquiz;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class rankingActivity extends AppCompatActivity {
    private static final String TAG = "rankingActivity";
    //Android layout
    private ListView lvRank;
    private ArrayList<Rank> ranks = new ArrayList<>();
    private ArrayList<String> rankShower = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        lvRank = (ListView) findViewById(R.id.lvRank);
        final ProgressDialog progress = new ProgressDialog(this);
        progress.setMessage("Loading data... ");
        progress.show();
        DatabaseReference candidatesRef = FirebaseDatabase.getInstance().getReference("candidates");
        candidatesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                progress.dismiss();
                ranks.clear();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        try {
                            String username = ds.child("username").getValue(String.class);
                            int score = ds.child("score").getValue(int.class);
                            //String id, String uname, String pw, int score, int current
                            Rank newRank = new Rank(username, score, 0);
                            ranks.add(newRank);
                        } catch (Exception ex) {
                            Log.d(TAG, ex.getMessage());
                        }
                    }
                    Collections.sort(ranks);
                    Collections.sort(ranks, new Comparator<Rank>(){
                        public int compare(Rank obj1, Rank obj2) {

                            return Integer.valueOf(obj2.score).compareTo(Integer.valueOf(obj1.score)); // To compare integer values


                        }
                    });
                    int rankPoint = 1;
                    for(Rank rank : ranks){
                        rank.rankpoint = rankPoint;
                        rankPoint++;
                        rankShower.add(rank.rankpoint + ". " + rank.username + ": " + rank.score);
                    }
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(rankingActivity.this, android.R.layout.simple_list_item_1, rankShower);
                lvRank.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
