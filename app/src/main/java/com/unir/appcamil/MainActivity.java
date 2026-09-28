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

import com.unir.appcamil.room.AppDatabase;
import com.unir.appcamil.room.Review;
import com.unir.appcamil.room.ReviewDAO;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText editTitle;
    private EditText editReview;
    private Button btnAdd;
    private Button btnDelete;
    private Spinner spinner;
    private TextView relatorio;
    private RatingBar ratingBar;
    private AppDatabase db;
    private ReviewDAO dao;
    private List<Review> listaReviews;
    private Review reviewAtual = new Review();
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

        db = AppDatabase.obterInstancia(this);
        dao = db.reviewDAO();
        listaReviews = dao.obterTodas();

        editTitle = findViewById(R.id.editTitle);
        editReview = findViewById(R.id.editReview);
        ratingBar = findViewById(R.id.ratingBar);
        btnAdd = findViewById(R.id.btnAdd);
        btnDelete = findViewById(R.id.buttonDelete);
        relatorio = findViewById(R.id.textViewRelatorio);
        spinner = findViewById(R.id.spinner);

        ArrayList<String> titles = new ArrayList<>();
        titles.add("Ver avaliados");
        for(Review r : listaReviews){
            titles.add(r.titulo);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, titles);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) ;
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i != 0) {
                    reviewAtual = listaReviews.get(i - 1);
                    relatorio.setText("Nota: " + reviewAtual.nota + "\n\nReview: " + reviewAtual.review);
                    btnDelete.setVisibility(com.google.android.material.R.id.visible);
                }else{
                    btnDelete.setVisibility(com.google.android.material.R.id.gone);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dao.deletar(reviewAtual);
                listaReviews.remove(reviewAtual);
                adapter.remove(reviewAtual.titulo);
                adapter.notifyDataSetChanged();
                spinner.setSelection(0);
                relatorio.setText("");
            }
        });

        ratingBar.setStepSize(0.5f);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = editTitle.getText().toString();
                String desc = editReview.getText().toString();
                float stars = ratingBar.getRating();

                if(title.isEmpty() || desc.isEmpty() || stars == 0){
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

                adapter.add(title);

                Review review = new Review();
                review.titulo = title;
                review.nota = stars;
                review.review = desc;
                dao.inserir(review);
                listaReviews.add(review);

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