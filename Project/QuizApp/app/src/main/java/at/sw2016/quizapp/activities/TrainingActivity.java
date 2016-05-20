package at.sw2016.quizapp.activities;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import at.sw2016.quizapp.R;

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

        createButton(R.id.upper_left_button, R.string.upperLeftButton);
        createButton(R.id.upper_right_button, R.string.upperRightButton);
        createButton(R.id.lower_left_button, R.string.lowerLeftButton);
        createButton(R.id.lower_right_button, R.string.lowerRightButton);
    }

    public void createButton(int id, int text){
        Button button = (Button) findViewById(id);
        if(button != null) {
            button.setText(getString(text));
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
