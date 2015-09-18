package com.foresee.test.util.http.download;

//import com.hoo.util.DownloadUtils;
/**
 * <b>function:</b> 下载测试
 * 
 * @author hoojo
 * @createDate 2011-9-23 下午05:49:46
 * @file TestDownloadMain.java
 * @package com.hoo.download
 * @project MultiThreadDownLoad
 * @blog http://blog.csdn.net/IBM_hoojo
 * @email hoojo_@126.com
 * @version 1.0
 */
public class TestDownloadMain {
    public static void main(String[] args) {
        /*
         * DownloadInfo bean = new DownloadInfo(
         * "http://i7.meishichina.com/Health/UploadFiles/201109/2011092116224363.jpg"
         * ); System.out.println(bean); BatchDownloadFile down = new
         * BatchDownloadFile(bean); new Thread(down).start();
         */
        // DownloadUtils.download("http://i7.meishichina.com/Health/UploadFiles/201109/2011092116224363.jpg");
        // DownloadUtils.download("http://mp3.baidu.com/j?j=2&url=http%3A%2F%2Fzhangmenshiting2.baidu.com%2Fdata%2Fmusic%2F1669425%2F%25E9%2599%25B7%25E5%2585%25A5%25E7%2588%25B1%25E9%2587%258C%25E9%259D%25A2.mp3%3Fxcode%3D2ff36fb70737c816553396c56deab3f1",
        // "aa.mp3", "c:/temp", 5);
        DownloadUtils
                .download(
                        "http://183.62.253.122:10217/release/%e6%83%a0%e5%b7%9e%e7%94%b5%e5%ad%90%e5%b7%a5%e4%bd%9c%e5%b9%b3%e5%8f%b0/2015/201505/20150512/%e9%87%91%e4%b8%89%e7%94%b5%e5%ad%90%e5%b7%a5%e4%bd%9c%e5%b9%b3%e5%8f%b0(%e7%94%a8%e6%88%b7%e7%94%9f%e4%ba%a7%e7%8e%af%e5%a2%8320150512_160635).exe",
                        "aa.exe", "d:/", 5);
    }
}