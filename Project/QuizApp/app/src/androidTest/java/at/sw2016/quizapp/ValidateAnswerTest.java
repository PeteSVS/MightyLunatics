package at.sw2016.quizapp;

import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.content.ContextCompat;
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

//TODO: not possible to execute all at once
public class ValidateAnswerTest extends ActivityInstrumentationTestCase2<TrainingActivity> {

    private Solo mySolo;
    Question question;

    public ValidateAnswerTest() throws Exception {
        super(TrainingActivity.class);
    }

    public void setUp() throws Exception {
        super.setUp();
        mySolo = new Solo(getInstrumentation(), getActivity());
        question = getActivity().getCurrentQuestion();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    @UiThreadTest
    public void testCorrectButtonPressed() {
        int id = findValidAnswer();
        assertNotSame(id, -1);
        try {
            correctButtonTest(id);
        } catch (InterruptedException e){
            //nothing
        }
    }

    @UiThreadTest
    public void testWrongButtonPressed() {
        int id = findValidAnswer();
        int invalid = findFirstInvalidAnswer();
        assertNotSame(id, -1);
        assertNotSame(invalid, -1);
        try {
            wrongButtonTest(id, invalid);
        } catch (InterruptedException e){
            //nothing
        }
    }

    protected int findValidAnswer(){
        int retVal = checkIfValid(R.id.lower_left_button);
        if(retVal != 0){
            return retVal;
        }
        retVal = checkIfValid(R.id.lower_right_button);
        if(retVal != 0){
            return retVal;
        }

        retVal = checkIfValid(R.id.upper_left_button);
        if(retVal != 0){
            return retVal;
        }

        retVal = checkIfValid(R.id.upper_right_button);
        if(retVal != 0){
            return retVal;
        }

        return -1;
    }

    protected int findFirstInvalidAnswer(){
        int retVal = checkIfValid(R.id.lower_left_button);
        if(retVal == 0){
            return R.id.lower_left_button;
        }
        retVal = checkIfValid(R.id.lower_right_button);
        if(retVal == 0){
            return R.id.lower_right_button;
        }

        retVal = checkIfValid(R.id.upper_left_button);
        if(retVal == 0){
            return R.id.upper_left_button;
        }

        retVal = checkIfValid(R.id.upper_right_button);
        if(retVal == 0){
            return R.id.upper_right_button;
        }

        return -1;
    }


    protected int checkIfValid(int id){
        final Button button = (Button) mySolo.getCurrentActivity().findViewById(id);
        assertEquals(View.VISIBLE, button.getVisibility());
        String answerText = button.getText().toString();
        if(answerText.equals(question.getCorrectAnswer())){
            return id;
        }
        return 0;
    }

    public void correctButtonTest(int id) throws InterruptedException {
        final Button button = (Button) mySolo.getCurrentActivity().findViewById(id);
        button.performClick();
        ColorDrawable buttonColor = (ColorDrawable) button.getBackground();
        if( buttonColor != null){
            assertEquals(buttonColor.getColor(), getActivity().getResources().getColor(R.color.buttonFeedbackColor));
        }

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                ColorDrawable buttonColor = (ColorDrawable) button.getBackground();
                if( buttonColor != null){
                    assertEquals(buttonColor.getColor(), getActivity().getResources().getColor(R.color.buttonCorrectAnswer));
                }

            }
        }, 1000);
    }

    public void wrongButtonTest(int id, int invalid) throws InterruptedException {
        final Button wrongButton = (Button) mySolo.getCurrentActivity().findViewById(invalid);
        final Button correctButton = (Button) mySolo.getCurrentActivity().findViewById(id);
        wrongButton.performClick();
        ColorDrawable buttonColor = (ColorDrawable) wrongButton.getBackground();
        if( buttonColor != null){
            assertEquals(buttonColor.getColor(), getActivity().getResources().getColor(R.color.buttonFeedbackColor));
        }

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                ColorDrawable buttonColor = (ColorDrawable) wrongButton.getBackground();
                if( buttonColor != null){
                    assertEquals(buttonColor.getColor(), getActivity().getResources().getColor(R.color.buttonWrongAnswer));
                }
                buttonColor = (ColorDrawable) correctButton.getBackground();
                if( buttonColor != null){
                    assertEquals(buttonColor.getColor(), getActivity().getResources().getColor(R.color.buttonFeedbackColor));
                }

            }
        }, 1000);
    }
}