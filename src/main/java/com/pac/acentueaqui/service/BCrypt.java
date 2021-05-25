package com.pac.acentueaqui.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCrypt {
    public static void main(String[] args) {
        BCryptPasswordEncoder passswordEncoder = new BCryptPasswordEncoder();
        System.out.println(passswordEncoder.encode("admin123"));
    }
}