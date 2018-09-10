package com.csp.formwork.libwidget.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collection;

@SuppressWarnings("unused")
public class HeadFootAdapter extends RecyclerView.Adapter<ViewHolder> {
    private MultipleAdapter mAdapter;

    private static final int VIEW_TYPE_HEAD = 1 << 16;
    private static final int VIEW_TYPE_FOOT = 2 << 16;

    private SparseArrayCompat<View> mHeadViews = new SparseArrayCompat<>();
    private SparseArrayCompat<View> mFootViews = new SparseArrayCompat<>();

    public MultipleAdapter getAdapter() {
        return mAdapter;
    }

    /**
     * 加入HeaderView
     */
    public void addHeaderView(View view) {
        mHeadViews.put(mHeadViews.size() + VIEW_TYPE_HEAD, view);
    }

    /**
     * 加入FooterView
     */
    public void addFootView(View view) {
        mFootViews.put(mFootViews.size() + VIEW_TYPE_FOOT, view);
    }

    public void clearHeadView() {
        mHeadViews.clear();
    }

    public void clearFootView() {
        mFootViews.clear();
    }

    /**
     * @see MultipleAdapter#addData(Collection, boolean)
     */
    @SuppressWarnings("unchecked")
    public void addData(Collection data, boolean append) {
        mAdapter.addData(data, append);
    }

    public HeadFootAdapter(@NonNull MultipleAdapter adapter) {
        mAdapter = adapter;
    }

    @Override
    public int getItemCount() {
        return mHeadViews.size() + mFootViews.size() + mAdapter.getItemCount();
    }

    @Override
    public int getItemViewType(int position) {
        if (position < mHeadViews.size())
            return mHeadViews.keyAt(position);

        int headSize = mHeadViews.size();
        int index = position - headSize - mAdapter.getItemCount();
        if (index > -1)
            return mFootViews.keyAt(index);
        else
            return mAdapter.getItemViewType(position - headSize);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (isHeadOrFootByViewType(viewType)) {
            View view = mHeadViews.get(viewType);
            if (view == null)
                view = mFootViews.get(viewType);

            Context context = mAdapter.mContext;
            ViewHolder holder = new ViewHolder(context, view);

            mAdapter.onCreateViewHolder(holder);
            // mAdapter.setOnClickListener(parent, holder);
            return holder;
        } else
            return mAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int viewType = holder.getItemViewType();
        if (!isHeadOrFootByViewType(viewType))
            mAdapter.onBindViewHolder(holder, position - mHeadViews.size());
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        mAdapter.onAttachedToRecyclerView(recyclerView);

        final RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = (GridLayoutManager) manager;
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int viewType = getItemViewType(position);
                    return isHeadOrFootByViewType(viewType)
                            ? gridManager.getSpanCount() : 1;
                }
            });
        }
    }

    @Override
    public void onViewAttachedToWindow(@NonNull ViewHolder holder) {
        super.onViewAttachedToWindow(holder);

        int viewType = holder.getItemViewType();
        if (isHeadOrFootByViewType(viewType)) {
            ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
            if (null != lp && lp instanceof StaggeredGridLayoutManager.LayoutParams) {
                StaggeredGridLayoutManager.LayoutParams p
                        = (StaggeredGridLayoutManager.LayoutParams) lp;
                p.setFullSpan(true);
            }
        }
    }

    private boolean isHeadOrFootByViewType(int viewType) {
        return (viewType & VIEW_TYPE_HEAD) == VIEW_TYPE_HEAD
                || (viewType & VIEW_TYPE_FOOT) == VIEW_TYPE_FOOT;
    }

    /**
     * 解析 XML，如果 ViewGroup 是 RecyclerView，那么保证 RecyclerView 已经执行过 setAdapter()
     */
    public View inflate(int layoutId, ViewGroup parent) {
        return mAdapter.mInflater.inflate(layoutId, parent, false);
    }
}
