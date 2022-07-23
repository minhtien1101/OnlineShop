/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.home;

import dal.CartDBContext;
import dal.ProductDBContext;
import dal.UserDBContext;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Cart;
import model.Cart_Product;
import model.Product;
import model.User;

/**
 *
 * @author Admin
 */
@WebServlet(name = "AddProductToCartController", urlPatterns = {"/addProductToCart"})
public class AddProductToCartController extends HttpServlet {

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
        int customerId = Integer.parseInt(request.getParameter("customerId"));
        int quantityOrder = Integer.parseInt(request.getParameter("quantityOrder"));
        int productId = Integer.parseInt(request.getParameter("productId"));
      
        Timestamp currentDateForCart_Product = new Timestamp(System.currentTimeMillis());

        CartDBContext cartDB = new CartDBContext();
        UserDBContext userDB = new UserDBContext();
        ProductDBContext productDB = new ProductDBContext();

        // get quantity of product was contain
        Product product_existed = productDB.getProductById(productId);
        int product_quatinty_contain = (int) product_existed.getQuantity();

        Cart cart = cartDB.getCartByCustomerId(customerId);
        if (cart == null) {
            Cart newCart = new Cart();

            User customer = userDB.getUserById(customerId);
            newCart.setCustomer(customer);

            ArrayList<Cart_Product> cart_Products = new ArrayList<>();
            Cart_Product cart_Product = new Cart_Product();
            
            cart_Product.setProductId(productId);
            cart_Product.setQuantity(quantityOrder);
            cart_Products.add(cart_Product);
            //date cart_product
            cart_Product.setDateUpdated(currentDateForCart_Product);
            
            newCart.getCart_Products().addAll(cart_Products);
            
            // date cart
            newCart.setDateUpdated(new java.sql.Date(System.currentTimeMillis()));
           
            
            cartDB.addNewCartForNewCustomer(newCart);
        } else {
            boolean isNewProduct = true;

            ArrayList<Cart_Product> products = cart.getCart_Products();

            for (int i = 0; i < products.size(); i++) {
                if (products.get(i).getProductId() == productId) {
                    int oldQuantity = products.get(i).getQuantity();
                    int newQuantity = oldQuantity + quantityOrder;

                    if (newQuantity > product_quatinty_contain) {
                        products.get(i).setQuantity(product_quatinty_contain);
                    } else {
                        products.get(i).setQuantity(newQuantity);
                    }
                    
                    // update date cart_product
                    products.get(i).setDateUpdated(currentDateForCart_Product);
                    isNewProduct = false;
                    break;
                }
            }

            if (isNewProduct == true) {
                Cart_Product product = new Cart_Product();
                product.setCartId(cart.getId());
                product.setProductId(productId);
                product.setQuantity(quantityOrder);
                product.setDateUpdated(currentDateForCart_Product);
                products.add(product);
            }

            cart.setDateUpdated(new java.sql.Date(System.currentTimeMillis()));
            cartDB.updateCart(cart);
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
