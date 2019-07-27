package com.hone.system.utils;

import java.io.*;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.model.StorageClass;
import com.qcloud.cos.region.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * SimpleUpload 给出了简单上传的示例
 */
@Component
public class SimpleUploadFileCos {

    @Value("${cos.accessKey}")
    private  String accesskey;
    @Value("${cos.sercetKey}")
    private  String sercetKey;
    @Value("${cos.bucketName}")
    private  String bucketName;
    @Value("${cos.bucketUrl}")
    private  String bucketUrl;

    // 将本地文件上传到COS
    public  void SimpleUploadFileFromLocal() {
        // 1 初始化用户身份信息(secretId, secretKey)
        COSCredentials cred = new BasicCOSCredentials(accesskey, sercetKey);
        // 2 设置bucket的区域, COS地域的简称请参照 https://www.qcloud.com/document/product/436/6224
        ClientConfig clientConfig = new ClientConfig(new Region("ap-shanghai"));
        // 3 生成cos客户端
        COSClient cosclient = new COSClient(cred, clientConfig);
        // bucket名需包含appid

        String key = "images/001.jpg";
        File localFile = new File("c:/01.jpg");
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, localFile);
        // 设置存储类型, 默认是标准(Standard), 低频(standard_ia)
        putObjectRequest.setStorageClass(StorageClass.Standard_IA);
        try {
            PutObjectResult putObjectResult = cosclient.putObject(putObjectRequest);
            // putobjectResult会返回文件的etag
            String etag = putObjectResult.getETag();
            System.out.println("etag==="+etag);
        } catch (CosServiceException e) {
            e.printStackTrace();
        } catch (CosClientException e) {
            e.printStackTrace();
        }

        // 关闭客户端
        cosclient.shutdown();
    }

    // 从输入流进行读取并上传到COS
    public  String SimpleUploadFileFromStream(InputStream fileInputStream,String fileName,long size) {
        boolean flag=false;
        // 1 初始化用户身份信息(secretId, secretKey)
        COSCredentials cred = new BasicCOSCredentials(accesskey, sercetKey);
        // 2 设置bucket的区域, COS地域的简称请参照 https://www.qcloud.com/document/product/436/6224
        ClientConfig clientConfig = new ClientConfig(new Region("ap-shanghai"));
        // 3 生成cos客户端
        COSClient cosclient = new COSClient(cred, clientConfig);
        // images/yyyy-MM-dd/xx.jpg
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        String key = "images/"+DateUtils.YYYYMM()+"/"+DateUtils.YYYYMMDD()+"/"+System.currentTimeMillis()+"."+suffix;
        ObjectMetadata objectMetadata = new ObjectMetadata();
        // 从输入流上传必须制定content length, 否则http客户端可能会缓存所有数据，存在内存OOM的情况
        objectMetadata.setContentLength(size);
        // 默认下载时根据cos路径key的后缀返回响应的contenttype, 上传时设置contenttype会覆盖默认值
        objectMetadata.setContentType("image/jpeg");
        PutObjectRequest putObjectRequest =
                new PutObjectRequest(bucketName, key, fileInputStream, objectMetadata);
        // 设置存储类型, 默认是标准(Standard), 低频(standard_ia)
        putObjectRequest.setStorageClass(StorageClass.Standard_IA);
        try {
            PutObjectResult putObjectResult = cosclient.putObject(putObjectRequest);
            // putobjectResult会返回文件的etag
            String etag = putObjectResult.getETag();
            System.out.println(etag);
            flag=true;
        } catch (CosServiceException e) {
            e.printStackTrace();
        } catch (CosClientException e) {
            e.printStackTrace();
        }

        // 关闭客户端
        cosclient.shutdown();
        //文件上传成功
        if(flag){
            return bucketUrl+key;
        }else {
         //文件上传失败
            return null;
        }
    }

    /**
     * 删除单个文件
     */
    public void DelSingleFile(String key) {
        // 1 初始化用户身份信息(secretId, secretKey)
        COSCredentials cred = new BasicCOSCredentials(accesskey, sercetKey);
        // 2 设置bucket的区域, COS地域的简称请参照 https://www.qcloud.com/document/product/436/6224
        ClientConfig clientConfig = new ClientConfig(new Region("ap-shanghai"));
        // 3 生成cos客户端
        COSClient cosclient = new COSClient(cred, clientConfig);
        try {
            cosclient.deleteObject(bucketName, key);
        } catch (CosServiceException e) { // 如果是其他错误, 比如参数错误， 身份验证不过等会抛出CosServiceException
            e.printStackTrace();
        } catch (CosClientException e) { // 如果是客户端错误，比如连接不上COS
            e.printStackTrace();
        } catch(IllegalArgumentException e) { // 该测试用例的预期结果
            e.printStackTrace();
        }

        try {
            cosclient.deleteObject(bucketName, key);
        } catch (CosServiceException e) { // 如果是其他错误, 比如参数错误， 身份验证不过等会抛出CosServiceException
            e.printStackTrace();
        } catch (CosClientException e) { // 如果是客户端错误，比如连接不上COS
            e.printStackTrace();
        }

        // 关闭客户端
        cosclient.shutdown();
    }

    public static void main(String[] args){
        String str="https://hongone-1258770736.cos.ap-shanghai.myqcloud.com/images/201906/20190605/1559726733144.png";
        System.out.println(str.substring(str.indexOf("images")));
    }
}
