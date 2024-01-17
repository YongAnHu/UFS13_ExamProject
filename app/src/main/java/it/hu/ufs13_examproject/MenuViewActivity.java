package it.hu.ufs13_examproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import it.hu.ufs13_examproject.data.Repository;
import it.hu.ufs13_examproject.model.Table;

public class MenuViewActivity extends AppCompatActivity {
    private Table table;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_view);

        table = getIntent().getSerializableExtra("table", Table.class);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(table.getName());

        FragmentContainerView fragCont = findViewById(R.id.main_fragment_containter);
        BottomNavigationView bottomNav = findViewById(R.id.main_bottom_nav);

        bottomNav.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_home: return showFragment(Menu.class);
                case R.id.nav_daily: return showFragment(DailyMenu.class);
                case R.id.nav_order: return showFragment(OrdersViewFragment.class);

                default:
                    Log.w("Fragment_Error", "Not fragment!");
                    return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.dropdown_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MenuViewActivity.this);
        final EditText input = new EditText(this);

        // Handle item selection
        switch (item.getItemId()) {
            case R.id.drop_add_table:
                // This is a method, that performs logging user out
                builder.setTitle("AGGIUNGI UN TAVOLO");
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                input.setHint("Nome Tavolo");
                builder.setView(input);

                builder
                        .setPositiveButton("Aggiungi", (dialog, which) -> {
                            new Repository().addTableToDB(input.getText().toString());
                        })
                        .setNegativeButton("Annulla", (dialog, which) -> dialog.cancel())
                        .show();

                return true;

            case R.id.drop_remove_table:
                // This is a method, that performs logging user out
                builder.setTitle("RIMUOVI UN TAVOLO");
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                input.setHint("Nome Tavolo");
                builder.setView(input);

                builder
                        .setPositiveButton("Rimuovi", (dialog, which) -> {
                            new Repository().removeTableFromDB(input.getText().toString());
                            if (table.getName().equalsIgnoreCase(input.getText().toString())) {
                                finish();
                            }
                        })
                        .setNegativeButton("Annulla", (dialog, which) -> dialog.cancel())
                        .show();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected  Boolean showFragment(Class <? extends Fragment> fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.main_fragment_containter, fragment, null)
                .setReorderingAllowed(true)
                .commit();
        return true;
    }
}