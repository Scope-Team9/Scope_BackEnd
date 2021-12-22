package com.studycollaboproject.scope.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SignupRequestDto {

    @NotNull
    @Schema(description = "SNS ID")
    private String snsId;

    @NotNull
    @Length(min = 2, max =5 )
    @Schema(description = "닉네임")
    private String nickname;

    @NotNull
    @Schema(description = "기술스택 리스트")
    private List<String> techStack;

    @NotNull
    @Schema(description = "유저 성향 테스트 결과")
    List<String> userPropensityType;

    @NotNull
    @Schema(description = "유저 선호 성향 테스트 결과")
    List<String> memberPropensityType;
}
