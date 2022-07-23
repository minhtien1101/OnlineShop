/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package configs;

import java.util.UUID;

/**
 *
 * @author Admin
 */
public class TokenGenerator {
    
    public static String uniqueToken() {
    String token = UUID.randomUUID().toString();
    return token;

}
    
    public static void main(String[] args) {
        String rand = uniqueToken();
        System.out.println(rand);
    }
}

