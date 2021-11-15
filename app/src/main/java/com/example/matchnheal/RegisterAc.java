package com.example.matchnheal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class RegisterAc extends AppCompatActivity  {
    private EditText mail,sif;
    private String tmail, tsif,tisim,toyisim,ttel;
    private FirebaseAuth mAuth;
    private FirebaseUser fuser;
    private static final String TAG = "RegisterAc";
    private EditText isim, soyisim, tel;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mail = (EditText)findViewById(R.id.etEmailAdd);
        sif = (EditText)findViewById(R.id.passforeg);

        isim = findViewById(R.id.firstnamereg);
        soyisim = findViewById(R.id.surnamereg);
        tel = findViewById(R.id.phonereg);


        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
    }

    public void kayitOl(View v){

        tmail = mail.getText().toString();
        tsif = sif.getText().toString();
        tisim = isim.getText().toString();
        toyisim = soyisim.getText().toString();
        ttel = tel.getText().toString();
        Map<String, Object> user = new HashMap<>();
        user.put("Name",tisim);
        user.put("Surname",toyisim);
        user.put("Phone",ttel);
        user.put("Email",tmail);

        db.collection("Users")
                .add(user)
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



        mAuth.createUserWithEmailAndPassword(tmail, tsif)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                            Intent intent = new Intent(RegisterAc.this, LoginAc.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterAc.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });


    }

    private void updateUI(FirebaseUser user) {

    }


}