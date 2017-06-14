package yahoo;

import java.io.FileInputStream;
import java.lang.reflect.Method;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;

@Listeners({ATUReportsListener.class,MethodListener.class,ConfigurationListener.class})
public class SanityTest extends Driverclass
{
	
	{
		System.setProperty("atu.reporter.config","d:/atu.properties");
	}
    @Test
	@Parameters({"browser"})
	public void sanitytesting(String str) throws Exception
	{
		if(str.matches("firefox"))
		{
			driver=new FirefoxDriver();
		}
		if(str.matches("chrome"))
		{
			System.setProperty("webdriver.chrome.driver","c:\\chromedriver.exe");
			driver=new ChromeDriver();
		}
		FileInputStream fin=new FileInputStream("d:\\7pm_may_11\\testdata.xlsx"); 
		XSSFWorkbook wb=new XSSFWorkbook(fin); 
		XSSFSheet ws=wb.getSheet("sanitytest"); 
		
		Row row;
		for(int r=1;r<=ws.getLastRowNum();r++) 
		{
		    row=ws.getRow(r);
		    
			if(row.getCell(4).getStringCellValue().matches("yes"))
			{
				Class c=Class.forName(row.getCell(2).getStringCellValue()); //load the class into memory
				Method m=c.getMethod(row.getCell(3).getStringCellValue(),null); //get method in the class
				Object o=c.newInstance(); //create obj for the class
				m.invoke(o,null);  //invoke or call the method 
			}
		}
		fin.close();
	}
}












