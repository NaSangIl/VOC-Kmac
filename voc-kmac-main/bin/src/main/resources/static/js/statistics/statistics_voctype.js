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
    $('.ui.checkbox').on('click', function(){ setVocType($(this).attr('id')); });

    // dropbox data setting ----------------
    DropdownUtil.makeCodeList('RCPT_CHNN_CD', $frm.find('.d-rcptChnnCd'));     //접수채널코드
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

let columns = [
    { data: 'vocTypeNm1',       className: "text-center"   },
    { data: 'vocTypeNm2',       className: "text-center"   },
    { data: 'vocTypeNm3',       className: "text-center"   },
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
];

let GRID_OPTIONS = {
    columns     : columns,
    paging: false,
    dom: 'Bftrip',
    // footer에 합계와 평균 내용을 추가하기 위한 custom option 항목
    footerCalculation   : {
        targetSumColIndexs : [3,4,5,7,8,10,11,13,14,16],
        targetAvgColIndexs : [6,9,12,15],
        isNumberFormat  : true,
    },
    buttons: [
        {
            extend: 'excel',
            text: '엑셀다운로드',
            filename: '유형별VOC현황-' + Util.getToday(),
            title: '유형별VOC현황 : '+ Util.getToday(),
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

    // 그리드 컬럼 변경
    /*
    $('#vocTypeTr').html('');
    for(let i=0; i<15; i++) {
        $('#vocTypeTr').append('<td></td>');
    }*/

    /*
    GRID_OPTIONS.columns = $.extend(columns1, columns);
        console.log(GRID_OPTIONS);

    $('.vocTypeDiv').attr('colspan', 1);

    if($('.chk-vocTypeCd2').hasClass('checked')) {
        GRID_OPTIONS.columns = $.merge(columns2, columns);
        $('.vocTypeDiv').attr('colspan', 2);
        $('#vocTypeTr').append('<td></td>');
    }
    if($('.chk-vocTypeCd3').hasClass('checked')) {
        GRID_OPTIONS.columns = $.merge(columns3, columns);
        $('.vocTypeDiv').attr('colspan', 3);
        let len = $('#vocTypeTr').children('td').length;
        for(let i=0; i<18-len; i++) {
            $('#vocTypeTr').append('<td></td>');
        }
    }
*/
    let gridOptions = $.extend(true, {}, GRID_OPTIONS);
    let url = '/kmacvoc/v1/statistics/list/voctype';
    let param = $('#searchForm').form('get.values');

    param.regDtStart = $frm.find('#regDtStart').val();
    param.regDtEnd = $frm.find('#regDtEnd').val();
    param.vocTypeCd2Yn = param.vocTypeCd2Yn == false ? '' : 'Y';
    param.vocTypeCd3Yn = param.vocTypeCd3Yn == false ? '' : 'Y';

    $Grid = gridUtil.loadGrid("listDataTableVoctype", gridOptions, url, param);
};

/**
 * VOC 유형 컨트롤
 */
let setVocType = function(id){
    if($('.chk-vocTypeCd'+id).hasClass('checked')) {
        $('.d-vocTypeCd'+id).removeClass('disabled');
    } else {
        $('.d-vocTypeCd'+id).addClass('disabled');
        $('.d-vocTypeCd'+id).dropdown('clear');
    }
}
