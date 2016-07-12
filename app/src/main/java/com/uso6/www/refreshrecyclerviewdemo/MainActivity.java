package com.uso6.www.refreshrecyclerviewdemo;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    static List<String> items = new ArrayList<>();
    static {
//        items.add("one");
//        items.add("two");
//        items.add("three");
    }

    RefreshRecyclerView recyclerView;
    BaseQuickAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RefreshRecyclerView) findViewById(R.id.recycleView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new BaseQuickAdapter<String>(android.R.layout.simple_list_item_1, items) {
            @Override
            protected void convert(BaseViewHolder baseViewHolder, String s) {
                baseViewHolder.setText(android.R.id.text1, s);
            }
        };
        recyclerView.setAdapter(adapter);
        recyclerView.setOnRefreshListener(this);
        recyclerView.autoRefresh();

    }



    @Override
    public void onRefresh() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(3000);
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            items.add("item");
                            adapter.notifyDataSetChanged();
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
