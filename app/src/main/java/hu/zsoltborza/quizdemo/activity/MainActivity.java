package hu.zsoltborza.quizdemo.activity;

import android.content.Intent;
import android.os.CountDownTimer;
import android.provider.ContactsContract;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import hu.zsoltborza.quizdemo.R;
import hu.zsoltborza.quizdemo.adapter.QuizRecyclerAdapter;
import hu.zsoltborza.quizdemo.domain.QuizItem;
import hu.zsoltborza.quizdemo.utilities.CustomSnapHelper;
import hu.zsoltborza.quizdemo.utilities.Utils;

public class MainActivity extends AppCompatActivity implements QuizRecyclerAdapter.RecyclerViewClickListener{

    @BindView(R.id.pratice_toolbar) Toolbar toolbar;
    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.examButton)
    ImageView examButton;
    @BindView(R.id.replayButton)
    ImageView replayButton;
    @BindView(R.id.time_text_view)
    TextView timerText;

    static  TextView scoreTextView;
    @BindView(R.id.timerButton)
    ImageView buttonTimer;

    private List<QuizItem> quizList = new ArrayList<>();

    private QuizRecyclerAdapter mAdapter;

    private CountDownTimer countDownTimer; // built in android class
    private long totalTimeCountInMilliseconds; // total count down time in milliseconds
    private long timeBlinkInMilliseconds; // start time of start blinking
    private boolean blink; // controls the blinking .. on and off

    private boolean isTimerClicked = false;


    // R.drawable.stop_white
    public final int [] mNextTimerIcon = {R.drawable.timer_white_48dp,R.drawable.timer_off_white_48dp};
    int mSelectedTimer;

    // 3...
    public int getNextTimer(){
        return  ( mSelectedTimer + 1 ) % 2;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(MainActivity.this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        setupRecyclerView();

        scoreTextView = (TextView) findViewById(R.id.scoreText);

        examButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent;
                mainIntent = new Intent(MainActivity.this
                        , PagerActivity.class);
                startActivity(mainIntent);
            }
        });

        replayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shuffleAll();
            }
        });


        buttonTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mSelectedTimer == 0){
                    setTimer(1);
                    startTimer();
                }else{
                    countDownTimer.cancel();
                }
                mSelectedTimer = getNextTimer();
                buttonTimer.setImageResource(mNextTimerIcon[mSelectedTimer]);
            }
        });
    }

    public void setupRecyclerView(){

        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(MainActivity.this);
        mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(mLinearLayoutManagerVertical);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        CustomSnapHelper customSnapHelper = new CustomSnapHelper(Gravity.TOP);
        customSnapHelper.attachToRecyclerView(recyclerView);

        shuffleAll();


    }

    public static void updateToolBar(int score) {

        scoreTextView.setText(String.valueOf(score));
    }


    public void shuffleAll(){


        quizList = Utils.getQuizFromFile(MainActivity.this);

        Collections.shuffle(quizList);

        mAdapter = new QuizRecyclerAdapter(MainActivity.this,
                this,quizList);

        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        QuizRecyclerAdapter.score = 0;


    }


    @Override
    protected void onPause() {
        super.onPause();



    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    public void recyclerViewListClicked(View v, int position) {

    }

    private void setTimer(int timer) {
        int time = 0;
        if (timer !=0) {
            time = timer;
        }

        totalTimeCountInMilliseconds = 60 * time * 1000;

        timeBlinkInMilliseconds = 30 * 1000;


    }

    private void startTimer() {

        countDownTimer = new CountDownTimer(totalTimeCountInMilliseconds, 500) {
            // 500 means, onTick function will be called at every 500
            // milliseconds

            @Override
            public void onTick(long leftTimeInMilliseconds) {
                long seconds = leftTimeInMilliseconds / 1000;

                if (leftTimeInMilliseconds < timeBlinkInMilliseconds) {
                  //  textViewShowTime.setTextAppearance(getApplicationContext(),
                           // R.style.blinkText);
                    // change the style of the textview .. giving a red
                    // alert style

                    if (blink) {
                       // textViewShowTime.setVisibility(View.VISIBLE);
                        // if blink is true, textview will be visible
                    } else {
                       // textViewShowTime.setVisibility(View.INVISIBLE);
                    }

                    blink = !blink; // toggle the value of blink
                }

                timerText.setText(String.format("%02d", seconds / 60)
                        + ":" + String.format("%02d", seconds % 60));
                // format the textview to show the easily readable format

            }

            @Override
            public void onFinish() {
                // this function will be called when the timecount is finished

            }

        }.start();

    }

}
