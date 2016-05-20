package at.sw2016.quizapp.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

import at.sw2016.quizapp.R;
import at.sw2016.quizapp.database.DatabaseHelper;
import at.sw2016.quizapp.database.QuestionDao;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Intent intent = new Intent(this, TrainingActivity.class);
        startActivity(intent);
    }
}
