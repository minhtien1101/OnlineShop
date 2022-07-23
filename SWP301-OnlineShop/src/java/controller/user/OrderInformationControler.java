/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.user;

import dal.OrderDBContext;
import dal.ProductCategoryDBContext;
import dal.ProductListDBContext;
import filter.BaseAuthController;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Category;
import model.Order;
import model.Product;
import model.User;

/**
 *
 * @author Hoang Quang
 */
@WebServlet(name = "OrderInformationControler", urlPatterns = {"/orderInfor"})
public class OrderInformationControler extends BaseAuthController { 
    
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

        HttpSession session = request.getSession();
        ProductCategoryDBContext productCategoryDBContext = new ProductCategoryDBContext();
        ProductListDBContext productListDBContext = new ProductListDBContext();
        OrderDBContext orderDBContext = new OrderDBContext();
        
        User user = (User) session.getAttribute("user");
        int userBuyId = user.getId();

        //get value from request
        String raw_orderID = request.getParameter("orderID");
        //validate value
        int orderID = Integer.parseInt(raw_orderID);
        
        //GET SIDER INFOR
        //get list subcategory
        ArrayList<Category> listCategorys = productCategoryDBContext.getAllCategory();
        //get least post
        ArrayList<Product> leastProduct = productListDBContext.getListLeastProduct();

        //GET ORDER ID, ORDER DATE, Total, status
        Order informationOrder = orderDBContext.getInformationOfOrderByID(orderID);
        //GET RECIVER INFOR OF USER
        User userOrderInfioramtion = orderDBContext.getUserOrderInformation(orderID);
        //GET LIST ORDERED BY ORDER ID
        ArrayList<Order> listOrderProductOfUser = orderDBContext.getListOrderProductOfUser2(orderID);

        String reasionCancel = orderDBContext.getReasionCancel(orderID);
//        System.out.println("Cancel Reasion" + listOrderProductOfUser.size());
        
        String alter = request.getParameter("alter");
        request.setAttribute("alter", alter);
        request.setAttribute("reasionCancel", reasionCancel);
        request.setAttribute("listOrderProductOfUser", listOrderProductOfUser);
        request.setAttribute("informationOrder", informationOrder);
        request.setAttribute("userOrderInfioramtion", userOrderInfioramtion);
        request.setAttribute("listCategorys", listCategorys);
        request.setAttribute("leastProduct", leastProduct);
        request.getRequestDispatcher("/view/public/orderInfor.jsp").forward(request, response);
    }

    @Override
    protected void processGet(HttpServletRequest request, HttpServletResponse response)
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
    protected void processPost(HttpServletRequest request, HttpServletResponse response)
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
