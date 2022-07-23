/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package configs;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Admin
 */
public class DeleteFile {

    public static void handleDeleteFile(String fileName, HttpServletRequest request) {
        String appPath = request.getServletContext().getRealPath("");
        appPath = appPath.replace('\\', '/');
        int indexFolderRoot = appPath.indexOf("/build");
        appPath = appPath.substring(0, indexFolderRoot) + "/web/assets/img/";
        File oldFile = new File(appPath + "" + fileName.substring(fileName.lastIndexOf("/") + 1));
        System.out.println(oldFile);
        oldFile.delete();
    }

    public static void main(String[] args) {
        DeleteFile.handleDeleteFile("/assets/img/con-cho.png");
    }

    public static String getAppPath(HttpServletRequest request) {
        return request.getServletContext().getRealPath("");
    }

    public static void handleDeleteFile(String image) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
