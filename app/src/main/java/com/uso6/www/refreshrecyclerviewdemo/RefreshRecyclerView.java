package com.uso6.www.refreshrecyclerviewdemo;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author aaa539687357@vip.qq.com
 */
public class RefreshRecyclerView extends SwipeRefreshLayout {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    AdapterObserver observer = new AdapterObserver();

    public RefreshRecyclerView(Context context) {
        this(context, null);
    }

    public RefreshRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        recyclerView = new RecyclerView(getContext());
        this.addView(recyclerView);
    }

    /**
     * 获取 RecyclerView
     * @return
     */
    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    /**
     * 设置布局
     * @param layoutManager
     */
    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        recyclerView.setLayoutManager(layoutManager);
    }

    /**
     * 设置适配器
     * @param adapter 适配器
     */
    public void setAdapter(RecyclerView.Adapter adapter) {
        if (adapter != null && this.adapter == null) {
            this.adapter = adapter;
            this.adapter.registerAdapterDataObserver(observer);
        } else if (this.adapter != null && adapter != null) {
            this.adapter.unregisterAdapterDataObserver(observer);
            this.adapter = adapter;
            this.adapter.registerAdapterDataObserver(observer);
        } else if (this.adapter != null) {
            this.adapter.unregisterAdapterDataObserver(observer);
            this.adapter = null;
        } else {
            this.adapter = null;
        }
        recyclerView.setAdapter(adapter);
    }

    /**
     * 添加分割线
     * @param itemDecoration 分割线
     */
    public void addItemDecoration(RecyclerView.ItemDecoration itemDecoration) {
        recyclerView.addItemDecoration(itemDecoration);
    }

    /**
     * 自动刷新
     */
    public void autoRefresh() {
        try {
            Field mCircleView = SwipeRefreshLayout.class.getDeclaredField("mCircleView");
            mCircleView.setAccessible(true);
            View progress = (View) mCircleView.get(this);
            progress.setVisibility(View.VISIBLE);

            Method setRefreshing = SwipeRefreshLayout.class.getDeclaredMethod("setRefreshing", boolean.class, boolean.class);
            setRefreshing.setAccessible(true);
            setRefreshing.invoke(this, true, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 适配器观察者
     */
    private class AdapterObserver extends RecyclerView.AdapterDataObserver {

        @Override
        public void onChanged() {
            super.onChanged();
            RefreshRecyclerView.this.setRefreshing(false);
        }

    }

}
