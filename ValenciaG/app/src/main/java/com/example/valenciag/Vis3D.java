package com.example.valenciag;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.text.format.DateFormat;
import android.view.MotionEvent;
import android.view.PixelCopy;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.ar.core.Anchor;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.ArSceneView;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.BaseArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Vis3D extends AppCompatActivity {
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSION_STORAGE = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    private ArFragment arFragment;
    private ModelRenderable modelRenderable;
    Button bPS;
    List<TransformableNode> videoNodeList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vis3_d);

        verifyStoragePermission(this);



        bPS = findViewById(R.id.bPS);
        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.arFragment);

        setUpModel();
        setUpPlane();

        bPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bPS.setVisibility(View.INVISIBLE);

                arFragment.getArSceneView().getPlaneRenderer().setVisible(false);
                for (TransformableNode node : videoNodeList){
                    if (node.isSelected()){
                        node.getTransformationSystem().selectNode(null);
                    }
                }

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //takeScreenShot(getWindow().getDecorView().getRootView(), "result");
                        takePhoto();
                    }
                }, 500);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        bPS.setVisibility(View.VISIBLE);
                        Toast.makeText(Vis3D.this, "Screen printed!", Toast.LENGTH_SHORT).show();
                    }
                }, 1500);
            }
        });
    }

    private void setUpModel(){
        ModelRenderable.builder().setSource(this, getResources().getIdentifier("monumento_teodoro_llorente", "raw", getPackageName())).setIsFilamentGltf(true).build()
                .thenAccept(renderable -> modelRenderable = renderable).exceptionally(throwable ->{
                    Toast.makeText(Vis3D.this, "Modelo não foi carregado", Toast.LENGTH_SHORT).show();
                    return null;
        });
    }

    private void setUpPlane(){
        arFragment.setOnTapArPlaneListener(new BaseArFragment.OnTapArPlaneListener() {
            @Override
            public void onTapPlane(HitResult hitResult, Plane plane, MotionEvent motionEvent) {
                Anchor anchor = hitResult.createAnchor();
                AnchorNode anchorNode = new AnchorNode(anchor);
                anchorNode.setParent(arFragment.getArSceneView().getScene());
                createModel(anchorNode);
            }
        });
    }

    private void createModel(AnchorNode anchorNode){
        TransformableNode node = new TransformableNode(arFragment.getTransformationSystem());
        //node.getScaleController().setMinScale(5.5f);
        //node.getScaleController().setMaxScale(10.5f);
        node.setParent(anchorNode);
        node.setRenderable(modelRenderable);

        //esse método funciona tanto quanto o setParent no setUpPlane:
        //arFragment.getArSceneView().getScene().addChild(anchorNode);

        //adicionar diferenciação entre os nodes
        videoNodeList.add(node);
        node.select();
    }

    private void takePhoto() {
        ArSceneView view = arFragment.getArSceneView();

        // Create a bitmap the size of the scene view.
        final Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),
                Bitmap.Config.ARGB_8888);

        // Create a handler thread to offload the processing of the image.
        final HandlerThread handlerThread = new HandlerThread("PixelCopier");
        handlerThread.start();
        // Make the request to copy.
        PixelCopy.request(view, bitmap, (copyResult) -> {
            if (copyResult == PixelCopy.SUCCESS) {
                try {
                    saveBitmapToDisk(bitmap);
                } catch (IOException e) {
                    Toast toast = Toast.makeText(Vis3D.this, e.toString(),
                            Toast.LENGTH_LONG);
                    toast.show();
                    return;
                }

            } else {

            }
            handlerThread.quitSafely();
        }, new Handler(handlerThread.getLooper()));
    }


    public void saveBitmapToDisk(Bitmap bitmap) throws IOException {
        try {
            //MUDAR FIM DO ENDEREÇO PARA NOME DO APP:
            String dirPath = Environment.getExternalStorageDirectory().toString() + "/teste2";
            File fileDir = new File(dirPath);
            if (!fileDir.exists()) {
                boolean mkdir = fileDir.mkdir();
            }

            Date date = new Date();
            CharSequence format = DateFormat.format("yyyy-MM-dd_hh:mm:ss", date);

            File mediaFile = new File(dirPath, "Vis3D" + format + ".jpeg");

            FileOutputStream fileOutputStream = new FileOutputStream(mediaFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        }catch (FileNotFoundException e){
        e.printStackTrace();
        }catch (IOException e){
        e.printStackTrace();
        }
    }

    /*private void tirarPrint2(){
        ArSceneView view = arFragment.getArSceneView();

        final Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);

        final HandlerThread handlerThread = new HandlerThread("PixelCopier");
        handlerThread.start();

        PixelCopy.request(view, bitmap, (copyResult) -> {
            if (copyResult == PixelCopy.SUCCESS){
                try {
                    saveBitmapToDisk(bitmap);
                }catch (IOException e){
                    Toast toast = Toast.makeText(Vis3D.this, e.toString(), Toast.LENGTH_LONG);
                    toast.show();
                    return;
                }
                SnackbarUtility.showSnackbarTypeLong(settingsButton, "Screenshot saved");
            }else{
                SnackbarUtility.showSnackbarTypeLong(settingsButton, "Screenshot NOT saved");
            }
        });
    }

    protected static File takeScreenShot(View view, String fileName){
        Date date = new Date();
        CharSequence format = DateFormat.format("yyyy-MM-dd_hh:mm:ss", date);

        try {
            //MUDAR FIM DO ENDEREÇO PARA NOME DO APP:
            String dirPath = Environment.getExternalStorageDirectory().toString() + "/teste";
            File fileDir = new File(dirPath);
            if (!fileDir.exists()) {
                boolean mkdir = fileDir.mkdir();
            }

            String path = dirPath + "/" + fileName + "-" + format + ".jpeg";

            view.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
            view.setDrawingCacheEnabled(false);

            File imageFile = new File(path);

            FileOutputStream fileOutputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            return imageFile;

        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }*/

    public static void verifyStoragePermission(Activity activity){
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(activity, PERMISSION_STORAGE, REQUEST_EXTERNAL_STORAGE);
        }
    }

    /*private void printScreen(){
        Date now = new Date();
        android.text.format.DateFormat.format("yyy-MM-dd_hh:mm:ss", now);

        try {
            String mPath = Environment.getExternalStorageDirectory().toString() + "/" + now + ".jpg";

            View v1 = getWindow().getDecorView().getRootView();
            v1.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            v1.setDrawingCacheEnabled(false);

            File imageFile = new File(mPath);

            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();

            //openScreenshot(imageFile);
        }catch (Throwable e){
            e.printStackTrace();
        }
    }

    private void openScreenshot(File imageFile){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(imageFile);
        intent.setDataAndType(uri, "image/*");
        startActivity(intent);
    }

    public static Bitmap printScreen2(Activity activity){

        try{
            Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            view.draw(canvas);
            return bitmap;
        }catch (Throwable e){
        }
        return null;
    }*/

}