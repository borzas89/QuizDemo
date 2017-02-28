package hu.zsoltborza.quizdemo.fragment;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import butterknife.BindView;
import butterknife.ButterKnife;
import hu.zsoltborza.quizdemo.R;
import hu.zsoltborza.quizdemo.domain.Quiz;
import hu.zsoltborza.quizdemo.domain.QuizItem;
import hu.zsoltborza.quizdemo.utilities.Utils;

/**
 * Created by Borzas on 2017. 02. 19..
 */

public class SectionFragment extends Fragment {


    private OnSectionInteractionListener mListener;

    @BindView(R.id.tvQestion) TextView tvQuestion;
    @BindView(R.id.tvAnswerA) TextView tvAnswerA;
    @BindView(R.id.tvAnswerB) TextView tvAnswerB;
    @BindView(R.id.tvAnswerC) TextView tvAnswerC;
    @BindView(R.id.tvAnswerD) TextView tvAnswerD;
    @BindView(R.id.tvAnswerE) TextView tvAnswerE;
    @BindView(R.id.imgCompound)
    ImageView compoundImage;

    /* The fragment argument representing the section number for this
            * fragment.
    */
    private static final String ARG_SECTION_NUMBER = "section_number";

    static SectionFragment fragment;

    public SectionFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static SectionFragment getInstance(int sectionNumber,QuizItem quizItem) {
        fragment = new SectionFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        args.putParcelable("quizItems",quizItem);
        fragment.setArguments(args);
        return fragment;
    }

    /*public Parcelable getParcel(){
        return getArguments().getParcelable("quizItems");
    }*/

    public QuizItem getQuizItem(){

        return getArguments().getParcelable("quizItems");
    }

    public Integer getSectionNumber() {
        return getArguments().getInt(ARG_SECTION_NUMBER);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnSectionInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnSectionInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_section, container, false);
        ButterKnife.bind(this,rootView);

        QuizItem quizItem = getQuizItem();

        if(quizItem.getId() == 35 || quizItem.getId() == 36
                || quizItem.getId() == 37
                || quizItem.getId() == 38
                || quizItem.getId() == 39
                || quizItem.getId() == 40
                || quizItem.getId() == 41)
        {

            compoundImage.setVisibility(View.VISIBLE);

        }




        List<String> answers = quizItem.getAnswerArray();
        String correctAnswer = answers.get(quizItem.getCorrectAnswerIndex());

        tvQuestion.setText(getSectionNumber().toString()+ ". kérdés " + quizItem.getQuestionText());
        tvAnswerA.setText(answers.get(0));
        tvAnswerB.setText(answers.get(1));
        tvAnswerC.setText(answers.get(2));
        tvAnswerD.setText(answers.get(3));
        tvAnswerE.setText(answers.get(4));



        /*tvQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((OnSectionInteractionListener)getActivity()).checker(v);
            }
        });*/


        return rootView;
    }

    public interface OnSectionInteractionListener {
        void checker(View v);
    }


}


