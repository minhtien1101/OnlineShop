/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Cart;
import model.Cart_Product;
import model.Product;
import model.User;

/**
 *
 * @author Hoang Quang
 */
public class CartDBContext extends DBContext {

//    public ArrayList<Cart> getListCartByUserID(int userID, int pageindex, int pagesize) {
//        ArrayList<Cart> listCarts = new ArrayList<>();
//        String sql = " with x as (select ROW_NUMBER() OVER (ORDER BY id desc) as r\n"
//                + "                    		, * from Cart WHERE userBuyId = ? )\n"
//                + "                    		SElECT* FROM x where r between (? - 1)* ? +1 and ? * ?  ";
//        try {
//            PreparedStatement ps = connection.prepareStatement(sql);
//            ps.setInt(1, userID);
//            ps.setInt(2, pageindex);
//            ps.setInt(3, pagesize);
//            ps.setInt(4, pageindex);
//            ps.setInt(5, pagesize);
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                Cart cart = new Cart();
//                cart.setId(rs.getInt("id"));
//
//                Product product = new Product();
//                product.setId(rs.getInt("productId"));
//                product.setName(rs.getString("productName"));
//                cart.setProduct(product);
//
//                cart.setPrice(rs.getLong("price"));
//
//                User userBuy = new User();
//                userBuy.setId(rs.getInt("userBuyId"));
//                cart.setUserBuy(userBuy);
//
//                User userSeller = new User();
//                userSeller.setId(rs.getInt("sellerId"));
//                cart.setUserSeller(userSeller);
//
//                cart.setThumbnail(rs.getString("thumbnail"));
//                cart.setQuantityOrder(rs.getInt("quantityOrder"));
//                
//                listCarts.add(cart);
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(CartDBContext.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return listCarts;
//    }
    public int countTotalOfListCart(int userID) {
        try {

            String sql1 = "with x as (select ROW_NUMBER() OVER (ORDER BY id desc) as r\n"
                    + "                    		, * from Cart WHERE userBuyId = ? )\n"
                    + "					SElECT COUNT(*) as Total FROM x ";

            PreparedStatement ps = connection.prepareStatement(sql1);
            ps.setInt(1, userID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("Total");
            }
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public Cart getCartByCustomerId(int customerId) {
        Cart cart = new Cart();
        try {
            connection.setAutoCommit(false);
            String sql = "select * from Cart where customerId = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                cart.setId(rs.getInt(1));
                User customer = new User();
                customer.setId(rs.getInt(2));
                cart.setCustomer(customer);
            }

            ArrayList<Cart_Product> cart_Products = new ArrayList<>();
            String sql1 = "select *  from [dbo].[Cart_Product] where cartId = ?";
            PreparedStatement ps1 = connection.prepareStatement(sql1);
            ps1.setInt(1, cart.getId());
            ResultSet rs1 = ps1.executeQuery();
            while (rs1.next()) {
                Cart_Product cart_Product = new Cart_Product();
                cart_Product.setCartId(rs1.getInt(1));
                cart_Product.setProductId(rs1.getInt(2));
                cart_Product.setQuantity(rs1.getInt(3));
                cart_Product.setDateUpdated(rs1.getTimestamp("DateUpdated"));

                cart_Products.add(cart_Product);
            }
            cart.getCart_Products().addAll(cart_Products);

            connection.commit();
            if (cart.getId() == 0) {
                return null;
            }
            return cart;
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(CartDBContext.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Logger.getLogger(CartDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(CartDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return null;
    }

    public void addNewCartForNewCustomer(Cart cart) {

        try {
            connection.setAutoCommit(false);
            String sql = "INSERT INTO [dbo].[Cart]\n"
                    + "           ([customerId]\n"
                    + "           ,[DateUpdated])\n"
                    + "     VALUES\n"
                    + "           (?\n"
                    + "           ,?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, cart.getCustomer().getId());
            ps.setDate(2, cart.getDateUpdated());
            ps.executeUpdate();

            int cartId = 0;
            String sql1 = "select @@IDENTITY as cartId";
            PreparedStatement ps1 = connection.prepareStatement(sql1);
            ResultSet rs = ps1.executeQuery();
            if (rs.next()) {
                cartId = Integer.parseInt(rs.getString(1));
            }

            String sql2 = "INSERT INTO [dbo].[Cart_Product]\n"
                    + "           ([CartId]\n"
                    + "           ,[ProductId]\n"
                    + "           ,[Quantity]\n"
                    + "           ,[DateUpdated])\n"
                    + "     VALUES\n"
                    + "           (?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?)";
            PreparedStatement ps2 = connection.prepareStatement(sql2);
            ps2.setInt(1, cartId);
            ps2.setInt(2, cart.getCart_Products().get(0).getProductId());
            ps2.setInt(3, cart.getCart_Products().get(0).getQuantity());
            ps2.setTimestamp(4, cart.getCart_Products().get(0).getDateUpdated());
            ps2.executeUpdate();

            connection.commit();
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(CartDBContext.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Logger.getLogger(CartDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(CartDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void updateCart(Cart cart) {

        try {
            connection.setAutoCommit(false);

            String sql = "DELETE FROM [dbo].[Cart_Product]\n"
                    + "      WHERE cartId = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, cart.getId());
            ps.executeUpdate();

            for (int i = 0; i < cart.getCart_Products().size(); i++) {
                String sql2 = "INSERT INTO [dbo].[Cart_Product]\n"
                        + "           ([CartId]\n"
                        + "           ,[ProductId]\n"
                        + "           ,[Quantity]\n"
                        + "           ,[DateUpdated])\n"
                        + "     VALUES\n"
                        + "           (?\n"
                        + "           ,?\n"
                        + "           ,?\n"
                        + "           ,?)";
                PreparedStatement ps2 = connection.prepareStatement(sql2);
                ps2.setInt(1, cart.getId());
                ps2.setInt(2, cart.getCart_Products().get(i).getProductId());
                ps2.setInt(3, cart.getCart_Products().get(i).getQuantity());
                ps2.setTimestamp(4, cart.getCart_Products().get(i).getDateUpdated());
                ps2.executeUpdate();
            }

            String sql3 = "UPDATE [dbo].[Cart]\n"
                    + "   SET \n"
                    + "      [DateUpdated] = ?\n"
                    + " WHERE [Cart].id = ?";
            PreparedStatement ps3 = connection.prepareStatement(sql3);
            ps3.setDate(1, cart.getDateUpdated());
            ps3.setInt(2, cart.getId());
            ps3.executeUpdate();

            connection.commit();
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(CartDBContext.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Logger.getLogger(CartDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(CartDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public Cart getCartByIndexAndUserId(int index, int sizePage, String productName, int userId) {
        Cart cart = null;
        UserDBContext userDb = new UserDBContext();
        ProductDBContext productDb = new ProductDBContext();
        ArrayList<Cart_Product> lstProduct = new ArrayList<>();
        User userBuy = null;
        Cart_Product cartProduct = null;
        try {

            String sql = "select cp.*,c.customerId, c.DateUpdated [CartUpdated], c.Status_Id, [Product].quantity as product_quantity\n"
                    + "from [Cart] as c join [Cart_Product] cp on c.id = cp.cartId \n"
                    + "	join Product on Product.id = cp.ProductId\n"
                    + "where customerId = ?";
            if (!productName.isEmpty()) {
                sql += " AND LOWER(productName) like '%" + productName.toLowerCase() + "%'";
            }
            sql += " order by DateUpdated desc";
            if (index != 0 && sizePage != 0) {
                sql += " offset ? rows fetch next ? rows only;";
            }

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, userId);
            if (index != 0 && sizePage != 0) {
                ps.setInt(2, ((index - 1) * 2));
                ps.setInt(3, sizePage);
            }
            ResultSet rs = ps.executeQuery();
            cart = new Cart();

            while (rs.next()) {
                cart.setId(rs.getInt("CartId"));
                userBuy = userDb.getUserById(rs.getInt("customerId"));
                cart.setCustomer(userBuy);

                cart.setDateUpdated(rs.getDate("CartUpdated"));
                cart.setStatusId(rs.getInt("Status_id"));
                int pid = 0;
                cartProduct = new Cart_Product();
                cartProduct.setCartId(rs.getInt("CartId"));
                pid = rs.getInt("ProductId");
                cartProduct.setProduct(productDb.getProductById(pid));
                
                //cartProduct.setQuantity(rs.getInt("Quantity"));
                
                int product_quantity = rs.getInt("product_quantity");
                int order_quantity = rs.getInt("Quantity");
                System.out.println("product quantity :"+product_quantity);
                System.out.println("order quantity quantity :"+order_quantity);
                if(order_quantity <= product_quantity){
                    cartProduct.setQuantity(order_quantity);
                }else{
                    cartProduct.setQuantity(product_quantity);
                }

                cartProduct.setDateUpdated(rs.getTimestamp("DateUpdated"));

                lstProduct.add(cartProduct);
                cart.setCart_Products(lstProduct);
            }
        } catch (SQLException ex) {

        }
        return cart;
    }

    public Cart_Product getCartProductByCidAndPid(int cid, int pid) {
        Cart_Product cartProduct = null;
        ProductDBContext productDb =  new ProductDBContext();

        try {
            String sql = "select * from Cart_Product where CartId = ? And ProductId = ?";

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, cid);
            ps.setInt(2, pid);

            ResultSet rs = ps.executeQuery();
            cartProduct = new Cart_Product();

            while (rs.next()) {
                
                cartProduct.setCartId(rs.getInt("CartId"));
                cartProduct.setProductId(rs.getInt("ProductId"));
                int productId = rs.getInt("ProductId");
                cartProduct.setProduct(productDb.getProductById(productId));
                int quantityCartProduct = rs.getInt("Quantity");
                if (quantityCartProduct > productDb.getProductById(productId).getQuantity()) {
                    quantityCartProduct = (int)productDb.getProductById(productId).getQuantity();
                    setQuantityCartProduct(pid, cid, quantityCartProduct);
                }
                cartProduct.setQuantity(quantityCartProduct);
                cartProduct.setDateUpdated(rs.getTimestamp("DateUpdated"));

            }
        } catch (SQLException ex) {

        }
        return cartProduct;
    }

    public boolean deleteCartProduct(int productId, int cartId, boolean isAll) {

        try {
            String sql = "DELETE FROM [dbo].[Cart_Product]\n Where 1 = 1";

            if (isAll) {
                sql += " And CartId = ?";
                sql += "\n Delete from Cart Where id = ?";
            } else {
                sql += " And ProductId = ? And CartId = ?";

            }

            PreparedStatement ps = connection.prepareStatement(sql);
            if (isAll) {
                ps.setInt(1, cartId);
                ps.setInt(2, cartId);
            } else {
                ps.setInt(1, productId);
                ps.setInt(2, cartId);
                setCurrentDateForCart(cartId);
            }

            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {

        }
        return false;
    }

    public boolean setCurrentDateForCart(int cartId) {

        try {
            long millis = System.currentTimeMillis();

            // creating a new object of the class Date  
            java.sql.Date date = new java.sql.Date(millis);
            String sql = "Update Cart set DateUpdated = ? Where Id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setDate(1, date);
            ps.setInt(2, cartId);

            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {

        }
        return false;
    }

    public boolean setQuantityCartProduct(int productId, int cartId, int number) {

        try {
            String sql = "Update Cart_Product set quantity = ? Where ProductId = ? And CartId = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, number);
            ps.setInt(2, productId);
            ps.setInt(3, cartId);
            setCurrentDateForCart(cartId);

            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {

        }
        return false;
    }

    public static void main(String[] args) {
        CartDBContext db = new CartDBContext();

//        ArrayList<Cart_Product> arrayList = new ArrayList<>();
//        arrayList.add(new Cart_Product(5, 9, 10));
//        cart.getCart_Products().addAll(arrayList);
//        System.out.println();
//        db.updateCart(cart);
//        db.addNewCartForNewCustomer(cart);
//    Cart c = db.getCartByCustomerId(76);
//        System.out.println(c.getId() + " " + c.getCustomer().getId() + " " + c.getCart_Products().get(0).getProductId()+ " " + c.getCart_Products().get(0).getQuantity());
//        Cart cart = db.getCartByIndexAndUserId(0, 0, "", 1);
//        //System.out.println(cart.getId());
//        for (Cart_Product c : cart.getCart_Products()) {
//            System.out.println(c.getProduct().getName());
//        }
        System.out.println(db.getCartProductByCidAndPid(17, 10).getQuantity());
    }

    public int getIdCartOfCustomer(int id) {
        try {
            String sql = "select id from Cart\n"
                    + "where customerId = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CartDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public void deleteProductOrdered(Product[] productsOrder, int idCart) {
        try {
            String sql = "DELETE FROM [dbo].[Cart_Product]\n"
                    + "      WHERE CartId = ? and ProductId = ?";
            for (Product product : productsOrder) {
                PreparedStatement stm = connection.prepareStatement(sql);
                stm.setInt(1, idCart);
                stm.setInt(2, product.getId());
                stm.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(CartDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
