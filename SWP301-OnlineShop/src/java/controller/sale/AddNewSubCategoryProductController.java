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
import model.SubCategory;

/**
 *
 * @author Admin
 */
@WebServlet(name = "SaleAddNewSubCategoryProductController", urlPatterns = {"/sale/addSubCategoryProduct"})
public class AddNewSubCategoryProductController extends BaseAuthController {

   

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
        PrintWriter out = response.getWriter();
        int categoryId = Integer.parseInt(request.getParameter("categoryId"));
        CategoryDBContext categoryDB = new CategoryDBContext();
        ArrayList<SubCategory> subCatgorys = categoryDB.getSubCatgory(categoryId);
        for(SubCategory s : subCatgorys){
            out.println("<option value=\""+ s.getId() +"\" >\n" +
                        ""+ s.getName() +"\n" +
                        "</option>");
            //out.println("<option  value=\""+s.getId()+"\" ${requestScope.categoryId == "+ s.getId() +" ? \"selected='selected'\":\"\"}>"+ s.getName() +"</option>");
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
        PrintWriter out = response.getWriter();
        int categoryId = Integer.parseInt(request.getParameter("categoryId"));
        String subcategory = request.getParameter("subCategory");
        CategoryDBContext categoryDB = new CategoryDBContext();
        ArrayList<SubCategory> subCategorys = categoryDB.getSubCatgory(categoryId);
        boolean isExisted = false;
        for (SubCategory subCategory : subCategorys) {
            if (subCategory.getName().equalsIgnoreCase(subcategory)) {
                isExisted = true;
            }
        }
        
        if (isExisted == true) {
            response.sendRedirect("");
        } else {
            SubCategory c = categoryDB.addSubcategory(categoryId,subcategory);
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
