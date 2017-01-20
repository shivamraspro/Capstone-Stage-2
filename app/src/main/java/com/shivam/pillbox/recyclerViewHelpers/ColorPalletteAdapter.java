package com.shivam.pillbox.recyclerViewHelpers;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.shivam.pillbox.R;
import com.shivam.pillbox.ui.AddMedicationActivity;

/**
 * Created by shivam on 18/01/17.
 */

public class ColorPalletteAdapter extends RecyclerView.Adapter<ColorPalletteAdapter.ViewHolder> {
    private int[] colors;
    private Context context;

    public ColorPalletteAdapter(Context context, int[] c) {
        this.context = context;
        colors = c;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ColorPalletteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                              int viewType) {
        // create a new view
        View c = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.color_pallette_item, parent, false);
        ViewHolder vh = new ViewHolder(c);

        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        if(position == 0) {
            holder.colorViewContainer.setBackground(context.getResources().getDrawable(R.drawable
                    .scrim));
            AddMedicationActivity.oldView = holder.colorViewContainer;
        }
        holder.colorView.setBackgroundColor(context.getResources().getColor(colors[position]));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return 8;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final CardView colorView;
        public final FrameLayout colorViewContainer;

        public ViewHolder(View v) {
            super(v);
            colorViewContainer = (FrameLayout) v.findViewById(R.id.color_view_container);
            colorView = (CardView) colorViewContainer.findViewById(R.id.color_view);
        }
    }
}
