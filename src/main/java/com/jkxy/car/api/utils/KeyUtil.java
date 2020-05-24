package com.jkxy.car.api.utils;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class KeyUtil {

    public static String getKey() {
        return UUID.randomUUID().toString();
    }

    public static void main(String[] args) {
        System.out.println(KeyUtil.getKey());
    }
}
