package com.youtube.jwt.entity;

import javax.persistence.*;

@Entity
@Table(name = "image_model")
public class ImageModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String type;
    @Column(length = 500000000)
    private byte[] picByte;

    public ImageModel(){

    }
    public ImageModel(Long id, String name, String type, byte[] picByte) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.picByte = picByte;
    }
}
