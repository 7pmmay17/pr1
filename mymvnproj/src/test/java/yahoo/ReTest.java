package yahoo;


import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.net.URL;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.Platform;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;


@Listeners({ATUReportsListener.class,MethodListener.class,ConfigurationListener.class})
public class ReTest extends Driverclass
{
     DesiredCapabilities ds;
     
	{
		System.setProperty("atu.reporter.config","d:/atu.properties");
	}
    @Test
	@Parameters({"browser"})
	public void retesting(String str) throws Exception
	{
		if(str.matches("firefox"))
		{
			//ds=DesiredCapabilities.firefox();
			//ds.setPlatform(Platform.WINDOWS);
			
			driver=new FirefoxDriver();
		}
		if(str.matches("chrome"))
		{
			//ds=DesiredCapabilities.chrome();
			//ds.setPlatform(Platform.WINDOWS);
			
			System.setProperty("webdriver.chrome.driver","D:\\7PM_may_11\\chromedriver.exe");
			driver=new ChromeDriver();
		}
									//hubaddress, desiredcapobj
		//driver=new RemoteWebDriver(new URL("http://192.168.1.169:1234/wd/hub"), ds);
		FileInputStream fin=new FileInputStream("d:\\7pm_may_11\\testdata.xlsx"); 
		XSSFWorkbook wb=new XSSFWorkbook(fin); 
		XSSFSheet ws=wb.getSheet("retest"); 
		
		Row row;
		for(int r=1;r<=ws.getLastRowNum();r++) 
		{
		    row=ws.getRow(r);
		    try
		    {
			if(row.getCell(4).getStringCellValue().matches("yes"))
			{
				Class c=Class.forName(row.getCell(2).getStringCellValue()); //load the class into memory
				Method m=c.getMethod(row.getCell(3).getStringCellValue(),null); //get method in the class
				Object o=c.newInstance(); //create obj for the class
				m.invoke(o,null);  //invoke or call the method 
			}
		    }
		    catch(Exception e){}
		}
		fin.close();
		
		/*Home h=new Home(driver);
		h.validate_links();
		h.createacc();
		h.login();
		
		Inbox i=new Inbox(driver);
		i.deletemail();
		
		Compose c=new Compose(driver);
		c.signout();*/
	}
}













