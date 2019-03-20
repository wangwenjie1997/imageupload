package com.wwj.imageupload.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.wwj.imageupload.util.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ImageUploadController {

    /**
     * 普通上传
     * 对应html:index.html
     * url:localhost:8080/index.html
     * ajax上传
     * 对应html:ajaxIndex.html
     * url:localhost:8080/ajaxIndex.html
     * @param file
     * @return
     */
    @RequestMapping("/imageUpload")
    public String imageUpload(@RequestParam("fileName") MultipartFile file){
        String result_msg="";//上传结果信息
        boolean upload_result=true;//上传结果
        //result_msg和upload_result可用一个int型参数代替(如0表示xx,1表示xx)

        Map<String,Object> root=new HashMap<String, Object>();

        if (file.getSize() / 1000 > 100){
            upload_result=false;
            result_msg="图片大小不能超过100KB";
        }
        else{
            //判断上传文件格式
            String fileType = file.getContentType();
            if (fileType.equals("image/jpeg") || fileType.equals("image/png") || fileType.equals("image/jpeg")) {
                // 要上传的目标文件存放的绝对路径
                String localPath="F:\\IDEAProject\\imageupload\\src\\main\\resources\\static\\img";
                //上传后保存的文件名
                String fileName=file.getOriginalFilename();
                if (FileUtils.upload(file, localPath, fileName)) {
                    //文件存放的相对路径(一般存放在数据库用于img标签的src)
                    String relativePath="img/"+file.getOriginalFilename();
                    root.put("relativePath",relativePath);
                    result_msg="图片上传成功";
                }
                else{
                    upload_result=false;
                    result_msg="图片上传失败";
                }
            }
            else{
                upload_result=false;
                result_msg="图片格式不正确";
            }
        }

        root.put("upload_result",upload_result);
        root.put("result_msg",result_msg);
//        JSON.toJSONString(root,SerializerFeature.DisableCircularReferenceDetect);
        String root_json=JSON.toJSONString(root);
        System.out.println(root_json);
        return root_json;
    }

}
