package hu.zsoltborza.quizdemo.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by zsoltbora on 2017. 01. 25..
 */

public class Quiz {

    @SerializedName("questionList")
    @Expose
    private List<QuizItem> questionList = null;

    public List<QuizItem> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<QuizItem> questionList) {
        this.questionList = questionList;
    }

}