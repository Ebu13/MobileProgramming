package com.example.personadd;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private List<User> userList;
    private OnUserListener onUserListener;

    public UserAdapter(List<User> userList, OnUserListener onUserListener) {
        this.userList = userList;
        this.onUserListener = onUserListener;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false); // Yeni XML dosyasını kullan
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = userList.get(position);
        holder.nameTextView.setText(user.getName());
        holder.emailTextView.setText(user.getEmail());
        holder.updateButton.setOnClickListener(v -> onUserListener.onUpdateClick(user));
        holder.deleteButton.setOnClickListener(v -> onUserListener.onDeleteClick(user.getId()));
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView emailTextView;
        Button updateButton;
        Button deleteButton;

        UserViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.textViewName);
            emailTextView = itemView.findViewById(R.id.textViewEmail);
            updateButton = itemView.findViewById(R.id.buttonUpdate); // Buton tanımlandı
            deleteButton = itemView.findViewById(R.id.buttonDelete); // Buton tanımlandı
        }
    }

    public interface OnUserListener {
        void onUpdateClick(User user);
        void onDeleteClick(int userId);
    }
}
