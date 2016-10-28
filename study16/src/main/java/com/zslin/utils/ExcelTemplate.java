package com.zslin.utils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.*;

/**
 * 该类实现了基于模板的导出
 * 如果要导出序号，需要在excel中定义一个标识为sernums
 * 如果要替换信息，需要传入一个Map，这个map中存储着要替换信息的值，在excel中通过#来开头
 * 要从哪一行那一列开始替换需要定义一个标识为datas
 * 如果要设定相应的样式，可以在该行使用styles完成设定，此时所有此行都使用该样式
 * 如果使用defaultStyls作为表示，表示默认样式，如果没有defaultStyles使用datas行作为默认样式
 * Created by 钟述林 393156105@qq.com on 2016/10/28 23:38.
 */
public class ExcelTemplate {

    /**
     * 数据行标识
     */
    public final static String DATA_LINE = "datas";
    /**
     * 默认样式标识
     */
    public final static String DEFAULT_STYLE = "defaultStyles";
    /**
     * 行样式标识
     */
    public final static String STYLE = "styles";
    /**
     * 插入序号样式标识
     */
    public final static String SER_NUM = "sernums";
    private static ExcelTemplate et = new ExcelTemplate();
    private Workbook wb;
    private Sheet sheet;
    /**
     * 数据的初始化列数
     */
    private int initColIndex;
    /**
     * 数据的初始化行数
     */
    private int initRowIndex;
    /**
     * 当前列数
     */
    private int curColIndex;
    /**
     * 当前行数
     */
    private int curRowIndex;
    /**
     * 当前行对象
     */
    private Row curRow;
    /**
     * 最后一行的数据
     */
    private int lastRowIndex;
    /**
     * 默认样式
     */
    private CellStyle defaultStyle;
    /**
     * 默认行高
     */
    private float rowHeight;
    /**
     * 存储某一方所对于的样式
     */
    private Map<Integer,CellStyle> styles;
    /**
     * 序号的列
     */
    private int serColIndex;
    private ExcelTemplate(){

    }
    public static ExcelTemplate getInstance() {
        return et;
    }

    /**
     * 从classpath路径下读取相应的模板文件
     * @param path
     * @return
     */
    public ExcelTemplate readTemplateByClasspath(String path) {
        try {
            wb = new HSSFWorkbook(TemplateFileUtil.getTemplates(path));
            initTemplate();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("读取模板不存在！请检查");
        }
        return this;
    }
    /**
     * 将文件写到相应的路径下
     * @param filepath
     */
    public void writeToFile(String filepath) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(filepath);
            wb.write(fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("写入的文件不存在");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("写入数据失败:"+e.getMessage());
        } finally {
            try {
                if(fos!=null) fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * 将文件写到某个输出流中
     * @param os
     */
    public void wirteToStream(OutputStream os) {
        try {
            wb.write(os);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("写入流失败:"+e.getMessage());
        }
    }
    /**
     * 从某个路径来读取模板
     * @param path
     * @return
     */
    public ExcelTemplate readTemplateByPath(String path) {
        try {
            wb = new HSSFWorkbook(TemplateFileUtil.getTemplates(path));
            initTemplate();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("读取模板不存在！请检查");
        }
        return this;
    }

    /**
     * 创建相应的元素，基于String类型
     * @param value
     */
    public void createCell(String value) {
        Cell c = curRow.createCell(curColIndex);
        setCellStyle(c);
        c.setCellValue(value);
        curColIndex++;
    }
    public void createCell(int value) {
        Cell c = curRow.createCell(curColIndex);
        setCellStyle(c);
        c.setCellValue((int)value);
        curColIndex++;
    }
    public void createCell(Date value) {
        Cell c = curRow.createCell(curColIndex);
        setCellStyle(c);
        c.setCellValue(value);
        curColIndex++;
    }
    public void createCell(double value) {
        Cell c = curRow.createCell(curColIndex);
        setCellStyle(c);
        c.setCellValue(value);
        curColIndex++;
    }
    public void createCell(boolean value) {
        Cell c = curRow.createCell(curColIndex);
        setCellStyle(c);
        c.setCellValue(value);
        curColIndex++;
    }

    public void createCell(Calendar value) {
        Cell c = curRow.createCell(curColIndex);
        setCellStyle(c);
        c.setCellValue(value);
        curColIndex++;
    }
    public void createCell(BigInteger value) {
        Cell c = curRow.createCell(curColIndex);
        setCellStyle(c);
        c.setCellValue(value==null?0:value.intValue());
        curColIndex++;
    }
    /**
     * 设置某个元素的样式
     * @param c
     */
    private void setCellStyle(Cell c) {
        if(styles.containsKey(curColIndex)) {
            c.setCellStyle(styles.get(curColIndex));
        } else {
            c.setCellStyle(defaultStyle);
        }
    }
    /**
     * 创建新行，在使用时只要添加完一行，需要调用该方法创建
     */
    public void createNewRow() {
        if(lastRowIndex>curRowIndex&&curRowIndex!=initRowIndex) {
            sheet.shiftRows(curRowIndex, lastRowIndex, 1,true,true);
            lastRowIndex++;
        }
        curRow = sheet.createRow(curRowIndex);
        curRow.setHeightInPoints(rowHeight);
        curRowIndex++;
        curColIndex = initColIndex;
    }

    /**
     * 插入序号，会自动找相应的序号标示的位置完成插入
     */
    public void insertSer() {
        int index = 1;
        Row row = null;
        Cell c = null;
        for(int i=initRowIndex;i<curRowIndex;i++) {
            row = sheet.getRow(i);
            c = row.createCell(serColIndex);
            setCellStyle(c);
            c.setCellValue(index++);
        }
    }
    /**
     * 根据map替换相应的常量，通过Map中的值来替换#开头的值
     * @param datas
     */
    public void replaceFinalData(Map<String,String> datas) {
        if(datas==null) return;
        for(Row row:sheet) {
            for(Cell c:row) {
//                if(c.getCellType()!=Cell.CELL_TYPE_STRING) continue;
                String str = c.getStringCellValue().trim();
                if(str.startsWith("#")) {
                    if(datas.containsKey(str.substring(1))) {
                        c.setCellValue(datas.get(str.substring(1)));
                    }
                }
            }
        }
    }
    /**
     * 基于Properties的替换，依然也是替换#开始的
     * @param prop
     */
    public void replaceFinalData(Properties prop) {
        if(prop==null) return;
        for(Row row:sheet) {
            for(Cell c:row) {
//                if(c.getCellType()!=Cell.CELL_TYPE_STRING) continue;
                String str = c.getStringCellValue().trim();
                if(str.startsWith("#")) {
                    if(prop.containsKey(str.substring(1))) {
                        c.setCellValue(prop.getProperty(str.substring(1)));
                    }
                }
            }
        }
    }

    private void initTemplate() {
        sheet = wb.getSheetAt(0);
        initConfigData();
        lastRowIndex = sheet.getLastRowNum();
        curRow = sheet.createRow(curRowIndex);
    }
    /**
     * 初始化数据信息
     */
    private void initConfigData() {
        boolean findData = false;
        boolean findSer = false;
        for(Row row:sheet) {
            if(findData) break;
            for(Cell c:row) {
//                if(c.getCellType()!=Cell.CELL_TYPE_STRING) continue;
                String str = c.getStringCellValue().trim();
                if(str.equals(SER_NUM)) {
                    serColIndex = c.getColumnIndex();
                    findSer = true;
                }
                if(str.equals(DATA_LINE)) {
                    initColIndex = c.getColumnIndex();
                    initRowIndex = row.getRowNum();
                    curColIndex = initColIndex;
                    curRowIndex = initRowIndex;
                    findData = true;
                    defaultStyle = c.getCellStyle();
                    rowHeight = row.getHeightInPoints();
                    initStyles();
                    break;
                }
            }
        }
        if(!findSer) {
            initSer();
        }
    }
    /**
     * 初始化序号位置
     */
    private void initSer() {
        for(Row row:sheet) {
            for(Cell c:row) {
//                if(c.getCellType()!=Cell.CELL_TYPE_STRING) continue;
                String str = c.getStringCellValue().trim();
                if(str.equals(SER_NUM)) {
                    serColIndex = c.getColumnIndex();
                }
            }
        }
    }
    /**
     * 初始化样式信息
     */
    private void initStyles() {
        styles = new HashMap<Integer, CellStyle>();
        for(Row row:sheet) {
            for(Cell c:row) {
//                if(c.getCellType()!=Cell.CELL_TYPE_STRING) continue;
                String str = c.getStringCellValue().trim();
                if(str.equals(DEFAULT_STYLE)) {
                    defaultStyle = c.getCellStyle();
                }
                if(str.equals(STYLE)) {
                    styles.put(c.getColumnIndex(), c.getCellStyle());
                }
            }
        }
    }
}
