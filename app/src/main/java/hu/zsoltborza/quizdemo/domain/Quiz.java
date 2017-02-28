package hu.zsoltborza.quizdemo.domain;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zsoltbora on 2017. 01. 25..
 */

public class Quiz implements Parcelable{

    @SerializedName("questionList")
    @Expose
    private List<QuizItem> questionList = null;

    public List<QuizItem> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<QuizItem> questionList) {
        this.questionList = questionList;
    }

    public Quiz(List<QuizItem> questionList) {
        this.questionList = questionList;
    }

    public static final Creator<Quiz> CREATOR = new Creator<Quiz>() {
        @Override
        public Quiz createFromParcel(Parcel in) {
            String name = in.readString();
            ArrayList<QuizItem> quizItemsList = new ArrayList<>();
            in.readTypedList(quizItemsList, QuizItem.CREATOR);
            return new Quiz(quizItemsList);
        }

        @Override
        public Quiz[] newArray(int size) {
            return new Quiz[size];
        }
    };

    public List<QuizItem> getQuestionsArray() {
        return questionList.size() > 0 ? this.questionList : null;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(questionList);

    }
}