package it.hu.ufs13_examproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import it.hu.ufs13_examproject.adapter.TableAdapter;
import it.hu.ufs13_examproject.data.Data;
import it.hu.ufs13_examproject.data.Repository;
import it.hu.ufs13_examproject.data.TableAsyncResponse;
import it.hu.ufs13_examproject.databinding.ActivityMainBinding;
import it.hu.ufs13_examproject.model.Table;

public class MainActivity extends AppCompatActivity {
    protected ActivityMainBinding binding;
    private ArrayList<Table> table_list = new ArrayList<>();
    private TableAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        setSupportActionBar(binding.mainToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        adapter = new TableAdapter(MainActivity.this, table_list);
        binding.mainGridTables.setAdapter((ListAdapter) adapter);
        binding.mainGridTables.setOnItemClickListener((parent, view, position, id) -> {
            Intent itn = new Intent(MainActivity.this, MenuViewActivity.class);

            if (table_list.get(position).getNum() != 0) {
                itn.putExtra("table", table_list.get(position));
                startActivity(itn);
            }
            else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this)
                        .setTitle("Inserisci numero persone");
                final EditText input = new EditText(this);
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                builder.setView(input);

                builder.setPositiveButton("OK", (dialog, which) -> {
                    int table_num = Integer.parseInt(input.getText().toString());
                    if (table_num == 0) table_num = 1;

                    table_list.get(position).setNum(table_num);
                    new Repository().updateTable(table_list.get(position), "tables");

                    itn.putExtra("table", table_list.get(position));
                    startActivity(itn);
                });
                builder.setNegativeButton("Annulla", (dialog, which) -> {
                    dialog.cancel();
                });

                builder.show();
            }
        });

        fillTables();
    }

    private void fillTables() {
        new Repository().getTables(new TableAsyncResponse() {
            @Override
            public void processTerminated(ArrayList<Table> tables) {
                Log.d("Fetch_Menu_Daily", "Terminated");

                table_list.clear();
                table_list.addAll(tables);
                runOnUiThread(adapter::notifyDataSetChanged);
            }

            @Override
            public void processFailed(Exception e) {
                Log.w("Fetch_Menu_Daily_Error", e);
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
        // Handle item selection
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
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
                        })
                        .setNegativeButton("Annulla", (dialog, which) -> dialog.cancel())
                        .show();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}