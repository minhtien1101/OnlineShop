/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.home;

import dal.ProductCategoryDBContext;
import dal.ProductListDBContext;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Cart;
import model.Category;
import model.Feedback;
import model.Product;
import model.User;

/**
 *
 * @author Hoang Quang
 */
@WebServlet(name = "ProductDetailsControler", urlPatterns = {"/productdetails"})
public class ProductDetailsControler extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
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
        Product productInfomation = productListDBContext.getProductById(productID);
        
        //get all feedback
        double total_start_percent = productListDBContext.getStartPercent(productID);
        int total_feedback = productListDBContext.getTotalFeedback(productID);
        int total_product_quantity_solded = productListDBContext.getTotalQuantityProductSolded(productID);
        //get all feedback of that product
        ArrayList<Feedback> listFeedbacks = productListDBContext.getListFeedbackByProductID(productID, pageindex, pagesize);

        //check value
        System.out.println(numofrecords);
        System.out.println(listFeedbacks.size());

        String[] product_description = productInfomation.getDescription().split("\n");


        //pass to jsp
        request.setAttribute("total_product_quantity_solded", total_product_quantity_solded);
        request.setAttribute("total_feedback", total_feedback);
        request.setAttribute("total_start_percent", total_start_percent);
        request.setAttribute("product_description", product_description);
        request.setAttribute("productInfomation", productInfomation);
        request.setAttribute("listCategorys", listCategorys);
        request.setAttribute("listFeedbacks", listFeedbacks);
        request.setAttribute("leastProduct", leastProduct);
        request.setAttribute("pagesize", pagesize);
        request.setAttribute("pageindex", pageindex);
        request.setAttribute("totalpage", totalpage);
        request.getRequestDispatcher("/view/public/productDetails.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
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
