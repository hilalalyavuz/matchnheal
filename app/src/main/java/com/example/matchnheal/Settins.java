package com.example.matchnheal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class Settins extends AppCompatActivity implements View.OnClickListener{
    private ImageButton but1, but2, but3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settins);


        but1 = findViewById(R.id.button10);
        but1.setOnClickListener(this);

        but2 = findViewById(R.id.button4);
        but2.setOnClickListener(this);

        but3 = findViewById(R.id.button6);
        but3.setOnClickListener(this);



    }



    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.button10:
                Intent intent = new Intent(this,Anasayfa.class);
                intent.putExtra("hello1","1");
                startActivity(intent);
                break;
            case R.id.button4:

                Intent intent1 = new Intent(this,Anasayfa.class);
                intent1.putExtra("hello2","2");
                startActivity(intent1);
                break;
            case R.id.button6:

                Intent intent2 = new Intent(this,Anasayfa.class);
                intent2.putExtra("hello3","3");
                startActivity(intent2);
                break;

        }
    }
}