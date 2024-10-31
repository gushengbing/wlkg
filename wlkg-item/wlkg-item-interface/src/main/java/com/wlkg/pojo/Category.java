package com.wlkg.pojo;

import lombok.Data;

import javax.persistence.*;

@Table(name="tb_category")
@Data
public class Category {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(name = "parent_id")
    private Long parentId;
    @Column(name = "is_parent")
    private Boolean isParent;
    private Integer sort;
}
