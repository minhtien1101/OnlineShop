/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.sale;

import configs.DeleteFile;
import configs.HandleGenerate;
import dal.CategoryDBContext;
import dal.ProductDBContext;
import filter.BaseAuthController;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
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
import model.Product;
import model.SubCategory;
import model.User;

/**
 *
 * @author Admin
 */
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 30, // 10MB
        maxRequestSize = 1024 * 1024 * 50) // 50MB
@WebServlet(name = "SaleEditProductInfoController", urlPatterns = {"/sale/editProductInfo"})
public class EditProductInfoController extends BaseAuthController {


    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void processGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int productId = Integer.parseInt(request.getParameter("id"));
        ProductDBContext productDB = new ProductDBContext();
        Product product = productDB.getProductById(productId);

        CategoryDBContext categoryDB = new CategoryDBContext();
        ArrayList<Category> categorys = categoryDB.getAllCategory();
        int categoryId = product.getSubCategory().getCategory().getId();
        int subCategoryId = product.getSubCategory().getId();

        ArrayList<SubCategory> subCatgorys = categoryDB.getSubCatgory(categoryId);
        request.setAttribute("product", product);
        System.out.println(product);
        request.setAttribute("categorys", categorys);
        request.setAttribute("subCategorys", subCatgorys);
        request.setAttribute("categoryId", categoryId);
        request.setAttribute("subCategoryId", subCategoryId);
        request.getRequestDispatcher("../view/sale/productInfo.jsp").forward(request, response);
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
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        int sellerId = user.getId();
//        int sellerId = 1;
        int subCategoryId = Integer.parseInt(request.getParameter("subCategory"));
        long price = Long.parseLong(request.getParameter("price"));
        int discount = Integer.parseInt(request.getParameter("discount"));
        long quantity = Long.parseLong(request.getParameter("quantity"));
        boolean featured = request.getParameter("featured").equals("activate");
        boolean status = request.getParameter("status").equals("activate");

        ProductDBContext productDB = new ProductDBContext();
        productDB.updateProduct(id, name, description, sellerId, subCategoryId, price, discount, quantity, featured, status);
        // save file
        saveFile(request, id);
        response.sendRedirect("productlist?alter=Eidt product info success!&search=" + id);
    }
    
    private static final long serialVersionUID = 1L;
    public static final String SAVE_DIRECTORY = "img";

    protected void saveFile(HttpServletRequest request, int productId) throws IOException, ServletException {
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
        ProductDBContext productDB = new ProductDBContext();
        Product product = productDB.getProductById(productId);

        Part partThumbnail = request.getPart("thumbnail");
        if (partThumbnail.getSize() > 0) {
            String fileNameThumbnail = extractFileName(partThumbnail, fullSavePath);
            if (fileNameThumbnail != null && fileNameThumbnail.length() > 0) {
                String fileUrl = "/assets/img/" + fileNameThumbnail;
                // delete old thubnail
                DeleteFile.handleDeleteFile(product.getThumbnail(),request);
                // update thubnail
                productDB.updateThumbnailProduct(productId, fileUrl);

                String filePath = fullSavePath + File.separator + fileNameThumbnail;
                // Ghi vào file.
                partThumbnail.write(filePath);
                new ProductDBContext().updateThumbnailProduct(productId, fileUrl);
            }
        }

        Part partAttachedImg1 = request.getPart("attachedImg1");
        if (partAttachedImg1.getSize() > 0) {
            String fileAttachedImg1 = extractFileName(partAttachedImg1, fullSavePath);
            if (fileAttachedImg1 != null && fileAttachedImg1.length() > 0) {
                String fileUrl = "/assets/img/" + fileAttachedImg1;
                // delete old attached image 1
                String linkImg = product.getImage().get(0).getImage();
                DeleteFile.handleDeleteFile(linkImg,request);
                // update attached image 1
                int attchedImg1Id = product.getImage().get(0).getId();
                productDB.updateImage(attchedImg1Id, fileUrl);

                String filePath = fullSavePath + File.separator + fileAttachedImg1;
                // Ghi vào file.
                partAttachedImg1.write(filePath);
                new ProductDBContext().updateImage(attchedImg1Id, fileUrl);
            }
        }

        Part partAttachedImg2 = request.getPart("attachedImg2");
        if (partAttachedImg2.getSize() > 0) {
            String fileAttachedImg2 = extractFileName(partAttachedImg2, fullSavePath);
            if (fileAttachedImg2 != null && fileAttachedImg2.length() > 0) {
                String fileUrl = "/assets/img/" + fileAttachedImg2;

                // delete old attached image 2
                String linkImg = product.getImage().get(1).getImage();
                DeleteFile.handleDeleteFile(linkImg,request);
                // update attached image 2
                int attchedImg2Id = product.getImage().get(1).getId();
                productDB.updateImage(attchedImg2Id, fileUrl);

                String filePath = fullSavePath + File.separator + fileAttachedImg2;
                System.out.println("Write attachment to file: " + filePath);
                // Ghi vào file.
                partAttachedImg2.write(filePath);
                new ProductDBContext().updateImage(attchedImg2Id, fileUrl);
            }
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
