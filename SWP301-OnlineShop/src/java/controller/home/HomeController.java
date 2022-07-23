/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.home;

import dal.PostDBContext;
import dal.ProductDBContext;
import dal.SliderDBContext;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Post;
import model.Product;
import model.Slider;

/**
 *
 * @author DELL
 */

public class HomeController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ProductDBContext productDB = new ProductDBContext();
        SliderDBContext sliderDB = new SliderDBContext();
        PostDBContext postDB = new PostDBContext();
        
        ArrayList<Slider> listSliderActive = sliderDB.getSlidersActive();
        ArrayList<Product> listProductFeatured = productDB.getProductsFeatured();
        ArrayList<Post> listHotPost = postDB.getHotPost();
        ArrayList<Post> listLatestPost = postDB.getLatestPost();
        
        int numberOfSliders = listSliderActive.size();
        
        request.setAttribute("listSliderActive", listSliderActive);
        request.setAttribute("numberOfSliders", numberOfSliders);
        request.setAttribute("listProductFeatured", listProductFeatured);
        request.setAttribute("listHotPost", listHotPost);
        request.setAttribute("listLatestPost", listLatestPost);
        request.setAttribute("active", "home");
        
        request.getRequestDispatcher("view/public/home.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
