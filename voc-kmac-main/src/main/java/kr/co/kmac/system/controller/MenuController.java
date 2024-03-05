package kr.co.kmac.system.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.kmac.common.util.ResponseUtil;
import kr.co.kmac.common.util.validation.PostMethod;
import kr.co.kmac.coreframework.common.response.ResponseObject;
import kr.co.kmac.coreframework.controller.BaseController;
import kr.co.kmac.system.dto.MenuDto;
import kr.co.kmac.system.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 메뉴 관리/작업 Controller
 * 
 * @ClassName MenuController.java
 * @Description 메뉴
 * @menuor mjkim
 * @since 2023. 9. 18.
 */
@Tag(name = "MenuController", description = "메뉴관리 API")
@RestController
@RequestMapping("/kmacvoc/v1/menu")
public class MenuController extends BaseController
{
    @Autowired
    private MenuService service;

    /**
     * 메뉴 목록 조회
     * 
     * @param param 메뉴 검색 조건 포함 객체
     * @return 결과 행 및 페이지 정보를 포함한 PaginatedResponse 객체
     */
    @Operation(summary = "GET 메뉴 목록 조회", description = "메뉴 목록 조회")
    @GetMapping("/list")
    public ResponseObject<MenuDto.ListResponse> getMenuList(HttpServletRequest req, MenuDto.Request param)
    {
        return ResponseUtil.getResponse(req, service.getMenuList(param));
    }
	
    /**
     * 메뉴 상세 조회
     * 
     * @param menuSeq 메뉴 검색 조건 포함 객체
     * @return 메뉴 객체
     */
    @Operation(summary = "GET 메뉴 상세 조회", description = "menuSeq로 메뉴 정보 1건 조회")
    @GetMapping("/{menuSeq}")
    public ResponseObject<MenuDto.Info> getMenu(HttpServletRequest req
            , @Parameter(description = "메뉴일련번호") @PathVariable(name = "menuSeq") int menuSeq)
    {
        return ResponseUtil.getResponse(req, service.getMenu(menuSeq));
    }

    /**
     * 메뉴 등록
     *ss
     * @param param 등록할 메뉴 객체
     * @return 등록결과 객체
     */
    @Operation(summary = "메뉴 등록", description = "메뉴 정보를 등록한다.")
    @PostMapping(value="/add")
    public ResponseObject insertMenu(HttpServletRequest req, @RequestBody @Validated(PostMethod.class) MenuDto.Info param)
    {
        return ResponseUtil.getResponse(req, service.insertMenu(param));
    }

    /**
     * 메뉴 수정
     *
     * @param param 수정할 메뉴 객체
     * @return 수정결과 객체
     */
    @Operation(summary = "메뉴 수정", description = "메뉴 정보를 수정한다.")
    @PostMapping("/modify")
    public ResponseObject updateMenu(HttpServletRequest req, @RequestBody @Validated(PostMethod.class) MenuDto.Info param)
    {
        return ResponseUtil.getResponse(req, service.updateMenu(param));
    }

    /**end
     * 메뉴 삭제
     * 
     * @param menuSeq 삭제할 메뉴 key
     */
    @Operation(summary = "VOC 삭제", description = "VOC정보를 삭제한다.")
    @PostMapping("/remove/{menuSeq}")
    public ResponseObject deleteMenu(HttpServletRequest req
            , @Parameter(description = "메뉴일련번호") @PathVariable(name = "menuSeq") int menuSeq)
    {
        return ResponseUtil.getResponse(req, service.deleteMenu(menuSeq));
    }
}
