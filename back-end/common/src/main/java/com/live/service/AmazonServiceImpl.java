package com.live.service;

import cn.hutool.core.io.FileUtil;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Date;

@Service
public class AmazonServiceImpl {

    private AmazonS3 s3client;

    @Value("${amazon.s3.region}")
    private String region;
    @Value("${amazon.s3.default-bucket}")
    private String bucketName;
    @Value("${amazon.s3.dirname}")
    private String dirname;
    @Value("${amazon.aws.access-key-id}")
    private String accessKey;
    @Value("${amazon.aws.access-key-secret}")
    private String secretKey;;


    @PostConstruct
    private void initializeAmazon() {
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
        this.s3client = AmazonS3ClientBuilder.standard()
                .withRegion(Regions.fromName(region))
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();
    }

    /**
     * 上传头像到S3中，进行类型判断
     * @param multipartFile
     * @return
     */
    public String uploadFile(MultipartFile multipartFile) throws IOException {

        File file = convertMultiPartToFile(multipartFile);
        String fileName = generateFileName(multipartFile);
        String type = FileUtil.getType(file);
        System.out.println("上传的图片类型: " + type);
//        if(!(type.equals("jpg") || type.equals("jpeg") || type.equals("png"))){
//            //如果不正确，直接抛出异常
//            throw new ApiException(ResultCode.TYPE_ERROR);
//        }

        //上传文件到S3中
        String url = uploadFileTos3bucket(fileName, file);
        file.delete();

        return url;
    }



    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    private String generateFileName(MultipartFile multiPart) {
        return new Date().getTime() + "-" + multiPart.getOriginalFilename().replace(" ", "_");
    }

    private String uploadFileTos3bucket(String fileName, File file) {
        s3client.putObject(new PutObjectRequest(bucketName+ "/" +dirname, fileName, file)
                .withCannedAcl(CannedAccessControlList.PublicRead));

        //上传之后返回图片的路径，进行数据库保存。
        URL url = s3client.getUrl(bucketName + "/" + dirname, fileName);
        return url.toString();
    }

    public String deleteFileFromS3Bucket(String fileUrl) {
        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
        s3client.deleteObject(new DeleteObjectRequest(bucketName, fileName));
        return "Successfully deleted";
    }

//    public List<String> listFiles() {
//        ListObjectsV2Request listObjectsRequest =
//                new ListObjectsV2Request()
//                        .withBucketName(bucketName)
//                        .withPrefix("/");
//
//        List<String> keys = new ArrayList<>();
//        ListObjectsV2Result objects = s3client.listObjectsV2(listObjectsRequest);
//
//        ListObjectsV2Request listObjectsReqManual = ListObjectsV2Request.builder()
//                .bucket(bucketName)
//                .maxKeys(1)
//                .build();
//        while (true) {
//            List<S3ObjectSummary> summaries = objects.getObjectSummaries();
//            if (summaries.size() < 1) {
//                break;
//            }
//            for (S3ObjectSummary item : summaries) {
//                if (!item.getKey().endsWith("/"))
//                    keys.add(item.getKey());
//            }
////            ListObjectsV2Iterable listRes = s3.listObjectsV2Paginator(listReq);
////            objects = s3client.listObjectsV2(objects);
//            listObjectsReqManual = listObjectsReqManual.toBuilder()
//                    .continuationToken(listObjResponse.nextContinuationToken())
//                    .build();
//        }
//        return keys;
//    }
    public String getFilePathFromS3Bucket(){
        s3client.getObjectMetadata(bucketName,accessKey);
        return "";
    }

}