package com.ayro.domTracker;

import com.ayro.domTracker.FileLogicPackage.FileComparator;
import com.ayro.domTracker.FileLogicPackage.FileHandling;
import com.ayro.domTracker.FileLogicPackage.PrintToConsoleService;
import com.ayro.domTracker.SeleniumUtils.GetURLDom;
import com.ayro.domTracker.SeleniumUtils.SetUpClass;
import difflib.Chunk;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class DomElementCheckerManager {

    static FileHandling fileHandling;
    static GetURLDom getURLDom;
    static private SetUpClass setUpClass;
    static WebDriver driver;
//    static String url = "www.ynet.co.il/";
    static PrintToConsoleService printToConsoleService;
    private static final Logger logger = LoggerFactory.getLogger(FileHandling.class);

    public static void main(String[] args) throws IOException {

        Scanner input = new Scanner(System.in);
        System.out.println("Please enter your first URL: ");
        String userURL = input.nextLine();

        setUpClass = new SetUpClass();
        driver = setUpClass.getDriver();
        fileHandling = setUpClass.getFileHandling();

        getURLDom = setUpClass.getGetURLDom();
        String path1 = setUpClass.getDirectoryForTest() + setUpClass.getFirstFileName();
        String path2 = setUpClass.getDirectoryForTest() + setUpClass.getSecondFileName();
        String path3 = setUpClass.getDirectoryForTest() + setUpClass.getResultFileName();
        printToConsoleService = new PrintToConsoleService(fileHandling);
        String str = getURLDom.extractingUrlDom("https://" + userURL);
        fileHandling.saveToFile(str, setUpClass.getFirstFileName());
        String hash1 = fileHandling.calcFileHash(path1);
        String res = getURLDom.extractingUrlDom("https://" + userURL);
        fileHandling.saveToFile(res, setUpClass.getSecondFileName());
        String hash2 = fileHandling.calcFileHash(path2);

        if(hash1.equals(hash2)){
            logger.info("Both DOM elements are equal");
            System.exit(1);
        }

        logger.info("Files are different");
        logger.info("Going to display the changes:");

        final FileComparator comparator = new FileComparator(new File(path1), new File(path2));
        final List<Chunk> changesFromOriginal = comparator.getChangesFromOriginal();
        final List<Chunk> insertsFromOriginal = comparator.getInsertsFromOriginal();
        final List<Chunk> deletesFromOriginal = comparator.getDeletesFromOriginal();

        printToConsoleService.printListItems(changesFromOriginal, "changesFromOriginal");
        printToConsoleService.printListItems(insertsFromOriginal, "insertsFromOriginal");
        printToConsoleService.printListItems(deletesFromOriginal, "deletesFromOriginal");

    }


}
