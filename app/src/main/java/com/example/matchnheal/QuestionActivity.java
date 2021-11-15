package com.example.matchnheal;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class QuestionActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView question, qCount;
    private Button option1, option2, option3, option4;
    private List<Question> questionList;
    private int quesNum;
    private int score;
    private FirebaseFirestore firebase;
    private ImageView imaz;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        score = 0;

        question = findViewById(R.id.question);
        qCount = findViewById(R.id.qnum);
        imaz = findViewById(R.id.imagez);

        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);

        option1.setOnClickListener(this);
        option2.setOnClickListener(this);
        option3.setOnClickListener(this);
        option4.setOnClickListener(this);

        firebase = FirebaseFirestore.getInstance();


        getQuestionsList();
    }

    private void getQuestionsList(){
        questionList = new ArrayList<>();


        firebase.collection("Quiz").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    QuerySnapshot questions = task.getResult();
                    for(QueryDocumentSnapshot doc: questions){
                        questionList.add(new Question(doc.getString("Question"),doc.getString("A"),
                                doc.getString("B"),doc.getString("C"),doc.getString("D"),Integer.valueOf(doc.getString("Answer"))));

                    }
                        setQuestion();


                }else{
                    Toast.makeText(QuestionActivity.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    private void setQuestion(){
        question.setText(questionList.get(0).getQuestion());
        option1.setText(questionList.get(0).getOptionA());
        option2.setText(questionList.get(0).getOptionB());
        option3.setText(questionList.get(0).getOptionC());
        option4.setText(questionList.get(0).getOptionD());

        qCount.setText(String.valueOf(1) + "/" + String.valueOf(questionList.size()));

        quesNum = 0;
        if(quesNum==0){
            imaz.setImageResource(R.drawable.b1);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {

        int selectedOption = 0;

        switch(v.getId()){
            case R.id.option1 :
                selectedOption = 1;
                break;
            case R.id.option2:
                selectedOption = 2;
                break;
            case R.id.option3:
                selectedOption = 3;
                break;
            case R.id.option4:
                selectedOption = 4;
                break;

            default:

        }
        checkAnswer(selectedOption,v);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void checkAnswer(int selectedOption, View view){

        if(selectedOption == questionList.get(quesNum).getCorrectAns()){
            //right ans
            ((Button)view).setBackgroundTintList(ColorStateList.valueOf(Color.rgb(219,255,132)));
            score++;

        }else{
            //wrong ans
            ((Button)view).setBackgroundTintList(ColorStateList.valueOf(Color.rgb(255, 178, 178)));

            switch(questionList.get(quesNum).getCorrectAns()){
                case 1:
                    option1.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(219,255,132)));
                    break;
                case 2:
                    option2.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(219,255,132)));
                    break;
                case 3:
                    option3.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(219,255,132)));
                    break;
                case 4:
                    option4.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(219,255,132)));
                    break;
            }
        }

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                changeQuestion();
            }
        }, 1000);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void changeQuestion(){


        if(quesNum < questionList.size()-1){
            quesNum++;
            switch(quesNum){
                case 1:
                    imaz.setImageResource(R.drawable.angry);
                    break;
                case 2:
                    imaz.setImageResource(R.drawable.shy);
                    break;
                case 3:
                    imaz.setImageResource(R.drawable.scared);
                    break;
                case 4:
                    imaz.setImageResource(R.drawable.sad);
                    break;
                case 5:
                    imaz.setImageResource(R.drawable.surprised);
                    break;
                case 6:
                    imaz.setImageResource(R.drawable.sleepy);
                    break;
                case 7:
                    imaz.setImageResource(R.drawable.love);
                    break;
                default:
                    imaz.setImageResource(R.drawable.b);

            }


            playAnim(question,0,0);
            playAnim(option1,0,1);
            playAnim(option2,0,2);
            playAnim(option3,0,3);
            playAnim(option4,0,4);

            qCount.setText(String.valueOf(quesNum+1) + "/" + String.valueOf(questionList.size()));




        }else{
            // Go to Score Activity
            Intent intent = new Intent(QuestionActivity.this, ScoreActivity.class);
            intent.putExtra("SCORE", String.valueOf(score) + "/" + String.valueOf(questionList.size()));
            startActivity(intent);
            QuestionActivity.this.finish();
        }
    }

    private void playAnim(View view, final int value, int viewNum){
        view.animate().alpha(value).scaleX(value).scaleY(value).setDuration(500)
                .setStartDelay(200).setInterpolator(new DecelerateInterpolator())
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if(value==0){
                            switch (viewNum){
                                case 0:
                                    ((TextView)view).setText(questionList.get(quesNum).getQuestion());
                                    break;
                                case 1:
                                    ((Button)view).setText(questionList.get(quesNum).getOptionA());
                                    break;
                                case 2:
                                    ((Button)view).setText(questionList.get(quesNum).getOptionB());
                                    break;
                                case 3:
                                    ((Button)view).setText(questionList.get(quesNum).getOptionC());
                                    break;
                                case 4:
                                    ((Button)view).setText(questionList.get(quesNum).getOptionD());
                                    break;

                            }
                            if(viewNum!=0){
                                ((Button)view).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#a2d9ff")));
                            }
                            playAnim(view,1,viewNum);

                        }


                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });

    }
}