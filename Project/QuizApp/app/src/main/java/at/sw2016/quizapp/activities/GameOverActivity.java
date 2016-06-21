package at.sw2016.quizapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import at.sw2016.quizapp.R;

public class GameOverActivity extends AppCompatActivity {

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game_over);
        final TextView textView = (TextView) findViewById(R.id.scoreBoard);
        Intent intent = getIntent();
        int score = intent.getIntExtra("correctAnswersCounter", 0);
        textView.setText(Integer.toString(score));
    }

    public void restartGame(View view)

    {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    public void backToMenu(View view)

    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
