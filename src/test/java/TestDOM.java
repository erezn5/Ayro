import com.ayro.domTracker.FileLogicPackage.FileHandling;
import com.ayro.domTracker.SeleniumUtils.GetURLDom;
import com.ayro.domTracker.SeleniumUtils.LibraryUtils;
import com.ayro.domTracker.SeleniumUtils.SetUpClass;
import org.openqa.selenium.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.io.File;
import java.security.NoSuchAlgorithmException;

import static org.testng.Assert.fail;

public class TestDOM {

    String url = "https://www.google.com/";
    String workingDir = System.getProperty("user.dir");
    String directoryForTest = workingDir + "\\src\\main\\resources\\data";
    String synthecticDirectory = workingDir + "\\src\\main\\resources\\syntheticFiles";
//    String directoryForTest;
    String firstFileName;
    String secondFileName;
    private WebDriver driver;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();
    private LibraryUtils libraryUtils = new LibraryUtils();
    GetURLDom getURLDom;
    private FileHandling fileHandling;
    private SetUpClass setUpClass;

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        setUpClass = new SetUpClass();
        firstFileName = setUpClass.getFirstFileName();
        this.secondFileName = setUpClass.getSecondFileName();
        driver = setUpClass.getDriver();
        directoryForTest = setUpClass.getDirectoryForTest();
        this.fileHandling = setUpClass.getFileHandling();
        this.getURLDom = setUpClass.getGetURLDom();
        new File(directoryForTest).mkdir();

    }

    @Test
    public void gettingUrlDom() throws NoSuchAlgorithmException {
        String str = getURLDom.extractingUrlDom(url);
        fileHandling.saveToFile(str, firstFileName);
        assert(fileHandling.calcFileHash(directoryForTest + firstFileName).length()>0);
    }
    //For testing that both of the files are different
    @Test
    public void testDifferentFiles(){
        String hash1 = fileHandling.calcFileHash(synthecticDirectory + "\\3.txt");
        String hash2 = fileHandling.calcFileHash(synthecticDirectory + "\\4.txt");
        assert(!hash1.equals(hash2));
    }
    //For testing that both of the files are identical
    @Test
    public void testSameFiles(){
        String hash1 = fileHandling.calcFileHash(synthecticDirectory + "\\1.txt");
        String hash2 = fileHandling.calcFileHash(synthecticDirectory + "\\2.txt");
        assert(hash1.equals(hash2));
    }
    @Test
    public void testBothURLIdentical(){
        String str = getURLDom.extractingUrlDom(url);
        fileHandling.saveToFile(str, firstFileName);
        String hash1 = fileHandling.calcFileHash(directoryForTest + firstFileName);
        String res = getURLDom.extractingUrlDom(url);
        fileHandling.saveToFile(res, secondFileName);
        String hash2 = fileHandling.calcFileHash(directoryForTest + secondFileName);
        assert(hash1.equals(hash2));


    }

    @Test
    public void testDeviceType(){
        String userAgent = (String) ((JavascriptExecutor) driver).executeScript("return navigator.userAgent;");
        System.out.println(userAgent);
        assert(userAgent.contains("iPhone"));

    }


    @AfterClass(alwaysRun = true)
    public void tearDown() throws Exception {
        fileHandling.deleteDirectory(directoryForTest);
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
