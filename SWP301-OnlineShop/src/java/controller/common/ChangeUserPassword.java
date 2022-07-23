/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.common;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import dal.UserDBContext;
import filter.BaseAuthController;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.User;

/**
 *
 * @author hoan
 */
public class ChangeUserPassword extends BaseAuthController {

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
        JsonObject json = new JsonObject();
        json.addProperty("Code", 405);
        json.addProperty("Msg", "Method GET not allowed on this end-point!");
        response.setStatus(405);
        response.getWriter().println(new GsonBuilder().setPrettyPrinting().create().toJson(json).toString());
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
        try {
            String oldPass = request.getParameter("oldPassword");
            String newPass = request.getParameter("newPassword");
            UserDBContext userDB = new UserDBContext();
            User u = (User) request.getSession().getAttribute("user");
            String userOldPass = userDB.getUserPassword(u.getId());
            JsonObject json = new JsonObject();
            if (oldPass.equals(userOldPass)) {
                json.addProperty("Code", 200);
                json.addProperty("Msg", "Your password has been changed successfully!");
                userDB.updateUserPassword(u.getId(), newPass);
                response.setStatus(200);
                response.getWriter().println(new GsonBuilder().setPrettyPrinting().create().toJson(json).toString());
            } else {
                json.addProperty("Code", 500);
                json.addProperty("Msg", "Old password you entered does not match with your current password!");
                response.setStatus(500);
                response.getWriter().println(new GsonBuilder().setPrettyPrinting().create().toJson(json).toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            JsonObject json = new JsonObject();
            json.addProperty("Code", 500);
            json.addProperty("Msg", "An error has occured!" + e.getMessage());
            response.setStatus(500);
            response.getWriter().println(new GsonBuilder().setPrettyPrinting().create().toJson(json).toString());
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
