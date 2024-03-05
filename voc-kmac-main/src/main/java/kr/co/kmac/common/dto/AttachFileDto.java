package kr.co.kmac.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.co.kmac.common.util.validation.PostMethod;
import kr.co.kmac.coreframework.dto.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Size;
import java.util.List;

public class AttachFileDto {
    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode(callSuper= false)
    @Schema(description = "첨부파일 조회 request", name="AttachFileSearchRequest")
    public static class Request extends BaseDto.Request {
        @Schema(description = "파일일련번호")
        private int fileSeq;

        @Schema(description = "파일참조테이블키(VOC 아이디)")
        private String fileRefKey;

        @Schema(description = "파일참조구분(VOC 상태코드)")
        private String fileRefDiv;
    }

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode(callSuper= false)
    @Schema(description = "첨부파일 정보객제", name="AttachFileInfo")
    public static class Info {

        @Schema(description = "파일일련번호")
        private int fileSeq;

        @Schema(description = "파일참조테이블키(VOC 아이디)")
        @Size(max=100, message="파일참조테이블키(VOC 아이디): 100자를 넘을 수 없습니다.", groups= PostMethod.class)
        private String fileRefKey;

        @Schema(description = "파일참조구분(VOC 상태코드)")
        @Size(max=20, message="파일참조구분(VOC 상태코드): 20자를 넘을 수 없습니다.", groups=PostMethod.class)
        private String fileRefDiv;

        @Schema(description = "파일명")
        @Size(max=300, message="파일명: 300자를 넘을 수 없습니다.", groups=PostMethod.class)
        private String fileNm;

        @Schema(description = "파일경로")
        @Size(max=500, message="파일경로: 500자를 넘을 수 없습니다.", groups=PostMethod.class)
        private String filePath;

        @Schema(description = "파일확장자")
        @Size(max=10, message="파일확장자: 10자를 넘을 수 없습니다.", groups=PostMethod.class)
        private String fileExtn;

        @Schema(description = "파일사이즈")
        private int fileSize;

        @Schema(description = "등록자")
        @Size(max=20, message="등록자: 20자를 넘을 수 없습니다.", groups=PostMethod.class)
        private String regUserNo;

        @Schema(description = "등록일시")
        private String regDt;

    }

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper= false)
    @Schema(description = "첨부파일 처리 결과", name="AttachFileResponse")
    public static class Response extends BaseDto.Result {

    }

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper= false)
    @Schema(description = "첨부파일 조회목록 결과", name="AttachFileListResponse")
    public static class ListResponse extends BaseDto.ListResult {
        List<Info> list;
    }
}
