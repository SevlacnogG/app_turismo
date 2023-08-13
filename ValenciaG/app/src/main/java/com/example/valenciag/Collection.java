package com.example.valenciag;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class Collection extends AppCompatActivity {

    LinearLayout item1;
    Button bRealidad;
    ImageButton botao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        item1 = findViewById(R.id.item1col);
        bRealidad = findViewById(R.id.bRealidad);
        botao = findViewById(R.id.botaoItemLista);

        if(!Compra.compraEfetuada()){
            item1.setVisibility(View.INVISIBLE);
        }else{
            item1.setVisibility(View.VISIBLE);
        }

        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Collection.this, Galeria2.class);
            }
        });

        bRealidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Collection.this, Vis3D.class);
                startActivity(intent);
            }
        });

    }
}