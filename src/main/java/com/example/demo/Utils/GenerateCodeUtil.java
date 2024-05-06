package com.example.demo.Utils;

import java.util.Random;

public class GenerateCodeUtil {
    public static String generateCode() {
        Random random = new Random();
        long codeRandom = random.nextInt(900000) + 100000; //Tạo ra mã gồm 6 chữ số
        return String.valueOf(codeRandom);
    }
}
