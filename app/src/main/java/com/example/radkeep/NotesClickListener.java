package com.example.radkeep;

import androidx.cardview.widget.CardView;

import com.example.radkeep.Models.Notes;

public interface NotesClickListener {
    void onClick(Notes notes);
    void onLongClick(Notes notes, CardView cardView);
}
