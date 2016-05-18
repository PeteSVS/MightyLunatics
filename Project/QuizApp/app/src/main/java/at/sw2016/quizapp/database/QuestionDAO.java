package at.sw2016.quizapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import at.sw2016.quizapp.model.Question;
import at.sw2016.quizapp.utils.AnswerNumber;
import at.sw2016.quizapp.utils.Category;

/**
 * Created by Lukas on 05.05.2016.
 */
public class QuestionDao extends BasisDao {

    public QuestionDao(Context context) throws SQLException {
        super(context);
    }

    public long addQuestion(Question question) {
        ContentValues values = new ContentValues();
        values.put(QuestionHelper.QuestionEntry.COLUMN_NAME_QUESTION, question.getQuestion());
        values = addAnswers(values, question);
        values.put(QuestionHelper.QuestionEntry.COLUMN_NAME_CATEGORY, getCategory(question.getCategory()));
        long id = getDatabase().insert(QuestionHelper.QuestionEntry.TABLE_NAME, null, values);
        question.setId(id);
        return id;
    }

    public void deleteQuestion(Question question) {
        SQLiteDatabase db = this.getDbHelper().getWritableDatabase();
        db.delete(QuestionHelper.QuestionEntry.TABLE_NAME, QuestionHelper.getDeleteQuestionEntry(), new String[] {String.valueOf(question.getId())});
    }

    public Question getRandomQuestion(){
        Question question = new Question();
        SQLiteDatabase db = this.getDbHelper().getReadableDatabase();
        String query = QuestionHelper.getSelectRandomQuestion();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null)
            cursor.moveToFirst();

        if (cursor == null) {
            return null;
        }
        question.setId(cursor.getLong(cursor.getColumnIndex(QuestionHelper.QuestionEntry.COLUMN_NAME_ENTRY_ID)));
        question.setQuestion(cursor.getString(cursor.getColumnIndex(QuestionHelper.QuestionEntry.COLUMN_NAME_QUESTION)));
        question.setCorrectAnswer(cursor.getString(cursor.getColumnIndex(QuestionHelper.QuestionEntry.COLUMN_NAME_CORRECT_ANSWER)));
        question = setAnswers(question, cursor);
        question = setCategory(question, cursor);

        return question;
    }

    public Question getQuestion(long questionId) {
        Question question = new Question();
        SQLiteDatabase db = this.getDbHelper().getReadableDatabase();
        String query = QuestionHelper.getSelectQuestionById(questionId);
        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null)
            cursor.moveToFirst();

        if (cursor == null) {
            return null;
        }
        question.setId(cursor.getLong(cursor.getColumnIndex(QuestionHelper.QuestionEntry.COLUMN_NAME_ENTRY_ID)));
        question.setQuestion(cursor.getString(cursor.getColumnIndex(QuestionHelper.QuestionEntry.COLUMN_NAME_QUESTION)));
        question.setCorrectAnswer(cursor.getString(cursor.getColumnIndex(QuestionHelper.QuestionEntry.COLUMN_NAME_CORRECT_ANSWER)));
        question = setAnswers(question, cursor);
        question = setCategory(question, cursor);

        return question;
    }

    public void insertCSVFileIntoTable(InputStreamReader fileReader) throws IOException {
        BufferedReader buffer = new BufferedReader(fileReader);
        String line;
        getDatabase().beginTransaction();
        while ((line = buffer.readLine()) != null) {
            ContentValues values = generateQuestionEntry(line);
            getDatabase().insert(QuestionHelper.QuestionEntry.TABLE_NAME, null, values);
        }
        getDatabase().setTransactionSuccessful();
        getDatabase().endTransaction();
    }

    protected ContentValues generateQuestionEntry(String string){
        String[] str = string.split(";");
        ContentValues values = new ContentValues();
        values.put(QuestionHelper.QuestionEntry.COLUMN_NAME_QUESTION, str[0]);
        values.put(QuestionHelper.QuestionEntry.COLUMN_NAME_CORRECT_ANSWER, str[1]);
        values.put(QuestionHelper.QuestionEntry.COLUMN_NAME_ANSWER_2, str[2]);
        values.put(QuestionHelper.QuestionEntry.COLUMN_NAME_ANSWER_3, str[3]);
        values.put(QuestionHelper.QuestionEntry.COLUMN_NAME_ANSWER_4, str[4]);
        values.put(QuestionHelper.QuestionEntry.COLUMN_NAME_CATEGORY, str[5]);
        return values;
    }

    protected ContentValues addAnswers(ContentValues values, Question question){

        if(question.getCorrectAnswer() == null) {
            return values;
        }
        values.put(QuestionHelper.QuestionEntry.COLUMN_NAME_CORRECT_ANSWER, question.getCorrectAnswer());
        values = addWrongAnswers(values, question);
        return values;
    }

    protected ContentValues addWrongAnswers(ContentValues values, Question question) {
        switch (findCorrectAnswer(question)){
            case ANSWER_1:
                values.put(QuestionHelper.QuestionEntry.COLUMN_NAME_ANSWER_2, question.getAnswer2());
                values.put(QuestionHelper.QuestionEntry.COLUMN_NAME_ANSWER_3, question.getAnswer3());
                values.put(QuestionHelper.QuestionEntry.COLUMN_NAME_ANSWER_4, question.getAnswer4());
                break;
            case ANSWER_2:
                values.put(QuestionHelper.QuestionEntry.COLUMN_NAME_ANSWER_2, question.getAnswer1());
                values.put(QuestionHelper.QuestionEntry.COLUMN_NAME_ANSWER_3, question.getAnswer3());
                values.put(QuestionHelper.QuestionEntry.COLUMN_NAME_ANSWER_4, question.getAnswer4());
                break;
            case ANSWER_3:
                values.put(QuestionHelper.QuestionEntry.COLUMN_NAME_ANSWER_2, question.getAnswer1());
                values.put(QuestionHelper.QuestionEntry.COLUMN_NAME_ANSWER_3, question.getAnswer2());
                values.put(QuestionHelper.QuestionEntry.COLUMN_NAME_ANSWER_4, question.getAnswer4());
                break;
            case ANSWER_4:
                values.put(QuestionHelper.QuestionEntry.COLUMN_NAME_ANSWER_2, question.getAnswer1());
                values.put(QuestionHelper.QuestionEntry.COLUMN_NAME_ANSWER_3, question.getAnswer2());
                values.put(QuestionHelper.QuestionEntry.COLUMN_NAME_ANSWER_4, question.getAnswer3());
                break;
            default:
                break;
        }
        return values;
    }
        protected AnswerNumber findCorrectAnswer(Question question){
        if(question.getCorrectAnswer().equals(question.getAnswer1())) {
            return AnswerNumber.ANSWER_1;
        }
        if(question.getCorrectAnswer().equals(question.getAnswer2())) {
            return AnswerNumber.ANSWER_2;
        }
        if(question.getCorrectAnswer().equals(question.getAnswer3())) {
            return AnswerNumber.ANSWER_3;
        }
        if(question.getCorrectAnswer().equals(question.getAnswer4())) {
            return AnswerNumber.ANSWER_4;
        }

        return AnswerNumber.NOT_SET;
    }

    protected Question setAnswers(Question question, Cursor cursor){
        question.setCorrectAnswer(cursor.getString(cursor.getColumnIndex(QuestionHelper.QuestionEntry.COLUMN_NAME_CORRECT_ANSWER)));

        List<AnswerNumber> answerOrder = new ArrayList<>();
        answerOrder.add(AnswerNumber.ANSWER_1);
        answerOrder.add(AnswerNumber.ANSWER_2);
        answerOrder.add(AnswerNumber.ANSWER_3);
        answerOrder.add(AnswerNumber.ANSWER_4);
        Collections.shuffle(answerOrder);

        for(int i = 0; i < answerOrder.size(); i++){
            question = setAnswer(question, cursor, answerOrder, i);
        }

        return question;
    }

    protected Question setAnswer(Question question, Cursor cursor,List<AnswerNumber> answerOrder, int i){
        switch(answerOrder.get(i)) {
            case ANSWER_1:
                question.setAnswer1(cursor.getString(cursor.getColumnIndex(getAnswerColumn(i))));
                break;
            case ANSWER_2:
                question.setAnswer2(cursor.getString(cursor.getColumnIndex(getAnswerColumn(i))));
                break;
            case ANSWER_3:
                question.setAnswer3(cursor.getString(cursor.getColumnIndex(getAnswerColumn(i))));
                break;
            case ANSWER_4:
                question.setAnswer4(cursor.getString(cursor.getColumnIndex(getAnswerColumn(i))));
                break;
            default:
                break;
        }

        return question;
    }

    protected String getAnswerColumn(int i){
        switch (i) {
            case 1:
                return QuestionHelper.QuestionEntry.COLUMN_NAME_CORRECT_ANSWER;
            case 2:
                return QuestionHelper.QuestionEntry.COLUMN_NAME_ANSWER_2;
            case 3:
                return QuestionHelper.QuestionEntry.COLUMN_NAME_ANSWER_3;
            case 4:
                return QuestionHelper.QuestionEntry.COLUMN_NAME_ANSWER_4;
            default:
                return QuestionHelper.QuestionEntry.COLUMN_NAME_CORRECT_ANSWER;
        }
    }

    protected Question setCategory(Question question, Cursor cursor){
        switch (cursor.getInt(cursor.getColumnIndex(QuestionHelper.QuestionEntry.COLUMN_NAME_CATEGORY))) {
            case 1:
                question.setCategory(Category.SPORT);
                break;
            case 2:
                question.setCategory(Category.MOVIE);
                break;
            case 3:
                question.setCategory(Category.MUSIC);
                break;
            case 4:
                question.setCategory(Category.GEOGRAPHY);
                break;
            case 5:
                question.setCategory(Category.HISTORY);
                break;
            case 6:
                question.setCategory(Category.TECHNOLOGY);
                break;
            case 7:
                question.setCategory(Category.BUSINESS);
                break;
            case 8:
                question.setCategory(Category.GAMEING);
                break;
            default:
                question.setCategory(Category.NOT_SET);
                break;
        }
        return question;
    }

    protected int getCategory(Category category){
        switch (category) {
            case SPORT:
                return 1;
            case MOVIE:
                return 2;
            case MUSIC:
                return 3;
            case GEOGRAPHY:
                return 4;
            case HISTORY:
                return 5;
            case TECHNOLOGY:
                return 6;
            case BUSINESS:
                return 7;
            case GAMEING:
                return 8;
            default:
                return 0;
        }
    }
}