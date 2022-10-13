package com.akbaramanov.huffmanarchiver.repository;

import com.akbaramanov.huffmanarchiver.model.DecompressedFiles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DecompressedFilesRepository extends JpaRepository<DecompressedFiles, String> {

}
