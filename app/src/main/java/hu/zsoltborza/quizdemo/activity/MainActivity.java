package hu.zsoltborza.quizdemo.activity;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import hu.zsoltborza.quizdemo.R;
import hu.zsoltborza.quizdemo.adapter.QuizRecyclerAdapter;
import hu.zsoltborza.quizdemo.domain.Quiz;
import hu.zsoltborza.quizdemo.domain.QuizItem;

public class MainActivity extends AppCompatActivity implements QuizRecyclerAdapter.RecyclerViewClickListener{

    private List<QuizItem> quizList = new ArrayList<>();
    private RecyclerView recyclerView;
    private QuizRecyclerAdapter mAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setupRecyclerView();
    }

    public void setupRecyclerView(){

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(MainActivity.this);
        mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(mLinearLayoutManagerVertical);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        quizList = getQuizFromFile();

        mAdapter = new QuizRecyclerAdapter(MainActivity.this,
                R.layout.quiz_list_row,this,quizList);

        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

    }



    public List<QuizItem> getQuizFromFile(){

        Resources res = getResources();

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

    public void shuffleAll(){


        quizList = getQuizFromFile();

        Collections.shuffle(quizList);
        mAdapter = new QuizRecyclerAdapter(MainActivity.this,
                R.layout.quiz_list_row,this,quizList);

        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        QuizRecyclerAdapter.score = 0;


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case R.id.shuffle:
                shuffleAll();
                break;

            case R.id.score:
                int score = QuizRecyclerAdapter.score;
                Toast.makeText(MainActivity.this,"Pontszám: " + score,Toast.LENGTH_SHORT).show();
                break;


        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void recyclerViewListClicked(View v, int position) {



    }
}
