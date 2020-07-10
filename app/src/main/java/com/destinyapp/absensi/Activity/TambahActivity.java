package com.destinyapp.absensi.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.destinyapp.absensi.R;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCameraView;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class TambahActivity extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener2 {
    JavaCameraView javaCameraView;
    File caseFile;
    CascadeClassifier faceDetector;
    private Mat mRgba,mGrey;
    Dialog myDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah);

        //Request Permission
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        OpenCVLoader.initDebug();

                        javaCameraView = (JavaCameraView)findViewById(R.id.JavaCamView);
                        if (!OpenCVLoader.initDebug()){
                            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_0_0,TambahActivity.this,baseCallback);
                        }
                        else
                        {
                            try {
                                baseCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                        javaCameraView.setCvCameraViewListener(TambahActivity.this);
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        Intent intent = new Intent(TambahActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                    }
                }).check();
    }

    @Override
    public void onCameraViewStarted(int width, int height) {
        mRgba = new Mat();
        mGrey = new Mat();
    }

    @Override
    public void onCameraViewStopped() {
        mRgba.release();
        mGrey.release();
    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        mRgba = inputFrame.rgba();
        mGrey = inputFrame.gray();

        //Detect Face

        MatOfRect faceDetection = new MatOfRect();
        faceDetector.detectMultiScale(mRgba,faceDetection);
        if (faceDetection.toArray().length > 0){
            Intent intent = new Intent(TambahActivity.this,MainActivity.class);
            startActivity(intent);
            int i=0;
            Toast.makeText(this, "Penambahan Karyawan berhasil Ditambahkan", Toast.LENGTH_SHORT).show();
            for (Rect rect: faceDetection.toArray()){
//            Toast.makeText(this, faceDetection.toString(), Toast.LENGTH_SHORT).show();

                Imgproc.rectangle(mRgba,new Point(rect.x,rect.y),
                        new Point(rect.x+rect.width,rect.y+rect.height),
                        new Scalar(255,0,0));

                Rect rectCrop = new Rect(rect.x, rect.y, rect.width, rect.height);

                Mat image_roi = new Mat(mRgba,rectCrop);
                Imgcodecs.imwrite("./face"+ i +".jpg",image_roi);
                i++;
            }
        }

        return mRgba;
    }


    private BaseLoaderCallback baseCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) throws IOException {
            switch (status)
            {
                case LoaderCallbackInterface.SUCCESS:
                {
                    InputStream is = getResources().openRawResource(R.raw.haarcascade_frontalface_alt2);
                    File cascadeDir = getDir("cascade", Context.MODE_PRIVATE);
                    caseFile = new File(cascadeDir,"haarcascade_frontalface_alt2.xml");

                    FileOutputStream fos = new FileOutputStream(caseFile);
                    byte[] buffers = new byte[4096];
                    int bytesRead;

                    while ((bytesRead = is.read(buffers))!=-1)
                    {
                        fos.write(buffers,0,bytesRead);
                    }
                    is.close();
                    fos.close();

                    faceDetector = new CascadeClassifier(caseFile.getAbsolutePath());

                    if(faceDetector.empty()){
                        faceDetector = null;
                    }else{
                        cascadeDir.delete();
                    }
                    javaCameraView.enableView();
                }
                break;
                default: {
                    super.onManagerConnected(status);
                }
                break;

            }
        }
    };

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(TambahActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}