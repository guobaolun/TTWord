package com.snowy.ttword.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.snowy.ttword.dao.entity.Sentence;
import com.snowy.ttword.entity.ReciteResultData;
import com.snowy.ttword.entity.Word;
import com.snowy.ttword.entity.WordProgressData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class WordDao {
//    http://owsbeqedw.bkt.clouddn.com/audio/AudioResources.zip

    private WordHelper dbHelper;

    private static final String WORD_TABLE = "word";
    private static final String SENTENCE_TABLE = "sentence";


    private static final int WORD_COLUMNS_ID = 0;
    private static final int WORD_COLUMNS_WORD = 1;
    private static final int WORD_COLUMNS_CHINESE = 2;
    private static final int WORD_COLUMNS_US_PHONETIC = 3;
    private static final int WORD_COLUMNS_UK_PHONETIC = 4;


    private static final String[] WORD_COLUMNS = {"_id", "word", "chinese", "us_phonetic", "uk_phonetic"};
    private static final String[] SENTENCE_COLUMNS = {"_id", "en", "zh", "word"};


    private static final int SENTENCE_COLUMNS_ID = 0;
    private static final int SENTENCE_COLUMNS_EN = 1;
    private static final int SENTENCE_COLUMNS_ZH = 2;


    public WordDao(Context context) {
        dbHelper = new WordHelper(context);
        dbHelper.closeDatabase();
    }

    /**
     * 查询数据
     */
    public List<Word> queryPhoneticWordList(String regex) {
        SQLiteDatabase db = dbHelper.openDateBase();
        List<Word> wordList = new ArrayList<>();

        Cursor c = db.query(
                WORD_TABLE,
                WORD_COLUMNS,
                null,
                null,
                null,
                null,
                "RANDOM() limit 4000");


        Pattern pattern = Pattern.compile(regex);
        while (c.moveToNext() && wordList.size() < 4) {
            String id = c.getString(WORD_COLUMNS_ID);
            String wordStr = c.getString(WORD_COLUMNS_WORD);
            String chinese = c.getString(WORD_COLUMNS_CHINESE);
            String usPhonetic = c.getString(WORD_COLUMNS_US_PHONETIC);
            String ukPhonetic = c.getString(WORD_COLUMNS_UK_PHONETIC);

            Matcher matcher = pattern.matcher(ukPhonetic);

            if (matcher.find()) {
                Word word = new Word(id, wordStr, chinese, usPhonetic, ukPhonetic);
                wordList.add(word);
            }
        }
        c.close();
        db.close(); // 关闭数据库
        return wordList;
    }


    /**
     * 查询数据
     */
    public List<Sentence> querySentenceList(String word) {
        SQLiteDatabase db = dbHelper.openDateBase();
        List<Sentence> list = new ArrayList<>();

        Cursor c = db.query(
                SENTENCE_TABLE,
                SENTENCE_COLUMNS,
                "word=?",
                new String[] { word },
                null,
                null,
                null);


        while (c.moveToNext()) {
            String id = c.getString(SENTENCE_COLUMNS_ID);
            String en = c.getString(SENTENCE_COLUMNS_EN);
            String zh = c.getString(SENTENCE_COLUMNS_ZH);
            Sentence sentence = new Sentence(id, en, zh, word);
            list.add(sentence);
        }

        c.close();
        db.close(); // 关闭数据库
        return list;
    }


    /**
     * 查询数据
     */
    public ArrayList<ReciteResultData> querySelectWordList(ArrayList<WordProgressData> wordProgressList) {


        StringBuilder builder = new StringBuilder();
        Map<String, WordProgressData> progressMap = new HashMap<>();

        for (int i = 0; i < wordProgressList.size(); i++) {
            WordProgressData progressData = wordProgressList.get(i);
            builder.append("'");
            builder.append(progressData.getWord());
            builder.append("'");
            if (i != wordProgressList.size() - 1) {
                builder.append(",");
            }

            progressMap.put(progressData.getWord(), progressData);
        }

        SQLiteDatabase db = dbHelper.openDateBase();
        ArrayList<ReciteResultData> wordList = new ArrayList<>();


        Cursor c = db.query(
                WORD_TABLE,
                WORD_COLUMNS,
                "word in (" + builder.toString() + ");",
                null,
                null,
                null,
                null);


        while (c.moveToNext()) {
            String id = c.getString(WORD_COLUMNS_ID);
            String wordStr = c.getString(WORD_COLUMNS_WORD);
            String chinese = c.getString(WORD_COLUMNS_CHINESE);
            String usPhonetic = c.getString(WORD_COLUMNS_US_PHONETIC);
            String ukPhonetic = c.getString(WORD_COLUMNS_UK_PHONETIC);
            List<Sentence> sentence = querySentenceList(wordStr);
            ReciteResultData word = new ReciteResultData(id, wordStr, chinese, usPhonetic, ukPhonetic);
            word.setProgress(progressMap.get(wordStr).getProgress());
            word.setNewWord(progressMap.get(wordStr).isNewWord());
            word.setSentenceList(sentence);
            wordList.add(word);
        }
        c.close();
        db.close(); // 关闭数据库\
        return wordList;
    }


    /**
     * 查询数据
     */
    public Word queryWord(String wordStr) {


        SQLiteDatabase db = dbHelper.openDateBase();


        Cursor c = db.query(
                WORD_TABLE,
                WORD_COLUMNS,
                "word = ?",
                new String[]{wordStr},
                null,
                null,
                null);

        Word word = null;
        while (c.moveToNext()) {
            word = new Word();
            word.setId(c.getString(WORD_COLUMNS_ID));
            word.setWord(c.getString(WORD_COLUMNS_WORD));
            word.setChinese(c.getString(WORD_COLUMNS_CHINESE));
            word.setUsPhonetic(c.getString(WORD_COLUMNS_US_PHONETIC));
            word.setUkPhonetic(c.getString(WORD_COLUMNS_UK_PHONETIC));
        }
        c.close();
        db.close(); // 关闭数据库\
        return word;
    }

}
