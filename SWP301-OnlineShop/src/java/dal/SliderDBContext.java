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
import model.Slider;
import model.User;

/**
 *
 * @author DELL
 */
public class SliderDBContext extends DBContext {

    public ArrayList<Slider> getSlidersActive() {
        ArrayList<Slider> listSlider = new ArrayList<>();
        try {
            String sql = "Select * from Slider\n"
                    + "Where status = 1";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Slider slider = new Slider();
                slider.setId(rs.getInt(1));
                slider.setTitle(rs.getString(2));
                slider.setImage(rs.getString(3));
                slider.setBacklink(rs.getString(4));
                slider.setStatus(rs.getBoolean(5));
                User user = new User();
                user.setId(rs.getInt(6));
                slider.setUser(user);
                slider.setNote(rs.getString(7));
                listSlider.add(slider);
            }
        } catch (SQLException e) {
        }
        return listSlider;
    }

    public ArrayList<Slider> getAllSlider() {
        ArrayList<Slider> listSlider = new ArrayList<>();
        Slider slider = null;
        try {
            String sql = "Select * from Slider";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                slider = new Slider();
                slider.setId(rs.getInt(1));
                slider.setTitle(rs.getString(2));
                slider.setImage(rs.getString(3));
                slider.setBacklink(rs.getString(4));
                slider.setStatus(rs.getBoolean(5));
                User user = new User();
                user.setId(rs.getInt(6));
                slider.setUser(user);
                slider.setNote(rs.getString(7));
                listSlider.add(slider);
            }
        } catch (SQLException e) {
        }
        return listSlider;
    }

    public boolean changeStatus(int id, boolean status) {
        try {
            String sql = "UPDATE [Slider] set status = ? where id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setBoolean(1, status);
            ps.setInt(2, id);

            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public ArrayList<Slider> searchSliders(int status, String search, int searchBy) {
        ArrayList<Slider> lst = new ArrayList<>();
        try {
            String sql = "select s.*, u.[password], u.avatar, u.fullname, u.status "
                    + "[statusUser], u.mobile, u.address, u.roleId, u.email, u.gender from Slider s, [user] u where s.userid = u.id and 1=1";

            if (status != -1) {
                sql += " AND s.status = " + status;
            }
            if (!search.isEmpty()) {
                if (searchBy == 0) {
                    sql += " AND s.backlink = '" + search + "'";
                } else {
                    sql += " AND s.title like '%" + search + "%'";
                }
            }
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            Slider slider = null;
            while (rs.next()) {
                slider = new Slider();
                slider.setId(rs.getInt("id"));
                slider.setTitle(rs.getString("title"));
                slider.setImage(rs.getString("image"));
                slider.setBacklink(rs.getString("backlink"));
                slider.setStatus(rs.getBoolean("status"));
                slider.setNote(rs.getString("notes"));

                slider.setUser(new User(rs.getInt("userId"), rs.getString("password"), rs.getString("avatar"), rs.getString("email"),
                        rs.getString("fullname"), rs.getBoolean("gender"), rs.getString("mobile"), rs.getString("address"), rs.getBoolean("statusUser")));
                lst.add(slider);
            }
        } catch (Exception ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lst;
    }

    public ArrayList<Slider> getSliderByIndex(int index, int sizePage, int status, String search, int searchBy) {
        ArrayList<Slider> lst = new ArrayList<>();
        try {
            String sql = "select s.*, u.[password], u.avatar, u.fullname, u.status "
                    + "[statusUser], u.mobile, u.address, u.roleId, u.email, u.gender from Slider s, [user] u where s.userid = u.id";
            if (status != -1) {
                sql += " AND s.status = " + status;
            }
            if (!search.isEmpty()) {
                if (searchBy == 0) {
                    sql += " AND s.backlink = '" + search + "'";
                } else {
                    sql += " AND s.title like '%" + search + "%'";
                }
            }

            sql += " order by Id";
            sql += " offset ? rows fetch next ? rows only;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, ((index - 1) * 2));
            ps.setInt(2, sizePage);
            ResultSet rs = ps.executeQuery();
            Slider slider = null;
            while (rs.next()) {
                slider = new Slider();
                slider.setId(rs.getInt("id"));
                slider.setTitle(rs.getString("title"));
                slider.setImage(rs.getString("image"));
                slider.setBacklink(rs.getString("backlink"));
                slider.setStatus(rs.getBoolean("status"));
                slider.setNote(rs.getString("notes"));

                slider.setUser(new User(rs.getInt("userId"), rs.getString("password"), rs.getString("avatar"), rs.getString("email"),
                        rs.getString("fullname"), rs.getBoolean("gender"), rs.getString("mobile"), rs.getString("address"), rs.getBoolean("statusUser")));
                lst.add(slider);
            }
        } catch (SQLException ex) {

        }
        return lst;
    }

    public Slider getSliderById(int id) {
        Slider slider = null;
        try {
            String sql = "select s.*, u.[password], u.avatar, u.fullname, u.status "
                    + "[statusUser], u.mobile, u.address, u.roleId, u.email, u.gender"
                    + " from Slider s, [user] u where s.userid = u.id and s.id = ?";

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                slider = new Slider();
                slider.setId(rs.getInt("id"));
                slider.setTitle(rs.getString("title"));
                slider.setImage(rs.getString("image"));
                slider.setBacklink(rs.getString("backlink"));
                slider.setStatus(rs.getBoolean("status"));
                slider.setNote(rs.getString("notes"));

                slider.setUser(new User(rs.getInt("userId"), rs.getString("password"), rs.getString("avatar"), rs.getString("email"),
                        rs.getString("fullname"), rs.getBoolean("gender"), rs.getString("mobile"), rs.getString("address"), rs.getBoolean("statusUser")));

            }
        } catch (SQLException ex) {

        }
        return slider;
    }

    public boolean addSlider(Slider s) {
        try {
            String sql = "insert into slider(title, image, backlink, status, Userid , notes)"
                    + "values (?,?,?,?,?,?)";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, s.getTitle());
            stm.setString(2, s.getImage());
            stm.setString(3, s.getBacklink());
            stm.setBoolean(4, s.isStatus());
            stm.setInt(5, s.getUser().getId());
            stm.setString(6, s.getNote());

            return stm.executeUpdate() > 0;
        } catch (Exception ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean updateSlider(Slider s) {
        try {
            String sql = "update slider set title=?, image=?, backlink=?, status=?, userid=?, notes=? where id = ?";

            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, s.getTitle());
            stm.setString(2, s.getImage());
            stm.setString(3, s.getBacklink());
            stm.setBoolean(4, s.isStatus());
            stm.setInt(5, s.getUser().getId());
            stm.setString(6, s.getNote());
            stm.setInt(7, s.getId());

            return stm.executeUpdate() > 0;
        } catch (Exception ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static void main(String[] args) {
        SliderDBContext db = new SliderDBContext();
//        ArrayList<Slider> listSlider = db.getAllSlider();
//        for (Slider slider : listSlider) {
//            System.out.println(slider.getTitle());
//        }
//        ArrayList<Slider> listSlider = db.getSliderByIndex(1, 0, "");
//        for (Slider slider : listSlider) {
//            System.out.println(slider.getTitle());
//        }

//Slider s = new Slider();
//        s.setTitle("Hot Hot Hot");
//        s.setImage("slider1.jpg");
//        s.setBacklink("abc");
//        s.setNote("Sale 50%");
//        s.setStatus(true);
//        User u = new User();
//        u.setId(6);
//        s.setUser(u);
//        Slider s = db.getSliderById(7);
//        s.setNote("20%");
//        rs = db.updateSlider(s);
//        System.out.println(rs);
        db.changeStatus(1, false);
    }

}
