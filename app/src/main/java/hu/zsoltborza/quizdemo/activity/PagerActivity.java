package hu.zsoltborza.quizdemo.activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import hu.zsoltborza.quizdemo.R;
import hu.zsoltborza.quizdemo.adapter.SectionPagerAdapter;
import hu.zsoltborza.quizdemo.domain.Quiz;
import hu.zsoltborza.quizdemo.domain.QuizItem;
import hu.zsoltborza.quizdemo.fragment.FinishFragment;
import hu.zsoltborza.quizdemo.fragment.SectionFragment;
import hu.zsoltborza.quizdemo.utilities.LockableViewPager;
import hu.zsoltborza.quizdemo.utilities.Utils;

/**
 * Created by Borzas on 2017. 02. 20..
 */

public class PagerActivity extends AppCompatActivity implements
        View.OnClickListener, FinishFragment.OnSettingsInteractionListener,SectionFragment.OnSectionInteractionListener {


    @BindView(R.id.backButtonImage)
    ImageView backButton;

    @BindView(R.id.replayButton)
    ImageView replayButton;

    @BindView(R.id.timerButton)
    ImageView timerButton;

    @BindView(R.id.scoreText)
    TextView textViewScore;

    private int indexTab;
    Toolbar toolbar;
    private int currentId;

    List<QuizItem> quizList;

    TextView selectedTextView;

    private static final String PAGER_SCORE_TAG = "PAGER_SCORE_TAG";

    public static int mScore = 0;
    public static int mSectionCount = 30;
    boolean isClicked = false;

    public static int getScores() {
        return mScore;
    }

    /**
     * The {@link ViewPager} that will host the section contents.
     */
   LockableViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section);

        ButterKnife.bind(PagerActivity.this);

        //SettingsActivitynél...
        /*Intent intent = getIntent();
        if (intent.getExtras() != null) {

            questionCount = intent.getExtras().getString("SETTINGS");
        }

        Toast.makeText(this,questionCount,Toast.LENGTH_SHORT).show();*/


        replayButton.setVisibility(View.GONE);
        timerButton.setVisibility(View.GONE);

        mViewPager = (LockableViewPager) findViewById(R.id.viewpager);
        if (mViewPager != null) {
            setupViewPager(mViewPager);
        }

        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // When the given tab is selected, switch to the corresponding page in
                // the ViewPager.
                mViewPager.setCurrentItem(tab.getPosition());
                indexTab = mViewPager.getCurrentItem();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        // When swiping between different sections, select the corresponding
        // tab. We can also use TabLayout.setScrollPosition(#) to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.setScrollPosition(position, 0f, true);
            }

        });



       backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               mScore = 0;
               Utils.saveIntByTag(PagerActivity.this,PAGER_SCORE_TAG,mScore);
               onBackPressed();
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();

        int retrievedScore = Utils.retrieveIntByTag(this,PAGER_SCORE_TAG);
        updateToolBar(retrievedScore);


    }


    @Override
    protected void onPause() {
        super.onPause();





    }

    private void updateToolBar(int score) {
        textViewScore.setText(String.valueOf(score));
    }

    private void setupViewPager(LockableViewPager viewPager) {

        quizList = Utils.getQuizFromFile(viewPager.getContext());
        // prevent shuffle in test phase, comment the next line
       Utils.shuffleQuestionsList(quizList);
        SectionPagerAdapter mAdapter = new SectionPagerAdapter(getSupportFragmentManager(),mSectionCount,quizList);
        viewPager.setAdapter(mAdapter);
        viewPager.setSwipeable(false);
        //viewPager.setOffscreenPageLimit(10);
    }



    @Override
    public void onClick(View v) {
        // answerChecker(clickedAnswer, position, selectedTextView);
        String clickedAnswer = "";
        selectedTextView = (TextView) v.findViewById(v.getId());
        switch (v.getId()) {
            case R.id.tvAnswerA:
                clickedAnswer = selectedTextView.getText().toString();
                break;
            case R.id.tvAnswerB:
                clickedAnswer = selectedTextView.getText().toString();
                break;
            case R.id.tvAnswerC:
                clickedAnswer = selectedTextView.getText().toString();
                break;
            case R.id.tvAnswerD:
                clickedAnswer = selectedTextView.getText().toString();
                break;
            case R.id.tvAnswerE:
                clickedAnswer = selectedTextView.getText().toString();
                break; }


        indexTab = mViewPager.getCurrentItem();
        QuizItem quizItem = quizList.get(indexTab);
        String correctAnswer = quizItem.getAnswerArray().get(quizItem.getCorrectAnswerIndex());



            if(clickedAnswer.equals(correctAnswer)){
                selectedTextView.setBackgroundResource(R.drawable.correct_answer);;
                ++mScore;
                updateToolBar(mScore);
                Utils.saveIntByTag(PagerActivity.this,PAGER_SCORE_TAG,mScore);

            }else {
                selectedTextView.setBackgroundResource(R.drawable.wrong_answer);
            }



       // Toast.makeText(PagerActivity.this,clickedAnswer+"it: " + indexTab ,Toast.LENGTH_SHORT).show();


        moveNextSection();
    }

    private void moveNextSection() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
            }
        }, 1000);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //scoreFragment = 0;
    }

    @Override
    public void onSaveScores() {

    }

    @Override
    public void checker(View v) {

    }
}
