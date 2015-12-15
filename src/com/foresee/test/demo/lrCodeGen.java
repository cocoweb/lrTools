package com.foresee.test.demo;

import java.io.IOException;
import javaHttpJ.JavaHTTPCodeGen;
import junit.framework.TestCase;


//java lrCodeGen -classpath 
public class lrCodeGen extends TestCase
{
	JavaHTTPCodeGen javaHTTPCodeGen;

	  public static void main(String[] args)
	  {
		  //以下代码放到LoadRunner下 运行，可以从c生成java代码
	    try
	    {
	      JavaHTTPCodeGen javaHTTPCodeGen = JavaHTTPCodeGen.getInstance();

	      String scriptDirectory ="z:\\F\\Products File\\hzscripts\\c_omni\\data";   // "C:\\scripts\\SS\\siteScopeReplayFromRec2\\data";
	      if (args.length != 0) {
	        scriptDirectory = args[0];
	      }
	      if (scriptDirectory.lastIndexOf('\\') != scriptDirectory.length() - 1)
	        scriptDirectory = scriptDirectory + '\\';
	      System.out.println("script directory:" + scriptDirectory);

	      String LRDirectory = "c:\\HP\\LoadRunner\\";
	      String scriptCFileName = "actions.c";
	      String newFileName = "newAction.java";

	      javaHTTPCodeGen.phase1RunSed(LRDirectory, scriptDirectory, scriptCFileName, newFileName);

	      javaHTTPCodeGen.phase2ScanScript(scriptDirectory, newFileName);
	    } catch (IOException e) {
	      e.printStackTrace();
	    } catch (InterruptedException e) {
	      e.printStackTrace();
	    }
	    catch (Throwable t)
	    {
	      t.printStackTrace();
	    }
	  }

	  public void test() throws Exception {
	    try {
	      JavaHTTPCodeGen javaHTTPCodeGen = JavaHTTPCodeGen.getInstance();
	      String LRDirectory = "c:\\hp\\loadrunner\\";
	      String scriptDirectory = "C:\\scripts\\SS\\noname2\\";
	      String scriptCFileName = "action.c";
	      String newFileName = "newAction.java";

	      javaHTTPCodeGen.phase1RunSed(LRDirectory, scriptDirectory, scriptCFileName, newFileName);

	      javaHTTPCodeGen.phase2ScanScript(scriptDirectory, newFileName);
	    }
	    catch (IOException ex) {
	      ex.printStackTrace();
	    }
	    catch (Throwable t) {
	      t.printStackTrace();
	    }
	  }
	  
	  public void LR_c2java(String scriptDirectory){
	      try
	        {
	          JavaHTTPCodeGen javaHTTPCodeGen = JavaHTTPCodeGen.getInstance();

	          //String scriptDirectory ="z:\\F\\Products File\\hzscripts\\c_omni\\data";   // "C:\\scripts\\SS\\siteScopeReplayFromRec2\\data";
//	          if (args.length != 0) {
//	            scriptDirectory = args[0];
//	          }
	          if (scriptDirectory.lastIndexOf('\\') != scriptDirectory.length() - 1)
	            scriptDirectory = scriptDirectory + '\\';
	          System.out.println("script directory:" + scriptDirectory);

	          String LRDirectory = "c:\\HP\\LoadRunner\\";
	          String scriptCFileName = "actions.c";
	          String newFileName = "newAction.java";

	          javaHTTPCodeGen.phase1RunSed(LRDirectory, scriptDirectory, scriptCFileName, newFileName);

	          javaHTTPCodeGen.phase2ScanScript(scriptDirectory, newFileName);
	        } catch (IOException e) {
	          e.printStackTrace();
	        } catch (InterruptedException e) {
	          e.printStackTrace();
	        }
	        catch (Throwable t)
	        {
	          t.printStackTrace();
	        }

	  }
	}


