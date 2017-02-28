package hu.zsoltborza.quizdemo.domain;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zsoltborza on 2017. 01. 25..
 */

public class QuizItem implements Parcelable {

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

    public QuizItem(Integer id, String questionText, List<String> answerArray, Integer correctAnswerIndex, boolean clicked) {
        this.id = id;
        this.questionText = questionText;
        this.answerArray = answerArray;
        this.correctAnswerIndex = correctAnswerIndex;
        this.clicked = clicked;
    }

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

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(questionText);
        dest.writeList(answerArray);
        dest.writeInt(correctAnswerIndex);
        dest.writeByte((byte) (clicked ? 1 : 0));     //if clicked == true, byte == 1


    }

    public static final Creator<QuizItem> CREATOR = new Creator<QuizItem>() {
        @Override
        public QuizItem createFromParcel(Parcel in) {

            int redId = in.readInt();
            String redQuestionText = in.readString();
            String name = in.readString();
            List<String> redAnswers = in.createStringArrayList();
            int redCorrectIndex = in.readInt();
            boolean redClicked = in.readByte() !=0;// clicked == true if byte != 0

            return new QuizItem(redId,redQuestionText,redAnswers,redCorrectIndex,redClicked);
        }

        @Override
        public QuizItem[] newArray(int size) {
            return new QuizItem[size];
        }
    };



}
