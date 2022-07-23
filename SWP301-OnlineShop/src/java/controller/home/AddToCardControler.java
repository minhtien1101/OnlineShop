///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
// */
//package controller.home;
//
//import dal.ProductCategoryDBContext;
//import dal.ProductListDBContext;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.util.ArrayList;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import model.Cart;
//import model.Category;
//import model.Feedback;
//import model.Product;
//import model.User;
//
///**
// *
// * @author Hoang Quang
// */
//@WebServlet(name = "AddToCardControler", urlPatterns = {"/addcart"})
//public class AddToCardControler extends HttpServlet {
//
//    public static String ALERT = "";
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        ProductCategoryDBContext productCategoryDBContext = new ProductCategoryDBContext();
//        ProductListDBContext productListDBContext = new ProductListDBContext();
//
//        //get list subcategory
//        ArrayList<Category> listCategorys = productCategoryDBContext.getAllCategory();
//
//        //get least post
//        ArrayList<Product> leastProduct = productListDBContext.getListLeastProduct();
//
//        //GET PRODUCT DETAILS, FEEDBACK OF THAT PRODUCT
//        //get value from jsp
//        String raw_productID = request.getParameter("productID");
//
//        // validate value
//        int productID = Integer.parseInt(raw_productID);
//        if (ALERT.trim().length() > 0 || ALERT != "") {
//            request.setAttribute("alert", "Add to cart successful!");
//        }
//        ALERT = "";
//        //get product with that id
//        Product productInfomation = productListDBContext.getProductByID(productID);
//        //get all feedback of that product
//        //pass to jsp
//        request.setAttribute("productInfomation", productInfomation);
//        request.setAttribute("listCategorys", listCategorys);
//        request.setAttribute("leastProduct", leastProduct);
//        request.getRequestDispatcher("/view/public/addToCart.jsp").forward(request, response);
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        ProductListDBContext productListDBContext = new ProductListDBContext();
//        HttpSession session = request.getSession();
//        ALERT = "success";
//        //get user Id
//        User user = (User) session.getAttribute("user");
////        int userBuyId = user.getId();
//        int userBuyId = 76;
//
//        String productName = request.getParameter("productName");
//        String raw_productId = request.getParameter("productId");
//        String raw_quantity = request.getParameter("quantity");
//        String raw_quantityOrder = request.getParameter("quantityOrder");
//        String raw_price = request.getParameter("price");
//        String raw_sellerId = request.getParameter("sellerId");
//        String thumbnail = request.getParameter("thumbnail");
//        String alert = request.getParameter("alert");
//
//        //validate value
//        int productId = Integer.parseInt(raw_productId);
//        int quantity = Integer.parseInt(raw_quantity);
//        int quantityOrder = Integer.parseInt(raw_quantityOrder);
//        long price = Integer.parseInt(raw_price);
//        int sellerId = Integer.parseInt(raw_sellerId);
//
//        Product product = new Product();
//        product.setId(productId);
//        product.setName(productName);
//
////        Cart cart = new Cart();
////        cart.setProduct(product);
////        cart.setQuantityOrder(quantityOrder);
////        cart.setPrice(price);
////
////        User userBuyId2 = new User();
////        userBuyId2.setId(userBuyId);
////
////        cart.setUserBuy(userBuyId2);
////
////        User userSeller = new User();
////        userSeller.setId(sellerId);
////
////        cart.setUserSeller(userSeller);
////        cart.setThumbnail(thumbnail);
////
////        //check userBuyId exist or not
////        //if exist, tang so quantity order, tang gia theo gia moi nhat
////        //if not, add new information
////        boolean check = true;
////        ArrayList<Cart> productIdList = productListDBContext.getAllProductIdInCart();
////        for (Cart cartlist : productIdList) {
////            //check userBuyId exist or not
////            if (cartlist.getUserBuy().getId() == userBuyId && cartlist.getProduct().getId() == productId) {
////                //update infomation here
////                productListDBContext.editQuantityOrderOfCart(quantityOrder, productId, userBuyId);
////                check = false;
////            }
////        }
////        //if dont have any userBuyId in list, add new infomation
////        if (check) {
////            productListDBContext.addNewCartInfomation(cart);
////        }
////        response.sendRedirect("addcart?productID=" + productId);
//    }
//
//    /**
//     * Returns a short description of the servlet.
//     *
//     * @return a String containing servlet description
//     */
//    @Override
//    public String getServletInfo() {
//        return "Short description";
//    }// </editor-fold>
//
//}
