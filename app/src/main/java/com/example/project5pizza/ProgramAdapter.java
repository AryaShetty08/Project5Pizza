package com.example.project5pizza;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class ProgramAdapter extends RecyclerView.Adapter<ProgramAdapter.ViewHolder> {

    Context context;
    String[] programNameList;
    String[] programDescriptionList;
    int[] images;
    public int singleitem_selection_position = 0;
    OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(String s);
    }

    public ProgramAdapter(Context context, String[] programNameList, String[] programDescriptionList, int[] images, OnItemClickListener listener){
        this.context = context;
        this.programNameList = programNameList;
        this.programDescriptionList = programDescriptionList;
        this.images = images;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ProgramAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.single_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProgramAdapter.ViewHolder holder, int position) {
        holder.rowName.setText(programNameList[position]);
        holder.rowDescription.setText(programDescriptionList[position]);
        holder.rowImage.setImageResource(images[position]);

        if(singleitem_selection_position == position){
            holder.itemView.setBackgroundColor(ContextCompat.
                    getColor(context, R.color.purple_200));
        }
        else{
            holder.itemView.setBackgroundColor(Color.TRANSPARENT);
        }
        holder.bind(programNameList[position], listener);
    }

    @Override
    public int getItemCount() {
        return programNameList.length;
    }

    public int getSingleitem_selection_position() {
        return singleitem_selection_position;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView rowName;
        TextView rowDescription;
        ImageView rowImage;
        LinearLayout rowItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rowName = itemView.findViewById(R.id.textView1);
            rowDescription = itemView.findViewById(R.id.textView2);
            rowImage = itemView.findViewById(R.id.imageView);
            rowItem = itemView.findViewById(R.id.rowitem);
        }

        public void bind (String s, OnItemClickListener listener){
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick (View view){
                    listener.onItemClick(s);
                    setSingleSelection(getAdapterPosition());
                }
            });
        }
    }

    public void setSingleSelection(int adapterPosition){
        if(adapterPosition == RecyclerView.NO_POSITION) return;

        notifyItemChanged(singleitem_selection_position);
        singleitem_selection_position = adapterPosition;
        notifyItemChanged(singleitem_selection_position);
    }
}
