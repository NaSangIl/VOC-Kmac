let $Grid        = null;

$(function () {
    // 초기 설정 및 수행
    init();
});

/**
 * 초기 설정 및 수행 내용
 */
let init = function(){
    // 초기 화면 구성 ----------------

    // 현재날짜 셋팅
    /*$('#useStartDtFr').val(Util.getToday('-'));
    $('#useStartDtTo').val(Util.getToday('-'));
    $('#useEndDtFr').val(Util.getToday('-'));
    $('#useEndDtTo').val(Util.getToday('-'));*/

    // event 연결 ----------------
    $('.btn-search').on('click', function(){ loadGrid(); });            // 조회
    $('.btn-wrap .btn-add').on('click', function(){ goAddCompany(); });     // 회사등록

    //더블클릭시 상세화면 이동
    $('#listDataTableCompany tbody').on('dblclick', 'tr', function (e) {
        goDtlCompany();
    });

    // dropbox data setting ----------------
    DropdownUtil.makeCodeList('USE_YN', $('#searchForm').find('.d-useYn'));
    loadGrid();

}

/**
 * 그리드 옵션
 */
let GRID_OPTIONS = {
    columns     : [
        { data: 'companyCd',   className: "text-center"   },
        { data: 'companyNm',   className: "text-center"   },
        { data: 'companyNmEn',   className: "text-center"   },
        { data: 'useYn',   className: "text-center"   },
        { data: 'useStartDt',   className: "text-center",
            'render': function (data, type, full, meta) {
                return Util.dateFormat(data, '-');
            }
        },
        { data: 'useEndDt',   className: "text-center" ,
            'render': function (data, type, full, meta) {
                return Util.dateFormat(data, '-');
            }
        },
    ],
};

/**
 * Grid 구성
 */
let loadGrid = function(){
    let gridOptions = $.extend(true, {}, GRID_OPTIONS);
    let url = '/kmacvoc/v1/company/list';
    let param = $('#searchForm').form('get.values');

    //날짜포맷 변경되어 별도 셋팅
    param.useStartDtFr = $('#searchForm').find('#useStartDtFr').val();
    param.useStartDtTo = $('#searchForm').find('#useStartDtTo').val();
    param.useEndDtFr = $('#searchForm').find('#useEndDtFr').val();
    param.useEndDtTo = $('#searchForm').find('#useEndDtTo').val();

    $Grid = gridUtil.loadGrid("listDataTableCompany", gridOptions, url, param);
};

/**
 * 등록/수정을 위한 회사 등록화면 이동
 */
let goAddCompany = function(){
	localStorage.removeItem('companySeq');
    goPage('/system/companydetail');
}

/**
 * 회사 상세화면 이동
 */
let goDtlCompany =  function(){
    localStorage.setItem("companySeq", $selectedRowData.companySeq);
    goPage('/system/companydetail');
}

