package com.studycollaboproject.scope.controller;

import com.studycollaboproject.scope.dto.ResponseDto;
import com.studycollaboproject.scope.exception.ErrorCode;
import com.studycollaboproject.scope.exception.RestApiException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@Slf4j
@Tag(name = "Image Controller", description = "이미지 등록")
public class ImageRestController {

    @Operation(summary = "이미지 등록")
    @PostMapping("/api/image")
    public ResponseDto saveImage(@Schema(description = "이미지 데이터(BASE64)") @RequestBody Map<String, String> map) {
        log.info("POST, /api/image, data={}", map.get("image"));
        String fileName = saveImageData(map.get("image"));
        log.info("fileName : {}", fileName);
        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("imageUrl", fileName);

        return new ResponseDto("200", "", responseMap);
    }

    private String saveImageData(String image) {
        String fileName;
        try {
            String data = image.split(",")[1];
            byte[] imageBytes = DatatypeConverter.parseBase64Binary(data);
            fileName = "/image/" + UUID.randomUUID() + ".png";
            String filePath = "." + fileName;
            try {
                BufferedImage bufImg = ImageIO.read(new ByteArrayInputStream(imageBytes));
                ImageIO.write(bufImg, "png", new File(filePath));
            } catch (IOException e) {
                e.printStackTrace();
                log.info("이미지 파일 저장 경로 에러");
                throw new RestApiException(ErrorCode.IMAGE_SAVE_ERROR);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            log.info("이미지 파일 데이터 형식 에러");
            throw new RestApiException(ErrorCode.IMAGE_SAVE_ERROR);
        }
        return fileName;
    }

}
