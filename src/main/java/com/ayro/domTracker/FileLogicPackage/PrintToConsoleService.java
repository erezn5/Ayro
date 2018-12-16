package com.ayro.domTracker.FileLogicPackage;

import difflib.Chunk;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;

public class PrintToConsoleService {
    private static final Logger logger = LoggerFactory.getLogger(PrintToConsoleService.class);
     FileHandling fileHandling;

    public PrintToConsoleService(FileHandling fileHandling){

        this.fileHandling = fileHandling;
    }
    public void printListItems(List<Chunk> lst, String name){
        if(lst.size()>0) {
            logger.info("Results for " + name);
            for (Chunk str : lst) {
                logger.info(str.toString());
                fileHandling.saveToFile(str.toString(), name);
            }
        }
    }
}
