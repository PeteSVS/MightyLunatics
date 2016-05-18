package at.sw2016.quizapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

import at.sw2016.quizapp.database.DatabaseHelper;
import at.sw2016.quizapp.database.QuestionDao;

public class MainActivity extends AppCompatActivity {

    public static final String FIRST_RUN = "first_run";
    public static final String[] FILENAMES = {"film.csv", "gaming.csv", "geo.csv", "geschichte.csv", "musik.csv", "sport.csv", "technik.csv", "wirtschaft.csv"};
    DatabaseHelper databaseHelper;
    QuestionDao questionDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUp();

        Intent intent = new Intent(this, TrainingActivity.class);
        startActivity(intent);
    }

    protected void setUp() {
        setContentView(R.layout.activity_main);
        databaseHelper = new DatabaseHelper(getApplicationContext());
        createQuestionDao();
        if(checkFirstOpen()){
            fillDatabase(FILENAMES);
        }
    }

    protected void createQuestionDao(){
        try {
            questionDao = new QuestionDao(getApplicationContext());
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
                questionDao.insertCSVFileIntoTable(inputStreamReader);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
