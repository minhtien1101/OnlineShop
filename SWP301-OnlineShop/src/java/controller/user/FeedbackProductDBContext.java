/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.user;

import configs.HandleGenerate;
import configs.UploadImage;
import static configs.UploadImage.extractFileName;
import static controller.marketing.AddNewPostController.SAVE_DIRECTORY;
import controller.marketing.PostListController;
import dal.FeedbackDBContext;
import dal.OrderDBContext;
import dal.ProductCategoryDBContext;
import dal.ProductDBContext;
import dal.ProductListDBContext;
import dal.UserDBContext;
import filter.BaseAuthController;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import model.Category;
import model.Feedback;
import model.Order;
import model.Product;
import model.User;

/**
 *
 * @author Hoang Quang
 */
@WebServlet(name = "FeedbackProductDBContext", urlPatterns = {"/feedbackProduct"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 30, // 30MB
        maxRequestSize = 1024 * 1024 * 50) // 50MB
public class FeedbackProductDBContext extends BaseAuthController {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private static final long serialVersionUID = 1L;

    public static final String SAVE_DIRECTORY = "img";

    @Override
    protected void processGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ProductCategoryDBContext productCategoryDBContext = new ProductCategoryDBContext();
        ProductListDBContext productListDBContext = new ProductListDBContext();
        OrderDBContext orderDBContext = new OrderDBContext();
        UserDBContext userDB = new UserDBContext();

        int userID = Integer.parseInt(request.getParameter("userID"));
        int productID = Integer.parseInt(request.getParameter("productID"));
        

        //get value from request
        String raw_orderID = request.getParameter("orderID");
        //validate value
        int orderID = Integer.parseInt(raw_orderID);
        
        //GET SIDER INFOR
        //get list subcategory
        ArrayList<Category> listCategorys = productCategoryDBContext.getAllCategory();
        //get least post
        ArrayList<Product> leastProduct = productListDBContext.getListLeastProduct();

        //Get information of user
        User userInformation = userDB.getUserInformationByID(userID);

        String alter = request.getParameter("alter");
        request.setAttribute("alter", alter);
        request.setAttribute("productID", productID);
        request.setAttribute("orderID", orderID);
        request.setAttribute("userInformation", userInformation);
        request.setAttribute("listCategorys", listCategorys);
        request.setAttribute("leastProduct", leastProduct);
        request.getRequestDispatcher("/view/public/feedbackForProduct.jsp").forward(request, response);
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
    protected void processPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int userID = Integer.parseInt(request.getParameter("userID"));
        int productID = Integer.parseInt(request.getParameter("productID"));
        int orderID = Integer.parseInt(request.getParameter("orderID"));
        
        int star = Integer.parseInt(request.getParameter("star"));
        String commnent = request.getParameter("commnet").trim();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu/MM/dd");
        LocalDate localDate = LocalDate.now();
        Date dateNow = Date.valueOf(dtf.format(localDate).replaceAll("/", "-"));
        boolean status = true;
        String img = "";


        FeedbackDBContext feedbackDB = new FeedbackDBContext();
        Feedback feedback = feedbackDB.addNewFeedback(userID, productID, star, commnent, img, status, dateNow);
        OrderDBContext orderDB = new OrderDBContext();
        orderDB.editStatusFeedback(productID, orderID);
        saveFile(request, feedback.getId());
        response.sendRedirect("orderInfor?orderID=" + orderID + "&alter=Feedback Successfully!");
    }

    protected void saveFile(HttpServletRequest request, int feedbackId) throws IOException, ServletException {
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

        Part partAttachedImg1 = request.getPart("imgfeedback1");
        String fileAttachedImg1 = extractFileName(partAttachedImg1, fullSavePath);
        if (fileAttachedImg1 != null && fileAttachedImg1.length() > 0) {
            String filePath = fullSavePath + File.separator + fileAttachedImg1;
            System.out.println("Write attachment to file: " + filePath);
            // Ghi vào file.
            partAttachedImg1.write(filePath);
            String fileUrl = "/assets/img/" + fileAttachedImg1;
            new FeedbackDBContext().addAttachedImageFeedback(feedbackId, fileUrl);
        }

        Part partAttachedImg2 = request.getPart("imgfeedback2");
        String fileAttachedImg2 = extractFileName(partAttachedImg2, fullSavePath);
        if (fileAttachedImg2 != null && fileAttachedImg2.length() > 0) {
            String filePath = fullSavePath + File.separator + fileAttachedImg2;
            System.out.println("Write attachment to file: " + filePath);
            // Ghi vào file.
            partAttachedImg2.write(filePath);
            String fileUrl = "/assets/img/" + fileAttachedImg2;
            new FeedbackDBContext().addAttachedImageFeedback(feedbackId, fileUrl);
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

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>



}
