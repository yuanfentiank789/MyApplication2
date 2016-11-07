package com.example.apple.myapplication;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

/**
 * 所有 Android 设备都有两个文件存储区域：“内部”和“外部”存储。这些名称在 Android 早期产生，当时大多数设备都提供内置的非易失性内存（内部存储），
 * 以及移动存储介质，比如微型 SD 卡（外部存储）。一些设备将永久性存储空间划分为“内部”和“外部”分区，即便没有移动存储介质，也始终有两个存储空间，
 * 并且无论外部存储设备是否可移动，API 的行为均一致。
 */
public class DataStorageActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();
    TextView internalPublicDirTv, externalPublicDirTv, freeSpaceTvExt, freeSpaceTvInt, metricTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_storage);

        internalPublicDirTv = (TextView) findViewById(R.id.internal_public_directory);
        externalPublicDirTv = (TextView) findViewById(R.id.external_public_directory);
        freeSpaceTvExt = (TextView) findViewById(R.id.freespaceext);
        freeSpaceTvInt = (TextView) findViewById(R.id.freespaceint);
        metricTv = (TextView) findViewById(R.id.displaymetric);
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        MyDisplayMetrics myDisplayMetrics = new MyDisplayMetrics(metrics);
        metricTv.setText(myDisplayMetrics.toString());
        getInternalPublicDirectory();
        getExternalPublicDirectory();
        readSDCardInfo();
        readSystemInfo();
    }


    /**
     * 您无需任何权限，即可在内部存储中保存文件。 您的应用始终具有在其内部存储目录中进行读写的权限
     */
    public void getInternalPublicDirectory() {
        // Get the directory for the user's public pictures directory.

        internalPublicDirTv.append("getFilesDir:" + getFilesDir() + "\n");
        internalPublicDirTv.append("getCacheDir:" + getCacheDir() + "\n");//android 剩余空间不多时，系统可能好会删除cache下的文件
        testOpenFileOutput();
        File tempFile = getTempFile(this, "temp");
        internalPublicDirTv.append("createTempFile:" + tempFile.getAbsolutePath() + "\n");
        internalPublicDirTv.append("fileList:" + Arrays.toString(fileList()) + "\n");//files目录下


    }

    /**
     * 尽管外部存储可被用户和其他应用进行修改，但您可在此处保存两类文件：
     * <p/>
     * 公共文件
     * 应供其他应用和用户自由使用的文件。 当用户卸载您的应用时，用户应仍可以使用这些文件。
     * 例如，您的应用拍摄的照片或其他已下载的文件。
     * <p/>
     * 私有文件
     * 本属于您的应用且应在用户卸载您的应用时删除的文件。尽管这些文件在技术上可被用户和其他应用访问（因为它们在外部存储上），它们是实际上不向您的应用之外的用户提供值的文件。当用户卸载您的应用时，系统会删除应用外部专用目录中的所有文件。
     * 例如，您的应用下载的其他资源或临时介质文件。
     */
    public void getExternalPublicDirectory() {
        externalPublicDirTv.append("\nPRIVATE:\n");
        externalPublicDirTv.append("ExternalFilesDir:" + getExternalFilesDir(null) + "\n");//参数为null则返回根目录,
        externalPublicDirTv.append("ExternalFilesDirs:" + Arrays.toString(ContextCompat.getExternalFilesDirs(this, null)) + "\n");//有的Android手机，系统已经从内部存储中分配了external空间，同时又提供了sd卡插槽，有多个external空间，该方法返回一个external数组，
        externalPublicDirTv.append("ExternalFilesDirByType:" + new File(getExternalFilesDir(
                Environment.DIRECTORY_PICTURES), "pics") + "\n");
        externalPublicDirTv.append("ExternalCacheDir:" + getExternalCacheDir() + "\n");//公共存储根目录
        externalPublicDirTv.append("\nPUBLIC:\n");
        externalPublicDirTv.append("ExternalStorageDirectory:" + Environment.getExternalStorageDirectory() + "\n");//公共存储根目录
//        externalPublicDirTv.append("ExternalStoragePublicDirectory:" + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + "\n");

    }

    private void testOpenFileOutput() {
        String filename = "myfile";
        String string = "Hello world!";
        FileOutputStream outputStream;

        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);//目录同getFilesDir，包名/files
            outputStream.write(string.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public File getTempFile(Context context, String url) {
        File file = null;
        try {
            String fileName = Uri.parse(url).getLastPathSegment();
            file = File.createTempFile(fileName, null, context.getCacheDir());
        } catch (IOException e) {
            // Error while creating file
        }
        return file;
    }

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }


    public File getAlubmExternaPublicDir(Context context, String albumName) {
        // Get the directory for the app's private pictures directory.
        File file = new File(context.getExternalFilesDir(
                Environment.DIRECTORY_PICTURES), albumName);
        file = Environment.getExternalStorageDirectory();
        if (!file.mkdirs()) {
            Log.e(TAG, "Directory not created");
        }
        return file;
    }


    void readSDCardInfo() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            File sdcardDir = Environment.getExternalStorageDirectory();
            StatFs sf = new StatFs(sdcardDir.getPath());
            long blockSize = sf.getBlockSize();
            long blockCount = sf.getBlockCount();
            long availCount = sf.getAvailableBlocks();
            freeSpaceTvExt.append("block大小:" + blockSize + ", block数目:" + blockCount + ", 总大小:" + blockSize * blockCount / 1024 / 1024 + "MB");
            freeSpaceTvExt.append("可用的block数目：:" + availCount + ",剩余空间:" + availCount * blockSize / 1024 / 1024 + "MB");
        }
    }

    void readSystemInfo() {
        File root = Environment.getRootDirectory();
        StatFs sf = new StatFs(root.getPath());
        long blockSize = sf.getBlockSize();
        long blockCount = sf.getBlockCount();
        long availCount = sf.getAvailableBlocks();
        freeSpaceTvInt.append("block大小:" + blockSize + ",block数目:" + blockCount + ",总大小:" + blockSize * blockCount / 1024 / 1024 + "MB");
        freeSpaceTvInt.append("可用的block数目：:" + availCount + ",可用大小:" + availCount * blockSize / 1024 / 1024 + "MB");
    }

    class MyDisplayMetrics extends DisplayMetrics {

        private DisplayMetrics mMetrics;

        public MyDisplayMetrics(DisplayMetrics metrics) {
            mMetrics = metrics;
        }

        @Override
        public String toString() {
            // return "DisplayMetrics{density=" + density + ", width=" +
            // widthPixels +
            // ", height=" + heightPixels + ", scaledDensity=" + scaledDensity +
            // ", xdpi=" + xdpi + ", ydpi=" + ydpi + "}";
            StringBuilder sb = new StringBuilder();
            sb.append(mMetrics.toString());
            sb.append("+densityDpi=" + mMetrics.densityDpi);
            return sb.toString();
        }

    }
}
