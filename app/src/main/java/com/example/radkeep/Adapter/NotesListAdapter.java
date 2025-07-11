package com.example.radkeep.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.radkeep.Models.Notes;
import com.example.radkeep.NotesClickListener;
import com.example.radkeep.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NotesListAdapter extends RecyclerView.Adapter <NotesViewHolder>{
    Context context;
    List<Notes> list;

    NotesClickListener listener;

    public NotesListAdapter(Context context, List<Notes> list, NotesClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NotesViewHolder(LayoutInflater.from(context).inflate(R.layout.notes_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {

        holder.textView_title.setText(list.get(position).getTitle());
        holder.textView_title.setSelected(true);

        holder.textView_notes.setText(list.get(position).getNotes());

        holder.textView_date.setText(list.get(position).getDate());
        holder.textView_date.setSelected(true);

        if (list.get(position).isPinned()){
            holder.imageView_pin.setImageResource(R.drawable.pin);
        }
        else{
            holder.imageView_pin.setImageResource(0);
        }
        int color_code = getRandomColor();
        holder.notes_cantainer.setCardBackgroundColor(holder.itemView.getResources().getColor(color_code, null));



        holder.notes_cantainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(list.get(holder.getAdapterPosition()));

            }
        });
        holder.notes_cantainer.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                listener.onLongClick(list.get(holder.getAdapterPosition()), holder.notes_cantainer);
                return true;
            }
        });

    }
    private int getRandomColor() {
        List <Integer> colorCode = new ArrayList<>();
        colorCode.add(R.color.red);
        colorCode.add(R.color.green);
        colorCode.add(R.color.purple);
        colorCode.add(R.color.blue);
        colorCode.add(R.color.yellow);

        Random random = new Random();
        int random_color = random.nextInt(colorCode.size());
        return  colorCode.get(random_color);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void filterList(List<Notes> filteredList){
        list = filteredList;
        notifyDataSetChanged();
    }
}

class NotesViewHolder extends RecyclerView.ViewHolder {
    CardView notes_cantainer;
    TextView textView_title, textView_notes, textView_date;
    ImageView imageView_pin;


    public NotesViewHolder(@NonNull View itemView) {
        super(itemView);

        notes_cantainer = itemView.findViewById(R.id.notes_container);
        textView_title = itemView.findViewById(R.id.textView_title);
        textView_notes = itemView.findViewById(R.id.textView_notes);
        textView_date = itemView.findViewById(R.id.textView_date);
        imageView_pin =itemView.findViewById(R.id.imageView_pin);
    }
}
