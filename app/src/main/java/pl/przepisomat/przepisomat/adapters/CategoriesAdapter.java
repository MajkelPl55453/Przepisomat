package pl.przepisomat.przepisomat.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import lombok.Setter;
import pl.przepisomat.przepisomat.R;
import pl.przepisomat.przepisomat.api.model.Category;
import pl.przepisomat.przepisomat.api.model.CategoryList;

/**
 * Created by Majkel on 2018-05-03.
 */

public class CategoriesAdapter extends BaseExpandableListAdapter{
    @Setter
    private CategoryList response;
    private Context context;

    public CategoriesAdapter(Context context){
        this.context = context;
    }

    public CategoriesAdapter(Context context, CategoryList response)
    {
        this.context = context;
        this.response = response;
    }

    @Override
    public int getGroupCount() {
        return response.categories.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return response.categories.get(i).childs.size();
    }

    @Override
    public Object getGroup(int i) {
        return response.categories.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return response.categories.get(i).childs.get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        Category category = (Category) this.getGroup(i);

        if(category != null)
        {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.category_parent_list_element, null);
        }

        TextView textView = view.findViewById(R.id.category_parent_list_element);
        textView.setText(category.name);



        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        Category category = (Category) this.getChild(i, i1);

        if(category != null)
        {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.category_child_list_element, null);
        }

        TextView textView = view.findViewById(R.id.category_child_list_element);
        textView.setText(category.name);

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
