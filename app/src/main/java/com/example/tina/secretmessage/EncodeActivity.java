package com.example.tina.secretmessage;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class EncodeActivity extends AppCompatActivity {

    EditText ed_text;
    Bitmap img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encode);

        Intent intent = getIntent();
        String s = intent.getStringExtra("slika");

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        img  = BitmapFactory.decodeFile(s, options);
        img = img.copy(img.getConfig(), true);
        ed_text = (EditText) findViewById(R.id.ed_text);


        File file = new File(s);
        boolean a = file.delete();

    }

    public void onClickText(View view){
       ed_text.setText("");
    }

    public void onClickEncodeText(View view) {
        //String text = "Tina";
        String text = ed_text.getText().toString();
        int duzina = text.length();
        int b = 0;
        for (int i = 0; i < img.getWidth(); i++)
        {
            for (int j = 0; j < img.getHeight(); j++)
            {
                int pixel = img.getPixel(i, j);
                //RGB
                if (b < duzina)
                {
                    //Console.WriteLine("R[" + i + "][" + j + "] : " + pixel.R);
                    //Console.WriteLine("G[" + i + "][" + j + "] : " + pixel.G);
                    //Console.WriteLine("B[" + i + "][" + j + "] : " + pixel.B);
                    char letter = text.charAt(j);
                    int value = letter;
                    //Console.WriteLine("Letter: " + letter + "\n Value: " + value);
                    img.setPixel(i, j, Color.rgb(Color.red(pixel), Color.green(pixel), value));
                    b+=1;
                }

                else{
                    i = img.getWidth();
                    j = img.getHeight();
                }

            }
        }
        int p = img.getPixel(img.getWidth() - 1,  img.getHeight() - 1);
        img.setPixel(img.getWidth() - 1, img.getHeight() - 1, Color.rgb(Color.red(p), Color.green(p), text.length()));
        try {
            String path = Environment.getExternalStorageDirectory().toString();
            OutputStream fOut = null;
            Integer counter = 5;

            File file = new File(path + "/" + Environment.DIRECTORY_PICTURES + "/Steganography/");
            boolean success = true;
            if (!file.exists()) {
                //Toast.makeText(MainActivity.this, "Directory Does Not Exist, Create It", Toast.LENGTH_SHORT).show();
                success = file.mkdir();
            }
            file = new File(path + "/" + Environment.DIRECTORY_PICTURES + "/Steganography/", "Steganography" + counter + ".jpg");

            fOut = new FileOutputStream(file);

            Bitmap pictureBitmap = img; // obtaining the Bitmap
            pictureBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut); // saving the Bitmap to a file compressed as a JPEG with 85% compression rate
            fOut.flush(); // Not really required
            fOut.close(); // do not forget to close the stream

            //MediaStore.Images.Media.insertImage(getContentResolver(), file.getAbsolutePath(), file.getName(), file.getName());

            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));


        }catch (Exception e){Log.i("--T>", "" + e.getMessage());}

    }


}
