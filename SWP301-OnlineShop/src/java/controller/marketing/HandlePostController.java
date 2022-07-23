/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.marketing;

import dal.CategoryPostDBContext;
import dal.FeedbackDBContext;
import dal.PostDBContext;
import filter.BaseAuthController;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.CategoryPost;
import model.SubCategoryPost;

/**
 *
 * @author DELL
 */
@WebServlet(name = "EditStatusPostController", urlPatterns = {"/marketing/handlePost"})
public class HandlePostController extends BaseAuthController {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String action = request.getParameter("action");
        if (action.trim().equalsIgnoreCase("status")) {
            int id = Integer.parseInt(request.getParameter("idPost"));
            String nameStatus = (request.getParameter("idStatus").trim().equalsIgnoreCase("show")) ? "Hide" : "Show";
            String btnStatus = (nameStatus.equalsIgnoreCase("show")) ? "btn-success" : "btn-danger";
            PostDBContext postDB = new PostDBContext();
            boolean isStatus = (nameStatus.trim().equalsIgnoreCase("show"));
            postDB.editStatusPost(id, isStatus);
            try ( PrintWriter out = response.getWriter()) {
                /* TODO output your page here. You may use following sample code. */
                out.println("<button id=\"btn-status-" + id + "\" type=\"button\" class=\"btn " + btnStatus + "\" data-toggle=\"modal\" data-target=\"#active\" onclick=\"openModals('" + id + "')\">\n"
                        + "                                                        " + nameStatus + "\n"
                        + "                                                    </button>");

            }
        } else if (action.trim().equalsIgnoreCase("featured")) {
            int id = Integer.parseInt(request.getParameter("idPost"));
            String nameFeatured = (request.getParameter("idFeatured").trim().equalsIgnoreCase("on")) ? "Off" : "On";
            String btnFeatured = (nameFeatured.equalsIgnoreCase("on")) ? "btn-success" : "btn-danger";
            PostDBContext postDB = new PostDBContext();
            boolean isFeatured = (nameFeatured.trim().equalsIgnoreCase("on"));
            postDB.editFeaturedPost(id, isFeatured);
            try ( PrintWriter out = response.getWriter()) {
                /* TODO output your page here. You may use following sample code. */
                out.println("<button id=\"btn-featured-" + id + "\" type=\"button\" class=\"btn " + btnFeatured + "\" data-toggle=\"modal\" data-target=\"#active\" onclick=\"openAnnouceAccept('" + id + "')\">\n"
                        + "                                                        " + nameFeatured + "\n"
                        + "                                                    </button>");

            }
        } else if (action.trim().equalsIgnoreCase("subcategory")) {
            int idCategory = Integer.parseInt(request.getParameter("idCategory"));
            CategoryPostDBContext categoryDB = new CategoryPostDBContext();
            ArrayList<SubCategoryPost> listSubCategoryPost = categoryDB.getListSubCategoryById(idCategory);
            try ( PrintWriter out = response.getWriter()) {
                /* TODO output your page here. You may use following sample code. */
                for (SubCategoryPost subCategoryPost : listSubCategoryPost) {
                    out.println("<option value=\"" + subCategoryPost.getId() + "\">" + subCategoryPost.getName() + "</option>");
                }
            }
        } else if (action.trim().equalsIgnoreCase("addNewSubCategory")) {
            int idCategory = Integer.parseInt(request.getParameter("idCategory"));
            String nameSubCategory = request.getParameter("nameSubCategory");
            CategoryPostDBContext categoryDB = new CategoryPostDBContext();
            categoryDB.addSubCategory(idCategory, nameSubCategory);
        } else if (action.trim().equalsIgnoreCase("addNewCategory")) {
            String nameCategory = request.getParameter("nameCategory");
            String nameSubCategory = request.getParameter("nameSubCategory");
            CategoryPostDBContext categoryDB = new CategoryPostDBContext();
            CategoryPost categoryPost = categoryDB.addCategoryPost(nameCategory, nameSubCategory);
            try ( PrintWriter out = response.getWriter()) {
                /* TODO output your page here. You may use following sample code. */
                out.println("<option value=\"" + categoryPost.getId() + "\">" + categoryPost.getName() + "</option>");
                System.out.println("<option value=\"" + categoryPost.getId() + "\">" + categoryPost.getName() + "</option>");
            }
        } else if (action.trim().equalsIgnoreCase("editStatusFeedback")) {
            FeedbackDBContext feedbackDB = new FeedbackDBContext();
            int id = Integer.parseInt(request.getParameter("idPost"));
            String nameStatus = (request.getParameter("idStatus").trim().equalsIgnoreCase("show")) ? "Hide" : "Show";
            String btnStatus = (nameStatus.equalsIgnoreCase("show")) ? "btn-success" : "btn-danger";
            boolean isStatus = (nameStatus.trim().equalsIgnoreCase("show"));
            feedbackDB.editStatusFeedback(id, isStatus);
            try ( PrintWriter out = response.getWriter()) {
                /* TODO output your page here. You may use following sample code. */
                out.println("<button id=\"btn-status-" + id + "\" type=\"button\" class=\"btn " + btnStatus + "\" data-toggle=\"modal\" data-target=\"#active\" onclick=\"openModals('" + id + "')\">\n"
                        + "                                                        " + nameStatus + "\n"
                        + "                                                    </button>");
            }
        }
    }

    @Override
    protected void processGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

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
