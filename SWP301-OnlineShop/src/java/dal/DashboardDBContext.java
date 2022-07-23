/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Category;
import model.Revenue;
import model.SubCategory;
import model.TrendOrder;

/**
 *
 * @author Admin
 */
public class DashboardDBContext extends DBContext {

    public int getCancelledOrders() {
        String sql = "select count(*) as CancelledOrders from [Order] where [status] = 0 and [Order].[date] >= DATEADD(day, -1, GETDATE())";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int cancelledCount = rs.getInt("CancelledOrders");
                return cancelledCount;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DashboardDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public int getProcessingOrders() {
        String sql = "select count(*) as ProcessingOrders from [Order] where [status] = 2 and [Order].[date] >= DATEADD(day, -1, GETDATE())";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int processingCount = rs.getInt("ProcessingOrders");
                return processingCount;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DashboardDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public int getSuccessOrders() {
        String sql = "select count(*) as SuccessOrders from [Order] where [status] = 4 and [Order].[date] >= DATEADD(day, -1, GETDATE())";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int successCount = rs.getInt("SuccessOrders");
                return successCount;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DashboardDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public ArrayList<Revenue> getRevenueByProductCategory(String startDate, String endDate) {
        String sql = "with t as\n"
                + "(\n"
                + "select Category.id as CategoryID, \n"
                + "SUM(([OrderDetail].price * [OrderDetail].quantity - ([OrderDetail].price * [OrderDetail].quantity * ([OrderDetail].discount / 100)))) as totalPrice\n"
                + "from [Order] inner join [OrderDetail] on [Order].id = [OrderDetail].orderId\n"
                + "inner join Product on [OrderDetail].productId = [Product].id\n"
                + "inner join SubCategory on Product.subCategoryId = SubCategory.id\n"
                + "inner join Category on SubCategory.categoryId = Category.id\n"
                + "where [Order].[status] = 4 and [Order].[date] between ? and ?\n"
                + "group by Category.id\n"
                + ")\n"
                + "select Category.id, isnull(t.totalPrice, 0) as totalPrice, Category.[name] \n"
                + "from t right outer join [Category] on Category.id = t.CategoryID";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, startDate);
            ps.setString(2, endDate);
            ResultSet rs = ps.executeQuery();
            ArrayList<Revenue> re = new ArrayList<>();
            while (rs.next()) {
                Revenue r = new Revenue();
                r.setCategoryid(rs.getInt("id"));
                r.setRevenue(rs.getInt("totalPrice"));
                r.setCategoryname(rs.getString("name"));
                re.add(r);

            }
            return re;
        } catch (SQLException ex) {
            Logger.getLogger(DashboardDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public int getTotalRevenue(String startDate, String endDate) {
        String sql = "with t as\n"
                + "(\n"
                + "select distinct [Order].totalPrice, [Order].[date]\n"
                + "from [Order] inner join [OrderDetail] on [Order].id = [OrderDetail].orderId\n"
                + "inner join Product on [OrderDetail].productId = [Product].id\n"
                + "inner join SubCategory on Product.subCategoryId = SubCategory.id\n"
                + "inner join Category on SubCategory.categoryId = Category.id\n"
                + "where [Order].[status] = 4)\n"
                + "select SUM(t.totalPrice) as totalRevenue from t where [date] between ? and ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, startDate);
            ps.setString(2, endDate);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int totalRevenue = rs.getInt("totalRevenue");
                return totalRevenue;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DashboardDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public int getNewCustomer() {
        String sql = "select count(*) as newCustomer from [User] where dateCreated >= DATEADD(day, -1, GETDATE())";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int totalNewCustomer = rs.getInt("newCustomer");
                return totalNewCustomer;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DashboardDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public int getNewCustomerBought() {
        String sql = "select count(*) as newCustomerBought from [User]\n"
                + "inner join [Order] on [User].id = [Order].userId\n"
                + "where [Order].[date] >= DATEADD(day, -1, GETDATE())";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int totalNewBought = rs.getInt("newCustomerBought");
                return totalNewBought;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DashboardDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public ArrayList<TrendOrder> getSuccessOrdersByDateRange(String startTime, String endTime) throws ParseException {
        String sql = "DECLARE @GeneratingDateFrom DATE = ?\n"
                + "DECLARE @GeneratingDateTo DATE = ?\n"
                + "\n"
                + ";WITH GeneratedDates AS\n"
                + "(\n"
                + "    SELECT\n"
                + "        GeneratedDate = @GeneratingDateFrom\n"
                + "\n"
                + "    UNION ALL\n"
                + "\n"
                + "    SELECT\n"
                + "        GeneratedDate = DATEADD(DAY, 1, G.GeneratedDate)\n"
                + "    FROM\n"
                + "        GeneratedDates AS G\n"
                + "    WHERE\n"
                + "        DATEADD(DAY, 1, G.GeneratedDate) < @GeneratingDateTo\n"
                + "),\n"
                + "a as \n"
                + "(select [order].id,[order].[date] from [order] where [order].[status] = 4)\n"
                + "SELECT\n"
                + "    G.GeneratedDate,\n"
                + "    count(a.id) as NumberOfSuccessOrders\n"
                + "FROM \n"
                + "    GeneratedDates AS G\n"
                + "    left outer JOIN a ON G.GeneratedDate = CONVERT(date, a.date)\n"
                + "GROUP BY\n"
                + "    G.GeneratedDate";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setDate(1, this.parseDateSQL(startTime));
            ps.setDate(2, this.parseDateSQL(endTime));
            ResultSet rs = ps.executeQuery();
            ArrayList<TrendOrder> successOrders = new ArrayList<>();
            while (rs.next()) {
                TrendOrder o = new TrendOrder();
                o.setDate(rs.getString("GeneratedDate"));
                o.setCount(rs.getInt("NumberOfSuccessOrders"));
                successOrders.add(o);
            }
            System.out.println("Success: " + successOrders.size());
            return successOrders;
        } catch (SQLException ex) {
            Logger.getLogger(DashboardDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList<TrendOrder> getTotalOrdersByDateRange(String startTime, String endTime) throws ParseException {
        String sql = "DECLARE @GeneratingDateFrom DATE = ?\n"
                + "DECLARE @GeneratingDateTo DATE = ?\n"
                + "\n"
                + ";WITH GeneratedDates AS\n"
                + "(\n"
                + "    SELECT\n"
                + "        GeneratedDate = @GeneratingDateFrom\n"
                + "\n"
                + "    UNION ALL\n"
                + "\n"
                + "    SELECT\n"
                + "        GeneratedDate = DATEADD(DAY, 1, G.GeneratedDate)\n"
                + "    FROM\n"
                + "        GeneratedDates AS G\n"
                + "    WHERE\n"
                + "        DATEADD(DAY, 1, G.GeneratedDate) < @GeneratingDateTo\n"
                + ")\n"
                + "SELECT\n"
                + "    G.GeneratedDate,\n"
                + "    count([Order].id) as NumberOfOrders\n"
                + "FROM \n"
                + "    GeneratedDates AS G\n"
                + "    left outer JOIN [Order] ON G.GeneratedDate = CONVERT(date, [Order].date)\n"
                + "GROUP BY\n"
                + "    G.GeneratedDate\n"
                + "";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setDate(1, this.parseDateSQL(startTime));
            ps.setDate(2, this.parseDateSQL(endTime));
            ResultSet rs = ps.executeQuery();
            ArrayList<TrendOrder> totalOrders = new ArrayList<>();
            while (rs.next()) {
                TrendOrder o = new TrendOrder();
                o.setDate(rs.getString("GeneratedDate"));
                o.setCount(rs.getInt("NumberOfOrders"));
                totalOrders.add(o);
            }
            System.out.println("total: " + totalOrders.size());
            return totalOrders;
        } catch (SQLException ex) {
            Logger.getLogger(DashboardDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Date parseDateSQL(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date d = new Date(sdf.parse(date).getTime());
        return d;
    }
}

class test {
    public static void main(String[] args) throws ParseException {
       DashboardDBContext d = new DashboardDBContext();
       d.getSuccessOrdersByDateRange("2022-07-01", "2022-07-14");
       d.getTotalOrdersByDateRange("2022-07-01", "2022-07-14");
    }
}
