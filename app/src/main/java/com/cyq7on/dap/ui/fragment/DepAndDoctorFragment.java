package com.cyq7on.dap.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.cyq7on.dap.R;
import com.cyq7on.dap.adapter.DepAndDoctorAdapter;
import com.cyq7on.dap.base.ParentWithNaviFragment;
import com.cyq7on.dap.bean.FatherData;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by cyq7on on 18-3-23.
 */

public class DepAndDoctorFragment extends ParentWithNaviFragment {
    @Bind(R.id.expand_list)
    ExpandableListView expandList;
    @Bind(R.id.sw_refresh)
    SwipeRefreshLayout swRefresh;
    private ArrayList<FatherData> list = new ArrayList<>();
    private DepAndDoctorAdapter adapter;

    @Override
    protected String title() {
        return "医生";
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new DepAndDoctorAdapter(getActivity(),list);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_contact_dep, container, false);
        ButterKnife.bind(this, rootView);
        expandList.setAdapter(adapter);
        expandList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                return false;
            }
        });
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
