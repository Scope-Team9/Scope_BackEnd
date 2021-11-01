package com.studycollaboproject.scope.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TeamRequestDto {
    @Schema(description = "유저 ID")
    private Long userId;
    @Schema(description = "승인/거절")
    private boolean accept;
}
