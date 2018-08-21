package com.zslin.utils;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 该类实现了将一组对象转换为 Excel 表格，并且可以从 Excel 表格中读取到一组 List 对象中
 * 使用该类的前提，在相应的实体对象上通过 ExcelResources 来完成相应的注解或者模板写好表头（复杂表头推荐模板写好）
 * Created by 钟述林 393156105@qq.com on 2016/10/29 0:15.
 */
public class ExcelUtil {
    private static ExcelUtil eu = new ExcelUtil();

    private ExcelUtil() {
    }

    public static ExcelUtil getInstance() {
        return eu;
    }

    /**
     * 将对象转换为 Excel 并且导出，该方法是基于模板的导出，导出到流
     *
     * @param data        模板中的替换的常量数据
     * @param template    模板路径
     * @param os          输出流
     * @param rows        对象列表
     * @param clz         对象的类型
     * @param isClasspath 模板是否在classPath路径下
     */
    public void exportObj2ExcelByTemplate(Map<String, String> data, String template, OutputStream os, List rows, Class clz, boolean isClasspath) {
        ExcelTemplate et = handlerObj2Excel(template, rows, clz, isClasspath);
        et.replaceFinalData(data);
        et.writeToStream(os);
    }

    /**
     * 将对象转换为 Excel 并且导出，该方法是基于模板的导出，导出到一个具体的路径中
     *
     * @param data        模板中的替换的常量数据
     * @param template    模板路径
     * @param outPath     输出路径
     * @param rows        对象列表
     * @param clz         对象的类型
     * @param isClasspath 模板是否在classPath路径下
     */
    public void exportObj2ExcelByTemplate(Map<String, String> data, String template, String outPath, List rows, Class clz, boolean isClasspath) {
        ExcelTemplate et = handlerObj2Excel(template, rows, clz, isClasspath);
        et.replaceFinalData(data);
        et.writeToFile(outPath);
    }

    /**
     * 将对象转换为 Excel 并且导出，该方法是基于模板的导出，导出到流,基于 Properties 作为常量数据
     *
     * @param prop        基于Properties的常量数据模型
     * @param template    模板路径
     * @param os          输出流
     * @param rows        对象列表
     * @param clz         对象的类型
     * @param isClasspath 模板是否在classPath路径下
     */
    public void exportObj2ExcelByTemplate(Properties prop, String template, OutputStream os, List rows, Class clz, boolean isClasspath) {
        ExcelTemplate et = handlerObj2Excel(template, rows, clz, isClasspath);
        et.replaceFinalData(prop);
        et.writeToStream(os);
    }

    /**
     * 将对象转换为Excel并且导出，该方法是基于模板的导出，导出到一个具体的路径中,基于Properties作为常量数据
     *
     * @param prop        基于Properties的常量数据模型
     * @param template    模板路径
     * @param outPath     输出路径
     * @param objs        对象列表
     * @param clz         对象的类型
     * @param isClasspath 模板是否在classPath路径下
     */
    public void exportObj2ExcelByTemplate(Properties prop, String template, String outPath, List objs, Class clz, boolean isClasspath) {
        ExcelTemplate et = handlerObj2Excel(template, objs, clz, isClasspath);
        et.replaceFinalData(prop);
        et.writeToFile(outPath);
    }

    private Workbook handleObj2Excel(List objs, Class clz) {
        Workbook wb = new HSSFWorkbook();
        try {
            Sheet sheet = wb.createSheet();
            Row r = sheet.createRow(0);
            List<ExcelHeader> headers = getHeaderList(clz);
            Collections.sort(headers);
            // 写标题
            for (int i = 0; i < headers.size(); i++) {
                r.createCell(i).setCellValue(headers.get(i).getTitle());
            }
            // 写数据
            Object obj = null;
            for (int i = 0; i < objs.size(); i++) {
                r = sheet.createRow(i + 1);
                obj = objs.get(i);
                for (int j = 0; j < headers.size(); j++) {
                    r.createCell(j).setCellValue(BeanUtils.getProperty(obj, getMethodName(headers.get(j))));
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return wb;
    }

    /**
     * 处理对象转换为Excel
     *
     * @param template    模板路径
     * @param rows        对象列表
     * @param clz         对象的类型
     * @param isClasspath 模板是否在classPath路径下
     * @return 模板对象
     */
    private ExcelTemplate handlerObj2Excel(String template, List rows, Class clz, boolean isClasspath) {
        ExcelTemplate et = ExcelTemplate.getInstance();
        try {
            if (isClasspath) {
                et.readTemplateByClasspath(template);
            } else {
                et.readTemplateByPath(template);
            }
            List<ExcelHeader> headers = getHeaderList(clz);
            Collections.sort(headers);
            // 输出值
            for (Object obj : rows) {
                et.createNewRow();
                for (ExcelHeader eh : headers) {
                    Method method = eh.getMethod();
                    Object cellValue = method.invoke(obj);
                    createCell(et, cellValue);
                }
            }
            // 输出序号
            et.insertSerialNumber();
            // 移除样式单元格
            et.removeStyleCells();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return et;
    }

    /**
     * 导出对象到Excel，不是基于模板的，直接新建一个Excel完成导出，基于路径的导出
     *
     * @param outPath 导出路径
     * @param rows    对象列表
     * @param clz     对象类型
     */
    public void exportObj2Excel(String outPath, List rows, Class clz) {
        Workbook wb = handleObj2Excel(rows, clz);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(outPath);
            wb.write(fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 导出对象到Excel，不是基于模板的，直接新建一个Excel完成导出，基于流
     *
     * @param os   输出流
     * @param objs 对象列表
     * @param clz  对象类型
     */
    public void exportObj2Excel(OutputStream os, List objs, Class clz) {
        try {
            Workbook wb = handleObj2Excel(objs, clz);
            wb.write(os);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从类路径读取相应的Excel文件到对象列表
     *
     * @param path     类路径下的path
     * @param clz      对象类型
     * @param readLine 开始行，注意是标题所在行
     * @param tailLine 底部有多少行，在读入对象时，会减去这些行
     * @return
     */
    public List<Object> readExcel2ObjsByClasspath(String path, Class clz, int readLine, int tailLine) {
        Workbook wb = null;
        try {
            wb = new HSSFWorkbook(TemplateFileUtil.getTemplateByClasspath(path));
            return handlerExcel2Objs(wb, clz, readLine, tailLine);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 从文件路径读取相应的Excel文件到对象列表
     *
     * @param path     文件路径下的path
     * @param clz      对象类型
     * @param readLine 开始行，注意是标题所在行
     * @param tailLine 底部有多少行，在读入对象时，会减去这些行
     * @return
     */
    public List<Object> readExcel2ObjsByPath(String path, Class clz, int readLine, int tailLine) {
        Workbook wb = null;
        try {
            wb = new HSSFWorkbook(TemplateFileUtil.getTemplateByClasspath(path));
            return handlerExcel2Objs(wb, clz, readLine, tailLine);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 从类路径读取相应的Excel文件到对象列表，标题行为0，没有尾行
     *
     * @param path 路径
     * @param clz  类型
     * @return 对象列表
     */
    public List<Object> readExcel2ObjsByClasspath(String path, Class clz) {
        return this.readExcel2ObjsByClasspath(path, clz, 0, 0);
    }

    /**
     * 从文件路径读取相应的Excel文件到对象列表，标题行为0，没有尾行
     *
     * @param path 路径
     * @param clz  类型
     * @return 对象列表
     */
    public List<Object> readExcel2ObjsByPath(String path, Class clz) {
        return this.readExcel2ObjsByPath(path, clz, 0, 0);
    }


    /**
     * 单元格值类型判断与转换
     *
     * @param et        模板对象
     * @param cellValue 单元格值
     */
    private void createCell(ExcelTemplate et, Object cellValue) {
        try {
            if (cellValue instanceof Integer) {
                et.createCell((Integer) cellValue);
            } else if (cellValue instanceof Double) {
                et.createCell((Double) cellValue);
            } else if (cellValue instanceof Long) {
                et.createCell((Long) cellValue);
            } else {
                et.createCell((String) cellValue);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建标题行
     *
     * @param et      模板对象
     * @param headers 注解标题信息
     */
    private void createHeader(ExcelTemplate et, List<ExcelHeader> headers) {
        et.createNewRow();
        for (ExcelHeader eh : headers) {
            et.createCell(eh.getTitle());
        }
    }

    /**
     * 根据标题获取相应的方法名称
     *
     * @param eh 数据列对象
     * @return 属性名
     */
    private String getMethodName(ExcelHeader eh) {
        String mn = eh.getMethodName().substring(3);
        mn = mn.substring(0, 1).toLowerCase() + mn.substring(1);
        return mn;
    }

    private String getCellValue(Cell cell) {
        String o = null;
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_BLANK:
                o = "";
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                o = String.valueOf(cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_FORMULA:
                o = String.valueOf(cell.getCellFormula());
                break;
            case Cell.CELL_TYPE_NUMERIC:
                o = String.valueOf(cell.getNumericCellValue());
                break;
            case Cell.CELL_TYPE_STRING:
                o = cell.getStringCellValue();
                break;
            default:
                o = null;
                break;
        }
        return o;
    }

    private List<Object> handlerExcel2Objs(Workbook wb, Class clz, int readLine, int tailLine) {
        Sheet sheet = wb.getSheetAt(0);
        List<Object> objs = null;
        try {
            Row row = sheet.getRow(readLine);
            objs = new ArrayList<Object>();
            Map<Integer, String> maps = getHeaderMap(row, clz);
            if (maps == null || maps.size() <= 0) throw new RuntimeException("要读取的Excel的格式不正确，检查是否设定了合适的行");
            for (int i = readLine + 1; i <= sheet.getLastRowNum() - tailLine; i++) {
                row = sheet.getRow(i);
                Object obj = clz.newInstance();
                for (Cell c : row) {
                    int ci = c.getColumnIndex();
                    String mn = maps.get(ci).substring(3);
                    mn = mn.substring(0, 1).toLowerCase() + mn.substring(1);
                    BeanUtils.copyProperty(obj, mn, this.getCellValue(c));
                }
                objs.add(obj);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return objs;
    }

    private List<ExcelHeader> getHeaderList(Class clz) {
        List<ExcelHeader> headers = new ArrayList<>();
        Method[] methods = clz.getDeclaredMethods();
        for (Method method : methods) {
            String methodName = method.getName();
            if (methodName.startsWith("get")) {
                if (method.isAnnotationPresent(ExcelResources.class)) {
                    ExcelResources er = method.getAnnotation(ExcelResources.class);
                    headers.add(new ExcelHeader(er.title(), er.order(), methodName, method));
                }
            }
        }
        return headers;
    }

    private Map<Integer, String> getHeaderMap(Row titleRow, Class clz) {
        List<ExcelHeader> headers = getHeaderList(clz);
        Map<Integer, String> maps = new HashMap<>();
        for (Cell c : titleRow) {
            String title = c.getStringCellValue();
            for (ExcelHeader eh : headers) {
                if (eh.getTitle().equals(title.trim())) {
                    maps.put(c.getColumnIndex(), eh.getMethodName().replace("get", "set"));
                    break;
                }
            }
        }
        return maps;
    }
}
