package pl.przepisomat.przepisomat.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import pl.przepisomat.przepisomat.R;
import pl.przepisomat.przepisomat.api.model.Steps;

public class StepsAdapter extends ArrayAdapter {
    private final Context context;
    private final Steps[] steps;

    public StepsAdapter(Context context, Steps[] steps) {
        super(context, -1, steps);
        this.context = context;
        this.steps = steps;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.step_item, parent, false);
        TextView textNumber = rowView.findViewById(R.id.stepNb);
        TextView textDescription = rowView.findViewById(R.id.stepDesc);

        textNumber.setText("Krok nr "+ (position+1));
        textDescription.setText(steps[position].getOpis());

        return rowView;
    }
}
