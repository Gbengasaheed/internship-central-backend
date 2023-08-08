package com.example.demo.controller;

import com.example.demo.annotation.ValidateMultipart;
import com.example.demo.models.IUserDetails;
import com.example.demo.service.CvService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.HttpHeaders.CONTENT_LOCATION;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cv")
@CrossOrigin(origins = "*")
public class CVController {

    private final CvService cvService;

    // max size of each cv is 5mb
    @PostMapping("/upload")
    @ResponseStatus(OK)
    @ValidateMultipart(extensions = "pdf,docx,doc", maxSize = 5242880)
    public void updateUserDocument(Authentication authentication, HttpServletResponse httpServletResponse,
                                   @RequestParam MultipartFile cv) throws IOException {
        long userId = IUserDetails.getId(authentication);
        String cvUrl = cvService.saveUserUpload(userId, cv);
        httpServletResponse.addHeader(CONTENT_LOCATION, cvUrl);
    }
}
