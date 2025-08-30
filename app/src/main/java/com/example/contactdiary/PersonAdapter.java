package com.example.contactdiary;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.Viwholder> {
    private ArrayList<ContactPerson> persons;
    private ContactPerson person;
    OnItemClickListener listener;
    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public PersonAdapter(ArrayList<ContactPerson>persons, OnItemClickListener listener){
        this.persons=persons;
        this.listener=listener;
    }
    @NonNull
    @Override
    public PersonAdapter.Viwholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowlayout,parent,false);
        return new Viwholder(view,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonAdapter.Viwholder holder, int position) {
        person = persons.get(position);
        holder.tvId.setText(String.valueOf(position + 1));
        holder.tvName.setText(person.getName());
        holder.tvPhone.setText(person.getPhone());
        holder.tvEmail.setText(person.getEmail());

    }
    public void updateArrayList(ArrayList<ContactPerson>persons){
        this.persons = persons;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return persons.size();
    }
    static class Viwholder extends RecyclerView.ViewHolder{
        ImageView tvImage;
        TextView tvName, tvPhone,tvEmail,tvId;

        public Viwholder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            tvId= itemView.findViewById(R.id.personId);
            tvName = itemView.findViewById(R.id.pName);
            tvPhone = itemView.findViewById(R.id.pPhone);
            tvEmail= itemView.findViewById(R.id.pEmail);
            tvImage = itemView.findViewById(R.id.pImage);
            itemView.setOnClickListener(v->{
                if(listener !=null){
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(position);
                    }

                }
            });
        }
    }
}
