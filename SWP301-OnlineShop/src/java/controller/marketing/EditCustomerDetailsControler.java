/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.marketing;

import dal.CustomerDBContext;
import filter.BaseAuthController;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.time.LocalDate;
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
@WebServlet(name = "EditCustomerDetailsControler", urlPatterns = {"/customer/editDetails"})
public class EditCustomerDetailsControler extends BaseAuthController {

    public static String ALERT = "";

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

        if (ALERT.trim().length() > 0 || ALERT != "") {
            request.setAttribute("alert", "Edit Susscessfully!");
        }
        ALERT = "";
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
        request.getRequestDispatcher("/view/marketing/customerDetails.jsp").forward(request, response);
    }

    @Override
    protected void processPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        ALERT = "success";
        //get value after choice Edit
        String raw_id = request.getParameter("customerID");
        String fullname = request.getParameter("fullname");
        String email = request.getParameter("email");
        String mobile = request.getParameter("mobile");
        String address = request.getParameter("address");
        String raw_gender = request.getParameter("gender");
        String updateBy = request.getParameter("updateBy");
//        String updateBy = "MrChekc";

        //validate value
        int customerID = Integer.parseInt(raw_id);
        boolean gender = raw_gender.equals("male");

        LocalDate updateDate_raw = java.time.LocalDate.now();
        Date updateDate = Date.valueOf(updateDate_raw);

        User customer = new User();
        customer.setId(customerID);
        customer.setEmail(email);
        customer.setAddress(address);
        customer.setFullname(fullname);
        customer.setGender(gender);
        customer.setMobile(mobile);
        CustomerDBContext customerDBContext = new CustomerDBContext();

        User_Update update = new User_Update();
        update.setEmail(email);
        update.setUpdateBy(updateBy);
        update.setUpdateDate(updateDate);
        update.setUserId(customerID);
        update.setFullname(fullname);
        update.setGender(gender);
        update.setMobile(mobile);
        update.setAddress(address);
        customerDBContext.editCustomer(customer);
        customerDBContext.addHistoryEditCustomer(update);

        response.sendRedirect("../customer/editDetails?id=" + customerID);
    }
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
