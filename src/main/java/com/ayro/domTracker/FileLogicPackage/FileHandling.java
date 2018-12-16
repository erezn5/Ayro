package com.ayro.domTracker.FileLogicPackage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class FileHandling {
    public String dataFolderpath="";

    private static final Logger logger = LoggerFactory.getLogger(FileHandling.class);
    File f;
    public FileHandling(String dataFolderpath){
        this.dataFolderpath = dataFolderpath;
        f = new File(dataFolderpath);
    }

    public void saveToFile(String dataToSaveToFile, String filename) {
        if(!isDirectoryEmpty(f)){
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(dataFolderpath+filename, true))) {
                bw.write(dataToSaveToFile);
                bw.newLine();
                logger.info("Saving to file finished successfully - Done");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isDirectoryEmpty(File f){

        if(f.isDirectory()){
            if(f.list().length > 0);
            logger.info("Directory is not empty");
            return false;
        }
        else{
            logger.info("Directory is empty");
            return true;
        }

    }

    public void deleteDirectory(String f){
        File directory = new File(f);
        //make sure directory exists
        if(!directory.exists()){
            logger.info("Directory does not exist.");
            System.exit(0);
        }else{
            try{
                delete(directory);
            }catch(IOException e){
                e.printStackTrace();
                System.exit(0);
            }
        }
        logger.info("Done");
    }

    public static void delete(File file) throws IOException{

        if(file.isDirectory()){
            //directory is empty, then delete it
            if(file.list().length==0){
                file.delete();
                logger.info("Directory is deleted : "
                        + file.getAbsolutePath());
            }else{
            //list all the directory contents
                String files[] = file.list();
                for (String temp : files) {
                    //construct the file structure
                    File fileDelete = new File(file, temp);
                    //recursive delete
                    delete(fileDelete);
                }
                //check the directory again, if empty then delete it
                if(file.list().length==0){
                    file.delete();
                    logger.info("Directory is deleted : "
                            + file.getAbsolutePath());
                }
            }
        }else{
            //if file, then delete it
            file.delete();
            logger.info("File is deleted : " + file.getAbsolutePath());
        }
    }
    public String calcStringHash(String input) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        byte[] bytesOfMessage = input.getBytes("UTF-8");

        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] thedigest = md.digest(bytesOfMessage);
       String res = thedigest.toString();
       return res;

    }

    public String calcFileHash(String filePath) {
        File file = new File(filePath);
        String md5 = toHex(Hash.MD5.checksum(file));
        logger.info("MD5    : " + md5);
        return md5;
    }

    private static String toHex(byte[] bytes) {
        return DatatypeConverter.printHexBinary(bytes);
    }

}
