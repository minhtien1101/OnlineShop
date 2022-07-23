/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import configs.Security;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Product;
import model.Role;
import model.User;

/**
 *
 * @author Admin
 */
public class UserDBContext extends DBContext {

    public User findUserById(int id) {
        try {
            String sql = "Select * From [User]\n"
                    + "Where id = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt(1));
                user.setPassword(rs.getString(2));
                user.setAvatar(rs.getString(3));
                user.setFullname(rs.getString(4));
                user.setGender(rs.getBoolean(5));
                user.setMobile(rs.getString(6));
                user.setAddress(rs.getString(7));
                Role role = new Role();
                role.setId(rs.getInt(8));
                user.setRole(role);
                user.setStatus(rs.getBoolean(9));
                user.setUsername(rs.getString(10));
                user.setEmail(rs.getString(11));
                return user;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList<User> getAllUser() {
        ArrayList<User> users = new ArrayList<>();
        String sql = "SELECT [fullname]\n"
                + "      ,[password]\n"
                + "      ,[gender]\n"
                + "      ,[email]\n"
                + "      ,[mobile]\n"
                + "      ,[address]\n"
                + "      ,[roleId]\n"
                + "      ,Role.name\n"
                + "      ,[User].[status]\n"
                + "      ,[User].id\n"
                + "  FROM [User] join Role on roleId = Role.id";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setFullname(rs.getString(1));
                user.setGender(rs.getBoolean(3));
                user.setEmail(rs.getString(4));
                user.setMobile(rs.getString(5));
                user.setAddress(rs.getString(6));
                Role role = new Role();
                role.setId(rs.getInt(7));
                role.setName(rs.getString(8));
                user.setRole(role);
                user.setStatus(rs.getBoolean(9));
                user.setId(rs.getInt(10));
                users.add(user);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return users;
    }

    public ArrayList<User> getListByPage(ArrayList<User> list, int start, int end) {
        ArrayList<User> arr = new ArrayList<>();
        for (int i = start; i < end; i++) {
            arr.add(list.get(i));
        }
        return arr;
    }

    public ArrayList<User> getListUserFilter(int roleId, String gender, String status, String search) {
        ArrayList<User> users = new ArrayList<>();
        try {
            String sql = "SELECT [fullname]\n"
                    + "      ,[password]\n"
                    + "      ,[gender]\n"
                    + "      ,[email]\n"
                    + "      ,[mobile]\n"
                    + "      ,[address]\n"
                    + "      ,[roleId]\n"
                    + "      ,Role.name\n"
                    + "      ,[User].[status]\n"
                    + "      ,[User].id\n"
                    + "  FROM [User] join Role on roleId = Role.id\n"
                    + "  WHERE (1=1)\n";
            int paramIndex = 0;
            HashMap<Integer, Object[]> params = new HashMap<>();
            if (roleId != 0) {
                sql += " and roleId = ?\n";
                paramIndex++;
                Object[] param = new Object[2];
                param[0] = Integer.class.getName();
                param[1] = roleId;
                params.put(paramIndex, param);
            }
            if (!gender.equals("all")) {
                sql += " and gender = ?\n";
                paramIndex++;
                Object[] param = new Object[2];
                param[0] = Boolean.class.getName();
                param[1] = gender.equals("male");
                params.put(paramIndex, param);
            }
            if (!status.equals("all")) {
                sql += " and [User].[status] = ?\n";
                paramIndex++;
                Object[] param = new Object[2];
                param[0] = Boolean.class.getName();
                param[1] = status.equals("active");
                params.put(paramIndex, param);
            }

            if (!search.equals("")) {
                sql += " and (fullname like ?\n"
                        + "or email like ?\n"
                        + "or mobile like ?)\n";
                paramIndex++;
                Object[] paramUsername = new Object[2];
                paramUsername[0] = String.class.getName();
                paramUsername[1] = "%" + search + "%";
                params.put(paramIndex, paramUsername);
                paramIndex++;
                Object[] paramEmail = new Object[2];
                paramEmail[0] = String.class.getName();
                paramEmail[1] = "%" + search + "%";
                params.put(paramIndex, paramEmail);
                paramIndex++;
                Object[] paramMobile = new Object[2];
                paramMobile[0] = String.class.getName();
                paramMobile[1] = "%" + search + "%";
                params.put(paramIndex, paramMobile);
            }

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
                User user = new User();
                user.setFullname(rs.getString(1));
                user.setGender(rs.getBoolean(3));
                user.setEmail(rs.getString(4));
                user.setMobile(rs.getString(5));
                user.setAddress(rs.getString(6));
                Role role = new Role();
                role.setId(rs.getInt(7));
                role.setName(rs.getString(8));
                user.setRole(role);
                user.setStatus(rs.getBoolean(9));
                user.setId(rs.getInt(10));
                users.add(user);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return users;
    }

    public void changeStatus(int id, boolean status) {
        try {
            String sql = "UPDATE [User]\n"
                    + "   SET [status] = ?\n"
                    + " WHERE id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setBoolean(1, status);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public User getUserByEmailAndPassword(String email, String password) {
        User user = null;
        try {
            String sql = "select u.*, r.name rname from [User] u, [role] r where email = ? and password = ? "
                    + "and u.roleId = r.id";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, email);
            stm.setString(2, password);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                user = new User(
                        rs.getString("password"),
                        rs.getString("avatar"),
                        rs.getString("email"),
                        rs.getString("fullname"),
                        rs.getBoolean("gender"),
                        rs.getString("mobile"),
                        rs.getString("address"),
                        rs.getBoolean("status"));

                user.setRole(new Role(rs.getInt("roleId"), rs.getString("rname")));
                user.setId(rs.getInt("id"));
            }
        } catch (Exception ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return user;
    }

    public User getUserByEmail(String email) {
        User user = null;
        try {
            String sql = "select u.*, r.name rname from [User] u, [role] r where email = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, email);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {

                user = new User(
                        rs.getString("password"),
                        rs.getString("avatar"),
                        rs.getString("email"),
                        rs.getString("fullname"),
                        rs.getBoolean("gender"),
                        rs.getString("mobile"),
                        rs.getString("address"),
                        rs.getBoolean("Status"));
                user.setUsername(rs.getString("username"));

                user.setRole(new Role(rs.getInt("roleId"), rs.getString("rname")));
                user.setId(rs.getInt("id"));

            }
        } catch (Exception ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return user;
    }

    public boolean updateUser(User user) {
        try {
            String sql = "update [User] set password=? where email = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, user.getPassword());
            stm.setString(2, user.getEmail());

            return stm.executeUpdate() > 0;
        } catch (Exception ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean updateUserInf(User user) {
        try {
            String sql = "update [User] set username = ?, mobile=?, address=? where email = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, user.getUsername());
            stm.setString(2, user.getMobile());
            stm.setString(3, user.getAddress());
            stm.setString(4, user.getEmail());

            return stm.executeUpdate() > 0;
        } catch (Exception ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean addUser(User user) {
        LocalDate date = LocalDate.now();
        try {
            String sql = "insert into [User]([password], email, fullname, username, gender, mobile, address, [Status], roleId, avatar, dateCreated)"
                    + "values (?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, user.getPassword());
            stm.setString(2, user.getEmail());
            stm.setString(3, user.getFullname());
            stm.setString(4, user.getFullname());
            stm.setBoolean(5, user.isGender());
            stm.setString(6, user.getMobile());
            stm.setString(7, user.getAddress());
            stm.setBoolean(8, Security.DEFAULT_STATUS);
            stm.setInt(9, Security.CUSTOMER_ROLL_ID);
            stm.setString(10, Security.EMAGE_DEFAULT);
            stm.setDate(11, Date.valueOf(date));

            return stm.executeUpdate() > 0;
        } catch (Exception ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public ArrayList<User> getListUserFilter(int roleId, String gender, String status, String search, String sort, String orderBy) {
        ArrayList<User> users = new ArrayList<>();
        try {
            String sql = "DECLARE @Col_Name VARCHAR(128) = " + "'" + sort + "'" + "\n";
            sql += "SELECT [fullname]\n"
                    + "      ,[password]\n"
                    + "      ,[gender]\n"
                    + "      ,[email]\n"
                    + "      ,[mobile]\n"
                    + "      ,[address]\n"
                    + "      ,[roleId]\n"
                    + "      ,Role.name\n"
                    + "      ,[User].[status]\n"
                    + "      ,[User].id\n"
                    + "  FROM [User] join [Role] on roleId = [Role].id\n"
                    + "  WHERE (1=1)\n";
            int paramIndex = 0;
            HashMap<Integer, Object[]> params = new HashMap<>();
            if (roleId != 0) {
                sql += " and roleId = ?\n";
                paramIndex++;
                Object[] param = new Object[2];
                param[0] = Integer.class.getName();
                param[1] = roleId;
                params.put(paramIndex, param);
            }
            if (!gender.equals("all")) {
                sql += " and gender = ?\n";
                paramIndex++;
                Object[] param = new Object[2];
                param[0] = Boolean.class.getName();
                param[1] = gender.equals("male");
                params.put(paramIndex, param);
            }
            if (!status.equals("all")) {
                sql += " and [User].status = ?\n";
                paramIndex++;
                Object[] param = new Object[2];
                param[0] = Boolean.class.getName();
                param[1] = status.equals("active");
                params.put(paramIndex, param);
            }

            if (!search.equals("")) {
                sql += " and (fullname like ?\n"
                        + "or email like ?\n"
                        + "or mobile like ?)\n";
                paramIndex++;
                Object[] paramUsername = new Object[2];
                paramUsername[0] = String.class.getName();
                paramUsername[1] = "%" + search + "%";
                params.put(paramIndex, paramUsername);
                paramIndex++;
                Object[] paramEmail = new Object[2];
                paramEmail[0] = String.class.getName();
                paramEmail[1] = "%" + search + "%";
                params.put(paramIndex, paramEmail);
                paramIndex++;
                Object[] paramMobile = new Object[2];
                paramMobile[0] = String.class.getName();
                paramMobile[1] = "%" + search + "%";
                params.put(paramIndex, paramMobile);
            }
            // sort
            sql += "ORDER BY CASE \n"
                    + "    WHEN @Col_Name = 'id' THEN CAST([User].id AS SQL_VARIANT)\n"
                    + "    WHEN @Col_Name = 'fullname' THEN CAST(fullname AS SQL_VARIANT)\n"
                    + "    WHEN @Col_Name = 'gender' THEN CAST(gender AS SQL_VARIANT)\n"
                    + "    WHEN @Col_Name = 'email' THEN CAST(email AS SQL_VARIANT)\n"
                    + "    WHEN @Col_Name = 'mobile' THEN CAST(mobile AS SQL_VARIANT)\n"
                    + "    WHEN @Col_Name = 'role' THEN CAST(roleId AS SQL_VARIANT)\n"
                    + "    WHEN @Col_Name = 'status' THEN CAST([User].status AS SQL_VARIANT)"
                    + " END " + orderBy;

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
                User user = new User();
                user.setFullname(rs.getString(1));
                user.setGender(rs.getBoolean(3));
                user.setEmail(rs.getString(4));
                user.setMobile(rs.getString(5));
                user.setAddress(rs.getString(6));
                Role role = new Role();
                role.setId(rs.getInt(7));
                role.setName(rs.getString(8));
                user.setRole(role);
                user.setStatus(rs.getBoolean(9));
                user.setId(rs.getInt(10));
                users.add(user);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return users;

    }

    public User getUserById(int id) {
        String sql = "SELECT [fullname]\n"
                + "	  ,[gender]\n"
                + "      ,[avatar]\n"
                + "      ,[email]\n"
                + "      ,[mobile]\n"
                + "      ,[address]\n"
                + "      ,[roleId]\n"
                + "	  ,[Role].[name]\n"
                + "      ,[User].[status]\n"
                + "  FROM [dbo].[User] join  [Role] on [User].roleId  = [Role].id\n"
                + "  WHERE [User].id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User u = new User();
                u.setFullname(rs.getString(1));
                u.setGender(rs.getBoolean(2));
//                //setting image
//                String base64Image = null;
//                Blob blob = rs.getBlob(3);
//                if (blob != null) {
//                    InputStream inputStream = blob.getBinaryStream();
//                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//                    byte[] buffer = new byte[4096];
//                    int bytesRead = -1;
//                    while ((bytesRead = inputStream.read(buffer)) != -1) {
//                        outputStream.write(buffer, 0, bytesRead);
//                    }
//                    byte[] imageBytes = outputStream.toByteArray();
//                    base64Image = Base64.getEncoder().encodeToString(imageBytes);
//                    inputStream.close();
//                    outputStream.close();
//                } else {
//                    base64Image = "default";
//                }
//                u.setAvatar(base64Image);
                //
                u.setAvatar(rs.getString(3));
                u.setEmail(rs.getString(4));
                u.setMobile(rs.getString(5));
                u.setAddress(rs.getString(6));
                Role role = new Role();
                role.setId(rs.getInt(7));
                role.setName(rs.getString(8));
                u.setRole(role);
                u.setStatus(rs.getBoolean(9));
                u.setId(id);

                System.out.println(u);
                return u;

            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void editUserProfile(int id, int roleId, boolean status) {
        try {
            String sql = "UPDATE [User]\n"
                    + "   SET \n"
                    + "      [status] = ?\n"
                    + "      ,roleId = ?\n"
                    + " WHERE id= ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setBoolean(1, status);
            ps.setInt(2, roleId);
            ps.setInt(3, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean checkAccountHaveEmailOrMobileExisted(String email, String mobile) {
        String sql = "SELECT *\n"
                + "FROM [User]\n"
                + "WHERE email = ? or mobile = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, mobile);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public void inserUser(String name, String password, boolean gender, String email, String mobile, String address, int role, boolean status) {
        try {
            String sql = "INSERT INTO [dbo].[User]\n"
                    + "           ([fullname]\n"
                    + "           ,[password]\n"
                    + "           ,[gender]\n"
                    + "           ,[email]\n"
                    + "           ,[mobile]\n"
                    + "           ,[address]\n"
                    + "           ,[roleId]\n"
                    + "           ,[status])\n"
                    + "     VALUES\n"
                    + "           (?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, password);
            ps.setBoolean(3, gender);
            ps.setString(4, email);
            ps.setString(5, mobile);
            ps.setString(6, address);
            ps.setInt(7, role);
            ps.setBoolean(8, status);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//    public ArrayList<User> getListUserFiltered(int roleId, String gender,
//            String status, String search, String sort, String orderBy, int pageindex, int pagesize) {
//        ArrayList<User> list = new ArrayList<>();
//        try {
//            String sql = "DECLARE @Col_Name VARCHAR(128) = " + "'" + sort + "'" + "\n";
//            sql += "SELECT fullname, [password],[gender],[email],[mobile],[address],[roleId],roleName, status, id\n"
//                    + "FROM (\n"
//                    + "	select ROW_NUMBER() over (order by [User].id asc) as  [row_number], \n"
//                    + "	[fullname],[password] ,[gender],[email] ,[mobile],[address] ,[roleId],Role.name as roleName,[User].[status],[User].id\n"
//                    + "	from [User] join [Role] on roleId = [Role].id\n"
//                    + "	Where(1=1)";
//            int paramIndex = 0;
//            HashMap<Integer, Object[]> params = new HashMap<>();
//            if (roleId != 0) {
//                sql += " and roleId = ?\n";
//                paramIndex++;
//                Object[] param = new Object[2];
//                param[0] = Integer.class.getName();
//                param[1] = roleId;
//                params.put(paramIndex, param);
//            }
//            if (!gender.equals("all")) {
//                sql += " and gender = ?\n";
//                paramIndex++;
//                Object[] param = new Object[2];
//                param[0] = Boolean.class.getName();
//                param[1] = gender.equals("male");
//                params.put(paramIndex, param);
//            }
//            if (!status.equals("all")) {
//                sql += " and [User].[status] = ?\n";
//                paramIndex++;
//                Object[] param = new Object[2];
//                param[0] = Boolean.class.getName();
//                param[1] = status.equals("active");
//                params.put(paramIndex, param);
//            }
//
//            if (!search.equals("")) {
//                sql += " and (fullname like ?\n"
//                        + "or email like ?\n"
//                        + "or mobile like ?)\n";
//                paramIndex++;
//                Object[] paramUsername = new Object[2];
//                paramUsername[0] = String.class.getName();
//                paramUsername[1] = "%" + search + "%";
//                params.put(paramIndex, paramUsername);
//                paramIndex++;
//                Object[] paramEmail = new Object[2];
//                paramEmail[0] = String.class.getName();
//                paramEmail[1] = "%" + search + "%";
//                params.put(paramIndex, paramEmail);
//                paramIndex++;
//                Object[] paramMobile = new Object[2];
//                paramMobile[0] = String.class.getName();
//                paramMobile[1] = "%" + search + "%";
//                params.put(paramIndex, paramMobile);
//            }
//
//            sql += "	) t\n"
//                    + "Where (1=1)";
//
//            if (pageindex != -1 || pagesize != -1) {
//                sql += "and t.[row_number] >= ((? - 1) * ?) + 1 and t.[row_number] <= ? * ?\n";
//                paramIndex++;
//                Object[] paramPageIndexStart = new Object[2];
//                paramPageIndexStart[0] = Integer.class.getName();
//                paramPageIndexStart[1] = pageindex;
//                params.put(paramIndex, paramPageIndexStart);
//
//                paramIndex++;
//                Object[] paramPageSizeStart = new Object[2];
//                paramPageSizeStart[0] = Integer.class.getName();
//                paramPageSizeStart[1] = pagesize;
//                params.put(paramIndex, paramPageSizeStart);
//                paramIndex++;
//
//                Object[] paramPageIndexEnd = new Object[2];
//                paramPageIndexEnd[0] = Integer.class.getName();
//                paramPageIndexEnd[1] = pageindex;
//                params.put(paramIndex, paramPageIndexEnd);
//
//                paramIndex++;
//                Object[] paramPageSizeEnd = new Object[2];
//                paramPageSizeEnd[0] = Integer.class.getName();
//                paramPageSizeEnd[1] = pagesize;
//                params.put(paramIndex, paramPageSizeEnd);
//            }
//
////            if (sort != null) {
////                sql += "order by ?  ?";
////                paramIndex++;
////                Object[] paramSort = new Object[2];
////                paramSort[0] = String.class.getName();
////                paramSort[1] = sort;
////                params.put(paramIndex, paramSort);
////                paramIndex++;
////                Object[] paramOrderBy = new Object[2];
////                paramOrderBy[0] = String.class.getName();
////                paramOrderBy[1] = orderBy;
////                params.put(paramIndex, paramOrderBy);
////            }
//            
//            sql += "ORDER BY CASE \n"
//                    + "    WHEN @Col_Name = 'id' THEN CAST(id AS SQL_VARIANT)\n"
//                    + "    WHEN @Col_Name = 'fullname' THEN CAST(fullname AS SQL_VARIANT)\n"
//                    + "    WHEN @Col_Name = 'gender' THEN CAST(gender AS SQL_VARIANT)\n"
//                    + "    WHEN @Col_Name = 'email' THEN CAST(email AS SQL_VARIANT)\n"
//                    + "    WHEN @Col_Name = 'mobile' THEN CAST(mobile AS SQL_VARIANT)\n"
//                    + "    WHEN @Col_Name = 'role' THEN CAST(roleId AS SQL_VARIANT)\n"
//                    + "    WHEN @Col_Name = 'status' THEN CAST(status AS SQL_VARIANT)"
//                    + " END " + orderBy;
//
//            PreparedStatement ps = connection.prepareStatement(sql);
//            for (Map.Entry<Integer, Object[]> entry : params.entrySet()) {
//                Integer index = entry.getKey();
//                Object[] value = entry.getValue();
//                String type = value[0].toString();
//                if (type.equals(Integer.class.getName())) {
//                    ps.setInt(index, (Integer) value[1]);
//                }
//                if (type.equals(Boolean.class.getName())) {
//                    ps.setBoolean(index, (Boolean) value[1]);
//                }
//                if (type.equals(String.class.getName())) {
//                    ps.setString(index, (String) value[1]);
//                }
//            }
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                User user = new User();
//                user.setFullname(rs.getString(1));
//                user.setGender(rs.getBoolean(3));
//                user.setEmail(rs.getString(4));
//                user.setMobile(rs.getString(5));
//                user.setAddress(rs.getString(6));
//                Role role = new Role();
//                role.setId(rs.getInt(7));
//                role.setName(rs.getString(8));
//                user.setRole(role);
//                user.setStatus(rs.getBoolean(9));
//                user.setId(rs.getInt(10));
//                list.add(user);
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return list;
//    }
//        public ArrayList<User> getListUserFiltered(int roleId, String gender,
//                String status, String search, String sort, String orderBy, int pageindex, int pagesize) {
//            ArrayList<User> list = new ArrayList<>();
//            try {
//                connection.setAutoCommit(false);
//            } catch (SQLException ex) {
//                Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            try {
//                String sql = "DECLARE @Col_Name VARCHAR(128) = " + "'" + sort + "'" + "\n";
//                sql += "DECLARE @User_table TABLE (\n"
//                        + "	[row_number] int,\n"
//                        + "    [fullname] nvarchar(200) NOT NULL,\n"
//                        + "	[password] nvarchar(50) NOT NULL,\n"
//                        + "    [gender] bit,\n"
//                        + "	[email] nvarchar(200) NOT NULL,\n"
//                        + "	[mobile] nvarchar(50) NOT NULL,\n"
//                        + "	[address] nvarchar(300) NOT NULL,\n"
//                        + "	[roleId] int,\n"
//                        + "	roleName nvarchar(300) NOT NULL,\n"
//                        + "	[status] bit,\n"
//                        + "	id int\n"
//                        + ");\n";
//
//                sql += "INSERT INTO @User_table\n"
//                        + "SELECT ROW_NUMBER() over (order by [User].id asc) as  [row_number], \n"
//                        + "	[fullname],[password] ,[gender],[email] ,[mobile],[address] ,[roleId],Role.name as roleName,[User].[status],[User].id\n"
//                        + "    from [User] join [Role] on roleId = [Role].id\n"
//                        + "    Where(1=1)";
//
//                int paramIndex = 0;
//                HashMap<Integer, Object[]> params = new HashMap<>();
//                if (roleId != 0) {
//                    sql += " and roleId = ?\n";
//                    paramIndex++;
//                    Object[] param = new Object[2];
//                    param[0] = Integer.class.getName();
//                    param[1] = roleId;
//                    params.put(paramIndex, param);
//                }
//                if (!gender.equals("all")) {
//                    sql += " and gender = ?\n";
//                    paramIndex++;
//                    Object[] param = new Object[2];
//                    param[0] = Boolean.class.getName();
//                    param[1] = gender.equals("male");
//                    params.put(paramIndex, param);
//                }
//                if (!status.equals("all")) {
//                    sql += " and [User].[status] = ?\n";
//                    paramIndex++;
//                    Object[] param = new Object[2];
//                    param[0] = Boolean.class.getName();
//                    param[1] = status.equals("active");
//                    params.put(paramIndex, param);
//                }
//
//                if (!search.equals("")) {
//                    sql += " and (fullname like ?\n"
//                            + "or email like ?\n"
//                            + "or mobile like ?)\n";
//                    paramIndex++;
//                    Object[] paramUsername = new Object[2];
//                    paramUsername[0] = String.class.getName();
//                    paramUsername[1] = "%" + search + "%";
//                    params.put(paramIndex, paramUsername);
//                    paramIndex++;
//                    Object[] paramEmail = new Object[2];
//                    paramEmail[0] = String.class.getName();
//                    paramEmail[1] = "%" + search + "%";
//                    params.put(paramIndex, paramEmail);
//                    paramIndex++;
//                    Object[] paramMobile = new Object[2];
//                    paramMobile[0] = String.class.getName();
//                    paramMobile[1] = "%" + search + "%";
//                    params.put(paramIndex, paramMobile);
//                }
//
//                sql += "ORDER BY CASE \n"
//                        + "    WHEN @Col_Name = 'id' THEN CAST([User].id AS SQL_VARIANT)\n"
//                        + "    WHEN @Col_Name = 'fullname' THEN CAST(fullname AS SQL_VARIANT)\n"
//                        + "    WHEN @Col_Name = 'gender' THEN CAST(gender AS SQL_VARIANT)\n"
//                        + "    WHEN @Col_Name = 'email' THEN CAST(email AS SQL_VARIANT)\n"
//                        + "    WHEN @Col_Name = 'mobile' THEN CAST(mobile AS SQL_VARIANT)\n"
//                        + "    WHEN @Col_Name = 'role' THEN CAST(roleId AS SQL_VARIANT)\n"
//                        + "    WHEN @Col_Name = 'status' THEN CAST([User].status AS SQL_VARIANT)"
//                        + " END " + orderBy + "\n";
//
//                sql += "select fullname, [password],[gender],[email],[mobile],[address],[roleId],roleName, status, id\n"
//                        + "from @User_table as t\n"
//                        + "where (1=1)";
//
//                if (pageindex != -1 || pagesize != -1) {
//                    sql += "and t.[row_number] >= ((? - 1) * ?) + 1 and t.[row_number] <= ? * ?\n";
//                    paramIndex++;
//                    Object[] paramPageIndexStart = new Object[2];
//                    paramPageIndexStart[0] = Integer.class.getName();
//                    paramPageIndexStart[1] = pageindex;
//                    params.put(paramIndex, paramPageIndexStart);
//
//                    paramIndex++;
//                    Object[] paramPageSizeStart = new Object[2];
//                    paramPageSizeStart[0] = Integer.class.getName();
//                    paramPageSizeStart[1] = pagesize;
//                    params.put(paramIndex, paramPageSizeStart);
//                    paramIndex++;
//
//                    Object[] paramPageIndexEnd = new Object[2];
//                    paramPageIndexEnd[0] = Integer.class.getName();
//                    paramPageIndexEnd[1] = pageindex;
//                    params.put(paramIndex, paramPageIndexEnd);
//
//                    paramIndex++;
//                    Object[] paramPageSizeEnd = new Object[2];
//                    paramPageSizeEnd[0] = Integer.class.getName();
//                    paramPageSizeEnd[1] = pagesize;
//                    params.put(paramIndex, paramPageSizeEnd);
//                }
//                System.out.println(sql);
//                PreparedStatement ps = connection.prepareStatement(sql);
//                for (Map.Entry<Integer, Object[]> entry : params.entrySet()) {
//                    Integer index = entry.getKey();
//                    Object[] value = entry.getValue();
//                    String type = value[0].toString();
//                    if (type.equals(Integer.class.getName())) {
//                        ps.setInt(index, (Integer) value[1]);
//                    }
//                    if (type.equals(Boolean.class.getName())) {
//                        ps.setBoolean(index, (Boolean) value[1]);
//                    }
//                    if (type.equals(String.class.getName())) {
//                        ps.setString(index, (String) value[1]);
//                    }
//                }
//                ResultSet rs = ps.executeQuery();
//                while (rs.next()) {
//                    User user = new User();
//                    user.setFullname(rs.getString(1));
//                    user.setGender(rs.getBoolean(3));
//                    user.setEmail(rs.getString(4));
//                    user.setMobile(rs.getString(5));
//                    user.setAddress(rs.getString(6));
//                    Role role = new Role();
//                    role.setId(rs.getInt(7));
//                    role.setName(rs.getString(8));
//                    user.setRole(role);
//                    user.setStatus(rs.getBoolean(9));
//                    user.setId(rs.getInt(10));
//                    list.add(user);
//                }
//                connection.commit();
//            } catch (SQLException ex) {
//                Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
//                try {
//                    connection.rollback();
//                } catch (SQLException ex1) {
//                    Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex1);
//                }
//            } finally{
//                try {
//                    connection.setAutoCommit(true);
//                } catch (SQLException ex) {
//                    Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//            return list;
//        }
    public ArrayList<User> getListUserFiltered(int roleId, String gender,
            String status, String search, String sort, String orderBy, int pageindex, int pagesize) {
        ArrayList<User> list = new ArrayList<>();
        try {
            connection.setAutoCommit(false);
        } catch (SQLException ex) {
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            String sql_insert = "DECLARE @Col_Name VARCHAR(128) = " + "'" + sort + "'" + "\n";
            sql_insert += "CREATE TABLE User_sort_table  (\n"
                    + "	[row_number] int,\n"
                    + "    [fullname] nvarchar(200) NOT NULL,\n"
                    + "	[password] nvarchar(50) NOT NULL,\n"
                    + "    [gender] bit,\n"
                    + "	[email] nvarchar(200) NOT NULL,\n"
                    + "	[mobile] nvarchar(50) NOT NULL,\n"
                    + "	[address] nvarchar(300) NOT NULL,\n"
                    + "	[roleId] int,\n"
                    + "	roleName nvarchar(300) NOT NULL,\n"
                    + "	[status] bit,\n"
                    + "	id int\n"
                    + ");\n";

            sql_insert += "INSERT INTO User_sort_table\n"
                    + "SELECT ROW_NUMBER() over (order by [User].id asc) as  [row_number], \n"
                    + "	[fullname],[password] ,[gender],[email] ,[mobile],[address] ,[roleId],Role.name as roleName,[User].[status],[User].id\n"
                    + "    from [User] join [Role] on roleId = [Role].id\n"
                    + "    Where(1=1)";

            int paramIndex = 0;
            HashMap<Integer, Object[]> params = new HashMap<>();
            if (roleId != 0) {
                sql_insert += " and roleId = ?\n";
                paramIndex++;
                Object[] param = new Object[2];
                param[0] = Integer.class.getName();
                param[1] = roleId;
                params.put(paramIndex, param);
            }
            if (!gender.equals("all")) {
                sql_insert += " and gender = ?\n";
                paramIndex++;
                Object[] param = new Object[2];
                param[0] = Boolean.class.getName();
                param[1] = gender.equals("male");
                params.put(paramIndex, param);
            }
            if (!status.equals("all")) {
                sql_insert += " and [User].[status] = ?\n";
                paramIndex++;
                Object[] param = new Object[2];
                param[0] = Boolean.class.getName();
                param[1] = status.equals("active");
                params.put(paramIndex, param);
            }

            if (!search.equals("")) {
                sql_insert += " and (fullname like ?\n"
                        + "or email like ?\n"
                        + "or mobile like ?)\n";
                paramIndex++;
                Object[] paramUsername = new Object[2];
                paramUsername[0] = String.class.getName();
                paramUsername[1] = "%" + search + "%";
                params.put(paramIndex, paramUsername);
                paramIndex++;
                Object[] paramEmail = new Object[2];
                paramEmail[0] = String.class.getName();
                paramEmail[1] = "%" + search + "%";
                params.put(paramIndex, paramEmail);
                paramIndex++;
                Object[] paramMobile = new Object[2];
                paramMobile[0] = String.class.getName();
                paramMobile[1] = "%" + search + "%";
                params.put(paramIndex, paramMobile);
            }

            sql_insert += "ORDER BY CASE \n"
                    + "    WHEN @Col_Name = 'id' THEN CAST([User].id AS SQL_VARIANT)\n"
                    + "    WHEN @Col_Name = 'fullname' THEN CAST(fullname AS SQL_VARIANT)\n"
                    + "    WHEN @Col_Name = 'gender' THEN CAST(gender AS SQL_VARIANT)\n"
                    + "    WHEN @Col_Name = 'email' THEN CAST(email AS SQL_VARIANT)\n"
                    + "    WHEN @Col_Name = 'mobile' THEN CAST(mobile AS SQL_VARIANT)\n"
                    + "    WHEN @Col_Name = 'role' THEN CAST(roleId AS SQL_VARIANT)\n"
                    + "    WHEN @Col_Name = 'status' THEN CAST([User].status AS SQL_VARIANT)"
                    + " END " + orderBy + "\n";

            PreparedStatement ps_insert = connection.prepareStatement(sql_insert);
            for (Map.Entry<Integer, Object[]> entry : params.entrySet()) {
                Integer index = entry.getKey();
                Object[] value = entry.getValue();
                String type = value[0].toString();
                if (type.equals(Integer.class.getName())) {
                    ps_insert.setInt(index, (Integer) value[1]);
                }
                if (type.equals(Boolean.class.getName())) {
                    ps_insert.setBoolean(index, (Boolean) value[1]);
                }
                if (type.equals(String.class.getName())) {
                    ps_insert.setString(index, (String) value[1]);
                }
            }
            ps_insert.execute();

            String sql_select = "select fullname, [password],[gender],[email],[mobile],[address],[roleId],roleName, status, id\n"
                    + "from User_sort_table as t\n"
                    + "where (1=1)";

            if (pageindex != -1 || pagesize != -1) {
                sql_select += "and t.[row_number] >= ((? - 1) * ?) + 1 and t.[row_number] <= ? * ?\n";
            }

            PreparedStatement ps = connection.prepareStatement(sql_select);
            if (pageindex != -1 || pagesize != -1) {
                ps.setInt(1, pageindex);
                ps.setInt(2, pagesize);
                ps.setInt(3, pageindex);
                ps.setInt(4, pagesize);
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setFullname(rs.getString(1));
                user.setGender(rs.getBoolean(3));
                user.setEmail(rs.getString(4));
                user.setMobile(rs.getString(5));
                user.setAddress(rs.getString(6));
                Role role = new Role();
                role.setId(rs.getInt(7));
                role.setName(rs.getString(8));
                user.setRole(role);
                user.setStatus(rs.getBoolean(9));
                user.setId(rs.getInt(10));
                list.add(user);
            }

            String sql_drop = "DROP TABLE User_sort_table";
            PreparedStatement ps_drop = connection.prepareStatement(sql_drop);
            ps_drop.execute();
            connection.commit();
        } catch (SQLException ex) {
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return list;
    }

    public User getUserByIDLogin(int id) {
        String sql = "SELECT [fullname]\n"
                + "                     ,[password]\n"
                + "                      ,[gender]\n"
                + "                      ,[email]\n"
                + "                      ,[mobile]\n"
                + "                      ,[address]\n"
                + "                      ,[roleId]\n"
                + "                      ,Role.name\n"
                + "                      ,[User].[status]\n"
                + "                      ,[User].id\n"
                + "                FROM [User] join Role on roleId = Role.id\n"
                + "				WHERE [User].id = ?";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setFullname(rs.getString(1));
                user.setGender(rs.getBoolean(3));
                user.setEmail(rs.getString(4));
                user.setMobile(rs.getString(5));
                user.setAddress(rs.getString(6));
                Role role = new Role();
                role.setId(rs.getInt(7));
                role.setName(rs.getString(8));
                user.setRole(role);
                user.setStatus(rs.getBoolean(9));
                user.setId(rs.getInt(10));
                return user;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void editUserProfile(User user) {
        String sql = " UPDATE [dbo].[User]\n"
                + "   SET [avatar] = ?\n"
                + "      ,[fullname] = ?\n"
                + "      ,[gender] = ?\n"
                + "      ,[mobile] = ?\n"
                + "      ,[address] = ?\n"
                + "      \n"
                + " WHERE id = ? ";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, user.getAvatar());
            stm.setString(2, user.getFullname());
            stm.setBoolean(3, user.isGender());
            stm.setString(4, user.getMobile());
            stm.setString(5, user.getAddress());
            stm.setInt(6, user.getId());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(CustomerDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(CustomerDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void editUserProfileWithoutImg(User user) {
        String sql = " UPDATE [dbo].[User]\n"
                + "   SET   [fullname] = ?\n"
                + "      ,[gender] = ?\n"
                + "      ,[mobile] = ?\n"
                + "      ,[address] = ?\n"
                + "      \n"
                + " WHERE id = ? ";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, user.getFullname());
            stm.setBoolean(2, user.isGender());
            stm.setString(3, user.getMobile());
            stm.setString(4, user.getAddress());
            stm.setInt(5, user.getId());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(CustomerDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(CustomerDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public User getUser(int id) {
        String sql = "SELECT [User].id"
                + "       ,[fullname]\n"
                + "      ,[gender]\n"
                + "      ,[email]\n"
                + "      ,[mobile]\n"
                + "      ,[address]\n"
                + "      ,[roleId]\n"
                + "      ,Role.name\n"
                + "      ,[user].[status]\n"
                + "      ,[avatar]\n"
                + "      ,[Role].isSuper\n"
                + "  FROM [User] join Role on roleId = Role.id"
                + "       WHERE id = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt(1));
                user.setFullname(rs.getString(2));
                user.setGender(rs.getBoolean(3));
                user.setEmail(rs.getString(4));
                user.setMobile(rs.getString(5));
                user.setAddress(rs.getString(6));
                Role role = new Role();
                role.setId(rs.getInt(7));
                role.setName(rs.getString(8));
                role.setAllowFeatures(new RoleDBContext().getAllowFeatures(role.getId()));
                role.setIssuperadmin(rs.getBoolean("isSuper"));

                user.setRole(role);
                user.setStatus(rs.getBoolean(9));
                user.setAvatar(rs.getString(10));
                return user;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public User login(String email, String password) {
        String sql = "SELECT [User].id"
                + "       ,[fullname]\n"
                + "      ,[gender]\n"
                + "      ,[email]\n"
                + "      ,[mobile]\n"
                + "      ,[address]\n"
                + "      ,[roleId]\n"
                + "      ,Role.name\n"
                + "      ,[user].[status]\n"
                + "      ,[avatar]\n"
                + "      ,[username]\n"
                + "  FROM [User] join Role on roleId = Role.id"
                + "       WHERE email = ? AND password = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt(1));
                user.setFullname(rs.getString(2));
                user.setGender(rs.getBoolean(3));
                user.setEmail(rs.getString(4));
                user.setMobile(rs.getString(5));
                user.setAddress(rs.getString(6));
                Role role = new Role();
                role.setId(rs.getInt(7));
                role.setName(rs.getString(8));
                role.setAllowFeatures(new RoleDBContext().getAllowFeatures(role.getId()));

                user.setRole(role);
                user.setStatus(rs.getBoolean(9));
                user.setAvatar(rs.getString(10));
                user.setUsername(rs.getString(11));
                return user;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String getUserPassword(int uid) {
        String sql = "SELECT [password] FROM [User]\n"
                + "WHERE id = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, uid);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("password");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void updateUserPassword(int uid, String password) throws SQLException {
        connection.setAutoCommit(false);
        String sql = "UPDATE [dbo].[User]\n"
                + "   SET [password] = ?\n"
                + " WHERE id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, password);
            ps.setInt(2, uid);
            ps.executeUpdate();
            connection.commit();
        } catch (SQLException ex) {
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
            connection.rollback();
            connection.close();
        }
    }

    public ArrayList<User> getUserMarketing() {
        ArrayList<User> listUserMarketing = new ArrayList<>();
        try {
            String sql = "Select * from [User] \n"
                    + "Where roleId = 2";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt(1));
                user.setPassword(rs.getString(2));
                user.setAvatar(rs.getString(3));
                user.setFullname(rs.getString(4));
                user.setGender(rs.getBoolean(5));
                user.setMobile(rs.getString(6));
                user.setAddress(rs.getString(7));
                Role role = new Role();
                role.setId(rs.getInt(8));
                user.setRole(role);
                user.setStatus(rs.getBoolean(9));
                user.setUsername(rs.getString(10));
                user.setEmail(rs.getString(11));
                listUserMarketing.add(user);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listUserMarketing;
    }

    public ArrayList<User> getSaleUser() {
        ArrayList<User> sales = new ArrayList<>();
        try {
            String sql = "Select [User].id, [User].fullname from [User] \n"
                    + "Where roleId = 3";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt(1));
                user.setFullname(rs.getString(2));
                sales.add(user);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sales;
    }

    public static void main(String[] args) {
        UserDBContext db = new UserDBContext();
//        System.out.println(db.getListUserFiltered(3, "male", "active", "Le", "id", "asc", 1, 5).size());
//        for (User u : db.getListUserFiltered(3, "male", "active", "Le", "email", "asc", 1, 5)) {
//            System.out.println(u.toString());
//        }
//        db.inserUser("hie", "123", true, "daw@daw", "012", "dqaw", 1, true);
//        try {
//            System.out.println(db.getUserById(5));
//        } catch (Exception e) {
//
//        }
       User u = new User();
       u.setPassword("123");
       u.setFullname("ddd");
       u.setGender(true);
       u.setMobile("00558");
       u.setAddress("adas");
       u.setEmail("3123123");
       boolean rs = db.addUser(u);
       
        System.out.println(rs);
//        System.out.println(db.getUserById(1).toString());
//        User user = db.getUserByEmail("hieunvhe153769@fpt.edu.vn");
////        System.out.println(user.getUsername());
//        user.setUsername("Hieu Nguyen 123345");
//        db.updateUserInf(user);
//        User user1 = db.getUserByEmail("hieunvhe153769@fpt.edu.vn");
////        user = db.getUserByEmail("hieunvhe153769@fpt.edu.vn");
//        System.out.println(user.getUsername());
        
        

    }

    public ArrayList<User> getIdSellersProduct(Product[] productsOrder) {
        ArrayList<User> listSeller = new ArrayList<>();
        try {
            String sql = "select distinct sellerId from product\n"
                    + "where ";
            for (int i = 0; i < productsOrder.length; i++) {
                if (i == productsOrder.length - 1) {
                    sql += " Product.id = ? ";
                } else {
                    sql += " or Product.id = ? ";
                }
            }
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt(1));
                listSeller.add(user);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listSeller;
    }

    public int getLastSellerReceiveOrder() {
        try {
            String sql = "select top 1 sellerid from [order]\n"
                    + "order by id desc";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public int countNumberSeller() {
        try {
            String sql = "Select count(id) as total \n"
                    + "from [User] where roleId = 3 and status = 1";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public int getindexSellerReceiveOrder(int lastSellerReceiveOrder) {
        try {
            String sql = "Select * from \n"
                    + "( Select id, ROW_NUMBER() over(order by id asc) as row_index from [User] \n"
                    + "where roleId = 3 and status = 1) indexSeller\n"
                    + "where id = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, lastSellerReceiveOrder);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt(2);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public int getIdNextSeller(int indexNextSellerReceiveOrder) {
        try {
            String sql = "Select * from \n"
                    + "( Select id, ROW_NUMBER() over(order by id asc) as row_index from [User] \n"
                    + "where roleId = 3 and status = 1) indexSeller\n"
                    + "where row_index = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, indexNextSellerReceiveOrder);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public User getUserInformationByID(int userID) {
        try {
            String sql = "Select * From [User]\n"
                    + "Where id = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, userID);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt(1));
                user.setPassword(rs.getString(2));
                user.setAvatar(rs.getString(3));
                user.setFullname(rs.getString(4));
                user.setGender(rs.getBoolean(5));
                user.setMobile(rs.getString(6));
                user.setAddress(rs.getString(7));
                Role role = new Role();
                role.setId(rs.getInt(8));
                user.setRole(role);
                user.setStatus(rs.getBoolean(9));
                user.setUsername(rs.getString(10));
                user.setEmail(rs.getString(11));
                return user;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
