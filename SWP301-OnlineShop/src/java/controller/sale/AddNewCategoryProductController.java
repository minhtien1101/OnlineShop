/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.sale;

import dal.CategoryDBContext;
import filter.BaseAuthController;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Category;

/**
 *
 * @author Admin
 */
@WebServlet(name = "SaleAddNewCategoryProductController", urlPatterns = {"/sale/addCategoryProduct"})
public class AddNewCategoryProductController extends BaseAuthController {



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
        PrintWriter out = response.getWriter();
        String newCategory = request.getParameter("newCategory");
        CategoryDBContext categoryDB = new CategoryDBContext();
        ArrayList<Category> allCategory = categoryDB.getAllCategory();
        boolean isExisted = false;
        for (Category category : allCategory) {
            if (category.getName().equalsIgnoreCase(newCategory)) {
                isExisted = true;
            }
        }
        
        if (isExisted == true) {
            response.sendRedirect("");
        } else {
            Category c = categoryDB.addCategory(newCategory);
            out.println("<option value=\""+ c.getId() +"\"\n"
                    + " <c:if test=\"${category == "+ c.getId() +"}\">\n"
                    + " "+ c.getName() +"\n"
                    + "</option>\n");
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
