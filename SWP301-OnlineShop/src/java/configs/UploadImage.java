/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package configs;

import java.io.File;
import javax.servlet.http.Part;

/**
 *
 * @author DELL
 */
public class UploadImage {
    public static String extractFileName(Part part, String fullPath) {
        // form-data; name="file"; filename="C:\file1.zip"
        // form-data; name="file"; filename="C:\Note\file2.zip"
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                // C:\file1.zip
                // C:\Note\file2.zip
                String clientFileName = s.substring(s.indexOf("=") + 2, s.length() - 1);
                clientFileName = clientFileName.replace("\\", "/");
                int i = clientFileName.lastIndexOf('/');
                // file1.zip
                // file2.zip
                //check flename o day
                //do something check file name
                File checkFile = new File(fullPath + "/" + clientFileName.substring(i + 1));
                String oldFileName = clientFileName.substring(i + 1);
                System.out.println("oldName " + oldFileName);
                String newName = "";
                if (checkFile.exists()) {
                    newName = oldFileName.substring(0, oldFileName.lastIndexOf(".")) + HandleGenerate.generateSubNameFile() + oldFileName.substring(oldFileName.lastIndexOf("."));
                } else {
                    newName = clientFileName.substring(i + 1);
                }
                return newName;
            }
        }
        return null;
    }
    public static String extractFileNameForProfile(Part part, String fullPath) {
        // form-data; name="file"; filename="C:\file1.zip"
        // form-data; name="file"; filename="C:\Note\file2.zip"
        String fileName = "abc";
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                // C:\file1.zip
                // C:\Note\file2.zip
                String clientFileName = s.substring(s.indexOf("=") + 2, s.length() - 1);
                clientFileName = clientFileName.replace("\\", "/");
                if(clientFileName.isEmpty()){
                    return fileName;
                }
                int i = clientFileName.lastIndexOf('/');
                // file1.zip
                // file2.zip
                //check flename o day
                //do something check file name
                File checkFile = new File(fullPath + "/" + clientFileName.substring(i + 1));
                String oldFileName = clientFileName.substring(i + 1);
                if (checkFile.exists()) {
                    fileName = oldFileName.substring(0, oldFileName.lastIndexOf(".")) + HandleGenerate.generateSubNameFile() + oldFileName.substring(oldFileName.lastIndexOf("."));
                } else {
                    fileName = clientFileName.substring(i + 1);
                }
                return fileName;
            }
        }
        return null;
    }

}
