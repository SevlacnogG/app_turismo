package com.example.valenciag;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.ar.sceneform.HitTestResult;
import com.google.ar.sceneform.Scene;
import com.google.ar.sceneform.SceneView;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.FootprintSelectionVisualizer;
import com.google.ar.sceneform.ux.TransformableNode;
import com.google.ar.sceneform.ux.TransformationSystem;

public class Galeria extends AppCompatActivity {
    Button btn;
    public SceneView sceneView;;
    private Scene scene;
    private ModelRenderable modelRenderable;
    public TransformationSystem transformationSystem;
    float grau = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galeria);
        sceneView = findViewById(R.id.sceneview);
        scene = sceneView.getScene();
        btn = findViewById(R.id.btnTeste);

        setUpModel();
        //onRenderableLoaded(modelRenderable);

        //Node node = new Node();

        /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                node.setRenderable(modelRenderable);
            }
        }, 2500);

        node.setParent(sceneView.getScene());
        sceneView.getScene().addChild(node);
        node.setLocalPosition(new Vector3(0, 0, -0.2f));
        node.setLocalScale(new Vector3(3, 3, 3));*/
        //node.setLocalRotation(Quaternion.axisAngle(Vector3.right(), -30f));

        /*btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (float i = 0f; i < 360f; i = i + 3){
                    grau = i;
                    node.setLocalRotation(Quaternion.axisAngle(Vector3.right(), grau));
                }
            }
        });*/

        //onRenderableLoaded(modelRenderable);

        makeTransformationSystem();

        TransformableNode tNode = new TransformableNode(transformationSystem);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                tNode.setRenderable(modelRenderable);
            }
        }, 5000);

        tNode.getRotationController().setEnabled(true);
        tNode.getScaleController().setEnabled(true);
        tNode.getTranslationController().setEnabled(true);
        tNode.getScaleController().setMinScale(5.5f);
        tNode.getScaleController().setMaxScale(10.5f);
        tNode.setLocalScale(new Vector3(0, 0, -0.2f));
        sceneView.getScene().addChild(tNode);

        //sceneView.getScene().addOnUpdateListener(this::onFrameUpdate);
        sceneView.getRenderer().setClearColor(new com.google.ar.sceneform.rendering.Color(Color.LTGRAY));
        sceneView.getScene().addOnPeekTouchListener(new Scene.OnPeekTouchListener() {
            @Override
            public void onPeekTouch(HitTestResult hitTestResult, MotionEvent motionEvent) {
                transformationSystem.onTouch(hitTestResult, motionEvent);
            }
        });
    }

    private void makeTransformationSystem(){
        transformationSystem = new TransformationSystem(getResources().getDisplayMetrics(), new FootprintSelectionVisualizer());
    }

    private void setUpModel(){
        ModelRenderable.builder().setSource(this, getResources().getIdentifier("rex", "raw", getPackageName())).setIsFilamentGltf(true).build()
                .thenAccept(renderable -> modelRenderable = renderable).exceptionally(throwable ->{
            Toast.makeText(Galeria.this, "Modelo não foi carregado", Toast.LENGTH_SHORT).show();
            return null;
        });
    }

    /*public void onRenderableLoaded(ModelRenderable renderable){
        Node node = new Node();
        node.setRenderable(renderable);
        node.setParent(sceneView.getScene());
        sceneView.getScene().addChild(node);
        node.setLocalPosition(new Vector3(0, 0, -1f));
        node.setLocalScale(new Vector3(5, 5, 5));
    }*/ //Por algum motivo, precisa carregar a classe duas vezes para funcionar.
            // Por isso, optei por "forçar a espera" do carregamento com o Handler.

    @Override
    public void onPause() {
        super.onPause();
        sceneView.pause();
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            sceneView.resume();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}