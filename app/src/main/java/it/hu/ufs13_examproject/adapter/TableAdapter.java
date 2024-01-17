package it.hu.ufs13_examproject.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import it.hu.ufs13_examproject.R;
import it.hu.ufs13_examproject.model.Order;
import it.hu.ufs13_examproject.model.Row;
import it.hu.ufs13_examproject.model.Table;

public class TableAdapter extends ArrayAdapter<Table> {

    public TableAdapter(Context context, ArrayList<Table> tables) {
        super(context, 0, tables);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Table table = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.table_layout_item, parent, false);
        } else {
            Log.d("PersonAdapter getView", "Recycling view");
        }

        TextView ti_name = (TextView) convertView.findViewById(R.id.table_item_name);
        TextView ti_num = (TextView) convertView.findViewById(R.id.table_item_number);

        ti_name.setText(table.getName());
        ti_num.setText("N°: " + table.getNum());

        return convertView;
    }
}
