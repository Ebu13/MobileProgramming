package com.example.personadd;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MainActivity extends AppCompatActivity implements UserAdapter.OnUserListener {

    private EditText editTextName, editTextEmail;
    private SQLiteHelper dbHelper;
    private UserAdapter userAdapter;
    private List<User> userList;
    private RecyclerView recyclerViewUsers;
    private int selectedUserId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        Button buttonAddUser = findViewById(R.id.buttonAddUser);
        recyclerViewUsers = findViewById(R.id.recyclerViewUsers);

        dbHelper = new SQLiteHelper(this);
        userList = dbHelper.getAllUsers();
        userAdapter = new UserAdapter(userList, this);

        recyclerViewUsers.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewUsers.setAdapter(userAdapter);

        buttonAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString().trim();
                String email = editTextEmail.getText().toString().trim();

                if (name.isEmpty() || email.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Lütfen tüm alanları doldurun.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (selectedUserId != -1) {
                    dbHelper.updateUser(selectedUserId, name, email);
                    selectedUserId = -1; // Seçili kullanıcıyı sıfırla
                    buttonAddUser.setText("Kullanıcı Ekle");
                } else {
                    dbHelper.addUser(name, email);
                }

                editTextName.setText("");
                editTextEmail.setText("");
                updateUserList();
            }
        });
    }

    private void updateUserList() {
        userList.clear();
        userList.addAll(dbHelper.getAllUsers());
        userAdapter.notifyDataSetChanged();
    }

    @Override
    public void onUpdateClick(User user) {
        selectedUserId = user.getId();
        editTextName.setText(user.getName());
        editTextEmail.setText(user.getEmail());
        Button buttonAddUser = findViewById(R.id.buttonAddUser);
        buttonAddUser.setText("Kullanıcı Güncelle");
    }

    @Override
    public void onDeleteClick(int userId) {
        dbHelper.deleteUser(userId);
        updateUserList();
    }
}
