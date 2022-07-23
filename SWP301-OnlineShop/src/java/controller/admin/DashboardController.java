/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.admin;

import dal.DashboardDBContext;
import filter.BaseAuthController;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.User;

/**
 *
 * @author Admin
 */
public class DashboardController extends BaseAuthController {

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
        DashboardDBContext dashboardDB = new DashboardDBContext();
        request.setAttribute("successCount", dashboardDB.getSuccessOrders());
        request.setAttribute("cancelledCount", dashboardDB.getCancelledOrders());
        request.setAttribute("processingCount", dashboardDB.getProcessingOrders());
        request.setAttribute("newCustomer", dashboardDB.getNewCustomer());
        request.setAttribute("newBought", dashboardDB.getNewCustomerBought());

        try {
            String reStartTime = request.getParameter("reStartTime");
            String reEndTime = request.getParameter("reEndTime");
            String trendStartTime = request.getParameter("trendStartTime");
            String trendEndTime = request.getParameter("trendEndTime");

            if (reStartTime != null && reEndTime != null) {
                request.setAttribute("totalRevenue", dashboardDB.getTotalRevenue(reStartTime, reEndTime));
                request.setAttribute("revenueByCategory", dashboardDB.getRevenueByProductCategory(reStartTime, reEndTime));
            } else {
                reStartTime = this.getDateMinus(-7); //Last 7-day
                reEndTime = this.getDateMinus(0); //Today
                request.setAttribute("totalRevenue", dashboardDB.getTotalRevenue(reStartTime, reEndTime));
                request.setAttribute("revenueByCategory", dashboardDB.getRevenueByProductCategory(reStartTime, reEndTime));
            }

            if (trendStartTime != null && trendEndTime != null) {
                request.setAttribute("SuccessOrdersRange", dashboardDB.getSuccessOrdersByDateRange(trendStartTime, trendEndTime));
                request.setAttribute("TotalOrdersRange", dashboardDB.getTotalOrdersByDateRange(trendStartTime, trendEndTime));
            } else {
                System.out.println("Case 2");
                trendStartTime = this.getDateMinus(-7); //Last 7-day
                trendEndTime = this.getDateMinus(0); //Today
                request.setAttribute("SuccessOrdersRange", dashboardDB.getSuccessOrdersByDateRange(trendStartTime, trendEndTime));
                request.setAttribute("TotalOrdersRange", dashboardDB.getTotalOrdersByDateRange(trendStartTime, trendEndTime));
            }
        } catch (Exception e) {
        }

        request.getRequestDispatcher("../view/admin/dashboard.jsp").forward(request, response);
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

    public String getDateMinus(int minusDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, minusDate);
        return sdf.format(cal.getTime());
    }
}
