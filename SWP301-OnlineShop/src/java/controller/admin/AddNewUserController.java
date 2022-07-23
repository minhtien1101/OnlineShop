/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.admin;

import configs.HandleGenerate;
import configs.SendMail;
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
import model.Role;
import model.User;

/**
 *
 * @author Admin
 */
@WebServlet(name = "AddNewUserController", urlPatterns = {"/admin/addNewUser"})
public class AddNewUserController extends BaseAuthController {

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
        ArrayList<Role> roles = roleDB.getAllRole();
        request.setAttribute("roles", roles);
        request.getRequestDispatcher("../view/admin/addNewUser.jsp").forward(request, response);
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
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        UserDBContext userDB = new UserDBContext();
        RoleDBContext roleDB = new RoleDBContext();
        ArrayList<Role> roles = roleDB.getAllRole();
        request.setAttribute("roles", roles);
        String alter, message;
        String fullname = request.getParameter("fullname");
        boolean gender = request.getParameter("gender").equals("male");
        String email = request.getParameter("email");
        String mobile = request.getParameter("mobile");
        int role = Integer.parseInt(request.getParameter("role"));
        String address = request.getParameter("address");
        boolean status = request.getParameter("status").equals("active");
        if (userDB.checkAccountHaveEmailOrMobileExisted(email, mobile)) {
            message = "Email or mobile was exited!";
            request.setAttribute("fullname", fullname);
            request.setAttribute("gender", gender);
            request.setAttribute("email", email);
            request.setAttribute("mobile", mobile);
            request.setAttribute("role", role);
            request.setAttribute("address", address);
            request.setAttribute("status", status);
            request.setAttribute("message", message);
            request.getRequestDispatcher("../view/admin/addNewUser.jsp").forward(request, response);
        } else {
            String password = HandleGenerate.generatePassword();
            String url = "http://localhost:8080/login";
            userDB.inserUser(fullname, password, gender, email, mobile, address, role, status);
            SendMail.sendPassword(email, fullname, password, url);
            request.setAttribute("alter", "success");
            request.setAttribute("search", email);
            response.sendRedirect("userList?alter=success&search=" + email);
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

}
