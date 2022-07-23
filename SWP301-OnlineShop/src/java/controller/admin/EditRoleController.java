/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.admin;

import dal.RoleDBContext;
import filter.BaseAuthController;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Feature;
import model.Role;
import model.User;

/**
 *
 * @author hoan
 */
public class EditRoleController extends BaseAuthController {

    private static int rawid = 0;

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
        ArrayList<Role> roles = roleDB.getAllRole();
        request.setAttribute("roles", roles);

        ArrayList<Feature> AdminFeatures = roleDB.getFeatureByGroup("Admin");
        ArrayList<Feature> MarketingFeatures = roleDB.getFeatureByGroup("Marketing");
        ArrayList<Feature> SalesFeatures = roleDB.getFeatureByGroup("Sales");
        try {
            rawid = Integer.parseInt(request.getParameter("id"));
            LinkedHashMap<Feature, Boolean> allowedFeatures = roleDB.getAllowFeatures(rawid);
            request.setAttribute("allowedFeatures", allowedFeatures);
            request.setAttribute("rawid", rawid);
        } catch (Exception ex) {
        }
        request.setAttribute("AdminFeatures", AdminFeatures);
        request.setAttribute("MarketingFeatures", MarketingFeatures);
        request.setAttribute("SalesFeatues", SalesFeatures);
        request.getRequestDispatcher("../view/admin/editRole.jsp").forward(request, response);
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
        int roleid = Integer.parseInt(request.getParameter("roleId"));

        RoleDBContext roledb = new RoleDBContext();
        ArrayList<Role> roles = roledb.getAllRole();
        ArrayList<Feature> AdminFeatures = roledb.getFeatureByGroup("Admin");
        ArrayList<Feature> MarketingFeatures = roledb.getFeatureByGroup("Marketing");
        ArrayList<Feature> SalesFeatures = roledb.getFeatureByGroup("Sales");
        try {
            String[] selectedFeature = request.getParameterValues("fid");
            roledb.updateRole(roleid, selectedFeature);
            request.setAttribute("message", "Edit user's role success!");
            request.setAttribute("error", false);
        } catch (Exception ex) {
            request.setAttribute("message", "An error occured when update role!");
            request.setAttribute("error", true);
            Logger.getLogger(EditRoleController.class.getName()).log(Level.SEVERE, null, ex);
        }
        request.setAttribute("AdminFeatures", AdminFeatures);
        request.setAttribute("MarketingFeatures", MarketingFeatures);
        request.setAttribute("SalesFeatues", SalesFeatures);
        request.setAttribute("roles", roles);
        request.getRequestDispatcher("../view/admin/editRole.jsp").forward(request, response);
//        try {
//            roledb.updateRole(roleid, selectedFeature);
//            request.setAttribute("message", "Edit user's role success!");
//            request.setAttribute("error", false);
//            request.getRequestDispatcher("../view/admin/editRole.jsp").forward(request, response);
//        } catch (SQLException ex) {
//            request.setAttribute("message", "Edit user's role failed!");
//            request.setAttribute("error", true);
//            request.getRequestDispatcher("../view/admin/editRole.jsp").forward(request, response);
//        }

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
