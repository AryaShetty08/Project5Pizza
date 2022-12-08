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

/**
 * Adapter class for Recycler View in PizzaOrdering Activity
 * Defines certain variables that the each row of the recycler view holds
 * Also defines certain listeners for activity to use when the user selects
 * pizza they want to purchase
 * @author Arya Shetty, John Greaney-Cheng
 */
public class ProgramAdapter extends RecyclerView.Adapter<ProgramAdapter.ViewHolder> {

    Context context;
    String[] programNameList;
    String[] programDescriptionList;
    int[] images;
    public int singleitem_selection_position = 0;
    OnItemClickListener listener;

    /**
     * Defines item click listener for recycler view
     * Which has the String s that is the pizza the user clicked on
     */
    public interface OnItemClickListener {
        /**
         * Method in recycler's click listener
         * @param s, string for the pizza name
         */
        void onItemClick(String s);
    }

    /**
     * The constructor for this adapter that will be called in PizzaOrdering Activity
     * for the recycler to use
     * @param context, the context of the activity the adapter is in
     * @param programNameList, the list of strings that are the names of the pizzas
     * @param programDescriptionList, a quick list that decribes the pizzas
     * @param images, a list of images for each specific pizza
     * @param listener, the listener for users to be able to interact with recycler when clicking
     *
     */
    public ProgramAdapter(Context context, String[] programNameList, String[] programDescriptionList, int[] images, OnItemClickListener listener){
        this.context = context;
        this.programNameList = programNameList;
        this.programDescriptionList = programDescriptionList;
        this.images = images;
        this.listener = listener;
    }

    /**
     * Mandatory method of program adapter that defines the view and layout of each row
     * In recycler view
     * In this case it takes the form of the single_item xml file layout
     * @param parent, parent of view
     * @param viewType
     * @return ProgramAdapter.ViewHolder, this contains the specific row view
     */
    @NonNull
    @Override
    public ProgramAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.single_item, parent, false);

        return new ViewHolder(view);
    }

    /**
     * Instead of creating a new view,
     * Bind view holder creates a new view with an existing view when scrolling
     * This in turn saves space compared to list view
     * @param holder
     * @param position, the integer position of the row view user is on
     */
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

    /**
     * Shows how long the Recycler is
     * @return int, length of programNameList array
     */
    @Override
    public int getItemCount() {
        return programNameList.length;
    }

    /**
     * ViewHolder class for Recycler View that defines the layout
     * Of each row throughout the Recycler View
     * @author Arya Shetty, John Greaney-Cheng
     */
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView rowName;
        TextView rowDescription;
        ImageView rowImage;
        LinearLayout rowItem;

        /**
         * Initializes the variables of the single_item xml file
         * @param itemView, one View of the Recycler
         */
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rowName = itemView.findViewById(R.id.textView1);
            rowDescription = itemView.findViewById(R.id.textView2);
            rowImage = itemView.findViewById(R.id.imageView);
            rowItem = itemView.findViewById(R.id.rowitem);
        }

        /**
         * Sets up the listener and updates position when user clicks one view
         * @param s, string that represents the pizza
         * @param listener, item click listener that was defined earlier
         */
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

    /**
     * Updates the position that user is selected on so the adapter knows
     * which Recycler cell to highlight
     * @param adapterPosition, number position of specific row
     */
    public void setSingleSelection(int adapterPosition){
        if(adapterPosition == RecyclerView.NO_POSITION) return;

        notifyItemChanged(singleitem_selection_position);
        singleitem_selection_position = adapterPosition;
        notifyItemChanged(singleitem_selection_position);
    }
}
