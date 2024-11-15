package com.adidark.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "image")
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
public class ImageEntity extends BaseEntity{

    @Column(name = "imageurl", nullable = false)
    private String URL;

    @Column(name = "description", columnDefinition = "TEXT")
    @Lob
    private String description;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity productEntity;

}
