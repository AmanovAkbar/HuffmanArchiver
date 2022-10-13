package com.akbaramanov.huffmanarchiver.repository;

import com.akbaramanov.huffmanarchiver.model.CompressedFiles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompressedFilesRepository extends JpaRepository <CompressedFiles, String> {


}
