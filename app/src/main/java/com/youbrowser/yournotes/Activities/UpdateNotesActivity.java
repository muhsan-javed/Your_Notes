package com.youbrowser.yournotes.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.youbrowser.yournotes.R;
import com.youbrowser.yournotes.databinding.ActivityUpdateNotesBinding;
import com.youbrowser.yournotes.model.Notes;
import com.youbrowser.yournotes.viewModel.NotesViewModel;

import java.util.Date;

public class UpdateNotesActivity extends AppCompatActivity {

    ActivityUpdateNotesBinding binding;
    String PRIORITY = "1";
    int iId;
    String sTitle, sSubTitle, sNotes, sPriority;
    NotesViewModel notesViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateNotesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        notesViewModel = ViewModelProviders.of(this).get(NotesViewModel.class);
        // GET DATA FORM INTENT
        iId = getIntent().getIntExtra("id", 0);
        sTitle = getIntent().getStringExtra("title");
        sSubTitle = getIntent().getStringExtra("subtitle");
        sNotes = getIntent().getStringExtra("notesData");
        sPriority = getIntent().getStringExtra("priority");
        // UPDATE DATA TITLE SUBTITLE and NOTE_CONTENT
        binding.UpTitle.setText(sTitle);
        binding.UpSubTitle.setText(sSubTitle);
        binding.UpNotes.setText(sNotes);
        // Priority Checking
        switch (sPriority) {
            case "1":
                binding.UpGreenPriority.setImageResource(R.drawable.green_shape);
                break;
            case "2":
                binding.UpYellowPriority.setImageResource(R.drawable.yellow);
                break;
            case "3":
                binding.UpRedPriority.setImageResource(R.drawable.red_shape);
                break;
        }
        // Priority setOnClickListener
        binding.UpGreenPriority.setOnClickListener(view -> {
            binding.UpGreenPriority.setImageResource(R.drawable.ic_baseline_done_24);
            binding.UpYellowPriority.setImageResource(0);
            binding.UpRedPriority.setImageResource(0);
            PRIORITY = "1";
        });
        // Priority setOnClickListener
        binding.UpYellowPriority.setOnClickListener(view -> {
            binding.UpYellowPriority.setImageResource(R.drawable.ic_baseline_done_24);
            binding.UpGreenPriority.setImageResource(0);
            binding.UpRedPriority.setImageResource(0);
            PRIORITY = "2";
        });
        // Priority setOnClickListener
        binding.UpRedPriority.setOnClickListener(view -> {
            binding.UpRedPriority.setImageResource(R.drawable.ic_baseline_done_24);
            binding.UpYellowPriority.setImageResource(0);
            binding.UpGreenPriority.setImageResource(0);
            PRIORITY = "3";
        });
        // Update Note Button
        binding.updateNotesBtn.setOnClickListener(view -> {
            String title = binding.UpTitle.getText().toString();
            String subTitle = binding.UpSubTitle.getText().toString();
            String notesData = binding.UpNotes.getText().toString();

            UpdateNotes(title, subTitle, notesData);
        });

    }

    // Update Function
    private void UpdateNotes(String title, String subTitle, String notesData) {
        Date date = new Date();
        CharSequence sequence = DateFormat.format("MMMM d, yyyy", date.getTime());

        Notes updateNotes = new Notes();
        updateNotes.id = iId;
        updateNotes.notesTitle = title;
        updateNotes.notesStubTitle = subTitle;
        updateNotes.notes = notesData;
        updateNotes.notesPriority = PRIORITY;
        updateNotes.notesDate = sequence.toString();

        notesViewModel.updateNote(updateNotes);
        Toast.makeText(this, "Notes UPDATE Successfully", Toast.LENGTH_SHORT).show();
        finish();
    }

    // Menu Add Function
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.delete_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // Add bottomSheetDialog And Buttons Action
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.delete_menu) {
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(UpdateNotesActivity.this, R.style.BottomSheetStyle);

            View view = LayoutInflater.from(UpdateNotesActivity.this)
                    .inflate(R.layout.delete_bottom_sheet, (LinearLayout) findViewById(R.id.bottomSheet));

            bottomSheetDialog.setContentView(view);

            TextView yes, no;
            yes = view.findViewById(R.id.yesDelete);
            no = view.findViewById(R.id.noDelete);

            yes.setOnClickListener(view1 -> {
                notesViewModel.deleteNote(iId);
                finish();
            });

            no.setOnClickListener(view1 -> bottomSheetDialog.dismiss());
            bottomSheetDialog.show();
        }

        return super.onOptionsItemSelected(item);
    }
}