/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.admin;

import dal.RoleDBContext;
import filter.BaseAuthController;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Feature;
import model.User;

/**
 *
 * @author hoan
 */
public class AddRoleController extends BaseAuthController {

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
    protected void processGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        RoleDBContext roleDB = new RoleDBContext();
        ArrayList<Feature> adminFeatures = roleDB.getFeatureByGroup("Admin");
        ArrayList<Feature> marketingFeatures = roleDB.getFeatureByGroup("Marketing");
        ArrayList<Feature> SalesFeatures = roleDB.getFeatureByGroup("Sales");

        request.setAttribute("adminFeatures", adminFeatures);
        request.setAttribute("marketingFeatures", marketingFeatures);
        request.setAttribute("SalesFeatures", SalesFeatures);
        request.getRequestDispatcher("../view/admin/addRole.jsp").forward(request, response);
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
        String[] roles = request.getParameterValues("roleID");
        String roleName = request.getParameter("roleName");
        RoleDBContext roleDB = new RoleDBContext();
        ArrayList<Feature> adminFeatures = roleDB.getFeatureByGroup("Admin");
        ArrayList<Feature> marketingFeatures = roleDB.getFeatureByGroup("Marketing");
        ArrayList<Feature> SalesFeatures = roleDB.getFeatureByGroup("Sales");

        request.setAttribute("adminFeatures", adminFeatures);
        request.setAttribute("marketingFeatures", marketingFeatures);
        request.setAttribute("SalesFeatures", SalesFeatures);
        try {
            if (!roleDB.checkRoleExist(roleName)) {
                roleDB.insertNewRole(roles, roleName);
                request.setAttribute("message", "Add user's role success!");
                request.setAttribute("error", false);
            } else {
                request.setAttribute("message", "Insert role failed because role exist on the system! Please check role name again.");
                request.setAttribute("error", true);
            }
        } catch (SQLException ex) {
            request.setAttribute("message", "Add user's role failed!");
            request.setAttribute("error", true);
        }
        request.getRequestDispatcher("../view/admin/addRole.jsp").forward(request, response);
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
