package com.assignment.view.City;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.assignment.R;

import java.util.List;

/**
 * Created by agni on 25/01/18.
 */

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.CityViewHolder>{

    private LayoutInflater inflater;
    private Context context;
    private List<String> cities;


    public CityAdapter(Context context,List<String> cities) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.cities = cities;
    }

    @Override
    public CityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.layout_item_city, parent, false);
        return new CityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CityViewHolder holder, int position) {
        holder.cityName.setText(cities.get(position));
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }

    class CityViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView cityName;

        private CityViewHolder(View itemView) {
            super(itemView);
            cityName = itemView.findViewById(R.id.text_city);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            String text = "Selection is - " + cities.get(getAdapterPosition());
            Toast.makeText(context, text, Toast.LENGTH_LONG).show();
        }
    }
}
