package com.foresee.test.util.io;

import java.io.FileOutputStream;
import java.io.FilterOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Hashtable;

import com.foresee.test.loadrunner.lrapi4j.lr;

public class console extends PrintStream {
    Hashtable<Thread, String> bufs = new Hashtable<Thread, String>();
    @SuppressWarnings("unused")
    private static final String SPACES = "                                                                                                                                                      ";
    public static final String NOTIFY = "                                                                                                                                                      Notify:";
    public static final String ERROR = "                                                                                                                                                      Error";
    public static final String SYS_OUT = "System.out: ";
    public static final String SYS_ERR = "System.err: ";
    @SuppressWarnings("unused")
    private static final int DEBUG_FLAG = 25;
    private String prefix = "";
    private String suffix = "";
    private PrintWriter pw = null;

    public console() {
        super(new FilterOutputStream(null));
    }

    public console(String prefix, String suffix) {
        super(new FilterOutputStream(null));

        this.prefix = prefix;
        this.suffix = suffix;
    }

    public void write(int i) {
        bufferIt("" + i);
    }

    public void write(byte[] bytes, int i, int j) {
        bufferIt(new String(bytes, i, j));
    }

    public void print(boolean flag) {
        bufferIt(String.valueOf(flag));
    }

    public void print(char c) {
        bufferIt(String.valueOf(c));
    }

    public void print(int i) {
        bufferIt(String.valueOf(i));
    }

    public void print(long l) {
        bufferIt(String.valueOf(l));
    }

    public void print(float f) {
        bufferIt(String.valueOf(f));
    }

    public void print(double d) {
        bufferIt(String.valueOf(d));
    }

    public void print(char[] chars) {
        bufferIt(String.valueOf(chars));
    }

    public void print(String s) {
        bufferIt(String.valueOf(s));
    }

    public void print(Object obj) {
        bufferIt(String.valueOf(obj));
    }

    public void println() {
        newLine();
    }

    public void println(boolean flag) {
        print(flag);
        newLine();
    }

    public void println(char c) {
        print(c);
        newLine();
    }

    public void println(int i) {
        print(i);
        newLine();
    }

    public void println(long l) {
        print(l);
        newLine();
    }

    public void println(float f) {
        print(f);
        newLine();
    }

    public void println(double d) {
        print(d);
        newLine();
    }

    public void println(char[] chars) {
        print(chars);
        newLine();
    }

    public void println(String s) {
        print(s);
        newLine();
    }

    public void println(Object obj) {
        print(obj);
        newLine();
    }

    public void flush() {
        String str = (String) this.bufs.get(Thread.currentThread());

        String for_redirect = this.prefix + (str == null ? "" : str) + this.suffix;
        int i = 0;
        if (for_redirect.indexOf("Exception") == -1)
            i = lr.message(for_redirect);
        else {
            i = lr.error_message(for_redirect);
        }

        if (i == -1) {
            String vuser_dir = lr.get_attrib_string("lr_usr_dir");
            if (vuser_dir == null)
                vuser_dir = "";
            else
                vuser_dir = vuser_dir + "\\";
            String f_name = vuser_dir + "stdOutErr.txt";

            if (this.pw == null)
                try {
                    this.pw = new PrintWriter(new FileOutputStream(f_name, true));
                } catch (Exception e) {
                }
            this.pw.println(for_redirect);
            this.pw.flush();
        }

        this.bufs.remove(Thread.currentThread());
    }

    public void close() {
        lr.message("Notify: Method 'close' is deprecated (redirection enabled).");
    }

    public boolean checkError() {
        lr.message("Notify: Method 'checkError' is deprecated (redirection enabled).");
        return false;
    }

    public void setError() {
        lr.message("Notify: Method 'setError' is deprecated (redirection enabled).");
    }

    private void bufferIt(String str) {
        String temp_str = (String) this.bufs.get(Thread.currentThread());
        String new_str = (temp_str == null ? "" : temp_str) + str;
        this.bufs.put(Thread.currentThread(), new_str);
    }

    private void newLine() {
        flush();
    }
}