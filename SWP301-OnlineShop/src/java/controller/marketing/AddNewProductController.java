/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.marketing;

import configs.HandleGenerate;
import dal.CategoryDBContext;
import dal.ProductDBContext;
import dal.UserDBContext;
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
import javax.servlet.http.Part;
import model.Category;
import model.Product;
import model.SubCategory;
import model.User;

/**
 *
 * @author Admin
 */
@WebServlet(name = "AddNewProductController", urlPatterns = {"/marketing/addproduct"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 30, // 10MB
        maxRequestSize = 1024 * 1024 * 50) // 50MB
public class AddNewProductController extends BaseAuthController {

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

        String name = request.getParameter("name") == null ? "" : request.getParameter("name");
        String description = request.getParameter("description") == null ? "" : request.getParameter("description");
        String seller = request.getParameter("seller") == null ? "" : request.getParameter("seller");
        String category = request.getParameter("category") == null ? "" : request.getParameter("category");
        String subCategory = request.getParameter("subCategory") == null ? "" : request.getParameter("subCategory");
        String price = request.getParameter("price") == null ? "" : request.getParameter("price");
        String discount = request.getParameter("discount") == null ? "" : request.getParameter("discount");
        String quantity = request.getParameter("quantity") == null ? "" : request.getParameter("quantity");
        String featured = request.getParameter("featured") == null ? "deactivate" : request.getParameter("featured");
        String status = request.getParameter("status") == null ? "deactivate" : request.getParameter("status");

        request.setAttribute("name", name);
        request.setAttribute("description", description);
        request.setAttribute("seller", seller);
        request.setAttribute("category", category);
        request.setAttribute("subCategory", subCategory);
        request.setAttribute("price", price);
        request.setAttribute("discount", discount);
        request.setAttribute("quantity", quantity);
        request.setAttribute("featured", featured);
        request.setAttribute("status", status);

        String alterFail = request.getParameter("alterFail");
        String alterSuccess = request.getParameter("alterSuccess");
        request.setAttribute("alterFail", alterFail);
        request.setAttribute("alterSuccess", alterSuccess);

        CategoryDBContext categoryDB = new CategoryDBContext();
        ArrayList<Category> categorys = categoryDB.getAllCategory();
        String tempCategoryId = request.getParameter("categoryId");
        int categoryId;
        if (tempCategoryId == null) {
            categoryId = categorys.get(0).getId();
        } else {
            categoryId = Integer.parseInt(tempCategoryId);
        }
        UserDBContext userDb = new UserDBContext();
        ArrayList<User> sales = userDb.getSaleUser();
        ArrayList<SubCategory> subCatgorys = categoryDB.getSubCatgory(categoryId);
        request.setAttribute("sales", sales);
        request.setAttribute("categorys", categorys);
        request.setAttribute("subCategorys", subCatgorys);
        request.getRequestDispatcher("../view/marketing/addNewProduct.jsp").forward(request, response);
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
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        int sellerId = Integer.parseInt(request.getParameter("sellerId"));
        int categoryId = Integer.parseInt(request.getParameter("category"));
        int subCategoryId = Integer.parseInt(request.getParameter("subCategory"));
        long price = Long.parseLong(request.getParameter("price"));
        int discount = Integer.parseInt(request.getParameter("discount"));
        long quantity = Long.parseLong(request.getParameter("quantity"));
        boolean featured = request.getParameter("featured").equals("activate");
        boolean status = request.getParameter("status").equals("activate");

        ProductDBContext productDB = new ProductDBContext();
        Product product = productDB.addProduct(name, description, sellerId, subCategoryId, price, discount, quantity, featured, status);
        // save file
        saveFile(request, product.getId());
        response.sendRedirect("productlist?alter=All new product success!&search=" + product.getId());
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

        Part partThumbnail = request.getPart("thumbnail");
        String fileNameThumbnail = extractFileName(partThumbnail, fullSavePath);
        if (fileNameThumbnail != null && fileNameThumbnail.length() > 0) {
            String filePath = fullSavePath + File.separator + fileNameThumbnail;
            System.out.println("Write attachment to file: " + filePath);
            // Ghi vào file.
            partThumbnail.write(filePath);
            String fileUrl = "/assets/img/" + fileNameThumbnail;
            new ProductDBContext().updateThumbnailProduct(productId, fileUrl);
        }

        Part partAttachedImg1 = request.getPart("attachedImg1");
        String fileAttachedImg1 = extractFileName(partAttachedImg1, fullSavePath);
        if (fileAttachedImg1 != null && fileAttachedImg1.length() > 0) {
            String filePath = fullSavePath + File.separator + fileAttachedImg1;
            System.out.println("Write attachment to file: " + filePath);
            // Ghi vào file.
            partAttachedImg1.write(filePath);
            String fileUrl = "/assets/img/" + fileAttachedImg1;
            new ProductDBContext().addAttachedImageProduct(productId, fileUrl);
        }

        Part partAttachedImg2 = request.getPart("attachedImg2");
        String fileAttachedImg2 = extractFileName(partAttachedImg2, fullSavePath);
        if (fileAttachedImg2 != null && fileAttachedImg2.length() > 0) {
            String filePath = fullSavePath + File.separator + fileAttachedImg2;
            System.out.println("Write attachment to file: " + filePath);
            // Ghi vào file.
            partAttachedImg2.write(filePath);
            String fileUrl = "/assets/img/" + fileAttachedImg2;
            new ProductDBContext().addAttachedImageProduct(productId, fileUrl);
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
