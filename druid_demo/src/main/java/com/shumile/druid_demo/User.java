package com.shumile.druid_demo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
    private long id;

    private String name;

    private int age;

}
