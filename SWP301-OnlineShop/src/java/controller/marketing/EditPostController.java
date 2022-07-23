/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.marketing;

import configs.UploadImage;
import static controller.marketing.AddNewPostController.SAVE_DIRECTORY;
import dal.CategoryPostDBContext;
import dal.PostDBContext;
import filter.BaseAuthController;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
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
import model.Post;
import model.SubCategoryPost;
import model.User;

/**
 *
 * @author DELL
 */
@WebServlet(name = "EditPostController", urlPatterns = {"/marketing/editPost"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 30, // 30MB
        maxRequestSize = 1024 * 1024 * 50) // 50MB
public class EditPostController extends BaseAuthController {

    @Override
    protected void processGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        int idPost = Integer.parseInt(id);

        PostDBContext postDB = new PostDBContext();
        CategoryPostDBContext categoryPost = new CategoryPostDBContext();
        Post post = postDB.getPostByIdIncludeCategory(idPost);

        ArrayList<CategoryPost> listCategory = categoryPost.getAllCategoryPost();
        ArrayList<SubCategoryPost> listSubCategory = categoryPost.getListSubCategoryById(post.getPostCategory().getCategory().getId());

        request.setAttribute("listCategory", listCategory);
        request.setAttribute("listSubCategory", listSubCategory);
        request.setAttribute("post", post);
        request.getRequestDispatcher("../view/marketing/editPost.jsp").forward(request, response);
    }

    @Override
    protected void processPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        PostDBContext postDB = new PostDBContext();

        int id = Integer.parseInt(request.getParameter("id"));
        String title = request.getParameter("title").trim();
        String briefInfo = request.getParameter("brief").trim();
        String description = request.getParameter("description").trim();
        int category = Integer.parseInt(request.getParameter("category"));
        int subCategory = Integer.parseInt(request.getParameter("subCategory"));
        boolean isFeatured = (request.getParameter("featured").equalsIgnoreCase("hot"));
        boolean isStatus = (request.getParameter("status").equalsIgnoreCase("show"));
        
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu/MM/dd");
        LocalDate localDate = LocalDate.now();
        Date dateNow = Date.valueOf(dtf.format(localDate).replaceAll("/", "-"));
        User user = (User) request.getSession().getAttribute("user");
        int idUser = user.getId();
        Part fileImage = request.getPart("file");
//        String fileNameRaw = Paths.get(fileImage.getSubmittedFileName()).getFileName().toString();
        if (fileImage.getSize() <= 0) { //fileNameRaw == null || fileNameRaw.equals("") 
            System.out.println("true");
            try {
                String urlImage = "";
                postDB.editPost(id, urlImage, title, briefInfo, description, dateNow, idUser, isFeatured, isStatus, category, subCategory);
                PostListController.ALERT = "success2";
            } catch (Exception e) {
                PostListController.ALERT = "failed2";
            }
        } else {
            try {
                String urlImageOld = request.getParameter("thumbnailOld").trim();
                String appPath = request.getServletContext().getRealPath("");
//                System.out.println("app " + appPath);
//                System.out.println("Name path " + request.getContextPath());
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
                File fileSaveDir = new File(fullSavePath);
                if (!fileSaveDir.exists()) {
                    fileSaveDir.mkdir();
                }
                String fileName = UploadImage.extractFileName(fileImage, fullSavePath);
                if (fileName != null && fileName.length() > 0) {
                    String filePath = fullSavePath + File.separator + fileName;
                    System.out.println("Write attachment to file: " + filePath);

                    // Ghi vào file.
                    fileImage.write(filePath);
                    String urlImage = request.getContextPath().trim() + "/assets/img/" + fileName;
                    postDB.editPost(id, urlImage, title, briefInfo, description, dateNow, idUser, isFeatured, isStatus, category, subCategory);
                    // delete old file
                    System.out.println("old url " + appPath + "" + urlImageOld.substring(urlImageOld.lastIndexOf("/") + 1));
                    File oldFile = new File(appPath + "" + urlImageOld.substring(urlImageOld.lastIndexOf("/") + 1));
                    oldFile.delete();
                }
                PostListController.ALERT = "success2";
            } catch (Exception e) {
                PostListController.ALERT = "failed2";
                e.printStackTrace();
            }
        }
        response.sendRedirect("./postlist");

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
