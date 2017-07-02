package com.foresee.test.util;
import java.io.*;
import java.util.Properties;
 

/*# 根据状态查询用户
[select_user_by_status]
select id, name
from user_info
where status=0
  and type=1;
 
# 更新用户密码
[update_user_password]
update user_info
set password=?
where id=?

*/
		
public class MultiLinesFile {
    protected Properties props = new Properties();
 
    public MultiLinesFile(File file, String encoding) {
        try {
            load(new BufferedReader(new InputStreamReader(new FileInputStream(file), encoding)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
 
    public MultiLinesFile(InputStream is, String encoding) {
        try {
            load(new BufferedReader(new InputStreamReader(is, encoding)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
 
    public MultiLinesFile(Reader reader) {
        try {
            if (!(reader instanceof BufferedReader)) {
                reader = new BufferedReader(reader);
            }
            load((BufferedReader) reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
 
    private void load(BufferedReader reader) throws IOException {
        String line = null;
        String key = null;
        StringBuilder values = new StringBuilder(64);
 
        while ((line = reader.readLine()) != null) {
            String str = line.trim();
            if (str.startsWith("#")) {
                continue;
            }
            if (str.startsWith("[") && str.endsWith("]")) {
                if (key != null) { // save last key/value
                    props.put(key, values.toString());
                }
                key = str.substring(1, str.length() - 1).trim();
                values.setLength(0);
            } else {
                values.append(line).append("\n");
            }
        }
 
        if (key != null) { // save last key/value
            props.put(key, values.toString());
        }
    }
 
    public boolean exist(String key) {
        return props.containsKey(key);
    }
 
    public String getProperty(String key) {
        return props.getProperty(key);
    }
 
    public Properties getProperties() {
        return props;
    }
     
    public static void main(String[] args) {
        MultiLinesFile p = new MultiLinesFile(new File("sqls.txt"), "utf-8");
        System.out.println(p.getProperties());
    }
}