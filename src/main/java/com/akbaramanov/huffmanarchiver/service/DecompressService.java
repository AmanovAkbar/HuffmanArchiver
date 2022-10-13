package com.akbaramanov.huffmanarchiver.service;

import com.akbaramanov.huffmanarchiver.huffman.HuffmanCode;
import com.akbaramanov.huffmanarchiver.model.CompressedFiles;
import com.akbaramanov.huffmanarchiver.model.DecompressedFiles;
import com.akbaramanov.huffmanarchiver.repository.CompressedFilesRepository;
import com.akbaramanov.huffmanarchiver.repository.DecompressedFilesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.stream.Stream;

@Service
public class DecompressService {


    @Autowired
    private DecompressedFilesRepository decompressedFilesRepository;
    @Autowired
    private CompressedFilesRepository compressedFilesRepository;
    private HuffmanCode huffmanCode;
    public CompressedFiles saveFile (MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        fileName = fileName.substring(0, fileName.length()-4);
        byte[]data = file.getBytes();
        CompressedFiles compressedFiles = new CompressedFiles(fileName, data, "text/plain");
        return compressedFilesRepository.save(compressedFiles);
    }

    public DecompressedFiles decompress(String id) {
        CompressedFiles compressedFile = compressedFilesRepository.getReferenceById(id);
        String fileName = compressedFile.getFilename();
        byte[] data = compressedFile.getData();
        huffmanCode = new HuffmanCode(data);
        byte [] decompressedData = huffmanCode.decompress();
        DecompressedFiles decompressedFile = new DecompressedFiles(fileName, decompressedData);
        return decompressedFilesRepository.save(decompressedFile);

    }

    public DecompressedFiles decompressFileFromDB(String id) {
        CompressedFiles compressedFile = compressedFilesRepository.getReferenceById(id);
        String fileName = compressedFile.getFilename();
        fileName = fileName.substring(0, fileName.length()-4);
        String type = compressedFile.getType();
        byte[]data = compressedFile.getData();
        huffmanCode = new HuffmanCode(data);
        byte [] decompressedData = huffmanCode.decompress();
        DecompressedFiles decompressedFile = new DecompressedFiles(fileName, type, decompressedData);
        return decompressedFilesRepository.save(decompressedFile);

    }
    private void cleanDB(){
        decompressedFilesRepository.deleteAll();
    }
    public Stream<DecompressedFiles> getAllFiles(){
        return decompressedFilesRepository.findAll().stream();
    }

}
