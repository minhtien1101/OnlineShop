/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Post;
import model.CategoryPost;
import model.SubCategoryPost;
import model.User;

/**
 *
 * @author DELL
 */
public class PostDBContext extends DBContext {

    public ArrayList<Post> getHotPost() {
        ArrayList<Post> listPost = new ArrayList<>();
        try {
            String sql = "select top 3 * from Post\n"
                    + "where feature = 1 and status = 1\n"
                    + "order by dateUpdated desc";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Post post = new Post();
                post.setId(rs.getInt(1));
                post.setThumbnail(rs.getString(2));
                post.setTitle(rs.getString(3));
                SubCategoryPost category = new SubCategoryPost();
                category.setId(rs.getInt(4));
                post.setPostCategory(category);
                post.setBriefInfo(rs.getString(5));
                post.setDescription(rs.getString(6));
                post.setFeatured(rs.getBoolean(7));
                post.setDate(rs.getDate(8));
                post.setStatus(rs.getBoolean(9));
                User user = new User();
                user.setId(rs.getInt(10));
                post.setUser(user);
                listPost.add(post);
            }
        } catch (SQLException e) {
        }
        return listPost;
    }

    public ArrayList<Post> getLatestPost() {
        ArrayList<Post> listPost = new ArrayList<>();
        try {
            String sql = "select top 3 * from Post\n"
                    + "where status = 1\n"
                    + "order by dateUpdated desc";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Post post = new Post();
                post.setId(rs.getInt(1));
                post.setThumbnail(rs.getString(2));
                post.setTitle(rs.getString(3));
                SubCategoryPost category = new SubCategoryPost();
                category.setId(rs.getInt(4));
                post.setPostCategory(category);
                post.setBriefInfo(rs.getString(5));
                post.setDescription(rs.getString(6));
                post.setFeatured(rs.getBoolean(7));
                post.setDate(rs.getDate(8));
                post.setStatus(rs.getBoolean(9));
                User user = new User();
                user.setId(rs.getInt(10));
                post.setUser(user);
                listPost.add(post);
            }
        } catch (SQLException e) {
        }
        return listPost;
    }

    public ArrayList<Post> getListPostFiltered(String searchContent, int idSubCategory, int pageIndex, int pageSize) {
        ArrayList<Post> listPost = new ArrayList<>();
        try {
            String sql = "Select * from\n"
                    + "(Select p.*, c.name, c.idcategory , ROW_NUMBER() over(order by dateUpdated DESC) as row_index\n"
                    + "from Post p join SubCategoryPost c\n"
                    + "on p.categoryId = c.id and p.status = 1\n";
            if (idSubCategory != -1) {
                sql += "and p.categoryId = ? ";
            }
            if (!searchContent.isEmpty()) {
                sql += "and (p.title like ? or p.briefInfo like ?)";
            }
            sql += ") PostPaging\n"
                    + "where row_index >= (?-1)*?+1 and row_index <= ? * ?";

            PreparedStatement stm = connection.prepareStatement(sql);
            if (idSubCategory != -1 && searchContent.trim().isEmpty()) {
                stm.setInt(1, idSubCategory);
                stm.setInt(2, pageIndex);
                stm.setInt(3, pageSize);
                stm.setInt(4, pageIndex);
                stm.setInt(5, pageSize);
            } else if (idSubCategory == -1 && !searchContent.trim().isEmpty()) {
                stm.setString(1, "%" + searchContent + "%");
                stm.setString(2, "%" + searchContent + "%");
                stm.setInt(3, pageIndex);
                stm.setInt(4, pageSize);
                stm.setInt(5, pageIndex);
                stm.setInt(6, pageSize);
            } else if (idSubCategory == -1 && searchContent.trim().isEmpty()) {
                stm.setInt(1, pageIndex);
                stm.setInt(2, pageSize);
                stm.setInt(3, pageIndex);
                stm.setInt(4, pageSize);
            } else {
                stm.setInt(1, idSubCategory);
                stm.setString(2, "%" + searchContent + "%");
                stm.setString(3, "%" + searchContent + "%");
                stm.setInt(4, pageIndex);
                stm.setInt(5, pageSize);
                stm.setInt(6, pageIndex);
                stm.setInt(7, pageSize);
            }
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Post post = new Post();
                post.setId(rs.getInt(1));
                post.setThumbnail(rs.getString(2));
                post.setTitle(rs.getString(3));
                SubCategoryPost subCategory = new SubCategoryPost();
                subCategory.setId(rs.getInt(4));
                post.setBriefInfo(rs.getString(5));
                post.setDescription(rs.getString(6));
                post.setFeatured(rs.getBoolean(7));
                post.setDate(rs.getDate(8));
                post.setStatus(rs.getBoolean(9));
                User user = new UserDBContext().findUserById(rs.getInt(10));
                subCategory.setName(rs.getString(11));
                CategoryPost categoryPost = new CategoryPost();
                categoryPost.setId(rs.getInt(12));
                subCategory.setCategory(categoryPost);
                post.setPostCategory(subCategory);
                if (user == null) {
                    post.setUser(new User());
                } else {
                    post.setUser(user);
                }
                listPost.add(post);
            }
        } catch (SQLException e) {
        }
        return listPost;
    }

    public int numberRowListPost(String searchContent, int idCategory) {
        try {
            String sql = "Select count(*) as numberRow from Post\n";
            if (!searchContent.trim().isEmpty() && idCategory == -1) {
                sql += "where (title like ? or briefInfo like ?) and [status] = 1";
            } else if (searchContent.trim().isEmpty() && idCategory != -1) {
                sql += "where categoryId = ? and [status] = 1";
            } else if (!searchContent.trim().isEmpty() && idCategory != -1) {
                sql += "where (title like ? or briefInfo like ?) and categoryId = ? and [status] = 1";
            } else {
                sql += " where [status] = 1 ";
            }

            PreparedStatement stm = connection.prepareStatement(sql);

            if (!searchContent.trim().isEmpty() && idCategory == -1) {
                stm.setString(1, "%" + searchContent + "%");
                stm.setString(2, "%" + searchContent + "%");
            } else if (searchContent.trim().isEmpty() && idCategory != -1) {
                stm.setInt(1, idCategory);
            } else if (!searchContent.trim().isEmpty() && idCategory != -1) {
                stm.setString(1, "%" + searchContent + "%");
                stm.setString(2, "%" + searchContent + "%");
                stm.setInt(3, idCategory);
            }
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
        }
        return 0;
    }

    public ArrayList<CategoryPost> getAllCategory() {
        ArrayList<CategoryPost> listCategory = new ArrayList<>();
        try {
            String sql = "Select id, name from CategoryPost";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                CategoryPost category = new CategoryPost();
                category.setId(rs.getInt(1));
                category.setName(rs.getString(2));
                ArrayList<SubCategoryPost> listSubPost = getListSubPostById(rs.getInt(1));
                category.setListSubPost(listSubPost);
                listCategory.add(category);
            }
        } catch (SQLException e) {
        }
        return listCategory;
    }

    public ArrayList<SubCategoryPost> getListSubPostById(int idCategory) {
        ArrayList<SubCategoryPost> listCategory = new ArrayList<>();
        try {
            String sql = "Select * from SubCategoryPost Where idCategory = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, idCategory);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                SubCategoryPost subCategory = new SubCategoryPost();
                subCategory.setId(rs.getInt(1));
                subCategory.setName(rs.getString(2));
                listCategory.add(subCategory);
            }
        } catch (SQLException e) {
        }
        return listCategory;
    }

    public Post getPostById(int id) {
        String sql = "SELECT Post.id\n"
                + "	  ,[thumbnail]\n"
                + "      ,[title]\n"
                + "      ,[briefInfo]\n"
                + "      ,[description]\n"
                + "      ,[feature]\n"
                + "      ,[dateUpdated]\n"
                + "      ,Post.[status]\n"
                + "      ,[UserId]\n"
                + "	  ,[User].fullname\n"
                + "	  ,SubCategoryPost.id\n"
                + "	  ,SubCategoryPost.name\n"
                + "  FROM [dbo].[Post] join SubCategoryPost on Post.categoryId = SubCategoryPost.id\n"
                + "					join [User] on Post.UserId = [User].id\n"
                + "  WHERE Post.id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Post post = new Post();
                User user = new User();
                SubCategoryPost postCategory = new SubCategoryPost();
                post.setId(rs.getInt(1));
                post.setThumbnail(rs.getString(2));
                post.setTitle(rs.getString(3));
                post.setBriefInfo(rs.getString(4));
                post.setDescription(rs.getString(5));
                post.setFeatured(rs.getBoolean(6));
                post.setDate(rs.getDate(7));
                post.setStatus(rs.getBoolean(8));
                user.setId(rs.getInt(9));
                user.setFullname(rs.getString(10));
                post.setUser(user);
                postCategory.setId(rs.getInt(11));
                postCategory.setName(rs.getString(12));
                post.setPostCategory(postCategory);

                return post;
            }
        } catch (SQLException ex) {
            Logger.getLogger(PostDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public int getPostNumber() {
        int postNumber = 0;
        try {
            String sql = "select COUNT(*) num from post";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                postNumber = rs.getInt("num");
            }
        } catch (SQLException e) {
        }
        return postNumber;
    }
    


    public ArrayList<Post> getAllPostFiltered(int idCategory, int idAuthor, int idStatus, String searchBy, String orderBy, String sortBy) {
        ArrayList<Post> listPosts = new ArrayList<>();
        try {
            String sql = "DECLARE @Col_Name VARCHAR(128) = " + "'" + orderBy + "'" + "\n"
                    + "Select p.id, p.thumbnail, p.title, p.feature, p.[status], p.UserId, s.email, s.fullname, p.categoryId as idSubCategory, sp.[name] as nameSubCategory, cp.id as idCategoryPost, cp.[name] as nameCategoryPost\n"
                    + "                    From post p join [user] s\n"
                    + "                    on p.UserId = s.id\n"
                    + "                    join SubCategoryPost sp on p.categoryId = sp.id\n"
                    + "                    join CategoryPost cp on cp.id = sp.idcategory\n";
            int index = 0;
            HashMap<Integer, Object[]> params = new HashMap<>();
            if (idCategory != -1) {
                sql += " and cp.id = ?\n";
                index++;
                Object[] param = new Object[2];
                param[0] = Integer.class.getName();
                param[1] = idCategory;
                params.put(index, param);
            }
            if (idAuthor != -1) {
                sql += " and p.UserId = ?\n";
                index++;
                Object[] param = new Object[2];
                param[0] = Integer.class.getName();
                param[1] = idAuthor;
                params.put(index, param);
            }
            if (idStatus != -1) {
                sql += " and p.[status] = ?\n";
                index++;
                Object[] param1 = new Object[2];
                param1[0] = Boolean.class.getName();
                param1[1] = (idStatus == 1);
                params.put(index, param1);
            }

            if (!searchBy.equals("")) {
                sql += " and ( p.title like ?\n";
                index++;
                Object[] searchContent = new Object[2];
                searchContent[0] = String.class.getName();
                searchContent[1] = "%" + searchBy + "%";
                params.put(index, searchContent);
                sql += "or s.fullname like ?)\n";
                index++;
                Object[] searchAuthor = new Object[2];
                searchAuthor[0] = String.class.getName();
                searchAuthor[1] = "%" + searchBy + "%";
                params.put(index, searchAuthor);
            }
            sql += "ORDER BY CASE \n"
                    + "                    WHEN @Col_Name = 'title' THEN CAST(p.title AS SQL_VARIANT)\n"
                    + "                    WHEN @Col_Name = 'category' THEN CAST(cp.id AS SQL_VARIANT)\n"
                    + "                    WHEN @Col_Name = 'author' THEN CAST(p.UserId AS SQL_VARIANT)\n"
                    + "                    WHEN @Col_Name = 'featured' THEN CAST(p.feature AS SQL_VARIANT)\n"
                    + "                    WHEN @Col_Name = 'status' THEN CAST(p.status AS SQL_VARIANT)\n"
                    + "                     END\n" + sortBy;
            PreparedStatement stm = connection.prepareStatement(sql);
            for (Map.Entry<Integer, Object[]> entry : params.entrySet()) {
                Integer indexes = entry.getKey();
                Object[] value = entry.getValue();
                String type = value[0].toString();
                if (type.equalsIgnoreCase(Integer.class.getName())) {
                    stm.setInt(indexes, (Integer) value[1]);
                } else if (type.equalsIgnoreCase(Boolean.class.getName())) {
                    stm.setBoolean(indexes, (Boolean) value[1]);
                } else if (type.equalsIgnoreCase(String.class.getName())) {
                    stm.setString(indexes, (String) value[1]);
                }
            }
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Post post = new Post();
                post.setId(rs.getInt(1));
                post.setThumbnail(rs.getString(2));
                post.setTitle(rs.getString(3));
                post.setFeatured(rs.getBoolean(4));
                post.setStatus(rs.getBoolean(5));

                User author = new User();
                author.setId(rs.getInt(6));
                author.setEmail(rs.getString(7));
                author.setFullname(rs.getString(8));
                post.setUser(author);

                SubCategoryPost subCategory = new SubCategoryPost();
                subCategory.setId(rs.getInt(9));
                subCategory.setName(rs.getString(10));
                CategoryPost category = new CategoryPost();
                category.setId(rs.getInt(11));
                category.setName(rs.getString(12));
                subCategory.setCategory(category);
                post.setPostCategory(subCategory);

                listPosts.add(post);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PostDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listPosts;
    }

    public void editStatusPost(int id, boolean status) {
        try {
            String sql = "UPDATE [dbo].[Post]\n"
                    + "   SET [status] = ?\n"
                    + " WHERE id = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setBoolean(1, status);
            stm.setInt(2, id);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PostDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void editFeaturedPost(int id, boolean featured) {
        try {
            String sql = "UPDATE [dbo].[Post]\n"
                    + "   SET [feature] = ?\n"
                    + " WHERE id = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setBoolean(1, featured);
            stm.setInt(2, id);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PostDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void insertPost(String title, String urlImage, String brief,
            String description, Date dateUpdate, int idSubCategory,
            boolean featured, boolean status, int idUser) {
        try {
            String sql = "INSERT INTO [dbo].[Post]\n"
                    + "           ([thumbnail]\n"
                    + "           ,[title]\n"
                    + "           ,[categoryId]\n"
                    + "           ,[briefInfo]\n"
                    + "           ,[description]\n"
                    + "           ,[feature]\n"
                    + "           ,[dateUpdated]\n"
                    + "           ,[status]\n"
                    + "           ,[UserId])\n"
                    + "     VALUES\n"
                    + "           (?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?)";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, urlImage);
            stm.setString(2, title);
            stm.setInt(3, idSubCategory);
            stm.setString(4, brief);
            stm.setString(5, description);
            stm.setBoolean(6, featured);
            stm.setDate(7, dateUpdate);
            stm.setBoolean(8, status);
            stm.setInt(9, idUser);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PostDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public ArrayList<Post> listPostPaging(ArrayList<Post> listPosts, int start, int end) {
        ArrayList<Post> arr = new ArrayList<>();
        if (!listPosts.isEmpty()) {
            for (int i = start; i < end; i++) {
                arr.add(listPosts.get(i));
            }
        }
        return arr;
    }

    public Post getPostByIdIncludeCategory(int idPost) {
        try {
            String sql = "Select p.id, p.thumbnail, p.title,p.briefInfo, p.[description], p.feature, "
                    + "p.[status], p.UserId, s.email, s.fullname, p.categoryId as idSubCategory, "
                    + "sp.[name] as nameSubCategory, cp.id as idCategoryPost, "
                    + "cp.[name] as nameCategoryPost, p.dateUpdated\n"
                    + "From post p join [user] s on p.UserId = s.id\n"
                    + "join SubCategoryPost sp on p.categoryId = sp.id\n"
                    + "join CategoryPost cp on cp.id = sp.idcategory\n"
                    + "and p.id = ?\n";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, idPost);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                Post post = new Post();
                post.setId(rs.getInt(1));
                post.setThumbnail(rs.getString(2));
                post.setTitle(rs.getString(3));
                post.setBriefInfo(rs.getString(4));
                post.setDescription(rs.getString(5));
                post.setFeatured(rs.getBoolean(6));
                post.setStatus(rs.getBoolean(7));

                User author = new User();
                author.setId(rs.getInt(8));
                author.setEmail(rs.getString(9));
                author.setFullname(rs.getString(10));
                post.setUser(author);

                SubCategoryPost subCategory = new SubCategoryPost();
                subCategory.setId(rs.getInt(11));
                subCategory.setName(rs.getString(12));
                CategoryPost category = new CategoryPost();
                category.setId(rs.getInt(13));
                category.setName(rs.getString(14));
                subCategory.setCategory(category);
                post.setPostCategory(subCategory);
                post.setDate(rs.getDate(15));
                return post;
            }
        } catch (SQLException ex) {
            Logger.getLogger(PostDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void editPost(int id, String urlImage, String title, String briefInfo,
            String description, Date dateNow, int idUser, boolean featured,
            boolean status, int category, int subCategory) {
        try {
            String sql = "UPDATE [dbo].[Post]\n"
                    + "   SET \n"
                    + "      [title] = ?\n"
                    + "      ,[categoryId] = ?\n"
                    + "      ,[briefInfo] = ?\n"
                    + "      ,[description] = ?\n"
                    + "      ,[feature] = ?\n"
                    + "      ,[dateUpdated] = ?\n"
                    + "      ,[status] = ?\n"
                    + "      ,[UserId] = ?\n";

            if (!urlImage.equalsIgnoreCase("")) {
                sql += "     ,[thumbnail] = " + "'" + urlImage + "'" + "\n";
            }
            sql += " WHERE [id] = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, title);
            stm.setInt(2, subCategory);
            stm.setString(3, briefInfo);
            stm.setString(4, description);
            stm.setBoolean(5, featured);
            stm.setDate(6, dateNow);
            stm.setBoolean(7, status);
            stm.setInt(8, idUser);
            stm.setInt(9, id);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PostDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        PostDBContext db = new PostDBContext();
        System.out.println(db.getPostById(2));
    }
}
