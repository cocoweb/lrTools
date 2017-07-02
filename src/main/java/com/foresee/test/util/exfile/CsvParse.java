package com.foresee.test.util.exfile;

import java.io.BufferedReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

/**
 * @author Administrator
 *该解析算法的解析规则与excel或者wps大致相同。另外包含去掉注释的方法。

构建方法该类包含一个构建方法，参数为要读取的csv文件的文件名（包含绝对路径）。

普通方法：

① getVContent()：一个得到当前行的值向量的方法。如果调用此方法前未调用readCSVNextRecord方法，则将返回Null。

② getLineContentVector()：一个得到下一行值向量的方法。如果该方法返回Null，则说明已经读到文件末尾。

③ close()：关闭流。该方法为调用该类后应该被最后调用的方法。

④ readCSVNextRecord()：该方法读取csv文件的下一行，如果该方法已经读到了文件末尾，则返回false；

⑤ readAtomString(String)：该方法返回csv文件逻辑一行的第一个值，和该逻辑行第一个值后面的内容，如果该内容以逗号开始，则已经去掉了该逗号。这两个值以一个二维数组的方法返回。

⑥ isQuoteAdjacent(String)：判断一个给定字符串的引号是否两两相邻。如果两两相邻，返回真。如果该字符串不包含引号，也返回真。

⑦ readCSVFileTitle()：该方法返回csv文件中的第一行——该行不以#号开始（包括正常解析后的#号），且该行不为空 
 */
public class CsvParse {
    // 声明读取流
    /**
     * 
     */
    private BufferedReader inStream = null;
    // 声明返回向量
    private Vector<String> vContent = null;

    /**
     * 构建方法，参数为csv文件名<br>
     * 如果没有找到文件，则抛出异常<br>
     * 如果抛出异常，则不能进行页面的文件读取操作
     */
    public CsvParse(String csvFileName) throws FileNotFoundException {
        inStream = new BufferedReader(new FileReader(csvFileName));
    }

    /**
     * 返回已经读取到的一行的向量
     * 
     * @return vContent
     */
    public Vector<String> getVContent() {
        return this.vContent;
    }

    /**
     * 读取下一行，并把该行的内容填充入向量中<br>
     * 返回该向量<br>
     * 
     * @return vContent 装载了下一行的向量
     * @throws IOException
     * @throws Exception
     */
    public Vector<String> getLineContentVector() throws IOException, Exception {
        if (this.readCSVNextRecord()) {
            return this.vContent;
        }
        return null;
    }

    /**
     * 关闭流
     */
    public void close() {
        if (inStream != null) {
            try {
                inStream.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /**
     * 调用此方法时应该确认该类已经被正常初始化<br>
     * 该方法用于读取csv文件的下一个逻辑行<br>
     * 读取到的内容放入向量中<br>
     * 如果该方法返回了false，则可能是流未被成功初始化<br>
     * 或者已经读到了文件末尾<br>
     * 如果发生异常，则不应该再进行读取
     * 
     * @return 返回值用于标识是否读到文件末尾
     * @throws Exception
     */
    public boolean readCSVNextRecord() throws IOException, Exception {
        // 如果流未被初始化则返回false
        if (inStream == null) {
            return false;
        }
        // 如果结果向量未被初始化，则初始化
        if (vContent == null) {
            vContent = new Vector<String>();
        }
        // 移除向量中以前的元素
        vContent.removeAllElements();
        // 声明逻辑行
        String logicLineStr = "";
        // 用于存放读到的行
        StringBuilder strb = new StringBuilder();
        // 声明是否为逻辑行的标志，初始化为false
        boolean isLogicLine = false;
        try {
            while (!isLogicLine) {
                String newLineStr = inStream.readLine();
                if (newLineStr == null) {
                    strb = null;
                    vContent = null;
                    isLogicLine = true;
                    break;
                }
                if (newLineStr.startsWith("#")) {
                    // 去掉注释
                    continue;
                }
                if (!strb.toString().equals("")) {
                    strb.append("\r\n");
                }
                strb.append(newLineStr);
                String oldLineStr = strb.toString();
                if (oldLineStr.indexOf(",") == -1) {
                    // 如果该行未包含逗号
                    if (containsNumber(oldLineStr, "\"") % 2 == 0) {
                        // 如果包含偶数个引号
                        isLogicLine = true;
                        break;
                    } else {
                        if (oldLineStr.startsWith("\"")) {
                            if (oldLineStr.equals("\"")) {
                                continue;
                            } else {
                                String tempOldStr = oldLineStr.substring(1);
                                if (isQuoteAdjacent(tempOldStr)) {
                                    // 如果剩下的引号两两相邻，则不是一行
                                    continue;
                                } else {
                                    // 否则就是一行
                                    isLogicLine = true;
                                    break;
                                }
                            }
                        }
                    }
                } else {
                    // quotes表示复数的quote
                    String tempOldLineStr = oldLineStr.replace("\"\"", "");
                    int lastQuoteIndex = tempOldLineStr.lastIndexOf("\"");
                    if (lastQuoteIndex == 0) {
                        continue;
                    } else if (lastQuoteIndex == -1) {
                        isLogicLine = true;
                        break;
                    } else {
                        tempOldLineStr = tempOldLineStr.replace("\",\"", "");
                        lastQuoteIndex = tempOldLineStr.lastIndexOf("\"");
                        if (lastQuoteIndex == 0) {
                            continue;
                        }
                        if (tempOldLineStr.charAt(lastQuoteIndex - 1) == ',') {
                            continue;
                        } else {
                            isLogicLine = true;
                            break;
                        }
                    }
                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
            // 发生异常时关闭流
            if (inStream != null) {
                inStream.close();
            }
            throw ioe;
        } catch (Exception e) {
            e.printStackTrace();
            // 发生异常时关闭流
            if (inStream != null) {
                inStream.close();
            }
            throw e;
        }
        if (strb == null) {
            // 读到行尾时为返回
            return false;
        }
        // 提取逻辑行
        logicLineStr = strb.toString();
        if (logicLineStr != null) {
            // 拆分逻辑行，把分离出来的原子字符串放入向量中
            while (!logicLineStr.equals("")) {
                String[] ret = readAtomString(logicLineStr);
                String atomString = ret[0];
                logicLineStr = ret[1];
                vContent.add(atomString);
            }
        }
        return true;
    }

    /**
     * 读取一个逻辑行中的第一个字符串，并返回剩下的字符串<br>
     * 剩下的字符串中不包含第一个字符串后面的逗号<br>
     * 
     * @param lineStr
     *            一个逻辑行
     * @return 第一个字符串和剩下的逻辑行内容
     */
    public String[] readAtomString(String lineStr) {
        String atomString = "";// 要读取的原子字符串
        String orgString = "";// 保存第一次读取下一个逗号时的未经任何处理的字符串
        String[] ret = new String[2];// 要返回到外面的数组
        boolean isAtom = false;// 是否是原子字符串的标志
        String[] commaStr = lineStr.split(",");
        while (!isAtom) {
            for (String str : commaStr) {
                if (!atomString.equals("")) {
                    atomString = atomString + ",";
                }
                atomString = atomString + str;
                orgString = atomString;
                if (!isQuoteContained(atomString)) {
                    // 如果字符串中不包含引号，则为正常，返回
                    isAtom = true;
                    break;
                } else {
                    if (!atomString.startsWith("\"")) {
                        // 如果字符串不是以引号开始，则表示不转义，返回
                        isAtom = true;
                        break;
                    } else if (atomString.startsWith("\"")) {
                        // 如果字符串以引号开始，则表示转义
                        if (containsNumber(atomString, "\"") % 2 == 0) {
                            // 如果含有偶数个引号
                            String temp = atomString;
                            if (temp.endsWith("\"")) {
                                temp = temp.replace("\"\"", "");
                                if (temp.equals("")) {
                                    // 如果temp为空
                                    atomString = "";
                                    isAtom = true;
                                    break;
                                } else {
                                    // 如果temp不为空，则去掉前后引号
                                    temp = temp.substring(1, temp.lastIndexOf("\""));
                                    if (temp.indexOf("\"") > -1) {
                                        // 去掉前后引号和相邻引号之后，若temp还包含有引号
                                        // 说明这些引号是单个单个出现的
                                        temp = atomString;
                                        temp = temp.substring(1);
                                        temp = temp.substring(0, temp.indexOf("\""))
                                                + temp.substring(temp.indexOf("\"") + 1);
                                        atomString = temp;
                                        isAtom = true;
                                        break;
                                    } else {
                                        // 正常的csv文件
                                        temp = atomString;
                                        temp = temp.substring(1, temp.lastIndexOf("\""));
                                        temp = temp.replace("\"\"", "\"");
                                        atomString = temp;
                                        isAtom = true;
                                        break;
                                    }
                                }
                            } else {
                                // 如果不是以引号结束，则去掉前两个引号
                                temp = temp.substring(1, temp.indexOf('\"', 1))
                                        + temp.substring(temp.indexOf('\"', 1) + 1);
                                atomString = temp;
                                isAtom = true;
                                break;
                            }
                        } else {
                            // 如果含有奇数个引号
                            // TODO 处理奇数个引号的情况
                            if (!atomString.equals("\"")) {
                                String tempAtomStr = atomString.substring(1);
                                if (!isQuoteAdjacent(tempAtomStr)) {
                                    // 这里做的原因是，如果判断前面的字符串不是原子字符串的时候就读取第一个取到的字符串
                                    // 后面取到的字符串不计入该原子字符串
                                    tempAtomStr = atomString.substring(1);
                                    int tempQutoIndex = tempAtomStr.indexOf("\"");
                                    // 这里既然有奇数个quto，所以第二个quto肯定不是最后一个
                                    tempAtomStr = tempAtomStr.substring(0, tempQutoIndex)
                                            + tempAtomStr.substring(tempQutoIndex + 1);
                                    atomString = tempAtomStr;
                                    isAtom = true;
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        // 先去掉之前读取的原字符串的母字符串
        if (lineStr.length() > orgString.length()) {
            lineStr = lineStr.substring(orgString.length());
        } else {
            lineStr = "";
        }
        // 去掉之后，判断是否以逗号开始，如果以逗号开始则去掉逗号
        if (lineStr.startsWith(",")) {
            if (lineStr.length() > 1) {
                lineStr = lineStr.substring(1);
            } else {
                lineStr = "";
            }
        }
        ret[0] = atomString;
        ret[1] = lineStr;
        return ret;
    }

    /**
     * 该方法取得父字符串中包含指定字符串的数量<br>
     * 如果父字符串和字字符串任意一个为空值，则返回零
     * 
     * @param parentStr
     * @param parameter
     * @return
     */
    public int containsNumber(String parentStr, String parameter) {
        int containNumber = 0;
        if (parentStr == null || parentStr.equals("")) {
            return 0;
        }
        if (parameter == null || parameter.equals("")) {
            return 0;
        }
        for (int i = 0; i < parentStr.length(); i++) {
            i = parentStr.indexOf(parameter, i);
            if (i > -1) {
                i = i + parameter.length();
                i--;
                containNumber = containNumber + 1;
            } else {
                break;
            }
        }
        return containNumber;
    }

    /**
     * 该方法用于判断给定的字符串中的引号是否相邻<br>
     * 如果相邻返回真，否则返回假<br>
     * 
     * @param p_String
     * @return
     */
    public boolean isQuoteAdjacent(String p_String) {
        boolean ret = false;
        String temp = p_String;
        temp = temp.replace("\"\"", "");
        if (temp.indexOf("\"") == -1) {
            ret = true;
        }
        // TODO 引号相邻
        return ret;
    }

    /**
     * 该方法用于判断给定的字符串中是否包含引号<br>
     * 如果字符串为空或者不包含返回假，包含返回真<br>
     * 
     * @param p_String
     * @return
     */
    public boolean isQuoteContained(String p_String) {
        boolean ret = false;
        if (p_String == null || p_String.equals("")) {
            return false;
        }
        if (p_String.indexOf("\"") > -1) {
            ret = true;
        }
        return ret;
    }

    /**
     * 读取文件标题
     * 
     * @return 正确读取文件标题时返回 true,否则返回 false
     * @throws Exception
     * @throws IOException
     */
    public boolean readCSVFileTitle() throws IOException, Exception {
        String strValue = "";
        boolean isLineEmpty = true;
        do {
            if (!readCSVNextRecord()) {
                return false;
            }
            if (vContent.size() > 0) {
                strValue = (String) vContent.get(0);
            }
            for (String str : vContent) {
                if (str != null && !str.equals("")) {
                    isLineEmpty = false;
                    break;
                }
            }
            // csv 文件中前面几行以 # 开头为注释行
        } while (strValue.trim().startsWith("#") || isLineEmpty);
        return true;
    }
}
