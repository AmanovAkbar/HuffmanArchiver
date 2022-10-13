package com.akbaramanov.huffmanarchiver.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name="decompressedFiles")
public class DecompressedFiles {
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

    public DecompressedFiles() {
    }

    public DecompressedFiles(String filename,  byte[] data) {
        this.filename = filename;

        this.data = data;
    }
    public DecompressedFiles(String filename, String type, byte[] data) {
        this.filename = filename;
        this.type = type;
        this.data = data;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
