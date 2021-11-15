package com.example.matchnheal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class Anasayfa extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG ="" ;
    private DrawerLayout drawer;
    private Button button1,button3;
    private FirebaseAuth mfire;
    private FirebaseFirestore db;
    private TextView t2, t3;
    private List<DocumentSnapshot> list;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);


        String v = getIntent().getStringExtra("hello1");
        String v1 = getIntent().getStringExtra("hello2");
        String v2 = getIntent().getStringExtra("hello3");

        mfire = FirebaseAuth.getInstance();

        FirebaseUser userz = mfire.getCurrentUser();
        String mail_str = userz.getEmail();

        db = FirebaseFirestore.getInstance();



        NavigationView navigationView = (NavigationView) findViewById(R.id.naview);
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.textView2);
        TextView navmail = (TextView) headerView.findViewById(R.id.textView5);
        ImageView im = (ImageView) headerView.findViewById(R.id.hilalz);
        im.setImageResource(R.drawable.butterfly);
        if(v!=null){
            im.setImageResource(R.drawable.butterfly);
            v = null;
        }else if(v1!=null){
            im.setImageResource(R.drawable.ball);
            v1 = null;
        }else if(v2!=null){
            im.setImageResource(R.drawable.fish);
            v2 = null;
        }
        

        db.collection("Users")
                .whereEqualTo("Email", mail_str)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                navUsername.setText(document.getString("Name"));
                                navmail.setText(document.getString("Email"));
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });



        button1 = findViewById(R.id.button2);
        button1.setOnClickListener(this);

        button3 = findViewById(R.id.button3);
        button3.setOnClickListener(this);

        Toolbar toolbar = findViewById(R.id.topAppBar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.dlayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        setNavigationViewListener();

    }



    private void setNavigationViewListener() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.naview);
        navigationView.setNavigationItemSelectedListener(this);
    }
    private void Logout(){
        mfire.signOut();
        finish();
        Intent intent = new Intent(Anasayfa.this,MainActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.game: {
                Intent intent = new Intent(this,QuestionActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.scores:{
                Intent intent = new Intent(this,MyScores.class);
                startActivity(intent);
                break;

            }
            case R.id.logout:{
               Logout();
                break;
            }
        }
        //close navigation drawer
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    @Override
    public void onClick(View v) {

                switch(v.getId()){
                    case R.id.button2:
                        Intent intent = new Intent(this,QuestionActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.button3:
                        Intent intent1 = new Intent(this,Settins.class);
                        startActivity(intent1);
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + v.getId());
                }



    }
}