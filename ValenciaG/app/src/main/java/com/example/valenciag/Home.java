package com.example.valenciag;

import android.content.Intent;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

//A fazer: arrumar a interface; relacionar códigos QR aos destinos; criar atividade de descrição.
public class Home extends AppCompatActivity /*implements SurfaceHolder.Callback*/{
    Button bQR, bPrev, bRealidad;
    ImageButton bVisit, bGal;
    Camera camera;
    SurfaceView camView;
    SurfaceHolder surfaceHolder;
    AutoCompleteTextView autoCompleteTxt;
    boolean camCondition = false;
    public static String selecao = "";
    static String resultadoQR;
    String[] destinos =  {"Museo Nacional de Cerámica y Artes Suntuarias González Martí",
            "Falla maestro Valls",
            "Igreja de Santa Maria de Eunate",
            "Monumento al Poeta Teodor L.",
            "Capilla de Santo Caliz"};
    ArrayAdapter<String> adapterItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        bQR = findViewById(R.id.bQR);
        bVisit = findViewById(R.id.bVisit);
        bRealidad = findViewById(R.id.botaoReserva);
        bPrev = findViewById(R.id.bPrev);
        bGal = findViewById(R.id.bGal);
        autoCompleteTxt = findViewById(R.id.auto_complete_txt);
        adapterItems = new ArrayAdapter<String>(this,R.layout.list_item,destinos);
        autoCompleteTxt.setAdapter(adapterItems);

        Fragment fragment = new MapFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, fragment).commit();

        autoCompleteTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selecao = parent.getItemAtPosition(position).toString();
                //Toast.makeText(getApplicationContext(),"Destino: "+selecao,Toast.LENGTH_SHORT).show();
            }
        });

        bQR.setOnClickListener(v->{
            scanCode();

            /*new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(Home.this, resultadoQR, Toast.LENGTH_LONG).show();
                }
            }, 500);*/

            /*Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(string1));
            startActivity(intent);*/
        });

        bPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, Compra.class);
                startActivity(intent);
            }
        });

        bGal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Galeria2.class);
                startActivity(intent);
            }
        });

        bVisit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://www.valencia.es/");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                //intent.setClass(getApplicationContext(), Fragment_Map2.class);
                startActivity(intent);
            }
        });



        //getWindow().setFormat(PixelFormat.UNKNOWN);
        /*camView = (SurfaceView) findViewById(R.id.campreview); // Criar o campreview no layout da nova atividade.
        surfaceHolder = camView.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_NORMAL);*/ // Relativo à prévia da câmera na Home.

        /*bQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, QR.class);
                startActivity(intent);
            }
        });*/ //A classe independente pro leitor de QR não está funcionando ainda (só usaria pra adicionar o leitor num fragmento, mas é desnecessário);
    }

    /*private void abrirVis3D(){
        Intent intent = new Intent(this, Vis3D.class);
        startActivity(intent);
    }*/

    private void scanCode(){
        ScanOptions options = new ScanOptions();
        options.setTimeout(10000);
        options.setPrompt("Aumente o volume para ligar o flash.");
        options.setBeepEnabled(false);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureAct.class);
        barLauncher.launch(options);
    }

    ActivityResultLauncher<ScanOptions> barLauncher = registerForActivityResult(new ScanContract(), result -> {
       if(result.getContents() != null ){
           resultadoQR = result.getContents();
           verificacao();
           /*AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
           builder.setTitle("Resultado");
           builder.setMessage(result.getContents());
           builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialogInterface, int i) {
                   dialogInterface.dismiss();
               }
           }).show();*/
       }
    });

    public static String getSelecao() {
        return selecao;
    }

    public static String verificacao(){
        String endereco1 = null;
        if (resultadoQR.equals("Graal")) {
            endereco1 = "santo_graal";
            return endereco1;
        }else{
            if (resultadoQR.equals("Fachada Palacio Marques de Dos Aguas")){
                endereco1 = "fachada_marques_aguas";
                return endereco1;
            }else{
                if (resultadoQR.equals("Iglesia Santa María de Eunate")){
                    endereco1 = "eunate";
                    return endereco1;
                }else{
                    if (resultadoQR.equals("Falla")){
                        endereco1 = "falla";
                        return endereco1;
                    }else{
                        if (resultadoQR.equals("Monumento Teodoro Llorente")){
                            endereco1 = "monumento_teodoro_llorente";
                            return endereco1;
                        }else{
                            return endereco1;
                        }
                    }
                }
            }
        }
    }

    /*@Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){
        if(camCondition){
            camera.stopPreview(); //para a pré-visualização;
            camCondition = false;
        }

        if(camera != null){
            try{
                Camera.Parameters parameters = camera.getParameters();
                parameters.setColorEffect(Camera.Parameters.EFFECT_SEPIA);
                camera.setParameters(parameters);
                camera.setPreviewDisplay(surfaceHolder);
                camera.startPreview();

                camCondition = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){
        camera = Camera.open();
        camera.setDisplayOrientation(90);
    }

    public void surfaceDestroyed(SurfaceHolder holder){
        camera.stopPreview();
        camera.release();
        camera = null;
        camCondition = false;
    }*/ // Relativo à prévia da câmera na Home.

}