package at.sw2016.quizapp;

import android.graphics.drawable.ColorDrawable;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.robotium.solo.Solo;

import java.util.ArrayList;
import java.util.List;

import at.sw2016.quizapp.activities.TrainingActivity;
import at.sw2016.quizapp.model.Question;

/**
 * Created by Stefan on 03.05.2016.
 * Created by Lukas on 03.05.2016.
 */
public class TrainingTest extends ActivityInstrumentationTestCase2<TrainingActivity> {

    private Solo mySolo;

    public TrainingTest() throws Exception {
        super(TrainingActivity.class);
    }

    public void setUp() throws Exception {
        super.setUp();
        mySolo = new Solo(getInstrumentation(), getActivity());
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testLayout() {
        final TrainingActivity activity = getActivity();

        final TextView firstQuestion = (TextView) activity.findViewById(R.id.question_field);
        String firstAskedQuestion = firstQuestion.getText().toString();

        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                Button button = (Button) activity.findViewById(R.id.upper_right_button);
                button.performClick();
            }
        });
        try {
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            //do something
        }


        final TextView secondQuestion = (TextView) activity.findViewById(R.id.question_field);
        String secondAskedQuestion = secondQuestion.getText().toString();

        assertTrue(compareQuestions(firstAskedQuestion, secondAskedQuestion));
    }

    protected boolean compareQuestions(String first, String second){
        return (first == null || second == null) ? false : !first.equals(second);
    }

}