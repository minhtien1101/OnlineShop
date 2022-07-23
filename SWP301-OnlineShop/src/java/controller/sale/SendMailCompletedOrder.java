/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.sale;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import configs.SendMail;
import dal.OrderDBContext;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Product;

/**
 *
 * @author Hoang Quang
 */
@WebServlet(name = "SendMailCompletedOrder", urlPatterns = {"/send/completedOrder"})
public class SendMailCompletedOrder extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        OrderDBContext orderDB = new OrderDBContext();
        int orderid = Integer.parseInt(request.getParameter("orderid"));
        int status = Integer.parseInt(request.getParameter("status"));

        String emailUserBuy = request.getParameter("emailUserBuy");

        if (status == 4) {
            ArrayList<Product> listOrderProductOfUser = orderDB.getListOrderProductOfUser(orderid);
            SendMail.sendMailFeedbackCompleted(emailUserBuy, listOrderProductOfUser);
//            System.out.println("Order ID: " + orderid);
//            System.out.println("Status: " + status);
//            System.out.println("Mail: " + emailUserBuy);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
