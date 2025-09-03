package ru.ifmo.testing.report;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import java.util.HashMap;
import java.util.Map;

public class ExtentTestManager {
    private static final Map<Long, ExtentTest> extentTestMap = new HashMap<>();
    private static final ExtentReports extent = ExtentManager.getExtentReports();

    public static synchronized ExtentTest getTest() {
        return extentTestMap.get(Thread.currentThread().getId());
    }

    public static synchronized void startTest(String testName, String desc) {
        ExtentTest test = extent.createTest(testName, desc);
        extentTestMap.put(Thread.currentThread().getId(), test);
    }
}
