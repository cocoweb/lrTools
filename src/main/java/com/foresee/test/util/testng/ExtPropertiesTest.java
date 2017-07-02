package com.foresee.test.util.testng;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.foresee.test.fileprops.FileDefinition;
import com.foresee.test.util.exfile.ExtProperties;

public class ExtPropertiesTest {

	static ExtProperties prop;

	@BeforeMethod
	@BeforeClass
	public static void setUp() throws Exception {
		initProperties("/file.properties");
	}

	private static void initProperties(String path) {
		if (prop == null) {
			try {
				// 从用来加载类的搜索路径打开具有指定名称的资源，以读取该资源。此方法通过系统类加载器
				// InputStream in
				// =Thread.defaultThread().getContextClassLoader().getResourceAsStream(path);

				InputStream in = FileDefinition.class.getResourceAsStream(path);
				if (in == null) {
					System.out.println("未找到" + path + "文件！");
				}
				// 转换下，避免中文乱码
				System.out.println("==Load " + path + "文件！");

				prop = new ExtProperties();
				prop.load(new InputStreamReader(in, "UTF-8"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	@Test
	public final void testGetSectionItems() {

		System.out.println(prop.getSectionItems("SWZJ.HXZG.SB.BCZZSXGMSB")
				.toString());
	}

	@Test
	public final void testGetSectionItem() {

		System.out.println(prop.getSectionItem("default", "sbrq"));
	}

	@Test
	public final void testGetSectionDefault() {

		System.out.println(prop.getSectionDefault("SWZJ.HXZG.SB.BCZZSXGMSB"));
	}

	@Test
	public final void testContainSection() {

		System.out.println(prop.containSection("SWZJ.HXZG.SB.BCZZSXGMSBxx"));
	}

}
