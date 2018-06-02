package pl.przepisomat.przepisomat.components;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.AbsListView;

public class ListView extends android.widget.ListView implements AbsListView.OnScrollListener {

    private boolean inEndOfList = false;
    private ListViewListener listViewListener;

    public ListView(Context context) {
        super(context);
        init(context);
    }

    public ListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int scrollState) {
    }

    @Override
    public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        int tot = firstVisibleItem + visibleItemCount;
        if(tot >= totalItemCount) {

            if(!inEndOfList) {
                Log("onScroll, end of list");
                inEndOfList = true;

                if(listViewListener != null)
                    listViewListener.onEndOfList();
            }
        } else
            inEndOfList = false;
    }


    private void init(Context context) {
        this.setOnScrollListener(this);
    }

    private void Log(String str) {
        Log.d("ListView", str);
    }

    public void setListViewListener(ListViewListener listViewListener) {
        this.listViewListener = listViewListener;
    }

    public  interface ListViewListener {
         void onEndOfList() ;
    }

}
