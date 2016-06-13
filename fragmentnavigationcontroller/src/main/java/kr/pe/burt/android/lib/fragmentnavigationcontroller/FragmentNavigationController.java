package kr.pe.burt.android.lib.fragmentnavigationcontroller;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import java.lang.ref.WeakReference;
import java.util.Stack;

/**
 * Created by burt on 2016. 5. 24..
 */
@SuppressLint("ValidFragment")
public class FragmentNavigationController extends AndroidFragment {

    private FragmentManager fragmentManager = null;
    private Stack<AndroidFragment> fragmentStack = new Stack<>();
    private @IdRes int containerViewId;
    private Object sync = new Object();
    private PresentStyle presentStyle = PresentStyle.get(PresentStyle.NONE);
    private Interpolator interpolator = new LinearInterpolator();
    private long duration = 500;

    public static FragmentNavigationController navigationController(@NonNull FragmentManager fragmentManager, @IdRes int containerViewId) {
        return new FragmentNavigationController(fragmentManager, containerViewId);
    }

    private FragmentNavigationController(@NonNull FragmentManager fragmentManager, @IdRes int containerViewId) {
        setRetainInstance(true);
        this.containerViewId = containerViewId;
        this.fragmentManager = fragmentManager;

        synchronized (sync) {
            // 자기 자신을 넣는다.
            fragmentManager
                    .beginTransaction()
                    .replace(containerViewId, this, "navigation-controller")
                    .commit();
        }
    }

    @Nullable
    @Override
    protected View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return null;
    }

    public int getFragmentCount() {
        return fragmentStack.size();
    }


    /**
     * set the present style
     * @param style
     */
    public void setPresentStyle(int style) {
        presentStyle = PresentStyle.get(style);
    }

    /**
     * for setting of user defined PrensetStyle
     * @param style
     */
    public void setPresentStyle(PresentStyle style) {
        presentStyle = style;
    }

    PresentStyle getPresentStyle() {
        return presentStyle;
    }

    public void setInterpolator(Interpolator interpolator) {
        this.interpolator = interpolator;
    }

    public void setDuration(long presentDuration) {
        duration = presentDuration;
    }

    Interpolator getInterpolator() {
        return interpolator;
    }

    long getDuration() {
        return duration;
    }

    public void pushFragment(AndroidFragment fragment) {
        PresentStyle oldPresetStyle = presentStyle;
        setDuration(300);
        setInterpolator(new AccelerateDecelerateInterpolator());
        setPresentStyle(PresentStyle.SLIDE_LEFT);
        presentFragment(fragment);
        presentStyle = oldPresetStyle;
        setPresentStyle(presentStyle);
    }

    public void popFragment() {
        dismissFragment();
    }

    public void presentFragment(AndroidFragment fragment) {
        presentFragment(fragment, true);
    }

    public void presentFragment(AndroidFragment fragment, boolean withAnimation) {

        if(fragmentManager == null) return;

        synchronized (sync) {

            if (fragmentStack.size() == 0) {
                fragment.setNavigationController(this);
                fragment.setAnimatable(false);
                fragment.setPresentStyle(presentStyle);
                fragmentManager
                        .beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .replace(containerViewId, fragment, "root")
                        .commit();

            } else {

                fragment.setNavigationController(this);
                fragment.setAnimatable(withAnimation);
                fragment.setPresentStyle(presentStyle);
                // hide last fragment and add new fragment
                fragmentManager
                        .beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .hide(fragmentStack.peek())
                        .add(containerViewId, fragment, fragment.getClass().getName())
                        .commit();
            }
            fragmentStack.add(fragment);
        }
    }

    public boolean dismissFragment() {
        return dismissFragment(true);
    }

    public boolean dismissFragment(boolean withAnimation) {

        if(fragmentManager == null) return false;

        // fragmentStack only has root fragment
        if(fragmentStack.size() == 1) {

            // show the root fragment
            AndroidFragment fragmentToShow = fragmentStack.peek();
            fragmentToShow.setNavigationController(this);
            fragmentToShow.setAnimatable(withAnimation);
            fragmentManager
                    .beginTransaction()
                    .show(fragmentToShow)
                    .commit();
            return false;
        }


        synchronized (sync) {

            AndroidFragment fragmentToRemove = fragmentStack.pop();
            fragmentToRemove.setNavigationController(this);
            fragmentToRemove.setAnimatable(withAnimation);

            AndroidFragment fragmentToShow = fragmentStack.peek();
            fragmentToShow.setNavigationController(this);
            fragmentToShow.setAnimatable(withAnimation);
            fragmentManager
                    .beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                    .show(fragmentToShow)
                    .remove(fragmentToRemove)
                    .commit();

        }
        return true;
    }

    public void popToRootFragment() {
        while (fragmentStack.size() >= 2) {
            dismissFragment();
        }
    }

}
