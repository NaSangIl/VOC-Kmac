package kr.co.kmac.system.service;

import kr.co.kmac.common.util.MessageUtil;
import kr.co.kmac.coreframework.service.BaseService;
import kr.co.kmac.system.dto.MenuDto;
import kr.co.kmac.system.mapper.MenuMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 메뉴 서비스 클래스
 * 
 * @ClassName MenuService.java
 * @Description 메뉴 서비스 클래스
 * @menuor mjkim
 * @since 2023. 9. 18.
 */
@Service
public class MenuService extends BaseService
{
    // 메뉴 조작 위한 DAO 객체
    @Autowired
    private MenuMapper mapper;

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
     * 메뉴 목록 조회
     * 
     * @param param 메뉴 검색 조건 포함 객체
     * @return 결과 행 및 페이지 정보를 포함한 PaginatedResponse 객체
     */
    public MenuDto.ListResponse getMenuList(MenuDto.Request param)
    {
        long total = mapper.getMenuListCount(param);
        return MenuDto.ListResponse
                .<MenuDto.Info>builder()
                .totalCount(total)
                .list(mapper.getMenuList(param))
                .build();
	}

    /**
     * 메뉴 상세 조회
     * 
     * @param menuSeq 메뉴 pk
     * @return 메뉴 객체
     */
    public MenuDto.Info getMenu(int menuSeq)
    {
        return mapper.getMenu(menuSeq);
    }

    /**
     * 메뉴구성을 위한 목록 조회
     *
     * @param
     * @return 메뉴 객체
     */
    public List<MenuDto.MenuInfo> getHeaderMenuList(MenuDto.AuthRequest param)
    {
        List<MenuDto.MenuInfo> menuList = new ArrayList<>();
        List<MenuDto.Info> subMenulist = null;
        MenuDto.MenuInfo menuInfo = MenuDto.MenuInfo.builder().build();
        param.setMenuYn("Y");
        if(param.getUserAuthCodeList() == null) {
            return menuList;
        }

        List<MenuDto.Info> list = mapper.getHeaderMenuList(param);
        for(MenuDto.Info info1 : list) {
            if(info1.getMenuLevl() == 1){
                subMenulist = new ArrayList<>();
                menuInfo = MenuDto.MenuInfo.builder().build();
                BeanUtils.copyProperties(info1, menuInfo);
            }
            for(MenuDto.Info info2 : list) {
                if(StringUtils.equals(info1.getMenuId(), info2.getParentMenuId())) {
                    subMenulist.add(info2);
                }
            }
            menuInfo.setSubMenulist(subMenulist);
            if(info1.getMenuLevl() == 1) menuList.add(menuInfo);
        }

        return menuList;
    }

    /**
     * 권한메뉴목록 조회
     *
     * @param
     * @return 메뉴 객체
     */
    public List<MenuDto.Info> getAuthMenuList(MenuDto.AuthRequest param)
    {
        return mapper.getHeaderMenuList(param);
    }

    /**
     * 메뉴 등록(insert)
     * 
     * @param param 등록할 메뉴 객체
     * @return 적용된 행 수
     */
    @Transactional
    public MenuDto.Response insertMenu(MenuDto.Info param) {
        MenuDto.Response res = MenuDto.Response.builder().build();

        res.setRtnCnt(mapper.insertMenu(param));
        res.setRtnMessage(MessageUtil.getMessage("common.insert.0000"));
        return res;
    }

    /**
     * 메뉴 수정(update)
     *
     * @param param 저장할 메뉴 객체
     * @return 적용된 행 수
     */
    @Transactional
    public MenuDto.Response updateMenu(MenuDto.Info param) {
        MenuDto.Response res = MenuDto.Response.builder().build();

        res.setRtnCnt(mapper.updateMenu(param));
        res.setRtnMessage(MessageUtil.getMessage("common.update.0000"));
        return res;
    }

	
    /**
     * 메뉴 삭제
     * 
     * @param menuSeq 메뉴 pk
     * @return 적용된 행 수
     */
    @Transactional
    public MenuDto.Response deleteMenu(int menuSeq) {
        MenuDto.Response res = MenuDto.Response.builder().build();

        res.setRtnCnt(mapper.deleteMenu(menuSeq));
        res.setRtnMessage(MessageUtil.getMessage("common.delete.0000"));
        return res;
    }

}
