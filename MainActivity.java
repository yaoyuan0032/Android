package com.example.touch_screen;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.Image;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.view.ScaleGestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {


    String currentPhotoPath;

    private Uri filep;

    static final int PICK_IMAGE = 0;
    ImageView img;

    TouchImageView touchImageView;


    private Rect mContentRect;
    //private ScaleGestureDetector mScaleGestureDetector;


    private float mScaleFactor = 1.0f;

    Matrix matrix = new Matrix();
    Float scale = 1f;
    ScaleGestureDetector SGD;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_gallery = (Button) findViewById(R.id.btngallery);
        img = (ImageView) findViewById(R.id.img);

        SGD = new ScaleGestureDetector(this, new ScaleListener());



        btn_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickimage();
            }
        });




        img.setOnTouchListener(new View.OnTouchListener() {

            PointF DownPT = new PointF(); // record the mouse Positon when Pressed Down
            PointF StartPT = new PointF(); // Record start Position of img

            @Override
            public boolean onTouch(View v, MotionEvent event) {


                SGD.onTouchEvent(event);


                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        img.setX((int) (StartPT.x + event.getX() - DownPT.x));
                        img.setY((int) (StartPT.y + event.getY() - DownPT.y));
                        StartPT.set(img.getX(), img.getY());
                        break;
                    case MotionEvent.ACTION_DOWN:
                        DownPT.set(event.getX(), event.getY());
                        StartPT.set(img.getX(), img.getY());
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                    default:
                        break;

                }

                return true;

            }


        });


    }




    /*private  class ScaleListener extends
            ScaleGestureDetector.SimpleOnScaleGestureListener{

        @Override
        public boolean onScale(ScaleGestureDetector scaleGestureDetector){



            mScaleFactor *= scaleGestureDetector.getScaleFactor();

            mScaleFactor = Math.max(0.1f,Math.min(mScaleFactor, 10.0f));

            img.setScaleX(mScaleFactor);
            img.setScaleY(mScaleFactor);

            return  true;
        }
    }*/

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            scale = scale * detector.getScaleFactor();
            scale = Math.max(0.1f, Math.min(scale, 5f));
            matrix.setScale(scale, scale);
            img.setImageMatrix(matrix);

            return true;
        }
    }











    private void pickimage(){

        Intent pic = new Intent();
        pic.setType("image/*");
        pic.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(pic, "Select picture"), PICK_IMAGE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE) {


            filep = data.getData();

            Picasso.with(this).load(filep).into(img);

        }
        }




    }



