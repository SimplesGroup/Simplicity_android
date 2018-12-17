package simplicity_an.simplicity_an;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by adammcneilly on 9/2/15.
 */
public class FABScrollBehavior extends FloatingActionButton.Behavior {
    public FABScrollBehavior(Context context, AttributeSet attributeSet){
        super();
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View directTargetChild, View target, int nestedScrollAxes) {
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
/*
        if(dyConsumed >0 && child.getVisibility() == View.VISIBLE){
            child.hide();
        } else if(dyConsumed < 15 && child.getVisibility() == View.GONE){
            child.show();
        }*/
     /*   if (dyConsumed > 15 && child.getVisibility() == View.GONE) {
            // User scrolled down and the FAB is currently visible -> hide the FAB
            child.show();
        } else if (dyConsumed < 15 && child.getVisibility() != View.VISIBLE) {
            // User scrolled up and the FAB is currently not visible -> show the FAB
            child.hide();
        }*/
if(dyConsumed<15){
    child.hide();
}else {
    child.show();
}
    }
}
