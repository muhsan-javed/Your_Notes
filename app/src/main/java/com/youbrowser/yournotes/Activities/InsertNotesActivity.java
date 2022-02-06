package com.youbrowser.yournotes.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.Toast;

import com.youbrowser.yournotes.R;
import com.youbrowser.yournotes.databinding.ActivityInsertNotesBinding;

import com.youbrowser.yournotes.model.Notes;
import com.youbrowser.yournotes.viewModel.NotesViewModel;

import java.util.Date;

public class InsertNotesActivity extends AppCompatActivity {

    ActivityInsertNotesBinding binding;
    String title, subTitle, notes;
    NotesViewModel notesViewModel;
    String PRIORITY = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInsertNotesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Not working
        /*notesViewModel = ViewModelProviders.of(this).get(NotesViewModel.class);
        notesViewModel = new ViewModelProviderKt(this).get(NotesViewModel.class);
        notesViewModel = new ViewModelProviders(this).get(NotesViewModel.class);
        notesViewModel = getDefaultViewModelProviderFactory(this);
        NotesViewModel viewModel= new ViewModelProviders(this).get(NotesViewModel.class);*/
        // GET Views
        notesViewModel = ViewModelProviders.of(this).get(NotesViewModel.class);
        // Create Notes
        binding.doneNotesBtn.setOnClickListener(view -> {
            title = binding.notesTitle.getText().toString();
            subTitle = binding.notesSubTitle.getText().toString();
            notes = binding.notesData.getText().toString();
            // CreateNotes Method getText
            CreateNotes(title, subTitle, notes);
        });
        // Set Notes Priority
        // SetPriority Green Note is not Important Notes
        binding.greenPriority.setOnClickListener(view -> {
            binding.greenPriority.setImageResource(R.drawable.ic_baseline_done_24);
            binding.yellowPriority.setImageResource(0);
            binding.redPriority.setImageResource(0);
            PRIORITY = "1";
        });
        // SetPriority yellow Good Notes
        binding.yellowPriority.setOnClickListener(view -> {
            binding.yellowPriority.setImageResource(R.drawable.ic_baseline_done_24);
            binding.greenPriority.setImageResource(0);
            binding.redPriority.setImageResource(0);
            PRIORITY = "2";
        });
        // SetPriority Red very Important Notes
        binding.redPriority.setOnClickListener(view -> {
            binding.redPriority.setImageResource(R.drawable.ic_baseline_done_24);
            binding.yellowPriority.setImageResource(0);
            binding.greenPriority.setImageResource(0);
            PRIORITY = "3";
        });
    }

    // Create Notes
    private void CreateNotes(String title, String subTitle, String notes) {
        Date date = new Date(); // Create Date
        CharSequence sequence = DateFormat.format("MMMM d, yyyy", date.getTime()); // format
        // Create Notes or save Notes in DataBase Room
        Notes notes1 = new Notes(); // GET Notes Data
        notes1.notesTitle = title; // Set Title
        notes1.notesStubTitle = subTitle; // Set SubTitle
        notes1.notes = notes; // NotesData
        notes1.notesPriority = PRIORITY; // Set Note Priority
        notes1.notesDate = sequence.toString(); // setDate
        notesViewModel.insertNote(notes1); // Save Notes in Data in InsertNotes method
        Toast.makeText(this, "Notes Created Successfully", Toast.LENGTH_SHORT).show();// Show User Message success create notes
        finish();// create after close
    }
}