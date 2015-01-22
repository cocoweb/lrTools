package com.foresee.test.util.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;



/**
 * <pre>
 * 文件拷贝工具类
 * </pre>
 * 
 * @author LiuJiuxing liujiuxing@foresee.cn
 * @version 1.00.00
 * 
 *          <pre>
 * 2013-1-10下午04:59:03
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class FileCopyUtil {

	//private static Log log = LogFactory.getLog(FileCopyUtil.class);

	/**
	 * 拷贝文件
	 * 
	 * @param src
	 *            源文件(包含路径)
	 * @param dest
	 *            目标文件(包含路径)
	 * @throws java.io.IOException
	 */
	public static void copyFileToPath(String src, String dest) throws java.io.IOException {
		File fromFile = new File(src);
		if (fromFile.exists() && fromFile.isFile()) {
			// 创建文件
			File toFile = new File(dest);
			if (toFile.exists()) {
				toFile.deleteOnExit();
			}

			BufferedInputStream input = null;
			BufferedOutputStream output = null;
			try {
				input = new BufferedInputStream(new FileInputStream(fromFile));
				output = new BufferedOutputStream(new FileOutputStream(toFile));
				byte[] buffer = new byte[4096];
				int length = 0;
				while ((length = input.read(buffer)) != -1) {
					output.write(buffer, 0, length);
				}
				output.flush();
			} catch (IOException ex) {
				throw new IOException("复制文件错误！");
			} finally {
				if (output != null) {
					try {
						output.close();
					} catch (IOException ex) {
						// ignore
					}
				}
				if (input != null) {
					try {
						input.close();
					} catch (IOException ex) {
						// ignore
					}
				}
			}
		}
	}

	/**
	 * 拷贝文件
	 * 
	 * @param srcRoot
	 *            源根路径
	 * @param src
	 *            源文件路径及文件名
	 * @param destRoot
	 *            目标根路径
	 * @param dest
	 *            目标文件及文件名
	 * @throws java.io.IOException
	 */
	public static void copyFileToPath(String srcRoot, String src, String destRoot, String dest)
			throws java.io.IOException {
		copyFileToPath(srcRoot + "/" + src, destRoot + "/" + dest);
	}

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
	    System.out.println("读取<strong>文件</strong>总数: "+ count);  
	}

	/**
	 * 复制单个文件
	 * 
	 * @param strOldFilePath
	 *            准备复制的文件源
	 * @param strNewFilePath
	 *            拷贝到新绝对路径带文件名
	 * @return boolean
	 */
	public static boolean copyFile(String strOldFilePath, String strNewFilePath) {
		boolean blnResult = false;
		FileInputStream fileInputStream = null;
		FileOutputStream fileOutputStream = null;
		File file = null;
		byte[] byteArray = null;
		int intIndex = 0;
		try {
			fileInputStream = new FileInputStream(strOldFilePath);
			file = new File(strNewFilePath);
			if (!file.exists()) {
				file.createNewFile();
			}
			fileOutputStream = new FileOutputStream(file);
			byteArray = new byte[1024];
			while ((intIndex = fileInputStream.read(byteArray)) != -1) {
				for (int i = 0; i < intIndex; i++)
					fileOutputStream.write(byteArray[i]);
			}
			intIndex = 0;
			fileInputStream.close();
			fileOutputStream.close();
			blnResult = true;
		} catch (Exception e) {
			File2Util.logger.info("类:FileUtil,方法:copyFile,信息:被拷贝文件不存在!" + e);
		} finally {
			fileInputStream = null;
			fileOutputStream = null;
			file = null;
			byteArray = null;
		}
		// 释放对象
		strNewFilePath = null;
		strOldFilePath = null;
		return blnResult;
	}

	/**
	 * 复制单个文件
	 * 
	 * @param strOldFilePath
	 *            准备复制的文件源
	 * @param strNewFilePath
	 *            拷贝到新绝对路径带文件名
	 * @return boolean
	 */
	public static boolean copyFile2(String f, String d) {
		File file = new File(f);
		File dir = new File(d);
		boolean flag = true;
		if(!dir.exists())
			flag = dir.mkdirs();
		if(flag){
			File newfile = new File(d+file.getName());
			if(!newfile.exists())
				file.renameTo(newfile);
			else{
				if(newfile.delete())
					file.renameTo(newfile);
				else
					flag = false;
			}
		}
		return flag;
	}

	/**
	 * 复制整个文件夹的内容
	 * 
	 * @param strOldFolderPath
	 *            准备拷贝的目录
	 * 
	 * @param strNewFolderPath
	 *            指定绝对路径的新目录
	 * @return void
	 */
	public static void copyFolder(String strOldFolderPath,
			String strNewFolderPath) {
		FileInputStream fileInputStream = null;
		FileOutputStream fileOutputStream = null;
		File file = null;
		String[] strArrayFile = null;
		File fileTemp = null;
		byte[] byteArray = null;
		int intIndex = 0;
		try {
			new File(strNewFolderPath).mkdirs(); // 如果文件夹不存在 则建立新文件夹
	
			file = new File(strOldFolderPath);
			strArrayFile = file.list();
			for (int i = 0; i < strArrayFile.length; i++) {
				if (strOldFolderPath.endsWith(File.separator)) {
					fileTemp = new File(strOldFolderPath + strArrayFile[i]);
				} else {
					fileTemp = new File(strOldFolderPath + File.separator
							+ strArrayFile[i]);
				}
				if (fileTemp.isFile() && (!fileTemp.isHidden())) {
					fileInputStream = new FileInputStream(fileTemp);
					fileOutputStream = new FileOutputStream(strNewFolderPath
							+ "/" + (fileTemp.getName()).toString());
					byteArray = new byte[1024 * 5];
					while ((intIndex = fileInputStream.read(byteArray)) != -1) {
						fileOutputStream.write(byteArray, 0, intIndex);
					}
					fileOutputStream.flush();
					fileOutputStream.close();
					fileInputStream.close();
					intIndex = 0;
				}
				if (fileTemp.isDirectory() && (!fileTemp.isHidden())) {// 如果是子文件夹
	
					copyFolder(strOldFolderPath + File.separator
							+ strArrayFile[i], strNewFolderPath
							+ File.separator + strArrayFile[i]);
				}
			}
		} catch (Exception e) {
			File2Util.logger.info("类:FileUtil,方法:copyFolder,信息:复制整个文件夹内容操作出错," + e);
		} finally {
			fileInputStream = null;
			fileOutputStream = null;
			file = null;
			fileTemp = null;
			byteArray = null;
		}
		// 释放对象
		strArrayFile = null;
		strNewFolderPath = null;
		strOldFolderPath = null;
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
			@SuppressWarnings("unused")
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
		FileOutputStream fs = null;
		InputStream inStream = null;
		try {
			int bytesum = 0;
			int byteread = 0;
			// File oldfile = new File(oldPath);
			if (oldfile.exists()) {
				 inStream = new FileInputStream(oldfile);
				 fs = new FileOutputStream(newPath);
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
	
    private static int count;


}
