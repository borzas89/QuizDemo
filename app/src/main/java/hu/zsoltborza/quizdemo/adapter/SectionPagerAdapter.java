package hu.zsoltborza.quizdemo.adapter;

/**
 * Created by Borzas on 2017. 02. 21..
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;
import java.util.Locale;

import hu.zsoltborza.quizdemo.domain.QuizItem;
import hu.zsoltborza.quizdemo.fragment.FinishFragment;
import hu.zsoltborza.quizdemo.fragment.SectionFragment;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionPagerAdapter extends FragmentPagerAdapter {
    private long item;
    private List<QuizItem> mList;
    private int mPageCount;

    public SectionPagerAdapter(FragmentManager fm, int pageCount, List<QuizItem> list) {
        super(fm);
        this.mPageCount = pageCount;
        this.mList = list;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        item = getItemId(position);

        if (position < mPageCount) {

            return SectionFragment.getInstance(position + 1, mList.get(position));

        } else {

            return FinishFragment.getInstance();

        }

    }

    @Override
    public int getCount() {
        return mPageCount + 1;

    }


    // title for sections...
    /*@Override
    public CharSequence getPageTitle(int position) {

        Locale l = Locale.getDefault();
        Integer pos = 0;
        if (position < mPageCount) {

            pos = position + 1;

            return ("" + pos).toUpperCase(l);

        }
        return "Finish";


    }*/
}
