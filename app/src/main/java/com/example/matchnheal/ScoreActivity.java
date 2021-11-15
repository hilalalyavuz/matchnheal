package com.example.matchnheal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ScoreActivity extends AppCompatActivity {

    private static final String TAG = "";
    private TextView score;
    private Button done;
    private FirebaseFirestore db;
    private FirebaseAuth mauth;
    private String mila;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        db = FirebaseFirestore.getInstance();
        mauth = FirebaseAuth.getInstance();
        mila = mauth.getCurrentUser().getEmail();


        score = findViewById(R.id.score);
        done = findViewById(R.id.buttonscore);

        String score_str = getIntent().getStringExtra("SCORE");
        score.setText(score_str);

        Map<String, Object> sc = new HashMap<>();
        sc.put("score",score_str);
        sc.put("usermail",mila);


        db.collection("Scores")
                .add(sc)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });

        done.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(ScoreActivity.this, Anasayfa.class);
                startActivity(intent);
            }
        });
    }
}