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
public class BlogDetailController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        String page = request.getParameter("page");
        String idCategory_raw = request.getParameter("category");
        String searchContent = request.getParameter("search");
        String subCategory_raw = request.getParameter("subcategory");
        
        if(searchContent == null || searchContent.length() == 0) {
            searchContent = "";
        }
        
        if(idCategory_raw == null || idCategory_raw.length() == 0) {
            idCategory_raw = "-1";
        }
        
        if(page == null || page.length() == 0) {
            page = "1";
        }
        
        if(subCategory_raw == null || subCategory_raw.length() == 0) {
            subCategory_raw = "-1";
        }
        // blog detail
        int idCategory = Integer.parseInt(idCategory_raw);
        int subCategory = Integer.parseInt(subCategory_raw);
        PostDBContext postDB = new PostDBContext();
        
        int blogId = Integer.parseInt(request.getParameter("blogId"));
        Post blog = postDB.getPostById(blogId);
        String[] splitContent = blog.getDescription().split("\n");
        ArrayList<String> content = new ArrayList<>();
        for(String s : splitContent){
            content.add(s);
        }
        request.setAttribute("content", content);
        request.setAttribute("blog", blog);

        
        int pageIndex = Integer.parseInt(page);
        int totalPosts = postDB.numberRowListPost(searchContent, idCategory);
        int pageSize = 3;
        int totalPage = (totalPosts % pageSize == 0) ? (totalPosts / pageSize) : ((totalPosts / pageSize) + 1);
        ArrayList<CategoryPost> listAllCateogry = postDB.getAllCategory();
        ArrayList<Post> listPostFiltered = postDB.getListPostFiltered(searchContent, idCategory, pageIndex, pageSize);
        ArrayList<Post> listLatestPost = postDB.getLatestPost();
        request.setAttribute("searchContent", searchContent);
        request.setAttribute("category", idCategory);
        request.setAttribute("subCategory", subCategory);
        request.setAttribute("totalPage", totalPage);
        request.setAttribute("page", page);
        request.setAttribute("listPostFiltered", listPostFiltered);
        request.setAttribute("listLatestPost", listLatestPost);
        request.setAttribute("listAllCateogry", listAllCateogry);
        request.setAttribute("active", "blog");
        request.getRequestDispatcher("view/public/blogDetail.jsp").forward(request, response);

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
