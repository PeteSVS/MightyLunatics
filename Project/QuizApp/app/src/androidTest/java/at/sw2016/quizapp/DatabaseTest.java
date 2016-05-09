package at.sw2016.quizapp;

import android.database.sqlite.SQLiteDatabase;
import android.test.ActivityInstrumentationTestCase2;
import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.widget.TextView;

import com.robotium.solo.Solo;

import at.sw2016.quizapp.database.QuestionDao;
import at.sw2016.quizapp.model.Question;
import at.sw2016.quizapp.utils.Category;

/**
 * Created by Lukas on 05.05.2016.
 */
public class DatabaseTest extends AndroidTestCase {

    private QuestionDao questionDao;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        RenamingDelegatingContext context = new RenamingDelegatingContext(getContext(), "test_");
        questionDao = new QuestionDao(context);
        questionDao.open();
    }

    @Override
    protected void tearDown() throws Exception {
        questionDao.close();
        super.tearDown();
    }

    @LargeTest
    public void testDatabaseConnection() throws Exception {
        Question testQuestion = new Question();
        testQuestion.setQuestion("Welche Antwort ist richtig?");
        testQuestion.setAnswer1("1");
        testQuestion.setAnswer2("2");
        testQuestion.setAnswer3("3");
        testQuestion.setAnswer4("4");
        testQuestion.setCorrectAnswer("1");
        testQuestion.setCategory(Category.HISTORY);
        long id = questionDao.addQuestion(testQuestion);
        Question answerQuestion = questionDao.getQuestion(id);
        assertEquals(answerQuestion.getQuestion(), testQuestion.getQuestion());
    }

}