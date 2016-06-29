package kr.pe.burt.android.lib.fragmentnavigationcontroller.app;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Random;

import kr.pe.burt.android.lib.fragmentnavigationcontroller.AndroidFragment;
import kr.pe.burt.android.lib.fragmentnavigationcontroller.FragmentNavigationController;
import kr.pe.burt.android.lib.fragmentnavigationcontroller.PresentStyle;

/**
 * Created by burt on 2016. 5. 22..
 */
public class FragmentOne extends AndroidFragment {

    FragmentNavigationController childNavigationController;

    @Nullable
    @Override
    protected View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_one, container, false);
        TextView t = (TextView) v.findViewById(R.id.title);

        v.findViewById(R.id.childButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextFragment();
            }
        });

        FragmentNavigationController navigationController = getNavigationController();
        if(navigationController != null) {
           t.setText("" + navigationController.getFragmentCount());
        }

        childNavigationController = FragmentNavigationController.navigationController(getChildFragmentManager(), R.id.childFragmentContainer);
        childNavigationController.setPresentStyle(PresentStyle.ACCORDION_LEFT);
        childNavigationController.setInterpolator(new AccelerateDecelerateInterpolator());

        nextFragment();

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        Random r = new Random();
        getContentView().setBackgroundColor(Color.rgb(r.nextInt(256), r.nextInt(256), r.nextInt(256)));
    }

    private void nextFragment() {
        Random r = new Random();
        childNavigationController.setPresentStyle(r.nextInt(39)+1); //exclude NONE present style
        childNavigationController.presentFragment(new ChildFragment());
    }

    @Override
    public void onShowFragment() {
        Log.d("TAGTAG", "onShowFragment");
    }

    @Override
    public void onHideFragment() {
        Log.d("TAGTAG", "onHideFragment");
    }
}
