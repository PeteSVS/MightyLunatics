package at.sw2016.quizapp.database;

import android.provider.BaseColumns;

import at.sw2016.quizapp.model.Question;

/**
 * Created by Lukas on 05.05.2016.
 */
public class QuestionHelper {

    public static abstract class QuestionEntry implements BaseColumns {
        public static final String TABLE_NAME = "questions";
        public static final String COLUMN_NAME_ENTRY_ID = "id";
        public static final String COLUMN_NAME_QUESTION = "question";
        public static final String COLUMN_NAME_CORRECT_ANSWER = "answercorrect";
        public static final String COLUMN_NAME_ANSWER_2 = "answer2";
        public static final String COLUMN_NAME_ANSWER_3 = "answer3";
        public static final String COLUMN_NAME_ANSWER_4 = "answer4";
        public static final String COLUMN_NAME_CATEGORY = "category";
    }

    private static final String TEXT_TYPE = "TEXT";
    private static final String INTEGER_TYPE = "INTEGER";
    private static final String EMPTY_SPACE = " ";
    private static final String COMMA_SEP = ",";

    private static final String CREATE_QUESTION_ENTRY = "CREATE TABLE "
            + QuestionEntry.TABLE_NAME + "(" + QuestionEntry.COLUMN_NAME_ENTRY_ID
            + " INTEGER PRIMARY KEY,"
            + QuestionEntry.COLUMN_NAME_QUESTION + EMPTY_SPACE + TEXT_TYPE + COMMA_SEP
            + QuestionEntry.COLUMN_NAME_CORRECT_ANSWER + EMPTY_SPACE + TEXT_TYPE + COMMA_SEP
            + QuestionEntry.COLUMN_NAME_ANSWER_2 + EMPTY_SPACE + TEXT_TYPE + COMMA_SEP
            + QuestionEntry.COLUMN_NAME_ANSWER_3 + EMPTY_SPACE + TEXT_TYPE + COMMA_SEP
            + QuestionEntry.COLUMN_NAME_ANSWER_4 + EMPTY_SPACE + TEXT_TYPE + COMMA_SEP
            + QuestionEntry.COLUMN_NAME_CATEGORY + EMPTY_SPACE + INTEGER_TYPE + ")";

    private static final String DELETE_QUESTION_ENTRIES =
            "DROP TABLE IF EXISTS " + QuestionEntry.TABLE_NAME;

    private static final String DELETE_QUESTION_ENTRY = QuestionEntry.COLUMN_NAME_ENTRY_ID + " = ?";

    private static final String SELECT_QUESTION_BY_ID = "SELECT * FROM " + QuestionEntry.TABLE_NAME + " WHERE " + QuestionEntry.COLUMN_NAME_ENTRY_ID + " = ";

    public static String getCreateQuestionEntry() {
        return CREATE_QUESTION_ENTRY;
    }

    public static String getDeleteQuestionEntries(){
        return DELETE_QUESTION_ENTRIES;
    }

    public static String getDeleteQuestionEntry() {
        return DELETE_QUESTION_ENTRY ;
    }

    public static String getSelectQuestionById(long id) {
        return SELECT_QUESTION_BY_ID + id;
    }
}
