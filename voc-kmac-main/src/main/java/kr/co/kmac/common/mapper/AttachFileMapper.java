package kr.co.kmac.common.mapper;

import kr.co.kmac.common.dto.AttachFileDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 첨부파일 MyBatis Mapper 클래스
 * 
 * @ClassName AttachFileMapper.java
 * @Description 첨부파일 Mapper 클래스
 * @author mjkim
 * @since 2023. 9. 18.
 *
 */
@Repository
@Mapper
public interface AttachFileMapper
{
    /**
     * 첨부파일 목록 조회
     *
     * @param param 첨부파일 검색 조건 포함 객체
     */
    List<AttachFileDto.Info> getAttachFileList(AttachFileDto.Request param);

    /**
     * 첨부파일 상세 조회
     * 
     * @param fileSeq 첨부파일 pk
     * @return 첨부파일 객체
     */
    AttachFileDto.Info getAttachFile(int fileSeq);

    /**
     * 첨부파일 입력(insert)
     * 
     * @param param 저장 할 첨부파일 객체
     * @return 적용된 행 수
     */
    int insertAttachFile(AttachFileDto.Info param);


    /**
     * 첨부파일 삭제(delete)
     *
     * @param fileSeq 저장 할 첨부파일 객체
     * @return 적용된 행 수
     */
    int deleteAttachFile(int fileSeq);
}
