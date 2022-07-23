/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import configs.KeyValuePair1;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Category;
import model.Order;
import model.Product;
import model.Role;
import model.SubCategory;
import model.User;

/**
 *
 * @author hoan
 */
public class OrderDBContext extends DBContext {

    public ArrayList<Order> getUserOrders(int uid, String startDate, String endDate) {
        try {
            String sql = "WITH \n"
                    + "t as\n"
                    + "(SELECT [Order].id as OrderID, [Order].[date], [Order].totalPrice, [Product].[name] as ProductName, [Order].[status] as OrderStatus\n"
                    + "FROM [Order] inner join OrderDetail ON [Order].id = OrderDetail.orderId\n"
                    + "inner join Product ON OrderDetail.productId = Product.id\n"
                    + "WHERE [Order].userId = ?),\n"
                    + "b as\n"
                    + "(SELECT [Order].id as OrderID, COUNT(Product.id) as NumberOfProducts\n"
                    + "FROM [Order] inner join OrderDetail ON [Order].id = OrderDetail.orderId\n"
                    + "inner join Product ON OrderDetail.productId = Product.id\n"
                    + "WHERE [Order].userId = ? group by [Order].id),\n"
                    + "c as\n"
                    + "(\n"
                    + "SELECT  a.*\n"
                    + "FROM    (\n"
                    + "        SELECT  DISTINCT t.OrderID\n"
                    + "        FROM t\n"
                    + "        ) mo\n"
                    + "CROSS APPLY\n"
                    + "        (\n"
                    + "        SELECT  TOP 1 *\n"
                    + "        FROM    t mi\n"
                    + "        WHERE   mi.OrderID = mo.OrderID\n"
                    + "        ) a\n"
                    + "		)\n"
                    + "Select c.OrderID, c.[date], c.totalPrice, c.ProductName, c.OrderStatus, b.NumberOfProducts from c inner join b on c.OrderID = b.OrderID\n"
                    + "WHERE c.[date] between ? and ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, uid);
            stm.setInt(2, uid);
            stm.setString(3, startDate);
            stm.setString(4, endDate);
            
            ResultSet rs = stm.executeQuery();
            ArrayList<Order> orders = new ArrayList<>();
            while (rs.next()) {
                Order o = new Order();
                o.setId(rs.getInt("OrderID"));
                o.setDate(rs.getDate("date"));
                o.setTotalcost(rs.getDouble("totalPrice"));
                o.setStatus(rs.getInt("OrderStatus"));
                o.setNumproducts(rs.getInt("NumberOfProducts"));

                ArrayList<Product> products = new ArrayList<>();
                Product p = new Product();
                p.setName(rs.getString("ProductName"));
                products.add(p);

                o.setProducts(products);
                orders.add(o);
            }
            return orders;
        } catch (SQLException ex) {
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public int addOrder(Product[] productsOrder, long total, int idCustomer,
            String email, String shipFullName, String shipAddress, String shipPhone,
            String shipNote, int idPayment, int idSeller) {
        int idOrder = 0;
        try {
            connection.setAutoCommit(false);
            String sqlInsertShip = "INSERT INTO [dbo].[ShipInfo]\n"
                    + "           ([fullname]\n"
                    + "           ,[address]\n"
                    + "           ,[phone]\n"
                    + "           ,[email])\n"
                    + "     VALUES\n"
                    + "           (?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?)";
            PreparedStatement stmInsertShip = connection.prepareStatement(sqlInsertShip);
            stmInsertShip.setString(1, shipFullName);
            stmInsertShip.setString(2, shipAddress);
            stmInsertShip.setString(3, shipPhone);
            stmInsertShip.setString(4, email);
            stmInsertShip.executeUpdate();

            String getIdShipInfo = "SELECT @@IDENTITY AS idShip";
            PreparedStatement stmGetIdShip = connection.prepareStatement(getIdShipInfo);
            ResultSet rs = stmGetIdShip.executeQuery();
            int idShip = 0;
            while (rs.next()) {
                idShip = rs.getInt(1);
            }

            String sqlInsertOrder = "INSERT INTO [dbo].[Order]\n"
                    + "           ([userId]\n"
                    + "           ,[totalPrice]\n"
                    + "           ,[note]\n"
                    + "           ,[status]\n"
                    + "           ,[date]\n"
                    + "           ,[idShip]\n"
                    + "           ,[payment]\n"
                    + "           ,[sellerid])\n"
                    + "     VALUES\n"
                    + "           (?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?)";
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu/MM/dd");
            LocalDate localDate = LocalDate.now();
            Date dateNow = Date.valueOf(dtf.format(localDate).replaceAll("/", "-"));
            PreparedStatement stmInsertOrder = connection.prepareStatement(sqlInsertOrder);
            stmInsertOrder.setInt(1, idCustomer);
            stmInsertOrder.setFloat(2, total);
            stmInsertOrder.setString(3, shipNote);
            stmInsertOrder.setInt(4, 1);
            stmInsertOrder.setDate(5, dateNow);
            stmInsertOrder.setInt(6, idShip);
            stmInsertOrder.setInt(7, idPayment);
            stmInsertOrder.setInt(8, idSeller);
            stmInsertOrder.executeUpdate();

            String getIdOrder = "SELECT @@IDENTITY AS id";
            PreparedStatement stmGetIdOrder = connection.prepareStatement(getIdOrder);
            ResultSet rs2 = stmGetIdOrder.executeQuery();

            while (rs2.next()) {
                idOrder = rs2.getInt(1);
            }
            String sqlInsertProductOrder = "INSERT INTO [dbo].[OrderDetail]\n"
                    + "           ([orderId]\n"
                    + "           ,[productId]\n"
                    + "           ,[discount]\n"
                    + "           ,[quantity]\n"
                    + "           ,[price])\n"
                    + "     VALUES\n"
                    + "           (?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?)";
            for (Product product : productsOrder) {
                PreparedStatement stmInsertProductsOrder = connection.prepareStatement(sqlInsertProductOrder);
                stmInsertProductsOrder.setInt(1, idOrder);
                stmInsertProductsOrder.setInt(2, product.getId());
                stmInsertProductsOrder.setInt(3, product.getDiscount());
                stmInsertProductsOrder.setLong(4, product.getQuantity());
                stmInsertProductsOrder.setLong(5, product.getPrice());
                stmInsertProductsOrder.executeUpdate();
            }
            connection.commit();
        } catch (SQLException ex) {
            idOrder = 0;
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(OrderDBContext.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Logger.getLogger(OrderDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(OrderDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(OrderDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return idOrder;
    }

    public ArrayList<Order> getOrders(String startDate, String endDate) {
        try {
            String sql = "WITH \n"
                    + "t as\n"
                    + "(SELECT [Order].id as OrderID,[User].fullname as CustomerName,[User].id as CustomerID, [Order].[date], [Order].totalPrice, [Product].[name] as ProductName, [Order].[status] as OrderStatus, [Order].sellerId\n"
                    + "FROM \n"
                    + "[User] inner join [Order] on [User].id = [Order].userId\n"
                    + "inner join OrderDetail ON [Order].id = OrderDetail.orderId\n"
                    + "inner join Product ON OrderDetail.productId = Product.id),\n"
                    + "b as\n"
                    + "(SELECT [Order].id as OrderID, COUNT(Product.id) as NumberOfProducts\n"
                    + "FROM [Order] inner join OrderDetail ON [Order].id = OrderDetail.orderId\n"
                    + "inner join Product ON OrderDetail.productId = Product.id\n"
                    + "group by [Order].id),\n"
                    + "c as\n"
                    + "(\n"
                    + "SELECT  a.*\n"
                    + "FROM    (\n"
                    + "        SELECT  DISTINCT t.OrderID\n"
                    + "        FROM t\n"
                    + "        ) mo\n"
                    + "CROSS APPLY\n"
                    + "        (\n"
                    + "        SELECT  TOP 1 *\n"
                    + "        FROM    t mi\n"
                    + "        WHERE   mi.OrderID = mo.OrderID\n"
                    + "        ) a\n"
                    + "		)\n"
                    + "Select c.OrderID, c.[date], c.totalPrice, c.CustomerID, c.CustomerName, c.ProductName, c.OrderStatus, b.NumberOfProducts, c.sellerId, [User].fullname as SellerName\n"
                    + "from c inner join b on c.OrderID = b.OrderID\n"
                    + "left outer join [User] on c.sellerId = [User].id\n"
                    + "\n"
                    + "WHERE c.[date] between ? and ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, startDate);
            stm.setString(2, endDate);

            ResultSet rs = stm.executeQuery();
            ArrayList<Order> orders = new ArrayList<>();
            while (rs.next()) {
                Order o = new Order();
                o.setId(rs.getInt("OrderID"));
                o.setDate(rs.getDate("date"));
                o.setTotalcost(rs.getDouble("totalPrice"));
                o.setStatus(rs.getInt("OrderStatus"));
                o.setNumproducts(rs.getInt("NumberOfProducts"));

                User buyer = new User();
                buyer.setId(rs.getInt("CustomerID"));
                buyer.setFullname(rs.getString("CustomerName"));

                User seller = new User();
                seller.setId(rs.getInt("sellerId"));
                seller.setFullname(rs.getString("SellerName"));

                o.setBuyer(buyer);
                o.setSale(seller);

                ArrayList<Product> products = new ArrayList<>();
                Product p = new Product();
                p.setName(rs.getString("ProductName"));
                products.add(p);

                o.setProducts(products);
                orders.add(o);
            }
            return orders;
        } catch (SQLException ex) {
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList<Order> getOrders(String startDate, String endDate, int saleid, int status) {
        try {
            String sql = "WITH \n"
                    + "t as\n"
                    + "(SELECT [Order].id as OrderID,[User].fullname as CustomerName,[User].id as CustomerID, [Order].[date], [Order].totalPrice, [Product].[name] as ProductName, [Order].[status] as OrderStatus, [Order].sellerId\n"
                    + "FROM \n"
                    + "[User] inner join [Order] on [User].id = [Order].userId\n"
                    + "inner join OrderDetail ON [Order].id = OrderDetail.orderId\n"
                    + "inner join Product ON OrderDetail.productId = Product.id),\n"
                    + "b as\n"
                    + "(SELECT [Order].id as OrderID, COUNT(Product.id) as NumberOfProducts\n"
                    + "FROM [Order] inner join OrderDetail ON [Order].id = OrderDetail.orderId\n"
                    + "inner join Product ON OrderDetail.productId = Product.id\n"
                    + "group by [Order].id),\n"
                    + "c as\n"
                    + "(\n"
                    + "SELECT  a.*\n"
                    + "FROM    (\n"
                    + "        SELECT  DISTINCT t.OrderID\n"
                    + "        FROM t\n"
                    + "        ) mo\n"
                    + "CROSS APPLY\n"
                    + "        (\n"
                    + "        SELECT  TOP 1 *\n"
                    + "        FROM    t mi\n"
                    + "        WHERE   mi.OrderID = mo.OrderID\n"
                    + "        ) a\n"
                    + "		)\n"
                    + "Select c.OrderID, c.[date], c.totalPrice, c.CustomerID, c.CustomerName, c.ProductName, c.OrderStatus, b.NumberOfProducts, c.sellerId, [User].fullname as SellerName\n"
                    + "from c inner join b on c.OrderID = b.OrderID\n"
                    + "left outer join [User] on c.sellerId = [User].id\n"
                    + "\n"
                    + "WHERE c.[date] between ? and ? and c.sellerId = ? and c.OrderStatus = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, startDate);
            stm.setString(2, endDate);
            stm.setInt(3, saleid);
            stm.setInt(4, status);
            ResultSet rs = stm.executeQuery();
            ArrayList<Order> orders = new ArrayList<>();
            while (rs.next()) {
                Order o = new Order();
                o.setId(rs.getInt("OrderID"));
                o.setDate(rs.getDate("date"));
                o.setTotalcost(rs.getDouble("totalPrice"));
                o.setStatus(rs.getInt("OrderStatus"));
                o.setNumproducts(rs.getInt("NumberOfProducts"));

                User buyer = new User();
                buyer.setId(rs.getInt("CustomerID"));
                buyer.setFullname(rs.getString("CustomerName"));

                User seller = new User();
                seller.setId(rs.getInt("sellerId"));
                seller.setFullname(rs.getString("SellerName"));

                o.setBuyer(buyer);
                o.setSale(seller);

                ArrayList<Product> products = new ArrayList<>();
                Product p = new Product();
                p.setName(rs.getString("ProductName"));
                products.add(p);

                o.setProducts(products);
                orders.add(o);
            }
            return orders;
        } catch (SQLException ex) {
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Order getInformationOfOrderByID(int orderID) {
        try {
            String sql = " select DISTINCT  od.id, od.date, od.status, od.totalPrice, od.sellerid, [User].fullname as sellerName, od.note as CustomerNote, od.sellernote as SaleNote\n"
                    + ", od.cancelledReason\n"
                    + "from\n"
                    + "[Order] od inner join [User] on od.sellerid = [User].id\n"
                    + " WHERE od.id = ? ";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, orderID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Order order = new Order();
                order.setId(rs.getInt("id"));
                order.setDate(rs.getDate("date"));
                order.setStatus(rs.getInt("status"));
                order.setTotalcost(rs.getDouble("totalPrice"));
                order.setCustomernote(rs.getString("CustomerNote"));
                order.setSalenote(rs.getString("SaleNote"));
                order.setCancelreason(rs.getString("cancelledReason"));
                User sale = new User();
                sale.setId(rs.getInt("sellerid"));
                sale.setFullname(rs.getString("sellerName"));
                order.setSale(sale);
                return order;
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList<Order> getOrders(String startDate, String endDate, int saleid) {
        try {
            String sql = "WITH \n"
                    + "t as\n"
                    + "(SELECT [Order].id as OrderID,[User].fullname as CustomerName,[User].id as CustomerID, [Order].[date], [Order].totalPrice, [Product].[name] as ProductName, [Order].[status] as OrderStatus, [Order].sellerId\n"
                    + "FROM \n"
                    + "[User] inner join [Order] on [User].id = [Order].userId\n"
                    + "inner join OrderDetail ON [Order].id = OrderDetail.orderId\n"
                    + "inner join Product ON OrderDetail.productId = Product.id),\n"
                    + "b as\n"
                    + "(SELECT [Order].id as OrderID, COUNT(Product.id) as NumberOfProducts\n"
                    + "FROM [Order] inner join OrderDetail ON [Order].id = OrderDetail.orderId\n"
                    + "inner join Product ON OrderDetail.productId = Product.id\n"
                    + "group by [Order].id),\n"
                    + "c as\n"
                    + "(\n"
                    + "SELECT  a.*\n"
                    + "FROM    (\n"
                    + "        SELECT  DISTINCT t.OrderID\n"
                    + "        FROM t\n"
                    + "        ) mo\n"
                    + "CROSS APPLY\n"
                    + "        (\n"
                    + "        SELECT  TOP 1 *\n"
                    + "        FROM    t mi\n"
                    + "        WHERE   mi.OrderID = mo.OrderID\n"
                    + "        ) a\n"
                    + "		)\n"
                    + "Select c.OrderID, c.[date], c.totalPrice, c.CustomerID, c.CustomerName, c.ProductName, c.OrderStatus, b.NumberOfProducts, c.sellerId, [User].fullname as SellerName\n"
                    + "from c inner join b on c.OrderID = b.OrderID\n"
                    + "left outer join [User] on c.sellerId = [User].id\n"
                    + "\n"
                    + "WHERE c.[date] between ? and ? and c.sellerId = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, startDate);
            stm.setString(2, endDate);
            stm.setInt(3, saleid);;
            ResultSet rs = stm.executeQuery();
            ArrayList<Order> orders = new ArrayList<>();
            while (rs.next()) {
                Order o = new Order();
                o.setId(rs.getInt("OrderID"));
                o.setDate(rs.getDate("date"));
                o.setTotalcost(rs.getDouble("totalPrice"));
                o.setStatus(rs.getInt("OrderStatus"));
                o.setNumproducts(rs.getInt("NumberOfProducts"));

                User buyer = new User();
                buyer.setId(rs.getInt("CustomerID"));
                buyer.setFullname(rs.getString("CustomerName"));

                User seller = new User();
                seller.setId(rs.getInt("sellerId"));
                seller.setFullname(rs.getString("SellerName"));

                o.setBuyer(buyer);
                o.setSale(seller);

                ArrayList<Product> products = new ArrayList<>();
                Product p = new Product();
                p.setName(rs.getString("ProductName"));
                products.add(p);

                o.setProducts(products);
                orders.add(o);
            }
            return orders;
        } catch (SQLException ex) {
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public User getUserOrderInformation(int orderID) {
        try {
            String sql = " select DISTINCT  s.fullname, u.gender, s.email, s.phone\n"
                    + "from\n"
                    + "[Order] od\n"
                    + " INNER JOIN  ShipInfo s ON od.idShip = s.id\n"
                    + " INNER JOIN [User] u on od.userId = u.id\n"
                    + "WHERE od.id = ? ";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, orderID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setFullname(rs.getString("fullname"));
                user.setGender(rs.getBoolean("gender"));
                user.setEmail(rs.getString("email"));
                user.setMobile(rs.getString("phone"));
                return user;
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList<Order> getOrdersByStatus(String startDate, String endDate, int status) {
        try {
            String sql = "WITH \n"
                    + "t as\n"
                    + "(SELECT [Order].id as OrderID,[User].fullname as CustomerName,[User].id as CustomerID, [Order].[date], [Order].totalPrice, [Product].[name] as ProductName, [Order].[status] as OrderStatus, [Order].sellerId\n"
                    + "FROM \n"
                    + "[User] inner join [Order] on [User].id = [Order].userId\n"
                    + "inner join OrderDetail ON [Order].id = OrderDetail.orderId\n"
                    + "inner join Product ON OrderDetail.productId = Product.id),\n"
                    + "b as\n"
                    + "(SELECT [Order].id as OrderID, COUNT(Product.id) as NumberOfProducts\n"
                    + "FROM [Order] inner join OrderDetail ON [Order].id = OrderDetail.orderId\n"
                    + "inner join Product ON OrderDetail.productId = Product.id\n"
                    + "group by [Order].id),\n"
                    + "c as\n"
                    + "(\n"
                    + "SELECT  a.*\n"
                    + "FROM    (\n"
                    + "        SELECT  DISTINCT t.OrderID\n"
                    + "        FROM t\n"
                    + "        ) mo\n"
                    + "CROSS APPLY\n"
                    + "        (\n"
                    + "        SELECT  TOP 1 *\n"
                    + "        FROM    t mi\n"
                    + "        WHERE   mi.OrderID = mo.OrderID\n"
                    + "        ) a\n"
                    + "		)\n"
                    + "Select c.OrderID, c.[date], c.totalPrice, c.CustomerID, c.CustomerName, c.ProductName, c.OrderStatus, b.NumberOfProducts, c.sellerId, [User].fullname as SellerName\n"
                    + "from c inner join b on c.OrderID = b.OrderID\n"
                    + "left outer join [User] on c.sellerId = [User].id\n"
                    + "\n"
                    + "WHERE c.[date] between ? and ? and c.OrderStatus = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, startDate);
            stm.setString(2, endDate);
            stm.setInt(3, status);;
            ResultSet rs = stm.executeQuery();
            ArrayList<Order> orders = new ArrayList<>();
            while (rs.next()) {
                Order o = new Order();
                o.setId(rs.getInt("OrderID"));
                o.setDate(rs.getDate("date"));
                o.setTotalcost(rs.getDouble("totalPrice"));
                o.setStatus(rs.getInt("OrderStatus"));
                o.setNumproducts(rs.getInt("NumberOfProducts"));

                User buyer = new User();
                buyer.setId(rs.getInt("CustomerID"));
                buyer.setFullname(rs.getString("CustomerName"));

                User seller = new User();
                seller.setId(rs.getInt("sellerId"));
                seller.setFullname(rs.getString("SellerName"));

                o.setBuyer(buyer);
                o.setSale(seller);

                ArrayList<Product> products = new ArrayList<>();
                Product p = new Product();
                p.setName(rs.getString("ProductName"));
                products.add(p);

                o.setProducts(products);
                orders.add(o);
            }
            return orders;
        } catch (SQLException ex) {
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void updateOrderStatus(int orderid, int status) {
        try {
            String sql = "UPDATE [dbo].[Order]\n"
                    + "   SET [status] = ?\n"
                    + " WHERE [Order].id = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, status);
            stm.setInt(2, orderid);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateOrderStatus(int orderid, int status, String cancelledReason) {
        try {
            String sql = "UPDATE [dbo].[Order]\n"
                    + "   SET [status] = ?\n"
                    + "   ,[cancelledReason] = ?\n"
                    + " WHERE [Order].id = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, status);
            stm.setInt(3, orderid);
            stm.setString(2, cancelledReason);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<Product> getListOrderProductOfUser(int orderID) {
        ArrayList<Product> listProduct = new ArrayList<>();
        String sql = " select p.id as productID, p.thumbnail, p.name as pname, c.name as categoryName, o.quantity, o.discount, o.price, od.userId, od.cancelledReason\n"
                + "               from\n"
                + "               [Order] od\n"
                + "                INNER JOIN OrderDetail o ON o.orderId = od.id\n"
                + "                INNER JOIN Product p ON o.productId = p.id\n"
                + "                INNER JOIN SubCategory sub ON p.subCategoryId = sub.id\n"
                + "                INNER JOIN Category c ON sub.categoryId = c.id\n"
                + "                WHERE o.orderId = ?  ";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, orderID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("productID"));
                product.setThumbnail(rs.getString("thumbnail"));
                product.setName(rs.getString("pname"));
                product.setQuantity(rs.getLong("quantity"));
                product.setDiscount(rs.getInt("discount"));
                product.setPrice(rs.getLong("price"));

                Category category = new Category();
                category.setName(rs.getString("categoryName"));

                SubCategory subCategory = new SubCategory();
                subCategory.setCategory(category);

                product.setSubCategory(subCategory);

                listProduct.add(product);
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listProduct;
    }

    public ArrayList<Order> getListOrderProductOfUser2(int orderID) {
        ArrayList<Order> listOrder = new ArrayList<>();

        String sql = " select p.id as productID, p.thumbnail, p.name as pname, c.name as categoryName, o.quantity, o.discount, o.price, od.userId, o.isFeedback \n"
                + "               from\n"
                + "               [Order] od\n"
                + "                INNER JOIN OrderDetail o ON o.orderId = od.id\n"
                + "                INNER JOIN Product p ON o.productId = p.id\n"
                + "                INNER JOIN SubCategory sub ON p.subCategoryId = sub.id\n"
                + "                INNER JOIN Category c ON sub.categoryId = c.id\n"
                + "                WHERE o.orderId = ?  ";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, orderID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ArrayList<Product> listProduct = new ArrayList<>();

                Product product = new Product();
                product.setId(rs.getInt("productID"));
                product.setThumbnail(rs.getString("thumbnail"));
                product.setName(rs.getString("pname"));
                product.setQuantity(rs.getLong("quantity"));
                product.setDiscount(rs.getInt("discount"));
                product.setPrice(rs.getLong("price"));

                Category category = new Category();
                category.setName(rs.getString("categoryName"));

                SubCategory subCategory = new SubCategory();
                subCategory.setCategory(category);

                product.setSubCategory(subCategory);

                listProduct.add(product);

                Order order = new Order();
                order.setIsFeedback(rs.getBoolean("isFeedback"));
                order.setProducts(listProduct);

                listOrder.add(order);
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listOrder;
    }

    public void editStatusOrder(int orderID) {
        String spl1 = " UPDATE [dbo].[Order]\n"
                + "SET [status] = 0 \n"
                + "WHERE id = ? ";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(spl1);
            stm.setInt(1, orderID);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(OrderDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
//        finally {
//            if (stm != null) {
//                try {
//                    stm.close();
//                } catch (SQLException ex) {
//                    Logger.getLogger(OrderDBContext.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//            if (connection != null) {
//                try {
//                    connection.close();
//                } catch (SQLException ex) {
//                    Logger.getLogger(OrderDBContext.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//        }
    }

    public void updateSaleNote(int orderid, String note) {
        try {
            String sql = "UPDATE [dbo].[Order]\n"
                    + "   SET [sellernote] = ?\n"
                    + " WHERE [Order].id = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, note);
            stm.setInt(2, orderid);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateSale(int orderid, int saleid) {
        try {
            String sql = "UPDATE [dbo].[Order]\n"
                    + "   SET [sellerid] = ?\n"
                    + " WHERE [Order].id = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, saleid);
            stm.setInt(2, orderid);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<Order> getListProductOrderByID(int orderID) {
        ArrayList<Order> listOrder = new ArrayList<>();
        String sql = " select * from [OrderDetail] where orderId = ?  ";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, orderID);
//            ps.setInt(2, userBuyID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getInt("orderId"));

                Product product = new Product();
                product.setId(rs.getInt("productId"));

                ArrayList<Product> listProduct = new ArrayList<>();

                listProduct.add(product);

                order.setProducts(listProduct);

                listOrder.add(order);
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listOrder;
    }

    public void deleteOrderDetailsByID(int orderID) {
        String spl1 = " DELETE FROM [dbo].[OrderDetail]\n"
                + "      WHERE orderId = ? ";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(spl1);
            stm.setInt(1, orderID);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(OrderDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteOrderByID(int orderID) {
        String spl1 = " DELETE FROM [dbo].[Order]\n"
                + "      WHERE id = ? ";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(spl1);
            stm.setInt(1, orderID);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(OrderDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addReasionToCancel(int orderID, String reasionCancel) {
        String spl1 = " UPDATE [dbo].[Order]\n"
                + "   SET [cancelledReason] = ? \n"
                + " WHERE id = ? ";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(spl1);
            stm.setString(1, reasionCancel);
            stm.setInt(2, orderID);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(OrderDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(OrderDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(OrderDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public String getReasionCancel(int orderID) {
        try {
            String sql = "SELECT cancelledReason FROM [Order] Where id = ?  ";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, orderID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("cancelledReason");
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void editStatusFeedback(int productID, int orderID) {
        String spl1 = " UPDATE [dbo].[OrderDetail]\n"
                + "   SET [isFeedback] = 1 \n"
                + " WHERE orderId = ? and productId = ? ";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(spl1);
            stm.setInt(1, orderID);
            stm.setInt(2, productID);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(OrderDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<KeyValuePair1> getTop5BestCustomer() {
        List<KeyValuePair1> lst = null;
        try {
            String sql = "select u.*, t.totalPrice from [User] u, \n"
                    + "(select top 5 userId, sum(totalPrice) totalPrice from [Order]\n"
                    + "group by userId\n"
                    + "order by totalPrice desc) t where u.id = t.userId";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            lst = new ArrayList<>();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setFullname(rs.getString("fullname"));
                user.setEmail(rs.getString("email"));
                user.setMobile(rs.getString("mobile"));
                KeyValuePair1 kv = new KeyValuePair1(user, rs.getLong("totalPrice"));
                lst.add(kv);
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lst;
    }

    public List<KeyValuePair1> getTop5BestSeller(Date from, Date to) {
        List<KeyValuePair1> lst = null;
        UserDBContext userDb = new UserDBContext();
        try {
            String sql = "select u.id,t.numberOrder, t.totalPrice from [User] u, (\n"
                    + "select top 5 sellerid, COUNT(*) numberOrder, sum(totalPrice) totalPrice from [Order] where 1=1";

            if (from != null) {
                sql += " and [date] >= '" + from + "'";
            }
            if (to != null) {
                sql += " and [date] <= '" + to + "'";
            }
            if (from == null && to == null) {
                sql += " And [date] >= DATEADD(day,-7, GETDATE())";
            }
            sql += "group by sellerid\n"
                    + "order by totalPrice desc) t \n"
                    + "where u.id = t.sellerid";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            lst = new ArrayList<>();
            User user = null;
            while (rs.next()) {
                user = new User();
                int uid = rs.getInt("id");
                user = userDb.getUserById(uid);
                KeyValuePair1 kv = new KeyValuePair1(user, rs.getInt("numberOrder"),rs.getLong("totalPrice"));
                lst.add(kv);
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lst;
    }

    public static void main(String[] args) {
        OrderDBContext db = new OrderDBContext();
        List<KeyValuePair1> lst = db.getTop5BestSeller(null, null);
        for (KeyValuePair1 keyValuePair1 : lst) {
            System.out.println(((User) keyValuePair1.getKey()).getFullname());
            System.out.println(keyValuePair1.getValue());
            System.out.println(keyValuePair1.getValue1());

        }
    }

}
