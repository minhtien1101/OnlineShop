/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import model.Feature;
import model.Role;

/**
 *
 * @author Admin
 */
public class RoleDBContext extends DBContext {

    public ArrayList<Role> getAllRole() {
        ArrayList<Role> roles = new ArrayList<>();
        String sql = "SELECT [id]\n"
                + "      ,[name]\n"
                + "  FROM [dbo].[Role]\n";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Role role = new Role();
                role.setId(rs.getInt(1));
                role.setName(rs.getString(2));
                roles.add(role);
            }
        } catch (Exception e) {
        }
        return roles;
    }

    public static void main(String[] args) {
        RoleDBContext bContext = new RoleDBContext();
        System.out.println(bContext.getAllRole().size());
    }

    public LinkedHashMap<Feature, Boolean> getAllowFeatures(int role) {
        String sql = "SELECT [Feature].id, [Feature].url, [Feature].name, [Role_Feature].enable\n"
                + "from [Role]\n"
                + "inner join Role_Feature on [Role].id = Role_Feature.roleId\n"
                + "inner join Feature on [Role_Feature].featureId = [Feature].id\n"
                + "WHERE Role_Feature.roleId = ?";
        try {
            LinkedHashMap<Feature, Boolean> features = new LinkedHashMap<Feature, Boolean>();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, role);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Feature f = new Feature();
                f.setId(rs.getInt("id"));
                f.setName(rs.getString("name"));
                f.setUrl(rs.getString("url"));
                features.put(f, rs.getBoolean("enable"));
            }
            return features;
        } catch (Exception e) {
        }
        return null;
    }

    public ArrayList<Feature> getPublicFeature() {
        String sql = "SELECT id, [name], [url] FROM Feature\n"
                + "WHERE isPublic = 1";
        try {
            ArrayList<Feature> publicFeatures = new ArrayList<>();
            PreparedStatement ps = connection.prepareStatement(sql);;
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Feature f = new Feature();
                f.setId(rs.getInt("id"));
                f.setName(rs.getString("name"));
                f.setUrl(rs.getString("url"));
                publicFeatures.add(f);
            }
            return publicFeatures;
        } catch (Exception e) {
        }
        return null;
    }

    public ArrayList<Feature> getFeatureByGroup(String groupName) {
        String sql = "SELECT [Feature].[id]\n"
                + "      ,[Feature].[name]\n"
                + "      ,[url]\n"
                + "      ,[isPublic]\n"
                + "	  ,[Feature_Group].[name]\n"
                + "  FROM [dbo].[Feature] inner join [Feature_Group] on [Feature].groupID = [Feature_Group].id\n"
                + "  WHERE Feature_Group.[name] = ?";
        try {
            ArrayList<Feature> features = new ArrayList<>();
            PreparedStatement ps = connection.prepareStatement(sql);;
            ps.setString(1, groupName);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Feature f = new Feature();
                f.setId(rs.getInt("id"));
                f.setName(rs.getString("name"));
                f.setUrl(rs.getString("url"));
                features.add(f);
            }
            return features;
        } catch (Exception e) {
        }
        return null;
    }

    public void insertNewRole(String[] permissionsID, String roleName) throws SQLException {
        try {
            connection.setAutoCommit(false);
            String sql = "INSERT INTO [dbo].[Role]\n"
                    + "           ([status]\n"
                    + "           ,[name])\n"
                    //                    + "           ,[isSuperAdmin])\n"
                    + "     VALUES\n"
                    + "           (1, ?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, roleName);
            ps.executeUpdate();
            String sql1 = "Select @@IDENTITY as roleId";
            PreparedStatement ps1 = connection.prepareStatement(sql1);
            ResultSet r = ps1.executeQuery();
            int roleID = -1;
            if (r.next()) {
                roleID = r.getInt("roleId");
            }

            String sql2 = "SELECT id as featureID FROM Feature WHERE isPublic = 0";
            PreparedStatement ps2 = connection.prepareStatement(sql2);
            ResultSet rs2 = ps2.executeQuery();
            ArrayList<String> listFeatureID = new ArrayList<>();
            while (rs2.next()) {
                listFeatureID.add(rs2.getString("featureID"));
            }
            String sql3 = "INSERT INTO [dbo].[Role_Feature]\n"
                    + "           ([roleId]\n"
                    + "           ,[enable]\n"
                    + "           ,[featureId])\n"
                    + "     VALUES\n"
                    + "           (?, ?, ?)";
            PreparedStatement ps3 = connection.prepareStatement(sql3);
            for (String featureID : listFeatureID) {
                if (Arrays.asList(permissionsID).contains(featureID)) {
                    ps3.setInt(1, roleID);
                    ps3.setBoolean(2, true);
                    ps3.setInt(3, Integer.parseInt(featureID));
                    ps3.executeUpdate();
                } else {
                    ps3.setInt(1, roleID);
                    ps3.setBoolean(2, false);
                    ps3.setInt(3, Integer.parseInt(featureID));
                    ps3.executeUpdate();
                }
            }
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
            connection.close();
        }
    }

    public boolean checkRoleExist(String roleName) throws SQLException {
        try {
            String sql = "select [Role].[name] from [Role] where [Role].[name] = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, roleName);
            ResultSet r = ps.executeQuery();
            if(r.next())
            {
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            connection.close();
        }
        return false;
    }

    public void updateRole(int roleID, String[] permissionID) throws SQLException {
        try {
            connection.setAutoCommit(false);
            String sql = "UPDATE dbo.[Role_Feature]\n"
                    + "   SET [enable] = 0\n"
                    + " WHERE roleId = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, roleID);
            stm.executeUpdate();

            String sql2 = "IF (NOT EXISTS(SELECT [Role_Feature].featureId FROM [Role_Feature] WHERE [Role_Feature].roleId = ? AND [Role_Feature].featureId = ?))\n"
                    + "BEGIN\n"
                    + "INSERT INTO [dbo].[Role_Feature](roleId, [enable], featureId)\n"
                    + "VALUES (?, 1, ?)\n"
                    + "END\n"
                    + "ELSE\n"
                    + "BEGIN\n"
                    + "UPDATE dbo.[Role_Feature]\n"
                    + "SET [enable] = 1\n"
                    + "WHERE roleId = ? and featureId=?\n"
                    + "END";
            PreparedStatement stm2 = connection.prepareStatement(sql2);
            for (String permission : permissionID) {
                stm2.setInt(1, roleID);
                stm2.setInt(2, Integer.parseInt(permission));
                stm2.setInt(3, roleID);
                stm2.setInt(4, Integer.parseInt(permission));
                stm2.setInt(5, roleID);
                stm2.setInt(6, Integer.parseInt(permission));
                stm2.executeUpdate();
            }
            connection.commit();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
            connection.close();
        }
    }

    public ArrayList<Feature> getEnabledFeature(int roleID, String groupName) {
        String sql = "select * \n"
                + "from Role_Feature inner join Feature on Role_Feature.featureId = Feature.id\n"
                + "inner join Feature_Group on Feature_Group.id = Feature.groupID\n"
                + "where\n"
                + "Role_Feature.roleId = ? and Feature_Group.name = ?";
        try {
            ArrayList<Feature> features = new ArrayList<>();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, roleID);
            ps.setString(2, groupName);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Feature f = new Feature();
                f.setId(rs.getInt("id"));
                f.setName(rs.getString("name"));
                f.setUrl(rs.getString("url"));
                f.setEnabled(rs.getBoolean("enable"));
                features.add(f);
            }
            return features;
        } catch (Exception e) {
        }
        return null;
    }
}
