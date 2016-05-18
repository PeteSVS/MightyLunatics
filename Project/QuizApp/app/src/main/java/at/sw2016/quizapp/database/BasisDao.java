package at.sw2016.quizapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.io.FileNotFoundException;
import java.sql.SQLException;

/**
 * Created by Lukas on 06.05.2016.
 */
public class BasisDao {
    // Database fields
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    public BasisDao(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public SQLiteDatabase getDatabase() {
        return database;
    }

    public void setDatabase(SQLiteDatabase database) {
        this.database = database;
    }

    public DatabaseHelper getDbHelper() {
        return dbHelper;
    }

    public void setDbHelper(DatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }
}
