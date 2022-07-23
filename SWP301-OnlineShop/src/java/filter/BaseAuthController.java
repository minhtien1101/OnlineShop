/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filter;

import dal.UserDBContext;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.User;

/**
 *
 * @author SAP-LAP-FPT
 */
public abstract class BaseAuthController extends HttpServlet {

    private boolean isAuth(HttpServletRequest request)
    {
        User user = (User)request.getSession().getAttribute("user");
        if(user ==null)
            return false;
        else
        {
            return true;
        }
    }

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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if(isAuth(request))
        {
            try {
                //business
                processGet(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(BaseAuthController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else
        {
            response.sendRedirect(request.getContextPath() + "/401.html");
        }
    }
    
    protected abstract void processGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException;
    protected abstract void processPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException;

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if(isAuth(request))
        {
            try {
                //business
                processPost(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(BaseAuthController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else
        {
            response.sendRedirect(request.getContextPath() + "/401.html");
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
