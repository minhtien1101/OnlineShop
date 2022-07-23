/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import configs.HandleGenerate;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 *
 * @author DELL
 */
@WebServlet(urlPatterns = {"/uploadFile"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50) // 50MB
public class UploadImageController extends HttpServlet {

    private static final long serialVersionUID = 1L;

//    public static final String SAVE_DIRECTORY = "uploadDir";
    public static final String SAVE_DIRECTORY = "img";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/jsps/uploadFile.jsp");

        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String description = request.getParameter("description");
            System.out.println("Description: " + description);
            System.out.println("-----------------------------------------");
            // Đường dẫn tuyệt đối tới thư mục gốc của web app.
            String appPath = request.getServletContext().getRealPath("");
            System.out.println("app " +appPath);
            System.out.println("Name path "+ request.getContextPath());
            appPath = appPath.replace('\\', '/');
            int indexFolderRoot = appPath.indexOf("/build");
            appPath = appPath.substring(0, indexFolderRoot) + "/web/assets/";
            // Thư mục để save file tải lên. 
            String fullSavePath = null;
            if (appPath.endsWith("/")) {
                fullSavePath = appPath + SAVE_DIRECTORY;
            } else {
                fullSavePath = appPath + "/" + SAVE_DIRECTORY;
            }

            // Tạo thư mục nếu nó không tồn tại.
            File fileSaveDir = new File(fullSavePath);
            if (!fileSaveDir.exists()) {
                fileSaveDir.mkdir();
            }

            // Danh mục các phần đã upload lên (Có thể là nhiều file).
            for (Part part : request.getParts()) {
                
                String fileName = extractFileName(part, fullSavePath);
                if (fileName != null && fileName.length() > 0) {
                    String filePath = fullSavePath + File.separator + fileName;
                    System.out.println("Write attachment to file: " + filePath);

                    // Ghi vào file.
                    part.write(filePath);
//                    new ImageDBContext().insertImage(request.getContextPath() +"/view/images/"+ fileName);// /UploadFileLocal/filename
                }
            }

            // Upload thành công.
            response.sendRedirect(request.getContextPath() + "/uploadFileResults");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error: " + e.getMessage());
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/jsps/uploadFile.jsp");
            dispatcher.forward(request, response);
        }
    }

    private String extractFileName(Part part, String fullPath) {
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
                File checkFile = new File(fullPath+"/"+clientFileName.substring(i + 1));
                String oldFileName = clientFileName.substring(i + 1);
                System.out.println("oldName "+oldFileName);
                String newName ="";
                if(checkFile.exists()) {
                    newName = oldFileName.substring(0, oldFileName.lastIndexOf("."))+HandleGenerate.generateSubNameFile()+oldFileName.substring(oldFileName.lastIndexOf("."));
                }else {
                    newName = clientFileName.substring(i + 1);
                }
                return newName;
            }
        }
        return null;
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
