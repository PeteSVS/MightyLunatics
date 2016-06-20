package at.sw2016.quizapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import at.sw2016.quizapp.R;

public class HighscoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final TextView textView = (TextView) findViewById(R.id.scoreBoard);
        Intent intent = getIntent();
        int score = intent.getIntExtra("correctAnswersCounter", 0);
        textView.setText(Integer.toString(score));

        setContentView(R.layout.highscore);
    }


}


