package hu.zsoltborza.quizdemo.utilities;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;


/**
 * Created by Borzas on 2017. 01. 13..
 */

public class CustomSnapHelper extends LinearSnapHelper {

    private OrientationHelper mVerticalHelper;

    private int mGravity;
    private boolean mIsRtlHorizontal;
    private boolean mSnapLastItemEnabled;
    SnapListener mSnapListener;
    boolean mSnapping;

    private RecyclerView.OnScrollListener mScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_SETTLING) {
                mSnapping = false;
            }
            if (newState == RecyclerView.SCROLL_STATE_IDLE && mSnapping && mSnapListener != null) {
                int position = getSnappedPosition(recyclerView);
                if (position != RecyclerView.NO_POSITION) {
                    mSnapListener.onSnap(position);
                }
                mSnapping = false;
            }
        }
    };

    public CustomSnapHelper(int gravity) {
        this(gravity, false, null);
    }


    public CustomSnapHelper(int gravity, boolean enableSnapLastItem, SnapListener snapListener) {
        if (gravity !=  Gravity.TOP) {
            throw new IllegalArgumentException("Invalid gravity value. Use TOP constants");
        }
        mSnapListener = snapListener;
        mGravity = gravity;
        mSnapLastItemEnabled = enableSnapLastItem;
    }

    @Override
    public void attachToRecyclerView(@Nullable RecyclerView recyclerView)
            throws IllegalStateException {
        if (recyclerView != null) {
            if (mGravity == Gravity.START || mGravity == Gravity.END) {
                mIsRtlHorizontal = false;
                //  = recyclerView.getContext().getResources().getBoolean(R.bool.is_rtl);
            }
            if (mSnapListener != null) {
                recyclerView.addOnScrollListener(mScrollListener);
            }
        }
        super.attachToRecyclerView(recyclerView);
    }

    @Override
    public int[] calculateDistanceToFinalSnap(@NonNull RecyclerView.LayoutManager layoutManager,
                                              @NonNull View targetView) {
        int[] out = new int[2];


        if (layoutManager.canScrollVertically()) {
            if (mGravity == Gravity.TOP) {
                out[1] = distanceToStart(targetView, getVerticalHelper(layoutManager), false);
            } else { // BOTTOM
                out[1] = distanceToEnd(targetView, getVerticalHelper(layoutManager), false);
            }
        } else {
            out[1] = 0;
        }

        return out;
    }

    @Override
    public View findSnapView(RecyclerView.LayoutManager layoutManager) {
        View snapView = null;
        if (layoutManager instanceof LinearLayoutManager) {



            if(mGravity == Gravity.TOP){
                snapView = findStartView(layoutManager, getVerticalHelper(layoutManager));

            }
        }

        mSnapping = snapView != null;

        return snapView;
    }


    private int distanceToStart(View targetView, OrientationHelper helper, boolean fromEnd) {
        if (mIsRtlHorizontal && !fromEnd) {
            return distanceToEnd(targetView, helper, true);
        }

        return helper.getDecoratedStart(targetView) - helper.getStartAfterPadding();
    }

    private int distanceToEnd(View targetView, OrientationHelper helper, boolean fromStart) {
        if (mIsRtlHorizontal && !fromStart) {
            return distanceToStart(targetView, helper, true);
        }

        return helper.getDecoratedEnd(targetView) - helper.getEndAfterPadding();
    }

    /**
     * /**
     * Returns the first view that we should snap to.
     *
     * @param layoutManager the recyclerview's layout manager
     * @param helper        orientation helper to calculate view sizes
     * @return the first view in the LayoutManager to snap to
     */
    private View findStartView(RecyclerView.LayoutManager layoutManager,
                               OrientationHelper helper) {

        if (layoutManager instanceof LinearLayoutManager) {

            int firstChild = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();

            int lastChild = ((LinearLayoutManager) layoutManager).findFirstCompletelyVisibleItemPosition();

            int lastChildForTwoPager = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();


            if (firstChild == RecyclerView.NO_POSITION) {
                return null;
            }

            View child = layoutManager.findViewByPosition(firstChild);

            View lastChildView = layoutManager.findViewByPosition(lastChild);
            View lastChildViewForTwoPager = layoutManager.findViewByPosition(lastChildForTwoPager);

            float visibleWidth;

            // We should return the child if it's visible width
            // is greater than 0.5 of it's total width.
            // In a RTL configuration, we need to check the start point and in LTR the end point
            if (mIsRtlHorizontal) {
                visibleWidth = (float) (helper.getTotalSpace() - helper.getDecoratedStart(child))
                        / helper.getDecoratedMeasurement(child);
            } else {
                visibleWidth = (float) helper.getDecoratedEnd(child)
                        / helper.getDecoratedMeasurement(child);
            }

            // If we're at the end of the list, we shouldn't snap
            // to avoid having the last item not completely visible.
            boolean endOfList = ((LinearLayoutManager) layoutManager)
                    .findLastCompletelyVisibleItemPosition()
                    == layoutManager.getItemCount() - 1;

            if (visibleWidth > 0.5f && !endOfList) {
                return child;

            }else if (visibleWidth < 0.5f && !endOfList) {
                // check the position of first completely visible item
                if (lastChild>0){
                    return lastChildView;
                }else{
                    return lastChildViewForTwoPager;
                }
            } else if (mSnapLastItemEnabled && endOfList) {
                return child;
            } else if (endOfList) {
                return null;
            } else {
                // If the child wasn't returned, we need to return
                // the next view close to the start.
                return layoutManager.findViewByPosition(firstChild + 1);
            }
        }

        return null;
    }

    int getSnappedPosition(RecyclerView recyclerView) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();

        if (layoutManager instanceof LinearLayoutManager) {
            if ( mGravity == Gravity.TOP) {
                //   return ((LinearLayoutManager) layoutManager).findLastCompletelyVisibleItemPosition();
                return ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
            }
        }

        return RecyclerView.NO_POSITION;

    }

    private OrientationHelper getVerticalHelper(RecyclerView.LayoutManager layoutManager) {
        if (mVerticalHelper == null) {
            mVerticalHelper = OrientationHelper.createVerticalHelper(layoutManager);
        }
        return mVerticalHelper;
    }


    public interface SnapListener {
        void onSnap(int position);
    }

}
