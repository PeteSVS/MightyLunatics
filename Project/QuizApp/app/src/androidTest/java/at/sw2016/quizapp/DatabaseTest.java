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

}