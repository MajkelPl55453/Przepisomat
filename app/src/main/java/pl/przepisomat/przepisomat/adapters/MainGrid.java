package pl.przepisomat.przepisomat.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import pl.przepisomat.przepisomat.R;
import pl.przepisomat.przepisomat.api.model.Recipe;

public class MainGrid extends BaseAdapter {
    private Context context;
    private final Recipe[] recipes;

    public MainGrid(Context c,Recipe[] recipes) {
        context = c;
        this.recipes = recipes;
    }

    @Override
    public int getCount() {
        return recipes.length;
    }

    @Override
    public Object getItem(int position) {
        return recipes[position];
    }

    @Override
    public long getItemId(int position) {
        return recipes[position].getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View grid;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            grid = inflater.inflate(R.layout.grid_single, null);
            TextView textView = grid.findViewById(R.id.mainGridText);
            ImageView imageView = grid.findViewById(R.id.mainGridImg);
            textView.setText(recipes[position].getNazwa());
            imageView.setImageBitmap(RecipeArrayAdapter.getImageBitmap(context, recipes[position].getZdjecie()));
        } else {
            grid = convertView;
        }

        return grid;
    }
}
