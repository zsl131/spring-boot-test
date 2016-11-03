package com.zslin.web;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by 钟述林 393156105@qq.com on 2016/11/3 11:00.
 */
@Controller
public class IndexController {

    //获取上传的文件夹，具体路径参考application.properties中的配置
    @Value("${web.upload-path}")
    private String uploadPath;

    /**
     * GET请求
     * 上传页面，也将显示已经存在的文件
     * @param model
     * @return
     */
    @GetMapping(value = "/index")
    public String index(Model model) {
        //获取已存在的文件
        File [] files = new File(uploadPath).listFiles();
        model.addAttribute("files", files);
        return "web/index";
    }

    /**
     * POST请求
     * @param request
     * @param files
     * @return
     */
    @PostMapping(value = "index")
    public String index(HttpServletRequest request, @RequestParam("headimg")MultipartFile[] files) {
        //可以从页面传参数过来
        System.out.println("name====="+request.getParameter("name"));
        //这里可以支持多文件上传
        if(files!=null && files.length>=1) {
            BufferedOutputStream bw = null;
            try {
                String fileName = files[0].getOriginalFilename();
                //判断是否有文件且是否为图片文件
                if(fileName!=null && !"".equalsIgnoreCase(fileName.trim()) && isImageFile(fileName)) {
                    //创建输出文件对象
                    File outFile = new File(uploadPath + "/" + UUID.randomUUID().toString()+ getFileType(fileName));
                    //拷贝文件到输出文件对象
                    FileUtils.copyInputStreamToFile(files[0].getInputStream(), outFile);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if(bw!=null) {bw.close();}
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "redirect:/index";
    }

    /**
     * 判断文件是否为图片文件
     * @param fileName
     * @return
     */
    private Boolean isImageFile(String fileName) {
        String [] img_type = new String[]{".jpg", ".jpeg", ".png", ".gif", ".bmp"};
        if(fileName==null) {return false;}
        fileName = fileName.toLowerCase();
        for(String type : img_type) {
            if(fileName.endsWith(type)) {return true;}
        }
        return false;
    }

    /**
     * 获取文件后缀名
     * @param fileName
     * @return
     */
    private String getFileType(String fileName) {
        if(fileName!=null && fileName.indexOf(".")>=0) {
            return fileName.substring(fileName.lastIndexOf("."), fileName.length());
        }
        return "";
    }
}
