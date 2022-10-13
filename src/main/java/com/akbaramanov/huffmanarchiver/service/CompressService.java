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
public class CompressService {
    @Autowired
    private CompressedFilesRepository compressedFilesRepository;
    @Autowired
    private DecompressedFilesRepository decompressedFilesRepository;
    private HuffmanCode huffmanCode;

    public DecompressedFiles saveFile(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        String contentType = file.getContentType();
        System.out.println(fileName + " " + contentType);
        byte[]data = file.getBytes();
        DecompressedFiles decompressedFile = new DecompressedFiles(fileName, contentType, data);
        return decompressedFilesRepository.save(decompressedFile);

    }

    public CompressedFiles compress(String id)  { //Decompress Id
        DecompressedFiles decompressedFile = decompressedFilesRepository.getReferenceById(id);
        String fileName = decompressedFile.getFilename();
        System.out.println(fileName + " id: " +id + " in compressservice.compress");
        String contentType = decompressedFile.getType();
        byte[]data = decompressedFile.getData();
        huffmanCode = new HuffmanCode(data);
        byte [] compressedData = huffmanCode.compress();
        CompressedFiles compressedFile = new CompressedFiles((fileName+".hfm"), compressedData, contentType);
        return compressedFilesRepository.save(compressedFile);
    }

    public CompressedFiles compressFileFromDB(String id){
        DecompressedFiles decompressedFile = decompressedFilesRepository.getReferenceById(id);
        String fileName = decompressedFile.getFilename()+".hfm";
        String contentType = decompressedFile.getType();
        if(contentType==null){
            contentType="text/plain";
        }
        byte data[] = decompressedFile.getData();
        huffmanCode = new HuffmanCode(data);
        byte [] compressedData = huffmanCode.compress();
        CompressedFiles compressedFile = new CompressedFiles(fileName, compressedData, contentType);
        return compressedFilesRepository.save(compressedFile);
    }
    private void cleanDB(){
        compressedFilesRepository.deleteAll();
    }

    public Stream<CompressedFiles> getAllFiles(){
        return compressedFilesRepository.findAll().stream();
    }



}
