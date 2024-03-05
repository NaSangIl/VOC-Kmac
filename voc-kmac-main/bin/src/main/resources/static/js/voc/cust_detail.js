let $Grid        = null;

$(function () {
    // 초기 설정 및 수행
    init();
});

/**
 * 초기 설정 및 수행 내용
 */
let init = function(){
    let custSeq = localStorage.getItem("custSeq");

    // event 연결 ----------------
    $('.btn-wrap .btn-go-list').on('click', function(){ goList(); });               //목록
    $('.btn-wrap .btn-save-cust').on('click', function(){ saveData(); });           //저장
    $('.btn-wrap .btn-delt-cust').on('click', function(){ deleteData(); });         //삭제
    $('.btn-wrap .btn-search-voc').on('click', function(){ loadGrid(custSeq); });   //VOC이력 조회
    $('#registForm').find('#emailAddr3').on('change', function(){ setMailAddr(); }); //메일dropbox 변경시

    // dropbox data setting ----------------
    DropdownUtil.makeCodeList('EMAIL_ADDR', $('#registForm').find('.d-emailAddr'));
    DropdownUtil.makeCompList($('#registForm').find('.d-companyCd'));

    setTimeout(function() {
        if(custSeq > 0) searchData(custSeq);
        //localStorage.removeItem('custSeq');
    }, 200);

	loadGrid(custSeq);
}

/**
 * 그리드 옵션
 */
let GRID_OPTIONS = {
    columns     : [
        { data: 'sensSpecYn',   className: "text-center"   },
        { data: 'vocCaseNm',   className: "text-center"   },
        { data: 'vocTypeNm1',   className: "text-center"   },
        { data: 'rcptChnnNm',   className: "text-center"   },
        { data: 'custNm',   className: "text-center"   },
        { data: 'vocTitle',   className: "text-center"   },
        { data: 'regUserNm',   className: "text-center"   },
        { data: 'regDt',   className: "text-center"   },
        { data: 'vocStatusNm',   className: "text-center"   },
        { data: 'vocActTypeNm1',   className: "text-center"   },
        { data: 'vocActUserNm',   className: "text-center"   },
        { data: 'vocActDt',   className: "text-center"   },
    ],
    pageLength : 5,
    scrollX: '100%',
    scrollXInner: '1800px',
};

/**
 * Grid 구성
 */
let loadGrid = function(custSeq){
    if(custSeq == 0 || ObjectUtil.isEmpty(custSeq)) custSeq = -1;
    let gridOptions = $.extend(true, {}, GRID_OPTIONS);
    let url = '/kmacvoc/v1/voc/list';
    let sensSpecYn = $('#sensSpecYn').val() == 'Y' ? 'Y' : '';
    let param = {'custSeq':custSeq, 'sensSpecYn':sensSpecYn};
    $Grid = gridUtil.loadGrid("listDataTableCustVoc", gridOptions, url, param);

    $('#listDataTableCustVoc tbody').on('dblclick', 'tr', function (e) {
        let row = $("#listDataTableCustVoc").DataTable().row($(this)).data();
        let win = window.open("/voc/vocdetailpop?vocSeq="+row.vocSeq, "", "width=1600, height=950");
    });
};

/**
 * 목록화면 이동
 */
let goList = function(){
    goPage('/voc/custlist');
}

/**
 * 데이터 조회
 */
let searchData =  function(key){
    AjaxUtil.get(
        '/kmacvoc/v1/customer/'+key,
        {},
        function(result){
            if(result && result.data){
				$('.d-companyCd').dropdown('set selected', result?.data?.companyCd);
                let d = result.data;
                if(d.telNo != '') {
                    d.telNo1 = d.telNo.split('-')[0];
                    d.telNo2 = d.telNo.split('-')[1];
                    d.telNo3 = d.telNo.split('-')[2];
                }
                if(d.emailAddr != '') {
                    d.emailAddr1 = d.emailAddr.split('@')[0];
                    d.emailAddr2 = d.emailAddr.split('@')[1];
                }
                $('#registForm').form('clear');
                $('#registForm').form('set.values', d);
                $('.btn-delt-cust, .vocHistDiv').removeClass('blind');   //삭제버튼,VOC이력 표시

            }
        }
    );
}

/**
 * 데이터 저장
 */
let saveData = function(){
    let $frm = $('#registForm');
    if($frm.find('#companyCd').val() == '') {
        alert('회사코드는 필수항목입니다.');
        $frm.find('#companyCd').focus();
        return;
    }
    if($frm.find('#custNm').val() == '') {
        alert('고객명은 필수항목입니다.');
        $frm.find('#custNm').focus();
        return;
    }
    if($frm.find('#telNo1').val() == '' || $frm.find('#telNo2').val() == '' || $frm.find('#telNo3').val() == '') {
        alert('전화번호는 필수항목입니다.');
        $frm.find('#telNo1').focus();
        return;
    }

    $frm.find('#telNo').val($frm.find('#telNo1').val() + '-' + $frm.find('#telNo2').val() + '-' + $frm.find('#telNo3').val());
    $frm.find('#emailAddr').val($frm.find('#emailAddr1').val() + '@' + $frm.find('#emailAddr2').val());

    let formData = $('#registForm').serializeObject();
    let url = formData.custSeq == '0' ? '/kmacvoc/v1/customer/add' : '/kmacvoc/v1/customer/modify';

    AjaxUtil.post(
        url,
        JSON.stringify(formData),
        function(result){
            if(result && result.messageCode == '0000'){
                alert(result.data.rtnMessage);
                goList();
            }
        }
    );
}

/**
 * 데이터 삭제
 */
let deleteData = function(){

    if(!confirm('고객정보를 삭제하시겠습니까?')) return;

    let custSeq = $('#registForm').find('#custSeq').val();
    let url = '/kmacvoc/v1/customer/remove/'+custSeq;

    AjaxUtil.post(
        url,
        {},
        function(result){
            if(result && result.messageCode == '0000'){
                goList();
            }
        }
    );
}

/**
 * 메일주소 dropbox 선택시 셋팅
 */
let setMailAddr = function(){
    let v = $('#registForm').find('#emailAddr3').val();
    if(v == 'DIRECT_INPUT') {
        v = '';
    }
    $('#registForm').find('#emailAddr2').val(v);
}
