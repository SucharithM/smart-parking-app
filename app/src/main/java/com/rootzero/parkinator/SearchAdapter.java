package com.rootzero.parkinator;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import org.jetbrains.annotations.NotNull;

import de.hdodenhof.circleimageview.CircleImageView;

public class SearchAdapter extends FirebaseRecyclerAdapter<SlotModel, SearchAdapter.MyViewHolder> {

    public SearchAdapter(@NonNull @NotNull FirebaseRecyclerOptions<SlotModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull @NotNull SearchAdapter.MyViewHolder holder, int position, @NonNull @NotNull SlotModel model) {
        holder.slotLocation.setText(model.getSlotLocation().toString());
        holder.slotType.setText(model.getSlotType().toString());
        holder.slotPrice.setText(model.getSlotPrice().toString());


        Glide.with(holder.img.getContext()).load(model.getSlotImage()).into(holder.img);

    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_slot,parent,false);
        return new MyViewHolder(view);
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        CircleImageView img;
        TextView slotLocation,slotType,slotPrice;
        RelativeLayout relativeLayout;
        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.relativeLayout);
            img = (CircleImageView) itemView.findViewById(R.id.img1);
            slotLocation = (TextView)itemView.findViewById(R.id.slotLocation);
            slotType = (TextView)itemView.findViewById(R.id.slotType);
            slotPrice = (TextView)itemView.findViewById(R.id.slotPrice);

            itemView.setClickable(true);
            itemView.setFocusableInTouchMode(true);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(itemView.getContext(), "Booked", Toast.LENGTH_LONG).show();
                }
            });

            relativeLayout.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {

                    Toast.makeText(itemView.getContext(), "Booked", Toast.LENGTH_LONG).show();
                }

            });

        }
    }
}
