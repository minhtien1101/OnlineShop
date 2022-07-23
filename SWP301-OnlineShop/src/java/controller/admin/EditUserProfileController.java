/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.admin;

import dal.RoleDBContext;
import dal.UserDBContext;
import filter.BaseAuthController;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Role;
import model.User;

/**
 *
 * @author Admin
 */
@WebServlet(name = "EditUserProfileController", urlPatterns = {"/admin/editUserProfile"})
public class EditUserProfileController extends BaseAuthController {

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
        RoleDBContext roleDB = new RoleDBContext();
        UserDBContext userDB = new UserDBContext();
        int id = Integer.parseInt(request.getParameter("id"));
        ArrayList<Role> roles = roleDB.getAllRole();
        User user = userDB.getUserById(id);
        request.setAttribute("user", user);
        request.setAttribute("roles", roles);
        request.getRequestDispatcher("../view/admin/userProfile.jsp").forward(request, response);
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
        UserDBContext userDB = new UserDBContext();
        int id = Integer.parseInt(request.getParameter("id"));
        boolean status = request.getParameter("status").equals("active");
        int roleId = Integer.parseInt(request.getParameter("role"));
        User user = userDB.getUserById(id);
        userDB.editUserProfile(id, roleId, status);
        String alter = "Update user's information success";
        request.setAttribute("alter", alter);
        request.setAttribute("id", id);
        response.sendRedirect("userList?search="+ user.getEmail()+"&alter=" + alter);
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
