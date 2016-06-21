package at.sw2016.quizapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import at.sw2016.quizapp.R;

public class MainActivity extends AppCompatActivity {

    public int getHighscore() {
        return highscore;
    }

    public void setHighscore(int highscore) {
        this.highscore = highscore;
    }

    private int highscore = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            System.exit(0);
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.menu);

    }

    public void backToMenu(View view)

    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void startTrainingActivity(View view)

    {
        Intent intent = new Intent(this, TrainingActivity.class);
        startActivity(intent);
    }

    public void startGameActivity(View view)

    {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    public void showHighscore(View view){

        Intent intent = new Intent(this, HighscoreActivity.class);
        startActivity(intent);

        //setContentView(R.layout.highscore);
    }
}
