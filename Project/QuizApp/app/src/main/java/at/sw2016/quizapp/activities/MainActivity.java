package at.sw2016.quizapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import at.sw2016.quizapp.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.menu);

    }

    public void startTrainingActivity(View view)

    {
        Intent intent = new Intent(this, TrainingActivity.class);
        startActivity(intent);
    }

    public void startGameActivity(View view)

    {
        //setContentView(R.layout.activity_main);
        Intent intent = new Intent(this, TrainingActivity.class);
        startActivity(intent);
    }

    public void showHighscore(View view)

    {
        setContentView(R.layout.highscore);
    }
}
