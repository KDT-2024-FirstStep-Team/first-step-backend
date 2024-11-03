package com.kdt.firststep.file;


import org.springframework.web.multipart.MultipartFile;

public interface S3FileService {
    UploadResponseDto upload(MultipartFile file);

    void delete(String fileName);
}
