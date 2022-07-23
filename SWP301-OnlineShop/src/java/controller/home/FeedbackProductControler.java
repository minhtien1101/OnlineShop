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
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Category;
import model.Feedback;
import model.Product;

/**
 *
 * @author Hoang Quang
 */
@WebServlet(name = "FeedbackProductControler", urlPatterns = {"/feedbackproduct"})
public class FeedbackProductControler extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ProductCategoryDBContext productCategoryDBContext = new ProductCategoryDBContext();
        ProductListDBContext productListDBContext = new ProductListDBContext();

        //get list subcategory
        ArrayList<Category> listCategorys = productCategoryDBContext.getAllCategory();

        //get least post
        ArrayList<Product> leastProduct = productListDBContext.getListLeastProduct();

        //GET PRODUCT DETAILS, FEEDBACK OF THAT PRODUCT
        //get value from jsp
        String raw_productID = request.getParameter("productID");
        String page = request.getParameter("page");

        // validate value
        int productID = Integer.parseInt(raw_productID);

        //get list product sort by date
        if (page == null || page.trim().length() == 0) {
            page = "1";
        }
        int pagesize = 5;
        int pageindex = Integer.parseInt(page);

        //get pageSize
        int numofrecords = productListDBContext.countSizeOfFeedback(productID);
        int totalpage = (numofrecords % pagesize == 0) ? (numofrecords / pagesize)
                : (numofrecords / pagesize) + 1;

        //get product with that id
        Product productInfomation = productListDBContext.getProductByID(productID);
        //get all feedback of that product
        ArrayList<Feedback> listFeedbacks = productListDBContext.getListFeedbackByProductID(productID, pageindex, pagesize);

        //check value
        System.out.println(numofrecords);
        System.out.println(listFeedbacks.size());

        //pass to jsp
        request.setAttribute("productInfomation", productInfomation);
        request.setAttribute("listCategorys", listCategorys);
        request.setAttribute("listFeedbacks", listFeedbacks);
        request.setAttribute("leastProduct", leastProduct);
        request.setAttribute("pagesize", pagesize);
        request.setAttribute("pageindex", pageindex);
        request.setAttribute("totalpage", totalpage);
        request.getRequestDispatcher("/view/public/feedbackProduct.jsp").forward(request, response);
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
