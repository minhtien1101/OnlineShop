/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.user;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dal.OrderDBContext;
import dal.ProductCategoryDBContext;
import dal.ProductListDBContext;
import filter.BaseAuthController;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
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
public class UserOrdersController extends BaseAuthController {

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
        ProductCategoryDBContext productCategoryDBContext = new ProductCategoryDBContext();
        ProductListDBContext productListDBContext = new ProductListDBContext();
        User u = (User) request.getSession().getAttribute("user");
        //get list subcategory
        ArrayList<Category> listCategorys = productCategoryDBContext.getAllCategory();
        //get least post
        ArrayList<Product> leastProduct = productListDBContext.getListLeastProduct();
        String startDate = request.getParameter("startTime");
        String endDate = request.getParameter("endTime");
        if(startDate != null && endDate != null) {
            OrderDBContext orderDB = new OrderDBContext();
            ArrayList<Order> orders = orderDB.getUserOrders(u.getId(), startDate, endDate);
            request.setAttribute("orders", orders);
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            String currentDate = formatter.format(date);
            OrderDBContext orderDB = new OrderDBContext();
            ArrayList<Order> orders = orderDB.getUserOrders(u.getId(), currentDate, currentDate);
            request.setAttribute("orders", orders);
        }
        request.setAttribute("listCategorys", listCategorys);
        request.setAttribute("leastProduct", leastProduct);
        request.getRequestDispatcher("./view/public/myorders.jsp").forward(request, response);
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
        try {
//            String requestBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
//            JsonElement jelement = new JsonParser().parse(requestBody);
//            JsonObject jobject = jelement.getAsJsonObject();
//            String startDate = jobject.get("startTime").getAsString();
//            String endDate = jobject.get("endTime").getAsString();
            String startDate = request.getParameter("startTime");
            String endDate = request.getParameter("endTime");
            User u = (User) request.getSession().getAttribute("user");
            OrderDBContext orderDB = new OrderDBContext();
            ArrayList<Order> orders = orderDB.getUserOrders(u.getId(), startDate, endDate);
            if (orders != null) {
                response.getWriter().println(new GsonBuilder().setPrettyPrinting().create().toJson(orders).toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
            JsonObject json = new JsonObject();
            json.addProperty("Code", 500);
            json.addProperty("Msg", "An error has occured!" + e.getMessage());
            response.setStatus(500);
            response.getWriter().println(new GsonBuilder().setPrettyPrinting().create().toJson(json).toString());
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
