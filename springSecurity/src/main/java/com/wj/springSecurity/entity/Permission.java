package com.wj.springSecurity.entity;

import lombok.Data;

@Data
public class Permission {
    private Integer id;

    private String url;

    private String name;

    private String description;

}
