/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.home;

import configs.SendMail;
import dal.CartDBContext;
import dal.OrderDBContext;
import dal.ProductCategoryDBContext;
import dal.ProductDBContext;
import dal.ProductListDBContext;
import dal.UserDBContext;
import filter.BaseAuthController;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Category;
import model.Product;
import model.User;

/**
 *
 * @author DELL
 */
@WebServlet(name = "CartCompletionController", urlPatterns = {"/cartCompletion"})
public class CartCompletionController extends BaseAuthController {

    @Override
    protected void processGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void processPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        // sider
        ProductCategoryDBContext productCategoryDBContext = new ProductCategoryDBContext();
        ProductListDBContext productListDB = new ProductListDBContext();
        ProductDBContext productDB = new ProductDBContext();

        //get Parameter value
        String raw_subCategory = request.getParameter("subCategory");

        //get list subcategory
        ArrayList<Category> listCategorys = productCategoryDBContext.getAllCategory();

        //check subCategory
        if (raw_subCategory == null || raw_subCategory.length() == 0 || raw_subCategory.equals("-1")) {
            raw_subCategory = "0";
        }
        int subCategory = Integer.parseInt(raw_subCategory);

        // latest product
        ArrayList<Product> leastProduct = productListDB.getListLeastProduct();
        User user = (User) request.getSession().getAttribute("user");

        // get info bank
        String nameBank = getServletContext().getInitParameter("NameOfBank");
        String ownerAccount = getServletContext().getInitParameter("OwnerAccount");
        String accNumber = getServletContext().getInitParameter("AccountNumber");
        System.out.println(nameBank + " " + ownerAccount +" "+ accNumber);

        // get ship info
        String shipFullName = request.getParameter("txtFullname").trim();
        boolean shipGender = (request.getParameter("txtGender").trim().equalsIgnoreCase("true"))?true:false;
        String shipPhone = request.getParameter("txtPhone").trim();
        String shipAddress = request.getParameter("txtAddress").trim();
        String shipNote = request.getParameter("txtNote").trim();
        User infoCustomer = new User();
        infoCustomer.setFullname(shipFullName);
        infoCustomer.setMobile(shipPhone);
        infoCustomer.setAddress(shipAddress);
        infoCustomer.setGender(shipGender);
        infoCustomer.setEmail(user.getEmail());
        infoCustomer.setUsername(shipFullName);
        // get method payment
        String payment = request.getParameter("payment").trim();

        /*
            * isPayment:
            * 0: Payment on delivery
            * 1: Payment by bank
         */
        int idPayment = (payment.equalsIgnoreCase("delivery")) ? 0 : 1;
        
        // get product
        String[] idProducts_raw = request.getParameterValues("pr-id");
        String[] priceProducts_raw = request.getParameterValues("pr-price");
        String[] discountProducts_raw = request.getParameterValues("pr-discount");
        String[] quantityProducts_raw = request.getParameterValues("pr-quantity");
        String[] priceDiscountVnd = request.getParameterValues("pr-priceDiscountVnd");
        String[] nameProducts_raw = request.getParameterValues("pr-name");
        String totalVnd = request.getParameter("totalPriceVnd");
        
        Product[] productsOrder = new Product[idProducts_raw.length];
        for (int i = 0; i < productsOrder.length; i++) {
            Product pro = new Product();
            pro.setId(Integer.parseInt(idProducts_raw[i].trim()));
            pro.setName(nameProducts_raw[i].trim());
            pro.setPrice(Long.parseLong(priceProducts_raw[i].trim()));
            pro.setDiscount(Integer.parseInt(discountProducts_raw[i].trim()));
            pro.setQuantity(Integer.parseInt(quantityProducts_raw[i].trim()));

            productsOrder[i] = pro;
        }
        // list Product Order
        ArrayList<Product> listProduct = productListDB.getListProductById(productsOrder, user.getId());

        // total money
        long total = totalPrice(listProduct);
        

        // get last seller receive order
        OrderDBContext orderDB = new OrderDBContext();
        CartDBContext cartDB = new CartDBContext();
        UserDBContext userDB = new UserDBContext();

        int numberSeller = userDB.countNumberSeller();
        // get id of last seller receive order and index
        int lastSellerReceiveOrder = userDB.getLastSellerReceiveOrder();
        int indexSellerReceiveOrder = userDB.getindexSellerReceiveOrder(lastSellerReceiveOrder);

        // get next seller receive order
        int indexNextSellerReceiveOrder = 0;
        if (numberSeller == indexSellerReceiveOrder || indexSellerReceiveOrder == 0) {
            indexNextSellerReceiveOrder = 1;
        }
        else {
            indexNextSellerReceiveOrder = indexSellerReceiveOrder + 1;
        }
        int idNextSeller = userDB.getIdNextSeller(indexNextSellerReceiveOrder);

        int idCart = cartDB.getIdCartOfCustomer(user.getId());
        
        int idOrder = orderDB.addOrder(productsOrder, total, user.getId(), user.getEmail(), shipFullName, shipAddress, shipPhone, shipNote, idPayment, idNextSeller);
        
        if (idOrder > 0) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/uuuu");
            LocalDate localDate = LocalDate.now();
            
            // send mail
            SendMail.sendMailOrder(user.getEmail(), idOrder, idPayment, productsOrder, priceDiscountVnd,infoCustomer, totalVnd, dtf.format(localDate), nameBank, accNumber, ownerAccount);
            
            // update quantity product available
            productDB.updateQuantityProductAvailable(productsOrder);
            
            //update new information user
            userDB.updateUserInf(infoCustomer);
            // delete product ordered in cart
            cartDB.deleteProductOrdered(productsOrder, idCart);
            
            
            System.out.println("success");
            request.setAttribute("payment", idPayment);
            request.setAttribute("fullname", shipFullName);
            request.setAttribute("mobile", shipPhone);
            request.setAttribute("address", shipAddress);
            request.setAttribute("note", shipNote);
            request.setAttribute("listCategorys", listCategorys);
            request.setAttribute("subCategory", subCategory);
            request.setAttribute("listProduct", listProduct);
            request.setAttribute("leastProduct", leastProduct);
            request.setAttribute("email", user.getEmail());
            request.setAttribute("total", total);

            request.getRequestDispatcher("view/public/cartCompletion.jsp").forward(request, response);
        } else {
            System.out.println("error");
            request.getRequestDispatcher("404.html").forward(request, response);
        }

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private long totalPrice(ArrayList<Product> listProduct) {
        long sum = 0;
        for (Product product : listProduct) {
            sum += product.getPriceDiscount() * product.getQuantity();
        }
        return sum;
    }

}
