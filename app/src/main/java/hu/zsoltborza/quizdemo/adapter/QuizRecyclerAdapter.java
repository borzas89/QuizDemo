package hu.zsoltborza.quizdemo.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import hu.zsoltborza.quizdemo.R;
import hu.zsoltborza.quizdemo.domain.QuizItem;

/**
 * Created by Borzas on 2017. 01. 22..
 */
public class QuizRecyclerAdapter extends RecyclerView.Adapter<QuizRecyclerAdapter.QuizViewHolder> {

    private final List<QuizItem> mQuizItemList;
    private int rowLayout;
    private Context context;
    private LayoutInflater inflater;
    private final TypedValue mTypedValue = new TypedValue();
    private RecyclerViewClickListener mItemListener;

    String clickedAnswer;
    public static int score;

    public QuizRecyclerAdapter(Context context, int rowLayout, RecyclerViewClickListener itemListener, List<QuizItem> quizList) {
        context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
        inflater = LayoutInflater.from(context);
        this.rowLayout = rowLayout;
        mQuizItemList = quizList;
        this.mItemListener = itemListener;
    }

    public void setClickListener(RecyclerViewClickListener itemClickListener) {
        this.mItemListener = itemClickListener;
    }


    public void answerChecker(String clickedAnswerText, int position, TextView answer) {


        final QuizItem quizList = mQuizItemList.get(position);

        List<String> answers = mQuizItemList.get(position).getAnswerArray();
        String correctAnswer = answers.get(quizList.getCorrectAnswerIndex());

        if (correctAnswer.equals(clickedAnswerText)) {
            score++;
            answer.setBackgroundResource(R.drawable.correct_answer);
        } else {
            answer.setBackgroundResource(R.drawable.wrong_answer);
        }
    }

    @Override
    public QuizViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.quiz_list_row, parent, false);

        // Setting font
        final Typeface questionFont = Typeface.createFromAsset(itemView.getContext().getAssets(), "fonts/DejaVuSans-Bold.ttf");
        final Typeface font = Typeface.createFromAsset(itemView.getContext().getAssets(), "fonts/DejaVuSans.ttf");

        TextView questionText = (TextView) itemView.findViewById(R.id.tvQestion);
        questionText.setTypeface(questionFont);

        TextView answerTextA = (TextView) itemView.findViewById(R.id.tvAnswerA);
        answerTextA.setTypeface(font);

        TextView answerTextB = (TextView) itemView.findViewById(R.id.tvAnswerB);
        answerTextB.setTypeface(font);


        TextView answerTextC = (TextView) itemView.findViewById(R.id.tvAnswerC);
        answerTextC.setTypeface(font);


        TextView answerTextD = (TextView) itemView.findViewById(R.id.tvAnswerD);
        answerTextD.setTypeface(font);

        TextView answerTextE = (TextView) itemView.findViewById(R.id.tvAnswerE);
        answerTextE.setTypeface(font);


        final QuizRecyclerAdapter.QuizViewHolder vh = new QuizViewHolder(itemView, new QuizRecyclerAdapter.RecyclerViewClickListener() {
            public void recyclerViewListClicked(View v, int position) {

                TextView selectedTextView = (TextView) v.findViewById(v.getId());
                clickedAnswer = "";

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
                        break;


                }

                answerChecker(clickedAnswer, position, selectedTextView);

            }
        });


        return  vh;

        // QuizViewHolder holder = new QuizViewHolder(itemView,mItemListener);
        //return holder;

    }


    @Override
    public void onBindViewHolder(final QuizViewHolder holder, final int position) {


        final QuizItem quizList = mQuizItemList.get(position);

        holder.Question.setText(quizList.getQuestionText());

        List<String> answers = quizList.getAnswerArray();

        holder.AnswerA.setText(answers.get(0));
        holder.AnswerB.setText(answers.get(1));
        holder.AnswerC.setText(answers.get(2));
        holder.AnswerD.setText(answers.get(3));
        holder.AnswerE.setText(answers.get(4));


    }

    @Override
    public int getItemCount() {
        return mQuizItemList.size();
    }


    //ViewHolder class implement OnClickListener,
    //set clicklistener to itemView and,
    //send message back to Activity/Fragment
    public class QuizViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView Question;
        public TextView AnswerA;
        public TextView AnswerB;
        public TextView AnswerC;
        public TextView AnswerD;
        public TextView AnswerE;


        public QuizViewHolder(View view, RecyclerViewClickListener listener) {
            super(view);

            mItemListener = listener;

            Question = (TextView) view.findViewById(R.id.tvQestion);
            AnswerA = (TextView) view.findViewById(R.id.tvAnswerA);
            AnswerB = (TextView) view.findViewById(R.id.tvAnswerB);
            AnswerC = (TextView) view.findViewById(R.id.tvAnswerC);
            AnswerD = (TextView) view.findViewById(R.id.tvAnswerD);
            AnswerE = (TextView) view.findViewById(R.id.tvAnswerE);

            AnswerA.setOnClickListener(this);
            AnswerB.setOnClickListener(this);
            AnswerC.setOnClickListener(this);
            AnswerD.setOnClickListener(this);
            AnswerE.setOnClickListener(this);


        }


        @Override
        public void onClick(View v) {

            mItemListener.recyclerViewListClicked(v, this.getLayoutPosition());
        }

    }

    public static interface RecyclerViewClickListener {

        public void recyclerViewListClicked(View v, int position);
    }

}