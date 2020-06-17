package fundooapptest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterTest;
import testutility.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;

public class FundooAppTest {
    ExcelFileRead excelFile;
    ExtentReports extent;
    ExtentTest logger;
    WebDriver driver;
    @BeforeTest
    public void setup(){
        driver = WebDriverConfiguration.getDriver();
        driver.manage().window().maximize();
        extent = new ExtentReports();
        excelFile = new ExcelFileRead(Constant.EXCEL_FILE_PATH);
    }

  @Test(dataProvider = Constant.LOGIN_DATA_PROVIDER)
  public void loginToFundooApp(String userName,String password) throws InterruptedException, IOException {
      ExtentHtmlReporter reporter = new ExtentHtmlReporter(Constant.REPORT_FILE_PATH +"login_test.html");
      extent.attachReporter(reporter);
      logger =  extent.createTest("Login Test");
      logger.log(Status.INFO,"Login to FundooApp UserName: "+userName+" Password: "+password);
      Thread.sleep(3000);
      driver.navigate().to(Constant.LOGIN_URL);
      Thread.sleep(5000);
      driver.findElement(By.xpath("//*[@id=\"mat-input-0\"]")).sendKeys(userName);
      driver.findElement(By.xpath("//*[@id=\"mat-input-1\"]")).sendKeys(password);
      driver.findElement(By.xpath("//*[@id=\"sigi-button\"]")).click();
      Thread.sleep(12000);

      if(driver.getCurrentUrl().contains("dashboard")){
          logger.log(Status.PASS,"Login Success");
      }
      else {
      String path = ScreenShotCapturer.getScreenshot(driver);
      logger.fail("login failed reason", MediaEntityBuilder.createScreenCaptureFromPath(path).build());
      }
  }

    @Test(dataProvider = Constant.REGISTRATION_DATA_PROVIDER)
    public void registerToFundooApp(String firstName,String lastName,String mobileNumber,String
            email, String password,String confirmPassword) throws InterruptedException, IOException {
        ExtentHtmlReporter reporter = new ExtentHtmlReporter(Constant.REPORT_FILE_PATH +"registration_test.html");
        extent.attachReporter(reporter);
        logger =  extent.createTest("Registration Test");
        logger.log(Status.INFO,"Register to FundooApp firstName: "+firstName+" lastName: "+lastName+
                " mobileNumber: "+mobileNumber+" email: "+email+" Password: "+password+" confirmPassword: "+confirmPassword);
        Thread.sleep(5000);
        driver.navigate().to(Constant.REGISTER_URL);
        Thread.sleep(5000);
        driver.findElement(By.xpath("//*[@id=\"mat-input-0\"]")).sendKeys(firstName);
        driver.findElement(By.xpath("//*[@id=\"mat-input-1\"]")).sendKeys(lastName);
        driver.findElement(By.xpath("//*[@id=\"mat-input-2\"]")).sendKeys(mobileNumber);
        driver.findElement(By.xpath("//*[@id=\"mat-input-3\"]")).sendKeys(email);
        driver.findElement(By.xpath("//*[@id=\"mat-input-4\"]")).sendKeys(password);
        driver.findElement(By.xpath("//*[@id=\"mat-input-5\"]")).sendKeys(confirmPassword);
        driver.findElement(By.xpath("//*[@id=\"signbutton\"]/span")).click();
        Thread.sleep(15000);
        if(driver.getCurrentUrl().contains("login")){
            logger.log(Status.PASS,"Registration Success");
        }
        else { String path = ScreenShotCapturer.getScreenshot(driver);
            logger.fail("Registration failed reason", MediaEntityBuilder.createScreenCaptureFromPath(path).build());
        }

    }
    @Test(dataProvider = Constant.LOGIN_DATA_PROVIDER)
    public void logoutFromFundooApp(String userName,String password) throws InterruptedException {
        ExtentHtmlReporter reporter = new ExtentHtmlReporter(Constant.REPORT_FILE_PATH +"logout_test.html");
        extent.attachReporter(reporter);
        logger =  extent.createTest("Logout Test");
        logger.log(Status.INFO,"Logout from FundooApp UserName: "+userName+" Password: "+password);
        Thread.sleep(3000);
        driver.navigate().to(Constant.LOGIN_URL);
        Thread.sleep(5000);
        driver.findElement(By.xpath("//*[@id=\"mat-input-0\"]")).sendKeys(userName);
        driver.findElement(By.xpath("//*[@id=\"mat-input-1\"]")).sendKeys(password);
        driver.findElement(By.xpath("//*[@id=\"sigi-button\"]")).click();
        Thread.sleep(12000);
        if(driver.getCurrentUrl().contains("dashboard")){
        driver.findElement(By.xpath("/html/body/app-root/app-dashboard/div/mat-toolbar/button[2]")).click();
        driver.findElement(By.xpath("//*[@id=\"cdk-overlay-1\"]/div/div/button/span")).click();
        Thread.sleep(2000);
        if(driver.getCurrentUrl().contains("login") ){
            logger.log(Status.PASS,"Logout Success");
        }else {
            logger.log(Status.FAIL,"Logout Failed");
        }
        } else {
            logger.log(Status.FAIL,"Login Failed");
        }
    }
    @Test(dataProvider = Constant.FORGOT_PASSWORD_DATA_PROVIDER)
    public void forgotPasswordTest(String email) throws InterruptedException, IOException {
        ExtentHtmlReporter reporter = new ExtentHtmlReporter(Constant.REPORT_FILE_PATH +"forgotPassword_test.html");
        extent.attachReporter(reporter);
        logger =  extent.createTest("ForgotPassword Test");
        logger.log(Status.INFO,"ForgotPassword Email: "+email);
        driver.navigate().to(Constant.FORGOT_PASSWORD_URL);
        Thread.sleep(5000);
        driver.findElement(By.xpath("//*[@id=\"mat-input-0\"]")).sendKeys(email);
        driver.findElement(By.xpath("//*[@id=\"buttons\"]/span")).click();
        Thread.sleep(8000);
        String path = ScreenShotCapturer.getScreenshot(driver);
        logger.pass("ForgotPassword Test Success", MediaEntityBuilder.createScreenCaptureFromPath(path).build());
    }
    @DataProvider(name = Constant.LOGIN_DATA_PROVIDER)
    public Object[][] forwardLoginData(){
        return forwardData(0);
    }
    @DataProvider(name = Constant.REGISTRATION_DATA_PROVIDER)
    public Object[][] forwardRegistrationData(){
        return forwardData(1);
    }
    @DataProvider(name = Constant.FORGOT_PASSWORD_DATA_PROVIDER)
    public Object[][] forwardForgotPasswordData(){
        return forwardData(2);
    }
    public Object[][] forwardData(int sheetNumber ){
        int rowNumber = excelFile.getRowCount(sheetNumber);
        int colNumber = excelFile.getColumnCount(sheetNumber);
        Object[][] data = new Object[rowNumber][colNumber];
        System.out.println(colNumber);
        for(int i=0;i<rowNumber;i++){
            for(int j=0;j<colNumber;j++) {
                data[i][j] = excelFile.getData(sheetNumber, i, j);
            }
        }
        return data;
    }
    @AfterTest
    public void tearDown()
    {
        extent.flush();
        driver.quit();
    }
}
