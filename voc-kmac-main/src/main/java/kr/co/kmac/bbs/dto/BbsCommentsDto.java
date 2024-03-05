package kr.co.kmac.bbs.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.co.kmac.coreframework.dto.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

public class BbsCommentsDto {
    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode(callSuper= false)
    @Schema(description = "게시판댓글 조회 request", name="BbsCommentsSearchRequest")
    public static class Request extends BaseDto.Request {
        @Schema(description = "게시판순번")
        private int bbsSeq;

    }

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode(callSuper= false)
    @Schema(description = "게시판댓글 정보객제", name="BbsCommentsInfo")
    public static class Info extends BaseDto.Base {
        @Schema(description = "게시판순번")
        private int bbsSeq;

        @Schema(description = "게시판댓글순번")
        private int bbsCommentsSeq;

        @Schema(description = "댓글")
        private String comments;
    }

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper= false)
    @Schema(description = "게시판댓글 처리 결과", name="BbsCommentsResponse")
    public static class Response extends BaseDto.Result {

    }

    @Data
    @SuperBuilder
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper= false)
    @Schema(description = "게시판댓글 조회목록 결과", name="BbsCommentsListResponse")
    public static class ListResponse extends BaseDto.ListResult {
        List<Info> list;
    }
}
