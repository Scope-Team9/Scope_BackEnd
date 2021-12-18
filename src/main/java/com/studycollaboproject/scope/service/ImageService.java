package com.studycollaboproject.scope.service;

public interface ImageService {
    /*
    사용할 구현체의 @Service 애노테이션 활성화 및 사용하지 않는 구현체 @Service 애노테이션 비활성화
     */
    String saveImageData(String snsId, String image);
}
