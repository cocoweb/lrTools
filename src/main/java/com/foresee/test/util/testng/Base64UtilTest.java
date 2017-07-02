package com.foresee.test.util.testng;

import java.io.UnsupportedEncodingException;

import org.testng.annotations.Test;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.foresee.test.util.code.Base64Util;

public class Base64UtilTest {
  String code1 = "PHRheE1MIHhtbG5zPSJodHRwOi8vd3d3LmNoaW5hdGF4Lmdvdi5jbi9ndDNuZiIgeG1sbnM6eHNpPSJodHRwOi8vd3d3LnczLm9yZy8yMDAxL1hNTFNjaGVtYS1pbnN0YW5jZSIJeHNpOnR5cGU9InNiS2pncnNkc0NzaFJlcSIgY25OYW1lPSIiIG5hbWU9InNiS2pncnNkc0NzaFJlcSIgdmVyc2lvbj0iU1c1MDAxLTIwMDYiPjxuc3JzYmg+NTExMzgxNzUyMzE2MzRYPC9uc3JzYmg+PHNzc3FRPjIwMTUtMTEtMDE8L3Nzc3FRPjxzc3NxWj4yMDE1LTExLTMwPC9zc3NxWj48YnNyeG0+PC9ic3J4bT48c2xyRG0+PC9zbHJEbT48c2xyeG0+PC9zbHJ4bT48c2xzd2pnRG0+PC9zbHN3amdEbT48c2xzd2pnbWM+PC9zbHN3amdtYz48c2xycT48L3NscnE+PHNibHhEbT48L3NibHhEbT48c2JibHhEbT48L3NiYmx4RG0+PHpzZnNEbT48L3pzZnNEbT48enNmc21jPjwvenNmc21jPjxuc3J6Z2x4RG0+PC9uc3J6Z2x4RG0+PG5zcnpnbHhtYz48L25zcnpnbHhtYz48enN4bURtPjwvenN4bURtPjx6c3BtRG0+PC96c3BtRG0+PHpzem1EbT48L3pzem1EbT48c2JDb21tRGF0YT48L3NiQ29tbURhdGE+PHFjc0RhdGE+PC9xY3NEYXRhPjxqbXh4Pjwvam14eD48eWp4eD48L3lqeHg+PHNqc2Niej4xPC9zanNjYno+PGF0dGFjaG1lbnQ+PGJzemw+PGJjd2ptPjA3YTJjYTFmLTMzOTUtNDA4Ni05NGExLTU2MDdiMTAxNjZkYi54bHM8L2Jjd2ptPjwvYnN6bD48L2F0dGFjaG1lbnQ+PHZlcnNpb24+VjQuMDwvdmVyc2lvbj48a3pjcz48IVtDREFUQVs8aXRlbT48enN4bURtPjEwMTA2PC96c3htRG0+PHpzeG1NYz7kuKrkurrmiYDlvpfnqI48L3pzeG1NYz48enNwbURtPjEwMTA2MDEwMDwvenNwbURtPjx6c3BtTWM+5bel6LWE6Jaq6YeR5omA5b6XPC96c3BtTWM+PGh5RG0+MTc0MzwvaHlEbT48aHlNYz7kuJ3ljbDmn5Pnsr7liqDlt6U8L2h5TWM+PHNsPjA8L3NsPjx5c2ttRG0+MTAxMDYwMTA5PC95c2ttRG0+PHlza21NYz7lhbbku5bkuKrkurrmiYDlvpfnqI48L3lza21NYz48eXNmcGJsRG0+MjUxMTAwMTE8L3lzZnBibERtPjx5c2ZwYmxNYz48L3lzZnBibE1jPjxna0RtPjI1MTEzODEwMDE8L2drRG0+PGdrTWM+5Zu95a626YeR5bqT6ZiG5Lit5biC5pSv5bqTPC9na01jPjwvaXRlbT5dXT48L2t6Y3M+PGRqeGg+MTAxMjUxMTMwMDQwMDAxMzI0ODg8L2RqeGg+PC90YXhNTD4=";
  String code2 = "<taxML xmlns=\"http://www.chinatax.gov.cn/gt3nf\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"sbKjgrsdsCshReq\" cnName=\"\" name=\"sbKjgrsdsCshReq\" version=\"SW5001-2006\"><nsrsbh>51138175231634X</nsrsbh><sssqQ>2015-11-01</sssqQ><sssqZ>2015-11-30</sssqZ><bsrxm></bsrxm><slrDm></slrDm><slrxm></slrxm><slswjgDm></slswjgDm><slswjgmc></slswjgmc><slrq></slrq><sblxDm></sblxDm><sbblxDm></sbblxDm><zsfsDm></zsfsDm><zsfsmc></zsfsmc><nsrzglxDm></nsrzglxDm><nsrzglxmc></nsrzglxmc><zsxmDm></zsxmDm><zspmDm></zspmDm><zszmDm></zszmDm><sbCommData></sbCommData><qcsData></qcsData><jmxx></jmxx><yjxx></yjxx><sjscbz>1</sjscbz><attachment><bszl><bcwjm>07a2ca1f-3395-4086-94a1-5607b10166db.xls</bcwjm></bszl></attachment><version>V4.0</version><kzcs><![CDATA[<item><zsxmDm>10106</zsxmDm><zsxmMc>个人所得税</zsxmMc><zspmDm>101060100</zspmDm><zspmMc>工资薪金所得</zspmMc><hyDm>1743</hyDm><hyMc>丝印染精加工</hyMc><sl>0</sl><yskmDm>101060109</yskmDm><yskmMc>其他个人所得税</yskmMc><ysfpblDm>25110011</ysfpblDm><ysfpblMc></ysfpblMc><gkDm>2511381001</gkDm><gkMc>国家金库阆中市支库</gkMc></item>]]></kzcs><djxh>10125113004000132488</djxh></taxML>";
  @Test
  public void decode() {
    //System.out.println(Base64Util.decode(code1));
      System.out.println(getFromBase64(code1));
  }

  @Test
  public void encode() {
      //System.out.println(Base64Util.encode(code2));
  }
  
  
  // 加密
  public static String getBase64(String str) {
      byte[] b = null;
      String s = null;
      try {
          b = str.getBytes("utf-8");
      } catch (UnsupportedEncodingException e) {
          e.printStackTrace();
      }
      if (b != null) {
          s = new BASE64Encoder().encode(b);
      }
      return s;
  }

  // 解密
  public static String getFromBase64(String s) {
      byte[] b = null;
      String result = null;
      if (s != null) {
          BASE64Decoder decoder = new BASE64Decoder();
          try {
              b = decoder.decodeBuffer(s);
              result = new String(b, "utf-8");
          } catch (Exception e) {
              e.printStackTrace();
          }
      }
      return result;
  }

}
