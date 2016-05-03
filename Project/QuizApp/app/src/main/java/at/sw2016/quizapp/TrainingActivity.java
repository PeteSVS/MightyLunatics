package at.sw2016.quizapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class TrainingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_training);

        // globally
        TextView questionTextView = (TextView)findViewById(R.id.question_field);
        if (questionTextView != null) {
            questionTextView.setText(getString(R.string.question));
        }
//in your OnCreate() method

    }
}
