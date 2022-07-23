/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.admin;

import dal.UserDBContext;
import filter.BaseAuthController;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.User;

/**
 *
 * @author Admin
 */
@WebServlet(name = "EditStatusUserController", urlPatterns = {"/admin/editUserStatus"})
public class EditStatusUserController extends BaseAuthController {

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
        UserDBContext userDB = new UserDBContext();
        boolean status = request.getParameter("newStatus").equals("active");
        int id = Integer.parseInt(request.getParameter("id"));
        String xpage = request.getParameter("xpage");
        userDB.changeStatus(id, status);
        if (xpage == null) {
            xpage = "1";
        } else {
            xpage = request.getParameter("xpage");
        }
//        request.setAttribute("xpage", xpage);
//        request.setAttribute("alter", "Update status sucess");
//        request.getRequestDispatcher("userList");
        response.sendRedirect("userList?xpage=" + xpage + "&alter=Update status sucess!");
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
        boolean status = request.getParameter("newStatus").equals("active");
        int id = Integer.parseInt(request.getParameter("id"));
        String xpage = request.getParameter("xpage");
        userDB.changeStatus(id, status);
        if (xpage == null) {
            xpage = "1";
        } else {
            xpage = request.getParameter("page");
        }
        request.setAttribute("xpage", xpage);
        request.setAttribute("alter", "Edit user's profile success!");
//        request.getRequestDispatcher("userList").forward(request, response);
        response.sendRedirect("../admin/userList?xpage=" + xpage + "&alter=Update stauts success");
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
