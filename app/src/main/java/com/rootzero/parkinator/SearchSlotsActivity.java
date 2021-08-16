package com.rootzero.parkinator;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class SearchSlotsActivity extends AppCompatActivity {
    RecyclerView recView;
    SearchAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        recView = (RecyclerView) findViewById(R.id.recView);
        recView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<SlotModel> options =
                new FirebaseRecyclerOptions.Builder<SlotModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("slotDetails"), SlotModel.class)
                        .build();

        adapter = new SearchAdapter(options);
        recView.setAdapter(adapter);
    }
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.searchmenu,menu);

        MenuItem item = menu.findItem(R.id.search);

        SearchView searchView = (SearchView)item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                processSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                processSearch(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void processSearch(String query) {
        FirebaseRecyclerOptions<SlotModel> options =
                new FirebaseRecyclerOptions.Builder<SlotModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("slotDetails").orderByChild("slotLocation").startAt(query).endAt(query+"utf-8"), SlotModel.class)
                        .build();

        adapter = new SearchAdapter(options);
        adapter.startListening();
        recView.setAdapter(adapter);

    }

}
