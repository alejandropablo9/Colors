package com.ia.alejandro.objectcolor;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements OnClickListener, OnSeekBarChangeListener{
    Button boton;
    ImageView imagen;
    ImageView original;
    Intent i;
    TextView color;
    Bitmap bmp;
    SeekBar sbR;
    SeekBar sbG;
    SeekBar sbB;
    int data_imagen[][][];
    final static int cons = 0;

    int colorR = 0;
    int colorG = 0;
    int colorB = 0;

    boolean existeFoto = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    public void init(){
        boton = (Button)findViewById(R.id.btnCaptura);
        boton.setOnClickListener(this);
        color = (TextView)findViewById(R.id.color);
        imagen = (ImageView)findViewById(R.id.imagen);
        original = (ImageView)findViewById(R.id.imagen);;
        sbR = (SeekBar)findViewById(R.id.seekBar);
        sbG = (SeekBar)findViewById(R.id.seekBar3);
        sbB = (SeekBar)findViewById(R.id.seekBar5);
        sbR.setOnSeekBarChangeListener(this);
        sbG.setOnSeekBarChangeListener(this);
        sbB.setOnSeekBarChangeListener(this);

        color.setBackgroundColor(Color.argb(255, colorR, colorG, colorB));
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.btnCaptura:
                existeFoto = true;
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
            bmp = (Bitmap) ex.get("data");
            data_imagen = ProcesarImagen.BitToMat(bmp);
            procesarFoto();
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        switch (seekBar.getId()){
            case R.id.seekBar:
                colorR = i;
                //Toast.makeText(getApplicationContext(),"R: "+i, Toast.LENGTH_SHORT).show();
                break;
            case R.id.seekBar3:
                colorG = i;
                //Toast.makeText(getApplicationContext(),"G: "+i, Toast.LENGTH_SHORT).show();
                break;
            case R.id.seekBar5:
                colorB = i;
                //Toast.makeText(getApplicationContext(),"B: "+i, Toast.LENGTH_SHORT).show();
                break;
        }

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        switch (seekBar.getId()){
            case R.id.seekBar:
                Toast.makeText(getApplicationContext(),"R: "+seekBar.getProgress(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.seekBar3:
                Toast.makeText(getApplicationContext(),"G: "+seekBar.getProgress(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.seekBar5:
                Toast.makeText(getApplicationContext(),"B: "+seekBar.getProgress(), Toast.LENGTH_SHORT).show();
                break;
        }
        color.setBackgroundColor(Color.argb(255, colorR, colorG, colorB));
        if(existeFoto) {
            procesarFoto();
        }
    }

    private void procesarFoto(){
        int h = bmp.getHeight();
        int w = bmp.getWidth();
        int color[]  = {colorR, colorG, colorB};
        int hastaquecolor[] = {colorR + 10, colorG + 10, colorB + 10};
        imagen.setImageBitmap(
                ProcesarImagen.MatToBit(
                        ProcesarImagen.BinScale(
                                ProcesarImagen.GrayScale(data_imagen, h, w), h, w, color, hastaquecolor),
                        h, w)
        );
    }
}
