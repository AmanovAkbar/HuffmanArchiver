package com.akbaramanov.huffmanarchiver.controller;

import com.akbaramanov.huffmanarchiver.auxiliaryClasses.ResponseFile;
import com.akbaramanov.huffmanarchiver.auxiliaryClasses.ResponseMessage;
import com.akbaramanov.huffmanarchiver.model.CompressedFiles;
import com.akbaramanov.huffmanarchiver.model.DecompressedFiles;
import com.akbaramanov.huffmanarchiver.service.CompressService;
import com.akbaramanov.huffmanarchiver.service.DecompressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class HuffmanController {
    @Autowired
    private DecompressService decompressService;
    @Autowired
    private CompressService compressService;




    @PostMapping("/uploadtocompress")
    public ResponseEntity<ResponseMessage>uploadCompress(@RequestParam("file")MultipartFile file) throws IOException {
        String message = "";
        try{
            DecompressedFiles dcF  = compressService.saveFile(file);

            message = "Uploaded the file successfully: " + file.getOriginalFilename() + " " + dcF.getId();
            System.out.println(message);


            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message, dcF.getId()));
        }catch (Exception e){
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message, "null"));

        }


    }
    @GetMapping("/filesdecompressed")
    public ResponseEntity<List<ResponseFile>> getListFilesCompress(){
        List<ResponseFile> files = decompressService.getAllFiles().map(DecompressedFiles->{
            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/filesdecompressed/").path(DecompressedFiles.getId()).toUriString();
            return new ResponseFile(DecompressedFiles.getFilename(), fileDownloadUri);
        }).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(files);
    }
    @GetMapping("/filesdecompressed/{id}")
    public ResponseEntity<byte[]>getCompressed (@PathVariable String id){
        CompressedFiles file = compressService.compress(id);

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file.getData());
    }
    @PostMapping("/uploadtodecompress")
    public ResponseEntity<ResponseMessage>uploadDecompress(@RequestParam("file")MultipartFile file)  {
        String message = "";
        try{
            CompressedFiles cF  = decompressService.saveFile(file);
            message = "Uploaded the file successfully: " + file.getOriginalFilename();



            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message, cF.getId()));
        }catch (Exception e){
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message, "null"));
        }


    }

    @GetMapping("/filescompressed")
    public ResponseEntity<List<ResponseFile>> getListFilesDecompress(){
        List<ResponseFile> files = compressService.getAllFiles().map(CompressedFiles->{
            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/filescompressed/").path(CompressedFiles.getId()).toUriString();
            return new ResponseFile(CompressedFiles.getFilename(), fileDownloadUri);
        }).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(files);
    }
    @GetMapping("/filescompressed/{id}")
    public ResponseEntity<byte[]>getDecompressed (@PathVariable String id){
        DecompressedFiles file = decompressService.decompress(id);
        String fileName = file.getFilename();//.substring(0, file.getFilename().length()-4);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"").body(file.getData());
    }



}
