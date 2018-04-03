package com.example.tina.secretmessage;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.util.Log;
import android.widget.Toast;

import com.kosalgeek.android.photoutil.ImageLoader;

import java.io.FileNotFoundException;

/**
 * Created by Tina on 15.12.2016..
 */

public class BitmapWork {
    static public Bitmap GetBitmap( Bitmap bitmap, int w, int h)
    {
            //Bitmap bitmap = ImageLoader.init().from(photoPath).getBitmap();
            Bitmap ret;
            int sirinaSlike = bitmap.getWidth();
            int visinaSlike = bitmap.getHeight();



            if(sirinaSlike > visinaSlike){
                Matrix matrix = new Matrix();
                matrix.postRotate(-90);
                ret = Bitmap.createBitmap(bitmap , 0, 0, sirinaSlike, visinaSlike, matrix, true);
            }
            else{
                Matrix matrix = new Matrix();
                matrix.postRotate(0);
                ret = Bitmap.createBitmap(bitmap , 0, 0, sirinaSlike, visinaSlike, matrix, true);
            }
            ret = Bitmap.createScaledBitmap(ret, w,h,false);
            return ret;

    }
}
