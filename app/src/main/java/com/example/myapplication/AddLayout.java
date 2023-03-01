package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class AddLayout extends AppCompatActivity {
    private EditText etId;
    private EditText etFullname;
    private EditText etPhone;
    private ImageView ivImage;
    private Button btnOk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        etId = findViewById(R.id.ID_MA);
        etFullname = findViewById(R.id.ID_EDITHOTEN);
        etPhone = findViewById(R.id.ID_EDITPHONE);
        ivImage = findViewById(R.id.ID_ANH);
        btnOk = findViewById(R.id.ID_BUTTONTHEM);
        //Lấy intent từ MainActivity chuyển sang
        Intent intent = getIntent();
        //Lấy bundle
        Bundle bundle = intent.getExtras();

        if(bundle != null)
        {
            int id = bundle.getInt("Id");
            String image = bundle.getString("Image");
            String name = bundle.getString("Name");
            String phone = bundle.getString("Phone");
            etId.setText(String.valueOf(id));
            etFullname.setText(name);
            etPhone.setText(phone);

            btnOk.setText("Edit");
        }

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Lấy dữ liệu và gửi về cho MainActivity
                int id = Integer.parseInt(etId.getText().toString());
                String name = etFullname.getText().toString();
                String phone = etPhone.getText().toString();
                Intent intent = new Intent();
                Bundle b = new Bundle();
                b.putInt("Id", id);
                b.putString("Name", name);
                b.putString("Phone", phone);
                intent.putExtras(b);
                setResult(150, intent);
                finish();
            }
        });

    }
}
