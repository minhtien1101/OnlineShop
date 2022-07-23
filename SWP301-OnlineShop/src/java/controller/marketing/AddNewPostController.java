/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.marketing;

import configs.UploadImage;
import dal.CategoryPostDBContext;
import dal.PostDBContext;
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
import javax.servlet.http.Part;
import model.CategoryPost;
import model.SubCategoryPost;
import model.User;

/**
 *
 * @author DELL
 */
@WebServlet(name = "AddNewPostController", urlPatterns = {"/marketing/addPost"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 30, // 30MB
        maxRequestSize = 1024 * 1024 * 50) // 50MB
public class AddNewPostController extends BaseAuthController {

    private static final long serialVersionUID = 1L;

    public static final String SAVE_DIRECTORY = "img";

    @Override
    protected void processGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        CategoryPostDBContext categoryPostDB = new CategoryPostDBContext();
        ArrayList<CategoryPost> listCategoryPost = categoryPostDB.getAllCategoryPost();
        ArrayList<SubCategoryPost> listSubcategoryPost = categoryPostDB.getListSubCategoryById(listCategoryPost.get(0).getId());
        request.setAttribute("listCategoryPost", listCategoryPost);
        request.setAttribute("listSubcategoryPost", listSubcategoryPost);
        request.getRequestDispatcher("../view/marketing/addNewPost.jsp").forward(request, response);
    }

    @Override
    protected void processPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        try {
            PostDBContext postDB = new PostDBContext();

            String title_raw = request.getParameter("title").trim();
            String brief_raw = request.getParameter("brief").trim();
            String description_raw = request.getParameter("description").trim();
            String category_raw = request.getParameter("category");
            String SubCategory_raw = request.getParameter("subCategory");
            String featured_raw = request.getParameter("featured");
            String status_raw = request.getParameter("status");

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu/MM/dd");
            LocalDate localDate = LocalDate.now();
            Date dateNow = Date.valueOf(dtf.format(localDate).replaceAll("/", "-"));
            User user = (User) request.getSession().getAttribute("user");
            int idUser = user.getId();
            int idCategory = Integer.parseInt(category_raw);
            int idSubCategory = Integer.parseInt(SubCategory_raw);
            boolean isFeatured = (featured_raw.trim().equalsIgnoreCase("on"));
            boolean isStatus = (status_raw.trim().equalsIgnoreCase("show"));

            // Đường dẫn tuyệt đối tới thư mục gốc của web app.
            String appPath = request.getServletContext().getRealPath("");
            System.out.println("app " + appPath);
            System.out.println("Name path " + request.getContextPath());
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

            Part fileImage = request.getPart("file");
            String fileName = UploadImage.extractFileName(fileImage, fullSavePath);
            if (fileName != null && fileName.length() > 0) {
                String filePath = fullSavePath + File.separator + fileName;
                System.out.println("Write attachment to file: " + filePath);

                // Ghi vào file.
                fileImage.write(filePath);
//                String urlImage = request.getContextPath() +"/view/img/"+ fileName;
                String urlImage = request.getContextPath().trim() +"/assets/img/"+ fileName;
                postDB.insertPost(title_raw, urlImage, brief_raw, description_raw, dateNow,idSubCategory, isFeatured, isStatus, idUser);
            }
            PostListController.ALERT = "success";
        } catch (Exception e) {
            PostListController.ALERT = "failed";
            e.printStackTrace();
        }
        response.sendRedirect("./postlist");
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
