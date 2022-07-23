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
import model.Category;
import model.Feature;
import model.Feedback;
import model.Image;
import model.Product;
import model.SubCategory;
import model.User;

/**
 *
 * @author Hoang Quang
 */
public class ProductListDBContext extends DBContext {

    public Product getLeastProduct() {
        String sql = "select top 2 * from [Product] \n"
                + "where status = 1 \n"
                + "order by [date] desc";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getLong("price"));
                product.setDiscount(rs.getInt("discount"));
                product.setThumbnail(rs.getString("thumbnail"));
                return product;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductListDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList<Product> getListLeastProduct() {
        ArrayList<Product> listProduct = new ArrayList<>();
        String sql = "select top 3 * from [Product] \n"
                + "where status = 1 and quantity > 0 \n"
                + "order by [date] desc, id desc";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getLong("price"));
                product.setDiscount(rs.getInt("discount"));
                product.setThumbnail(rs.getString("thumbnail"));
                listProduct.add(product);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductListDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listProduct;
    }

    public int countSizeOfListProduct(String searchBy, int subCategory) {
        try {

            String sql1 = "with x as (select ROW_NUMBER() OVER (ORDER BY id desc, date desc) as r\n"
                    + ", * from [Product] where status = 1 AND quantity >0 \n"
                    + "                                    AND (name like ? \n"
                    + "					        OR description like ? )\n"
                    + "						)\n"
                    + "SElECT COUNT(*) as Total FROM x ";
            if (subCategory > 0) {
                sql1 += "WHERE  subCategoryId = ?";
            }

            PreparedStatement ps = connection.prepareStatement(sql1);
            ps.setString(1, "%" + searchBy + "%");
            ps.setString(2, "%" + searchBy + "%");
            if (subCategory > 0) {
                ps.setInt(3, subCategory);
            }
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("Total");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductListDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public ArrayList<Product> getListProductsPagging(String searchBy, int subCategory, int pageindex, int pagesize) {
        ArrayList<Product> listProdcuts = new ArrayList<>();
        String sql1 = "";

        if (subCategory > 0) {
            sql1 = " with x as (select ROW_NUMBER() OVER (ORDER BY date desc, id desc) as r\n"
                    + "                    		, * from [Product] where status = 1 and subCategoryId = ? \n"
                    + "					AND (name like ? \n"
                    + "					 OR description like ? ))\n"
                    + "                    		SElECT* FROM x where r between (?  - 1)* ? +1 and ? * ? ";
        }
        if (subCategory == 0) {
            sql1 = " with x as (select ROW_NUMBER() OVER (ORDER BY date desc, id desc) as r\n"
                    + "                    		, * from [Product] where status = 1 \n"
                    + "					AND (name like ? \n"
                    + "					 OR description like ? ))\n"
                    + "                    		SElECT* FROM x where r between (?  - 1)* ? +1 and ? * ? ";
        }

        try {
            PreparedStatement ps = connection.prepareStatement(sql1);
            if (subCategory > 0) {
                ps.setInt(1, subCategory);
                ps.setString(2, "%" + searchBy + "%");
                ps.setString(3, "%" + searchBy + "%");
                ps.setInt(4, pageindex);
                ps.setInt(5, pagesize);
                ps.setInt(6, pageindex);
                ps.setInt(7, pagesize);
            }
            if (subCategory == 0) {
                ps.setString(1, "%" + searchBy + "%");
                ps.setString(2, "%" + searchBy + "%");
                ps.setInt(3, pageindex);
                ps.setInt(4, pagesize);
                ps.setInt(5, pageindex);
                ps.setInt(6, pagesize);
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setQuantity(rs.getInt("quantity"));
                product.setPrice(rs.getLong("price"));
                product.setDiscount(rs.getInt("discount"));
                product.setThumbnail(rs.getString("thumbnail"));
                listProdcuts.add(product);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductListDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listProdcuts;
    }

    public Product getProductByID(int productID) {
        String sql = "SELECT p.id, p.[name], p.[description], p.price, p.discount, p.thumbnail , p.quantity , p.sellerId,u.id, u.fullname \n"
                + "FROM Product p INNER JOIN [User] u\n"
                + "ON p.sellerId = u.id\n"
                + "WHERE p.id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, productID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt(1));
                product.setName(rs.getString(2));
                product.setDescription(rs.getString(3));
                product.setPrice(rs.getLong(4));
                product.setDiscount(rs.getInt(5));
                product.setThumbnail(rs.getString(6));
                product.setQuantity(rs.getInt(7));
                User user = new User();
                user.setId(rs.getInt(9));
                user.setFullname(rs.getString(10));
                product.setUser(user);
                return product;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductListDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Product getProductById(int productId) {
        Product product = new Product();
        try {
            connection.setAutoCommit(false);
            String sql = "SELECT [Product].[id]\n"
                    + "      ,[Product].[name]\n"
                    + "      ,[description]\n"
                    + "      ,[price]\n"
                    + "      ,[discount]\n"
                    + "      ,[sellerId]\n"
                    + "      ,[User].[fullname]\n"
                    + "      ,[featured]\n"
                    + "      ,[thumbnail]\n"
                    + "      ,[date]\n"
                    + "      ,[subCategoryId]\n"
                    + "	  ,[SubCategory].[name]\n"
                    + "	  ,[Category].[id]\n"
                    + "	  ,[Category].[name]\n"
                    + "      ,[quantity]\n"
                    + "      ,[Product].[status]\n"
                    + "      ,[User].email\n"
                    + "      ,[User].avatar\n"
                    + "  FROM [dbo].[Product] join SubCategory on subCategoryId = SubCategory.id\n"
                    + "		     join Category on Category.id = SubCategory.categoryId\n"
                    + "			 join [User] on [User].id = sellerId\n"
                    + "Where [Product].[id] = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                product.setId(productId);
                product.setName(rs.getString(2));
                product.setDescription(rs.getString(3));
                product.setPrice(rs.getLong(4));
                product.setDiscount(rs.getInt(5));
                User user = new User();
                user.setId(rs.getInt(6));
                user.setFullname(rs.getString(7));
                product.setUser(user);
                product.setFeatured(rs.getBoolean(8));
                product.setThumbnail(rs.getString(9));
                product.setDate(rs.getDate(10));
                SubCategory subCategory = new SubCategory();
                subCategory.setId(rs.getInt(11));
                subCategory.setName(rs.getString(12));
                product.setSubCategory(subCategory);
                Category category = new Category();
                category.setId(rs.getInt(13));
                category.setName(rs.getString(14));
                subCategory.setCategory(category);
                product.setSubCategory(subCategory);
                product.setQuantity(rs.getInt(15));
                product.setStatus(rs.getBoolean(16));
                user.setEmail(rs.getString(17));
                user.setAddress(rs.getString(18));
                product.setUser(user);
            }

            String sql1 = " SELECT [Image].id, [Image].[image]\n"
                    + "  FROM [dbo].[Image] join Product_Image on [Image].id = Product_Image.imageId\n"
                    + "					join Product on Product.id = Product_Image.productId\n"
                    + "WHERE Product_Image.productId = ?";
            PreparedStatement ps1 = connection.prepareStatement(sql1);
            ps1.setInt(1, productId);
            ResultSet rs1 = ps1.executeQuery();

            ArrayList<Image> images = new ArrayList<>();
            while (rs1.next()) {
                Image image = new Image();
                image.setId(rs1.getInt(1));
                image.setImage(rs1.getString(2));
                images.add(image);
            }

            product.getImage().addAll(images);
            connection.commit();
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(ProductDBContext.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Logger.getLogger(ProductDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(ProductDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return product;
    }

    public int countSizeOfFeedback(int productID) {
        try {

            String sql1 = " with x as (select ROW_NUMBER() OVER (ORDER BY f.id desc) as r\n"
                    + "                    		, f.id, f.userId, f.productId, f.start,f.comment ,f.image, f.status, u.fullname from Feedback f INNER JOIN [User] u\n"
                    + "							ON f.userId = u.id\n"
                    + "							where f.status = 1\n"
                    + "							AND f.productId = ? )\n"
                    + "                    		SElECT COUNT(*) as Total FROM x ";

            PreparedStatement ps = connection.prepareStatement(sql1);
            ps.setInt(1, productID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("Total");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductListDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public ArrayList<Feedback> getListFeedbackByProductID(int productID, int pageindex, int pagesize) {
        ArrayList<Feedback> listFeedbacks = new ArrayList<>();

        try {
            String sql1 = " with x as (select ROW_NUMBER() OVER (ORDER BY f.id desc) as r\n"
                    + "                    		, f.id, f.userId, f.productId, f.start,f.comment ,f.image, f.status, u.fullname, u.avatar, f.date from Feedback f INNER JOIN [User] u\n"
                    + "							ON f.userId = u.id\n"
                    + "							where f.status = 1\n"
                    + "							AND f.productId = ? )\n"
                    + "                    		SElECT* FROM x where r between (? - 1)* ? +1 and ? * ?  ";

            PreparedStatement ps = connection.prepareStatement(sql1);
            ps.setInt(1, productID);
            ps.setInt(2, pageindex);
            ps.setInt(3, pagesize);
            ps.setInt(4, pageindex);
            ps.setInt(5, pagesize);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Feedback feedback = new Feedback();
                feedback.setId(rs.getInt("id"));
                User user = new User();
                user.setId(rs.getInt("userId"));
                user.setFullname(rs.getString("fullname"));
                user.setAvatar(rs.getString("avatar"));
                feedback.setUser(user);
                Product product = new Product();
                product.setId(rs.getInt("productId"));
                feedback.setProduct(product);
                feedback.setComment(rs.getString("comment"));
                feedback.setStart(rs.getInt("start"));
                if (rs.getDate("date") != null) {
                    feedback.setDate(rs.getTimestamp("date"));
                }

                ArrayList<Image> images = new ArrayList<>();
                String sql2 = "SELECT [Image].[image], [Image].[id]\n"
                        + "  FROM [dbo].[Feedback_Image] join Image on [Image].id = Feedback_Image.imageId\n"
                        + "  Where feedbackId = ?";
                PreparedStatement ps2 = connection.prepareStatement(sql2);
                ps2.setInt(1, feedback.getId());
                ResultSet rs2 = ps2.executeQuery();
                while (rs2.next()) {
                    Image image = new Image();
                    image.setImage(rs2.getString(1));
                    image.setId(rs.getInt(2));
                    images.add(image);
                }
                feedback.setImage(images);
                listFeedbacks.add(feedback);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductListDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listFeedbacks;
    }

//    public ArrayList<Cart> getAllProductIdInCart() {
//        ArrayList<Cart> listCarts = new ArrayList<>();
//        String sql1 = " select * from cart  ";
//        try {
//            PreparedStatement ps = connection.prepareStatement(sql1);
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                Product product = new Product();
//                product.setId(rs.getInt("productId"));
//
//                User userBuyId = new User();
//                userBuyId.setId(rs.getInt("userBuyId"));
//
//                Cart cart = new Cart();
//                cart.setProduct(product);
//                cart.setUserBuy(userBuyId);
//                listCarts.add(cart);
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(ProductListDBContext.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return listCarts;
//    }
    public void editQuantityOrderOfCart(int quantityOrder, int productId, int userBuyId) {
        String spl1 = " UPDATE [dbo].[Cart]\n"
                + "   SET [quantityOrder] = ? \n"
                + "      \n"
                + " WHERE userBuyId = ? and productId = ? ";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(spl1);
            stm.setInt(1, quantityOrder);
            stm.setInt(2, userBuyId);
            stm.setInt(3, productId);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ProductListDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ProductListDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ProductListDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

//    public void addNewCartInfomation(Cart cart) {
//        String sql = " INSERT INTO [dbo].[Cart]\n"
//                + "           ([productId]\n"
//                + "           ,[productName]\n"
//                + "           ,[quantityOrder]\n"
//                + "           ,[price]\n"
//                + "           ,[userBuyId]\n"
//                + "           ,[sellerId]\n"
//                + "           ,[thumbnail])\n"
//                + "     VALUES\n"
//                + "           (?\n"
//                + "           ,?\n"
//                + "           ,?\n"
//                + "           ,?\n"
//                + "           ,?\n"
//                + "           ,?\n"
//                + "           ,?) ";
//        PreparedStatement stm = null;
//
//        try {
//            stm = connection.prepareStatement(sql);
//            stm.setInt(1, cart.getProduct().getId());
//            stm.setString(2, cart.getProduct().getName());
//            stm.setInt(3, cart.getQuantityOrder());
//            stm.setLong(4, cart.getPrice());
//            stm.setInt(5, cart.getUserBuy().getId());
//            stm.setInt(6, cart.getUserSeller().getId());
//            stm.setString(7, cart.getThumbnail());
//            stm.executeUpdate();
//        } catch (SQLException ex) {
//            Logger.getLogger(ProductListDBContext.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {
//            if (stm != null) {
//                try {
//                    stm.close();
//                } catch (SQLException ex) {
//                    Logger.getLogger(ProductListDBContext.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//            if (connection != null) {
//                try {
//                    connection.close();
//                } catch (SQLException ex) {
//                    Logger.getLogger(ProductListDBContext.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//        }
//    }
    public static void main(String[] args) {
        ProductListDBContext bContext = new ProductListDBContext();
        System.out.println(bContext.getListFeedbackByProductID(9, 1, 5));
    }

    public ArrayList<Product> getListProductById(Product[] listIdProduct, int idUser) {
        ArrayList<Product> listProduct = new ArrayList<>();
        try {
            String sql = "Select cart.*, Cart_Product.ProductId, Cart_Product.Quantity, product.thumbnail, Product.[name], product.sellerId, [User].fullname,\n"
//                    + "((Product.price - (product.price*product.discount/100))) as totalPrice\n"
                    + "Product.price, product.discount \n"
                    + "from cart join Cart_Product\n"
                    + "on cart.id = Cart_Product.CartId\n"
                    + "join Product on Product.id = Cart_Product.ProductId\n"
                    + "join [User] on [User].id = product.sellerId\n"
                    + "where cart.customerId = ? and Cart_Product.ProductId = ?";
            for (Product i : listIdProduct) {
                PreparedStatement stm = connection.prepareCall(sql);
                stm.setInt(1, idUser);
                stm.setInt(2, i.getId());
                ResultSet rs = stm.executeQuery();
                while (rs.next()) {
                    Product product = new Product();
                    product.setId(rs.getInt(5));
                    product.setQuantity(rs.getInt(6));
                    product.setThumbnail(rs.getString(7));
                    product.setName(rs.getString(8));
                    User user = new User();
                    user.setId(rs.getInt(9));
                    user.setFullname(rs.getString(10));
                    product.setUser(user);
                    product.setPrice(rs.getLong(11));
                    product.setDiscount(rs.getInt(12));
                    listProduct.add(product);
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProductListDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listProduct;
    }

    public double getStartPercent(int productID) {
        int PERCENT_PER_STAR = 20;
        double total = 0;
        double count = 0;
        double average = 0;
        try {
            String sql = "SELECT \n"
                    + "      [start]\n"
                    + "  FROM [Feedback] join Product on Feedback.productId = Product.id\n"
                    + "  Where [Feedback].[status] = 1 \n"
                    + "  and Product.id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, productID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                total += rs.getInt(1);
                count++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductListDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (total == 0 || count == 0) {
            average = 0;
        } else {
            average = total / count;
        }

        return average;
    }

    public int getTotalFeedback(int productID) {
        try {
            String sql = "SELECT \n"
                    + "      Count(*)\n"
                    + "  FROM [Feedback] join Product on Feedback.productId = Product.id\n"
                    + "  Where [Feedback].[status] = 1 \n"
                    + "  and Product.id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, productID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductListDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public int getTotalQuantityProductSolded(int productID) {
        try {
            String sql = "select Sum([OrderDetail].quantity)\n"
                    + "from \n"
                    + "OrderDetail join [Order] on  [Order].id = OrderDetail.orderId\n"
                    + "where [Order].[status] = 4 and OrderDetail.productId = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, productID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductListDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
}
