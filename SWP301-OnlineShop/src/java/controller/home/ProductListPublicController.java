/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.home;

import dal.ProductCategoryDBContext;
import dal.ProductListDBContext;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Category;
import model.Product;

/**
 *
 * @author Hoang Quang
 */
public class ProductListPublicController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ProductListDBContext productListDBContext = new ProductListDBContext();
        ProductCategoryDBContext productCategoryDBContext = new ProductCategoryDBContext();
        
        //get Parameter value
        String searchBy = request.getParameter("searchBy");
        String raw_subCategory = request.getParameter("subCategory");
        String raw_categoryID = request.getParameter("categoryID");
        String page = request.getParameter("page");
        
        
        //get list subcategory
        ArrayList<Category> listCategorys = productCategoryDBContext.getAllCategory();

        //get least post
        ArrayList<Product> leastProduct = productListDBContext.getListLeastProduct();

        //get list product sort by date
        if (page == null || page.trim().length() == 0) {
            page = "1";
        }
        int pagesize = 9;
        int pageindex = Integer.parseInt(page);

        //check search
        if (searchBy == null || searchBy.length() == 0 || searchBy.equals("-1")) {
            searchBy = "";
        }
        //check subCategory
        if (raw_subCategory == null || raw_subCategory.length() == 0 || raw_subCategory.equals("-1")) {
            raw_subCategory = "0";
        }
        int subCategory = Integer.parseInt(raw_subCategory);
        
        //check Category
        if (raw_categoryID == null || raw_categoryID.length() == 0 || raw_categoryID.equals("-1")) {
            raw_categoryID = "0";
        }
        int categoryID = Integer.parseInt(raw_categoryID);
        
        int numofrecords = productListDBContext.countSizeOfListProduct(searchBy, subCategory);
        int totalpage = (numofrecords % pagesize == 0) ? (numofrecords / pagesize)
                : (numofrecords / pagesize) + 1;

        ArrayList<Product> listProducts = productListDBContext.getListProductsPagging(searchBy, subCategory, pageindex, pagesize);
        
//        System.out.println(subCategory);
//        System.out.println(categoryID);
//        System.out.println("---");
        
        request.setAttribute("listCategorys", listCategorys);
        request.setAttribute("leastProduct", leastProduct);
        request.setAttribute("listProducts", listProducts);
        request.setAttribute("searchBy", searchBy);
        request.setAttribute("subCategory", subCategory);
        request.setAttribute("categoryID", categoryID);
        request.setAttribute("pagesize", pagesize);
        request.setAttribute("pageindex", pageindex);
        request.setAttribute("totalpage", totalpage);
        request.getRequestDispatcher("view/public/productlist.jsp").forward(request, response);
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
        processRequest(request, response);
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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
