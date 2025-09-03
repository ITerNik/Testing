package ru.ifmo.testing.report;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Protocol;
import com.aventstack.extentreports.reporter.configuration.Theme;
import ru.ifmo.testing.utils.PropertyLoader;

public class ExtentManager {
    private static ExtentReports extent;

    public static ExtentReports createExtentReports() {
        if (extent == null) {
            extent = new ExtentReports();
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(PropertyLoader.returnConfigValue("extent.report.path"));
            extent.setSystemInfo("Author", "ITerNik");
            sparkReporter.config().setDocumentTitle("Testing Report");
            sparkReporter.config().setReportName("FL.ru report");
            sparkReporter.config().setProtocol(Protocol.HTTP);
            sparkReporter.config().setTheme(Theme.DARK);
            sparkReporter.config().setTimeStampFormat("dd/MM/yyyy HH:mm:ss");
            sparkReporter.config().setEncoding("UTF-8");

            extent.attachReporter(sparkReporter);
        }
        return extent;
    }

    public static ExtentReports getExtentReports() {
        if (extent == null) {
            return createExtentReports();
        }
        return extent;
    }

    public static void flushReports() {
        if (extent != null) {
            extent.flush();
        }
    }
}
