package com.iseem.backend.Utils;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordHash {

    public static String generateHash(String password){
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean verifyPwd(String password, String hash){
        return BCrypt.checkpw(password, hash);
    }
    
}
