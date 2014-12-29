package com.foresee.test.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
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

/***
 * 
 * @author Jacky
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

	public static boolean createFolder(String folder) {
		File dir = new File(folder);
		return dir.mkdirs();
	}

	public static boolean Move(File srcFile, String destPath) {
		// Destination directory
		File dir = new File(destPath);

		// Move file to new directory
		boolean success = srcFile.renameTo(new File(dir, srcFile.getName()));

		return success;
	}

	public static boolean Move(String srcFile, String destPath) {
		// File (or directory) to be moved
		File file = new File(srcFile);

		// Destination directory
		File dir = new File(destPath);

		// Move file to new directory
		boolean success = file.renameTo(new File(dir, file.getName()));

		return success;
	}

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

	public static void Copy(File oldfile, String newPath) {
		try {
			int bytesum = 0;
			int byteread = 0;
			// File oldfile = new File(oldPath);
			if (oldfile.exists()) {
				InputStream inStream = new FileInputStream(oldfile);
				FileOutputStream fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1024];
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread;
					System.out.println(bytesum);
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
			}
		} catch (Exception e) {
			System.out.println("error  ");
			e.printStackTrace();
		}
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
    private static int count = 0 ;
    		
    public static void copyToDirectory(File toFile, File fromFile) {  
        if (fromFile.isDirectory()) {  
            System.out.println("toFile路径: "+toFile.getAbsolutePath());  
            if (!toFile.exists()) {  
                toFile.mkdir();  
            }  
            for (String child : fromFile.list()) {  
                copyToDirectory(new File(toFile, child), new File(fromFile, child));//如果<strong>文件</strong>夹有多层会递归调用  
            }  
        }  
   
        int BYTE_SIZE = 1;  
        int SAVE_SIZE = 1024;  
        byte[] buff = new byte[BYTE_SIZE]; // 每次读的缓存  
        byte[] save = new byte[SAVE_SIZE]; // 保存前缓存  
        BufferedInputStream bf = null;  
        BufferedOutputStream bos = null;  
        try {  
            if(toFile.isDirectory()){  
                return;  
            }  
            System.out.println("toFile名称: "+toFile.getName());  
            bf = new BufferedInputStream(new FileInputStream(fromFile));  
            System.out.println("fromFile: "+fromFile.getName());  
            System.out.println("已经获取资源......");  
            bos = new BufferedOutputStream(new FileOutputStream(toFile));  
            System.out.println("准备保存到：" + toFile.getPath());  
            System.out.println("开始读入......");  
            int i = 0;  
            while (bf.read(buff) != -1) { // 一个字节一个字节读  
                save[i] = buff[0];  
                if (i == SAVE_SIZE - 1) { // 达到保存长度时开始保存  
                    bos.write(save, 0, SAVE_SIZE);  
                    save = new byte[SAVE_SIZE];  
                    i = 0;  
                } else {  
                    i++;  
                }  
            }  
            // 最后这段如果没达到保存长度，需要把前面的保存下来  
            if (i > 0) {  
                bos.write(save, 0, i - 1);  
            }  
            System.out.println("读取成功！！！");  
            count ++;  
   
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                if(bf!=null){  
                    bf.close();  
                }  
                if(bos!=null){  
                    bos.close();  
                }  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }  
        System.out.println("读取<strong>文件</strong>总数: "+count);  
    }  
	/**
	 * @param args
	 */
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