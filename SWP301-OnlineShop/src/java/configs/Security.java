/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package configs;

/**
 *
 * @author Admin
 */
public class Security {

    public static String USERNAME = "hieunvhe153769@fpt.edu.vn";
    public static String PASSWORD = "Skynet.com";
    public static String EMAGE_DEFAULT = "/assets/img/defaultUserAvatar.png";
    public static final int ADMIN_ROLL_ID = 1;
    public static final int MAKETING_ROLL_ID = 2;
    public static final int SALE_ROLL_ID = 3;
    public static final int CUSTOMER_ROLL_ID = 4;
    public static final int SALEMANAGE_ROLL_ID = 21;
    public static final int _ROLL_ID = 21;
    public static final int MAXIMUM_AGE = 60 * 60 * 24 * 10;
    public static final int MAXIMUM_AGE_TOKEN = 60 * 5;
    public static final boolean DEFAULT_STATUS = true;
    public static final boolean ACTIVE_STATUS = true;
    public static final boolean DEACTIVE_STATUS = false;
    public static final int MAXIMUM_AGE_TOKEN_REGISTER = 60 * 5;

    /*Start  Config of slider page*/
    public static final int SIZE_PAGE_SLIDER_LIST = 10; //Number of sliders per page
    /*End  Config of slider page*/
    
    /*Start  Config of cart page*/
    public static final int SIZE_PAGE_CART_LIST = 10; //Number of product per cart page 
    /*End  Config of cart page*/

    public static final boolean SHOW_STATUS = true;
    public static final boolean HIDE_STATUS = false;
}
