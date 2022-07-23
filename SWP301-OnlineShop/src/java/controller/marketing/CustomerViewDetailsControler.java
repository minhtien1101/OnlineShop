/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.marketing;

import dal.CustomerDBContext;
import filter.BaseAuthController;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.User;
import model.User_Update;

/**
 *
 * @author Hoang Quang
 */
@WebServlet(name = "CustomerViewDetailsControler", urlPatterns = {"/customer/viewdetails"})
public class CustomerViewDetailsControler extends BaseAuthController {

        @Override
    protected void processGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                   CustomerDBContext customerDBContext = new CustomerDBContext();
            //get id from jsp, when user choice to Edit or View
            String raw_customerID = request.getParameter("id");
            String page = request.getParameter("page");

            //validate input data
            int customerID = Integer.parseInt(raw_customerID);
            if (page == null || page.trim().length() == 0) {
                page = "1";
            }
            int pageindex = Integer.parseInt(page);
            //get infomation of that user in database

            int pagesize = 10;
            int numofrecords = customerDBContext.countForUpdate(customerID);
            int totalpage = (numofrecords % pagesize == 0) ? (numofrecords / pagesize)
                    : (numofrecords / pagesize) + 1;

            User customer = customerDBContext.getCustomerByID(customerID);
            ArrayList<User_Update> listHistoryUpdate = customerDBContext.getListHistoryUpdate(customerID, pagesize, pageindex);


            //go to customerDetails.jsp
            request.setAttribute("pagesize", pagesize);
            request.setAttribute("pageindex", pageindex);
            request.setAttribute("totalpage", totalpage);
            request.setAttribute("customer", customer);
            request.setAttribute("listHistoryUpdate", listHistoryUpdate);
            request.getRequestDispatcher("/view/marketing/customerViewDetails.jsp").forward(request, response);
    }

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

}
