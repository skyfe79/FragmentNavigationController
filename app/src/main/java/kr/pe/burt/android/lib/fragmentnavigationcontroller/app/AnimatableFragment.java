package kr.pe.burt.android.lib.fragmentnavigationcontroller.app;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;

import java.lang.ref.WeakReference;

/**
 * Created by burt on 2016. 5. 24..
 */
public class AnimatableFragment extends Fragment {

    private WeakReference<FragmentNavigationController> weakFragmentNaviagationController = null;
    protected boolean withAnimation = true;

    public void setNavigationController(FragmentNavigationController fragmentNavigationController) {
        weakFragmentNaviagationController = new WeakReference<>(fragmentNavigationController);
    }

    public FragmentNavigationController getNavigationController() {
        return weakFragmentNaviagationController.get();
    }

    public boolean withAnimation() {
        return withAnimation;
    }

    public void setWithAnimation(boolean withAnimation) {
        this.withAnimation = withAnimation;
    }
}
