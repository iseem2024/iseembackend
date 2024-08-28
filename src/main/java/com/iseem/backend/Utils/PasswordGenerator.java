package com.iseem.backend.Utils;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class PasswordGenerator {

    public static String generatePassword(int length) {
        // Définir les ensembles de caractères
        String upperCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerCase = "abcdefghijklmnopqrstuvwxyz";
        String digits = "0123456789";
        String specialChars = "!@#$%^&*()-_+=<>?";

        // Combiner tous les ensembles de caractères
        String allChars = upperCase + lowerCase + digits + specialChars;
        
        // Utiliser SecureRandom pour générer des valeurs aléatoires
        SecureRandom random = new SecureRandom();
        
        // StringBuilder pour construire le mot de passe
        StringBuilder password = new StringBuilder();

        // Assurer l'inclusion d'au moins un caractère de chaque ensemble
        password.append(upperCase.charAt(random.nextInt(upperCase.length())));
        password.append(lowerCase.charAt(random.nextInt(lowerCase.length())));
        password.append(digits.charAt(random.nextInt(digits.length())));
        password.append(specialChars.charAt(random.nextInt(specialChars.length())));

        // Compléter les caractères restants
        for (int i = 4; i < length; i++) {
            password.append(allChars.charAt(random.nextInt(allChars.length())));
        }

        // Mélanger les caractères du mot de passe pour éviter les schémas prédictibles
        String finalPassword = shuffleString(password.toString());
        return finalPassword;
    }

    // Fonction pour mélanger les caractères d'une chaîne
    private static String shuffleString(String input) {
        List<Character> characters = new ArrayList<>();
        for (char c : input.toCharArray()) {
            characters.add(c);
        }
        StringBuilder output = new StringBuilder(input.length());
        while (!characters.isEmpty()) {
            int randPicker = (int)(Math.random() * characters.size());
            output.append(characters.remove(randPicker));
        }
        return output.toString();
    }

    public static void main(String[] args) {
        // Générer un mot de passe de 8 caractères
        String password = generatePassword(8);
        System.out.println("Generated Password: " + password);
    }
}
