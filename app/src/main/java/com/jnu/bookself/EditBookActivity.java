package com.jnu.bookself;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class EditBookActivity extends AppCompatActivity {

    public static final int RESULT_CODE_SUCCESS = 666;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_book);

        position = this.getIntent().getIntExtra("position",0);
        String title = this.getIntent().getStringExtra("title");
        String author = this.getIntent().getStringExtra("author");
        String publisher = this.getIntent().getStringExtra("publisher");
        String pubYear = this.getIntent().getStringExtra("pubYear");
        String pubMonth = this.getIntent().getStringExtra("pubMonth");
        int image = this.getIntent().getIntExtra("image",R.drawable.ic_launcher_background);

        EditText editTextTitle = findViewById(R.id.edit_book_title);
        EditText editTextAuthor = findViewById(R.id.edit_book_author);
        EditText editTextPublisher = findViewById(R.id.edit_book_publisher);
        EditText editTextPubYear = findViewById(R.id.edit_book_pubYear);
        EditText editTextPubMonth = findViewById(R.id.edit_book_pubMonth);
        ImageView editImage = findViewById(R.id.edit_image_book_cover);

        if(null!=title){
            editTextTitle.setText(title);
            editTextAuthor.setText(author);
            editTextPublisher.setText(publisher);
            editTextPubYear.setText(pubYear);
            editTextPubMonth.setText(pubMonth);
            editImage.setImageResource(image);
        }

        TextView textViewSave = findViewById(R.id.edit_toolbar_save);
        ImageView imageViewClose = findViewById(R.id.edit_toolbar_close);

        imageViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditBookActivity.this.finish();
            }
        });
        textViewSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("title",editTextTitle.getText().toString());
                bundle.putString("author",editTextAuthor.getText().toString());
                bundle.putString("publisher",editTextPublisher.getText().toString());
                bundle.putString("pubYear",editTextPubYear.getText().toString());
                bundle.putString("pubMonth",editTextPubMonth.getText().toString());
//                bundle.putInt("image",editImage.getImageAlpha());
                bundle.putInt("position",position);
                intent.putExtras(bundle);
                setResult(RESULT_CODE_SUCCESS,intent);
                EditBookActivity.this.finish();
            }
        });

    }
}