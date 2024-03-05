package kr.co.kmac.bbs.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.co.kmac.common.dto.AttachFileDto;
import kr.co.kmac.common.util.validation.PostMethod;
import kr.co.kmac.coreframework.dto.BaseDto;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

public class BbsDto {
    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode(callSuper= false)
    @Schema(description = "게시판 조회 request", name="BbsSearchRequest")
    public static class Request extends BaseDto.Request {
        @Schema(description = "회사코드")
        private String companyCd;
        @Schema(description = "게시판유형코드")
        private String bbsTypeCd;

        @Schema(description = "제목")
        private String title;

        @Schema(description = "등록시작일:YYYY-MM-DD")
        private String regDtStart;

        @Schema(description = "등록종료일:YYYY-MM-DD")
        private String regDtEnd;

    }

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode(callSuper= false)
    @Schema(description = "게시판 정보객제", name="BbsInfo")
    public static class Info extends BaseDto.Base {
        @Schema(description = "게시판순번")
        private int bbsSeq;

        @Schema(description = "게시판유형코드")
        @NotBlank(message="게시판유형코드: 필수 항목입니다.", groups=PostMethod.class)
        @Size(max=20, message="게시판유형코드: 20자를 넘을 수 없습니다.", groups=PostMethod.class)
        private String bbsTypeCd;

        @Schema(description = "회사코드")
        @NotBlank(message="회사코드: 필수 항목입니다.", groups=PostMethod.class)
        @Size(max=10, message="회사코드: 10자를 넘을 수 없습니다.", groups=PostMethod.class)
        private String companyCd;

        @Schema(description = "회사명")
        private String companyNm;

        @Schema(description = "제목")
        @Size(max=1000, message="제목: 1000자를 넘을 수 없습니다.", groups=PostMethod.class)
        private String title;

        @Schema(description = "내용")
        private String contents;

        @Schema(description = "공지게시여부")
        private String notiPostYn;

        @Schema(description = "조회수")
        private Integer hit;

        @Schema(description = "사용여부")
        private String useYn;

        @Schema(description = "첨부파일목록")
        List<AttachFileDto.Info> fileList;

        @Schema(description = "댓글수")
        private Integer commentsCnt;

        List<MultipartFile> files;
    }

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper= false)
    @Schema(description = "게시판 처리 결과", name="BbsResponse")
    public static class Response extends BaseDto.Result {

    }

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper= false)
    @Schema(description = "게시판 조회목록 결과", name="BbsListResponse")
    public static class ListResponse extends BaseDto.ListResult {
        List<Info> list;
    }
}
