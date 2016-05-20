package at.sw2016.quizapp;

import android.graphics.drawable.ColorDrawable;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.view.View;
import android.widget.Button;

import com.robotium.solo.Solo;

import at.sw2016.quizapp.activities.TrainingActivity;

/**
 * Created by Stefan on 03.05.2016.
 * Created by Lukas on 03.05.2016.
 */
public class ShowAnswerButtonsTest extends ActivityInstrumentationTestCase2 {

    private Solo mySolo;

    public ShowAnswerButtonsTest() {
        super(TrainingActivity.class);
    }

    public void setUp() throws Exception {
        super.setUp();
        mySolo = new Solo(getInstrumentation(), getActivity());
    }

    @UiThreadTest
    public void testLayout() {
        buttonsTest(R.id.lower_left_button);
        buttonsTest(R.id.lower_right_button);
        buttonsTest(R.id.upper_left_button);
        buttonsTest(R.id.upper_right_button);
    }

    public void buttonsTest(int id) {
        final Button button = (Button) mySolo.getCurrentActivity().findViewById(id);
        assertEquals(View.VISIBLE, button.getVisibility());
        assertTrue(button.getText().toString().length() > 0);
                button.performClick();
                ColorDrawable buttonColor = (ColorDrawable) button.getBackground();
                /*if( buttonColor != null){
                    assertEquals(buttonColor.getColor(), 0xFFB74D);
                }*/

    }
}