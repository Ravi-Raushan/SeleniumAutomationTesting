package testutility;

import org.apache.poi.openxml4j.opc.internal.FileHelper;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;

public class ScreenShotCapturer {
    public static String getScreenshot(WebDriver driver)
    {
        TakesScreenshot ts=(TakesScreenshot) driver;

        File src=ts.getScreenshotAs(OutputType.FILE);

        String path=Constant.SCREENSHOT_FILE_PATH+System.currentTimeMillis()+"fundooapp.png";

        File destination=new File(path);

        try
        {
            FileHelper.copyFile(src, destination);
        } catch (IOException e)
        {
            System.out.println("Capture Failed "+e.getMessage());
        }

        return path;
    }


}
