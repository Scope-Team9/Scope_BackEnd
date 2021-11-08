package com.studycollaboproject.scope.service;

import com.studycollaboproject.scope.exception.ErrorCode;
import com.studycollaboproject.scope.exception.RestApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@Service
public class ImageService {
    public String saveImageData(String image) {
        String fileName;
        try {
            String data = image.split(",")[1];
            byte[] imageBytes = DatatypeConverter.parseBase64Binary(data);
            fileName = "/images/" + UUID.randomUUID() + ".png";
            String filePath = "." + fileName;
            try {
                BufferedImage bufImg = ImageIO.read(new ByteArrayInputStream(imageBytes));
                ImageIO.write(bufImg, "png", new File(filePath));
            } catch (IOException e) {
                e.printStackTrace();
                log.info("이미지 파일 저장 경로 에러");
                throw new RestApiException(ErrorCode.IMAGE_SAVE_ERROR);
            }
            log.info("이미지 파일 데이터 형식 에러");
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new RestApiException(ErrorCode.IMAGE_SAVE_ERROR);
        }
        return fileName;
    }
}
