package controller.home;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
import dal.PostDBContext;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Post;
import model.CategoryPost;

/**
 *
 * @author DELL
 */
public class BlogListController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        PostDBContext postDB = new PostDBContext();
        
        String pageIndex_raw = request.getParameter("page");
        String idCategory_raw = request.getParameter("category");
        String searchContent = request.getParameter("search");
        String subCategory_raw = request.getParameter("subcategory");
        
        if(searchContent == null || searchContent.length() == 0) {
            searchContent = "";
        }
        
        if(idCategory_raw == null || idCategory_raw.length() == 0) {
            idCategory_raw = "-1";
        }
        
        if(pageIndex_raw == null || pageIndex_raw.length() == 0) {
            pageIndex_raw = "1";
        }
        
        if(subCategory_raw == null || subCategory_raw.length() == 0) {
            subCategory_raw = "-1";
        }
        
        int idCategory = Integer.parseInt(idCategory_raw);
        int subCategory = Integer.parseInt(subCategory_raw);
        int pageIndex = Integer.parseInt(pageIndex_raw);
        int totalPosts = postDB.numberRowListPost(searchContent, subCategory);
        int pageSize = 9;
        int totalPage = ((totalPosts % pageSize) == 0) ? (totalPosts / pageSize) : ((totalPosts / pageSize) + 1);
        
        ArrayList<CategoryPost> listAllCateogry = postDB.getAllCategory();
        ArrayList<Post> listPostFiltered = postDB.getListPostFiltered(searchContent, subCategory, pageIndex, pageSize);
        ArrayList<Post> listTopLatestPost = postDB.getLatestPost();
        
        request.setAttribute("searchContent", searchContent);
        request.setAttribute("idCategory", idCategory);
        request.setAttribute("idSubCategory", subCategory);
        request.setAttribute("totalPage", totalPage);
        request.setAttribute("pageIndex", pageIndex);
        request.setAttribute("listPostFiltered", listPostFiltered);
        request.setAttribute("listTopLatestPost", listTopLatestPost);
        request.setAttribute("listAllCateogry", listAllCateogry);
        request.setAttribute("active", "blog");
        request.getRequestDispatcher("view/public/bloglist.jsp").forward(request, response);

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
