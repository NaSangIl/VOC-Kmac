package kr.co.kmac.bbs.mapper;

import kr.co.kmac.bbs.dto.BbsCommentsDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 게시판댓글 MyBatis Mapper 클래스
 * 
 * @ClassName BbsCommentsMapper.java
 * @Description 게시판댓글 Mapper 클래스
 * @author mjkim
 * @since 2023. 11. 13.
 *
 */
@Repository
@Mapper
public interface BbsCommentsMapper
{
    /**
    /**
     * 게시판댓글 목록 조회
     * 
     * @param param 게시판댓글 검색 조건 포함 객체
     */
    List<BbsCommentsDto.Info> getBbsCommentsList(BbsCommentsDto.Request param);

    /**
     * 게시판댓글 목록 개수 조회
     * 
     * @param param 게시판댓글 검색 조건 포함 객체
     * @return 조건에 해당되는 전체 행 수
     */
    long getBbsCommentsListCount(BbsCommentsDto.Request param);

    /**
     * 게시판댓글 상세 조회
     *
     * @param bbsCommentsSeq 게시판댓글 pk
     * @return 게시판댓글 객체
     */
    BbsCommentsDto.Info getBbsComments(int bbsCommentsSeq);

    /**
     * 게시판댓글 입력(insert)
     * 
     * @param param 저장 할 게시판댓글 객체
     * @return 적용된 행 수
     */
    int insertBbsComments(BbsCommentsDto.Info param);

    /**
     * 게시판댓글 수정(update)
     *
     * @param param 저장 할 게시판댓글 객체
     * @return 적용된 행 수
     */
    int updateBbsComments(BbsCommentsDto.Info param);

    /**
     * 게시판댓글 삭제
     * 
     * @param bbsCommentsSeq 게시판댓글 pk
     * @return 적용된 행 수
     */
    int deleteBbsComments(int bbsCommentsSeq);
}
