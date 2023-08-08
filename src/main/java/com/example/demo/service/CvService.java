package com.example.demo.service;

import com.example.demo.models.CV;
import com.example.demo.repositories.CvRepository;
import com.example.demo.service.storage.IAwsS3Service;
import com.example.demo.service.storage.IStorageService;
import com.example.demo.service.storage.UploadObject;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class CvService {

private final CvRepository cvRepository;

    private final IStorageService iStorageService;
    private final String userDocumentBucketName;

    public CvService(CvRepository cvRepository,
                                     @Qualifier(IAwsS3Service.NAME) IStorageService iStorageService,
                                     @Value("${SERVICE_BUCKET_NAME}") String bucketName) {
        this.cvRepository = cvRepository;
        this.iStorageService = iStorageService;
        this.userDocumentBucketName = bucketName + "/" + "UserCv";
    }

    public String saveUserUpload(long userId, MultipartFile cv) throws IOException {
        UploadObject uploadObject = new UploadObject();
        uploadObject.setBucketName(userDocumentBucketName);
        String name = cv.getOriginalFilename();
        uploadObject.setName(name);
        String url = iStorageService.uploadToBucket(cv.getInputStream(), uploadObject);
        CV newCv = cvRepository.findByUserId(userId).orElse(new CV());
        newCv.setUrl(url);
        newCv.setUserId(userId);
        cvRepository.save(newCv);
        return url;
    }

    public String getUrl(long userId){
        return cvRepository.findCvUrlByUserId(userId);
    }
}
