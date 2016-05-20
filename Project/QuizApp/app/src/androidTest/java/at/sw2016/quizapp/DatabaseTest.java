package at.sw2016.quizapp;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import android.test.suitebuilder.annotation.LargeTest;

import java.io.IOException;
import java.io.InputStreamReader;

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
        questionDao.deleteQuestion(testQuestion);
    }

    @LargeTest
    public void testInsertCsv() {
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(getContext().getAssets().open("testCSV.csv"));
            questionDao.insertCSVFileIntoTable(inputStreamReader);
            Question question = questionDao.getQuestion(1);
            assertEquals(question.getQuestion(), "Wie alt ist unser Universum?");
            assertEquals(question.getCategory(), Category.HISTORY);
            assertFalse("Wie alt ist unser Universum nicht?".equals(question.getQuestion()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}