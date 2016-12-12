package com.ia.alejandro.objectcolor;

import android.graphics.Bitmap;

/**
 * Created by Alejandro on 11/12/2016.
 */

public class ProcesarImagen {
    public static int[][][] BitToMat(Bitmap bmp) {
        int picw = bmp.getWidth();
        int pich = bmp.getHeight();
        int[] pix = new int[picw * pich];
        bmp.getPixels(pix, 0, picw, 0, 0, picw, pich);
        int matriz[][][] = new int[picw][pich][4];
        for (int y = 0; y < pich; y++){
            for (int x = 0; x < picw; x++) {
                int index = y * picw + x;
                matriz[x][y][0] = (pix[index] >> 24) & 0xff;
                matriz[x][y][1] = (pix[index] >> 16) & 0xff;
                matriz[x][y][2] = (pix[index] >> 8) & 0xff;
                matriz[x][y][3] =  pix[index] & 0xff;
            }
        }
        return matriz;
    }

    public static int[][][] GrayScale(int mat[][][], int h, int w){
        int matriz[][][] = new int[w][h][4];
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                int r = mat[x][y][1];
                int g = mat[x][y][2];
                int b = mat[x][y][3];
                int R = (int) ((r + g + b)/3);
                matriz[x][y][1] = R;
                matriz[x][y][2] = R;
                matriz[x][y][3] = R;
            }
        }
        return matriz;
    }

    public static int[][][] BinScale(int mat[][][], int h, int w, int[] colorRGB1, int[]colorRGB2){
        int matriz[][][] = new int[w][h][4];
        int grayUno = (int) ((colorRGB1[0] + colorRGB1[1] + colorRGB1[2])/3);
        int grayDos = (int) ((colorRGB2[0] + colorRGB2[1] + colorRGB2[2])/3);
        int negro = 0;
        int blanco = 255;
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                int r = mat[x][y][1];
                int g = mat[x][y][2];
                int b = mat[x][y][3];
                int R = (int) ((r + g + b)/3);
                if(R >= grayUno && R <= grayDos){
                    matriz[x][y][1] = negro;
                    matriz[x][y][2] = negro;
                    matriz[x][y][3] = negro;
                }else{
                    matriz[x][y][1] = blanco;
                    matriz[x][y][2] = blanco;
                    matriz[x][y][3] = blanco;
                }

            }
        }
        return matriz;
    }

    public static Bitmap MatToBit(int mat[][][], int h, int w) {
        int[] pix = new int[w * h];
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                int index = y * w + x;
                pix[index] = 0xff000000 | (mat[x][y][1] << 16) | (mat[x][y][2] << 8) | mat[x][y][3];
            }
        }
        Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_4444);
        bm.setPixels(pix, 0, w, 0, 0, w, h);
        return bm;
    }
}
