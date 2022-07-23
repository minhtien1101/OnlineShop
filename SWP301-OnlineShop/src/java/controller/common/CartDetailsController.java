
package controller.common;

import static configs.Security.SIZE_PAGE_CART_LIST;
import dal.CartDBContext;
import dal.ProductCategoryDBContext;
import dal.ProductDBContext;
import dal.ProductListDBContext;
import filter.BaseAuthController;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Cart;
import model.Category;
import model.Product;
import model.User;

/**
 *
 * @author Admin
 */
public class CartDetailsController extends HttpServlet {

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
        request.getRequestDispatcher("view/public/cartDetails.jsp").forward(request, response);
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Declare and initialize the initial value
        ProductCategoryDBContext productCategoryDBContext = new ProductCategoryDBContext();
        ProductListDBContext productListDBContext = new ProductListDBContext();
        CartDBContext cartDBContext = new CartDBContext();
        String search = ""; int userID = 0;
        int index = 1;
        HttpSession session = request.getSession();

        //Get user login from session
        User user = (User) session.getAttribute("user");
        if (user != null) userID = user.getId();

        //Get data from input search
        if (request.getParameter("txtSearch") != null) {
            search = request.getParameter("txtSearch").trim();
        }

        //Get current page from view
        if (request.getParameter("index") != null) {
            index = Integer.parseInt(request.getParameter("index"));
        }

        //Get list subcategory
        ArrayList<Category> listCategorys = productCategoryDBContext.getAllCategory();

        //get least post
        ArrayList<Product> leastProduct = productListDBContext.getListLeastProduct();

        //Get List Cart 
        Cart cart = cartDBContext.getCartByIndexAndUserId(0, 0, search, userID);

        System.out.println(cart.getId());
        //Calculator Last page
        int sizeOfList = cart.getCart_Products().size();
        int lastPage = sizeOfList / SIZE_PAGE_CART_LIST;
        if (sizeOfList % SIZE_PAGE_CART_LIST != 0) {
            lastPage++;
        }

        //Set List Cart by current index
        cart = cartDBContext.getCartByIndexAndUserId(index, SIZE_PAGE_CART_LIST, search, userID);

        System.out.println(cart.getId());
        //Send result after search
        request.setAttribute("search", search);
        //Send Last page of cart list
        request.setAttribute("lastPage", lastPage);
        //Send cart List
        request.setAttribute("carts", cart.getCart_Products());
        //Send index 
        request.setAttribute("index", index);

        request.setAttribute("cartId", cart.getId());

        request.setAttribute("listCategorys", listCategorys);

        request.setAttribute("leastProduct", leastProduct);

        //Check user login when click to cart
        if (user != null) {
            processRequest(request, response);
        }else {
            response.sendRedirect("login");
        }
        
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        CartDBContext cartDb = new CartDBContext();
        PrintWriter out = response.getWriter();
        int quantity = 0;
        int pid = Integer.parseInt(request.getParameter("pid"));
        int isUp = Integer.parseInt(request.getParameter("isUp"));
        int cid = Integer.parseInt(request.getParameter("cartId"));
        if (request.getParameter("quantity") != null) {
            quantity = Integer.parseInt(request.getParameter("quantity"));
        }
        int currentQuantity = cartDb.getCartProductByCidAndPid(cid, pid).getQuantity();
        if (isUp == 1) {
            currentQuantity += 1;
        } else if (isUp == 0) {
            currentQuantity -= 1;
        } else if (quantity != 0) {
            currentQuantity = quantity;
        }

        cartDb.setQuantityCartProduct(pid, cid, currentQuantity);
        out.println("<input class=\"cart_quantity_input\" type=\"number\" value=\"" + currentQuantity + "\" autocomplete=\"off\" size=\"2\">");
        
      

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
