package testutility;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class ExtentReportCreater {
    public static ExtentTest getLogger(String testName) {
        String fileName = testName.toLowerCase().replace(' ','_');
        ExtentHtmlReporter reporter = new ExtentHtmlReporter(Constant.REPORT_FILE_PATH +fileName+".html");
        ExtentReports extent = new ExtentReports();
        extent.attachReporter(reporter);
       return  extent.createTest(testName);
    }
}
