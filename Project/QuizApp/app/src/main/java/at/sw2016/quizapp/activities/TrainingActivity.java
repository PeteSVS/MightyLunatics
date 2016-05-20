package at.sw2016.quizapp.activities;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import at.sw2016.quizapp.R;
import at.sw2016.quizapp.application.QuizApplication;
import at.sw2016.quizapp.model.Question;

public class TrainingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_training);

        final QuizApplication application = (QuizApplication) getApplicationContext();
        Question question = application.getQuestionDao().getRandomQuestion();

        // globally
        TextView questionTextView = (TextView)findViewById(R.id.question_field);
        if (questionTextView != null) {
            questionTextView.setText(question.getQuestion());
        }
        createButton(R.id.upper_left_button, question.getAnswer1());
        createButton(R.id.upper_right_button, question.getAnswer2());
        createButton(R.id.lower_left_button, question.getAnswer3());
        createButton(R.id.lower_right_button, question.getAnswer4());
    }

    public void createButton(int id, String text){
        Button button = (Button) findViewById(id);
        if(button != null) {
            button.setText(text);
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    changeColor((Button) v);
                }
            });
        }
    }

    public void changeColor(Button button){
        button.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.buttonFeedbackColor));
    }
}
