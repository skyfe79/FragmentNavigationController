package kr.pe.burt.android.tabbarexample;

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
 * Created by burt on 2016. 6. 1..
 */
public class FragmentFirst extends AndroidFragment {

    @Nullable
    @Override
    protected View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_layout, container, false);
        TextView t = (TextView) v.findViewById(R.id.title);
        FragmentNavigationController navigationController = getNavigationController();
        if(navigationController != null) {
            t.setText("" + navigationController.getFragmentCount());
        }
        v.findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentNavigationController nv = getNavigationController();
                if(nv == null) return;

                Random r = new Random();
                nv.setPresentStyle(r.nextInt(39)+1); //exclude NONE present style
                nv.presentFragment(new FragmentFirst());
            }
        });
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        Random r = new Random();
        getContentView().setBackgroundColor(Color.rgb(r.nextInt(256), r.nextInt(256), r.nextInt(256)));
    }
}
