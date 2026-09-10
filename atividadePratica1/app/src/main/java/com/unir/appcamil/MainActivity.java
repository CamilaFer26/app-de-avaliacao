package com.unir.appcamil;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private EditText editTitle;
    private EditText editReview;
    private Button btnAdd;
    private Spinner spinner;
    private TextView relatorio;
    private RatingBar ratingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editTitle = findViewById(R.id.editTitle);
        editReview = findViewById(R.id.editReview);
        ratingBar = findViewById(R.id.ratingBar);
        btnAdd = findViewById(R.id.btnAdd);
        relatorio = findViewById(R.id.textViewRelatorio);
        spinner = findViewById(R.id.spinner);


        ArrayList<String> avaliacoes = new ArrayList<>();
        avaliacoes.add("");
        ArrayList<Double> numstars = new ArrayList<>();
        numstars.add(0.0);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        adapter.add("Ver avaliados");

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) ;
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i != 0) {
                    relatorio.setText("Nota: " + numstars.get(i) + "\n\nReview: " + avaliacoes.get(i));
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        ratingBar.setStepSize(0.5f);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = editTitle.getText().toString();
                String review = editReview.getText().toString();
                float stars = ratingBar.getRating();

                if(title.isEmpty() || review.isEmpty() || stars == 0){
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setMessage("Preencha todos os campos!");
                    builder.setCancelable(false);
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    builder.show();
                    return;
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setMessage("Avaliação salva com sucesso!");
                builder.setCancelable(false);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                builder.show();

                avaliacoes.add(review);
                numstars.add(Double.valueOf(stars));
                adapter.add(title);
                clean();
            }
        });
    }
    public void clean(){
        editTitle.setText("");
        editReview.setText("");
        ratingBar.setRating(0);
    }
}