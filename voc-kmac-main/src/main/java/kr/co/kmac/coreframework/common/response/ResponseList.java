package kr.co.kmac.coreframework.common.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.co.kmac.common.constants.CommonConstants;
import kr.co.kmac.common.util.MessageUtil;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author mjkim
 * @class ResponseList
 * @description
 * @since 2022/03/03
 */
@Data
@Builder(toBuilder = true)
@EqualsAndHashCode(callSuper = false)
public class ResponseList<List> {
    @Schema(description = "API 서비스 구분")
    @Builder.Default
    private final String serviceArea = CommonConstants.SERVICE_AREA;
    @Schema(description = "API URL 주소")
    private String api;
    @Schema(description = "오류 그룹 코드")
    @Builder.Default
    private String errorCode = CommonConstants.DEFAULT_SUCCESS;
    @Schema(description = "오류 그룹 메시지")
    @Builder.Default
    private String errorMessage = MessageUtil.getMessage(CommonConstants.DEFAULT_KEY + CommonConstants.DEFAULT_SUCCESS);
    @Schema(description = "메시지 코드")
    @Builder.Default
    private String messageCode = CommonConstants.DEFAULT_SUCCESS;
    @Schema(description = "메시지")
    @Builder.Default
    private String message = MessageUtil.getMessage(CommonConstants.DEFAULT_KEY + CommonConstants.DEFAULT_SUCCESS);
    @Schema(description = "조회된 데이터 수")
    @Builder.Default
    private int totalCount = 0;
    @Schema(description = "응답 데이터 목록")
    private List list;

    @Schema(description = "현재페이지번호")
    int pageNumber;
    @Schema(description = "페이지당 출력할 데이터수")
    int pageSize;
}
