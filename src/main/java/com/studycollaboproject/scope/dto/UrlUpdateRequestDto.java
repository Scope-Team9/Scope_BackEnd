package com.studycollaboproject.scope.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UrlUpdateRequestDto {
    private String frontUrl;
    private String backUrl;
}
