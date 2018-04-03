package com.example.tina.secretmessage;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.kosalgeek.android.photoutil.CameraPhoto;
import com.kosalgeek.android.photoutil.GalleryPhoto;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getName();

    //EditText et_password;

    ImageView ivCamera, ivGallery, ivUpload, ivImage;

    CameraPhoto cameraPhoto;
    GalleryPhoto galleryPhoto;

    String slicica;

    final int CAMERA_REQEST = 13323;
    final int GALLERY_REQEST = 22131;


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
            String[] perms = {"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.CAMERA"};
            int permsRequestCode = 200;
            requestPermissions(perms, permsRequestCode);
        }

        cameraPhoto = new CameraPhoto(getApplicationContext());
        galleryPhoto = new GalleryPhoto(getApplicationContext());

        //et_password = (EditText) findViewById(R.id.password);

        ivImage = (ImageView) findViewById(R.id.ivImage);
        ivCamera = (ImageView) findViewById(R.id.ivCamera);
        ivGallery = (ImageView) findViewById(R.id.ivGallery);
        ivUpload = (ImageView) findViewById(R.id.ivUpload);

        ivCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    startActivityForResult(cameraPhoto.takePhotoIntent(), CAMERA_REQEST);
                } catch (IOException e) {
                    Toast.makeText(getApplicationContext(),
                            "Something Wrong while taking photos", Toast.LENGTH_SHORT).show();
                }
            }
        });
        ivGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(galleryPhoto.openGalleryIntent(), GALLERY_REQEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == CAMERA_REQEST) {
                try {
                   // Bitmap bitmap = ImageLoader.init().from(cameraPhoto.getPhotoPath()).getBitmap();
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                    Bitmap bitmap = BitmapFactory.decodeFile(cameraPhoto.getPhotoPath(), options);
                    //bitmap = BitmapWork.GetBitmap(bitmap, ivImage.getWidth(), ivImage.getHeight());
                    ivImage.setImageBitmap(bitmap);
                    slicica = cameraPhoto.getPhotoPath();
                } catch (Exception e) {
                    Log.i("--T>", "" + e.getMessage());
                }

            } else if (requestCode == GALLERY_REQEST) {
                Uri uri = data.getData();
                galleryPhoto.setPhotoUri(uri);
                try {
                    //Bitmap bitmap = ImageLoader.init().from(galleryPhoto.getPath()).getBitmap();
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                    Bitmap bitmap = BitmapFactory.decodeFile(galleryPhoto.getPath(), options);
                   // bitmap = BitmapWork.GetBitmap(bitmap, ivImage.getWidth(), ivImage.getHeight());
                    ivImage.setImageBitmap(bitmap);
                    slicica = galleryPhoto.getPath();
                } catch (Exception e) {
                    Log.i("--T>", "" + e.getMessage());
                }
            }

        }

    }

    public void onClickEncode(View view) {
        Intent intent = new Intent(this, EncodeActivity.class);

        intent.putExtra("slika", slicica);
        startActivity(intent);
    }

    public void onClickDecode(View view) {

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap img  = BitmapFactory.decodeFile(slicica, options);

        int lpixel = img.getPixel(img.getWidth() - 1, img.getHeight() - 1);
            int messlen = Color.blue(lpixel);
            String message = "";

            int b = 0;
            for (int i = 0; i < img.getWidth(); i++)
            {
                for (int j = 0; j < img.getHeight(); j++)
                {
                    int pixel = img.getPixel(i, j);
                    //RGB
                    if (b < messlen)
                    {
                        //Console.WriteLine("R[" + i + "][" + j + "] : " + pixel.R);
                        //Console.WriteLine("G[" + i + "][" + j + "] : " + pixel.G);
                        //Console.WriteLine("B[" + i + "][" + j + "] : " + pixel.B);
                        int value = Color.blue(pixel);
                        //Console.WriteLine("Value: " + value);
                        char c = (char) value;

                        //string letter = System.Text.Encoding.ASCII.GetString(new byte[] { Convert.ToByte(c) });
                        String letter = "" + c;

                        message += letter;
                        b+=1;
                    }
                    else{
                        i = img.getWidth();
                        j = img.getHeight();
                    }
                }
            }
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            //string key = spc.Key(textBox1.Text);
            }


    //public void onClickText(View view){
    //    et_password.setText("");
    //}
}