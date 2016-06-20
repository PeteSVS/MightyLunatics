package at.sw2016.quizapp;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.TextView;

import com.robotium.solo.Solo;

import at.sw2016.quizapp.activities.TrainingActivity;

/**
 * Created by Stefan on 20.06.2016.
 * Created by Manuela on 20.06.2016.
 */
public class GameTest extends ActivityInstrumentationTestCase2<TrainingActivity> {

    private Solo mySolo;

    public GameTest() throws Exception {
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