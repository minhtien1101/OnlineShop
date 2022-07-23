/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.user;

import static configs.UploadImage.extractFileName;
import static configs.UploadImage.extractFileNameForProfile;
import dal.UserDBContext;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import model.Role;
import model.User;

/**
 *
 * @author Hoang Quang
 */
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 30, // 30MB
        maxRequestSize = 1024 * 1024 * 50) // 50MB
public class UserProfileControler extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public static final String SAVE_DIRECTORY = "img";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        //get paramester
        String raw_id = request.getParameter("id");
        String fullname = request.getParameter("fullname");
        String mobile = request.getParameter("mobile");
        String address = request.getParameter("address");
        String raw_gender = request.getParameter("gender");

        //validate value
        int id = Integer.parseInt(raw_id);
        boolean gender = raw_gender.equals("male");
        HttpSession session = request.getSession();
        User oldUserSession = (User) session.getAttribute("user");

        User user = new User();
        user.setId(id);
        user.setAddress(address);
        user.setFullname(fullname);
        user.setGender(gender);
        user.setMobile(mobile);

        user.setEmail(oldUserSession.getEmail());
        Role role = new Role();
        role.setId(oldUserSession.getRole().getId());
        role.setName(oldUserSession.getRole().getName());
        role.setAllowFeatures(oldUserSession.getRole().getAllowFeatures());
        user.setRole(role);

        //edit in db
        UserDBContext userDBContext = new UserDBContext();
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

        Part partAttachedImg1 = request.getPart("attachedImg1");
        String fileAttachedImg1 = extractFileNameForProfile(partAttachedImg1, fullSavePath);
        //change img
        if (fileAttachedImg1 != null && fileAttachedImg1.length() > 0 && fileAttachedImg1 != "abc") {
            String filePath = fullSavePath + File.separator + fileAttachedImg1;
            // Ghi vào file.
            partAttachedImg1.write(filePath);
            String fileUrl = "/assets/img/" + fileAttachedImg1;
            
            user.setAvatar(fileUrl);
            
            session.setAttribute("user", user);
            userDBContext.editUserProfile(user);
//            userDBContext.getUser(id);
            response.sendRedirect("../home?alterProfile=Update sucessfully!");
        }
        //do not change img
        if (fileAttachedImg1 == "abc") {
            user.setAvatar(oldUserSession.getAvatar());
            userDBContext.editUserProfileWithoutImg(user);
            session.setAttribute("user", user);
            response.sendRedirect("../home?alterProfile=Update sucessfully!");
        }

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
