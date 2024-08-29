package com.pragma.emazon_stock.infrastructure.out.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "article")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id")
    private Integer articleId;

    @Column(name = "article_name", nullable = false ,unique = true, length = 120)
    private String articleName;

    @Column(name = "article_description", nullable = false ,length = 160)
    private String articleDescription;

    @Column(name = "article_amount", nullable = false)
    private Integer articleAmount;

    @Column(name = "article_price", nullable = false)
    private Double articlePrice;

    @ManyToMany
    @JoinTable(
            name = "article_categories",
            joinColumns = @JoinColumn(name = "article_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<CategoryEntity> articleCategories;

}
