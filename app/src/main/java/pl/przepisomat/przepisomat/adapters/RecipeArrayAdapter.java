package pl.przepisomat.przepisomat.adapters;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.URL;
import java.util.Collection;

import pl.przepisomat.przepisomat.R;
import pl.przepisomat.przepisomat.api.model.Recipe;

public class RecipeArrayAdapter extends ArrayAdapter<Recipe> {
    private final Context context;
    private Recipe[] values;

    public RecipeArrayAdapter(Context context, Recipe[] values) {
        super(context, -1, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.recipe_item, parent, false);
        TextView textView = rowView.findViewById(R.id.firstLine);
        TextView textTime = rowView.findViewById(R.id.secondLine1Col);
        TextView textDiff = rowView.findViewById(R.id.secondLine2Col);
        TextView textPerson = rowView.findViewById(R.id.secondLine3Col);
        ImageView imageView = rowView.findViewById(R.id.icon);
        textView.setText(values[position].getNazwa());
        textTime.setText(values[position].getCzas_przygotowania());
        textDiff.setText(values[position].getTrudnosc());
        textPerson.setText(values[position].getIlosc_porcji());

        URL url = null;
        try {
            url = new URL(values[position].getZdjecie());
            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            imageView.setImageBitmap(bmp);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rowView;
    }

    @Override
    public void addAll(@NonNull Collection<? extends Recipe> collection) {
        values = concat(values, collection.toArray(new Recipe[collection.size()-1]));

    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
        super.registerDataSetObserver(observer);
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        super.unregisterDataSetObserver(observer);
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return values.length;
    }
    @Override
    public Recipe getItem(int pos) {
        return values[pos];
    }

    static Recipe[] concat(Recipe[]... arrays) {
        int length = 0;
        for (Recipe[] array : arrays) {
            length += array.length;
        }
        Recipe[] result = new Recipe[length];
        int pos = 0;
        for (Recipe[] array : arrays) {
            for (Recipe element : array) {
                result[pos] = element;
                pos++;
            }
        }
        return result;
    }
}
