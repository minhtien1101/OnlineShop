/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.marketing;

import dal.CategoryPostDBContext;
import dal.PostDBContext;
import dal.UserDBContext;
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
import model.Post;
import model.User;

/**
 *
 * @author DELL
 */
@WebServlet(name = "PostListController", urlPatterns = {"/marketing/postlist"})
public class PostListController extends BaseAuthController {

    public static String ALERT = "";

    @Override
    protected void processGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idCategoryStr = request.getParameter("category");
        String status = request.getParameter("status");
        String sortBy = request.getParameter("sort");
        String orderBy = request.getParameter("orderBy");
        String searchBy = request.getParameter("search");
        String pageStr = request.getParameter("page");
        if (idCategoryStr == null || idCategoryStr.trim().length() == 0) {
            idCategoryStr = "-1";
        }
        if (status == null || status.trim().length() == 0) {
            status = "-1";
        }
        if (sortBy == null || sortBy.trim().length() == 0) {
            sortBy = "title";
        }
        if (orderBy == null || orderBy.trim().length() == 0) {
            orderBy = "asc";
        }
        if (searchBy == null || searchBy.trim().length() == 0) {
            searchBy = "";
        }
        if (pageStr == null || pageStr.trim().length() == 0) {
            pageStr = "1";
        }
        PostDBContext postDB = new PostDBContext();
        int idCategory = Integer.parseInt(idCategoryStr);
        int idStatus = Integer.parseInt(status);
        int page = Integer.parseInt(pageStr);

        CategoryPostDBContext categoryPostDB = new CategoryPostDBContext();
        ArrayList<CategoryPost> listCateogry = categoryPostDB.getAllCategoryPost();
        
        ArrayList<Post> listPosts = postDB.getAllPostFiltered(idCategory, -1, idStatus, searchBy, sortBy, orderBy);
        int numbersRowPerPage = 5;
        int totalPage = ((listPosts.size() % numbersRowPerPage) == 0)
                ? (listPosts.size() / numbersRowPerPage)
                : ((listPosts.size() / numbersRowPerPage) + 1);
        int start = (page - 1) * numbersRowPerPage;
        int end = Math.min(page * numbersRowPerPage, listPosts.size());
        ArrayList<Post> listPagingFiltered = postDB.listPostPaging(listPosts, start, end);

        if(!ALERT.equalsIgnoreCase("")) {
            if(ALERT.equalsIgnoreCase("success")) {
                request.setAttribute("success", "Add New Post Successfully!");
            } else if(ALERT.equalsIgnoreCase("success2")) {
                request.setAttribute("success", "Edit Post Successfully!");
            } else if(ALERT.equalsIgnoreCase("failed")) {
                request.setAttribute("failed", "Add New Post Failed!");
            } else {
                request.setAttribute("failed", "Edit Post Failed!");
            }
        }
        ALERT = "";
        request.setAttribute("idCategory", idCategory);
        request.setAttribute("idStatus", idStatus);
        request.setAttribute("searchContent", searchBy);
        request.setAttribute("sortBy", sortBy);
        request.setAttribute("orderBy", orderBy);
        request.setAttribute("listPosts", listPagingFiltered);
        request.setAttribute("listCateogry", listCateogry);
        request.setAttribute("page", page);
        request.setAttribute("totalPage", totalPage);
        request.getRequestDispatcher("../view/marketing/postList.jsp").forward(request, response);
    }

    @Override
    protected void processPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
