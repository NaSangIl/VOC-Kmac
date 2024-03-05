package kr.co.kmac.common.service;

import kr.co.kmac.common.dto.AttachFileDto;
import kr.co.kmac.common.mapper.AttachFileMapper;
import kr.co.kmac.common.util.FileUtil;
import kr.co.kmac.common.util.MessageUtil;
import kr.co.kmac.coreframework.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 첨부파일 서비스 클래스
 * 
 * @ClassName AttachFileService.java
 * @Description 첨부파일 서비스 클래스
 * @author mjkim
 * @since 2023. 9. 18.
 */
@Service
public class AttachFileService extends BaseService
{
    // 첨부파일 조작 위한 DAO 객체
    @Autowired
    private AttachFileMapper mapper;

    // 메시지 소스
    @Autowired
    private MessageSource messageSource;

    /**
     * 메시지 소스 반환
     * 
     * @return 메시지 소스 객체
     */
    protected MessageSource getMessageSource()
    {
        return this.messageSource;
    }

    /**
     * 첨부파일 목록 조회
     *
     * @param fileType 파일참조구분
     * @param fileRefKey 파일참조테이블키
     * @return 사용자 객체
     */
    public List<AttachFileDto.Info> getAttachFileList(String fileType, String fileRefKey)
    {
        AttachFileDto.Request param = AttachFileDto.Request.builder().build();
        param.setFileRefDiv(fileType);
        param.setFileRefKey(fileRefKey);
        return mapper.getAttachFileList(param);
    }

    /**
     * 첨부파일 상세 조회
     *
     * @param fileSeq 사용자 pk
     * @return 사용자 객체
     */
    public AttachFileDto.Info getAttachFile(int fileSeq)
    {
        return mapper.getAttachFile(fileSeq);
    }

    /**
     * 첨부파일 입력(insert)
     *
     * @param param 저장 할 첨부파일 객체
     * @return 적용된 행 수
     */
    @Transactional
    public int insertAttachFile(AttachFileDto.Info param) {
        return mapper.insertAttachFile(param);
    }

    /**
     * 첨부파일 삭제
     *
     * @param fileSeq 삭제 할 조건객체
     * @return 적용된 행 수
     */
    @Transactional
    public AttachFileDto.Response deleteAttachFile(int fileSeq) {
        AttachFileDto.Response res = AttachFileDto.Response.builder().build();

        //물리파일 삭제
        AttachFileDto.Info f = mapper.getAttachFile(fileSeq);
        FileUtil.deleteFile(f);

        res.setRtnCnt(mapper.deleteAttachFile(fileSeq));
        res.setRtnMessage(MessageUtil.getMessage("file.delete.0000"));

        return res;
    }

    /**
     * 동일 참조키에 포함된 파일을 일괄 삭제한다.
     * parameter
     * @param fileType, fileRefKey
     * @return
     * @throws Exception
     */
    public void deleteAttachFileByRef(String fileType, String fileRefKey) {

        AttachFileDto.Request param = AttachFileDto.Request.builder().build();
        param.setFileRefDiv(fileType);
        param.setFileRefKey(fileRefKey);
        List<AttachFileDto.Info> fileList = mapper.getAttachFileList(param);

        if(fileList != null) {
            for (AttachFileDto.Info f : fileList) {
                mapper.deleteAttachFile(f.getFileSeq());    // DB delete
                FileUtil.deleteFile(f);         		    // file delete
            }
        }
    }
}
