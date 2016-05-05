package at.sw2016.quizapp;

import android.graphics.drawable.ColorDrawable;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.robotium.solo.Solo;

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

    public void testQuiz() {
        testButtons(R.id.lower_left_button, "42");
        testButtons(R.id.lower_right_button, "2.718");
        testButtons(R.id.upper_left_button, "3.141");
        testButtons(R.id.upper_right_button, "36");
    }

    public void testButtons(int id, String buttonText) {
        final Button button = (Button) mySolo.getCurrentActivity().findViewById(id);
        assertEquals(View.VISIBLE, button.getVisibility());
        assertEquals(buttonText , button.getText().toString());
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                button.performClick();
                ColorDrawable buttonColor = (ColorDrawable) button.getBackground();
                if( buttonColor != null){
                    assertEquals(buttonColor.getColor(), 0xFFB74D);
                }
            }
        });
    }

    public void testValidation(int id, final String buttonText) {

        final Button button = (Button) mySolo.getCurrentActivity().findViewById(id);
        getActivity().runOnUiThread(new Runnable() {

            @Override
            public void run() {
                mySolo.clickOnButton(0);
                ColorDrawable buttonColor = (ColorDrawable) button.getBackground();
                if (buttonColor != null) {
                    assertEquals(buttonColor.getColor(), 0xFFB74D);
                    buttonColor = (ColorDrawable) button.getBackground();

                    if (button.getText() == "42")
                        assertEquals(buttonColor.getColor(), 0x8BC34A);
                    else
                        assertEquals(buttonColor.getColor(), 0xF44336);
                }


            }
        });

    }
}