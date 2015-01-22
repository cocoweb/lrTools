package com.foresee.test.util.exfile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
* 文件规则
* Microsoft的格式是最简单的。以逗号分隔的值要么是“纯粹的”（仅仅包含在括号之前），
* 要么是在双引号之间（这时数据中的双引号以一对双引号表示）。
* Ten Thousand,10000, 2710 ,,"10,000","It's ""10 Grand"", baby",10K
* 这一行包含七个字段（fields）：
*        Ten Thousand
*        10000
*         2710
*        空字段
*        10,000
*        It's "10 Grand", baby
*        10K
* 每条记录占一行
* 以逗号为分隔符
* 逗号前后的空格会被忽略
* 字段中包含有逗号，该字段必须用双引号括起来。如果是全角的没有问题。
* 字段中包含有换行符，该字段必须用双引号括起来
* 字段前后包含有空格，该字段必须用双引号括起来
* 字段中的双引号用两个双引号表示
* 字段中如果有双引号，该字段必须用双引号括起来
* 第一条记录，可以是字段名
*/
/**

* <p>タイトル: xufei.CSVAnalysis.java


* <p>説明:


* <p>著作権: Copyright (c) 2006


* <p>会社名: technodia


* @author        徐飞
* @version 1.0
* createDate Aug 11, 2008
* 修正履歴
* 修正日      修正者　　　　　　　修正理由
*/
public class CSVAnalysis {
        private InputStreamReader fr = null;
        private BufferedReader br = null;

        public CSVAnalysis(String f) throws IOException {
                fr = new InputStreamReader(new FileInputStream(f));
        }
        public CSVAnalysis(File f) throws IOException {
            fr = new InputStreamReader(new FileInputStream(f));
        }

        /**
         * 解析csv文件 到一个list中
         * 每个单元个为一个String类型记录，每一行为一个list。
         * 再将所有的行放到一个总list中
         * @return
         * @throws IOException
         */
        public List<ArrayList<String>> readCSVFile() throws IOException {
                br = new BufferedReader(fr);
                String rec = null;//一行
                String str;//一个单元格
                List<ArrayList<String>> listFile = new ArrayList<ArrayList<String>>();
                try {                       
                        //读取一行
                        while ((rec = br.readLine()) != null) {
                                Pattern pCells = Pattern
                                                .compile("(\"[^\"]*(\"{2})*[^\"]*\")*[^,]*,");
                                Matcher mCells = pCells.matcher(rec);
                                int endindex=0;
                                
                                ArrayList<String> cells = new ArrayList<String>();//每行记录一个list
                                //读取每个单元格
                                while (mCells.find()) {
                                        str = mCells.group();
                                        endindex = mCells.end();
                                        
                                        str = str.replaceAll(
                                                        "(?sm)\"?([^\"]*(\"{2})*[^\"]*)\"?.*,", "$1");
                                        str = str.replaceAll("(?sm)(\"(\"))", "$2");
                                        cells.add(str);
                                }
                                if(endindex< rec.length()){
                                    str = rec.substring(endindex);
                                    cells.add(str);
                                }
                                
                                listFile.add(cells);
                        }                       
                } catch (Exception e) {
                        e.printStackTrace();
                } finally {
                        if (fr != null) {
                                fr.close();
                        }
                        if (br != null) {
                                br.close();
                        }
                }
                return listFile;
        }

        public static void main(String[] args) throws Throwable {
                CSVAnalysis parser = new CSVAnalysis("p:/fpsuper.dat");
                List<ArrayList<String>> xx= parser.readCSVFile();
                Iterator<ArrayList<String>> iter = xx.iterator();
                while(iter.hasNext()){
                    System.out.println(iter.next());
                }
        }
} 