/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Order;
import model.User;

/**
 *
 * @author Hoang Quang
 */
public class Order2DBContext extends DBContext {

    public Order getInformationOfOrderByID(int orderID) {
        try {
            String sql = " select DISTINCT  od.id, od.date, od.status, od.totalPrice\n"
                    + "from\n"
                    + "[Order] od WHERE od.id = ? ";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, orderID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Order order = new Order();
                order.setId(rs.getInt("id"));
                order.setDate(rs.getDate("date"));
                order.setStatus(rs.getInt("status"));
                order.setTotalcost(rs.getDouble("totalPrice"));
                return order;
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderDBContext.class.getName()).log(Level.SEVERE, null, ex);
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
}
