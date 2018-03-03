package hu.zsoltborza.quizdemo.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import hu.zsoltborza.quizdemo.R;
import hu.zsoltborza.quizdemo.activity.MainActivity;
import hu.zsoltborza.quizdemo.domain.QuizItem;

/**
 * Created by Borzas on 2017. 01. 22..
 */
public class QuizRecyclerAdapter extends RecyclerView.Adapter<QuizRecyclerAdapter.QuizViewHolder> {

    private final List<QuizItem> mQuizItemList;
    private Context mContext;
    private LayoutInflater inflater;
    private final TypedValue mTypedValue = new TypedValue();
    private RecyclerViewClickListener mItemListener;

    String clickedAnswer;
    public static int score;

    public static int getScore() {
        return score;
    }



    public QuizRecyclerAdapter(Context context, RecyclerViewClickListener itemListener, List<QuizItem> quizList) {
        context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
        inflater = LayoutInflater.from(context);
        mContext = context;
        mQuizItemList = quizList;
        this.mItemListener = itemListener;
    }

    public void setClickListener(RecyclerViewClickListener itemClickListener) {
        this.mItemListener = itemClickListener;
    }


    public void answerChecker(String clickedAnswerText, int position, TextView answer) {


        final QuizItem quizItem = mQuizItemList.get(position);
        List<String> answers = mQuizItemList.get(position).getAnswerArray();
        String correctAnswer = answers.get(quizItem.getCorrectAnswerIndex());

        // enable only one try to answer the question
     if(!quizItem.isClicked()) {
         if (correctAnswer.equals(clickedAnswerText)) {
             ++score;
            MainActivity.updateToolBar(QuizRecyclerAdapter.getScore());
             answer.setBackgroundResource(R.drawable.correct_answer);
         } else {
             answer.setBackgroundResource(R.drawable.wrong_answer);
         }
         quizItem.setClicked(true);
     }else{
         // show correct answer
         String correctAnswerText = answers.get(quizItem.getCorrectAnswerIndex());
         int correctNumber = quizItem.getCorrectAnswerIndex();

        // answer.setBackgroundResource(R.drawable.correct_answer);

         final Snackbar snackbar = Snackbar
                 .make(answer, "A helyes válasz a(z):  " + correctAnswerText, Snackbar.LENGTH_LONG)

                 .setAction("OK", new View.OnClickListener() {
             @Override
            public void onClick(View view) {

             }
         });
         View snackBarView = snackbar.getView();
         snackBarView.setBackgroundColor(snackBarView.getResources().getColor(R.color.primary));
         snackbar.show();
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

        Button answerTextA = (Button) itemView.findViewById(R.id.tvAnswerA);
        answerTextA.setTypeface(font);

        Button answerTextB = (Button) itemView.findViewById(R.id.tvAnswerB);
        answerTextB.setTypeface(font);


        Button answerTextC = (Button) itemView.findViewById(R.id.tvAnswerC);
        answerTextC.setTypeface(font);


        Button answerTextD = (Button) itemView.findViewById(R.id.tvAnswerD);
        answerTextD.setTypeface(font);

        Button answerTextE = (Button) itemView.findViewById(R.id.tvAnswerE);
        answerTextE.setTypeface(font);

        final QuizRecyclerAdapter.QuizViewHolder vh = new QuizViewHolder(itemView,
                new QuizRecyclerAdapter.RecyclerViewClickListener() {



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

        return vh;

    }


    @Override
    public void onBindViewHolder(final QuizViewHolder holder, int position) {

        final QuizItem quizItem = mQuizItemList.get(position);

        holder.Question.setText(quizItem.getQuestionText());

        List<String> answers = quizItem.getAnswerArray();

        // question numbers which has the compound picture
        if (quizItem.isCompound()) {
            holder.imgCompound.setVisibility(View.VISIBLE);
        } else {
            holder.imgCompound.setVisibility(View.GONE);
        }

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


    // To prevent items to be clicked another place too
    @Override
    public int getItemViewType(int position) {
        return position;
    }


    //ViewHolder class implement OnClickListener,
    //set clicklistener to itemView and,
    //send message back to Activity/Fragment
    public class QuizViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView Question;
        public Button AnswerA;
        public Button AnswerB;
        public Button AnswerC;
        public Button AnswerD;
        public Button AnswerE;
        public ImageView imgCompound;


        public QuizViewHolder(View view, RecyclerViewClickListener listener) {
            super(view);

            mItemListener = listener;

            Question = (TextView) view.findViewById(R.id.tvQestion);
            AnswerA = (Button) view.findViewById(R.id.tvAnswerA);
            AnswerB = (Button) view.findViewById(R.id.tvAnswerB);
            AnswerC = (Button) view.findViewById(R.id.tvAnswerC);
            AnswerD = (Button) view.findViewById(R.id.tvAnswerD);
            AnswerE = (Button) view.findViewById(R.id.tvAnswerE);
            imgCompound = (ImageView) view.findViewById(R.id.imgCompound);


            AnswerA.setOnClickListener(this);
            AnswerB.setOnClickListener(this);
            AnswerC.setOnClickListener(this);
            AnswerD.setOnClickListener(this);
            AnswerE.setOnClickListener(this);


        }



        @Override
        public void onClick(View v) {
            // Toast.makeText(mContext,getAdapterPosition(),Toast.LENGTH_SHORT).show();
            mItemListener.recyclerViewListClicked(v, this.getLayoutPosition());
        }

    }

    public static interface RecyclerViewClickListener {

        public void recyclerViewListClicked(View v, int position);
    }

}