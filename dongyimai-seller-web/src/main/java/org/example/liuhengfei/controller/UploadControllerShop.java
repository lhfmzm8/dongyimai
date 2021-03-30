package org.example.liuhengfei.controller;

import org.example.liuhengfei.dto.Result;
import org.example.liuhengfei.utils.FastDFSClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Properties;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("UploadControllerShop")
public class UploadControllerShop {

    @Value("${tracker_server}")
    private String trackerServer;

    @Value("${file_server_url}")
    private String fileServerUrl;

    @RequestMapping("upload")
    public Result upload (MultipartFile file){
        //提取扩展名
        String originalFilename = file.getOriginalFilename();
        String extName = originalFilename.substring(originalFilename.lastIndexOf('.') + 1);
        //调用fastdfs客户端上传文件
        try {
            Properties property = new Properties();
            property.setProperty("fastdfs.tracker_servers", trackerServer);
            FastDFSClient fastDFSClient = new FastDFSClient(property);
            String uploadFile = fastDFSClient.uploadFile(file.getBytes(), extName);
            return new Result(true,fileServerUrl+uploadFile);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"上传失败");
        }
    }

}
