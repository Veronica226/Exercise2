package test;

import java.util.regex.Pattern;
import java.io.File;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.Select;
import com.csvreader.CsvReader;

//第二次上机完成版改版
public class testDriver{
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
	File pathBinary = new File("D:\\Firefox\\firefox.exe");
	FirefoxBinary Binary = new FirefoxBinary(pathBinary);
	FirefoxProfile firefoxPro = new FirefoxProfile();
	System.setProperty("javax.xml.parsers.DocumentBuilderFactory",
				"com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl"); 
	driver = new FirefoxDriver(Binary,firefoxPro);
    baseUrl = "http://www.ncfxy.com/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test  
  public void test4() throws Exception {
	CsvReader c = new CsvReader("D://mooctest//eclipse//workspace//test//info2.csv", 
				',' ,Charset.forName("GBK"));
	while (c.readRecord()) {
		if(c.get(0).length()-6 > 0 ){
		driver.get(baseUrl);
	    driver.findElement(By.id("name")).clear();
	    driver.findElement(By.id("name")).sendKeys(c.get(0));
	    driver.findElement(By.id("pwd")).clear();
	    driver.findElement(By.id("pwd")).sendKeys(c.get(0).substring(c.get(0).length()-6,c.get(0).length()));
	    new Select(driver.findElement(By.id("gender"))).selectByVisibleText("女");
	    driver.findElement(By.id("submit")).click();
	    String number = driver.findElement(By.xpath("//tbody[@id='table-main']/tr[2]/td[2]")).getText();
		String mail = driver.findElement(By.xpath("//tbody[@id='table-main']/tr[1]/td[2]")).getText();
        if(c.get(0).equals(number)){
      	  assertEquals(c.get(1),mail);
        }
	}
	}
	
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}
