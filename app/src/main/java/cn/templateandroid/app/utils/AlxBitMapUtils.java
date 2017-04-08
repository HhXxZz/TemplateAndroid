package cn.templateandroid.app.utils;

/**
 * Created by Administrator on 2017/2/24.
 */

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Build;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by Administrator on 2016/4/6.
 */
public class AlxBitMapUtils {

    /**
     * 传入一个bitmap，根据传入比例进行大小缩放
     * @param bitmap
     * @param widthRatio 宽度比例，缩小就比1小，放大就比1大
     * @param heightRatio
     * @return
     */
    public static Bitmap scaleBitmap(Bitmap bitmap, float widthRatio, float heightRatio) {
        Matrix matrix = new Matrix();
        matrix.postScale(widthRatio,heightRatio);
        return Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
    }

    /**
     * 传入图片路径，根据图片进行压缩，仅压缩大小，不压缩质量
     * @param oriFile 源文件
     * @param targetFile 这个和 stream传一个就行
     * @param ifDel 是否需要在压缩完毕后删除原图
     */
    public static void compressImage(File oriFile, File targetFile, OutputStream stream, boolean ifDel) {
        if(oriFile ==null)return;
        Log.i("Alex","源图片为"+oriFile);
        Log.i("Alex","目标地址为"+targetFile);
        try {
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inJustDecodeBounds = true; // 不读取像素数组到内存中，仅读取图片的信息，非常重要
            BitmapFactory.decodeFile(oriFile.getAbsolutePath(), opts);//读取文件信息，存放到Options对象中
            // 从Options中获取图片的分辨率
            int imageHeight = opts.outHeight;
            int imageWidth = opts.outWidth;
            int longEdge = Math.max(imageHeight,imageWidth);//取出宽高中的长边
            int pixelCount = (imageWidth*imageHeight)>>20;//看看这张照片有多少百万像素
            Log.i("Alex","图片宽为"+imageWidth+"图片高为"+imageHeight+"图片像素数为"+pixelCount+"百万像素");

            long size = oriFile.length();
            Log.i("Alex","f.length 图片大小为"+(size)+" B");
            //走到这一步的时候，内存里还没有bitmap
            Bitmap bitmap = null;
            if(pixelCount > 4){//如果超过了4百万像素，那么就首先对大小进行压缩
                float compressRatio = longEdge /1280f;
                int compressRatioInt = Math.round(compressRatio);
                if(compressRatioInt%2!=0 && compressRatioInt!=1)compressRatioInt++;//如果是奇数的话，就给弄成偶数
                Log.i("Alex","长宽压缩比是"+compressRatio+" 偶数化后"+compressRatioInt);
                //尺寸压缩
                BitmapFactory.Options options = new BitmapFactory.Options();
                //目标出来的大小1024*1024 1百万像素，100k左右
                options.inSampleSize = Math.round(compressRatioInt);//注意，此处必须是偶数，根据计算好的比例进行压缩,如果长边没有超过1280*1.5，就不去压缩,否则就压缩成原来的一半
                options.inJustDecodeBounds = false;//在decode file的时候，不仅读取图片的信息，还把像素数组到内存
                options.inPreferredConfig = Bitmap.Config.RGB_565;//每个像素占四位，即R=5，G=6，B=5，没有透明度，那么一个像素点占5+6+5=16位
                //现在开始将bitmap放入内存
                bitmap = BitmapFactory.decodeFile(oriFile.getAbsolutePath(), options);//根据压缩比取出大小已经压缩好的bitmap
                //此处会打印出存入内存的bitmap大小
            }else {//如果是长图或者长边短于1920的图，那么只进行质量压缩
                // 现在开始将bitmap放入内存
                bitmap = BitmapFactory.decodeFile(oriFile.getAbsolutePath());
                //此处会打印出bitmap在内存中占得大小
            }
            if(targetFile!=null)compressMethodAndSave(bitmap, targetFile);
            if(stream!=null)compressBitmapToStream(bitmap,stream);
            if(ifDel) oriFile.delete();//是否要删除源文件
            System.gc();
        }catch (Exception e){
            Log.d("Alex",""+e.getMessage().toString());
        }
    }

    /**
     * 获取一个bitmap在内存中所占的大小
     * @param image
     * @return
     */
    private static int getSize(Bitmap image){
        int size=0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {    //API 19
            size = image.getAllocationByteCount();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {//API 12
            size = image.getByteCount();
        } else {
            size = image.getRowBytes() * image.getHeight();
        }
        return size;
    }

    /**
     * 根据传来的bitmap的大小计算一个质量压缩率，并且保存到指定路径中去，只压缩质量，不压缩大小
     * @param image
     * @param targetFile
     */
    public static void compressMethodAndSave(Bitmap image, File targetFile){
        try {
            OutputStream stream = new FileOutputStream(targetFile);
            int size = compressBitmapToStream(image,stream);
            if(size==0)return;
            long afterSize = targetFile.length();
            Log.i("Alex","压缩完后图片大小"+(afterSize>>10)+"KB 压缩率:::"+afterSize*100/size+"%");
        }catch (Exception e){
            Log.i("Alex","压缩图片出现异常",e);
        }
    }

    public static int compressBitmapToStream(Bitmap image, OutputStream stream){
        if(image==null || stream==null)return 0;
        try {
            Bitmap.CompressFormat format = Bitmap.CompressFormat.JPEG;
            int size = getSize(image);
            Log.i("Alex","存入内寸的bitmap大小是"+(size>>10)+" KB 宽度是"+image.getWidth()+" 高度是"+image.getHeight());
            int quality = getQuality(size);//根据图像的大小得到合适的有损压缩质量
            Log.i("Alex","目前适用的有损压缩率是"+quality);
            long startTime = System.currentTimeMillis();
            image.compress(format, quality, stream);//压缩文件并且输出
            if (image != null) {
                image.recycle();//此处把bitmap从内存中移除
                image = null;
            }
            Log.i("Alex","压缩图片并且存储的耗时"+(System.currentTimeMillis()-startTime));
            return size;
        }catch (Exception e){
            Log.i("Alex","压缩图片出现异常",e);
        }
        return 0;
    }

    /**
     * 根据图像的大小得到合适的有损压缩质量，因为此时传入的bitmap大小已经比较合适了，靠近1000*1000，所以根据其内存大小进行质量压缩
     * @param size
     * @return
     */
    private static int getQuality(int size){
        int mb=size>>20;//除以100万，也就是m
        int kb = size>>10;
        Log.i("Alex","准备按照图像大小计算压缩质量，大小是"+kb+"KB,兆数是"+mb);
        if(mb>70){
            return 17;
        }else if(mb>50){
            return 20;
        }else if(mb>40){
            return 25;
        }else if(mb>20){
            return 40;
        }else if(mb>10){
            return 60;
        }else if(mb>3){//目标压缩大小 100k，这里可根据实际情况来判断
            return 60;
        }else if(mb>=2){
            return 60;
        }else if(kb > 1500){
            return 70;
        }else if(kb > 1000){
            return 80;
        }else if(kb>500){
            return 85;
        }else if(kb>100){
            return 90;
        }
        else{
            return 100;
        }
    }

    /**
     * 从assets文件夹中根据文件名得到一个Bitmap
     * @param fileName
     * @return
     */
    public static Bitmap getDataFromAssets(Context context, String fileName){
        Log.i("Alex","准备从assets文件夹中读取文件"+fileName);
        try {
            //可以直接使用context.getResources().getAssets().open(fileName);得到一个InputStream再用BufferedInputStream通过缓冲区获得字符数组
            AssetFileDescriptor descriptor = context.getResources().getAssets().openFd(fileName);//此处获得文件描述之后可以得到FileInputStream，然后使用NIO得到Channel
            long fileSize = descriptor.getLength();
            Log.i("Alex","要读取的文件的长度是"+fileSize);//注意这个地方如果文件大小太大，在decodeStream需要设置参数进行裁剪
            Bitmap bitmap = BitmapFactory.decodeStream(context.getResources().getAssets().open(fileName));
//            Bitmap bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor);//注意，AssetFileDescriptor只能用来获取文件的大小，不能用来获取inputStream，用FileDescriptor获取的输入流BitmapFactory.decodeStream不能识别
            if(bitmap==null) Log.i("Alex","decode bitmap失败");
            return bitmap;
        } catch (Exception e) {
            Log.i("Alex","读取文件出现异常",e);
            e.printStackTrace();
        }
        return null;
    }

    public static boolean copyFile(File sourceFile, File targetFile){
        //NIO中读取数据的步骤：1）从FileInputStream中得到Channel对象;2)创建一个buffer对象;3)从Channel中读数据到Buffer中;
        FileInputStream fin  = null;
        FileOutputStream fout = null;
        FileChannel fcin = null;
        FileChannel fcout = null;
        try {
            fin = new FileInputStream( sourceFile );
            fout = new FileOutputStream( targetFile );
            fcin = fin.getChannel();
            fcout =fout.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int r = 0;
            while ((r = fcin.read(buffer))!=-1) {
                buffer.clear();
                buffer.flip();//反转一下，从写入状态变成读取状态
                fcout.write(buffer);
            }
            return true;
        } catch (Exception e){
            Log.i("Alex","复制文件发生错误",e);
        }finally {
            if(fin!=null) try {
                fin.close();
                if(fout!=null)fout.close();
                if(fcin!=null)fcin.close();
                if(fcout!=null)fcout.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }




}