let $Grid        = null;
let customType  = "";   //[VOC_TYPE:VOC유형,VOC_ACT_TYPE:VOC처리유형}

$(function () {
    // 초기 설정 및 수행
    init();
});

/**
 * 초기 설정 및 수행 내용
 */
let init = function(){
    customType = $('#searchForm').find('#customType').val();

    // event 연결 ----------------
    $('.btn-search').on('click', function(e){ e.preventDefault(); loadGrid(); });                // 조회
    $('.btn-wrap .btn-add').on('click', function(){ goAddCustomCode(); });  // 등록
    $('.btn-wrap .btn-dtl').on('click', function(){ goDtlCustomCode(); });  // 상세

    //더블클릭시 상세화면 이동
    $('#listDataTableCustom tbody').on('dblclick', 'tr', function (e) {
        goDtlCustomCode();
    });

    // dropbox data setting ----------------
    DropdownUtil.makeCodeList('INDUSTRY_CD', $('#searchForm').find('.d-industryCd'));
    DropdownUtil.makeCodeList('USE_YN', $('#searchForm').find('.d-useYn'));

    loadGrid();

}

/**
 * 그리드 옵션
 */
let GRID_OPTIONS = {
    columns     : [
        { data: 'industryNm',   className: "text-center"   },
        { data: 'customGrpCd',   className: "text-center"   },
        { data: 'customGrpNm',   className: "text-center"   },
        { data: 'useYn',   className: "text-center"   },
    ],
};

/**
 * Grid 구성
 */
let loadGrid = function(){
    let gridOptions = $.extend(true, {}, GRID_OPTIONS);
    let url = '/kmacvoc/v1/custom/mst/list';
    let param = $('#searchForm').form('get.values');
    //if(param.useYn == '') param.useYn = 'Y';

    $Grid = gridUtil.loadGrid("listDataTableCustom", gridOptions, url, param);
};

/**
 * 등록화면 이동
 */
let goAddCustomCode = function(){
	localStorage.removeItem('customMstSeq');
    goPage("/system/vocacttypedetail");
}

/**
 * 상세화면 이동
 */
let goDtlCustomCode =  function(){
    localStorage.setItem("customMstSeq", $selectedRowData.customMstSeq);
    goPage("/system/vocacttypedetail");
}

