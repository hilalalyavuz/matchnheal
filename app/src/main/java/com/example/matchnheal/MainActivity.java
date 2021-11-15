package com.example.matchnheal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button letsButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        letsButton = (Button)findViewById(R.id.buttonfirst);
        letsButton.setOnClickListener(this);
    }
    private void onClicklets(){
        Intent intent = new Intent(this, LoginAc.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        onClicklets();
    }
}