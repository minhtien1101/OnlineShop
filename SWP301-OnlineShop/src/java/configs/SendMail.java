/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package configs;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import model.Product;
import model.User;

/**
 *
 * @author Admin
 */
public class SendMail {

    public static void send(String to, String subject, String msg, final String user, final String pass) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, pass);
            }
        });
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject, "text/html; charset=UTF-8");
            message.setContent(msg, "text/html; charset=UTF-8");
            Transport.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public static void sendPassword(String email, String fullname, String password, String url) {
        String subject = "[OnlineShop]";
        String message = "<!DOCTYPE html>\n"
                + "<html lang=\"en-UTF-8\">\n"
                + "<head>\n"
                + "</head>\n"
                + "<body>\n"
                + "    <h3 style=\"color:blue\">Xin chào " + fullname + "</h3>\n"
                + "    <p>Tài khoản của bạn dã được khởi tạo thành công bằng tài khoản email này</p>\n"
                + "    <p>Password: <span style=\"color:red\">" + password + "</span></p>\n"
                + "    <p>Lưu ý không chia sẻ mật khẩu cho bất kì ai</p>\n"
                + "    <p>Click vào linh này để đồi mật khẩu mới an toàn hơn <a href=\"" + url + "\">Nhấp vào đây</a></p>\n"
                + "</body>\n"
                + "</html>";
        SendMail.send(email, subject, message, Security.USERNAME, Security.PASSWORD);
    }

    public static void setContent(String username, String code, String email) {
        String subject = "[OnlineShop] Please verify your email.";
        String message = "<!DOCTYPE html>\n"
                + "<html lang=\"en\">\n "
                + "\n"
                + "<head>\n "
                + "</head>\n"
                + "\n"
                + "<body>\n"
                + "    <h3 style=\"color: blue;\">Xin chào " + username + " !</h3>\n"
                + "    <div>Link xác minh tài khoản của bạn là : <a href=\"" + code + "\">VERIFY</a></div>\n"
                + "    <div>Thư này được tạo ra tự động.</div>\n"
                + "    <div>Nếu bạn cần trợ giúp hoặc có câu hỏi, hãy gửi email đến doctris.care@gmail.com bất cứ lúc nào.</div>\n"
                + "    <h3 style=\"color: blue;\">Trân trọng!</h3>\n"
                + "\n"
                + "</body>\n"
                + "\n"
                + "</html>";
        SendMail.send(email, subject, message, Security.USERNAME, Security.PASSWORD);
    }

    public static void sendMailOrder(String email, int idOrder, int idPayment,
            Product[] productsOrder, String[] priceDiscountVnd, User inforCustomer, String total, String dateOrder,
            String nameBank, String accNumber, String ownerAccount) {
        String payment = (idPayment == 0) ? "Payment on delivery" : "Bank payment";
        String subject = "[OnlineShop] Order Has Been Sucessfully.";
        String message = "<!DOCTYPE html>\n"
                + "<html lang=\"en\">\n"
                + "\n"
                + "<head>\n"
                + "</head>\n"
                + "\n"
                + "<body>\n"
                + "    <div>Hello You!</div>\n"
                + "    <div>Your order #" + idOrder + " was successfully ordered on " + dateOrder + ".</div>\n"
                + "    <h3>Order Information</h3>\n"
                + "    <table>\n"
                + "        <tr>\n"
                + "            <td>Code Order: </td>\n"
                + "            <td>#" + idOrder + "</td>\n"
                + "        </tr>\n"
                + "        <tr>\n"
                + "            <td>Name Customer: </td>\n"
                + "            <td>" + inforCustomer.getFullname() + "</td>\n"
                + "        </tr>\n"
                + "            <td>Gender: </td>\n"
                + "            <td>" + inforCustomer.getNameGender() + "</td>\n"
                + "        </tr>\n"
                + "        <tr>\n"
                + "            <td>Order Date: </td>\n"
                + "            <td>" + dateOrder + "</td>\n"
                + "        </tr>\n"
                + "        <tr>\n"
                + "            <td>Phone: </td>\n"
                + "            <td>" + inforCustomer.getMobile() + "</td>\n"
                + "        </tr>\n"
                + "        <tr>\n"
                + "            <td>Address: </td>\n"
                + "            <td>" + inforCustomer.getAddress() + "</td>\n"
                + "        </tr>\n"
                + "        <tr>\n"
                + "            <td>Payment methods: </td>\n"
                + "            <td>" + payment + "</td>\n"
                + "        </tr>\n"
                + "\n"
                + "    </table>\n";
        message += "<table style=\"margin-top: 10px;\">\n";
        for (int i = 0; i < productsOrder.length; i++) {
            message += "       <tr>\n"
                    + "            <td colspan=\"2\">" + (i + 1) + ". " + productsOrder[i].getName() + "</td>\n"
                    + "        </tr>\n"
                    + "        <tr>\n"
                    + "            <td>Quantity: </td>\n"
                    + "            <td>" + productsOrder[i].getQuantity() + "</td>\n"
                    + "        </tr>\n"
                    + "        <tr>\n"
                    + "            <td>Price: </td>\n"
                    + "            <td>" + priceDiscountVnd[i] + "</td>\n"
                    + "        </tr>\n";
        }
        message += "</table>\n"
                + "    <br/>\n"
                + "    <div style=\"font-weight: bold;\">Total Amount: " + total + "</div>\n"
                + "    <br/>\n"
                + "    <table>\n";
        if (idPayment == 1) {
            message += "<tr>\n"
                    + "            <td colspan=\"2\" style=\"font-weight: bold; font-size: 18px; color: red;\">Please follow the instructions below to pay for your order:</td>\n"
                    + "        </tr>\n"
                    + "        <tr>\n"
                    + "            <td>Bank Name: </td>\n"
                    + "            <td>" + nameBank + "</td>\n"
                    + "        </tr>\n"
                    + "        <tr>\n"
                    + "            <td>Account Number: </td>\n"
                    + "            <td>" + accNumber + "</td>\n"
                    + "        </tr>\n"
                    + "        <tr>\n"
                    + "            <td>Owner Account: </td>\n"
                    + "            <td>" + ownerAccount + "</td>\n"
                    + "        </tr>\n"
                    + "        <tr>\n"
                    + "            <td>Amount: </td>\n"
                    + "            <td>" + total + "</td>\n"
                    + "        </tr>\n"
                    + "        <tr>\n"
                    + "            <td>Money transfer content: </td>\n"
                    + "            <td>Payment of order code #" + idOrder + "</td>\n"
                    + "        </tr>\n"
                    + "        <tr>\n"
                    + "            <td colspan=\"2\" style=\"font-weight: bold; font-size: 18px;color: red;\">* If you do not pay, the order will not be delivered.</td>\n"
                    + "        </tr>\n"
                    + "        <tr>\n"
                    + "            <td></td>\n"
                    + "            <td><div style=\"padding-top: 38px; font-weight: bold; font-size: 17px;\">Thank you!</div></td>\n"
                    + "        </tr>\n";
        } else {
            message += "        <tr>\n"
                    + "            <td colspan=\"2\" style=\"font-weight: bold; font-size: 18px; color: red;\">Your order will be paid when you receive it.</td>\n"
                    + "        </tr>\n"
                    + "        <tr>\n"
                    + "            <td></td>\n"
                    + "            <td><div style=\"padding-top: 38px; font-weight: bold; font-size: 17px;\">Thank you!</div></td>\n"
                    + "        </tr>\n";
        }
        message += "</table>\n"
                + "</body>\n"
                + "\n"
                + "</html>";
        SendMail.send(email, subject, message, Security.USERNAME, Security.PASSWORD);
    }

    public static void sendMailFeedbackCompleted(String emailUserBuy, ArrayList<Product> listOrderProductOfUser) {
        String subject = "[OnlineShop] Your Order Has Been Delivered Successfully!";
        long totalCost = 0;
        for (Product product : listOrderProductOfUser) {
            totalCost += product.getUnitPrice();
        }
        System.out.println("Total " + totalCost);
        String message1 = "<!DOCTYPE html>\n"
                + "<html>\n"
                + "<head>\n"
                + "</head>\n"
                + "<body  style=\"display: inline-table;\">\n"
                + "\n"
                + "	<div>Hello You!</div>\n"
                + "    <div>Your Order Has Been Delivered Successfully!</div>\n"
                + "    <div>Can you take a moment to give Feedback on the product you purchased at our website?</div>\n"
                + "	<div>List below are the products and quantities of the products you have purchased: </div><br>";

        for (Product product : listOrderProductOfUser) {
            message1 += " <table>\n"
                    + "  	<tr>\n"
                    + "    <th>Product Name </th>\n"
                    + "    <th>Quantity</th>\n"
                    + "    <th>Unit Price</th>\n"
                    + "    </tr>\n"
                    + "    <tr>\n"
                    + "    <td>" + product.getName() + "</td>\n"
                    + "    <td>" + product.getQuantity() + "</td>\n"
                    + "    <td>" + product.getUnitPrice() + "</td>\n"
                    + "    </tr>";
        }
        message1 += "</table>\n"
                + "<div>Total Cost: " + totalCost + "</div><br>\n"
                + "   <div> If you have any problems with the product you received, please reply to this email, we will contact you as soon as possible.</div>\n"
                + "   <div>Thanks you for shopping on our website!</div>\n"
                + "</body>\n"
                + "</html>";
        SendMail.send(emailUserBuy, subject, message1, Security.USERNAME, Security.PASSWORD);
    }

    public static void main(String[] args) {
        String subject = "Test";
        String message = "tesing";
        SendMail.sendPassword("hieuldhe150703@fpt.edu.vn", "hello", Security.USERNAME, Security.PASSWORD);
    }

}
