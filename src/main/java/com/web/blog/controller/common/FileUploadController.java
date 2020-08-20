package com.web.blog.controller.common;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import com.web.blog.model.BasicResponse;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
        @ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
        @ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

        @CrossOrigin(origins = { "http://i3c104.p.ssafy.io:3000", "http://localhost:3000", "http://localhost:8081" })
@RestController
@RequestMapping
public class FileUploadController {
    @ApiOperation(value = "프로필 사진 업로드한다.", response = String.class)
    @PostMapping("/file/upload")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile multipartFile) {
        try {
            InputStream fileStream = multipartFile.getInputStream();
            String fileName = multipartFile.getOriginalFilename();
            String dirPath = "/home/ubuntu/webFront/jobis5/s03p13c104/src/webapp/file";

            File directory = new File(dirPath);
            if( !directory.exists()){
                directory.mkdir();
            }

            File dest = new File(dirPath + "/" + fileName);
            multipartFile.transferTo(dest);
            return new ResponseEntity<>("파일업로드에 성공했습니다.", HttpStatus.OK);
        } catch(IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("파일업로드에 실패했습니다.", HttpStatus.NOT_IMPLEMENTED);
        }
    }
}