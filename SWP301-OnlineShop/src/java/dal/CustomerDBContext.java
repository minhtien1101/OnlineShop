/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Role;
import model.User;
import model.User_Update;

/**
 *
 * @author Hoang Quang
 */
public class CustomerDBContext extends DBContext {

    public ArrayList<User> getListCustomer(int userRole, String searchBy, String statusBy) {
        ArrayList<User> listCustomer = new ArrayList<>();
        String sql1 = "SELECT [fullname] ,[password],[gender], [email], [mobile],[address] ,[roleId] ,Role.name ,[User].[status] ,[User].id\n"
                + "FROM [User] join Role on roleId = Role.id\n"
                + "WHERE roleId = ?\n"
                + "AND   \n"
                + "(fullname like ?\n"
                + "OR email LIKE ?\n"
                + "OR mobile LIKE ?)";
        //check status is empty or not
        if (!statusBy.isEmpty()) {
            sql1 += "AND [User].status LIKE ?";
        }
        try {
            PreparedStatement ps = connection.prepareStatement(sql1);
            //check status is empty or not
            if (!statusBy.isEmpty()) {
                ps.setInt(1, userRole);
                ps.setString(2, "%" + searchBy + "%");
                ps.setString(3, "%" + searchBy + "%");
                ps.setString(4, "%" + searchBy + "%");
                ps.setString(5, "%" + statusBy + "%");

            } else {
                ps.setInt(1, userRole);
                ps.setString(2, "%" + searchBy + "%");
                ps.setString(3, "%" + searchBy + "%");
                ps.setString(4, "%" + searchBy + "%");
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
                listCustomer.add(user);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listCustomer;
    }

    public User getCustomerByID(int customerID) {
        String sql = "SELECT [fullname]\n"
                + "                      ,[password]\n"
                + "                      ,[gender]\n"
                + "                      ,[email]\n"
                + "                      ,[mobile]\n"
                + "                      ,[address]\n"
                + "                      ,[roleId]\n"
                + "                      ,Role.name\n"
                + "                      ,[User].[status]\n"
                + "                      ,[User].id\n"
                + "                  FROM [User] join Role on roleId = Role.id\n"
                + "                WHERE [User].id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, customerID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User customer = new User();
                customer.setFullname(rs.getString(1));
                customer.setGender(rs.getBoolean(3));
                customer.setEmail(rs.getString(4));
                customer.setMobile(rs.getString(5));
                customer.setAddress(rs.getString(6));
                Role role = new Role();
                role.setId(rs.getInt(7));
                role.setName(rs.getString(8));
                customer.setRole(role);
                customer.setStatus(rs.getBoolean(9));
                customer.setId(rs.getInt(10));
                return customer;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void editCustomer(User customer) {
        String sql = "UPDATE [dbo].[User] \n"
                + "SET [fullname] = ?\n"
                + "      ,[gender] = ?\n"
                + "      ,[mobile] = ?\n"
                + "      ,[address] = ?\n"
                + "      ,[email] = ?\n"
                + " WHERE id = ?";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, customer.getFullname());
            stm.setBoolean(2, customer.isGender());
            stm.setString(3, customer.getMobile());
            stm.setString(4, customer.getAddress());
            stm.setString(5, customer.getEmail());
            stm.setInt(6, customer.getId());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
//        finally {
//            if (stm != null) {
//                try {
//                    stm.close();
//                } catch (SQLException ex) {
//                    Logger.getLogger(CustomerDBContext.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//            if (connection != null) {
//                try {
//                    connection.close();
//                } catch (SQLException ex) {
//                    Logger.getLogger(CustomerDBContext.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//        }
    }

    public void addCustomer(User cusomter) {
        String sql = "INSERT INTO [dbo].[User]\n"
                + "           ([password]\n"
                + "           ,[avatar]\n"
                + "           ,[fullname]\n"
                + "           ,[gender]\n"
                + "           ,[mobile]\n"
                + "           ,[address]\n"
                + "           ,[roleId]\n"
                + "           ,[status]\n"
                + "           ,[email])\n"
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
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, cusomter.getPassword());
            stm.setString(2, cusomter.getAvatar());
            stm.setString(3, cusomter.getFullname());
            stm.setBoolean(4, cusomter.isGender());
            stm.setString(5, cusomter.getMobile());
            stm.setString(6, cusomter.getAddress());
            stm.setInt(7, cusomter.getRole().getId());
            stm.setBoolean(8, cusomter.isStatus());
            stm.setString(9, cusomter.getEmail());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
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

    public void editStatus(int id, boolean status) {
        String spl1 = "UPDATE [dbo].[User]\n"
                + "   SET [status] = ?\n"
                + " WHERE id = ?";
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(spl1);
            stm.setInt(2, id);
            stm.setBoolean(1, status);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
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

    public ArrayList<User> getListCustomerSortBy(ArrayList<User> listCustomer2, String sortBy) {
        //check list customer is empty or not
        if (!listCustomer2.isEmpty()) {
            //sort by fullname
            if (sortBy.equals("fullname")) {
                Collections.sort(listCustomer2, new Comparator<User>() {
                    @Override
                    public int compare(User o1, User o2) {
                        return o1.getFullname().compareTo(o2.getFullname());
                    }
                });
            }
            //sort by email
            if (sortBy.equals("email")) {
                Collections.sort(listCustomer2, new Comparator<User>() {
                    @Override
                    public int compare(User o1, User o2) {
                        return o1.getEmail().compareTo(o2.getEmail());
                    }
                });
            }
            //sort by mobile
            if (sortBy.equals("mobile")) {
                Collections.sort(listCustomer2, new Comparator<User>() {
                    @Override
                    public int compare(User o1, User o2) {
                        return o1.getMobile().compareTo(o2.getMobile());
                    }
                });
            }
            //sort by mobile
            if (sortBy.equals("status")) {
                Collections.sort(listCustomer2, new Comparator<User>() {
                    @Override
                    public int compare(User o1, User o2) {
                        boolean b1 = o1.isStatus();
                        boolean b2 = o2.isStatus();
                        if (b1 == !b2) {
                            return 1;
                        }
                        if (!b1 == b2) {
                            return -1;
                        }
                        return 0;
                    }
                });
            }
        }
        return listCustomer2;
    }

    public int count(int userRole, String searchBy, String statusBy) {
        try {

            String sql1 = "";
            String sql2 = "";
            if (statusBy.isEmpty()) {
                sql1 = "with x as (select ROW_NUMBER() OVER (ORDER BY id desc) as r\n"
                        + "		, * from [User] where roleId = ? AND \n"
                        + "                               (fullname like ?\n"
                        + "                                OR email LIKE ?\n"
                        + "                                OR mobile LIKE ?)\n"
                        + "								)\n"
                        + "		SElECT COUNT(*) as Total FROM x";
            } else {
                sql2 = "with x as (select ROW_NUMBER() OVER (ORDER BY id desc) as r\n"
                        + "		, * from [User] where roleId = ? AND \n"
                        + "                               (fullname like ?\n"
                        + "                                OR email LIKE ?\n"
                        + "                                OR mobile LIKE ?)\n"
                        + "				AND status = ?)\n"
                        + "		SElECT COUNT(*) as Total FROM x";
            }

            PreparedStatement ps = null;
            if (statusBy.isEmpty()) {
                ps = connection.prepareStatement(sql1);
                ps.setInt(1, userRole);
                ps.setString(2, "%" + searchBy + "%");
                ps.setString(3, "%" + searchBy + "%");
                ps.setString(4, "%" + searchBy + "%");
            } else {
                ps = connection.prepareStatement(sql2);
                ps.setInt(1, userRole);
                ps.setString(2, "%" + searchBy + "%");
                ps.setString(3, "%" + searchBy + "%");
                ps.setString(4, "%" + searchBy + "%");
                ps.setString(5, statusBy);
            }
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("Total");
            }
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public int countForUpdate(int customerID) {
        try {
            String sql = "SELECT count(*) as Total FROM UserUpdate WHERE userId = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, customerID);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt("Total");
            }
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public ArrayList<User> getCustomerByPage(int userRole, String searchBy, String statusBy, int pageindex, int pagesize) {
        ArrayList<User> listCustomer = new ArrayList<>();
        String sql1 = "";
        String sql2 = "";
        //check status is empty or not
        if (!statusBy.isEmpty()) {
            sql1 = "with x as (select ROW_NUMBER() OVER (ORDER BY id desc) as r\n"
                    + "		, * from [User] where roleId = ? AND \n"
                    + "                               (fullname like ?\n"
                    + "                                OR email LIKE ?\n"
                    + "                                OR mobile LIKE ?)\n"
                    + "								AND status = ?)\n"
                    + "		SElECT* FROM x where r between (? - 1)* ? +1 and ? * ?";
        } else {
            sql2 = "		with x as (select ROW_NUMBER() OVER (ORDER BY id desc) as r\n"
                    + "		, * from [User] where roleId = ? AND \n"
                    + "                               (fullname like ?\n"
                    + "                                OR email LIKE ?\n"
                    + "                                OR mobile LIKE ?)\n"
                    + "								)\n"
                    + "		SElECT* FROM x where r between (? - 1)* ? +1 and ? * ?";
        }
        PreparedStatement ps = null;
        try {
            if (!statusBy.isEmpty()) {
                ps = connection.prepareStatement(sql1);
                ps.setInt(1, userRole);
                ps.setString(2, "%" + searchBy + "%");
                ps.setString(3, "%" + searchBy + "%");
                ps.setString(4, "%" + searchBy + "%");
                ps.setString(5, statusBy);
                ps.setInt(6, pageindex);
                ps.setInt(7, pagesize);
                ps.setInt(8, pageindex);
                ps.setInt(9, pagesize);
            } else {
                ps = connection.prepareStatement(sql2);
                ps.setInt(1, userRole);
                ps.setString(2, "%" + searchBy + "%");
                ps.setString(3, "%" + searchBy + "%");
                ps.setString(4, "%" + searchBy + "%");
                ps.setInt(5, pageindex);
                ps.setInt(6, pagesize);
                ps.setInt(7, pageindex);
                ps.setInt(8, pagesize);
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setFullname(rs.getString("fullname"));
                user.setGender(rs.getBoolean("gender"));
                user.setEmail(rs.getString("email"));
                user.setMobile(rs.getString("mobile"));
                user.setAddress(rs.getString("address"));
                Role role = new Role();
                role.setId(rs.getInt("roleId"));
                user.setRole(role);
                user.setStatus(rs.getBoolean("status"));
                user.setId(rs.getInt("id"));
                listCustomer.add(user);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listCustomer;
    }

    public ArrayList<User_Update> getListHistoryUpdate(int customerID, int pagesize, int pageindex) {
        ArrayList<User_Update> listHistoryUpdate = new ArrayList<>();
        String sql1 = " with x as (select ROW_NUMBER() OVER (ORDER BY uid desc) as r\n"
                + "                    		, * from [UserUpdate] where userId = ? )\n"
                + "SElECT* FROM x where r between (? - 1)* ? +1 and ? * ? ";

        try {
            PreparedStatement ps = connection.prepareStatement(sql1);
            //check status is empty or not
            ps.setInt(1, customerID);
            ps.setInt(2, pageindex);
            ps.setInt(3, pagesize);
            ps.setInt(4, pageindex);
            ps.setInt(5, pagesize);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User_Update update = new User_Update();
                update.setEmail(rs.getString("email"));
                update.setUpdateBy(rs.getString("updateBy"));
                update.setUpdateDate(rs.getDate("updateDate"));
                update.setUserId(rs.getInt("userId"));
                update.setFullname(rs.getString("fullname"));
                update.setGender(rs.getBoolean("gender"));
                update.setMobile(rs.getString("mobile"));
                update.setAddress(rs.getString("address"));
                listHistoryUpdate.add(update);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listHistoryUpdate;
    }

    public void addHistoryEditCustomer(User_Update update) {
        String sql = "INSERT INTO [dbo].[UserUpdate]\n"
                + "           ([email]\n"
                + "           ,[updateBy]\n"
                + "           ,[updateDate]\n"
                + "           ,[userId]\n"
                + "           ,[fullname]\n"
                + "           ,[gender]\n"
                + "           ,[mobile]\n"
                + "           ,[address])\n"
                + "     VALUES\n"
                + "           (?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,?)";
        PreparedStatement stm = null;

        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, update.getEmail());
            stm.setString(2, update.getUpdateBy());
            stm.setDate(3, update.getUpdateDate());
            stm.setInt(4, update.getUserId());
            stm.setString(5, update.getFullname());
            stm.setBoolean(6, update.isGender());
            stm.setString(7, update.getMobile());
            stm.setString(8, update.getAddress());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ProductDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ProductDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ProductDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public ArrayList<User> getListCustomerSortBy(int userRole, String searchBy, String statusBy, int pageindex, int pagesize, String sortBy) {
        ArrayList<User> listCustomer = new ArrayList<>();
        String sql1 = "";
        String sql2 = "";
        //check status is empty or not
        if (!statusBy.isEmpty()) {
            if (sortBy.equals("fullname")) {
                sql1 = "with x as (select ROW_NUMBER() OVER (ORDER BY fullname ) as r\n"
                        + "		, * from [User] where roleId = ? AND \n"
                        + "                               (fullname like ?\n"
                        + "                                OR email LIKE ?\n"
                        + "                                OR mobile LIKE ?)\n"
                        + "								AND status = ?)\n"
                        + "		SElECT* FROM x where r between (? - 1)* ? +1 and ? * ?";
            } else if (sortBy.equals("mobile")) {
                sql1 = "with x as (select ROW_NUMBER() OVER (ORDER BY mobile desc) as r\n"
                        + "		, * from [User] where roleId = ? AND \n"
                        + "                               (fullname like ?\n"
                        + "                                OR email LIKE ?\n"
                        + "                                OR mobile LIKE ?)\n"
                        + "								AND status = ?)\n"
                        + "		SElECT* FROM x where r between (? - 1)* ? +1 and ? * ?";
            } else if (sortBy.equals("email")) {
                sql1 = "with x as (select ROW_NUMBER() OVER (ORDER BY email desc) as r\n"
                        + "		, * from [User] where roleId = ? AND \n"
                        + "                               (fullname like ?\n"
                        + "                                OR email LIKE ?\n"
                        + "                                OR mobile LIKE ?)\n"
                        + "								AND status = ?)\n"
                        + "		SElECT* FROM x where r between (? - 1)* ? +1 and ? * ?";
            } else if (sortBy.equals("status")) {
                sql1 = "with x as (select ROW_NUMBER() OVER (ORDER BY status desc) as r\n"
                        + "		, * from [User] where roleId = ? AND \n"
                        + "                               (fullname like ?\n"
                        + "                                OR email LIKE ?\n"
                        + "                                OR mobile LIKE ?)\n"
                        + "								AND status = ?)\n"
                        + "		SElECT* FROM x where r between (? - 1)* ? +1 and ? * ?";
            } else if (sortBy.equals("")) {
                sql1 = "with x as (select ROW_NUMBER() OVER (ORDER BY id desc) as r\n"
                        + "		, * from [User] where roleId = ? AND \n"
                        + "                               (fullname like ?\n"
                        + "                                OR email LIKE ?\n"
                        + "                                OR mobile LIKE ?)\n"
                        + "								AND status = ?)\n"
                        + "		SElECT* FROM x where r between (? - 1)* ? +1 and ? * ?";
            }
        } else {
            if (sortBy.equals("fullname")) {
                sql2 = "		with x as (select ROW_NUMBER() OVER (ORDER BY fullname ) as r\n"
                        + "		, * from [User] where roleId = ? AND \n"
                        + "                               (fullname like ?\n"
                        + "                                OR email LIKE ?\n"
                        + "                                OR mobile LIKE ?)\n"
                        + "								)\n"
                        + "		SElECT* FROM x where r between (? - 1)* ? +1 and ? * ?";
            } else if (sortBy.equals("mobile")) {
                sql2 = "		with x as (select ROW_NUMBER() OVER (ORDER BY mobile desc) as r\n"
                        + "		, * from [User] where roleId = ? AND \n"
                        + "                               (fullname like ?\n"
                        + "                                OR email LIKE ?\n"
                        + "                                OR mobile LIKE ?)\n"
                        + "								)\n"
                        + "		SElECT* FROM x where r between (? - 1)* ? +1 and ? * ?";
            } else if (sortBy.equals("email")) {
                sql2 = "		with x as (select ROW_NUMBER() OVER (ORDER BY email desc) as r\n"
                        + "		, * from [User] where roleId = ? AND \n"
                        + "                               (fullname like ?\n"
                        + "                                OR email LIKE ?\n"
                        + "                                OR mobile LIKE ?)\n"
                        + "								)\n"
                        + "		SElECT* FROM x where r between (? - 1)* ? +1 and ? * ?";
            } else if (sortBy.equals("status")) {
                sql2 = "		with x as (select ROW_NUMBER() OVER (ORDER BY status desc) as r\n"
                        + "		, * from [User] where roleId = ? AND \n"
                        + "                               (fullname like ?\n"
                        + "                                OR email LIKE ?\n"
                        + "                                OR mobile LIKE ?)\n"
                        + "								)\n"
                        + "		SElECT* FROM x where r between (? - 1)* ? +1 and ? * ?";
            } else if (sortBy.equals("")) {
                sql2 = "		with x as (select ROW_NUMBER() OVER (ORDER BY id desc) as r\n"
                        + "		, * from [User] where roleId = ? AND \n"
                        + "                               (fullname like ?\n"
                        + "                                OR email LIKE ?\n"
                        + "                                OR mobile LIKE ?)\n"
                        + "								)\n"
                        + "		SElECT* FROM x where r between (? - 1)* ? +1 and ? * ?";
            }
        }
        PreparedStatement ps = null;
        try {
            if (!statusBy.isEmpty()) {
                ps = connection.prepareStatement(sql1);
                ps.setInt(1, userRole);
                ps.setString(2, "%" + searchBy + "%");
                ps.setString(3, "%" + searchBy + "%");
                ps.setString(4, "%" + searchBy + "%");
                ps.setString(5, statusBy);
                ps.setInt(6, pageindex);
                ps.setInt(7, pagesize);
                ps.setInt(8, pageindex);
                ps.setInt(9, pagesize);
            } else {
                ps = connection.prepareStatement(sql2);
                ps.setInt(1, userRole);
                ps.setString(2, "%" + searchBy + "%");
                ps.setString(3, "%" + searchBy + "%");
                ps.setString(4, "%" + searchBy + "%");
                ps.setInt(5, pageindex);
                ps.setInt(6, pagesize);
                ps.setInt(7, pageindex);
                ps.setInt(8, pagesize);
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setFullname(rs.getString("fullname"));
                user.setGender(rs.getBoolean("gender"));
                user.setEmail(rs.getString("email"));
                user.setMobile(rs.getString("mobile"));
                user.setAddress(rs.getString("address"));
                Role role = new Role();
                role.setId(rs.getInt("roleId"));
                user.setRole(role);
                user.setStatus(rs.getBoolean("status"));
                user.setId(rs.getInt("id"));
                listCustomer.add(user);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listCustomer;
    }

    public int count(int userRole) {
        int number = 0;
        try {

            String sql = "select COUNT(*) num from [User] where roleId = ?";

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, userRole);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                number = rs.getInt("num");
            }
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return number;
    }

    public static void main(String[] args) {
        CustomerDBContext db = new CustomerDBContext();
        System.out.println(db.count(4));
    }

    public ArrayList<String> getListEmail() {
        ArrayList<String> listEmail = new ArrayList<>();
        String sql1 = " SELECT email FROM [User] ";

        try {
            PreparedStatement ps = connection.prepareStatement(sql1);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                listEmail.add(rs.getString("email"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listEmail;
    }
}
