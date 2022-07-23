/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package configs;

import java.util.Random;

/**
 *
 * @author Admin
 */
public class HandleGenerate {

    private static final int PASSWORD_LENGTH = 6;

    public static String generatePassword() {
        String capitalCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
        String specialCharacters = "!@#$";
        String numbers = "1234567890";
        String combinedChars = capitalCaseLetters + lowerCaseLetters + specialCharacters + numbers;
        Random random = new Random();
        char[] password = new char[PASSWORD_LENGTH];
        int min = 0;
        int max = combinedChars.length() - 1;
        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            int random_index = (int) Math.floor(Math.random() * (max - min + 1) + min);
            password[i] = combinedChars.charAt(random_index);
        }
        return String.valueOf(password);
    }

    public static String generateSubNameFile() {
        String capitalCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "1234567890";
        String combinedChars = capitalCaseLetters + lowerCaseLetters + numbers;
        Random random = new Random();
        char[] password = new char[PASSWORD_LENGTH];
        int min = 0;
        int max = combinedChars.length() - 1;
        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            int random_index = (int) Math.floor(Math.random() * (max - min + 1) + min);
            password[i] = combinedChars.charAt(random_index);
        }
        return String.valueOf(password);
    }

    public static void main(String[] args) {
        System.out.println(generatePassword());
    }
}
