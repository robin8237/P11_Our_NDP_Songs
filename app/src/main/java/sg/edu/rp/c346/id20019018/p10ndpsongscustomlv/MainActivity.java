package sg.edu.rp.c346.id20019018.p10ndpsongscustomlv;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    EditText etSong,etSinger,etYear;
    Button btnIns,btnSL;
    RatingBar rb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etSong = findViewById(R.id.etSong);
        etSinger = findViewById(R.id.etSing);
        etYear = findViewById(R.id.etYear);
        btnIns = findViewById(R.id.btnIns);
        btnSL = findViewById(R.id.btnSL);
        rb = findViewById(R.id.ratingBar);

        btnIns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = etSong.getText().toString().trim();
                String singer = etSinger.getText().toString().trim();
                int year = -1;
                boolean strEmp = false;
                boolean strYear = false;
                if (etYear.getText().toString().trim().length() == 0) {
                    strYear = false;
                } else {
                    strYear = true;
                    year = Integer.parseInt(etYear.getText().toString().trim());
                }

                if (title.length() == 0 || singer.length() == 0) {
                    Toast.makeText(MainActivity.this, "Data Incomplete", Toast.LENGTH_SHORT).show();
                    strEmp = false;
                    return;
                } else {
                    strEmp = true;
                }

                float stars = -1;
                if(rb.getRating() != 0)
                {
                    stars = rb.getRating();
                }


                if (strEmp == true && strYear == true && stars != -1)
                {
                    DBHelper dbh = new DBHelper(MainActivity.this);
                    long result = dbh.insertSong(title,singer,year,stars);
                    if(result != -1)
                    {
                        Toast.makeText(MainActivity.this,"Song Inserted",Toast.LENGTH_SHORT).show();
                        etSong.setText("");
                        etSinger.setText("");
                        etYear.setText("");
                        rb.setRating(0);

                    }
                    else
                    {
                        Toast.makeText(MainActivity.this,"Insert Failed",Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Data Incomplete", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnSL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(MainActivity.this,NDPList.class);
                startActivity(j);
            }
        });

    }
}