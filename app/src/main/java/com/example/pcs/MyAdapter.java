package com.example.pcs;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<User> list;
    public MyAdapter(Context context, ArrayList<User> list) {
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.userentry,parent,false);
        return  new MyViewHolder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        User user = list.get(position);
        holder.date.setText(user.getDate());
        holder.time.setText(user.getTime());
        holder.description.setText(user.getDescription());
        holder.pressure.setText(user.getPressure());
        holder.temperature.setText(user.getTemperature());
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView date, time, description, temperature, pressure;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.textdate);
            time = itemView.findViewById(R.id.texttime);
            description = itemView.findViewById(R.id.textdescription);
            temperature = itemView.findViewById(R.id.texttemperature);
            pressure = itemView.findViewById(R.id.textpressure);
        }
    }
}