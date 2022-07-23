/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.common;

import dal.CartDBContext;
import dal.ProductCategoryDBContext;
import dal.ProductDBContext;
import dal.ProductListDBContext;
import dal.UserDBContext;
import filter.BaseAuthController;
import java.io.Console;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Cart_Product;
import model.Category;
import model.Product;
import model.User;

/**
 *
 * @author ASUS
 */
@WebServlet(name = "CartContactController", urlPatterns = {"/cartContact"})
public class CartContactController extends BaseAuthController {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.getRequestDispatcher("/view/public/cartContact.jsp").forward(request, response);
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
    protected void processGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ProductCategoryDBContext productCategoryDBContext = new ProductCategoryDBContext();
        ProductListDBContext productListDBContext = new ProductListDBContext();
        User user = null;
        long total = 0;
        HttpSession session = request.getSession();
        CartDBContext cartDb = new CartDBContext();
        UserDBContext userDb = new UserDBContext();
        
        String[] productId = request.getParameterValues("cboproduct");
        int cartId = Integer.parseInt(request.getParameter("hCartId"));
        ArrayList<Cart_Product> cartProduct = new ArrayList<Cart_Product>();
        for (int i = 0; i < productId.length; i++) {
            Cart_Product cartP = cartDb.getCartProductByCidAndPid(cartId, Integer.parseInt(productId[i]));
            if (cartP != null) {
                total += (cartP.getProduct().getPriceDiscount() * cartP.getQuantity());
                cartProduct.add(cartP);
            }

        }

        //Get list subcategory
        ArrayList<Category> listCategorys = productCategoryDBContext.getAllCategory();

        //get least post
        ArrayList<Product> leastProduct = productListDBContext.getListLeastProduct();

        //Get user login from session
        user = (User) session.getAttribute("user");
        User userNew = userDb.getUserByEmail(user.getEmail());
        
        //Set cart contact in session
        session.setAttribute("cartContact", cartProduct);
        int userID = user.getId();

        request.setAttribute("user", userNew);

        request.setAttribute("cartProduct", cartProduct);

        request.setAttribute("total", total);
        
        request.setAttribute("listCategorys", listCategorys);

        request.setAttribute("leastProduct", leastProduct);

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
    protected void processPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        UserDBContext userDb = new UserDBContext();
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        User user = null;
        boolean rs = false;
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String mobile = request.getParameter("mobile");
        String address = request.getParameter("address");
        user = userDb.getUserByEmail(email);
        if (user != null) {
            //Set inf for user
            user.setUsername(fullName);
            user.setMobile(mobile);

            user.setAddress(address);

            //Update user
            rs = userDb.updateUserInf(user);
        }

        if (rs) {
            
            out.println("<b>" + fullName + "&nbsp;&nbsp;" + mobile + "</b>&nbsp;&nbsp;&nbsp;&nbsp;" + address + "");
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
