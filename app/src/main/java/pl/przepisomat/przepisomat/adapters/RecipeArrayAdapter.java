package pl.przepisomat.przepisomat.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.URL;

import pl.przepisomat.przepisomat.R;
import pl.przepisomat.przepisomat.api.model.Recipe;

public class RecipeArrayAdapter extends ArrayAdapter<Recipe> {
    private final Context context;
    private final Recipe[] values;

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
}