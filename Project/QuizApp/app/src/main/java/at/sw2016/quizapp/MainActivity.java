package at.sw2016.quizapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import at.sw2016.quizapp.database.DatabaseHelper;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(getApplicationContext());
        Intent intent = new Intent(this, TrainingActivity.class);
        startActivity(intent);
    }
}
