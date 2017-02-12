package hu.zsoltborza.quizdemo.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by zsoltborza on 2017. 01. 25..
 */

public class QuizItem {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("questionText")
    @Expose
    private String questionText;
    @SerializedName("answerArray")
    @Expose
    private List<String> answerArray = null;
    @SerializedName("correctAnswerIndex")
    @Expose
    private Integer correctAnswerIndex;

    boolean clicked;

    public boolean isClicked() {
        return clicked;
    }

    public void setClicked(boolean clicked) {
        this.clicked = clicked;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public List<String> getAnswerArray() {
        return answerArray;
    }

    public void setAnswerArray(List<String> answerArray) {
        this.answerArray = answerArray;
    }

    public Integer getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }

    public void setCorrectAnswerIndex(Integer correctAnswerIndex) {
        this.correctAnswerIndex = correctAnswerIndex;
    }

}
