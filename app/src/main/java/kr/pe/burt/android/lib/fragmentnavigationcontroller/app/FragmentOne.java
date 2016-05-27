package kr.pe.burt.android.lib.fragmentnavigationcontroller.app;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Random;

import kr.pe.burt.android.lib.fragmentnavigationcontroller.AndroidFragment;
import kr.pe.burt.android.lib.fragmentnavigationcontroller.FragmentNavigationController;

/**
 * Created by burt on 2016. 5. 22..
 */
public class FragmentOne extends AndroidFragment {

    @Override
    protected View getContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_one, container, false);
        TextView t = (TextView) v.findViewById(R.id.title);
        FragmentNavigationController navigationController = getNavigationController();
        if(navigationController != null) {
           t.setText("" + navigationController.getFragmentCount());
        }
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        Random r = new Random();
        getContentView().setBackgroundColor(Color.rgb(r.nextInt(256), r.nextInt(256), r.nextInt(256)));
    }
}
