package com.akbaramanov.huffmanarchiver.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name="compressedFiles")
public class CompressedFiles {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column(name="filename")
    private String filename;


    @Column(name="type")
    private String type;

    @Column(name="data")
    @Lob
    private byte[] data;

    public CompressedFiles() {
    }

    public CompressedFiles(String fileName, byte[] data, String type) {
        this.filename = fileName;
        this.data = data;
        this.type = type;

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }


    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
