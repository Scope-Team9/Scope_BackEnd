package com.studycollaboproject.scope.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.studycollaboproject.scope.exception.BadRequestException;
import com.studycollaboproject.scope.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

//@Service
@RequiredArgsConstructor
@Slf4j
public class ImageS3ServiceImpl implements ImageService {

    private final AmazonS3Client amazonS3Client;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Override
    public String saveImageData(String snsId, String image) {
        String fileName = "images/" + snsId + "-" + UUID.randomUUID() + ".png";
        try {
            String data = image.split(",")[1];
            byte[] imageBytes = DatatypeConverter.parseBase64Binary(data);
            File file = new File("./images/temp.png");
            try {
                BufferedImage bufImg = ImageIO.read(new ByteArrayInputStream(imageBytes));
                ImageIO.write(bufImg, "png", file);
            } catch (IOException e) {
                log.info("이미지 파일 저장 경로 에러");
                throw new BadRequestException(ErrorCode.IMAGE_SAVE_ERROR);
            }
            amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, file).withCannedAcl(CannedAccessControlList.PublicRead));
            file.delete();
        } catch (ArrayIndexOutOfBoundsException e) {
            log.info("이미지 파일 데이터 형식 에러");
            throw new BadRequestException(ErrorCode.IMAGE_SAVE_ERROR);
        }
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }
}
