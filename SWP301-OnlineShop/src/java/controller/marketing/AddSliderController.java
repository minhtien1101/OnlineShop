/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.marketing;

import configs.DeleteFile;
import configs.HandleGenerate;
import dal.SliderDBContext;
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
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import model.Slider;
import model.User;

/**
 *
 * @author Admin
 */
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50) // 
public class AddSliderController extends HttpServlet {

    private static final long serialVersionUID = 1L;

//    public static final String SAVE_DIRECTORY = "uploadDir";
    public static final String SAVE_DIRECTORY = "img";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            SliderDBContext sliderDb = new SliderDBContext();
            int id = 0;
            if (request.getParameter("id") != null) {
                id = Integer.parseInt(request.getParameter("id"));
                Slider slider = sliderDb.getSliderById(id);
                request.setAttribute("s", slider);
                request.setAttribute("image", slider.getImage());
            }

            request.setAttribute("id", id);
        } catch (Exception e) {
        }
        request.getRequestDispatcher("/view/marketing/addSlider.jsp").forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        

        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        Slider slider = null;
        String strId = "";
        SliderDBContext sliderDb = new SliderDBContext();
        boolean isAdd = true;
        strId = request.getParameter("hId");
        String title = request.getParameter("txtTitle").trim();
        String backlink = request.getParameter("txtBacklink").trim();
        String strStatus = request.getParameter("txtStatus");
        String notes = request.getParameter("txtNotes").trim();
        slider = new Slider();
        slider.setTitle(title);
        slider.setBacklink(backlink);
        slider.setStatus(Boolean.parseBoolean(strStatus));
        boolean sta = slider.isStatus();
        slider.setNote(notes);
        slider.setUser((User) (session.getAttribute("user")));
        //Edit slider
        if (strId != null && strId != "" && !strId.equals("0")) {
            isAdd = false;
            int id = Integer.parseInt(strId);
            Slider sliderOld = sliderDb.getSliderById(id);
            slider.setId(sliderOld.getId());
            slider.setImage(sliderOld.getImage());
        }
        saveFile(request, slider, isAdd);

        request.getRequestDispatcher("/view/marketing/addSlider.jsp").forward(request, response);
    }

    protected void saveFile(HttpServletRequest request, Slider slider, boolean isAdd) throws IOException, ServletException {
        boolean rs = false;
        SliderDBContext sliderDb = new SliderDBContext();
        // Đường dẫn tuyệt đối tới thư mục gốc của web app.
        String appPath = request.getServletContext().getRealPath("");

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

        Part partSlider = request.getPart("fImage");
        if (partSlider.getSize() > 0) {
            String fileNameSlider = extractFileName(partSlider, fullSavePath);
            if (fileNameSlider != null && fileNameSlider.length() > 0) {
                String fileUrl = "/assets/img/" + fileNameSlider;
//                if (isAdd == false) {
//                    DeleteFile.handleDeleteFile(slider.getImage());
//                }
                slider.setImage(fileUrl);
                String filePath = fullSavePath + File.separator + fileNameSlider;
                partSlider.write(filePath);
//                new ProductDBContext().updateThumbnailProduct(productId, fileUrl);
            }
        }
             //Update Slider
            if (isAdd == false) {
                rs = sliderDb.updateSlider(slider);
                if (rs) {
                    request.setAttribute("messTrue", "Update Slider Successful!");
                } else {
                    request.setAttribute("messFalse", "Slider update failed. Please try again!");
                }
            
                request.setAttribute("s", slider);
                request.setAttribute("image", slider.getImage());
                request.setAttribute("id", slider.getId());
                //Add Slider
            } else {
                rs = sliderDb.addSlider(slider);
                if (rs) {
                    request.setAttribute("messTrue", "Add slider successfully!");
                } else {
                    request.setAttribute("messFalse", "Adding slider failed. Please try again!");
                }
                request.setAttribute("id", 0);
            }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

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

}
