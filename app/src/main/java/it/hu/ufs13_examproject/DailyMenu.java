package it.hu.ufs13_examproject;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import it.hu.ufs13_examproject.data.Data;
import it.hu.ufs13_examproject.data.MenuAsyncResponse;
import it.hu.ufs13_examproject.data.Repository;
import it.hu.ufs13_examproject.model.Table;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DailyMenu#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DailyMenu extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private SwipeRefreshLayout refreshLayout;

    public DailyMenu() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DailyMenu.
     */
    // TODO: Rename and change types and number of parameters
    public static DailyMenu newInstance(String param1, String param2) {
        DailyMenu fragment = new DailyMenu();
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
        return inflater.inflate(R.layout.fragment_daily_menu, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Table table = getActivity().getIntent().getSerializableExtra("table", Table.class);

        RecyclerView list = view.findViewById(R.id.daily_list);
        list.setLayoutManager(new LinearLayoutManager(view.getContext()));

        MenuAdapter adapter = new MenuAdapter(Data.getMenu_daily(), table.getId());
        list.setAdapter(adapter);

        if (Data.getMenu_daily().size() == 0) {
            fetchData(adapter);
        }

        refreshLayout = view.findViewById(R.id.daily_menu_swipe);
        refreshLayout.setOnRefreshListener(() -> {
            Log.i("Refresh_Daily_Menu", "onRefresh called from SwipeRefreshLayout");

            fetchData(adapter);
        });
    }

    private void fetchData(MenuAdapter adapter) {
        new Repository().getMenu("menu_daily", new MenuAsyncResponse() {
            @Override
            public void processTerminated(ArrayList<Object> daily_menu) {
                Log.d("Fetch_Menu_Daily", "Terminated");
                getActivity().runOnUiThread(adapter::notifyDataSetChanged);
                refreshLayout.setRefreshing(false);
            }

            @Override
            public void processFailed(Exception e) {
                Log.w("Fetch_Menu_Daily", e);
            }
        });
    }
}