package kr.pe.burt.android.lib.fragmentnavigationcontroller.app;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Random;

import kr.pe.burt.android.lib.fragmentnavigationcontroller.AndroidFragment;
import kr.pe.burt.android.lib.fragmentnavigationcontroller.FragmentNavigationController;

/**
 * Created by burt on 2016. 6. 13..
 */
public class ChildFragment extends AndroidFragment {

    @Nullable
    @Override
    protected View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_child, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        Random r = new Random();
        getContentView().setBackgroundColor(Color.rgb(r.nextInt(256), r.nextInt(256), r.nextInt(256)));
    }
}
