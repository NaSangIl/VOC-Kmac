let $Grid        = null;

$(function () {
    // 초기 설정 및 수행
    init();
});

/**
 * 초기 설정 및 수행 내용
 */
let init = function(){
    let $frm = $('#searchForm');

    // event 연결 ----------------
    $('.btn-search').on('click', function(){ loadGrid(); });

    // dropbox data setting ----------------
    DropdownUtil.makeCompList($frm.find('.d-companyCd'));   // 회사코드
    makeCodeVocType($('#searchForm'));  //VOC유형코드

    setTimeout(function() {
        $('#searchForm').find('#regDtStart').val(Util.getToday('-'));
        $('#searchForm').find('#regDtEnd').val(Util.getToday('-'));
        loadGrid();
    }, 200);
}

/**
 * 그리드 옵션
 */
let numberRender = $.fn.dataTable.render.number(',');   // 숫자 3자리마다 ',' 적용
let GRID_OPTIONS = {
    columns     : [
        { data: 'rcptChnnNm',       className: "text-center"   },
        { data: 'totalCnt',         className: "text-right"   ,render: numberRender},
        { data: 'totalYoyCnt',      className: "text-right"   ,render: numberRender},
        { data: 'complimentCnt',    className: "text-right"   ,render: numberRender},
        { data: 'complimentRate',   className: "text-right"   },
        { data: 'complimentYoyCnt', className: "text-right"   ,render: numberRender},
        { data: 'complaintCnt',     className: "text-right"   ,render: numberRender},
        { data: 'complaintRate',    className: "text-right"   },
        { data: 'complaintYoyCnt',  className: "text-right"   ,render: numberRender},
        { data: 'suggestionCnt',    className: "text-right"   ,render: numberRender},
        { data: 'suggestionRate',   className: "text-right"   },
        { data: 'suggestionYoyCnt', className: "text-right"   ,render: numberRender},
        { data: 'inquiryCnt',       className: "text-right"   ,render: numberRender},
        { data: 'inquiryRate',      className: "text-right"   },
        { data: 'inquiryYoyCnt',    className: "text-right"   ,render: numberRender},
    ],
    paging: false,
    dom: 'Bftrip',
    // footer에 합계와 평균 내용을 추가하기 위한 custom option 항목
    footerCalculation   : {
        targetSumColIndexs : [1,2,3,5,6,8,9,11,12,14],
        targetAvgColIndexs : [4,7,10,13],
        isNumberFormat  : true,
    },
    buttons             : [
        {
            extend: 'excel',
            text: '엑셀다운로드',
            filename: '채널별VOC현황-' + Util.getToday(),
            title: '채널별VOC현황 : '+ Util.getToday(),
        },
    ] ,
};

/**
 * Grid 구성
 */
let loadGrid = function(){
    let $frm = $('#searchForm');

    if(ObjectUtil.isEmpty($frm.find('#companyCd').val())) {
        alert('회사를 선택해 주세요.'); return;
    }
    if(ObjectUtil.isEmpty($frm.find('#regDtStart').val()) || ObjectUtil.isEmpty($frm.find('#regDtEnd').val())) {
        alert('등록일을 선택해 주세요.'); return;
    }

    let gridOptions = $.extend(true, {}, GRID_OPTIONS);
    let url = '/kmacvoc/v1/statistics/list/channel';
    let param = $('#searchForm').form('get.values');

    param.regDtStart = $frm.find('#regDtStart').val();
    param.regDtEnd = $frm.find('#regDtEnd').val();

    console.log('param',param);

    $Grid = gridUtil.loadGrid("listDataTableChannel", gridOptions, url, param);
};

