package com.foresee.test.loadrunner.lrapi4j.helper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.foresee.test.loadrunner.lrapi4j.lr;

/**
 * 参数集三种模式： 1、KEYVALUE模式 暂时不进入该类 2、参数串模式 3、指定字段及序号模式，即field:index...
 * 
 * @author Administrator
 *
 */
public class ArgsSet {
    public String keyName;
    public ArrayList<ArgItem> fieldList = new ArrayList<ArgItem>();
    public List<ArrayList<String>> dataList;

    /**
     * @param keyName
     * @param fields
     * @param dataList
     */
    public ArgsSet(String keyName, String fields, List<ArrayList<String>> dataList) {
        this(keyName, fields.split(","), dataList);
    }

    public ArgsSet(String keyName, String[] arrfields, List<ArrayList<String>> dataList) {
        super();
        this.keyName = keyName;
        this.dataList = dataList;

        try {
            for (int i = 0; i < arrfields.length; i++) {
                fieldList.add(new ArgItem(arrfields[i], i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // public ArgsSet(String keyName, ArrayList<String> fieldList,
    // List<ArrayList<String>> dataList) {
    // super();
    // this.keyName = keyName;
    // this.fieldList = fieldList;
    // this.dataList = dataList;
    // }

    /**
     * @param skey
     * @param index
     * @return 参数值的数组
     */
    public ArrayList<String> save_paramStringByKey(String skey, int index) {
        ArrayList<String> ret = new ArrayList<String>();
        ArrayList<String> values = dataList.get(index);
        // ArrayList<String> args = getArgsNameByKey(skey);
        Iterator<ArgItem> iter = fieldList.iterator();
        while (iter.hasNext()) {
            ArgItem item = iter.next();
            lr.save_string(values.get(item.index), item.Name);

            ret.add(values.get(item.index));

        }

        return ret;

        // for (int i = 0; i < fieldList.size(); i++) {
        // lr.save_string(values.get(i), fieldList.get(i));
        // }

    }

    public Iterator<Object[]> getArgsIterator() {
        return new ArgsIterator();
    }

    class ArgsIterator implements Iterator<Object[]> {
        private String skey;

        private int MAX;// xlistmap.get(skey).size() - 1;
        private int index = 0;

        public ArgsIterator() {
            super();
            this.skey = keyName;
            this.MAX = dataList.size();
        }

        public boolean hasNext() {
            return index + 1 < MAX;
        }

        public Object[] next() {
            index++;
            // save_paramStringByKey(skey, index);
            // ArrayList<String> ret = dataList.get(index);

            return new Object[]{save_paramStringByKey(skey, index)};
        }
    }

    public class ArgItem {
        public String Name;
        public int index;

        /**
         * @param name
         *            形如 casename:2,method:4
         * 
         * @throws Exception
         */
        public ArgItem(String name, int index) {
            super();

            // 解析参数名字
            if (name.indexOf(":") > 0) {
                this.Name = name.split(":")[0];
                this.index = Integer.parseInt(name.split(":")[1]) - 1; // -1避免以0序号开始

            } else {
                this.Name = name;
                this.index = index;
            }
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "ArgItem [Name=" + Name + ", index=" + index + "]";
        }

    }

}
