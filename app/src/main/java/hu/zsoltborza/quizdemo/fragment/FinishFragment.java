package hu.zsoltborza.quizdemo.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import hu.zsoltborza.quizdemo.R;
import hu.zsoltborza.quizdemo.activity.PagerActivity;

/**
 * Created by Borzas on 2017. 02. 20..
 */

public class FinishFragment  extends Fragment {


    private OnSettingsInteractionListener mListener;


    public static FinishFragment newInstance() {
        FinishFragment fragment = new FinishFragment();

        return fragment;
    }

    public FinishFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_finish, container, false);

        TextView scoreText = (TextView) v.findViewById(R.id.tvScore);


        scoreText.setText(String.valueOf(PagerActivity.getScores()));

        return v;

    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnSettingsInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public static Fragment getInstance() {
        return newInstance();
    }

    public interface OnSettingsInteractionListener {

        void onSaveScores();
    }


}
