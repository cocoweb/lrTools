package com.foresee.test.util.lang;

public class StringToken {  
    private char[] srcChars;  
    private char[] delims;  
  
    public StringToken(String src, String delim) {  
        srcChars = src.toCharArray();  
        delims = delim.toCharArray();  
    }  
  
    private int pos = 0;  
    private Status status = Status.UNSTART;  
  
    private int countIndex = 0;  
    private int endIndex = 0;  
  
    public boolean hasMoreTokens() {  
        if (status == Status.COMPLETE) {  
            return false;  
        }  
        countIndex = 0;  
        for (int s = pos; s < srcChars.length; s++) {  
            if (status == Status.UNSTART) {  
                if (srcChars[s] == delims[countIndex]) {  
                    countIndex++;  
                } else {  
                    countIndex = 0;  
                    if (srcChars[s] == delims[countIndex]) {  
                        countIndex++;  
                    }  
                }  
                if (delims.length == countIndex) {  
                    endIndex = s + 1;  
                    status = Status.START;  
                    /* 主要用户处理最开始的字符就是匹配字符问题 */  
                    if (endIndex != delims.length) {  
                        return true;  
                    } else {  
                        pos = endIndex;  
                        return hasMoreTokens();  
                    }  
                }  
            } else {  
                if (srcChars[s] == delims[countIndex]) {  
                    if (delims.length == countIndex + 1) {  
                        endIndex = s + 1;  
                        if (endIndex - pos != delims.length) {  
                            return true;  
                        } else {  
                            pos = endIndex;  
                            return hasMoreTokens();  
                        }  
                    }  
                } else {  
                    status = Status.UNSTART;  
                    // countIndex = 0;  
                }  
            }  
        }  
        if (srcChars.length > pos) {  
            status = Status.COMPLETE;  
            return true;  
        }  
        return false;  
    }  
  
    public String nextToken() {  
        if (status == Status.COMPLETE) {  
            try {  
                int length = srcChars.length - pos;  
                if (length == 0) {  
                    return "";  
                }  
                char[] res = new char[length];  
                System.arraycopy(srcChars, pos, res, 0, res.length);  
                return toString(res);  
            } finally {  
                pos = endIndex;  
            }  
        } else {  
            try {  
                int length = endIndex - pos - delims.length;  
                if (length < 1) {  
                    return "";  
                }  
                char[] res = new char[length];  
                System.arraycopy(srcChars, pos, res, 0, res.length);  
                return toString(res);  
            } finally {  
                pos = endIndex;  
                status = Status.UNSTART;  
            }  
        }  
    }  
  
    public String toString(char[] char_array) {  
        if (char_array.length == 0) {  
            return "";  
        }  
        StringBuilder builder = new StringBuilder();  
        for (int i = 0; i < char_array.length; i++) {  
            builder.append(char_array[i]);  
        }  
        return builder.toString();  
    }  
  
    public enum Status {  
        UNSTART, START, COMPLETE  
    }  
  
}  