package com.foresee.test.demo.testng.excel;


import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;

import org.openqa.selenium.WebDriver;

import org.openqa.selenium.firefox.FirefoxDriver;

import org.testng.annotations.Test;

import org.testng.annotations.BeforeMethod;

import org.testng.annotations.AfterMethod;

import org.testng.annotations.DataProvider;

import utility.ExcelUtils;

public class DataProviderWithExcel_002 {

	private String sTestCaseName;

	private int iTestCaseRow;

	WebDriver driver;

  @BeforeMethod

  public void beforeMethod() throws Exception {

	  driver = new FirefoxDriver();

      driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

      driver.get("http://www.store.demoqa.com");	 

  }	

  @Test(dataProvider = "Authentication")

  public void f(String sUserName, String sPassword) {

	    driver.findElement(By.xpath(".//*[@id='account']/a")).click();

	    driver.findElement(By.id("log")).sendKeys(sUserName);

		System.out.println(sUserName);

	    driver.findElement(By.id("pwd")).sendKeys(sPassword);

		System.out.println(sPassword);

	    driver.findElement(By.id("login")).click();

	    System.out.println(" Login Successfully, now it is the time to Log Off buddy.");

	    driver.findElement(By.xpath(".//*[@id='account_logout']/a")).click();

  }

  @AfterMethod

  public void afterMethod() {

	   driver.close();

  }

  @DataProvider

  public Object[][] Authentication() throws Exception{

	    // Setting up the Test Data Excel file

	 	ExcelUtils.setExcelFile("D://ToolsQA//OnlineStore//src//testData//TestData.xlsx","Sheet1");

	 	sTestCaseName = this.toString();

	  	// From above method we get long test case name including package and class name etc.

	  	// The below method will refine your test case name, exactly the name use have used

	  	sTestCaseName = ExcelUtils.getTestCaseName(this.toString());

	    // Fetching the Test Case row number from the Test Data Sheet

	    // Getting the Test Case name to get the TestCase row from the Test Data Excel sheet

	 	iTestCaseRow = ExcelUtils.getRowContains(sTestCaseName,0);

	    Object[][] testObjArray = ExcelUtils.getTableArray("D://ToolsQA//OnlineStore//src//testData//TestData.xlsx","Sheet1",iTestCaseRow);

	    	return (testObjArray);

		}

}