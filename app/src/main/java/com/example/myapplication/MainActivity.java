package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;



import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //Khai báo đối tượng lưu trữ danh sách các contact
    private ArrayList<Contact> ContactList;
    private Adapter ListAdapter;
    private ListView lstContact;
    private Button buttonAdd, buttonDelete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //FindViewId
        ContactList = new ArrayList<>();
        ContactList.add(new Contact(1, "img1", "Trần Văn An", "56789056", false));
        ContactList.add(new Contact(2, "img2", "Nguyễn Thế Hiền", "45678520", false));
        ContactList.add(new Contact(3, "img3", "Bùi Phương Linh", "69553114", false));

        lstContact = findViewById(R.id.ID_LIST);
        ListAdapter = new Adapter(ContactList, this);
        lstContact.setAdapter(ListAdapter);

        buttonAdd = findViewById(R.id.ID_BUTTONADD);
        buttonDelete = findViewById(R.id.ID_BUTTONDELETE);

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i = 0; i < ContactList.size(); i++)
                {
                    i = 0;
                    if(ContactList.get(i).isCheck()==true)
                    {
                        Toast.makeText(MainActivity.this, String.valueOf(i), Toast.LENGTH_LONG).show();
                        ContactList.remove(i);
                    }
                }
                ListAdapter = new Adapter(ContactList, MainActivity.this);
                lstContact.setAdapter(ListAdapter);
            }
        });
    }
}