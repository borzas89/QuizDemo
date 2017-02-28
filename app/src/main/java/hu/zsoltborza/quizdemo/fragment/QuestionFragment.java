package hu.zsoltborza.quizdemo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hu.zsoltborza.quizdemo.R;
import hu.zsoltborza.quizdemo.adapter.QuizRecyclerAdapter;

/**
 * Created by Borzas on 2017. 02. 23.
 *
 * Question fragment for displaying questions in a RecyclerView.
 *
 *
 */


public class QuestionFragment extends Fragment implements QuizRecyclerAdapter.RecyclerViewClickListener {


    static QuestionFragment questionRecyclerFragment;

    public QuestionFragment() {
    }

    public static QuestionFragment getInstance(){
        questionRecyclerFragment = new QuestionFragment();

        return questionRecyclerFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.quiz_activity,container,false);

        return rootView;
    }

    @Override
    public void recyclerViewListClicked(View v, int position) {

    }
}
