package com.zslin.utils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 该类实现了基于模板的导出
 * 如果要导出序号，需要在 excel 中定义一个标识为 serialNumber
 * 如果要替换信息，需要传入一个 Map，这个 map 中存储着要替换信息的值，在 excel 中通过#来开头
 * 要从哪一行那一列开始替换需要定义一个标识为 data
 * 如果要设定相应的样式，可以在该行使用 rowStyles 完成设定，此时所有此行都使用该样式
 * 如果使用 defaultStyles 作为表示，表示默认样式，如果没有 defaultStyles 使用 data 行作为默认样式
 * Created by 钟述林 393156105@qq.com on 2016/10/28 23:38.
 */
public class ExcelTemplate {

    /**
     * 数据行标识
     */
    public final static String DATA_LINE = "data";

    /**
     * 默认样式标识
     */
    public final static String DEFAULT_STYLES = "defaultStyles";

    /**
     * 行样式标识
     */
    public final static String ROW_STYLES = "rowStyles";

    /**
     * 插入序号样式标识
     */
    public final static String SERIAL_NUMBER = "serialNumber";

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
     * 样式单元格
     */
    private List<Cell> styleCells;

    /**
     * 存储某一方所对于的样式
     */
    private Map<Integer, CellStyle> styles;

    /**
     * 序号的列
     */
    private int serColIndex;

    /**
     * 序号样式
     */
    private CellStyle serialNumberStyle;

    private ExcelTemplate() {

    }

    public static ExcelTemplate getInstance() {
        return et;
    }

    /**
     * 从classpath路径下读取相应的模板文件
     *
     * @param path
     * @return
     */
    public ExcelTemplate readTemplateByClasspath(String path) {
        try {
            wb = new HSSFWorkbook(TemplateFileUtil.getTemplateByClasspath(path));
            initTemplate();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("读取模板不存在！请检查");
        }
        return this;
    }

    /**
     * 从某个路径来读取模板
     *
     * @param path
     * @return
     */
    public ExcelTemplate readTemplateByPath(String path) {
        try {
            wb = new HSSFWorkbook(TemplateFileUtil.getTemplateByPath(path));
            initTemplate();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("读取模板不存在！请检查");
        }
        return this;
    }

    /**
     * 将文件写到相应的路径下
     *
     * @param filepath 文件路径
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
            throw new RuntimeException("写入数据失败:" + e.getMessage());
        } finally {
            try {
                if (fos != null) fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 将文件写到某个输出流中
     *
     * @param os 输出流
     */
    public void writeToStream(OutputStream os) {
        try {
            wb.write(os);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("写入流失败:" + e.getMessage());
        } finally {
            try {
                if (null != os) {
                    os.flush();
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 创建相应的元素，基于 String 类型
     *
     * @param value 单元格的值
     */
    public void createCell(String value) {
        Cell cell = curRow.createCell(curColIndex);
        setCellStyle(cell);
        cell.setCellValue(value);
        curColIndex++;
    }

    /**
     * 创建相应的元素，基于 int 类型
     *
     * @param value 单元格的值
     */
    public void createCell(int value) {
        Cell cell = curRow.createCell(curColIndex);
        setCellStyle(cell);
        cell.setCellValue(value);
        curColIndex++;
    }

    /**
     * 创建相应的元素，基于 Date 类型
     *
     * @param value 单元格的值
     */
    public void createCell(Date value) {
        Cell cell = curRow.createCell(curColIndex);
        setCellStyle(cell);
        cell.setCellValue(value);
        curColIndex++;
    }

    /**
     * 创建相应的元素，基于 double 类型
     *
     * @param value 单元格的值
     */
    public void createCell(double value) {
        Cell cell = curRow.createCell(curColIndex);
        setCellStyle(cell);
        cell.setCellValue(value);
        curColIndex++;
    }

    /**
     * 创建相应的元素，基于 boolean 类型
     *
     * @param value 单元格的值
     */
    public void createCell(boolean value) {
        Cell c = curRow.createCell(curColIndex);
        setCellStyle(c);
        c.setCellValue(value);
        curColIndex++;
    }

    /**
     * 创建相应的元素，基于 Calendar 类型
     *
     * @param value 单元格的值
     */
    public void createCell(Calendar value) {
        Cell cell = curRow.createCell(curColIndex);
        setCellStyle(cell);
        cell.setCellValue(value);
        curColIndex++;
    }

    /**
     * 创建相应的元素，基于 BigInteger 类型
     *
     * @param value 单元格的值
     */
    public void createCell(BigInteger value) {
        Cell cell = curRow.createCell(curColIndex);
        setCellStyle(cell);
        cell.setCellValue(value == null ? 0 : value.intValue());
        curColIndex++;
    }

    /**
     * 设置某个元素的样式
     *
     * @param cell 单元格
     */
    private void setCellStyle(Cell cell) {
        if (styles.containsKey(curColIndex)) {
            cell.setCellStyle(styles.get(curColIndex));
        } else {
            cell.setCellStyle(defaultStyle);
        }
    }

    /**
     * 创建新行，在使用时只要添加完一行，需要调用该方法创建
     */
    public void createNewRow() {
        List<CellRangeAddress> originMerged = sheet.getMergedRegions();
        if (lastRowIndex > curRowIndex && curRowIndex != initRowIndex) {
            sheet.shiftRows(curRowIndex, lastRowIndex, 1, true, true);
            lastRowIndex++;

            List<CellRangeAddress> afterMerged = sheet.getMergedRegions();
            afterMerged.retainAll(originMerged);
            originMerged.removeAll(afterMerged);

            // 由于当前 poi 的 shiftRows 方法会破坏移动后的单元格合并格式，需要人工还原
            reduceMergedRegion(originMerged);
        }
        curRow = sheet.createRow(curRowIndex);
        curRow.setHeightInPoints(rowHeight);
        curRowIndex++;
        curColIndex = initColIndex;
    }

    private void reduceMergedRegion(List<CellRangeAddress> originMerged) {
        for (CellRangeAddress cellRangeAddress : originMerged) {
            if (cellRangeAddress.getFirstRow() > curRowIndex) {
                int firstRow = cellRangeAddress.getFirstRow() + 1;
                int lastRow = firstRow + (cellRangeAddress.getLastRow() - cellRangeAddress.getFirstRow());
                int firstCol = cellRangeAddress.getFirstColumn();
                int lastCol = cellRangeAddress.getLastColumn();
                CellRangeAddress newCellRangeAddress = new CellRangeAddress(firstRow, lastRow, firstCol, lastCol);
                sheet.addMergedRegion(newCellRangeAddress);
            }
        }
    }

    /**
     * 插入序号，会自动找相应的序号标示的位置完成插入
     */
    public void insertSerialNumber() {
        int index = 1;
        Row row = null;
        Cell cell = null;
        for (int i = initRowIndex; i < curRowIndex; i++) {
            row = sheet.getRow(i);
            cell = row.createCell(serColIndex);
            cell.setCellStyle(serialNumberStyle);
            cell.setCellValue(index++);
        }
    }

    /**
     * 根据 map 替换相应的常量，通过 Map 中的值来替换#开头的值
     *
     * @param data 常量值 map 对象
     */
    public void replaceFinalData(Map<String, String> data) {
        if (data == null) return;
        for (Row row : sheet) {
            for (Cell cell : row) {
                if (cell.getCellType() != Cell.CELL_TYPE_STRING) continue;
                String cellValue = cell.getStringCellValue().trim();
                if (cellValue.startsWith("#")) {
                    if (data.containsKey(cellValue.substring(1))) {
                        cell.setCellValue(data.get(cellValue.substring(1)));
                    }
                }
            }
        }
    }

    /**
     * 基于 Properties 的替换，依然也是替换#开始的
     *
     * @param prop 常量值 property 对象
     */
    public void replaceFinalData(Properties prop) {
        if (prop == null) return;
        for (Row row : sheet) {
            for (Cell cell : row) {
                if (cell.getCellType() != Cell.CELL_TYPE_STRING) continue;
                String cellValue = cell.getStringCellValue().trim();
                if (cellValue.startsWith("#")) {
                    if (prop.containsKey(cellValue.substring(1))) {
                        cell.setCellValue(prop.getProperty(cellValue.substring(1)));
                    }
                }
            }
        }
    }

    /**
     * 移除样式单元格
     */
    public void removeStyleCells() {
        for (Cell cell : styleCells) {
            int rowIndex = cell.getRowIndex();
            int colIndex = cell.getColumnIndex();
            Row row = sheet.getRow(rowIndex);
            row.removeCell(row.getCell(colIndex));
        }
    }

    /**
     * 初始化模板
     */
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
        for (Row row : sheet) {
            if (findData) break;
            for (Cell cell : row) {
                if (cell.getCellType() != Cell.CELL_TYPE_STRING) continue;
                String cellValue = cell.getStringCellValue().trim();
                if (cellValue.equals(SERIAL_NUMBER)) {
                    serColIndex = cell.getColumnIndex();
                    serialNumberStyle = cell.getCellStyle();
                    findSer = true;
                }
                if (cellValue.equals(DATA_LINE)) {
                    initColIndex = cell.getColumnIndex();
                    initRowIndex = row.getRowNum();
                    curColIndex = initColIndex;
                    curRowIndex = initRowIndex;
                    defaultStyle = cell.getCellStyle();
                    rowHeight = row.getHeightInPoints();
                    findData = true;
                    initStyles();
                    break;
                }
            }
        }
        if (!findSer) {
            initSer();
        }
    }

    /**
     * 初始化序号位置
     */
    private void initSer() {
        for (Row row : sheet) {
            for (Cell cell : row) {
                if (cell.getCellType() != Cell.CELL_TYPE_STRING) continue;
                String cellValue = cell.getStringCellValue().trim();
                if (cellValue.equals(SERIAL_NUMBER)) {
                    serColIndex = cell.getColumnIndex();
                }
            }
        }
    }

    /**
     * 初始化样式信息
     */
    private void initStyles() {
        styles = new HashMap<>();
        styleCells = new ArrayList<>();
        for (Row row : sheet) {
            for (Cell cell : row) {
                if (cell.getCellType() != Cell.CELL_TYPE_STRING) continue;
                String cellValue = cell.getStringCellValue().trim();
                if (cellValue.equals(DEFAULT_STYLES)) {
                    styleCells.add(cell);
                    defaultStyle = cell.getCellStyle();
                }
                if (cellValue.equals(ROW_STYLES)) {
                    styleCells.add(cell);
                    styles.put(cell.getColumnIndex(), cell.getCellStyle());
                }
            }
        }
    }
}
