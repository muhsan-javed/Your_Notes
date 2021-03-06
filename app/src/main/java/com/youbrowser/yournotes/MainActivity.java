package com.youbrowser.yournotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import com.youbrowser.yournotes.Activities.InsertNotesActivity;
import com.youbrowser.yournotes.adapter.NotesAdapter;
import com.youbrowser.yournotes.databinding.ActivityMainBinding;
import com.youbrowser.yournotes.model.Notes;
import com.youbrowser.yournotes.viewModel.NotesViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    NotesViewModel notesViewModel;
    NotesAdapter adapter;
    List<Notes> filterNotesAllLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // GET Views
        notesViewModel = ViewModelProviders.of(this).get(NotesViewModel.class);
        // noFilter Icon Set
        binding.noFilter.setBackgroundResource(R.drawable.filter_selected_shape);
        // noFilter TextView-> setOnclickListener
        binding.noFilter.setOnClickListener(view -> {
            loadData(0);
            binding.highToLow.setBackgroundResource(R.drawable.filter_nu_shape);
            binding.lowToHigh.setBackgroundResource(R.drawable.filter_nu_shape);
            binding.noFilter.setBackgroundResource(R.drawable.filter_selected_shape);
        });
        // HighToLow TextView-> setOnclickListener
        binding.highToLow.setOnClickListener(view -> {
            loadData(1);
            binding.noFilter.setBackgroundResource(R.drawable.filter_nu_shape);
            binding.lowToHigh.setBackgroundResource(R.drawable.filter_nu_shape);
            binding.highToLow.setBackgroundResource(R.drawable.filter_selected_shape);
        });
        // lowToHigh TextView-> setOnclickListener
        binding.lowToHigh.setOnClickListener(view -> {
            loadData(2);
            binding.noFilter.setBackgroundResource(R.drawable.filter_nu_shape);
            binding.lowToHigh.setBackgroundResource(R.drawable.filter_nu_shape);
            binding.lowToHigh.setBackgroundResource(R.drawable.filter_selected_shape);
        });
        // NewNotesButton GO To Next Activity
        binding.newNotesBtn.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), InsertNotesActivity.class)));
//        notesViewModel.getAllNotes.observe(this, notes -> {
//            binding.notesRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
//            adapter = new NotesAdapter(MainActivity.this, notes);
//            binding.notesRecyclerView.setAdapter(adapter);
//        });
        // GETing Notes
        notesViewModel.getAllNotes.observe(this, new Observer<List<Notes>>() {
            @Override
            public void onChanged(List<Notes> notes) {
                setAdapter(notes);
                filterNotesAllLists = notes; // Filtering
            }
        });
    }

    // setAdapter and set Layout or setRecycler a adapter
    public void setAdapter(List<Notes> notes) {
        binding.notesRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        adapter = new NotesAdapter(MainActivity.this, notes);
        binding.notesRecyclerView.setAdapter(adapter);
    }

    // This Function checking filter
    private void loadData(int i) {
        if (i == 0) {
            notesViewModel.getAllNotes.observe(this, notes -> {
                setAdapter(notes);
                filterNotesAllLists = notes;
            });
        } else if (i == 1) {
            notesViewModel.highToLow.observe(this, notes -> {
                setAdapter(notes);
                filterNotesAllLists = notes;

            });
        } else if (i == 2) {
            notesViewModel.lowToHigh.observe(this, notes -> {
                setAdapter(notes);
                filterNotesAllLists = notes;

            });
        }
    }

    // Add SearchView Methods or add menu Bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_notes, menu);

        MenuItem menuItem = menu.findItem(R.id.app_bar_search);

        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search Notes here...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                NotesFilter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    // Filter SearchView
    private void NotesFilter(String newText) {
        // Log.e("@@@@@", "NotesFilter: "+ newText);
        ArrayList<Notes> FilterName = new ArrayList<>();
        for (Notes notes : this.filterNotesAllLists) {
            if (notes.notesTitle.contains(newText) || notes.notesStubTitle.contains(newText)) {
                FilterName.add(notes);
            }
        }
        this.adapter.searchNotes(FilterName);
    }
}