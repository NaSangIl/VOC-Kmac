package kr.co.kmac.system.mapper;

import kr.co.kmac.system.dto.MenuDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 메뉴 MyBatis Mapper 클래스
 * 
 * @ClassName MenuMapper.java
 * @Description 메뉴 Mapper 클래스
 * @menuor mjkim
 * @since 2023. 9. 18.
 *
 */
@Repository
@Mapper
public interface MenuMapper
{
    /**
     * 메뉴 목록 조회
     * 
     * @param param 메뉴 검색 조건 포함 객체
     */
    List<MenuDto.Info> getMenuList(MenuDto.Request param);

    /**
     * 메뉴 목록 개수 조회
     * 
     * @param param 메뉴 검색 조건 포함 객체
     * @return 조건에 해당되는 전체 행 수
     */
    long getMenuListCount(MenuDto.Request param);

    /**
     * 메뉴구성을 위한 목록 조회
     *
     * @param
     */
    List<MenuDto.Info> getHeaderMenuList(MenuDto.AuthRequest param);

    /**
     * 메뉴 상세 조회
     *
     * @param menuSeq 메뉴 pk
     * @return 메뉴 객체
     */
    MenuDto.Info getMenu(int menuSeq);

    /**
     * 메뉴 입력(insert)
     * 
     * @param param 저장 할 메뉴 객체
     * @return 적용된 행 수
     */
    int insertMenu(MenuDto.Info param);

    /**
     * 메뉴 수정(update)
     *
     * @param param 저장 할 메뉴 객체
     * @return 적용된 행 수
     */
    int updateMenu(MenuDto.Info param);

    /**
     * 메뉴 삭제
     * 
     * @param menuSeq 메뉴 pk
     * @return 적용된 행 수
     */
    int deleteMenu(int menuSeq);
}
