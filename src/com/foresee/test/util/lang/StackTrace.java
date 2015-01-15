package com.foresee.test.util.lang;

import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Vector;

public class StackTrace
// implements RecorderProperties
{
    public static String getStackTrace() {
        return getStackTrace(new Throwable(), 0);
    }

    public static String getStackTrace(int cut) {
        return getStackTrace(new Throwable(), cut);
    }

    public static String getStackTrace(Throwable t) {
        return getStackTrace(t, 0);
    }

    public static String getStackTrace(Throwable t, int cut) {
        ExcpOutputStream eos = new ExcpOutputStream(cut);
        t.printStackTrace(new PrintWriter(eos, true));
        String stackStr = eos.toString();
        return stackStr;
    }

    public static String getStackTraceAsSingleLine() {
        ExcpOutputStream eos = new ExcpOutputStream(0);
        new Throwable().printStackTrace(new PrintWriter(eos, true));
        return formatSingleLine(eos.lines());
    }

    public static String getStackTraceAsSingleLine(Throwable t) {
        ExcpOutputStream eos = new ExcpOutputStream(0);
        t.printStackTrace(new PrintWriter(eos, true));
        return formatSingleLine(eos.lines());
    }

    public static String getStackTraceAsSingleLine(Throwable t, int cut) {
        ExcpOutputStream eos = new ExcpOutputStream(cut);
        t.printStackTrace(new PrintWriter(eos, true));
        return formatSingleLine(eos.lines());
    }

    private static String formatSingleLine(Vector<String> lines) {
        String res = "";
        for (int i = 0; i < lines.size(); i++) {
            String str = (String) lines.elementAt(i);
            String fixes = str.substring(4);
            res = res + "[" + fixes + "]";
            if (i < lines.size() - 1) {
                res = res + " <-- ";
            }
        }
        return res;
    }

    public static String dumpStackTrace(Throwable t, int i, String clazzName, String methodName, String id) {
        System.out.println("\tExamining throwable object, t = " + t);
        String traceStr = getStackTrace(t, i);

        return traceStr;
    }

    private static class ExcpOutputStream extends OutputStream {
        int cut;
        int line_count;
        StringBuffer buffer;
        Vector<String> lines;

        public String toString() {
            return this.buffer.toString();
        }

        public Vector<String> lines() {
            return this.lines;
        }

        public void flush() {
            this.line_count += 1;
        }

        public void write(int j) {
        }

        static final int STACK_TRACE_LIMIT = 30;

        public void write(byte[] b, int i, int j) {
            if ((this.line_count > this.cut) && (this.lines.size() < STACK_TRACE_LIMIT)) {
                String line = new String(b, i, j - 2);
                this.lines.addElement(line);
                this.buffer.append(line).append("\n");
            }
        }

        public ExcpOutputStream(int cut) {
            this.cut = 0;
            this.line_count = 0;
            this.buffer = new StringBuffer();
            this.lines = new Vector<String>();
            this.cut = cut;
        }
    }
}