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
    $('.ui.checkbox').on('click', function(){ setVocActType($(this).attr('id')); });

    // dropbox data setting ----------------
    DropdownUtil.makeCodeList('RCPT_CHNN_CD', $frm.find('.d-rcptChnnCd'));     //접수채널코드
    DropdownUtil.makeCompList($frm.find('.d-companyCd'));   // 회사코드
    makeCodeVocType($('#searchForm'));  //VOC유형코드
    makeCodeVocActType($('#searchForm'));   //처리유형코드

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
        { data: 'vocActTypeNm1',    className: "text-center"   },
        { data: 'vocActTypeNm2',    className: "text-center"   },
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
        targetSumColIndexs : [2,3,4,6,7,9,10,12,13,15],
        targetAvgColIndexs : [5,8,11,14],
        isNumberFormat  : true,
    },
    buttons: [
        {
            extend: 'excel',
            text: '엑셀다운로드',
            filename: '처리유형별VOC현황-' + Util.getToday(),
            title: '처리유형별VOC현황 : '+ Util.getToday(),
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
    let url = '/kmacvoc/v1/statistics/list/acttype';
    let param = $('#searchForm').form('get.values');

    param.regDtStart = $frm.find('#regDtStart').val();
    param.regDtEnd = $frm.find('#regDtEnd').val();
    param.vocActTypeCd2Yn = param.vocActTypeCd2Yn == false ? '' : 'Y';

    $Grid = gridUtil.loadGrid("listDataTableActtype", gridOptions, url, param);
};

/**
 * VOC 처리유형 컨트롤
 */
let setVocActType = function(id){
    if($('.chk-vocActTypeCd'+id).hasClass('checked')) {
        $('.d-vocActTypeCd'+id).removeClass('disabled');
    } else {
        $('.d-vocActTypeCd'+id).addClass('disabled');
        $('.d-vocActTypeCd'+id).dropdown('clear');
    }
}
