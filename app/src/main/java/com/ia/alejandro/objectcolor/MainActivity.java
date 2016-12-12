package com.ia.alejandro.objectcolor;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements OnClickListener {
    Button boton;
    ImageView imagen;
    Intent i;
    int data_imagen[][][];
    final static int cons = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    public void init(){
        boton = (Button)findViewById(R.id.btnCaptura);
        boton.setOnClickListener(this);

        imagen = (ImageView)findViewById(R.id.imagen);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.btnCaptura:
                i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                startActivityForResult(i, cons);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            Bundle ex = data.getExtras();
            Bitmap bmp = (Bitmap) ex.get("data");
            int h = bmp.getHeight();
            int w = bmp.getWidth();
            data_imagen = ProcesarImagen.BitToMat(bmp);
            int color[]  = {0, 0, 0};
            int hastaquecolor[] = {10, 10, 10};
            imagen.setImageBitmap(
                    ProcesarImagen.MatToBit(
                            ProcesarImagen.BinScale(
                                    ProcesarImagen.GrayScale(data_imagen, h, w), h, w, color, hastaquecolor),
                            h, w)
            );
        }
    }
}
