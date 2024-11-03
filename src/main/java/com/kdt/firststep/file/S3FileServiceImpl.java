package com.kdt.firststep.file;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class S3FileServiceImpl implements S3FileService {
    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Override
    public String upload(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());

        try {
            // 파일을 S3에 업로드
            amazonS3Client.putObject(bucket, fileName, file.getInputStream(), metadata);

            // 업로드된 파일의 URL을 가져오기
            return amazonS3Client.getUrl(bucket, fileName).toString();
        } catch (IOException e) {
            throw new RuntimeException("파일 업로드 중 오류가 발생했습니다.", e);
        }
    }

    @Override
    public void delete(String fileName) {
        amazonS3Client.deleteObject(bucket, fileName);
    }
}
