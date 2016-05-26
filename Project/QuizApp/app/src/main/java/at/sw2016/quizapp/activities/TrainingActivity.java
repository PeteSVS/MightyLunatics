package at.sw2016.quizapp.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import at.sw2016.quizapp.R;
import at.sw2016.quizapp.application.QuizApplication;
import at.sw2016.quizapp.model.Question;

public class TrainingActivity extends AppCompatActivity {

    private QuizApplication quizApplication;
    private Question currentQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);
        quizApplication = (QuizApplication) getApplicationContext();
        createQuestion();
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
    }

    public void createButton(int id, String text){
        Button button = (Button) findViewById(id);
        if(button != null) {
            button.setText(text);
            button.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.buttonDefaultColor));
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    changeColor((Button) v);
                    validateAnswer((Button) v);
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
                createQuestion();
            }
        }, 2000);
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
