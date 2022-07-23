/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.sale;

import dal.OrderDBContext;
import dal.ProductCategoryDBContext;
import dal.ProductListDBContext;
import dal.UserDBContext;
import filter.BaseAuthController;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Category;
import model.Order;
import model.Product;
import model.User;

/**
 *
 * @author Admin
 */
@WebServlet(name = "OrderListController", urlPatterns = {"/sale/orderslist"})
public class OrderListController extends BaseAuthController {

    private static int rawSaleId = 0;
    private static int rawStatus = 5;
    private static String rawStartTime = getCurrentDate();
    private static String rawEndTime = getCurrentDate();

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
        response.setContentType("text/html;charset=UTF-8");
        try {
            OrderDBContext orderDB = new OrderDBContext();
            UserDBContext userDB = new UserDBContext();
            ArrayList<Order> orders = null;
            rawStartTime = request.getParameter("startTime");
            rawEndTime = request.getParameter("endTime");
            rawSaleId = Integer.parseInt(request.getParameter("sale-filter"));
            rawStatus = Integer.parseInt(request.getParameter("status-filter"));
            //If user choose filter sale & status = all
            if (rawSaleId == 0 && rawStatus == 5) {
                orders = orderDB.getOrders(rawStartTime, rawEndTime);
            }
            //Filter Saleid & status = all
            if (rawSaleId != 0 && rawStatus == 5) {
                orders = orderDB.getOrders(rawStartTime, rawEndTime, rawSaleId);
            }
            //Not filter all
            if (rawSaleId != 0 && rawStatus != 5) {
                orders = orderDB.getOrders(rawStartTime, rawEndTime, rawSaleId, rawStatus);
            }
            if (rawSaleId == 0 && rawStatus != 5) {
                orders = orderDB.getOrdersByStatus(rawStartTime, rawEndTime, rawStatus);
            }
            ArrayList<User> sales = userDB.getSaleUser();
            request.setAttribute("orders", orders);
            request.setAttribute("sales", sales);
        } catch (Exception e) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            String currentDate = formatter.format(date);
            OrderDBContext orderDB = new OrderDBContext();
            UserDBContext userDB = new UserDBContext();
            ArrayList<User> sales = userDB.getSaleUser();
            request.setAttribute("sales", sales);
            ArrayList<Order> orders = orderDB.getOrders(currentDate, currentDate);
            request.setAttribute("orders", orders);
            request.setAttribute("sales", sales);
        }
        request.getRequestDispatcher("../view/sale/orderslist.jsp").forward(request, response);

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

    public static String getCurrentDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return formatter.format(date);
    }
}
