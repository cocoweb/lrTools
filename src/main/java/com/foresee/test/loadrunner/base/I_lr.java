package com.foresee.test.loadrunner.base;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.Enumeration;
import java.util.Vector;

import com.foresee.test.util.io.console;
import com.foresee.test.util.lang.StackTrace;


public abstract class I_lr {

	public static final int PASS = 0;
	public static final int FAIL = 1;
	public static final int AUTO = 2;
	public static final int STOP = 3;

	public static String eval_string(String paramString) {
		return lrapi.lr.eval_string(paramString);
	
	}

	public static int eval_int(String paramString) {

		return lrapi.lr.eval_int(paramString);
	}

	public static int save_string(String sValue, String ParaName) {

		return lrapi.lr.save_string(sValue, ParaName);
	}
	public static int save_int(int sValue, String ParaName) {
		return lrapi.lr.save_int(sValue, ParaName);
	}

	public static long start_timer() {
		return System.currentTimeMillis();
	}

	public static long end_timer(long startTimeMilis) {
		return System.currentTimeMillis() - startTimeMilis;
	}

	public static int start_transaction(String paramString) {
		
		return lrapi.lr.start_transaction(paramString);
	}

	public static int end_transaction(String paramString, int paramInt) {
		
		return lrapi.lr.end_transaction(paramString, paramInt);
	}
	public static int set_transaction(String paramString, double paramDouble, int paramInt){
	 // TODO 包装LoadRunner
            return lrapi.lr.set_transaction(paramString, paramDouble,paramInt);
	}
	
	public static int set_transaction_status(int paramInt){
	         // TODO 包装LoadRunner
            return lrapi.lr.set_transaction_status(paramInt);
	    
	}
	public static int message(String paramString) {
		// TODO 包装LoadRunner
		return lrapi.lr.message(paramString);
	}

	public static int error_message(String paramString) {
		return lrapi.lr.error_message(paramString);
	}

	public static void think_time(double d) {
		try {
			Thread.sleep((long) (d * 1000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static String usr_dir = null;
	public static String read_xml(String filename)
	   {
	     StringBuffer buf = new StringBuffer();
	     try
	     {
	       if (usr_dir == null) {
	         usr_dir = "";
	       }
	       File xml_file = new File(usr_dir + File.separator + "data" + File.separator + "SerializedObjects" + File.separator + filename);
	 
	       if (!xml_file.exists()) {
	         error_message(xml_file + " is not found.");
	         return "";
	       }
	 
	       FileReader file_reader = new FileReader(xml_file);
	       BufferedReader reader = new BufferedReader(file_reader);
	       String line = null;
	 
	       while ((line = reader.readLine()) != null)
	         buf.append(line);
	       
	       reader.close();
	     }
	     catch (Exception e) {
	       error_message("cannot read file " + filename);
	       printException(e);
	     }
	     return eval_string(buf.toString());
	   }
	public I_lr() {
		super();
	}
	//public static final Class connectExc = SRVUtils.loadClass("java.rmi.ConnectException");
	private static Vector<?> stubList = new Vector<Object>();
	
	 public static void printException(Throwable jthrow) {
        	   String message = "";
        	   String stacktrace = StackTrace.getStackTrace(jthrow);
        
        //	   if (((connectExc != null) && (connectExc.isInstance(jthrow))) || ((wlConnectExc != null) && (wlConnectExc.isInstance(jthrow))) || ((peerGoneExc != null) && (peerGoneExc.isInstance(jthrow))))
        //	   {
        	     for (Enumeration<?> stub_enum = stubList.elements(); stub_enum.hasMoreElements(); ) {
        	       if (stacktrace.indexOf((String)stub_enum.nextElement()) != -1) {
        	         message = "Message: [Possible connection problem due to expired RemoteReference ID; script may need re-recording]; ";
        	         break;
        	       }
        	     }
        	     jthrow.printStackTrace();
        	     int result = error_message(message + jthrow);
        	     if (result == -1) {
        	       System.out.println("Exception occured: " + message + jthrow);
        	       System.out.println(stacktrace);
        	     } else {
        	       error_message(stacktrace);
        	     }
        	     set_transaction_status(1);
	   }
	 
	 public static int redirect(String file_name, String str)
	 {
	   return redirect(file_name, str, true);
	 }

	
    public static int redirect(String file_name, String str, boolean append)
	 {
	   PrintStream out = null;
	   try
	   {
	     File file = new File(file_name);
	     out = new PrintStream(new FileOutputStream(file.toString(), append));
	   } catch (Exception e) {
	     System.out.println("lrapi.redirect: e =  " + e);
	     return 1;
	   }

	   out.println(str);
	   out.flush();

	   return 0;
	 }
	 
	 static PrintStream std_out = System.out;
	 static PrintStream std_err = System.err;
	 static console out_console = new console("System.out: ", "                                                                     Notify:");
	 static console err_console = new console("System.err: ", "                                                                     Error");
	 
	 public static void enable_redirection(boolean flag)
	 {
	   if (flag) {
	       System.setOut(out_console);
	       System.setErr(err_console);
	   } else {
	       System.setOut(std_out);
	       System.setErr(std_err);
	   }
	 }
}