package com.foresee.test.util.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

/***
 * 
 * @author allan
 *    
 */
public class FileUtil {

	public static void write(String path, String content, boolean append) {
		FileWriter writer = null;  
		try { 
			// 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
			writer = new FileWriter(path, append);
			writer.write(content);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static String readFile(String fileName) {
		File file = new File(fileName);
		BufferedReader reader = null;
		StringBuffer sb = new StringBuffer();

		try {
			// System.out.println("以行为单位读取文件内容，一次读一整行：");
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			// int line = 1;
			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null) {
				// 显示行号
				// System.out.println("line " + line + ": " + tempString);
				// result.add(tempString);
				// line++;

				sb.append(tempString);
				sb.append("\r\n");
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}

		return sb.toString();
	}

	public static void write(String path, String content) {
		String s = new String();
		String s1 = new String();
		try {
			File f = new File(path);
			if (f.exists()) {
				System.out.println("文件存在");
			} else {
				System.out.println("文件不存在，正在创建...");
				if (f.createNewFile()) {
					System.out.println("文件创建成功！");
				} else {
					System.out.println("文件创建失败！");
				}

			}
			BufferedReader input = new BufferedReader(new FileReader(f));

			while ((s = input.readLine()) != null) {
				s1 += s + "\n";
			}

			input.close();
			s1 += content;

			BufferedWriter output = new BufferedWriter(new FileWriter(f));
			output.write(s1);
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/***
	 * 合并文件
	 * 
	 * @param toFile
	 *            目标文件
	 * @param files
	 *            要合并的文件数组
	 */
	public static void hebingFiles(File toFile, File[] files) {
		try {
			@SuppressWarnings("resource")
			FileOutputStream fout = new FileOutputStream(toFile);
			FileInputStream fis = null;
			for (File file : files) {
				fis = new FileInputStream(file);
				byte data[] = new byte[1024];
				int read = 0;
				while ((read = fis.read(data)) != -1) {
					fout.write(data, 0, read);
				}
				fis.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/***
	 * 文件分割
	 * 
	 * @param file
	 *            要分割的文件
	 * @param dir
	 *            保存分割数据的文件夹
	 * @param comminuteSize
	 *            分割块大小
	 */
	@SuppressWarnings("resource")
    public static void fileComminute(File file, File dir, int comminuteSize) {
		long fileSize = file.length();
		System.out.println("fileSize==>" + fileSize);
		long bit = (fileSize - 1) / comminuteSize + 1;
		System.out.println("bit==>" + bit);
		if (fileSize < 2) {
			System.out.println("文件太小,不需要分割");
			return;
		}
		int fileNumber = 0;
		try {
			FileInputStream fis = new FileInputStream(file);
			String name = file.getName();
			FileOutputStream fout = null;
			int tempSize = comminuteSize;
			int read = 0;
			byte[] datas = new byte[1024];
			while ((read = fis.read(datas)) != -1) {
				if (tempSize >= comminuteSize) {
					if (fout != null) {
						fout.close();
					}
					File nextFile = new File(dir, name + ".sp" + (fileNumber++));
					fout = new FileOutputStream(nextFile);
					tempSize = 0;

				}
				tempSize += read;
				fout.write(datas, 0, read);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static boolean deleteRecursive(File path) {
		if (!path.exists()) {
			throw new RuntimeException("can not find");
		}
		boolean ret = true;
		if (path.isDirectory()) {
			for (File f : path.listFiles()) {
				ret = ret && deleteRecursive(f);
			}
		}
		return ret && path.delete();
	}

	// find out file 所在的folder名 如c:/temp/aa.txt,则返回c:/temp
	public static String getFolderPath(String file) {
		String result = "";
		if (null == file || "".equals(file)) {

		} else {
			String[] ss = file.split("\\/");
			for (int i = 0; i < ss.length - 1; i++) {
				result += ss[i];
				result += "/";
			}
		}

		return result;
	}

//	public static boolean createFolder(String folder) {
//		File dir = new File(folder);
//		return dir.mkdirs();
//	}

	@SuppressWarnings("unused")
    public static void Copy(String oldPath, String newPath) {
		FileOutputStream fs = null;
		InputStream inStream = null;
		try {
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPath);
			if (oldfile.exists()) {
				inStream = new FileInputStream(oldPath);
				fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1024];

				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread;
					//System.out.println(bytesum);
					fs.write(buffer, 0, byteread);
				}
				//inStream.close();
			}
		} catch (Exception e) {
			System.out.println("error  ");
			e.printStackTrace();
		 } finally {  
	        try {  
	            if(fs!=null){  
	                fs.close();  
	            }  
	            if(inStream!=null){  
	            	inStream.close();  
	            }  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
		 }  

	}

	public static boolean FileExist(String filePath) {
        // 判断文件or目录是否存在，如果不存在，则把目录都创建起来
        File file = new File(filePath);
        if (!file.exists()) {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
        } else {
            return true;
        }
        return false;

    }

    public static File lookupFileInClasspath(String paramString) {
        File localFile =new File(paramString);
        if (localFile.exists()){
            return localFile;
        }
        String str1 = System.getProperty("usr.dir");
        if (str1 == null) {
            str1 = "";
        }
        String str2 = System.getProperty("java.class.path");
        if (str2 == null)
            str2 = "";
        String str3 = System.getProperty("sun.boot.class.path");
        if (str3 == null) {
            str3 = "";
        }
        String str4 = "/" +File.pathSeparatorChar +"."+File.pathSeparatorChar +str3 + File.pathSeparatorChar + str2 + File.pathSeparatorChar + str1;

        StringTokenizer localStringTokenizer = new StringTokenizer(str4, File.pathSeparatorChar + "\"");
        while (localStringTokenizer.hasMoreTokens()) {
            String str5 = localStringTokenizer.nextToken();
            localFile = new File(str5, paramString);
            if (localFile.exists()) {
                return localFile;
            }
        }
        return null;
    }
	
    public static  int NewFile(String strxml, String path) {
        File file = new File(path);
        if (FileExist(path)) {
            return -1;
        }
        try {
            file.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {

            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(strxml);
            bw.flush();
            bw.close();
            fw.close();
            // file.close();
        } catch (Exception e) {
            e.printStackTrace();
        } 

        return 0;
    }
	/**
	 * @param args
	 */
	public static Boolean isSameFile(String fileName1, String fileName2) {

		Boolean isSame = false;
		FileInputStream fis1 = null;
		FileInputStream fis2 = null;
		try {
			fis1 = new FileInputStream(fileName1);
			fis2 = new FileInputStream(fileName2);
			int len1 = fis1.available();
			int len2 = fis2.available();
			if (len1 == len2) {// 长度相同，则比较具体内容//建立两个字节缓冲区
				byte[] data1 = new byte[len1];
				byte[] data2 = new byte[len2];
				// 分别将两个文件的内容读入缓冲区
				fis1.read(data1);
				fis2.read(data2);
				// 依次比较文件中的每一个字节
				for (int i = 0; i < len1; i++) {
					// 只要有一个字节不同，两个文件就不一样
					if (data1[i] != data2[i]) {
						/*
						 * System.out.println("文件内容不一样"); return;
						 */
						isSame = false;
						break;
					}
				}
				isSame = true;
			} else {// 长度不一样，文件肯定不同
				// System.out.println("两个文件长度不同");
				isSame = false;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != fis1) {
					fis1.close();
				}
				if (null != fis2) {
					fis2.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return isSame;

	}

	public static ArrayList<String> searchFolderFileNames(String folder) {
		ArrayList<String> results = new ArrayList<String>();
		ArrayList<File> files = searchFolder(folder);
		for (File file : files) {
			results.add(file.getAbsolutePath());
		}

		return results;
	}

	/***
	 * 查找folder下的所有文件
	 * 
	 * @param folder
	 *            文件夹下的所有文件
	 */
	public static ArrayList<File> searchFolder(String folder) {
		ArrayList<File> result = new ArrayList<File>();

		LinkedList<File> list = new LinkedList<File>();
		File dir = new File(folder);
		File file[] = dir.listFiles();

		for (int i = 0; i < file.length; i++) {
			if (file[i].isDirectory())
				list.add(file[i]);
			else {
				// System.out.println(file[i].getAbsolutePath());
			}
		}

		File tmp = null;
		while (!list.isEmpty()) {
			tmp = list.removeFirst();
			if (tmp.isDirectory()) {
				file = tmp.listFiles();
				if (file == null)
					continue;
				for (int i = 0; i < file.length; i++) {
					if (file[i].isDirectory())
						list.add(file[i]);
					else {
						// System.out.println(file[i].getAbsolutePath());
						result.add(file[i]);
					}

				}
			} else {
				// System.out.println(tmp.getAbsolutePath());
				result.add(tmp);
			}
		}

		return result;
	}

	/**
	 * 以行为单位读取文件，常用于读面向行的格式化文件
	 */
	public static List<String> readFileByLines(String fileName) {
		File file = new File(fileName);
		BufferedReader reader = null;
		List<String> result = new ArrayList<String>();
		try {
			// System.out.println("以行为单位读取文件内容，一次读一整行：");
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			// int line = 1;
			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null) {
				// 显示行号
				// System.out.println("line " + line + ": " + tempString);
				result.add(tempString);
				// line++;
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		return result;
	}

	public static void del(String filePath, String fileName) {

		@SuppressWarnings("unused")
        int lineDel = 1;
		RandomAccessFile readFile = null;
		try {
			readFile = new RandomAccessFile(filePath + "\\" + fileName, "r");
			readFile.readLine();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		RandomAccessFile writeFile = null;
		try {
			writeFile = new RandomAccessFile(filePath + "\\tempInitFile\\"
					+ fileName, "rw");
			String tempLine = null;
			while ((tempLine = readFile.readLine()) != null) {
				writeFile.writeBytes(tempLine);
				writeFile.writeBytes("\n");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			readFile.close();
			writeFile.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    public static void rmDirectory(File file){  
        if(file.isDirectory()){  
            for(String child:file.list()){  
                rmDirectory(new File(file,child)); //回调  
            }  
        }  
       if(file.isFile()){  
           file.delete();  
           System.out.println("成功删除<strong>文件</strong>:%s"+file.getName());  
       }  
       if(file.isDirectory()){  
           file.delete();  
           System.out.println("成功删除<strong>文件</strong>夹: "+file.getName());  
       }  
   }  
    /**
	 * 新建目录
	 * 
	 * @param strFolderPath
	 *            目录路径（含要创建的目录名称）
	 * 
	 * @return boolean
	 */
	public static boolean createFolder(String strFolderPath) {
		boolean blnResult = true;
		File file = null;
		if (strFolderPath != null && strFolderPath.trim().length() > 0) {
			try {
				file = new File(strFolderPath);
				if (!file.exists()) {
					blnResult = file.mkdirs();
				}
			} catch (Exception e) {
				blnResult = false;
			} finally {
				file = null;
			}
		}
		// 释放对象
		strFolderPath = null;
		return blnResult;
	}

	/**
	 * 删除文件
	 * 
	 * @param strFilePath
	 *            文件全路径（含文件名）
	 * 
	 * @return boolean
	 */
	public static boolean delFile(String strFilePath) {
		boolean blnResult = false;
		File file = null;
		if (strFilePath != null && strFilePath.trim().length() > 0) {
			try {
				file = new File(strFilePath);
				if (file.exists()) {
					file.delete();
					blnResult = true;
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				file = null;
			}
		}
		// 释放对象
		strFilePath = null;
		return blnResult;
	}

	/**
	 * 删除文件夹
	 * 
	 * 
	 * @param strFolderPath
	 *            文件夹完整绝对路径
	 * 
	 * @return void
	 */
	public static void delFolder(String strFolderPath) {
		File file = null;
		if (strFolderPath != null && strFolderPath.trim().length() > 0) {
			try {
				delAllFile(strFolderPath); // 删除完里面所有内容

				file = new File(strFolderPath);
				file.delete(); // 删除空文件夹
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				file = null;
			}
		}
		// 释放对象
		strFolderPath = null;
	}

	/**
	 * 删除指定文件夹下所有文件及目录
	 * 
	 * @param strFolderPath
	 *            文件夹完整绝对路径
	 * 
	 * @return boolean
	 */
	public static boolean delAllFile(String strFolderPath) {
		boolean blnResult = false;
		int intFileCount = 0;
		String[] strArrayFile = null;
		File file = new File(strFolderPath);
		if (file.exists() && file.isDirectory()) {
			strArrayFile = file.list();
			if (strArrayFile == null || strArrayFile.length <= 0) {
				blnResult = true;
			} else {
				intFileCount = strArrayFile.length;
				if (intFileCount > 0) {
					for (int i = 0; i < intFileCount; i++) {
						if (strFolderPath.endsWith(File.separator)) {
							file = new File(strFolderPath + strArrayFile[i]);
						} else {
							file = new File(strFolderPath + File.separator
									+ strArrayFile[i]);
						}
						if (file.isFile()) {
							file.delete();
						}
						if (file.isDirectory()) {
							delAllFile(strFolderPath + File.separator
									+ strArrayFile[i]);// 先删除文件夹里面的文件

							delFolder(strFolderPath + File.separator
									+ strArrayFile[i]);// 再删除空文件夹

						}
						blnResult = true;
					}
				} else {
					blnResult = true;
				}
			}
		}
		// 释放对象
		strFolderPath = null;
		strArrayFile = null;
		file = null;
		return blnResult;
	}
	/**
	 * @param args
	 */
	@SuppressWarnings("static-access")
    public static void main(String[] args) throws Exception {
		FileUtil fileUtil = new FileUtil();
		ArrayList<File> files = fileUtil
				.searchFolder("C:/ProjectCheckOut/GPFS/gpfs-web-deleted");
		System.out.println("files size===>" + files.size());

		List<String> results = fileUtil.readFileByLines("C:/list/change.txt");
		for (String temp : results) {
			System.out.println("temp===>" + temp);
		}
		System.out.println("results size===>" + results.size());
	}

}