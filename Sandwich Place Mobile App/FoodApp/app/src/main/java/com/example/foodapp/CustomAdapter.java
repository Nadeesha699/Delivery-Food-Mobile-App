package com.example.foodapp;

import static android.content.Context.MODE_PRIVATE;
import static androidx.core.content.ContextCompat.startActivity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> implements Filterable {


    private List<ListItem> itemList;
    private List<ListItem> filteredList;
    private Context context;

    ContentValues contentValues;


    public CustomAdapter(Context context, List<ListItem> itemList) {
        this.context = context;
        this.itemList = itemList;
        this.filteredList = new ArrayList<>(itemList);
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ListItem item = filteredList.get(position);
        holder.button.setText(item.getButtonText());
        holder.textView1.setText(item.getTextView1Text());
        holder.textView2.setText(item.getTextView2Text());
        holder.textView3.setText(item.getTextView3Text());
        holder.imageView.setImageResource(item.getImageResource());
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final CharSequence[] branch = {"Matara","Galle"};

                AlertDialog.Builder alert = new AlertDialog.Builder(context, R.style.chooses);

                alert.setTitle("CHOOSE THE BRANCH");

                alert.setSingleChoiceItems(branch, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(branch[which]=="Matara")

                        {
                            String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                            String currentTime = new SimpleDateFormat("HH:mm:ss",Locale.getDefault()).format(new Date());

                            Intent intent = new Intent(context,OrdersPending.class);
                            intent.putExtra("orderTime",currentTime);
                            intent.putExtra("orderDate",currentDate);
                            intent.putExtra("Location","Matara");
                            intent.putExtra("itemName",item.getTextView1Text());
                            intent.putExtra("price",Integer.parseInt(item.getTextView3Text()));
                            startActivity(context,intent,null);
                        }

                        else if (branch[which]=="Galle")

                        {
                            String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                            String currentTime = new SimpleDateFormat("HH:mm:ss",Locale.getDefault()).format(new Date());

                            Intent intent = new Intent(context,OrdersPending.class);
                            intent.putExtra("orderTime",currentTime);
                            intent.putExtra("orderDate",currentDate);
                            intent.putExtra("Location","Galle");
                            intent.putExtra("itemName",item.getTextView1Text());
                            intent.putExtra("price",Integer.parseInt(item.getTextView3Text()));
                            startActivity(context,intent,null);

                        }
                    }
                }).show();

               }


        });
    }




    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Button button;
        TextView textView1;
        TextView textView2;
        TextView textView3;
        ImageView imageView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            button = itemView.findViewById(R.id.Button1);
            textView1 = itemView.findViewById(R.id.txt_delprice);
            textView2 = itemView.findViewById(R.id.textView2);
            textView3 = itemView.findViewById(R.id.textView3);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String filterPattern = charSequence.toString().toLowerCase().trim();
                filteredList.clear();
                if (filterPattern.isEmpty()) {
                    filteredList.addAll(itemList);
                } else {
                    for (ListItem item : itemList) {
                        if (item.getButtonText().toLowerCase().contains(filterPattern) ||
                                item.getTextView1Text().toLowerCase().contains(filterPattern) ||
                                item.getTextView2Text().toLowerCase().contains(filterPattern) ||
                                item.getTextView3Text().toLowerCase().contains(filterPattern)) {
                            filteredList.add(item);
                        }
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                notifyDataSetChanged();
            }
        };
    }
}
