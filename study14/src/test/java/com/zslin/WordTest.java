package com.zslin;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 钟述林 393156105@qq.com on 2016/10/28 16:20.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class WordTest {

    @Test
    public void testExportWord() throws Exception {
        String tmpFile = "D:/temp/template.doc";
        String expFile = "D:/temp/result.doc";
        Map<String, String> datas = new HashMap<String, String>();
        datas.put("title", "标题部份");
        datas.put("content", "这里是内容，测试使用POI导出到Word的内容！");
        datas.put("author", "知识林");
        datas.put("url", "http://www.zslin.com");

        build(new File(tmpFile), datas, expFile);
    }

    @Test
    public void testExportWord2() throws Exception {
        String tmpFile = "classpath:template.doc";
        String expFile = "D:/temp/result.doc";
        Map<String, String> datas = new HashMap<String, String>();
        datas.put("title", "标题部份");
        datas.put("content", "这里是内容，测试使用POI导出到Word的内容！");
        datas.put("author", "知识林");
        datas.put("url", "http://www.zslin.com");

        build(ResourceUtils.getFile(tmpFile), datas, expFile);
    }

    private void build(File tmpFile, Map<String, String> contentMap, String exportFile) throws Exception {
        FileInputStream tempFileInputStream = new FileInputStream(tmpFile);
        HWPFDocument document = new HWPFDocument(tempFileInputStream);
        // 读取文本内容
        Range bodyRange = document.getRange();
        // 替换内容
        for (Map.Entry<String, String> entry : contentMap.entrySet()) {
            bodyRange.replaceText("${" + entry.getKey() + "}", entry.getValue());
        }

        //导出到文件
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        document.write(byteArrayOutputStream);
        OutputStream outputStream = new FileOutputStream(exportFile);
        outputStream.write(byteArrayOutputStream.toByteArray());
        outputStream.close();
    }
}
