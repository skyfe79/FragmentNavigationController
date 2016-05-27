package kr.pe.burt.android.lib.fragmentnavigationcontroller.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.animation.AccelerateDecelerateInterpolator;

import java.util.Random;

import kr.pe.burt.android.lib.fragmentnavigationcontroller.AndroidFragment;
import kr.pe.burt.android.lib.fragmentnavigationcontroller.PresentStyle;
import kr.pe.burt.android.lib.fragmentnavigationcontroller.FragmentNavigationController;

public class MainActivity extends AppCompatActivity {


    FragmentNavigationController navigationController;
    boolean one = false;
    AndroidFragment fragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigationController = FragmentNavigationController.navigationController(this, R.id.fragmentContainer);
        navigationController.setPresentStyle(PresentStyle.ACCORDION_LEFT);
        navigationController.setInterpolator(new AccelerateDecelerateInterpolator());

        tickFragment();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_UP) {
            tickFragment();
        }
        return super.onTouchEvent(event);
    }

    private void tickFragment() {
        Random r = new Random();
        navigationController.setPresentStyle(r.nextInt(39)+1); //exclude NONE present style
        navigationController.presentFragment(new FragmentOne());
    }

    @Override
    public void onBackPressed() {
        if(navigationController.dismissFragment() == false) {
            super.onBackPressed();
        }
    }
}
