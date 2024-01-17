package it.hu.ufs13_examproject;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import it.hu.ufs13_examproject.adapter.MenuAdapter;
import it.hu.ufs13_examproject.adapter.OrderAdapter;
import it.hu.ufs13_examproject.data.Data;
import it.hu.ufs13_examproject.data.MenuAsyncResponse;
import it.hu.ufs13_examproject.data.OrderAsyncResponse;
import it.hu.ufs13_examproject.data.Repository;
import it.hu.ufs13_examproject.data.TableAsyncResponse;
import it.hu.ufs13_examproject.model.Order;
import it.hu.ufs13_examproject.model.Table;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrdersViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrdersViewFragment extends Fragment {

	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";

	// TODO: Rename and change types of parameters
	private String mParam1;
	private String mParam2;
	private SwipeRefreshLayout refreshLayout;
	private Table table;

	public OrdersViewFragment() {
		// Required empty public constructor
	}

	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 *
	 * @param param1 Parameter 1.
	 * @param param2 Parameter 2.
	 * @return A new instance of fragment OrdersViewFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static OrdersViewFragment newInstance(String param1, String param2) {
		OrdersViewFragment fragment = new OrdersViewFragment();
		Bundle args = new Bundle();
		args.putString(ARG_PARAM1, param1);
		args.putString(ARG_PARAM2, param2);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			mParam1 = getArguments().getString(ARG_PARAM1);
			mParam2 = getArguments().getString(ARG_PARAM2);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_orders_view, container, false);
	}

	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		table = getActivity().getIntent().getSerializableExtra("table", Table.class);

		RecyclerView recyclerView = view.findViewById(R.id.orders_list);
		recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

		OrderAdapter adapter = new OrderAdapter(Data.getOrdersList());
		recyclerView.setAdapter(adapter);

		if (Data.getOrdersList().size() == 0) {
			fetchOrders(adapter);
		}

		refreshLayout = view.findViewById(R.id.orders_swipe);
		refreshLayout.setOnRefreshListener(() -> {
			Log.i("Refresh_Daily_Menu", "onRefresh called from SwipeRefreshLayout");

			fetchOrders(adapter);
		});

		adapter.setOnItemClickListener((view1, position) -> {
			AlertDialog.Builder builder = new AlertDialog.Builder(view1.getContext())
					.setTitle("Sei sicuro di rimuovere ordine?");

			builder
					.setPositiveButton("OK", (dialog, which) -> {;
						new Repository().removeItemFromOrder(Data.getOrdersList().get(position), table.getId());
						Data.getOrdersList().remove(position);
						getActivity().runOnUiThread(adapter::notifyDataSetChanged);
					})
					.setNegativeButton("Annulla", (dialog, which) -> dialog.cancel())
					.show();
		});
	}

	private void fetchOrders(OrderAdapter adapter) {
		new Repository().getOrders(table.getId(), new OrderAsyncResponse() {
			@Override
			public void processTerminated(ArrayList<Order> orders) {
				Log.d("Fetch_Orders", "Terminated");
				getActivity().runOnUiThread(adapter::notifyDataSetChanged);
				refreshLayout.setRefreshing(false);
			}

			@Override
			public void processFailed(Exception e) {
				Log.w("Fetch_Orders", e);
			}
		});
	}
}