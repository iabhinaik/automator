package com.aaars.b;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import static com.aaars.b.Root.USER_ID;

public class ModuleFragment extends Fragment {

    private List<ModuleData> data;
    private Map<String, Module> md;
    private RecyclerView rv;
    Boolean[] b;
    int i = 0;

    public ModuleFragment() { }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_module, container, false);
        RecyclerView rv = view.findViewById(R.id.rv);

        b = new Boolean[100];

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);

        initializeData();

        RVAdapter adapter = new RVAdapter(data);
        rv.setAdapter(adapter);

        return view;
    }

    private void initializeData(){
        data = new ArrayList<>();


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dr = database.getInstance().getReference().child("users").child(USER_ID).child("modules");
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), "Failed to load post.",
                        Toast.LENGTH_SHORT).show();
            }
        };
        dr.addValueEventListener(listener);

        try {
            data = new ArrayList<>();
            data.add(new ModuleData("Device Security", "Set volume to min through SMS", R.drawable.alpha, ContextCompat.getColor(getContext(), R.color.red), 9));
            data.add(new ModuleData("Safety", "Send SMS with device location when battery is critically low", R.drawable.alpha, ContextCompat.getColor(getContext(), R.color.orange), 1));
            data.add(new ModuleData("Wifi", "Turn Wi-Fi OFF at specific time to save battery life", R.drawable.network, ContextCompat.getColor(getContext(), R.color.cardGreen), 2));
            data.add(new ModuleData("Device Security", "Turns OFF Wi-Fi through SMS", R.drawable.alpha, ContextCompat.getColor(getContext(), R.color.red), 7));
            data.add(new ModuleData("Wifi", "Turn Wi-Fi ON at specific time to connect to the Internet", R.drawable.network, ContextCompat.getColor(getContext(), R.color.cardGreen), 3));
            data.add(new ModuleData("Location", "Log time spent at specific location", R.drawable.alpha, ContextCompat.getColor(getContext(), R.color.yellove), 4));
            data.add(new ModuleData("Device Security", "Turns On Wi-Fi through SMS", R.drawable.alpha, ContextCompat.getColor(getContext(), R.color.red), 6));
            data.add(new ModuleData("Email", "Sends you Quote of the Day to your email address", R.drawable.mail, ContextCompat.getColor(getContext(), R.color.cyan), 5));
            data.add(new ModuleData("Safety", "Sends location to selected contacts on notification tap", R.drawable.alpha, ContextCompat.getColor(getContext(), R.color.orange), 10));
            data.add(new ModuleData("Device Security", "Recover lost device through SMS", R.drawable.alpha, ContextCompat.getColor(getContext(), R.color.red), 15));
            data.add(new ModuleData("Email", "Sends you a comic snippet to your email address", R.drawable.mail, ContextCompat.getColor(getContext(), R.color.cyan), 11));
            data.add(new ModuleData("Device Security", "Set volume to max through SMS", R.drawable.alpha, ContextCompat.getColor(getContext(), R.color.red), 8));
            data.add(new ModuleData("Email", "Sends bundled RSS feed to your email address", R.drawable.mail, ContextCompat.getColor(getContext(), R.color.cyan), 13));
            data.add(new ModuleData("Device Security", "Locate your phone through SMS", R.drawable.alpha, ContextCompat.getColor(getContext(), R.color.red), 12));
            data.add(new ModuleData("Wifi", "Turn Wi-Fi ON at specific location for seamless switch", R.drawable.network, ContextCompat.getColor(getContext(), R.color.cardGreen), 14));
        }
        catch(Exception e) {

        }

        }

    private class RVAdapter extends RecyclerView.Adapter<RVAdapter.DataViewHolder>{

        List<ModuleData> data;
        RVAdapter(List<ModuleData> persons){
            this.data = persons;
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        @Override
        public DataViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_discover, viewGroup, false);
            DataViewHolder pvh = new DataViewHolder(v, viewGroup.getContext());
            return pvh;
        }

        @Override
        public void onBindViewHolder(DataViewHolder dataViewHolder, int i) {
            try {
                dataViewHolder.header.setText(data.get(i).header);
                dataViewHolder.desc.setText(data.get(i).desc);
                dataViewHolder.img.setImageResource(data.get(i).img);
                dataViewHolder.cv.setCardBackgroundColor(data.get(i).clr);
            }
            catch (Exception e){}
        }

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
        }



        public class DataViewHolder extends RecyclerView.ViewHolder {
            CardView cv;
            TextView header;
            TextView desc;
            ImageView img;

            DataViewHolder(View itemView, final Context context) {
                super(itemView);
                cv = itemView.findViewById(R.id.cv);
                header = itemView.findViewById(R.id.header);
                desc = itemView.findViewById(R.id.desc);
                img = itemView.findViewById(R.id.img);

                cv.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(getContext(),ModuleDesign.class);
                        i.putExtra("MODULE_ID",data.get(getAdapterPosition()).moduleid);
                        i.putExtra("USER_ID",Root.USER_ID);
                        startActivity(i);
                    }
                });
            }
        }
    }
}

class ModuleData {
    String header;
    String desc;
    int img;
    int clr;
    int moduleid;

    ModuleData(String header, String desc, int img, int clr, int moduleid) {
        this.header = header;
        this.desc = desc;
        this.img = img;
        this.clr = clr;
        this.moduleid = moduleid;


    }
}
