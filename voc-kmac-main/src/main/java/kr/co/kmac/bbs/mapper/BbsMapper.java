package kr.co.kmac.bbs.mapper;

import kr.co.kmac.bbs.dto.BbsDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 게시판 MyBatis Mapper 클래스
 * 
 * @ClassName BbsMapper.java
 * @Description 게시판 Mapper 클래스
 * @author mjkim
 * @since 2023. 11. 13.
 *
 */
@Repository
@Mapper
public interface BbsMapper
{
    /**
    /**
     * 게시판 목록 조회
     * 
     * @param param 게시판 검색 조건 포함 객체
     */
    List<BbsDto.Info> getBbsList(BbsDto.Request param);

    /**
     * 게시판 목록 개수 조회
     * 
     * @param param 게시판 검색 조건 포함 객체
     * @return 조건에 해당되는 전체 행 수
     */
    long getBbsListCount(BbsDto.Request param);

    /**
     * 게시판 상세 조회
     *
     * @param bbsSeq 게시판 pk
     * @return 게시판 객체
     */
    BbsDto.Info getBbs(int bbsSeq);

    /**
     * 게시판 입력(insert)
     * 
     * @param param 저장 할 게시판 객체
     * @return 적용된 행 수
     */
    int insertBbs(BbsDto.Info param);

    /**
     * 게시판 수정(update)
     *
     * @param param 저장 할 게시판 객체
     * @return 적용된 행 수
     */
    int updateBbs(BbsDto.Info param);

    /**
     * 조회수 수정(update)
     *
     * @param bbsSeq 저장 할 게시판 key
     * @return 적용된 행 수
     */
    int updateHit(int bbsSeq);

    /**
     * 게시판 삭제
     * 
     * @param bbsSeq 게시판 pk
     * @return 적용된 행 수
     */
    int deleteBbs(int bbsSeq);
}
