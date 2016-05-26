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
public class ShowQuestionAndAnswersTest extends ActivityInstrumentationTestCase2<TrainingActivity> {

    private Solo mySolo;
    private List<String> alreadyCheckedAnswers;
    Question question;

    public ShowQuestionAndAnswersTest() throws Exception {
        super(TrainingActivity.class);
        alreadyCheckedAnswers = new ArrayList<>();
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
    public void testLayout() {
        questionTest(R.id.question_field);
        buttonsTest(R.id.lower_left_button);
        buttonsTest(R.id.lower_right_button);
        buttonsTest(R.id.upper_left_button);
        buttonsTest(R.id.upper_right_button);
    }

    public void questionTest(int id) {
        final TextView questionTextView = (TextView) mySolo.getCurrentActivity().findViewById(id);
        assertEquals(View.VISIBLE, questionTextView.getVisibility());
        String askedQuestion = questionTextView.getText().toString();
        assertTrue(askedQuestion.length() > 0);
        assertEquals(question.getQuestion(), askedQuestion);
    }

    public void buttonsTest(int id) {
        final Button button = (Button) mySolo.getCurrentActivity().findViewById(id);
        assertEquals(View.VISIBLE, button.getVisibility());
        assertTrue(containsValidAnswerText(button.getText().toString()));
        button.performClick();
        ColorDrawable buttonColor = (ColorDrawable) button.getBackground();
        if( buttonColor != null){
            assertEquals(buttonColor.getColor(), getActivity().getResources().getColor(R.color.buttonFeedbackColor));
        }
    }

    protected boolean containsValidAnswerText(final String buttonText){
        if(buttonText == null || buttonText.length() < 0){
            return false;
        }
        if (buttonText.equals(question.getAnswer1())) {
            return (checkDoubleAnswers(question.getAnswer1()));
        } else if (buttonText.equals(question.getAnswer2())) {
            return (checkDoubleAnswers(question.getAnswer2()));
        } else if (buttonText.equals(question.getAnswer3())) {
            return (checkDoubleAnswers(question.getAnswer3()));
        } else if (buttonText.equals(question.getAnswer4())) {
            return (checkDoubleAnswers(question.getAnswer4()));
        }
        return false;
    }

    protected boolean checkDoubleAnswers(String answer){
        if(alreadyCheckedAnswers.contains(answer)){
            return false;
        }
        alreadyCheckedAnswers.add(answer);
        return true;
    }
}