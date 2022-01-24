package com.youbrowser.yournotes.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.youbrowser.yournotes.Activities.UpdateNotesActivity;
import com.youbrowser.yournotes.MainActivity;
import com.youbrowser.yournotes.R;
import com.youbrowser.yournotes.model.Notes;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.notesViewHolder> {

   // Context context;
    MainActivity mainActivity;
    List<Notes> notesList;
    List<Notes> allNotesItems;

    public NotesAdapter(MainActivity mainActivity, List<Notes> notesList) {
        this.mainActivity = mainActivity;
        //this.context = context;
        this.notesList = notesList;
        allNotesItems = new ArrayList<>(notesList);
    }

    public void searchNotes(List<Notes> filteredName){
        this.notesList = filteredName;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public notesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(mainActivity).inflate(R.layout.item_notes, parent, false);
        return new notesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull notesViewHolder holder, int position) {

        Notes note = notesList.get(position);

        holder.titleTV.setText(note.notesTitle);
        holder.subTitleTV.setText(note.notesStubTitle);
        holder.dateTimeTV.setText(note.notesDate);

        switch (note.notesPriority) {
            case "1":
                holder.notesViewPriority.setImageResource(R.drawable.green_shape);
                break;
            case "2":
                holder.notesViewPriority.setImageResource(R.drawable.yellow);
                break;
            case "3":
                holder.notesViewPriority.setImageResource(R.drawable.red_shape);
                break;
        }

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(mainActivity, UpdateNotesActivity.class);

            intent.putExtra("id",note.id);
            intent.putExtra("title",note.notesTitle);
            intent.putExtra("subtitle",note.notesStubTitle);
            intent.putExtra("notesData",note.notes);
            intent.putExtra("priority",note.notesPriority);

            mainActivity.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    static class notesViewHolder extends RecyclerView.ViewHolder{
        TextView titleTV, subTitleTV,dateTimeTV;
        ImageView notesViewPriority;
        public notesViewHolder(@NonNull View itemView) {
            super(itemView);

            titleTV= itemView.findViewById(R.id.TitleTV);
            subTitleTV= itemView.findViewById(R.id.subTitleTV);
            dateTimeTV= itemView.findViewById(R.id.dateTimeTV);
            notesViewPriority= itemView.findViewById(R.id.notesViewPriority);

        }
    }
}
