package kr.pe.burt.android.lib.fragmentnavigationcontroller.app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Objects;
import java.util.Stack;

/**
 * Created by burt on 2016. 5. 24..
 */
@SuppressLint("ValidFragment")
public class FragmentNavigationController extends AnimatableFragment {

    private WeakReference<AppCompatActivity> weakRootActivity = null;
    private Stack<AnimatableFragment> fragmentStacks = new Stack<>();
    private @IdRes int containerViewId;
    private Object sync = new Object();

    public static FragmentNavigationController navigationController(@NonNull AppCompatActivity activity, @IdRes int containerViewId) {
        return new FragmentNavigationController(activity, containerViewId);
    }

    private FragmentNavigationController(@NonNull AppCompatActivity activity, @IdRes int containerViewId) {
        weakRootActivity = new WeakReference<>(activity);
        this.containerViewId = containerViewId;

        // 자기 자신을 넣는다.
        synchronized (sync) {
            activity.getSupportFragmentManager()
                    .beginTransaction()
                    .add(this, "navigation-controller")
                    .commit();
        }
    }

    public void pushFragment(AnimatableFragment fragment, boolean withAnimation) {
        AppCompatActivity activity = weakRootActivity.get();
        if(activity == null) return;

        fragment.setWithAnimation(false);
        synchronized (sync) {
            if (fragmentStacks.size() == 0) {
                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .add(containerViewId, fragment, "root")
                        .commit();
            } else {
                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .add(containerViewId, fragment)
                        .commit();
            }
            fragmentStacks.add(fragment);
        }
    }

    public void popFragment(boolean withAnimation) {
        // root만 있는 경우,
        if(fragmentStacks.size() == 1) return;

        AppCompatActivity activity = weakRootActivity.get();
        if(activity == null) return;

        synchronized (sync) {
            AnimatableFragment fragmentToRemove = fragmentStacks.pop();
            fragmentToRemove.setWithAnimation(true);
            activity.getSupportFragmentManager()
                    .beginTransaction()
                    .remove(fragmentToRemove)
                    .commit();
        }
    }

    public void popToRootFragment() {
        if(fragmentStacks.size() <= 1) return;

        AppCompatActivity activity = weakRootActivity.get();
        if(activity == null) return;

        synchronized (sync) {

        }
    }

    public Activity getActity() {
        return weakRootActivity.get();
    }
}
