package at.sw2016.quizapp.application;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

import at.sw2016.quizapp.R;
import at.sw2016.quizapp.database.DatabaseHelper;
import at.sw2016.quizapp.database.QuestionDao;

/**
 * Created by Lukas on 20.05.2016.
 */
public class QuizApplication extends Application {

    public static final String FIRST_RUN = "first_run";
    public static final String[] FILENAMES = {"film.csv", "gaming.csv", "geo.csv", "geschichte.csv", "musik.csv", "sport.csv", "technik.csv", "wirtschaft.csv"};
    private DatabaseHelper databaseHelper;
    private QuestionDao questionDao;

    @Override
    public void onCreate() {
        super.onCreate();
        setUp();
    }

    protected void setUp() {
        setDatabaseHelper(new DatabaseHelper(getApplicationContext()));
        createQuestionDao();
        if(checkFirstOpen()){
            fillDatabase(FILENAMES);
        }
    }

    protected void createQuestionDao(){
        try {
            setQuestionDao(new QuestionDao(getApplicationContext()));
            getQuestionDao().open();
        } catch (SQLException e) {
            System.exit(-1);
        }
    }

    protected boolean checkFirstOpen(){
        SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(this);
        boolean firstRun = p.getBoolean(FIRST_RUN, true);
        p.edit().putBoolean(FIRST_RUN, false).apply();
        return firstRun;
    }

    protected void fillDatabase(String[] filenames){
        InputStreamReader inputStreamReader;
        try {
            for(String filename : filenames){
                inputStreamReader = new InputStreamReader(getApplicationContext().getAssets().open(filename));
                getQuestionDao().insertCSVFileIntoTable(inputStreamReader);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public DatabaseHelper getDatabaseHelper() {
        return databaseHelper;
    }

    public void setDatabaseHelper(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }

    public QuestionDao getQuestionDao() {
        return questionDao;
    }

    public void setQuestionDao(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }
}
