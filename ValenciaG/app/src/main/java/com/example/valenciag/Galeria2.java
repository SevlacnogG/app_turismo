package com.example.valenciag;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.ar.sceneform.FrameTime;
import com.google.ar.sceneform.HitTestResult;
import com.google.ar.sceneform.Scene;
import com.google.ar.sceneform.SceneView;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.FootprintSelectionVisualizer;
import com.google.ar.sceneform.ux.TransformableNode;
import com.google.ar.sceneform.ux.TransformationSystem;

public class Galeria2 extends AppCompatActivity {

    private SceneView sceneView;
    private boolean modelPlaced = false;
    private TransformationSystem transformationSystem;
    private ModelRenderable modelRenderable;
    public TextView titulo, conteudo, precoEu, precoEth;
    public ImageView imagem1;
    public ImageButton botaoHome;
    public Button botao3d, botaoAudio, botaoComprar;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galeria2);
        titulo = findViewById(R.id.tv_NFTt);
        conteudo = findViewById(R.id.tv_NFTc);
        precoEu = findViewById(R.id.tv_NFTpreuro);
        precoEth = findViewById(R.id.tv_NFTpreuth);
        botao3d = findViewById(R.id.bNFT3d);
        botaoAudio = findViewById(R.id.bAudio);
        botaoComprar = findViewById(R.id.bComprar2);
        botaoHome = findViewById(R.id.ib_Home1);
        imagem1 = findViewById(R.id.iv_visual);

        if(Home.verificacao() != null){
            setAudio();
            setTexto();
        }else{
            titulo.setText("Monument Al Poeta Teodor Llorente");
            conteudo.setText("El conjunto dispuesto en círculo, es una asociación de motivos llorentinos,- dulzainero, tamborilero, Faust y Margarita " +
                    "y un desnudo alegórico de la Poesía- la escena culmina con la condecoración de Llorente por Valencia");
            precoEu.setText("Moneda corriente: 1€");
            precoEth.setText("Moneda digital (Ethereum): 0,00058372 Eth");
            imagem1.setImageResource(R.drawable.teodoropng);
            mediaPlayer = MediaPlayer.create(Galeria2.this, getResources().getIdentifier("teodoro_entero", "raw", getPackageName()));
        }

        loadModel();

        sceneView = findViewById(R.id.sceneview);
        sceneView.setVisibility(View.INVISIBLE);
        transformationSystem = new TransformationSystem(getResources().getDisplayMetrics(), new FootprintSelectionVisualizer());
        //sceneView.getScene().setOnTouchListener(this::onTouch);
        //sceneView.getScene().addOnPeekTouchListener(this::onTouch);
        sceneView.getScene().addOnUpdateListener(this::onFrameUpdate);
        sceneView.getRenderer().setClearColor(new com.google.ar.sceneform.rendering.Color(Color.LTGRAY));
        sceneView.getScene().addOnPeekTouchListener(new Scene.OnPeekTouchListener() {
            @Override
            public void onPeekTouch(HitTestResult hitTestResult, MotionEvent motionEvent) {
                transformationSystem.onTouch(hitTestResult, motionEvent);
            }
        });

        botaoAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                }else{
                    mediaPlayer.start();
                }
            }
        });

        botao3d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sceneView.getVisibility() == View.INVISIBLE){
                    sceneView.setVisibility(View.VISIBLE);
                    imagem1.setVisibility(View.INVISIBLE);
                }else{
                    sceneView.setVisibility(View.INVISIBLE);
                    imagem1.setVisibility(View.VISIBLE);
                }
            }
        });

        botaoComprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Galeria2.this, Compra.class);
                startActivity(intent);
            }
        });

        botaoHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Galeria2.this, Home.class);
                startActivity(intent);
            }
        });
    }

    public void setTexto(){
        if (Home.verificacao().equals("santo_graal")){
            titulo.setText("Santo Graal\nCatedral Valencia");
            conteudo.setText("El santo Caliz, custodiado en la Catedral de Valencia, es el que la tradicion " +
                    "de la Corona de Aragon relaciona con la copa de la Ultima Cena. Esta compuesto por tres partes, " +
                    "copa en cornalina de origen judia, cuerpo central mudejar y naveta de base arabe.");
            precoEu.setText("Moneda corriente: 1€");
            precoEth.setText("Moneda digital (Ethereum): 0,00058372 Eth");
            imagem1.setImageResource(R.drawable.catedralpng);
        }else{
            if (Home.verificacao().equals("eunate")){
                titulo.setText("Iglesia Santa María de Eunate");
                conteudo.setText("Es una iglesia románica ubicada en campo libre, a 2km de Muruzábal, en Navarra, España. " +
                        "Se halla en el lugar donde se juntan los Caminos de Santiago de Somport (aragonés) y de Roncesvalles (navarro), " +
                        "ubicada en el Valle de Ilzarbe (Valdizarbe).");
                precoEu.setText("Moneda corriente: 1€");
                precoEth.setText("Moneda digital (Ethereum): 0,00058372 Eth");
                imagem1.setImageResource(R.drawable.eunatepng);
            }else{
                if (Home.verificacao().equals("fachada_marques_aguas")){
                    titulo.setText("Fachada Palacio Marques de Dos Aguas");
                    conteudo.setText("La portada, dividida en dos niveles, presenta una hornacina en la que se encuentra la Virgen del Rosario, " +
                            "no es la actual puesto que Ignacio Vergara realizó una en madera que desapareció. Por ello, en 18866 Francisco Molinelli Cano " +
                            "realizó una copia de uno de los yesos de Vergara.");
                    precoEu.setText("Moneda corriente: 1€");
                    precoEth.setText("Moneda digital (Ethereum): 0,00058372 Eth");
                    imagem1.setImageResource(R.drawable.palaciopng);
                }else{
                    if (Home.verificacao().equals("falla")){
                        titulo.setText("Falla Mestre Valls");
                        conteudo.setText("Prototipo de repositorio de monumentos catalogados.");
                        precoEu.setText("Moneda corriente: 1€");
                        precoEth.setText("Moneda digital (Ethereum): 0,00058372 Eth");
                        imagem1.setImageResource(R.drawable.fallapng);
                    }else{
                    }
                }
            }
        }
    }

    public void setAudio(){
        String resultadoAudio = "";
        if(Home.verificacao().equals("falla")){
            resultadoAudio = "falla_entero";
        }else{
            if (Home.verificacao().equals("eunate")){
                resultadoAudio = "eunate_entero";
            }else{
                if (Home.verificacao().equals("fachada_marques_aguas")){
                    resultadoAudio = "palacio_entero";
                }
            }
        }
        mediaPlayer = MediaPlayer.create(Galeria2.this, getResources().getIdentifier(resultadoAudio, "raw", getPackageName()));
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            sceneView.resume();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        sceneView.pause();
    }

    private void onFrameUpdate(FrameTime frameTime) {
        Log.d(TAG, "onFrameUpdate");
        if (!modelPlaced) {
            placeModel();
        }
    }

    private void placeModel() {
        ModelRenderable renderable = modelRenderable;
        if (renderable == null) {
            Log.d(TAG, "Renderable not yet ready");
            return;
        }

        //Node modelNode = new Node();
        TransformableNode modelNode = new TransformableNode(transformationSystem);
        modelNode.getRotationController().setEnabled(true);
        modelNode.getScaleController().setEnabled(true);
        modelNode.getTranslationController().setEnabled(false);
        //TransformableNode modelNode2=new TransformableNode(transformationSystem).apply()
        modelNode.setRenderable(renderable);
        sceneView.getScene().addChild(modelNode);
        modelNode.setLocalPosition(new Vector3(2, 0, -3f));
        //modelNode.setLocalRotation(Quaternion.axisAngle(new Vector3(0f, 0, 1), 90f));
        Log.d(TAG, "Placed renderable");
        modelPlaced = true;
    }


    private void loadModel(){
        ModelRenderable.builder().setSource(this, getResources().getIdentifier("monumento_teodoro_llorente", "raw", getPackageName())).setIsFilamentGltf(true).build()
                .thenAccept(renderable -> modelRenderable = renderable).exceptionally(throwable ->{
            Toast.makeText(Galeria2.this, "Modelo não foi carregado", Toast.LENGTH_SHORT).show();
            return null;
        });
    }
}