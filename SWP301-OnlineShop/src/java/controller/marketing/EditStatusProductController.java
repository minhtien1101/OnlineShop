/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.marketing;

import dal.PostDBContext;
import dal.ProductDBContext;
import filter.BaseAuthController;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Admin
 */
@WebServlet(name = "EditStatusProductController", urlPatterns = {"/marketing/editStatusProduct"})
public class EditStatusProductController extends BaseAuthController {

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
        
        int id = Integer.parseInt(request.getParameter("idPost"));
            String nameStatus = (request.getParameter("idStatus").trim().equalsIgnoreCase("show")) ? "Hide" : "Show";
            String btnStatus = (nameStatus.equalsIgnoreCase("show")) ? "btn-success" : "btn-danger";
            ProductDBContext productDB = new ProductDBContext();
            boolean isStatus = (nameStatus.trim().equalsIgnoreCase("show"));
            productDB.changeStatus(id, isStatus);
            try ( PrintWriter out = response.getWriter()) {
                /* TODO output your page here. You may use following sample code. */
                out.println("<button id=\"btn-status-" + id + "\" type=\"button\" class=\"btn " + btnStatus + "\" data-toggle=\"modal\" data-target=\"#active\" onclick=\"openModals('" + id + "')\">\n"
                        + "                                                        " + nameStatus + "\n"
                        + "                                                    </button>");

            }
         
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
