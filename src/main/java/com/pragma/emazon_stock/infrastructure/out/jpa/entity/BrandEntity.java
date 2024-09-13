package com.pragma.emazon_stock.infrastructure.out.jpa.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "brand")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrandEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "brand_id")
    private Integer brandId;

    @Column(name = "brand_name", nullable = false ,unique = true, length = 50)
    private String brandName;

    @Column(name = "brand_description", nullable = false ,length = 120)
    private String brandDescription;

    @OneToMany(mappedBy = "articleBrand", cascade = CascadeType.ALL)
    private List<ArticleEntity> brandArticles;

}
