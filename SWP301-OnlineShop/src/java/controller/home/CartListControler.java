/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.home;

import dal.CartDBContext;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Cart;
import model.User;

/**
 *
 * @author Hoang Quang
 */
@WebServlet(name = "CartListControler", urlPatterns = {"/cartlist"})
public class CartListControler extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //get list cart of user with id form DB
//        CartDBContext cartDBContext = new CartDBContext();
//        HttpSession session = request.getSession();
//        
//        //get user Id
//        User user = (User) session.getAttribute("user");
//        int userID = user.getId();
//        
//        //get value from paramester;
//        String page = request.getParameter("page");
//
//        //validate value
//        if (page == null || page.trim().length() == 0) {
//            page = "1";
//        }
//        int pagesize = 10;
//        int pageindex = Integer.parseInt(page);
//
//        int numofrecords = cartDBContext.countTotalOfListCart(userID);
//        int totalpage = (numofrecords % pagesize == 0) ? (numofrecords / pagesize)
//                : (numofrecords / pagesize) + 1;
//
//        ArrayList<Cart> listCarts = cartDBContext.getListCartByUserID(userID, pageindex, pagesize);
//
//        System.out.println(listCarts.size());
//        System.out.println(totalpage);
//        System.out.println(userID);
//
//        //pass infomation jsp
//        request.setAttribute("listCarts", listCarts);
//        request.setAttribute("pagesize", pagesize);
//        request.setAttribute("pageindex", pageindex);
//        request.setAttribute("totalpage", totalpage);
//        request.getRequestDispatcher("view/public/cart.jsp").forward(request, response);
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
