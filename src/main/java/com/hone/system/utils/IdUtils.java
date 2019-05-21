package com.hone.system.utils;

import java.util.UUID;

/**
 * ID工具类
 */
public class IdUtils {

    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static void main(String[] args){
        System.out.println(uuid());
        System.out.println(uuid());
        System.out.println(uuid());
    }

}
