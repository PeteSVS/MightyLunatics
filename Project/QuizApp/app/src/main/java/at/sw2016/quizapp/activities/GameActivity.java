package at.sw2016.quizapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import at.sw2016.quizapp.R;
import at.sw2016.quizapp.application.QuizApplication;
import at.sw2016.quizapp.model.Question;

public class GameActivity extends AppCompatActivity {

    private QuizApplication quizApplication;
    private Question currentQuestion;
    private boolean alreadyClicked = false;

    private int correctAnswersCounter = 0;

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
        setContentView(R.layout.activity_game);
        quizApplication = (QuizApplication) getApplicationContext();
        createQuestion();

        Context context = getApplicationContext();
        CharSequence text = "You are now in Gaming Mode";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public void createQuestion() {
        currentQuestion = getQuizApplication().getQuestionDao().getRandomQuestion();

        // globally
        TextView questionTextView = (TextView)findViewById(R.id.question_field);
        if (questionTextView != null) {
            questionTextView.setText(getCurrentQuestion().getQuestion());
        }
        createButton(R.id.upper_left_button, getCurrentQuestion().getAnswer1());
        createButton(R.id.upper_right_button, getCurrentQuestion().getAnswer2());
        createButton(R.id.lower_left_button, getCurrentQuestion().getAnswer3());
        createButton(R.id.lower_right_button, getCurrentQuestion().getAnswer4());

        alreadyClicked = false;
    }

    public void createButton(int id, String text){
        Button button = (Button) findViewById(id);
        if(button != null) {
            button.setText(text);
            button.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.buttonDefaultColor));
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if(alreadyClicked == false){
                        alreadyClicked = true;
                        changeColor((Button) v);
                        validateAnswer((Button) v);
                    }
                }
            });
        }
    }

    public void changeColor(Button button){
        button.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.buttonFeedbackColor));
    }

    public void validateAnswer(final Button button){
        if( button.getText() == null)
            return;

        if(button.getText().equals(currentQuestion.getCorrectAnswer())){
            correctAnswersCounter++;
            styleCorrectChoosenButton(button);
        } else {
            Button correctButton = findCorrectButton(button);
            styleWrongChoosenButton(correctButton, button);
        }
    }

    protected void styleCorrectChoosenButton(final Button button){
        final Handler handler = new Handler();


        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                button.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.buttonCorrectAnswer));
                Toast.makeText(getApplicationContext(),"Correct answer number " + correctAnswersCounter,Toast.LENGTH_SHORT).show();
            }
        }, 1000);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                createQuestion();
            }
        }, 2000);
    }

    protected void styleWrongChoosenButton(final Button correctButton, final Button clickedButton){
        if(correctButton != null ){
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    clickedButton.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.buttonWrongAnswer));
                    correctButton.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.buttonCorrectAnswer));
                }
            }, 1000);
        }
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //createQuestion();
                //create game over activity here
                GameActivity.this.finish();
            }
        }, 3000);

        Intent intent = new Intent(this, GameOverActivity.class);
        intent.putExtra("correctAnswersCounter", correctAnswersCounter);
        startActivity(intent);

        Intent intent_high = new Intent(this, HighscoreActivity.class);
        intent.putExtra("correctAnswersCounter", correctAnswersCounter);
        startActivity(intent);

    }

    protected Button findCorrectButton(Button clickedButton){
        if(isCorrectButton(R.id.upper_left_button)){
            return (Button) findViewById(R.id.upper_left_button);
        }
        if(isCorrectButton(R.id.upper_right_button)){
            return (Button) findViewById(R.id.upper_right_button);
        }
        if(isCorrectButton(R.id.lower_left_button)){
            return (Button) findViewById(R.id.lower_left_button);
        }
        if(isCorrectButton(R.id.lower_right_button)){
            return (Button) findViewById(R.id.lower_right_button);
        }
        return null;
    }

    protected boolean isCorrectButton(int id){
        final Button correctButton = (Button) findViewById(id);
        if(correctButton != null && correctButton.getText() != null && correctButton.getText().equals(currentQuestion.getCorrectAnswer())){
            return true;
        }
        return false;
    }

    public QuizApplication getQuizApplication() {
        return quizApplication;
    }

    public Question getCurrentQuestion() {
        return currentQuestion;
    }

    public void setCurrentQuestion(Question currentQuestion) {
        this.currentQuestion = currentQuestion;
    }
}

