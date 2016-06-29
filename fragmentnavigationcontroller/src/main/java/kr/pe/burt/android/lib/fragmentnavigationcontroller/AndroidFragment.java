package kr.pe.burt.android.lib.fragmentnavigationcontroller;


import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;

/**
 * Created by burt on 2016. 5. 24..
 */
public abstract class AndroidFragment extends Fragment  {

    private WeakReference<FragmentNavigationController> weakFragmentNaviagationController = null;
    protected boolean animatable = true;
    private AndroidFragmentFrameLayout innerRootLayout = null;
    private View contentView = null;
    private PresentStyle presentStyle = null;

    public FragmentNavigationController getNavigationController() {
        if(weakFragmentNaviagationController == null)
            return null;
        return weakFragmentNaviagationController.get();
    }

    protected void setNavigationController(FragmentNavigationController fragmentNavigationController) {
        weakFragmentNaviagationController = new WeakReference<>(fragmentNavigationController);
    }


    protected void setAnimatable(boolean animatable) {
        this.animatable = animatable;
    }

    protected void setPresentStyle(PresentStyle presentStyle) {
        this.presentStyle = presentStyle;
    }

    @Nullable
    @Override
    final public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = onCreateContentView(inflater, container, savedInstanceState);
        if(v == null) return v;
        contentView = v;
        innerRootLayout = new AndroidFragmentFrameLayout(getActivity());
        innerRootLayout.addView(contentView);
        return innerRootLayout;
    }

    @Nullable
    abstract protected View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    /**
     * This is the layout for wrapping contentView
     * @return AndroidFragmentFrameLayout
     */
    public AndroidFragmentFrameLayout getRootLayout() {
        return innerRootLayout;
    }

    /**
     * This is the layout-view which is definded by user.
     * @return content view
     */
    public View getContentView() {
        return contentView;
    }

    @Override
    public Animator onCreateAnimator(final int transit, final boolean enter, int nextAnim) {
        if(animatable == false) {
            animatable = true;
            return null;
        }

        FragmentNavigationController nav =  getNavigationController();
        if(nav == null) {
            return null; //no animatable
        }

        if(presentStyle == null) {
            return null; //no animatable
        }

        Animator animator = null;
        if(transit == FragmentTransaction.TRANSIT_FRAGMENT_OPEN) {

            if (enter) {
                int id = presentStyle.getOpenEnterAnimatorId();
                if(id != -1) animator = AnimatorInflater.loadAnimator(getActivity(), id);
            } else {
                int id = presentStyle.getOpenExitAnimatorId();
                if(id != -1) animator = AnimatorInflater.loadAnimator(getActivity(), id);
            }

        } else {

            if (enter) {
                int id = presentStyle.getCloseEnterAnimatorId();
                if(id != -1) animator = AnimatorInflater.loadAnimator(getActivity(), id);
            } else {
                int id = presentStyle.getCloseExitAnimatorId();
                if(id != -1) animator = AnimatorInflater.loadAnimator(getActivity(), id);
            }
        }
        if(animator != null) {
            animator.setInterpolator(nav.getInterpolator());
            animator.setDuration(nav.getDuration());
        }

        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                if(transit == FragmentTransaction.TRANSIT_FRAGMENT_CLOSE && enter == false) {
                    onHideFragment();
                }
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if(transit == FragmentTransaction.TRANSIT_FRAGMENT_OPEN && enter == true) {
                    onShowFragment();
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        return animator;
    }

    public void onShowFragment() {}
    public void onHideFragment() {}
}
