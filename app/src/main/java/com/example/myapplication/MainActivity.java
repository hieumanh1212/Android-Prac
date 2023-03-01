package com.example.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;



import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {

    //Khai báo đối tượng lưu trữ danh sách các contact
    private ArrayList<Contact> ContactList;
    private Adapter ListAdapter;
    private ListView lstContact;
    private Button buttonAdd, buttonDelete;
    private int SelectedItemId;

    private MyDB db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //FindViewId
        ContactList = new ArrayList<>();
//        ContactList.add(new Contact(1, "img1", "Trần Văn An", "56789056", false));
//        ContactList.add(new Contact(2, "img2", "Nguyễn Thế Hiền", "45678520", false));
//        ContactList.add(new Contact(3, "img3", "Bùi Phương Linh", "69553114", false));

        //Tạo db
        db = new MyDB(this, "ContactDB", null, 1);

        //Thêm dữ liệu lần đầu vào db
//        db.addContact(new Contact(1, "img1", "Trần Văn An", "56789056", false));
//        db.addContact(new Contact(2, "img2", "Nguyễn Thế Hiền", "45678520", false));
//        db.addContact(new Contact(3, "img3", "Bùi Phương Linh", "69553114", false));

        ContactList = db.getAllContact();

        lstContact = findViewById(R.id.ID_LIST);
        ListAdapter = new Adapter(ContactList, this);
        lstContact.setAdapter(ListAdapter);

        buttonAdd = findViewById(R.id.ID_BUTTONADD);
        buttonDelete = findViewById(R.id.ID_BUTTONDELETE);

        //Thêm người dùng
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //1. Tạo intent để mở subactivity
                Intent intent = new Intent(MainActivity.this, AddLayout.class);
                //2. Truyền dữ liệu sang subactivity bằng bundle nếu cần
                //3. Mở subactivity bằng cách gọi hàm startactivity hoặc startactivityforresult
                startActivityForResult(intent, 100);

            }
        });

        //Xóa người dùng
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i = ContactList.size()-1; i >= 0; i--)
                {
                    if(ContactList.get(i).isCheck()==true)
                    {
                        db.deleteContact(ContactList.get(i));
                        ContactList = db.getAllContact();
                    }
                }
                ListAdapter = new Adapter(ContactList, MainActivity.this);
                lstContact.setAdapter(ListAdapter);


            }
        });


        //Sự kiện LongClick
        lstContact.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                SelectedItemId = position;
                return false;
            }
        });

        //Gắn MenuPopup
        registerForContextMenu(lstContact);
    }
    //Hết onCreate


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bundle b = data.getExtras();
        int id = b.getInt("Id");
        String name = b.getString("Name");
        String phone = b.getString("Phone");
        Contact newcontact = new Contact(id, "Image", name, phone, false);
        if(requestCode == 100 && resultCode == 150) {
            //Truong hop them
//            ContactList.add(newcontact);
//            ListAdapter = new Adapter(ContactList, this);
//            lstContact.setAdapter(ListAdapter);

            //Thêm vào DB
            db.addContact(newcontact);
            //Đưa tất cả các bản ghi ở trong Database vào ContactList
            ContactList = db.getAllContact();
            //Đưa ContactList vào ListAdapter
        }
        else if(requestCode == 200 && resultCode == 150)
        {
            //Truong hop sua
//            for(Contact c: ContactList)
//            {
//                if(c.getId() == id)
//                {
//                    c.setName(name);
//                    c.setPhone(phone);
//                }
//            }

            for(Contact c: ContactList)
            {
                if(c.getId() == id)
                {
                    c.setName(name);
                    c.setPhone(phone);
                    db.updateContact(id, c);
                    ContactList = db.getAllContact();
                }
            }


            ListAdapter = new Adapter(ContactList, this);
            lstContact.setAdapter(ListAdapter);

        }
        ListAdapter = new Adapter(ContactList, MainActivity.this);
        lstContact.setAdapter(ListAdapter);
    }


    //Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = new MenuInflater((this));
        inflater.inflate(R.menu.sort_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //Hàm lấy tên
    public static String splitName(String fullname) {
        fullname = fullname.trim();
        String name = fullname.substring(fullname.lastIndexOf(" ")+1, fullname.length());
        return name;
    }

    //Menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.ID_SORTTANG:
                //Sap xep Arraylist<Contact> theo Name tang dan
                Collections.sort(ContactList, new Comparator<Contact>() {
                    @Override
                    public int compare(Contact o1, Contact o2) {
                        return (splitName(o1.getName()).compareTo(splitName(o2.getName())));
                    }
                });

                break;
            case R.id.ID_SORTGIAM:
                //Sap xep Arraylist<Contact> theo Name giam dan
                Collections.sort(ContactList, new Comparator<Contact>() {
                    @Override
                    public int compare(Contact o1, Contact o2) {
                        return (splitName(o2.getName()).compareTo(splitName(o1.getName())));
                    }
                });

                break;
        }
        ListAdapter = new Adapter(ContactList, this);
        lstContact.setAdapter(ListAdapter);
        return super.onOptionsItemSelected(item);
    }

    //Tạo MenuPupup
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        Contact con = ContactList.get(SelectedItemId);
        switch(item.getItemId())
        {
            case R.id.ID_MNUDEM:
                int count = 0;
                for(int i = 0; i < ContactList.size(); i++)
                {
                    if(splitName(ContactList.get(i).getName()).equals(splitName(con.getName())))
                    {
                        count++;
                    }
                }
                Toast.makeText(this, "Có " + String.valueOf(count-1) + " người cùng tên", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ID_EDIT:
                //1. Tạo intent để mở subactivity
                Intent intent = new Intent(MainActivity.this, AddLayout.class);
                //2. Truyền dữ liệu sang subactivity bằng bundle nếu cần
                Contact c = ContactList.get(SelectedItemId);
                Bundle b = new Bundle();
                b.putInt("Id", c.getId());
                b.putString("Image", c.getImages());
                b.putString("Name", c.getName());
                b.putString("Phone", c.getPhone());
                intent.putExtras(b);
                //3. Mở subactivity bằng cách gọi hàm startactivity hoặc startactivityforresult
                startActivityForResult(intent, 200);
                break;
        }
        return super.onContextItemSelected(item);
    }

}