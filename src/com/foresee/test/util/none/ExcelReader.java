package com.foresee.test.util.none;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * Excel 导入类，支持泛型<br>
 * 
 * ExcelReader<Enity> er = new ExcelReader<Enity>(file);
 * er.readExcelContentForClass(List<String>,Enity.class)
 * 
 * @author Javen
 * 
 * @param <T>
 */
public class ExcelReader<T> {

    protected Logger logger = LoggerFactory.getLogger(ExcelReader.class);
    private POIFSFileSystem fs;
    private Workbook wb;
    private Sheet sheet;
    private Row row;
    private FileInputStream input;
    private String[] excleTitle;
    private Class<T> poiClass;

    public int sheetNum;// 指定读取的sheet
    public int startRow;// 指定开始行号

    /**
     * @param file
     * @param poiClass
     * @param fileFileName
     * @param sheetNum
     *            指定读取的sheet
     * @param startRow
     *            指定开始行号 包括标题 0为第一行
     */
    public ExcelReader(File file, final Class<T> poiClass, String fileFileName, int sheetNum, int startRow) {
        try {
            boolean isExcel2003 = true;
            /** */
            /** 对文件的合法性进行验证 */
            if (fileFileName.matches("^.+\\.(?i)(xlsx)$")) {
                isExcel2003 = false;
            }
            input = new FileInputStream(file);
            // fs = new POIFSFileSystem(input);
            /** */
            /** 根据版本选择创建Workbook的方式 */
            wb = isExcel2003 ? new HSSFWorkbook(input) : new XSSFWorkbook(input);
            this.sheetNum = sheetNum;
            this.startRow = startRow;
            readExcelTitle();
            this.poiClass = poiClass;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 默认第一个sheet
     * 
     * @param file
     * @param poiClass
     * @param fileFileName
     */
    public ExcelReader(File file, final Class<T> poiClass, String fileFileName) {
        this(file, poiClass, fileFileName, 0, 0);
    }

    /**
     * 根据行列号获取数据
     * 
     * @param row
     *            行
     * @param cell
     *            列
     * @return
     */
    public String getDataBySeat(int row, int cell) {
        Row drow = sheet.getRow(row);
        if (drow == null) {
            return null;
        }
        return getStringCellValue(drow.getCell(cell)).trim();
    }

    public String[] readExcelTitle() {// 读取Excel表格表头的内容

        // 文件 的绝对路径
        sheet = wb.getSheetAt(sheetNum);

        row = sheet.getRow(startRow);// 得到标题的内容对象。
        if (row != null) {

            int colNum = row.getPhysicalNumberOfCells(); // 得到标题总列数
            excleTitle = new String[colNum];
            for (int i = 0; i < colNum; i++) {
                excleTitle[i] = getStringCellValue(row.getCell((short) i));
            }
        }

        return excleTitle;
    }

    /**
     * 根据poi串读取集合
     * 
     * @param list
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public List<T> readExcelContent(List<String> list) throws InstantiationException, IllegalAccessException {
        Assert.notEmpty(list, "poi串集合不能为空!");
        List<String[]> dbTitles = getSplitList(list);
        List<T> ts = new ArrayList<T>();
        int rowNum = sheet.getPhysicalNumberOfRows(); // 得到总行数
        for (int i = startRow + 1; i <= rowNum; i++) { // 正文内容应该从第二行开始,第一行为表头的标题
            row = sheet.getRow(i);
            if (row == null) {
                continue;
            }
            // 列
            boolean notNull = false;
            T t = (T) poiClass.newInstance();
            logger.debug("反射注入创建Bean---------->" + t.getClass().getSimpleName());
            for (int a = 0; a < excleTitle.length; a++) {
                String xslTitle = excleTitle[a];
                String parameter[] = getParameterFormDB(dbTitles, xslTitle);
                if (parameter != null) {
                    notNull = true;
                    // Object value = getObjectCellValue(row.getCell(a));
                    String value = getStringCellValue(row.getCell(a)).trim();
                    logger.debug(parameter[0] + ":" + value);
                    // 类型转换
                    Object valueObj = ReflectionUtils.convertStringToObject(parameter[2], value);
                    if (valueObj != null)
                        ReflectionUtils.invokeSetterMethod(t, parameter[0], valueObj);
                }
            }
            if (notNull)
                ts.add(t);
        }
        return ts;
    }

    private String[] getParameterFormDB(List<String[]> dbTitles, String xslTitle) {
        for (String[] strs : dbTitles) {
            if (strs[1].trim().equals(xslTitle.trim())) {
                return strs;
            }
        }
        return null;
    }

    public List<String[]> getSplitList(List<String> strList) {
        List<String[]> list = new ArrayList<String[]>();
        for (String str : strList) {
            String[] ss = StringUtils.split(str, ",");
            list.add(ss);
        }
        return list;
    }

    public Map<Integer, String> readExcelContent() {// 读取Excel数据内容
        Map<Integer, String> content = new HashMap<Integer, String>();
        String excelStr = "";// excel 内容

        sheet = wb.getSheetAt(sheetNum);
        int rowNum = sheet.getPhysicalNumberOfRows(); // 得到总行数
        row = sheet.getRow(startRow);// 得到标题的内容对象。
        int colNum = row.getPhysicalNumberOfCells();// 得到每行的列数。
        for (int i = startRow + 1; i < rowNum; i++) { // 正文内容应该从第二行开始,第一行为表头的标题
            row = sheet.getRow(i);
            int j = 0;
            while (j < colNum) {
                excelStr += getStringCellValue(row.getCell((short) j)).trim() + "&";
                j++;
            }
            content.put(i, excelStr);
            excelStr = "";
        }

        return content;
    }

    private Object getObjectCellValue(Cell cell) {
        Object object = null;
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
        case Cell.CELL_TYPE_STRING:
            object = cell.getStringCellValue();
            break;
        case Cell.CELL_TYPE_NUMERIC:
            object = cell.getNumericCellValue();
            break;
        case Cell.CELL_TYPE_BOOLEAN:
            object = cell.getBooleanCellValue();
            break;
        case Cell.CELL_TYPE_BLANK:
            object = "";
            break;
        default:
            object = "";
            break;
        }
        return object;
    }

    private static String getStringCellValue(Cell cell) {// 获取单元格数据内容为字符串类型的数据
        String strCell = "";
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
        case Cell.CELL_TYPE_FORMULA:
            cell.getCellFormula();
            try {
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    Date date = cell.getDateCellValue();
                    strCell = (date.getYear() + 1900) + "-" + (date.getMonth() + 1) + "-" + date.getDate();
                    break;
                } else {
                    DecimalFormat format = new DecimalFormat("#####0.00000");
                    strCell = String.valueOf(cell.getNumericCellValue());
                    if (StringUtils.isNotBlank(strCell) && StringUtils.lastIndexOf(strCell, ".0") != -1) {
                        strCell = StringUtils.removeEnd(strCell, ".0");
                    } else {
                        strCell = format.format(cell.getNumericCellValue());
                        if (StringUtils.isNotBlank(strCell) && StringUtils.lastIndexOf(strCell, ".00000") != -1) {
                            strCell = StringUtils.removeEnd(strCell, ".00000");
                        }
                    }
                }
            } catch (IllegalStateException e) {
                strCell = String.valueOf(cell.getRichStringCellValue());
            }
            break;
        case Cell.CELL_TYPE_STRING:
            strCell = cell.getStringCellValue();
            break;
        case Cell.CELL_TYPE_NUMERIC:
            if (HSSFDateUtil.isCellDateFormatted(cell)) {
                strCell = getDateCellValue(cell);
                break;
            } else {
                DecimalFormat format = new DecimalFormat("#####0.00000");
                strCell = String.valueOf(cell.getNumericCellValue());
                if (StringUtils.isNotBlank(strCell) && StringUtils.lastIndexOf(strCell, ".0") != -1) {
                    strCell = StringUtils.removeEnd(strCell, ".0");
                } else {
                    strCell = format.format(cell.getNumericCellValue());
                    if (StringUtils.isNotBlank(strCell) && StringUtils.lastIndexOf(strCell, ".00000") != -1) {
                        strCell = StringUtils.removeEnd(strCell, ".00000");
                    }
                }
                break;
            }
        case Cell.CELL_TYPE_BOOLEAN:
            strCell = String.valueOf(cell.getBooleanCellValue());
            break;
        case Cell.CELL_TYPE_BLANK:
            strCell = "";
            break;
        default:
            strCell = "";
            break;
        }
        return strCell;
    }

    
     public static void main(String[] args) throws IOException {
     
         File f=new File("C:\\次运费信息.xls");
         Workbook wb;
         Sheet sheet;
         Row row;
         FileInputStream input;
         String excelStr = "";// excel 内容
         boolean isExcel2003 = true;
         /** */
         /** 对文件的合法性进行验证 */
         if ("进口完成信息.xls".matches("^.+\\.(?i)(xlsx)$")) {
             isExcel2003 = false;
         }
         input = new FileInputStream(f);
         /** */
         /** 根据版本选择创建Workbook的方式 */
         wb = isExcel2003 ? new HSSFWorkbook(input) : new XSSFWorkbook(input);
        
         sheet = wb.getSheetAt(1);
         row = sheet.getRow(4);// 得到标题的内容对象。
         int colNum = row.getPhysicalNumberOfCells();// 得到每行的列数。
         for (int i =5; i < 7; i++) { // 正文内容应该从第二行开始,第一行为表头的标题
             row = sheet.getRow(i);
             int j = 0;
             while (j < colNum) {
                 excelStr += getStringCellValue(row.getCell((short) j)).trim() + "&";
                 j++;
             }
         }
         System.out.print(excelStr);
         
     }

    private static String getDateCellValue(Cell cell) {// 获取单元格数据内容为日期类型的数据
        String result = "";
        try {
            int cellType = cell.getCellType();
            if (cellType == Cell.CELL_TYPE_NUMERIC) {
                Date date = cell.getDateCellValue();
                result = (date.getYear() + 1900) + "-" + (date.getMonth() + 1) + "-" + date.getDate();
            } else if (cellType == Cell.CELL_TYPE_STRING) {
                String date = getStringCellValue(cell);
                result = date.replaceAll("[年月]", "-").replace("日", "").trim();
            } else if (cellType == Cell.CELL_TYPE_BLANK) {
                result = "";
            }
        } catch (Exception e) {
            System.out.println("日期格式不正确!");
            e.printStackTrace();
        }
        return result;
    }

}