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

    if($SessionInfo.getUserAuth().indexOf('100') < 0 && $SessionInfo.getUserAuth().indexOf('000') < 0) {
		$frm.find('#companyArea').hide();	
	}


    setTimeout(function() {
		
		let date = new Date();
        date.setMonth(date.getMonth() -12);
        
        var delimiter = "-";
        let bfday = date.getFullYear() + delimiter + ("0" + (date.getMonth()+1)).slice(-2) + delimiter + ("0" + date.getDate()).slice(-2);
        
        $('#searchForm').find('#regDtStart').val(bfday);
        $('#searchForm').find('#regDtEnd').val(Util.getToday('-'));
        loadGrid();
    }, 200);
}

/**
 * 그리드 옵션
 */
let numberRender = $.fn.dataTable.render.number(',');   // 숫자 3자리마다 ',' 적용
let columns = [
        { data: 'rcptChnnNm',       className: "text-center"   },
	    { data: 'totalCnt',         className: "text-right"   ,render: numberRender},
	    { data: 'totalYoyCnt',      className: "text-right"   ,render:function (data, type, row, meta) {return (data).toFixed(1) + '%'}},
	    { data: 'complimentCnt',    className: "text-right"   ,render: numberRender},                               
	    { data: 'complimentRate',   className: "text-right"   ,render:function (data, type, row, meta) {return (data).toFixed(1) + '%'}},
	    { data: 'complimentYoyCnt', className: "text-right"   ,render:function (data, type, row, meta) {return (data).toFixed(1) + '%'}},
	    { data: 'complaintCnt',     className: "text-right"   ,render: numberRender},                               
	    { data: 'complaintRate',    className: "text-right"   ,render:function (data, type, row, meta) {return (data).toFixed(1) + '%'}},
	    { data: 'complaintYoyCnt',  className: "text-right"   ,render:function (data, type, row, meta) {return (data).toFixed(1) + '%'}},
	    { data: 'suggestionCnt',    className: "text-right"   ,render: numberRender},                               
	    { data: 'suggestionRate',   className: "text-right"   ,render:function (data, type, row, meta) {return (data).toFixed(1) + '%'}},
	    { data: 'suggestionYoyCnt', className: "text-right"   ,render:function (data, type, row, meta) {return (data).toFixed(1) + '%'}},
	    { data: 'inquiryCnt',       className: "text-right"   ,render: numberRender},                               
	    { data: 'inquiryRate',      className: "text-right"   ,render:function (data, type, row, meta) {return (data).toFixed(1) + '%'}},
	    { data: 'inquiryYoyCnt',    className: "text-right"   ,render:function (data, type, row, meta) {return (data).toFixed(1) + '%'}},
	];
	
let GRID_OPTIONS = {
    columns     : columns,
    paging: false,
    dom: 'Bftrip',
    createdRow: function (row, data, dataIndex, full) {
		$(row).attr('rcptChnnCd', data.rcptChnnCd);
		
        $(row).children('td:nth-child(2)').attr('cellId', 'totalCnt');
        $(row).children('td:nth-child(2)').css('text-decoration', 'underline');
        $(row).children('td:nth-child(2)').css('color', 'blue');
        //$(row).children('td:nth-child(3)').attr('cellId', 'totalYoyCnt');
        
        $(row).children('td:nth-child(4)').attr('cellId', 'complimentCnt');
        $(row).children('td:nth-child(4)').css('text-decoration', 'underline');
        $(row).children('td:nth-child(4)').css('color', 'blue');
        //$(row).children('td:nth-child(5)').attr('cellId', 'complimentRate');
        //$(row).children('td:nth-child(6)').attr('cellId', 'complimentYoyCnt');
        
        $(row).children('td:nth-child(7)').attr('cellId', 'complaintCnt');
        $(row).children('td:nth-child(7)').css('text-decoration', 'underline');
        $(row).children('td:nth-child(7)').css('color', 'blue');
        //$(row).children('td:nth-child(8)').attr('cellId', 'complaintRate');
        //$(row).children('td:nth-child(9)').attr('cellId', 'complaintYoyCnt');
        
        $(row).children('td:nth-child(10)').attr('cellId', 'suggestionCnt');
        $(row).children('td:nth-child(10)').css('text-decoration', 'underline');
        $(row).children('td:nth-child(10)').css('color', 'blue');        
        //$(row).children('td:nth-child(11)').attr('cellId', 'suggestionRate');
        //$(row).children('td:nth-child(12)').attr('cellId', 'suggestionYoyCnt');
        
        $(row).children('td:nth-child(13)').attr('cellId', 'inquiryCnt');
        $(row).children('td:nth-child(13)').css('text-decoration', 'underline');
        $(row).children('td:nth-child(13)').css('color', 'blue');
        //$(row).children('td:nth-child(14)').attr('cellId', 'inquiryRate');
        //$(row).children('td:nth-child(15)').attr('cellId', 'inquiryYoyCnt');
 	},
	footerCallback: function (row, data, start, end, display ) {
		var api = this.api(), data;
        var totalCnt = 0;
        var totalYoyCnt = 0;
        
        var complimentCnt = 0;
        var complimentYoyCnt = 0;
        var complimentRate = 0;
        var complaintCnt = 0;
        var complaintYoyCnt = 0;
        var complaintRate = 0;
        var suggestionCnt = 0;
        var suggestionYoyCnt = 0;
        var suggestionRate = 0;
        var inquiryCnt = 0;
        var inquiryYoyCnt = 0;
        var inquiryRate = 0;
        
        //전체 접수 합계
        api.column(1, {search:'applied'}).data().each(function(data,index){
            totalCnt += parseFloat(data);
        });
        $(api.column(1).footer()).html(totalCnt.toLocaleString());
        
        if(data.length == 0){
			totalYoyCnt = 0
		}else{
			//전체 전년대비 증감율 평균
	        api.column(2, {search:'applied'}).data().each(function(data,index){
	            totalYoyCnt += parseFloat(data);
	        });
	        totalYoyCnt = (totalYoyCnt / data.length).toFixed(1) + '%';
		}    
        $(api.column(2).footer()).html(totalYoyCnt.toLocaleString());
        
        
        //------------------- 칭찬 -------------------
        //칭찬 접수 합계
        api.column(3, {search:'applied'}).data().each(function(data,index){
            complimentCnt += parseFloat(data);
        });
        $(api.column(3).footer()).html(complimentCnt.toLocaleString());

        //칭찬 비율 평균
        if(data.length == 0){
			complimentRate = 0
		}else{
			//칭찬 비율 평균
	        api.column(4, {search:'applied'}).data().each(function(data,index){
	            complimentRate += parseFloat(data);
	        });
	        complimentRate = (complimentRate / data.length).toFixed(1) + '%';
		}        
        $(api.column(4).footer()).html(complimentRate.toLocaleString());
        

        //칭찬 전년대비 증감율 평균
        if(data.length == 0){
			complimentYoyCnt = 0
		}else{
	        //칭찬 전년대비 증감율 평균
	        api.column(5, {search:'applied'}).data().each(function(data,index){
	            complimentYoyCnt += parseFloat(data);
	        });
			
	        complimentYoyCnt = (complimentYoyCnt / data.length).toFixed(1) + '%';
		}        
        $(api.column(5).footer()).html(complimentYoyCnt.toLocaleString());
        
        
        //------------------- 불만 -------------------
        //불만 접수 합계
        api.column(6, {search:'applied'}).data().each(function(data,index){
            complaintCnt += parseFloat(data);
        });
        $(api.column(6).footer()).html(complaintCnt.toLocaleString());
                

        //불만 비율 평균
        if(data.length == 0){
			complaintRate = 0
		}else{
	        //불만 비율 평균
	        api.column(7, {search:'applied'}).data().each(function(data,index){
	            complaintRate += parseFloat(data);
	        });			
	        complaintRate = (complaintRate / data.length).toFixed(1) + '%';
		}        
        $(api.column(7).footer()).html(complaintRate.toLocaleString());        
        
        //불만 전년대비 증감율 평균
        if(data.length == 0){
			complaintYoyCnt = 0
		}else{
	        //불만 전년대비 증감율 평균
	        api.column(8, {search:'applied'}).data().each(function(data,index){
	            complaintYoyCnt += parseFloat(data);
	        });			
	        complaintYoyCnt = (complaintYoyCnt / data.length).toFixed(1) + '%';
		}        
        $(api.column(8).footer()).html(complaintYoyCnt.toLocaleString());        
        
             
        //------------------- 제안 -------------------        
        //제안 접수 합계
        api.column(9, {search:'applied'}).data().each(function(data,index){
            suggestionCnt += parseFloat(data);
        });
        $(api.column(9).footer()).html(suggestionCnt.toLocaleString());
                
        //제안 비율 평균
        if(data.length == 0){
			suggestionRate = 0
		}else{
	        //제안 비율 평균
	        api.column(10, {search:'applied'}).data().each(function(data,index){
	            suggestionRate += parseFloat(data);
	        });			
	        suggestionRate = (suggestionRate / data.length).toFixed(1) + '%';
		}        
        $(api.column(10).footer()).html(suggestionRate.toLocaleString());        
        
        //제안 전년대비 증감율 평균
        if(data.length == 0){
			suggestionYoyCnt = 0
		}else{
	        //제안 전년대비 증감율 평균
	        api.column(11, {search:'applied'}).data().each(function(data,index){
	            suggestionYoyCnt += parseFloat(data);
	        });			
	        suggestionYoyCnt = (suggestionYoyCnt / data.length).toFixed(1) + '%';
		}        
        $(api.column(11).footer()).html(suggestionYoyCnt.toLocaleString());          
        
        
        //------------------- 문의 -------------------
        //문의 접수 합계
        api.column(12, {search:'applied'}).data().each(function(data,index){
            inquiryCnt += parseFloat(data);
        });
        $(api.column(12).footer()).html(inquiryCnt.toLocaleString());


        //문의 비율 평균
        if(data.length == 0){
			inquiryRate = 0
		}else{
	        //문의 비율 평균
	        api.column(13, {search:'applied'}).data().each(function(data,index){
	            inquiryRate += parseFloat(data);
	        });			
	        inquiryRate = (inquiryRate / data.length).toFixed(1) + '%';
		}        
        $(api.column(13).footer()).html(inquiryRate.toLocaleString());        
        
        //문의 전년대비 증감율 평균
        if(data.length == 0){
			inquiryYoyCnt = 0
		}else{
	        //문의 전년대비 증감율 평균
	        api.column(14, {search:'applied'}).data().each(function(data,index){
	            inquiryYoyCnt += parseFloat(data);
	        });			
	        inquiryYoyCnt = (inquiryYoyCnt / data.length).toFixed(1) + '%';
		}        
        $(api.column(14).footer()).html(inquiryYoyCnt.toLocaleString());          
        
	},    
	initComplete: function(){
		//전체건수 클릭
		$('[cellId="totalCnt"]').on('click', function(idx) {
			fnVocMove('', $(this).parent().attr('rcptChnnCd'));
		});
		
		//칭찬접수 클릭
		$('[cellId="complimentCnt"]').on('click', function() {
			fnVocMove('02', $(this).parent().attr('rcptChnnCd'));
		});
				
		//불만접수 클릭
		$('[cellId="complaintCnt"]').on('click', function() {
			fnVocMove('01', $(this).parent().attr('rcptChnnCd'));
		});
		
		//제안접수 클릭
		$('[cellId="suggestionCnt"]').on('click', function() {
			fnVocMove('03', $(this).parent().attr('rcptChnnCd'));
		});
								
		//문의접수 클릭
		$('[cellId="inquiryCnt"]').on('click', function() {
			fnVocMove('04', $(this).parent().attr('rcptChnnCd'));
		});						
	},
    buttons: [
        {
            extend: 'excel',
            text: '엑셀다운로드',
            filename: '채널별VOC현황-' + Util.getToday(),
            title: '채널별VOC현황 : '+ Util.getToday(),
        },
    ],
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

function fnVocMove(vocCaseCd, rcptChnnCd){
	let $frm = $('#searchForm');
	
    //조회일자조건 셋팅
    let regDtStart = $frm.find('#regDtStart').val();;
    let regDtEnd = $frm.find('#regDtEnd').val();
    
    //등록일자
	localStorage.setItem("regDtStart", regDtStart);
	localStorage.setItem("regDtEnd", regDtEnd);
	
	//회사코드
	localStorage.setItem("companyCd", $frm.find('#companyCd').val());
	
	//voc구분
	if(!ObjectUtil.isEmpty(vocCaseCd)) {
		localStorage.setItem("vocCaseCd", vocCaseCd);
	}
	
	//접수채널
	if(!ObjectUtil.isEmpty(rcptChnnCd)) {
		localStorage.setItem("rcptChnnCd", rcptChnnCd);
	}	
	
	//voc유형1
	if(!ObjectUtil.isEmpty(vocTypeCd1)) {
		localStorage.setItem("vocTypeCd1", $frm.find('#vocTypeCd1').val());
	}
	//voc유형2
	if(!ObjectUtil.isEmpty(vocTypeCd2)) {
		localStorage.setItem("vocTypeCd2", $frm.find('#vocTypeCd2').val());
	}
	//voc유형3
	if(!ObjectUtil.isEmpty(vocTypeCd3)) {
		localStorage.setItem("vocTypeCd3", $frm.find('#vocTypeCd3').val());
	}
	
	goPage('/voc/voclist');
	
}
