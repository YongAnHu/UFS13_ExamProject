package it.hu.ufs13_examproject.adapter;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import it.hu.ufs13_examproject.R;
import it.hu.ufs13_examproject.data.Repository;
import it.hu.ufs13_examproject.model.Item;
import it.hu.ufs13_examproject.model.Table;

public class MenuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
	private final ArrayList<Object> data;
	private static String table_id;
	private OnItemClickListener onItemClickListener;

	public MenuAdapter(ArrayList<Object> data, String table_id) {
		this.data = data;
		this.table_id = table_id;
	}

	public void setOnItemClickListener(OnItemClickListener listener) {
		this.onItemClickListener = listener;
	}

	@NonNull
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		if (viewType == 1)
			return new MenuHeader(LayoutInflater.from(parent.getContext())
					.inflate(R.layout.menu_layout_item_header, parent, false));
		else
			return new MenuList(LayoutInflater.from(parent.getContext())
					.inflate(R.layout.menu_layout_item, parent, false));
	}

	@Override
	public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
		Object data = this.data.get(position);

		if (holder instanceof MenuHeader && data instanceof String) {
			MenuHeader header = (MenuHeader) holder;
			header.bind(data.toString());
		} else if (holder instanceof MenuList && data instanceof Item) {
			MenuList list = (MenuList) holder;
			list.bind(((Item) data));

			list.itemView.setOnClickListener(v -> {
				Animation animation = AnimationUtils.loadAnimation(v.getContext(), R.anim.button_press_animation);
				holder.itemView.startAnimation(animation);

				if (onItemClickListener != null) {
					onItemClickListener.onItemClick(v, position);
				}
			});
		}
	}

	public int getItemCount() {
		return data.size();
	}

	@Override
	public int getItemViewType(int position) {
		return data.get(position) instanceof String ? 1 : 2;
	}

	static class MenuHeader extends RecyclerView.ViewHolder {
		private final TextView header;

		public MenuHeader(@NonNull View itemView) {
			super(itemView);
			this.header = itemView.findViewById(R.id.menu_item_header);
		}

		public void bind(String menu_item) {
			header.setText(menu_item);
		}
	}

	static class MenuList extends RecyclerView.ViewHolder {
		private final TextView name;
		private final TextView description;
		private final ImageView icon;
		private final Button btn_add;
		private final Button btn_remove;

		public MenuList(@NonNull View itemView) {
			super(itemView);
			this.name = itemView.findViewById(R.id.menu_item_name);
			this.description = itemView.findViewById(R.id.menu_item_description);
			this.icon = itemView.findViewById(R.id.menu_item_image_view);
			this.btn_add = itemView.findViewById(R.id.menu_item_btn_add);
			this.btn_remove = itemView.findViewById(R.id.menu_item_btn_remove);
		}

		public void bind(Item menu_item) {
			name.setText(menu_item.getName());
			description.setText(menu_item.getDescription());
//            icon.setImageResource(menu_item.image);

			HashMap<Item, Integer> order = new HashMap<>();

			btn_add.setOnClickListener(v -> {
                Log.d("Menu Item Add", menu_item.getName());

				new Repository().addItemToOrder(menu_item, table_id);
                Toast.makeText(v.getContext(), "Item added to order", Toast.LENGTH_LONG).show();
			});
//			btn_remove.setOnClickListener(v -> {
//				Log.d("Menu Item Remove", menu_item.getName());
//
//                String msg = "Quantity reduced by 1";
//
//                if (order.get(menu_item) != null) {
//	                new Repository().removeItemFromOrder(menu_item, table_id);
//				} else {
//                    msg = "The item was not present in the order";
//                }
//
//                Toast.makeText(v.getContext(), msg, Toast.LENGTH_LONG).show();
//			});
		}
	}
}
