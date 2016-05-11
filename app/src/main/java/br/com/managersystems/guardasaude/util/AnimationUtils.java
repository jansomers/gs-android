package br.com.managersystems.guardasaude.util;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.RelativeLayout;

/**
 * Utility class to handle layout animations.
 * <p/>
 * Currently handles animations for:
 * - Expanding views
 * - Collapsing views
 * <p/>
 * Authors:
 *
 * @author Jan Somers
 * @author Thanee Stevens
 */
public class AnimationUtils {

    /**
     * Measures the height for the view and sets it as the targetheight.
     * Sets the wanted height it should be after the animation is complete.
     * Shows the view.
     * Creates the animation and makes is so the height is no longer increased after reaching full size.
     * Sets the duration for the animation and starts it.
     * @param v View object representing the view that needs to expand.
     */
    public static void expand(final View v) {
        v.measure(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        final int targetHeight = v.getMeasuredHeight();

        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        v.getLayoutParams().height = 1;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? RelativeLayout.LayoutParams.WRAP_CONTENT
                        : (int) (targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int) ((targetHeight / v.getContext().getResources().getDisplayMetrics().density)));
        v.startAnimation(a);
    }

    /**
     * Checks the initial height.
     * Create the animation and hides the view when it's complete.
     * Sets the duration.
     * Starts the animation.
     * @param v View object representing the view that needs to collapse.
     */
    public static void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    v.setVisibility(View.GONE);
                } else {
                    v.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int) ((initialHeight / v.getContext().getResources().getDisplayMetrics().density)));
        v.startAnimation(a);
    }
}
