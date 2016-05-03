package at.sw2016.quizapp;

import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.TextView;

import com.robotium.solo.Solo;

import junit.framework.TestCase;

/**
 * Created by Stefan on 03.05.2016.
 */
public class QuizActivityTest extends ActivityInstrumentationTestCase2 {

    private Solo mySolo;

    public QuizActivityTest(Class activityClass) {
        super(activityClass);
    }

    public void setUp() throws Exception {
        super.setUp();

    }

    public void testQuiz() {

        mySolo.waitForActivity("QuizActivity");

        final TextView questionTextView = (TextView) mySolo.getCurrentActivity().findViewById(R.id.question_field);

        assertEquals(View.VISIBLE, questionTextView.getVisibility());
        assertEquals("What is the answer of life the universe and everything?", questionTextView.getText().toString());


    }

    public void tearDown() throws Exception {

    }
}