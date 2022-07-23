package model;

/**
 *
 * @author Admin
 */
public class User {

    private int id;
    private String password;
    private String avatar;
    private String fullname;
    private boolean gender;
    private String mobile;
    private String address;
    private String username;
    private Role role;
    private boolean status;
    private String email;

    public User() {
    }

    public User(String password, String avatar, String email, String fullname, boolean gender, String mobile, String address, boolean status) {
        this.password = password;
        this.avatar = avatar;
        this.email = email;
        this.fullname = fullname;
        this.gender = gender;
        this.mobile = mobile;
        this.address = address;
        this.status = status;
    }
    public User(int id, String password, String avatar, String email, String fullname, boolean gender, String mobile, String address, boolean status) {
        this.id = id;
        this.password = password;
        this.avatar = avatar;
        this.email = email;
        this.fullname = fullname;
        this.gender = gender;
        this.mobile = mobile;
        this.address = address;
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }
    public String getNameGender() {
        return gender?"Male":"Female";
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", password=" + password + ", avatar=" + avatar + ", email=" + email + ", fullname=" + fullname + ", gender=" + gender + ", mobile=" + mobile + ", address=" + address + ", status=" + status + ", role=" + role + '}';
    }

}
