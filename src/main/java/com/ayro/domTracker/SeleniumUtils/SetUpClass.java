package com.ayro.domTracker.SeleniumUtils;

import com.ayro.domTracker.FileLogicPackage.FileHandling;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class SetUpClass {
    private final Logger logger = LoggerFactory.getLogger(GetURLDom.class);
    String workingDir = System.getProperty("user.dir");
    String directoryForTest = workingDir + "\\src\\main\\resources\\data\\";
    String firstFileName="extracetdFirst.txt";
    String secondFileName="extractedSecond.txt";
    String resultFileName = "results.txt";

    private WebDriver driver;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();
    private LibraryUtils libraryUtils = new LibraryUtils();
    private FileHandling fileHandling;
    GetURLDom getURLDom;

    public SetUpClass(){

        new File(directoryForTest).mkdir();
        logger.info("Setting chrome webdriver... please wait");
        Map<String, String> mobileEmulation = new HashMap<>();
        mobileEmulation.put("deviceName", "iPhone 6");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);
        System.setProperty("webdriver.chrome.driver","./src/main/resources/drivers/chromedriver.exe");
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
        this.driver = new ChromeDriver(chromeOptions);
//        this.driver = new ChromeDriver();
        this.driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        logger.info("Setting " + driver + " done");

    }
    public FileHandling getFileHandling() {
        this.fileHandling = new FileHandling(directoryForTest);
        return fileHandling;
    }
    public WebDriver getDriver() {
        return driver;
    }


    public String getDirectoryForTest() {
        return this.directoryForTest;
    }

    public GetURLDom getGetURLDom() {
        getURLDom = new GetURLDom(driver);
        return getURLDom;
    }
    public String getResultFileName(){ return resultFileName;}

    public String getFirstFileName() {
        return firstFileName;
    }

    public String getSecondFileName() {
        return secondFileName;
    }
}
