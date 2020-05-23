package com.example.demo.util;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.ListObjectsRequest;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.File;
import java.net.URL;
import java.util.*;

public class AliyunOSSUtil {

    private static String FILE_URL;
    private static String bucketName = "zhilianjiaohu";
    private static String endpoint = "oss-cn-beijing.aliyuncs.com";
    private static String accessKeyId = *****
    private static String accessKeySecret = ***

    /**
     * 通过文件名下载文件
     *
     * @param objectName    要下载的文件名
     * @param localFileName 本地要创建的文件名
     */
    public static void downloadFile(String objectName, String localFileName) {

        // 创建OSSClient实例。
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        // 下载OSS文件到本地文件。如果指定的本地文件存在会覆盖，不存在则新建。
        ossClient.getObject(new GetObjectRequest(bucketName, objectName), new File(localFileName));
        // 关闭OSSClient。
        ossClient.shutdown();
    }
    /**
     * 列出指定目录下的所有文件，返回list
     */
    public static Map<String, List<String>> listALLfileBydir(String dir){
        Map<String, List<String>> map=new HashMap<>();
        // 创建OSSClient实例。
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        List<String> list = new ArrayList<>();
        List<String> list2 = new ArrayList<>();
        // 构造ListObjectsRequest请求。
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest(bucketName);
//        // 设置正斜线（/）为文件夹的分隔符。
        listObjectsRequest.setDelimiter("/");
// 设置prefix参数来获取fun目录下的所有文件
        listObjectsRequest.setPrefix(dir);
        // 递归列出fun目录下的所有文件。
        ObjectListing listing = ossClient.listObjects(listObjectsRequest);
        // 遍历所有文件。
        System.out.println("Objects:");
        for (OSSObjectSummary objectSummary : listing.getObjectSummaries()) {
            //不去掉前缀
            list.add(objectSummary.getKey());
            //去掉前缀
            list2.add(objectSummary.getKey().replace(dir,""));
        }
        map.put("hasDir",list);
        map.put("noDir",list2);
        // 关闭OSSClient。
        ossClient.shutdown();
        return map;
    }
    /**
     * 列出OSS中所有的文件名
     */
    public static List<String> listALLfile(String file){
        // 创建OSSClient实例。
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        List<String> list = new ArrayList<>();
   //     ObjectListing objectListing = ossClient.listObjects(bucketName);
        // 构造ListObjectsRequest请求。
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest(bucketName);
//        // 设置正斜线（/）为文件夹的分隔符。
        listObjectsRequest.setDelimiter("/");
// 设置prefix参数来获取fun目录下的所有文件
        listObjectsRequest.setPrefix(file);
        // 递归列出fun目录下的所有文件。
        ObjectListing listing = ossClient.listObjects(listObjectsRequest);
        // 遍历所有文件。

// 遍历所有commonPrefix。
        System.out.println("\nCommonPrefixes:");
        for (String commonPrefix : listing.getCommonPrefixes()) {
            System.out.println(commonPrefix);
list.add(commonPrefix);
        }

        // 关闭OSSClient。
        ossClient.shutdown();
        return list;
    }
            /**oss智能问答获取文件下载路径；
          * @param keyname
          * @return
          */
            public URL getUrl(String keyname){
            OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
            // 设置URL过期时间为1小时
            Date expiration = new Date(new Date().getTime() + 3600 * 10000);
            // 生成URL
            URL url = client.generatePresignedUrl(bucketName, keyname, expiration);
            return url;
            }
    /**oss文件库获取文件下载路径；
          * @param keyname
          * @return
          */
    public URL getUrlBy(String keyname){
        OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        // 设置URL过期时间为1小时
        Date expiration = new Date(new Date().getTime() + 3600*10000 );
        // 生成URL
        URL url = client.generatePresignedUrl(bucketName, keyname, expiration);
        return url;
    }
    /**oss获取一级目录
          * @param keyname
          * @return
          */
    public static List<String> listdirectory(){
        // 创建OSSClient实例。
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        List<String> list = new ArrayList<>();
// 列举文件。 如果不设置KeyPrefix，则列举存储空间下所有的文件。KeyPrefix，则列举包含指定前缀的文件。
        //     ObjectListing objectListing = ossClient.listObjects(bucketName);
        // 构造ListObjectsRequest请求。
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest(bucketName);
        // 设置正斜线（/）为文件夹的分隔符。
        listObjectsRequest.setDelimiter("/");
        // 设置prefix参数来获取fun目录下的所有文件
        listObjectsRequest.setPrefix("zhilianjiaohu/");

        ObjectListing listing = ossClient.listObjects(listObjectsRequest);
        // 遍历所有文件。
// 遍历所有commonPrefix。
        System.out.println("\nCommonPrefixes:");
        for (String commonPrefix : listing.getCommonPrefixes()) {
            list.add(commonPrefix);
            System.out.println(commonPrefix);
        }
        // 关闭OSSClient。
        ossClient.shutdown();
        return list;
    }
    public static JSONArray listfileByDir(String dir){
        JSONObject j=new JSONObject();
        JSONArray jsonArray=new JSONArray();
        // 创建OSSClient实例。
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        List<String> list = new ArrayList<>();
// 列举文件。 如果不设置KeyPrefix，则列举存储空间下所有的文件。KeyPrefix，则列举包含指定前缀的文件。
        //     ObjectListing objectListing = ossClient.listObjects(bucketName);
        // 构造ListObjectsRequest请求。
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest(bucketName);
// 设置prefix参数来获取fun目录下的所有文件
        listObjectsRequest.setPrefix(dir);
        // 递归列出fun目录下的所有文件。
        ObjectListing listing = ossClient.listObjects(listObjectsRequest);
        // 遍历所有文件。
        System.out.println("Objects:");
        for (OSSObjectSummary objectSummary : listing.getObjectSummaries()) {
            // System.out.println(objectSummary.getKey());
            if(objectSummary.getKey().equals(dir)){

            }else {
                j.put("filename",objectSummary.getKey().replace(dir,""));
//                list.add(objectSummary.getKey().replace(dir,""));
                jsonArray.add(j);
            }

        }


        // 关闭OSSClient。
        ossClient.shutdown();
        return jsonArray;
    }

    /**
     * 列出OSS中所有的文件名
     */
    public static List<String> listvideoALLfile(String file){
        // 创建OSSClient实例。
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        List<String> list = new ArrayList<>();
        // 构造ListObjectsRequest请求。
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest(bucketName);
//        // 设置正斜线（/）为文件夹的分隔符。
        listObjectsRequest.setDelimiter("/");
// 设置prefix参数来获取fun目录下的所有文件
        listObjectsRequest.setPrefix(file);
        // 递归列出fun目录下的所有文件。
        ObjectListing listing = ossClient.listObjects(listObjectsRequest);
        // 遍历所有文件。
        System.out.println("Objects:");
        for (OSSObjectSummary objectSummary : listing.getObjectSummaries()) {
            System.out.println(objectSummary.getKey());
            if (objectSummary.getKey().equals("视频/")){

            }else {
                list.add(objectSummary.getKey().replace("视频/",""));
            }

        }
        // 关闭OSSClient。
        ossClient.shutdown();
        return list;
    }
}

