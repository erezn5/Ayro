package com.ayro.domTracker.SeleniumUtils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LibraryUtils {
    String arr[] = new String[2];
    public WebElement waitForElementToBeVisible(WebDriver driver, WebElement webElement, int seconds) {
        WebDriverWait wait = new WebDriverWait(driver, seconds);
        WebElement element = wait.until(ExpectedConditions.visibilityOf(webElement));

        return element;
    }

    public WebElement waitForElementToBeClickable(WebDriver driver, WebElement webElement, int seconds){
        WebDriverWait wait = new WebDriverWait(driver, seconds);
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(webElement));
        return element;
    }

    public String[] gettingUsernameAndPasswordFromValidateCredentials(String str){

        String passwordToCheck = str.split("Username :")[1].split("</span")[0].split("/|Password :")[1].replace(")","").trim();
        String usernameToCheck = str.split("Username :")[1].split("</span")[0].split("/|Password :")[0].replace("|", "").trim();

        arr[0]=usernameToCheck;
        arr[1]=passwordToCheck;

        return arr;
    }
}
