/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.marketing;

import configs.Security;
import static configs.Security.SIZE_PAGE_SLIDER_LIST;
import dal.SliderDBContext;
import filter.BaseAuthController;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Slider;

/**
 *
 * @author Admin
 */
public class SliderListController extends BaseAuthController {

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
        SliderDBContext sliderDb = new SliderDBContext();
        String search = "";
        int status = -1, index = 1, searchBy = 1;
        

        //Get data from filter input
        if (request.getParameter("txtSearch") != null) {
            search = request.getParameter("txtSearch").trim().toLowerCase();
        }
        if (request.getParameter("select") != null) {
            status = Integer.parseInt(request.getParameter("select"));
        }
        
        if (request.getParameter("select-search") != null) {
            searchBy = Integer.parseInt(request.getParameter("select-search"));
        }
        
        

        //Get current page from view
        if (request.getParameter("index") != null) {
            index = Integer.parseInt(request.getParameter("index"));
        }

        //Get List Slider
        ArrayList<Slider> lstSlider = sliderDb.getSliderByIndex(index, SIZE_PAGE_SLIDER_LIST, status, search, searchBy);
        
        //Calculator Last page
        int sizeOfList = sliderDb.searchSliders(status, search, searchBy).size();
        int lastPage = sizeOfList / SIZE_PAGE_SLIDER_LIST;
        if (sizeOfList % SIZE_PAGE_SLIDER_LIST != 0) {
            lastPage++;
        }

        //Send result filter after search
        request.setAttribute("search", search);
        request.setAttribute("status", status);
        request.setAttribute("searchBy", searchBy);
        //Send Last page of slider list
        request.setAttribute("lastPage", lastPage);
        //Send slider List
        request.setAttribute("sliders", lstSlider);
        //Send index 
        request.setAttribute("index", index);
        
        request.getRequestDispatcher("/view/marketing/sliders.jsp").forward(request, response);
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
            
            boolean rs = false;
            String status = "", strId = "";
            SliderDBContext sliderDb = new SliderDBContext();
            int id = 0;
            status = request.getParameter("status");
            strId = request.getParameter("id");
            id = Integer.parseInt(strId);
            boolean s = Boolean.parseBoolean(status);
            if (s == false) {
                rs = sliderDb.changeStatus(id, Security.SHOW_STATUS);
            } else {
                rs = sliderDb.changeStatus(id, Security.HIDE_STATUS);
            }
            
        } catch (Exception e) {
        }

        
//        processRequest(request, response);

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
