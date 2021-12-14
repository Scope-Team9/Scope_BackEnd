package com.studycollaboproject.scope.controller;

import com.studycollaboproject.scope.dto.ResponseDto;
import com.studycollaboproject.scope.service.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@Slf4j
@Tag(name = "Image Controller", description = "이미지 등록")
public class ImageRestController {
    private final ImageService imageService;

    @Operation(summary = "이미지 등록")
    @PostMapping("/api/image")
    public ResponseEntity<Object> saveImage(@RequestBody String image) {
        String fileName = imageService.saveImageData(image);
        log.info("fileName : {}", fileName);
        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("imageUrl", fileName);
        return new ResponseEntity<>(
                new ResponseDto( "이미지 등록 성공", responseMap),
                HttpStatus.CREATED
        );
    }
}
