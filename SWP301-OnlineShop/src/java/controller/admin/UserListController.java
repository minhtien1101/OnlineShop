/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.admin;

import dal.RoleDBContext;
import dal.UserDBContext;
import filter.BaseAuthController;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Role;
import model.User;

/**
 *
 * @author Admin
 */
public class UserListController extends BaseAuthController {

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
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        User account = (User) session.getAttribute("account");
        UserDBContext userDB = new UserDBContext();
        RoleDBContext roleDB = new RoleDBContext();
        String action = request.getParameter("action");
        String message;
        String alter;
        ArrayList<User> listAllUser = new ArrayList<>();

        ArrayList<Role> roles = roleDB.getAllRole();
        request.setAttribute("roles", roles);

        int roleId;
        String gender, status, search, sort, orderBy;
        if (request.getParameter("roleId") == null) {
            roleId = 0;
        } else {
            roleId = Integer.parseInt(request.getParameter("roleId"));
        }
        if (request.getParameter("gender") == null) {
            gender = "all";
        } else {
            gender = request.getParameter("gender");
        }
        if (request.getParameter("status") == null) {
            status = "all";
        } else {
            status = request.getParameter("status");
        }
        if (request.getParameter("search") == null) {
            search = "";
        } else {
            search = request.getParameter("search");
        }
        if (request.getParameter("sort") == null) {
            sort = "id";
        } else {
            sort = request.getParameter("sort");
        }
        if (request.getParameter("orderBy") == null) {
            orderBy = "asc";
        } else {
            orderBy = request.getParameter("orderBy");
        }

//                ArrayList<User> users = userDB.getAllUser();
//        listAllUser = userDB.getListUserFilter(roleId, gender, status, search, sort, orderBy);
        int totalRecord = userDB.getListUserFilter(roleId, gender, status, search, sort, orderBy).size();
        int page, numberRecordPerPage = 5;
        int totalPage = totalRecord % numberRecordPerPage == 0
                ? totalRecord / numberRecordPerPage : totalRecord / numberRecordPerPage + 1;
        String currentPage = request.getParameter("xpage");
        if (currentPage == null) {
            page = 1;
        } else {
            page = Integer.parseInt(currentPage);
        }
//        listAllUser = userDB.getListUserFiltered(roleId, gender, status, search, 
//                sort, orderBy, page, numberRecordPerPage);
        listAllUser = userDB.getListUserFilter(roleId, gender, status, search, sort, orderBy);
        int startRecord = (page - 1) * numberRecordPerPage;
        int endRecord = Math.min(page * numberRecordPerPage, totalRecord);
        ArrayList<User> listPaging = userDB.getListByPage(listAllUser, startRecord, endRecord);

        // get alter
        alter = request.getParameter("alter");
        request.setAttribute("alter", alter);
        request.setAttribute("users", listPaging);
        request.setAttribute("roleId", roleId);
        request.setAttribute("gender", gender);
        request.setAttribute("status", status);
        request.setAttribute("search", search);
        request.setAttribute("sort", sort);
        request.setAttribute("orderBy", orderBy);
        request.setAttribute("page", page);
        request.setAttribute("totalPage", totalPage);
        request.getRequestDispatcher("../view/admin/userList.jsp").forward(request, response);
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
