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

        notesViewModel = ViewModelProviders.of(this).get(NotesViewModel.class);

        binding.doneNotesBtn.setOnClickListener(view -> {

            title = binding.notesTitle.getText().toString();
            subTitle = binding.notesSubTitle.getText().toString();
            notes = binding.notesData.getText().toString();

            CreateNotes(title, subTitle, notes);
        });

        binding.greenPriority.setOnClickListener(view -> {
            binding.greenPriority.setImageResource(R.drawable.ic_baseline_done_24);
            binding.yellowPriority.setImageResource(0);
            binding.redPriority.setImageResource(0);
            PRIORITY = "1";
        });

        binding.yellowPriority.setOnClickListener(view -> {
            binding.yellowPriority.setImageResource(R.drawable.ic_baseline_done_24);
            binding.greenPriority.setImageResource(0);
            binding.redPriority.setImageResource(0);
            PRIORITY = "2";
        });

        binding.redPriority.setOnClickListener(view -> {
            binding.redPriority.setImageResource(R.drawable.ic_baseline_done_24);
            binding.yellowPriority.setImageResource(0);
            binding.greenPriority.setImageResource(0);
            PRIORITY = "3";
        });



    }

    private void CreateNotes(String title, String subTitle, String notes){

        Date date = new Date();
        CharSequence sequence = DateFormat.format("MMMM d, yyyy", date.getTime());

        Notes notes1 = new Notes();
        notes1.notesTitle = title;
        notes1.notesStubTitle = subTitle;
        notes1.notes = notes;
        notes1.notesPriority = PRIORITY;
        notes1.notesDate = sequence.toString();

        notesViewModel.insertNote(notes1);

        Toast.makeText(this, "Notes Created Successfully", Toast.LENGTH_SHORT).show();
        finish();
    }
}