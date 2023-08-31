package com.aarenas.syssales;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.aarenas.syssales.databinding.ActivitySaleBinding;
import com.google.android.material.tabs.TabLayoutMediator;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import org.ksoap2.serialization.SoapObject;

import java.util.HashMap;
import java.util.List;

import Connection.WebMethods;
import Connection.WebServices;
import Data.Objects.Company;
import Data.Objects.User;

public class SaleActivity extends FragmentActivity implements SaleDataFragment.OnFragmentInteractionListener,
        SaleDetailsFragment.OnFragmentInteractionListener{

    //Constantes:
    private final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2, R.string.tab_text_3};

    private ViewPager2 viewPager;
    private ActivitySaleBinding binding;
    private FragmentStateAdapter pagerAdapter;

    private SaleDataFragment fragment1;
    private SaleDetailsFragment fragment2;
    private SaleSummaryFragment fragment3;

    //Parámetros:
    Bundle parameters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        parameters = getIntent().getExtras();

        binding = ActivitySaleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        fragment1 = SaleDataFragment.newInstance(parameters);
        fragment2 = SaleDetailsFragment.newInstance(parameters);
        fragment3 = SaleSummaryFragment.newInstance(parameters);

        viewPager = binding.viewPager;
        pagerAdapter = new FragmentAdapter(getSupportFragmentManager(), getLifecycle());
        viewPager.setAdapter(pagerAdapter);
        TabLayout tabs = binding.tabs;
        new TabLayoutMediator(tabs, viewPager, (tab, position) -> tab.setText(TAB_TITLES[position])).attach();

        FloatingActionButton fab = binding.fab;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
    }

    @Override
    public void onFragmentInteraction(HashMap<String, Object> objects, int id) {
        final int fragment_1 = R.layout.fragment_sale_data;
        final int fragment_2 = R.layout.fragment_sale_details;

        switch (id)
        {
            case fragment_1:
                fragment2.UpdateData(objects, id);
                fragment3.UpdateData(objects, id);
                break;
            case fragment_2:
                fragment1.UpdateData(objects, id);
                fragment3.UpdateData(objects, id);
            default:
                break;
        }
    }

    /*private static String makeFragmentName(int viewPagerId, long index) {
        return String.format("android:switcher:%s:%s", viewPagerId, index);
    }*/

    private class FragmentAdapter extends FragmentStateAdapter {

        public FragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
            super(fragmentManager, lifecycle);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            Fragment fragment;
            switch (position)
            {
                case 1:
                    fragment = fragment2;
                    break;
                case 2:
                    fragment = fragment3;
                    break;
                default:
                    fragment = fragment1;
                    break;
            }
            return fragment;
        }

        @Override
        public int getItemCount() {
            return TAB_TITLES.length;
        }
    }
}