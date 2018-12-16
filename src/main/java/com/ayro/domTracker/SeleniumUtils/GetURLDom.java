package com.ayro.domTracker.SeleniumUtils;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class GetURLDom {
    WebDriver driver;
    String str;
    private final Logger logger = LoggerFactory.getLogger(GetURLDom.class);
    File f;

    public GetURLDom(WebDriver driver){

        this.driver = driver;
    }

    public String extractingUrlDom(String url){
        logger.info("Navigating to " + url);
        this.driver.get(url);
        this.str = driver.getPageSource();
        
        return str;
    }

}
