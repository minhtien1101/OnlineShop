/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import configs.KeyValuePair;
import configs.KeyValuePair1;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Category;
import model.Image;
import model.Product;
import model.SubCategory;
import model.User;

/**
 *
 * @author DELL
 */
public class ProductDBContext extends DBContext {

    public ArrayList<Product> getProductsFeatured() {
        ArrayList<Product> listProduct = new ArrayList<>();
        try {
            String sql = "Select top 6 * from Product\n"
                    + "where featured = 1 and status = 1 and quantity > 0 \n"
                    + "order by date desc";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt(1));
                product.setName(rs.getString(2));
                product.setDescription(rs.getString(3));
                product.setPrice(rs.getLong(4));
                product.setDiscount(rs.getInt(5));
                User user = new User();
                user.setId(rs.getInt(6));
                product.setUser(user);
                product.setFeatured(rs.getBoolean(7));
                product.setThumbnail(rs.getString(8));
                product.setDate(rs.getDate(9));
                SubCategory subCategory = new SubCategory();
                subCategory.setId(rs.getInt(10));
                product.setSubCategory(subCategory);
                product.setQuantity(rs.getInt(11));
                product.setStatus(rs.getBoolean(12));
                listProduct.add(product);
            }
        } catch (SQLException e) {
        }
        return listProduct;
    }

    public ArrayList<KeyValuePair1> getProductsTrend(Date from, Date to) {
        ArrayList<KeyValuePair1> list = new ArrayList<>();
        try {
            String sql = "select top 5 productId, sum(quantity) as number from OrderDetail as o\n"
                    + "where o.orderId in (select id from [Order] where 1=1";

            if (from != null) {
                sql += " and [date] >= '" + from + "'";
            }
            if (to != null) {
                sql += " and [date] <= '" + to + "'";
            }
            if (from == null && to == null) {
                sql += " and [date] >= DATEADD(day,-7, GETDATE())";
            }
            sql += " )group by productId";
            sql += " order by number desc";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Product product = null;
                int pid = rs.getInt("productId");
                product = getProductById(pid);
                list.add(new KeyValuePair1(product, rs.getInt("number")));
            }
        } catch (SQLException e) {
        }
        return list;
    }

    public ArrayList<Product> getProductsBySliderId(int sid, String search) {
        ArrayList<Product> list = new ArrayList<>();
        try {
            String sql = "select * from [product] where sliderId = ?";
            
            if (!search.isEmpty()) {
                sql += " And [name] like '%" + search + "%'";
            }
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, sid);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt(1));
                product.setName(rs.getString(2));
                product.setDescription(rs.getString(3));
                product.setPrice(rs.getLong(4));
                product.setDiscount(rs.getInt(5));
                User user = new User();
                user.setId(rs.getInt(6));
                product.setUser(user);
                product.setFeatured(rs.getBoolean(7));
                product.setThumbnail(rs.getString(8));
                product.setDate(rs.getDate(9));
                SubCategory subCategory = new SubCategory();
                subCategory.setId(rs.getInt(10));
                product.setSubCategory(subCategory);
                product.setQuantity(rs.getInt(11));
                product.setStatus(rs.getBoolean(12));
                list.add(product);
            }
        } catch (SQLException e) {
        }
        return list;
    }

    public int getProductNumber() {
        int productNumber = 0;
        try {
            String sql = "select COUNT(*) num from product";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                productNumber = rs.getInt("num");
            }
        } catch (SQLException e) {
        }
        return productNumber;
    }

    public static void main(String[] args) {
        ProductDBContext db = new ProductDBContext();
//        Date sqlDate = Date.valueOf("2022-06-25");
//        Date sqlDate1 = Date.valueOf("2022-07-07");
//        ArrayList<KeyValuePair1> list = db.getProductsTrend(sqlDate, sqlDate1);
//        for (KeyValuePair1 keyValuePair1 : list) {
//            System.out.println(((Product) keyValuePair1.getKey()).getName());
//            System.out.println((keyValuePair1.getValue()));
//        }
        ArrayList<Product> lst = db.getProductsBySliderId(1, "Nike");
        for (Product product : lst) {
            System.out.println(product.getName());
        }
    }

    public Product addProduct(String name, String description, int sellerId, int subCategoryId, long price, int discount, long quantity, boolean featured, boolean status) {

        Product product = new Product();
        try {
            connection.setAutoCommit(false);
            String sql = "INSERT INTO [dbo].[Product]\n"
                    + "           ([name]\n"
                    + "           ,[description]\n"
                    + "           ,[price]\n"
                    + "           ,[discount]\n"
                    + "           ,[sellerId]\n"
                    + "           ,[featured]\n"
                    + "           ,[date]\n"
                    + "           ,[subCategoryId]\n"
                    + "           ,[quantity]\n"
                    + "           ,[status])\n"
                    + "     VALUES\n"
                    + "           (?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, description);
            ps.setLong(3, price);
            ps.setInt(4, discount);
            ps.setInt(5, sellerId);
            ps.setBoolean(6, featured);
            //
            java.util.Date date = new java.util.Date();
            Timestamp current = new Timestamp(date.getTime());
            ps.setTimestamp(7, current);
            //
            ps.setInt(8, subCategoryId);
            ps.setFloat(9, quantity);
            ps.setBoolean(10, status);
            ps.executeUpdate();

            int id = 0;
            String sql1 = "Select @@IDENTITY as productId";
            PreparedStatement ps1 = connection.prepareStatement(sql1);
            ResultSet rs = ps1.executeQuery();
            if (rs.next()) {
                id = rs.getInt(1);
            }

            product.setId(id);

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

    public void updateThumbnailProduct(int productId, String fileUrl) {
        try {
            String sql = "UPDATE [dbo].[Product]\n"
                    + "   SET \n"
                    + "      [thumbnail] = ?\n"
                    + " WHERE id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, fileUrl);
            ps.setInt(2, productId);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ProductDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addAttachedImageProduct(int productId, String fileUrl) {
        try {
            connection.setAutoCommit(false);
            String sql = "INSERT INTO [dbo].[Image]\n"
                    + "           ([image])\n"
                    + "     VALUES\n"
                    + "           (?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, fileUrl);
            ps.executeUpdate();

            int imgId = 0;
            String sql1 = "Select @@IDENTITY as imageId";
            PreparedStatement ps1 = connection.prepareStatement(sql1);
            ResultSet rs = ps1.executeQuery();
            if (rs.next()) {
                imgId = rs.getInt(1);
            }

            String sql2 = "INSERT INTO [dbo].[Product_Image]\n"
                    + "           ([productId]\n"
                    + "           ,[imageId])\n"
                    + "     VALUES\n"
                    + "           (?\n"
                    + "           ,?)";
            PreparedStatement ps2 = connection.prepareStatement(sql2);
            ps2.setInt(1, productId);
            ps2.setInt(2, imgId);
            ps2.executeUpdate();
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
    }

    public int getTotalRecord() {
        try {
            String sql = "select Count(*) from Product";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public ArrayList<Product> getListProductFilter(int categoryId, int subCategoryId, String status, String featured, String search, String orderBy, String sort) {

        ArrayList<Product> products = new ArrayList<>();
        try {
            String sql = "DECLARE @Col_Name VARCHAR(128) = " + "'" + orderBy + "'" + "\n";
            sql += "SELECT [Product].[id]\n"
                    + "      ,[Product].[name]\n"
                    + "      ,[description]\n"
                    + "      ,[price]\n"
                    + "      ,[discount]\n"
                    + "      ,[sellerId]\n"
                    + "      ,[featured]\n"
                    + "      ,[thumbnail]\n"
                    + "      ,[date]\n"
                    + "      ,[subCategoryId]\n"
                    + "      ,[SubCategory].[name] as [subCatogryName]\n"
                    + "      ,[Category].id as categoryId\n"
                    + "      ,[Category].[name] as [catogryName]\n"
                    + "      ,[quantity]\n"
                    + "      ,[status]\n"
                    + "	  ,[price] * [discount] as [salePrice]\n"
                    + "  FROM [dbo].[Product] join SubCategory on subCategoryId = SubCategory.id\n"
                    + "			join Category on SubCategory.categoryId = Category.id\n"
                    + "WHERE (1=1)";
            int paramIndex = 0;
            HashMap<Integer, Object[]> params = new HashMap<>();
            if (categoryId != 0) {
                sql += " and categoryId = ?\n";
                paramIndex++;
                Object[] param = new Object[2];
                param[0] = Integer.class.getName();
                param[1] = categoryId;
                params.put(paramIndex, param);
            }

            if (subCategoryId != 0) {
                sql += " and subCategoryId = ?\n";
                paramIndex++;
                Object[] param = new Object[2];
                param[0] = Integer.class.getName();
                param[1] = subCategoryId;
                params.put(paramIndex, param);
            }

            if (!status.equals("all")) {
                sql += " and [status] = ?\n";
                paramIndex++;
                Object[] param = new Object[2];
                param[0] = Boolean.class.getName();
                param[1] = status.equals("active");
                params.put(paramIndex, param);
            }

            if (!featured.equals("all")) {
                sql += " and [featured] = ?\n";
                paramIndex++;
                Object[] param = new Object[2];
                param[0] = Boolean.class.getName();
                param[1] = featured.equals("active");
                params.put(paramIndex, param);
            }

            if (!search.equals("")) {
                sql += "and ([Product].[name] like ? or [Product].[id] = ?)\n";
                paramIndex++;
                Object[] paramProductName = new Object[2];
                paramProductName[0] = String.class.getName();
                paramProductName[1] = "%" + search + "%";
                params.put(paramIndex, paramProductName);

                int id;
                try {
                    id = Integer.parseInt(search);
                } catch (Exception e) {
                    id = -1;
                }

                paramIndex++;
                Object[] paramProductId = new Object[2];
                paramProductId[0] = Integer.class.getName();
                paramProductId[1] = id;
                params.put(paramIndex, paramProductId);

            }
            // sort
            sql += "ORDER BY CASE \n"
                    + "	WHEN @Col_Name = 'id' THEN CAST([Product].[id] AS SQL_VARIANT)\n"
                    + "	WHEN @Col_Name = 'category' THEN CAST([Category].[name]  AS SQL_VARIANT)\n"
                    + "	WHEN @Col_Name = 'subCategory' THEN CAST([SubCategory].[name]  AS SQL_VARIANT)\n"
                    + "	WHEN @Col_Name = 'originalPrice' THEN CAST([price]  AS SQL_VARIANT)\n"
                    + "	WHEN @Col_Name = 'featured' THEN CAST([featured]  AS SQL_VARIANT)\n"
                    + "	WHEN @Col_Name = 'status' THEN CAST([status]  AS SQL_VARIANT)\n"
                    + "END " + sort;

            PreparedStatement ps = connection.prepareStatement(sql);
            for (Map.Entry<Integer, Object[]> entry : params.entrySet()) {
                Integer index = entry.getKey();
                Object[] value = entry.getValue();
                String type = value[0].toString();
                if (type.equals(Integer.class.getName())) {
                    ps.setInt(index, (Integer) value[1]);
                }
                if (type.equals(Boolean.class.getName())) {
                    ps.setBoolean(index, (Boolean) value[1]);
                }
                if (type.equals(String.class.getName())) {
                    ps.setString(index, (String) value[1]);
                }
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Product p = new Product();
                p.setId(rs.getInt(1));
                p.setThumbnail(rs.getString(8));
                p.setName(rs.getString(2));
                Category category = new Category();
                category.setId(rs.getInt(12));
                category.setName(rs.getString(13));
                SubCategory subCategory = new SubCategory();
                subCategory.setCategory(category);
                subCategory.setId(rs.getInt(10));
                subCategory.setName(rs.getString(11));
                p.setSubCategory(subCategory);
                p.setPrice(rs.getLong(4));
                p.setDiscount(rs.getInt(5));
                p.setFeatured(rs.getBoolean(7));
                p.setStatus(rs.getBoolean(15));
                products.add(p);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProductDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return products;
    }

    public ArrayList<Product> getListProductByPage(ArrayList<Product> list, int start, int end) {
        ArrayList<Product> arr = new ArrayList<>();
        int totalRecord = list.size();
        if (totalRecord < end) {
            arr.addAll(list);
            return arr;
        }
        for (int i = start; i < end; i++) {
            arr.add(list.get(i));
        }
        return arr;
    }

    public void changeStatus(int id, boolean status) {
        try {
            String sql = "UPDATE [dbo].[Product]\n"
                    + "   SET  [status] = ?\n"
                    + " WHERE id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setBoolean(1, status);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ProductDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void changeFeatured(int id, boolean featured) {
        try {
            String sql = "UPDATE [dbo].[Product]\n"
                    + "   SET  [featured] = ?\n"
                    + " WHERE id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setBoolean(1, featured);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ProductDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
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

    public void updateImage(int id, String imgPath) {
        try {
            String sql = "UPDATE [dbo].[Image]\n"
                    + "   SET [image] = ?\n"
                    + " WHERE id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, imgPath);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ProductDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateProduct(int id, String name, String description, int sellerId, int subCategoryId, long price, int discount, long quantity, boolean featured, boolean status) {
        try {
            connection.setAutoCommit(false);
            String sql = "UPDATE [dbo].[Product]\n"
                    + "   SET [name] = ?\n"
                    + "      ,[description] = ?\n"
                    + "      ,[price] = ?\n"
                    + "      ,[discount] = ?\n"
                    + "      ,[sellerId] = ?\n"
                    + "      ,[featured] = ?\n"
                    + "      ,[date] = ?\n"
                    + "      ,[subCategoryId] = ?\n"
                    + "      ,[quantity] = ?\n"
                    + "      ,[status] = ?\n"
                    + " WHERE id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, description);
            ps.setLong(3, price);
            ps.setInt(4, discount);
            ps.setInt(5, sellerId);
            ps.setBoolean(6, featured);
            //
            java.util.Date date = new java.util.Date();
            Timestamp current = new Timestamp(date.getTime());
            ps.setTimestamp(7, current);
            //
            ps.setInt(8, subCategoryId);
            ps.setFloat(9, quantity);
            ps.setBoolean(10, status);
            ps.setInt(11, id);
            ps.executeUpdate();

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
    }

    public ArrayList<Product> getListProductFilterBySaleId(int categoryId, int subCategoryId, String status, String featured, String search, String orderBy, String sort, int sellerId) {

        ArrayList<Product> products = new ArrayList<>();
        try {
            String sql = "DECLARE @Col_Name VARCHAR(128) = " + "'" + orderBy + "'" + "\n";
            sql += "SELECT [Product].[id]\n"
                    + "      ,[Product].[name]\n"
                    + "      ,[description]\n"
                    + "      ,[price]\n"
                    + "      ,[discount]\n"
                    + "      ,[sellerId]\n"
                    + "      ,[featured]\n"
                    + "      ,[thumbnail]\n"
                    + "      ,[date]\n"
                    + "      ,[subCategoryId]\n"
                    + "      ,[SubCategory].[name] as [subCatogryName]\n"
                    + "      ,[Category].id as categoryId\n"
                    + "      ,[Category].[name] as [catogryName]\n"
                    + "      ,[quantity]\n"
                    + "      ,[status]\n"
                    + "	  ,[price] * [discount] as [salePrice]\n"
                    + "  FROM [dbo].[Product] join SubCategory on subCategoryId = SubCategory.id\n"
                    + "			join Category on SubCategory.categoryId = Category.id\n"
                    + "WHERE (1=1) and [sellerId] = " + sellerId + "\n";
            int paramIndex = 0;
            HashMap<Integer, Object[]> params = new HashMap<>();
            if (categoryId != 0) {
                sql += " and categoryId = ?\n";
                paramIndex++;
                Object[] param = new Object[2];
                param[0] = Integer.class.getName();
                param[1] = categoryId;
                params.put(paramIndex, param);
            }

            if (subCategoryId != 0) {
                sql += " and subCategoryId = ?\n";
                paramIndex++;
                Object[] param = new Object[2];
                param[0] = Integer.class.getName();
                param[1] = subCategoryId;
                params.put(paramIndex, param);
            }

            if (!status.equals("all")) {
                sql += " and [status] = ?\n";
                paramIndex++;
                Object[] param = new Object[2];
                param[0] = Boolean.class.getName();
                param[1] = status.equals("active");
                params.put(paramIndex, param);
            }

            if (!featured.equals("all")) {
                sql += " and [featured] = ?\n";
                paramIndex++;
                Object[] param = new Object[2];
                param[0] = Boolean.class.getName();
                param[1] = featured.equals("active");
                params.put(paramIndex, param);
            }

            if (!search.equals("")) {
                sql += "and ([Product].[name] like ? or [Product].[id] = ?)\n";
                paramIndex++;
                Object[] paramProductName = new Object[2];
                paramProductName[0] = String.class.getName();
                paramProductName[1] = "%" + search + "%";
                params.put(paramIndex, paramProductName);

                int id;
                try {
                    id = Integer.parseInt(search);
                } catch (Exception e) {
                    id = -1;
                }

                paramIndex++;
                Object[] paramProductId = new Object[2];
                paramProductId[0] = Integer.class.getName();
                paramProductId[1] = id;
                params.put(paramIndex, paramProductId);

            }
            // sort
            sql += "ORDER BY CASE \n"
                    + "	WHEN @Col_Name = 'id' THEN CAST([Product].[id] AS SQL_VARIANT)\n"
                    + "	WHEN @Col_Name = 'category' THEN CAST([Category].[name]  AS SQL_VARIANT)\n"
                    + "	WHEN @Col_Name = 'subCategory' THEN CAST([SubCategory].[name]  AS SQL_VARIANT)\n"
                    + "	WHEN @Col_Name = 'originalPrice' THEN CAST([price]  AS SQL_VARIANT)\n"
                    + "	WHEN @Col_Name = 'featured' THEN CAST([featured]  AS SQL_VARIANT)\n"
                    + "	WHEN @Col_Name = 'status' THEN CAST([status]  AS SQL_VARIANT)\n"
                    + "END " + sort;

            PreparedStatement ps = connection.prepareStatement(sql);
            for (Map.Entry<Integer, Object[]> entry : params.entrySet()) {
                Integer index = entry.getKey();
                Object[] value = entry.getValue();
                String type = value[0].toString();
                if (type.equals(Integer.class.getName())) {
                    ps.setInt(index, (Integer) value[1]);
                }
                if (type.equals(Boolean.class.getName())) {
                    ps.setBoolean(index, (Boolean) value[1]);
                }
                if (type.equals(String.class.getName())) {
                    ps.setString(index, (String) value[1]);
                }
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Product p = new Product();
                p.setId(rs.getInt(1));
                p.setThumbnail(rs.getString(8));
                p.setName(rs.getString(2));
                Category category = new Category();
                category.setId(rs.getInt(12));
                category.setName(rs.getString(13));
                SubCategory subCategory = new SubCategory();
                subCategory.setCategory(category);
                subCategory.setId(rs.getInt(10));
                subCategory.setName(rs.getString(11));
                p.setSubCategory(subCategory);
                p.setPrice(rs.getLong(4));
                p.setDiscount(rs.getInt(5));
                p.setFeatured(rs.getBoolean(7));
                p.setStatus(rs.getBoolean(15));
                products.add(p);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProductDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return products;
    }
    
    public ArrayList<Product> getListProductFilterBySaleManage(int categoryId, int subCategoryId, String status, String featured, String search, String orderBy, String sort) {

        ArrayList<Product> products = new ArrayList<>();
        try {
            String sql = "DECLARE @Col_Name VARCHAR(128) = " + "'" + orderBy + "'" + "\n";
            sql += "SELECT [Product].[id]\n"
                    + "      ,[Product].[name]\n"
                    + "      ,[description]\n"
                    + "      ,[price]\n"
                    + "      ,[discount]\n"
                    + "      ,[sellerId]\n"
                    + "      ,[featured]\n"
                    + "      ,[thumbnail]\n"
                    + "      ,[date]\n"
                    + "      ,[subCategoryId]\n"
                    + "      ,[SubCategory].[name] as [subCatogryName]\n"
                    + "      ,[Category].id as categoryId\n"
                    + "      ,[Category].[name] as [catogryName]\n"
                    + "      ,[quantity]\n"
                    + "      ,[status]\n"
                    + "	  ,[price] * [discount] as [salePrice]\n"
                    + "  FROM [dbo].[Product] join SubCategory on subCategoryId = SubCategory.id\n"
                    + "			join Category on SubCategory.categoryId = Category.id\n"
                    + "WHERE (1=1) \n";
            int paramIndex = 0;
            HashMap<Integer, Object[]> params = new HashMap<>();
            if (categoryId != 0) {
                sql += " and categoryId = ?\n";
                paramIndex++;
                Object[] param = new Object[2];
                param[0] = Integer.class.getName();
                param[1] = categoryId;
                params.put(paramIndex, param);
            }

            if (subCategoryId != 0) {
                sql += " and subCategoryId = ?\n";
                paramIndex++;
                Object[] param = new Object[2];
                param[0] = Integer.class.getName();
                param[1] = subCategoryId;
                params.put(paramIndex, param);
            }

            if (!status.equals("all")) {
                sql += " and [status] = ?\n";
                paramIndex++;
                Object[] param = new Object[2];
                param[0] = Boolean.class.getName();
                param[1] = status.equals("active");
                params.put(paramIndex, param);
            }

            if (!featured.equals("all")) {
                sql += " and [featured] = ?\n";
                paramIndex++;
                Object[] param = new Object[2];
                param[0] = Boolean.class.getName();
                param[1] = featured.equals("active");
                params.put(paramIndex, param);
            }

            if (!search.equals("")) {
                sql += "and ([Product].[name] like ? or [Product].[id] = ?)\n";
                paramIndex++;
                Object[] paramProductName = new Object[2];
                paramProductName[0] = String.class.getName();
                paramProductName[1] = "%" + search + "%";
                params.put(paramIndex, paramProductName);

                int id;
                try {
                    id = Integer.parseInt(search);
                } catch (Exception e) {
                    id = -1;
                }

                paramIndex++;
                Object[] paramProductId = new Object[2];
                paramProductId[0] = Integer.class.getName();
                paramProductId[1] = id;
                params.put(paramIndex, paramProductId);

            }
            // sort
            sql += "ORDER BY CASE \n"
                    + "	WHEN @Col_Name = 'id' THEN CAST([Product].[id] AS SQL_VARIANT)\n"
                    + "	WHEN @Col_Name = 'category' THEN CAST([Category].[name]  AS SQL_VARIANT)\n"
                    + "	WHEN @Col_Name = 'subCategory' THEN CAST([SubCategory].[name]  AS SQL_VARIANT)\n"
                    + "	WHEN @Col_Name = 'originalPrice' THEN CAST([price]  AS SQL_VARIANT)\n"
                    + "	WHEN @Col_Name = 'featured' THEN CAST([featured]  AS SQL_VARIANT)\n"
                    + "	WHEN @Col_Name = 'status' THEN CAST([status]  AS SQL_VARIANT)\n"
                    + "END " + sort;

            PreparedStatement ps = connection.prepareStatement(sql);
            for (Map.Entry<Integer, Object[]> entry : params.entrySet()) {
                Integer index = entry.getKey();
                Object[] value = entry.getValue();
                String type = value[0].toString();
                if (type.equals(Integer.class.getName())) {
                    ps.setInt(index, (Integer) value[1]);
                }
                if (type.equals(Boolean.class.getName())) {
                    ps.setBoolean(index, (Boolean) value[1]);
                }
                if (type.equals(String.class.getName())) {
                    ps.setString(index, (String) value[1]);
                }
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Product p = new Product();
                p.setId(rs.getInt(1));
                p.setThumbnail(rs.getString(8));
                p.setName(rs.getString(2));
                Category category = new Category();
                category.setId(rs.getInt(12));
                category.setName(rs.getString(13));
                SubCategory subCategory = new SubCategory();
                subCategory.setCategory(category);
                subCategory.setId(rs.getInt(10));
                subCategory.setName(rs.getString(11));
                p.setSubCategory(subCategory);
                p.setPrice(rs.getLong(4));
                p.setDiscount(rs.getInt(5));
                p.setFeatured(rs.getBoolean(7));
                p.setStatus(rs.getBoolean(15));
                products.add(p);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProductDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return products;
    }

    public void updateQuantityProductAvailable(Product[] productsOrder) {
        try {
            String sqlGetProducts = "select id, quantity from Product\n"
                    + "where id = ? ";
            for (Product product : productsOrder) {
                PreparedStatement stm = connection.prepareStatement(sqlGetProducts);
                stm.setInt(1, product.getId());
                ResultSet rs = stm.executeQuery();
                if (rs.next()) {
                    long quantity = product.getQuantity();
                    product.setQuantity(rs.getLong(2) - quantity);
                }
            }
            String sqlUpdateQuantityProduct = "UPDATE [dbo].[Product]\n"
                    + "   SET [quantity] = ?\n"
                    + " WHERE id = ?";
            for (Product product : productsOrder) {
                PreparedStatement stm2 = connection.prepareStatement(sqlUpdateQuantityProduct);
                stm2.setLong(1, product.getQuantity());
                stm2.setInt(2, product.getId());
                stm2.executeUpdate();
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProductDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
