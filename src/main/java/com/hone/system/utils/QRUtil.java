package com.hone.system.utils;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import org.bouncycastle.util.encoders.Base64Encoder;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Objects;

/**
 * 二维码生产工具类
 */
public class QRUtil {

    private static final int BLACK = 0xFF000000;
    private static final int WHITE = 0xFFFFFFFF;

    private QRUtil() {}

    public static BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
            }
        }
        return image;
    }

    public static void writeToFile(BitMatrix matrix, String format, File file)
            throws IOException {
        BufferedImage image = toBufferedImage(matrix);
        if (!ImageIO.write(image, format, file)) {
            throw new IOException("Could not write an image of format " + format + " to " + file);
        }
    }

    public static void writeToStream(BitMatrix matrix, String format, OutputStream stream)
            throws IOException {
        BufferedImage image = toBufferedImage(matrix);
        if (!ImageIO.write(image, format, stream)) {
            throw new IOException("Could not write an image of format " + format);
        }
    }

    public static String createImage(String urlCode) throws Exception {

        //获取项目根路径,把生成的图片保存在 /wxqrcode 目录下 对应服务器目录 tomcat/bin/
        String relativelyPath = System.getProperty("user.dir");
        String mkdirs=relativelyPath+"/wxqrcode/"+DateUtils.YYYYMMDD()+"/";
        String filename=relativelyPath+"/wxqrcode/"+DateUtils.YYYYMMDD()+"/"+System.currentTimeMillis()+".jpg";
        System.out.println(filename);
        File filedirs = new File(mkdirs);
        File file=new File(filename);
        if (!filedirs.isDirectory()){
            filedirs.mkdirs();
        }

        Hashtable hints = new Hashtable();
        //内容所使用编码
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        BitMatrix bitMatrix;
        bitMatrix = new MultiFormatWriter().encode(urlCode, BarcodeFormat.QR_CODE, 400, 400, hints);
        QRUtil.writeToFile(bitMatrix,"jpg",file);

        return imageToBase64(filename);

    }

    /**
     * 本地图片转换Base64的方法
     *
     * @param imgPath     
     */

    private static String imageToBase64(String imgPath) {
        byte[] data = null;
        // 读取图片字节数组
        try {
            InputStream in = new FileInputStream(imgPath);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        // 返回Base64编码过的字节数组字符串
        String base64Str=encoder.encode(Objects.requireNonNull(data));
        System.out.println("本地图片转换Base64:\r\n" + base64Str);
        return base64Str;
    }


    public static void main(String[] args) throws IOException {
        try {
            createImage("hello");
            System.out.println("图片转换完成");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}

