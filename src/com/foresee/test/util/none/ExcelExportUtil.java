package com.foresee.test.util.none;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.record.formula.functions.T;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

import com.google.common.collect.Maps;
import com.zxyg.Constants;
import com.zxyg.modules.orm.Page;
import com.zxyg.modules.web.struts2.Struts2Utils;

/**
 * Poi excel导出工具类
 * 
 * @author Javen
 * 
 */
public class ExcelExportUtil {

    private static Map<String, CellStyle> styles;

    /**
     * 导出excel的数据处理及excel的导出
     * 
     * @param page
     *            业务数据
     * @param ehName
     *            (实体属性名 - 编号(excel列名) - 数据类型 (S String; B boolean; D date))
     *            例：name-名称-S,sex-性别-B
     * @param excelName
     *            excel名字 XX.xls
     * @return
     */
    public static void exportExcelData(Page<?> page, String ehName, String excelName) {
        List<String[]> list = new ArrayList<String[]>();
        for (String str : StrUtils.splitStr(ehName, ",")) {
            String[] ename = new String[3];
            ename[0] = StrUtils.splitStr(str, "-")[0];
            ename[1] = StrUtils.splitStr(str, "-")[1];
            ename[2] = StrUtils.splitStr(str, "-")[2];
            list.add(ename);
        }
        Workbook wb = ExcelExportUtil.exportExcelWorkbook(page, list, "sheet");
        Struts2Utils.renderExcel(wb, excelName, "no-cache:true");
    }

    /**
     * @param page
     * @param ehName  
     *          <value>resString,资源串,S</value>
     *          <value>label,名称,S</value>
     * @param excelName
     */
    public static void exportExcelData(Page<?> page, List<String> ehName, String excelName) {
        List<String[]> list = new ArrayList<String[]>();
        for (String str : ehName) {
            String[] ename = new String[3];
            ename[0] = StrUtils.splitStr(str, ",")[0];
            ename[1] = StrUtils.splitStr(str, ",")[1];
            ename[2] = StrUtils.splitStr(str, ",")[2];
            list.add(ename);
        }
        Workbook wb = ExcelExportUtil.exportExcelWorkbook(page, list, "sheet");
        Struts2Utils.renderExcel(wb, excelName, "no-cache:true");
    }

    // private static int rowIndex = 0;

    /**
     * @param page
     *            结果集
     * @param list
     *            number(实体属性名),编号(excel列名),S(数据类型 S String; B boolean; D date)
     * @param title
     *            excel sheet名称
     * @return
     */
    public static Workbook exportExcelWorkbook(Page page, List<String[]> list, String title) {

        // 创建Workbook
        Workbook wb = new HSSFWorkbook();
        // 创建所有Cell Style
        createStyles(wb);

        List listData = page.getResult();
        if (listData == null || listData.size() <= 0) {
            return wb;
        }
        int maxSize = Constants.EXCEL_MAXSIZE;
        List<List<T>> listTotalData = new ArrayList<List<T>>();
        List listDataTmp = null;
        boolean index = true;
        for (int i = 0; i < listData.size(); i++) {
            if (index) {
                listDataTmp = new ArrayList();
            }
            listDataTmp.add(listData.get(i));
            if (index) {
                listTotalData.add(listDataTmp);
            }
            if ((i + 1) % (maxSize) == 0) {
                index = true;
                continue;
            }
            index = false;
        }
        int tIndex = 0;
        for (List<T> excelData : listTotalData) {
            tIndex++;
            // 创建工作表.
            Sheet s = wb.createSheet(title + tIndex);
            // 设定冻结表头
            s.createFreezePane(0, 1, 0, 1);
            // 设定所有Column宽度自动配合内容宽度
            s.autoSizeColumn(0);
            s.autoSizeColumn(1);
            s.autoSizeColumn(2);
            // 产生标题
            //generateTitle(s, title + tIndex);
            // 产生表头
            generateHeader(s, list);
            // 产生内容
            generateContent(s, excelData, list, title + tIndex);
            // 产生合计
            // generateTotals(s);
        }

        return wb;
    }

    private static void generateTitle(Sheet s, String title) {
        Row r = s.createRow(0);
        Cell c1 = r.createCell(0);
        c1.setCellValue(title);
        c1.setCellStyle(styles.get("header"));
        // 合并单元格
        s.addMergedRegion(CellRangeAddress.valueOf("$A$1:$C$1"));
    }

    private static void generateHeader(Sheet s, List<String[]> list) {

        Row r = s.createRow(0);
        CellStyle headerStyle = styles.get("header");

        for (int a = 0; a < list.size(); a++) {
            Cell c1 = r.createCell(a);
            c1.setCellValue(list.get(a)[1]);
            c1.setCellStyle(headerStyle);
        }

    }

    private static void generateContent(Sheet s, List list, List<String[]> strs, String title) {
        CellStyle dateCellStyle = styles.get("dateCell");
        CellStyle numberCellStyle = styles.get("numberCell");

        int rowIndex = 1;
        for (int a = 0; a < list.size(); a++) {
            Row r = s.createRow(rowIndex++);
            for (int b = 0; b < strs.size(); b++) {
                Cell c1 = r.createCell(b);
                c1.setCellValue(convertObj(ReflectionUtils.getFieldValue(list.get(a), strs.get(b)[0])));
                // c1.setCellStyle(dateCellStyle);
            }
        }
    }

    private static String convertObj(Object obj) {
        if (obj != null) {
            if (obj instanceof Date) {
                return DateUtil.formatSimple((Date) obj);
            }
            if (obj instanceof Boolean) {
                boolean b = (Boolean) obj;
                if (b) {
                    return "是";
                } else {
                    return "否";
                }
            }
            return obj.toString();
        }
        return null;
    }

    private static void generateTotals(Sheet s) {

        Row r = s.createRow(200);
        CellStyle totalStyle = styles.get("total");

        // Cell强行分行
        Cell c1 = r.createCell(0);
        c1.setCellStyle(totalStyle);
        c1.setCellValue("合\n计");

        // 合计公式
        Cell c2 = r.createCell(1);
        c2.setCellStyle(totalStyle);
        c2.setCellFormula("SUM(B3:B32)");

        Cell c3 = r.createCell(2);
        c3.setCellStyle(totalStyle);
        c3.setCellFormula("SUM(C3:C32)");
    }

    private static Map<String, CellStyle> createStyles(Workbook wb) {
        styles = Maps.newHashMap();
        DataFormat df = wb.createDataFormat();

        // --字体设定 --//

        // 普通字体
        Font normalFont = wb.createFont();
        normalFont.setFontHeightInPoints((short) 10);

        // 加粗字体
        Font boldFont = wb.createFont();
        boldFont.setFontHeightInPoints((short) 10);
        boldFont.setBoldweight(Font.BOLDWEIGHT_BOLD);

        // 蓝色加粗字体
        Font blueBoldFont = wb.createFont();
        blueBoldFont.setFontHeightInPoints((short) 10);
        blueBoldFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        blueBoldFont.setColor(IndexedColors.BLUE.getIndex());

        // --Cell Style设定-- //

        // 标题格式
        CellStyle headerStyle = wb.createCellStyle();
        headerStyle.setFont(boldFont);
        styles.put("header", headerStyle);

        // 日期格式
        CellStyle dateCellStyle = wb.createCellStyle();
        dateCellStyle.setFont(normalFont);
        dateCellStyle.setDataFormat(df.getFormat("yyyy"));
        setBorder(dateCellStyle);
        styles.put("dateCell", dateCellStyle);

        // 数字格式
        CellStyle numberCellStyle = wb.createCellStyle();
        numberCellStyle.setFont(normalFont);
        numberCellStyle.setDataFormat(df.getFormat("#,##0.00"));
        setBorder(numberCellStyle);
        styles.put("numberCell", numberCellStyle);

        // 合计列格式
        CellStyle totalStyle = wb.createCellStyle();
        totalStyle.setFont(blueBoldFont);
        totalStyle.setWrapText(true);
        totalStyle.setAlignment(CellStyle.ALIGN_RIGHT);
        setBorder(totalStyle);
        styles.put("total", totalStyle);

        return styles;
    }

    private static void setBorder(CellStyle style) {
        // 设置边框
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());

        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());

        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());

        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
    }

}
