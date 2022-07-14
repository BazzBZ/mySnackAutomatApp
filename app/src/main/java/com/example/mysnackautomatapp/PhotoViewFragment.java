package com.example.mysnackautomatapp;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.ImageFormat;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.Image;
import android.media.ImageReader;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class PhotoViewFragment extends Fragment {

    private static final String TAG = PhotoViewFragment.class.getSimpleName();

    private static final int PERMISSIONS_REQUEST_CAMERA = 123;

    private SurfaceHolder holder;
    private CameraManager cameramanager;
    private String cameraID;
    private CameraDevice cameraDevice;
    private CameraCaptureSession activeSession;
    private CaptureRequest.Builder builderPicture;
    private CaptureRequest.Builder builderPreview;
    private SurfaceView surfaceView;
    private ImageReader imageReader;

    //private final CameraCaptureSession.CaptureCallback captureCallback = null;

    private final CameraCaptureSession.StateCallback captureSessionCallback =
            new CameraCaptureSession.StateCallback() {
                @Override
                public void onConfigured(CameraCaptureSession cameraCaptureSession) {
                    try {
                        //cameraCaptureSession.setRepeatingRequest(builderPreview.build(), captureCallback, null);
                        cameraCaptureSession.setRepeatingRequest(builderPreview.build(), null, null);
                        PhotoViewFragment.this.activeSession = cameraCaptureSession;
                    } catch(CameraAccessException e){
                        Log.e(TAG, "onConfigured()", e);
                    }
                }

                @Override
                public void onConfigureFailed(CameraCaptureSession cameraCaptureSession) {
                    Log.e(TAG, "onConfigureFailed()");
                }
            };

    private final SurfaceHolder.Callback surfaceHolderCallback =
            new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(SurfaceHolder surfaceHolder) {
                    Log.d(TAG, "surfaceCreated()");
                    try {
                        openCamera();
                    } catch (SecurityException | CameraAccessException e) {
                        Log.e(TAG, "openCamera()", e);
                    }
                }

                @Override
                public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
                    Log.d(TAG, "surfaceChanged()");
                }

                @Override
                public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                    Log.d(TAG, "surfaceDestroyed()");
                }
            };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {

        return inflater.inflate(R.layout.photo_view_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        surfaceView = view.findViewById(R.id.surfaceView);
        View.OnClickListener ocl = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    EditText editText = new EditText(getContext());
                    editText.setInputType(InputType.TYPE_CLASS_TEXT);
                    MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(getContext());
                    String comment;
                    materialAlertDialogBuilder
                            .setTitle("Comment")
                            .setNeutralButton("Cancel", (dialog, which) -> {
                                Toast.makeText(getContext(),"No Comment added", Toast.LENGTH_SHORT).show();
                            })
                            .setView(editText)
                            .setPositiveButton("Save", (dialog, which) -> {
                                Toast.makeText(getContext(),"Comment added", Toast.LENGTH_SHORT).show();
                            })
                            .show();
                    activeSession.capture(builderPicture.build(),null, new Handler());
                } catch (CameraAccessException e){
                    Log.e(TAG, "takePicture()", e);
                }
            }
        };
        surfaceView.setOnClickListener(ocl);
        holder = null;
        cameraDevice = null;
        cameraID = null;
    }



    @Override
    public void onPause(){
        super.onPause();
        surfaceView.setVisibility(View.GONE);
        if (cameraDevice != null){
            if(activeSession!= null){
                activeSession.close();
                activeSession = null;
            }
            cameraDevice.close();
            cameraDevice = null;
        }
        if (holder != null){
            holder.removeCallback(surfaceHolderCallback);
        }
        Log.d(TAG, "onPause()");
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getContext().checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[] {Manifest.permission.CAMERA}, PERMISSIONS_REQUEST_CAMERA);
        } else {
            configureHolder();
        }
        Log.d(TAG, "onResume()");
    }

    @Override
    public void onRequestPermissionsResult (int requestCode, String permissions[], int[] grantResults){
        if((requestCode == PERMISSIONS_REQUEST_CAMERA) &&
                (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)){
            configureHolder();
        }
    }

    private void configureHolder(){
        holder = surfaceView.getHolder();
        holder.addCallback(surfaceHolderCallback);
        cameramanager = getContext().getSystemService(CameraManager.class);
        Size[] sizes = findCameraFacingBack();
        if ((cameraID == null) || sizes == null) {
            Log.d(TAG, "keine passende Kamera gefunden");
            finish();
        } else {
            DisplayMetrics metrics = getResources().getDisplayMetrics();
            int _w = metrics.widthPixels;
            int _h = metrics.heightPixels;
            boolean found = false;
            for (Size size : sizes) {
                int width = size.getWidth();
                int height = size.getHeight();
                if (width>_w || height>_h){
                    continue;
                }
                holder.setFixedSize(width,height);
                found = true;
                imageReader = ImageReader.newInstance(width, height, ImageFormat.JPEG, 2);
                ImageReader.OnImageAvailableListener oial = new ImageReader.OnImageAvailableListener() {
                    @Override
                    public void onImageAvailable(ImageReader imageReader) {
                        Log.d(TAG, "setOnImageAvailableListener()");
                        Image image = imageReader.acquireLatestImage();
                        final Image.Plane[] planes = image.getPlanes();
                        ByteBuffer buffer = planes[0].getBuffer();
                        saveJPG(buffer);
                        image.close();
                    }
                };
                imageReader.setOnImageAvailableListener(oial, null);
                break;
            }
            if (!found) {
                Log.d(TAG, "Zu groß");
                finish();
            }
        }
        surfaceView.setVisibility(View.VISIBLE);
    }

    private Size[] findCameraFacingBack(){
        Size[] sizes = null;
        try {
            boolean found = false;
            String[] ids = cameramanager.getCameraIdList();
            for (String id : ids){
                CameraCharacteristics cc = cameramanager.getCameraCharacteristics(id);
                Log.d(TAG, id + ": " + cc.toString());
                Integer lensFacing = cc.get((CameraCharacteristics.LENS_FACING));
                if ((lensFacing!= null) && (lensFacing == CameraCharacteristics.LENS_FACING_BACK)) {
                    if(found){
                        continue;
                    }
                    found = true;
                    cameraID = id;
                    StreamConfigurationMap configs = cc.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
                    if (configs != null){
                        sizes = configs.getOutputSizes(SurfaceHolder.class);
                    }
                }
            }
        } catch (CameraAccessException | NullPointerException e) {
            Log.e(TAG, "findCameraFacingBack()", e);
        }
        return sizes;
    }

    private void openCamera() throws SecurityException, CameraAccessException {
        cameramanager.openCamera(cameraID, new CameraDevice.StateCallback() {
            @Override
            public void onOpened( CameraDevice cameraDevice) {
                Log.d(TAG, "onOpened()");
                PhotoViewFragment.this.cameraDevice = cameraDevice;
                PhotoViewFragment.this.createPreviewCaptureSession();
            }

            @Override
            public void onDisconnected(CameraDevice cameraDevice) {
                Log.d(TAG, "onDisconnected");
            }

            @Override
            public void onError(CameraDevice cameraDevice, int i) {
                Log.d(TAG, "onError()");
            }
        }, null);
    }

    private void createPreviewCaptureSession(){
        List<Surface> outputs = new ArrayList<>();
        outputs.add(holder.getSurface());
        try{
            Surface surface = imageReader.getSurface();
            outputs.add(surface);
            builderPicture = cameraDevice.createCaptureRequest(
                    CameraDevice.TEMPLATE_STILL_CAPTURE);
            builderPicture.addTarget(surface);
            builderPreview = cameraDevice.createCaptureRequest(
                    CameraDevice.TEMPLATE_PREVIEW);
            builderPreview.addTarget(holder.getSurface());
            cameraDevice.createCaptureSession(outputs, captureSessionCallback, new Handler());
        } catch (Exception e){
            Log.e(TAG, "createPreviewCaptureSessions()", e);
        }
    }

    private void saveJPG(ByteBuffer data){
        File dir = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (dir!=null){
            if( dir.mkdirs()){
                Log.d(TAG, "dirs created");
            }
            File f = new File (dir, TAG+"_" + Long.toBinaryString(System.currentTimeMillis())+ ".jpg");
            Log.d(TAG, "Dateiname: "+ f.getAbsolutePath());
            try (
                    FileOutputStream fos = new FileOutputStream(f);
                    BufferedOutputStream bos = new BufferedOutputStream(fos)) {
                while (data.hasRemaining()) {
                    bos.write(data.get());
                }
                addToMediaProvider(f);
            } catch (IOException e) {
                Log.e(TAG, "saveJPG", e);

            }
        }
    }

    private void addToMediaProvider(File f){
        MediaScannerConnection.OnScanCompletedListener oscl = new MediaScannerConnection.OnScanCompletedListener() {
            @Override
            public void onScanCompleted(String s, Uri uri) {
                Intent i = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(i);
            }
        };
        MediaScannerConnection.scanFile(this.getContext(), new String[] {f.toString()}, new String[] {"image/jpeg"},oscl);
    }

    public void finish() {
        throw new RuntimeException("Stub!");
    }
}