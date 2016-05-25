package kr.pe.burt.android.lib.fragmentnavigationcontroller.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;

public class MainActivity extends AppCompatActivity {


    FragmentNavigationController navigationController;
    boolean one = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigationController = FragmentNavigationController.navigationController(this, R.id.fragmentContainer);

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
        if(one == false) {
            navigationController.pushFragment(new FragmentOne(), true);
            one = true;
        } else {
            navigationController.popFragment(true);
            one = false;
        }
    }
}
