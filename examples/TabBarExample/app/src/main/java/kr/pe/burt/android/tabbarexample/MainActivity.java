package kr.pe.burt.android.tabbarexample;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.OvershootInterpolator;

import butterknife.BindView;
import butterknife.ButterKnife;
import kr.pe.burt.android.lib.fragmentnavigationcontroller.FragmentNavigationController;
import kr.pe.burt.android.lib.fragmentnavigationcontroller.PresentStyle;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tabs)
    TabLayout tabs;

    @BindView(R.id.tabContainer1)
    View tabContainer1;

    @BindView(R.id.tabContainer2)
    View tabContainer2;

    @BindView(R.id.tabContainer3)
    View tabContainer3;


    FragmentNavigationController listTab = null;
    FragmentNavigationController cardTab = null;
    FragmentNavigationController tileTab = null;
    FragmentNavigationController currentNavigationController = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        setupNavigationController();
        tabs.addTab(tabs.newTab().setText("Random"));
        tabs.addTab(tabs.newTab().setText("Accordion"));
        tabs.addTab(tabs.newTab().setText("Push"));
        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        tabContainer1.setVisibility(View.VISIBLE);
                        tabContainer2.setVisibility(View.GONE);
                        tabContainer3.setVisibility(View.GONE);
                        currentNavigationController = listTab;
                        break;
                    case 1:
                        tabContainer1.setVisibility(View.GONE);
                        tabContainer2.setVisibility(View.VISIBLE);
                        tabContainer3.setVisibility(View.GONE);
                        currentNavigationController = cardTab;
                        break;
                    case 2:
                        tabContainer1.setVisibility(View.GONE);
                        tabContainer2.setVisibility(View.GONE);
                        tabContainer3.setVisibility(View.VISIBLE);
                        currentNavigationController = tileTab;
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setupNavigationController() {

        listTab = FragmentNavigationController.navigationController(this, R.id.tabContainer1);
        listTab.setPresentStyle(PresentStyle.ACCORDION_LEFT);
        listTab.setInterpolator(new AccelerateDecelerateInterpolator());
        listTab.presentFragment(new FragmentFirst());


        cardTab = FragmentNavigationController.navigationController(this, R.id.tabContainer2);
        cardTab.setPresentStyle(PresentStyle.ACCORDION_LEFT);
        cardTab.setInterpolator(new BounceInterpolator());
        cardTab.presentFragment(new FragmentSecond());


        tileTab = FragmentNavigationController.navigationController(this, R.id.tabContainer3);
        tileTab.setPresentStyle(PresentStyle.SLIDE_LEFT);
        tileTab.setInterpolator(new OvershootInterpolator());
        tileTab.presentFragment(new FragmentThird());

        currentNavigationController = listTab;
    }


    @Override
    public void onBackPressed() {
        if(currentNavigationController == null) {
            super.onBackPressed();
        }

        if(!currentNavigationController.dismissFragment()) {
            super.onBackPressed();
        }
    }
}
