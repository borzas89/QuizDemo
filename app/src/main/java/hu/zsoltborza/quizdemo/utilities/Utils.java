package hu.zsoltborza.quizdemo.utilities;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;

import com.google.gson.Gson;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import hu.zsoltborza.quizdemo.R;
import hu.zsoltborza.quizdemo.domain.Quiz;
import hu.zsoltborza.quizdemo.domain.QuizItem;

/**
 * Created by Borzas on 2017. 02. 20..
 */

public class Utils {

    /**
     * Reading questions from file, returning as a list.
     * @param context
     * @return List of questions
     */
    public static List<QuizItem> getQuizFromFile(Context context){

        Resources res = context.getResources();

        StringBuilder builder = new StringBuilder();
        InputStream is = res.openRawResource(R.raw.chemistrylist);
        Scanner scanner = new Scanner(is);

        while (scanner.hasNextLine()){
            builder.append(scanner.nextLine());
        }

        String file = (builder.toString());

        Gson gson = new Gson();

        Quiz quiz = gson.fromJson(file,Quiz.class);

        return quiz.getQuestionList();
    }

    public static List<QuizItem> shuffleQuestionsList(List<QuizItem> questionList){

        List<QuizItem> shuffledList = new ArrayList<>();

        Collections.shuffle(questionList);

       return shuffledList;
    }


    public static void saveIntByTag(Activity activity, String tag, int value){

        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(tag, value);
        editor.apply();
    }

    public static int retrieveIntByTag(Activity activity, String tag){
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        int retrieveValue = sharedPref.getInt(tag, -1);
        if(retrieveValue > -1){
            return retrieveValue;
        }else {
            return 0;
        }
    }


    public static void saveBooleanByTag(Activity activity, String tag, boolean value){

        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(tag, value);
        editor.apply();
    }

    public static boolean retrieveBooleanByTag(Activity activity, String tag){
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        boolean retrieveValue = sharedPref.getBoolean(tag, false);
        return retrieveValue;
    }

}
