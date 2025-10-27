package com.p2f4.med_office.utils;

import org.mindrot.jbcrypt.BCrypt;

public class Encryption {

    private Encryption(){}

    public static String encryptWord(String word){
        return BCrypt.hashpw(word, BCrypt.gensalt());
    }

    public static boolean checkPassword(String word, String hashedWord){
        return BCrypt.checkpw(word, hashedWord);
    }

}
