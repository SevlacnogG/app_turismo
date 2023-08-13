package com.example.valenciag;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Compra extends AppCompatActivity {

    public EditText nomeCliente, cCodigo, cCred, cCredSec;
    public Button bComprar;
    public String nomeClientetxt, cCodigotxt, cCredtxt, cCredSectxt;
    public static boolean comprado;
    LinearLayout galeria;
    LayoutInflater inflater;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compra);

        bComprar = findViewById(R.id.bComprar);
        cCodigo = findViewById(R.id.et_cod);
        nomeCliente = findViewById(R.id.et_nomeCompra);
        cCred = findViewById(R.id.et_Credit);
        cCredSec = findViewById(R.id.et_CreditSec);
        galeria = findViewById(R.id.galeria);

        progressDialog = new ProgressDialog(this);

        bComprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cCodigotxt =  cCodigo.getText().toString();
                nomeClientetxt =  nomeCliente.getText().toString();
                cCredtxt = cCred.getText().toString();
                cCredSectxt = cCredSec.getText().toString();


                if(cCodigotxt.trim().equals("") | nomeClientetxt.trim().equals("") | cCredtxt.trim().equals("") | cCredSectxt.trim().equals("") ){
                    comprado = false;
                    Toast.makeText(Compra.this, "Rellene todos los campos", Toast.LENGTH_SHORT).show();
                }else{
                    progressDialog.setMessage("Registering purchase...");
                    progressDialog.show();

                    comprado = true;

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Compra.this, "¡Compra realizada!", Toast.LENGTH_SHORT).show();
                        }
                    }, 1500);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(Compra.this, Collection.class);
                            startActivity(intent);
                            finish();
                        }
                    }, 2500);
                }
            }
        });

    }

    public static boolean compraEfetuada(){
        if(comprado == true){
            return true;
        }else{
            return false;
        }
    }

    /*public String retornarCompra(){
        return cCodigotxt;
    }*/

    //inflater = LayoutInflater.from(this);
}