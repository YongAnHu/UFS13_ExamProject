package it.hu.ufs13_examproject.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;

import it.hu.ufs13_examproject.R;
import it.hu.ufs13_examproject.data.Data;
import it.hu.ufs13_examproject.data.OrderAsyncResponse;
import it.hu.ufs13_examproject.data.Repository;
import it.hu.ufs13_examproject.model.Order;
import it.hu.ufs13_examproject.model.Row;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
	private final ArrayList<Order> order;
	private OnItemClickListener onItemClickListener;

	public OrderAdapter(ArrayList<Order> order) {
		this.order = order;
	}

	public void setOnItemClickListener(OnItemClickListener listener) {
		this.onItemClickListener = listener;
	}

	@NonNull
	@Override
	public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext())
				.inflate(R.layout.order_layout_item, parent, false);
		return new OrderViewHolder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull OrderViewHolder holder, @SuppressLint("RecyclerView") int position) {
		holder.bind(order.get(position));

		holder.itemView.setOnClickListener(v -> {
			Animation animation = AnimationUtils.loadAnimation(v.getContext(), R.anim.button_press_animation);
			holder.itemView.startAnimation(animation);

			if (onItemClickListener != null) {
				onItemClickListener.onItemClick(v, position);
			}
		});
	}


	public int getItemCount() {
		return order.size();
	}

	static class OrderViewHolder extends RecyclerView.ViewHolder {
		private final TextView timestamp;
		private final TextView name;
//		private final ListView items;
		private final View view;

		public OrderViewHolder(@NonNull View itemView) {
			super(itemView);
			this.view = itemView;
			this.timestamp = itemView.findViewById(R.id.order_item_timestamp);
			this.name = itemView.findViewById(R.id.order_item_name);
//			this.items = itemView.findViewById(R.id.order_item_items_name);
		}

		public void bind(Order order) {
			timestamp.setText("Timestamp: " + order.getTimestampStr());
			name.setText(order.getItems().get(0).getName());

//			ArrayAdapter<Row> adapter = new ArrayAdapter<>(
//					view.getContext(),
//					android.R.layout.simple_list_item_1,
//					order.getItems()
//			);
//			items.setAdapter(adapter);
		}
	}
}
