package com.zslin;

import com.zslin.dto.WebDto;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 钟述林 393156105@qq.com on 2016/10/28 22:07.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ReadExcelTest2 {

    //读取单个单元格
    @Test
    public void testRead() throws Exception {
        //Excel文件
        HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(ResourceUtils.getFile("classpath:web-info.xls")));
        //Excel工作表
        HSSFSheet sheet = wb.getSheetAt(0);

        //表头那一行
        HSSFRow titleRow = sheet.getRow(0);

        //表头那个单元格
        HSSFCell titleCell = titleRow.getCell(0);

        String title = titleCell.getStringCellValue();

        System.out.println("标题是："+title);
    }

    //读取到列表
    @Test
    public void testReadList() throws Exception {
        List<WebDto> list = new ArrayList<WebDto>();

        HSSFWorkbook book = new HSSFWorkbook(new FileInputStream(ResourceUtils.getFile("classpath:eb-info.xls")));

        HSSFSheet sheet = book.getSheetAt(0);

        for(int i=2; i<sheet.getLastRowNum()+1; i++) {
            HSSFRow row = sheet.getRow(i);
            String name = row.getCell(0).getStringCellValue(); //名称
            String url = row.getCell(1).getStringCellValue(); //url
            String username = row.getCell(2).getStringCellValue();
            String password = row.getCell(3).getStringCellValue();
            Integer readCount = (int) row.getCell(4).getNumericCellValue();

            list.add(new WebDto(name, url, username, password, readCount));
        }

        System.out.println("共有 " + list.size() + " 条数据：");
        for(WebDto wd : list) {
            System.out.println(wd);
        }
    }
}
