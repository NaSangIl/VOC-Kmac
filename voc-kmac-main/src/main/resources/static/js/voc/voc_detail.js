let $GridCust = null;    		//고객리스트
let $GridHist = null;    		//이력리스트
let $VOC_SEARCH_DATA = {};      //VOC 상세 조회 데이터
let $editor_voc;               //VOC내용 웹에디터
let $editor_act;                //처리내용 웹에디터
let $toolbarItems = [
	['heading', 'bold', 'italic', 'strike'],
	['hr', 'quote'],
	['ul', 'ol', 'task'],
	['table'],
	['scrollSync'],
];
let $companyCd;                //회사코드
$(function() {
	// 초기 설정 및 수행
	init();
});

/**
 * 초기 설정 및 수행 내용
 */
let init = function() {
	// localStorage ----------------
	let vocSeq = localStorage.getItem("vocSeq");
	$companyCd = localStorage.getItem("companyCd");
	if("I" === $("#vocViewType").val()){
		vocSeq = -1;
		localStorage.removeItem('vocSeq');
		localStorage.removeItem('companyCd');
	}
	//localStorage.removeItem('vocSeq');

    
	// webeditor setting ----------------
	$editor_voc = new toastui.Editor.factory({
		el: document.querySelector('#editorVoc'),
		toolbarItems: $toolbarItems,
		initialEditType: 'wysiwyg',
		previewStyle: 'vertical',
		autofocus: false,
		hideModeSwitch:true,
		height: '100%',
		 // 이미지가 Base64 형식으로 입력되는 것 가로채주는 옵션
		    hooks: {
		    	addImageBlobHook: (blob, callback) => {
		    		// blob : Java Script 파일 객체
		    		//console.log(blob);
		    		
		    		const formData = new FormData();
		        	formData.append('vocContentsImage', blob);
		        	
		        	let url = '/kmacvoc/v1/voc/fileUpload';
		   			$.ajax({
		           		type: 'POST',
		           		enctype: 'multipart/form-data',
		           		url: url,
		           		data: formData,
		           		dataType: 'json',
		           		processData: false,
		           		contentType: false,
		           		cache: false,
		           		timeout: 600000,
		           		success: function(data) {
		           			
		           			callback("/upload/"+data.data.fileList1[0].fileNm, data.data.fileList1[0].fileNm);
		           		},
		           		error: function(e) {
		           			
		           			callback('image_load_fail', '사진업로드실패');
		           		}
		           	});
		    	}
		    }
	});

	$editor_act = new toastui.Editor.factory({
		el: document.querySelector('#editorAct'),
		toolbarItems: $toolbarItems,
		initialEditType: 'wysiwyg',
		previewStyle: 'vertical',
		autofocus: false,
		hideModeSwitch:true,
		height: '100%',
		 // 이미지가 Base64 형식으로 입력되는 것 가로채주는 옵션
		    hooks: {
		    	addImageBlobHook: (blob, callback) => {
		    		// blob : Java Script 파일 객체
		    		//console.log(blob);
		    		
		    		const formData = new FormData();
		        	formData.append('vocActContentsImage', blob);
		        	
		        	let url = '/kmacvoc/v1/voc/fileUpload';
		   			$.ajax({
		           		type: 'POST',
		           		enctype: 'multipart/form-data',
		           		url: url,
		           		data: formData,
		           		dataType: 'json',
		           		processData: false,
		           		contentType: false,
		           		cache: false,
		           		timeout: 600000,
		           		success: function(data) {
		           			
		           			callback("/upload/"+data.data.fileList1[0].fileNm, data.data.fileList1[0].fileNm);
		           		},
		           		error: function(e) {
		           			
		           			callback('image_load_fail', '사진업로드실패');
		           		}
		           	});
		    	}
		    }
	});

	// event 연결 ----------------
	$('.btn-wrap .btn-go-list').on('click', function() { goList(); });           	//목록
	$('.btn-wrap .btn-save-voc').on('click', function() { saveData(''); });        	//저장
	$('.btn-wrap .btn-delt-voc').on('click', function() { deleteData(); });      	//삭제
	$('.btn-wrap .btn-appr-voc').on('click', function() { saveData('APPROVAL'); }); //상신완료
	$('.actions .btn-proc-reject').on('click', function() { rejectData(); });    	//반려
	$('.btn-wrap .btn-finish-voc').on('click', function() { finishData(); });      	//완료

	$('.btn-wrap .btn-reject-voc').on('click', function() { popReject(); });   		//반려사유팝업
	$('.btn-wrap .btn-search-cust').on('click', function() { popSearchCust(); });   //고객찾기팝업
	$('.modal-cust .btn-search').on('click', function() { loadGridCust(); });   	//고객찾기팝업 검색

	$('.btn-select-cust').on('click', function() { selectCust(); });            	//고객선택

	$('.btn-reset-cust').on('click', function() { resetCust(); });           		//초기화
	$('.chk-anonymous').on('click', function() { setAnonymous(); });         		//익명고객
	$('.chk-immeAct').on('click', function() { setImmeAct(); });         			//즉시처리

	$('#registForm').find('#emailAddr3').on('change', function() { setMailAddr(); }); //메일dropbox 변경시

	// dropbox data setting ----------------
	DropdownUtil.makeCodeList('VOC_CASE_CD', $('#registForm').find('.d-vocCaseCd'));       //VOC구분코드
	DropdownUtil.makeCodeList('RCPT_CHNN_CD', $('#registForm').find('.d-rcptChnnCd'));     //접수채널코드
	DropdownUtil.makeCodeList('HOUR_CD', $('#registForm').find('.d-sourceDt2'));           //시간코드
	DropdownUtil.makeCodeList('MINUTE_CD', $('#registForm').find('.d-sourceDt3'));         //분코드
	DropdownUtil.makeCodeList('CUST_REPLY_CD', $('#registForm').find('.d-custReplyCd'));   //고객회신요청방법코드
	DropdownUtil.makeCodeList('EMAIL_ADDR', $('#registForm').find('.d-emailAddr'));        //메일

	DropdownUtil.makeCompList($('#registForm').find('.d-companyCd'));
	DropdownUtil.makeActUserList($('#registForm').find('#companyCd').val(), $('#registForm').find('.d-vocActUserNo'))    //처리자목록

	makeCodeVocType($('#registForm'), $companyCd);      //VOC유형코드
	makeCodeVocActType($('#registForm'), $companyCd);   //처리유형코드

	//일반사용자의 권한만 가진 경우, 즉시처리 여부 체크박스 숨김.
    if($SessionInfo.getUserAuth().indexOf('900') > -1 && $SessionInfo.getUserAuth().length == 3){
		$('#immeActLayout', $('#registForm')).addClass('blind');
	}else{
		$('#immeActLayout', $('#registForm')).removeClass('blind');
	}
    
    
    
	setTimeout(function() {
		$('#registForm').find('.d-companyCd').dropdown('set selected', $companyCd);
		if (vocSeq > 0){
			searchData(vocSeq);
		}
	}, 300);
}

/**
 * 고객찾기 그리드 옵션
 */
let GRID_OPTIONS_CUST = {
	columns: [
		{
			data: 'custSeq', className: "select-checkbox",
			'render': function(data, type, full, meta) {
				return '<input type="radio" value="' + data + '" />';
			}
		},
		{ data: 'custNo', className: "text-center" },
		{ data: 'custNm', className: "text-center" },
		{ data: 'telNo', className: "text-center" },
		{ data: 'regDt', className: "text-center" },
		{ data: 'vocCnt', className: "text-center" },
		{ data: 'sensVocCnt', className: "text-center" },
	],
	pageLength:5,
	scrollX: '100%',
	scrollXInner: '100%',
};

let GRID_OPTIONS_HIST = {
	columns: [
		{ data: 'histTypeNm', className: "text-center" },
		{ data: 'histCont', className: "text-center" },
		{ data: 'regUserNm', className: "text-center" },
		{ data: 'regDt', className: "text-center" },
	],
};

/**
 * 고객 Grid 구성
 */
let loadGridCust = function() {
	let gridOptions = $.extend(true, {}, GRID_OPTIONS_CUST);
	let url = '/kmacvoc/v1/customer/list';
	let param = $('#searchCustForm').form('get.values');
	$GridCust = gridUtil.loadGrid("listDataTableCust", gridOptions, url, param);
};

/**
 * History Grid 구성
 */
let loadGridHist = function() {
	let gridOptions = $.extend(true, {}, GRID_OPTIONS_HIST);
	let url = '/kmacvoc/v1/history/list/' + $('#registForm').find('#vocSeq').val();
	let param = {};

	$GridHist = gridUtil.loadGrid("listDataTableHist", gridOptions, url, param);
};

/**
 * 목록화면 이동
 */
let goList = function() {
	goPage('/voc/voclist');
}

/**
 * 데이터 조회
 */
let searchData = function(key) {
	AjaxUtil.get(
		'/kmacvoc/v1/voc/' + key,
		{},
		function(result) {
			if (result && result.data) {
				$VOC_SEARCH_DATA = result.data;
				let d = result.data;
				if (d.telNo != '') {
					d.telNo1 = d.telNo.split('-')[0];
					d.telNo2 = d.telNo.split('-')[1];
					d.telNo3 = d.telNo.split('-')[2];
				}
				if (d.emailAddr != '') {
					d.emailAddr1 = d.emailAddr.split('@')[0];
					d.emailAddr2 = d.emailAddr.split('@')[1];
				}
				if (d.sourceDt != '') {
					d.sourceDt1 = d.sourceDt.substring(0, 10);
					d.sourceDt2 = d.sourceDt.substring(11, 13);
					d.sourceDt3 = d.sourceDt.substring(14, 16);
				}
				$('#registForm').form('clear');
				$('#registForm').form('set.values', d);

				//editor
				$editor_voc.setHTML(d.vocCont, true);
				$editor_act.setHTML(d.vocActCont, true);
				
				
				//checkbox
				if (d.anonymCustYn == 'Y') $('#registForm').find('.chk-anonymous').click();
				if (d.custReplyYn == 'Y') $('#registForm').find('.chk-custReplyYn').click();
				if (d.sensSpecYn == 'Y') $('#registForm').find('.chk-sensSpecYn').click();
				if (d.immeActYn == 'Y') {
					$('#registForm').find('.chk-immeActYn').click();
					$('.vocActDiv').removeClass('blind');
				}

				//등록자만 저장,삭제버튼 노출
				if ($SessionInfo.getUserSeq() == d.regUserNo) {
					$('.btn-save-voc, btn-delt-voc').removeClass('blind');
				}

				$('.vocActDiv').removeClass('blind');
				$('.btn-save-voc').html('저장');
				$('.basicDiv').removeClass('blind');
				$('.viewMode').removeClass('blind');
				$('.regiMode').addClass('blind');
				$('#immeActLayout').remove();
				$companyCd = d.companyCd;

				if (d.vocStatusCd == 'P0') {  // 진행중상태

					// 등록자,처리자권한이 있는경우만 아래 버튼 노출
					if ($SessionInfo.getUserSeq() == d.regUserNo || $SessionInfo.getUserAuth().indexOf('500') > -1) {
						$('.btn-appr-voc').removeClass('blind');
						$('.btn-save-voc').removeClass('blind');
						$('.btn-delt-voc').removeClass('blind');
					} else {
						$("#registForm :input").attr("readonly", true);
						$('.ui.dropdown').addClass("disabled");
						$('.ui.checkbox').addClass("disabled");
						$('.ui.button, .file-btn').addClass("blind");
						$('.btn-go-list').removeClass("blind");
					}
				}

				if (d.vocStatusCd == 'A0') {  // 완료상신상태
				
					$("#registForm :input").attr("readonly", true);
					$('.ui.dropdown').addClass("disabled");
					$('.ui.checkbox').addClass("disabled");
					$('.ui.button, .file-btn').addClass("blind");
					$('.btn-go-list').removeClass("blind");
						
					if ($SessionInfo.getUserAuth().indexOf('200') > -1 || $SessionInfo.getUserAuth().indexOf('000') > -1) {
						
						$('.btn-finish-voc').removeClass('blind');
						$('.btn-reject-voc').removeClass('blind');
						$('.btn-appr-voc').addClass('blind');
						
						
					} 
				}

				if (d.vocStatusCd == 'C0') {  //완료상태
					$("#registForm :input").attr("readonly", true);
					$('.ui.dropdown').addClass("disabled");
					$('.ui.checkbox').addClass("disabled");
					$('.ui.button, .file-btn').addClass("blind");
					$('.btn-go-list').removeClass("blind");
					
					
				}

				// 고객 VOC 건수 표시
				$('#registForm').find('#vocCntDiv').removeClass('blind');
				$('#registForm').find('.voc-cnt').html(d.vocCustInfo.vocCnt);
				$('#registForm').find('.sens-cnt').html(d.vocCustInfo.sensVocCnt);

				// VOC등록 첨부파일표시
				d.fileList1.forEach(file => {
					
					var pageType = "readOnly";
					
					if (d.vocStatusCd == 'P0') {  // 진행중상태

						// 등록자,처리자권한이 있는경우만 아래 버튼 노출
						if ($SessionInfo.getUserSeq() == d.regUserNo || $SessionInfo.getUserAuth().indexOf('500') > -1) {
							pageType = "write";
						}else{
							pageType = "readOnly";
						}
					}
					
					//권한체크
					if (d.vocStatusCd == 'A0') {  // 완료상신상태
						pageType = "readOnly";
					}
					
					if (d.vocStatusCd == 'C0') {  //완료상태
						pageType = "readOnly";
					}
					
					//삭제가능
					if(pageType == "write"){
						$('.vocFilesDiv').append(`
	                    <li id="${file.fileSeq}">
	                        <a href='/kmacvoc/v1/file/download/${file.fileSeq}' title='${file.fileNm}' class='file-name' download>${file.fileNm}</a>
	                        <button data-index='${file.fileSeq}' onclick='javascript:deleteFile(${file.fileSeq});' class='btn-file-delt'></button>
	                    </li>`);
	                
	                //읽기전용    
					}else{
						
						$('.vocFilesDiv').append(`
	                    <li id="${file.fileSeq}">
	                        <a href='/kmacvoc/v1/file/download/${file.fileSeq}' title='${file.fileNm}' class='file-name' download>${file.fileNm}</a>
	                    </li>`);
					}
					
				});

				// VOC처리 첨부파일표시
				d.fileList2.forEach(file => {
					var pageType = "readOnly";
					
					if (d.vocStatusCd == 'P0') {  // 진행중상태

						// 등록자,처리자권한이 있는경우만 아래 버튼 노출
						if ($SessionInfo.getUserSeq() == d.regUserNo || $SessionInfo.getUserAuth().indexOf('500') > -1) {
							pageType = "write";
						}else{
							pageType = "readOnly";
						}
					}
					
					//권한체크
					if (d.vocStatusCd == 'A0') {  // 완료상신상태
						if ($SessionInfo.getUserAuth().indexOf('200') > -1 || $SessionInfo.getUserAuth().indexOf('000') > -1) {
							pageType = "write";
						}else{
							pageType = "readOnly";
						}
					}
					
					if (d.vocStatusCd == 'C0') {  //완료상태
						pageType = "readOnly";
					}
					
					//삭제가능
					if(pageType == "write"){
						$('.vocActFilesDiv').append(`
		                    <li id="${file.fileSeq}">
		                        <a href='/kmacvoc/v1/file/download/${file.fileSeq}' title='${file.fileNm}' class='file-name' download>${file.fileNm}</a>
		                        <button data-index='${file.fileSeq}' onclick='javascript:deleteFile(${file.fileSeq});' class='btn-file-delt'></button>
		                    </li>`);
	                
	                //읽기전용    
					}else{
						
						$('.vocActFilesDiv').append(`
		                    <li id="${file.fileSeq}">
		                        <a href='/kmacvoc/v1/file/download/${file.fileSeq}' title='${file.fileNm}' class='file-name' download>${file.fileNm}</a>
		                    </li>`);
					}
					
					
				});

				$('.btn-file-delt').on('click', function(e) {
					e.preventDefault();
				});

				loadGridHist(d.histList);
			}
		}
	);
}

/**
 * 고객찾기 팝업오픈
 */
let popSearchCust = function() {
	$('#searchCustForm').form('clear');
	$('#searchCustForm').find('#companyCd').val($companyCd ? $companyCd : $SessionInfo.getCompanyCd());
	loadGridCust();
	$('.ui.modal-cust').modal('show');
}


/**
 * 고객선택
 */
let selectCust = function() {
	let $frm = $('#registForm');
	if ($selectedRowData.custSeq == undefined) {
		alert('고객을 선택해 주세요.');
		return;
	}
	
	//익명체크 해제
	$('#anonymCustYn', $frm).val('');
	$('#anonymCustYn', $frm).prop('checked',false);
	setAnonymous();
	

	$frm.find('#custSeq').val($selectedRowData.custSeq);
	$frm.find('#custNm').val($selectedRowData.custNm);
	$frm.find('#custNo').val($selectedRowData.custNo);
	if ($selectedRowData.telNo != '') {
		$frm.find('#telNo').val($selectedRowData.telNo);
		$frm.find('#telNo1').val($selectedRowData.telNo.split('-')[0]);
		$frm.find('#telNo2').val($selectedRowData.telNo.split('-')[1]);
		$frm.find('#telNo3').val($selectedRowData.telNo.split('-')[2]);
	}
	if ($selectedRowData.emailAddr != '') {
		$frm.find('#emailAddr').val($selectedRowData.emailAddr);
		$frm.find('#emailAddr1').val($selectedRowData.emailAddr.split('@')[0]);
		$frm.find('#emailAddr2').val($selectedRowData.emailAddr.split('@')[1]);
	}

	$frm.find('.voc-cnt').html($selectedRowData.vocCnt);
	$frm.find('.sens-cnt').html($selectedRowData.sensVocCnt);
	$frm.find('#custNm').attr('readonly', true);
	$frm.find('.btn-reset-cust').removeClass('blind');
	$frm.find('#vocCntDiv').removeClass('blind');
	$('#listDataTableCust').find('tr.selected').removeClass('selected');
	$('.ui.modal-cust').modal('hide');
}

/**
 * 고객정보 초기화
 */
let resetCust = function() {
	$('#registForm').find('.cust-data').val('');
	$('#registForm').find('.voc-cnt').html('');
	$('#registForm').find('.sens-cnt').html('');
	$('#registForm').find('#custNm').attr('readonly', false);
	$('#registForm').find('.chk-anonymous').attr('checked', false);
	$('#registForm').find('.btn-reset-cust').addClass('blind');
	$('#registForm').find('#vocCntDiv').addClass('blind');
	$('#registForm').find('.d-emailAddr').dropdown('restore defaults');
	$('#anonymCustYn', $('#registForm')).val('');
	$('#anonymCustYn', $('#registForm')).prop('checked', false);
	setAnonymous();
	
}

/**
 * 익명고객처리
 */
let setAnonymous = function() {
	let $frm = $('#registForm');
	if ($frm.find('#anonymCustYn').val() == 'Y') {  //익명인 경우
		$frm.find('#custNm').val('익명고객');
		$frm.find('#custNm').attr("readonly", true);
		
		$frm.find('#custNo').val($frm.find('#companyCd').val() + '_99999');
		$frm.find('.display1').val('');
		$frm.find('#display1').addClass('blind');
		$frm.find('#vocCntDiv').addClass('blind');
		$frm.find('.btn-reset-cust').removeClass('blind');
	} else {
		
		$frm.find('#custNm').val('');
		$frm.find('#custNm').attr("readonly", false);
		
		$frm.find('#custNo').val('');
		$frm.find('#display1').removeClass('blind');
		$frm.find('.btn-reset-cust').addClass('blind');
	}
}

/**
 * 즉시처리
 */
let setImmeAct = function() {
	let $frm = $('#registForm');
	if ($frm.find('#immeActYn').val() == 'Y') {  //즉시처리인 경우
		$frm.find('.vocActDiv').removeClass('blind');
		$frm.find('.btn-save-voc').text('VOC즉시처리');
		
	} else {
		$frm.find('.vocActDiv').addClass('blind');
		$frm.find('.voc-act-data').val('');
		$frm.find('.file-list.voc-act-data').html('');
		$frm.find('.d-vocActTypeCd1').dropdown('restore defaults');
		$frm.find('.d-vocActTypeCd2').dropdown('restore defaults');
		$frm.find('.d-vocActUserNo').dropdown('restore defaults');
		$frm.find('.btn-save-voc').text('VOC등록');
	}
}

/**
 * 데이터 저장
 */
let saveData = function(flag) {
	let $frm = $('#registForm');

	let vocCont = $editor_voc.getHTML();
	let vocActCont = $editor_act.getHTML();

	if ($frm.find('#custNm').val() == '') {
		alert('고객명은 필수항목입니다.');
		$frm.find('#custNm').focus();
		return;
	}
	if ($frm.find('#vocCaseCd').val() == '') {
		alert('VOC구분은 필수항목입니다.');
		$frm.find('#vocCaseCd').focus();
		return;
	}
	if ($frm.find('#vocTypeCd1').val() == '' || $frm.find('#vocTypeCd2').val() == '' || $frm.find('#vocTypeCd3').val() == '') {
		alert('VOC유형은 필수항목입니다.');
		$frm.find('#vocTypeCd1').focus();
		return;
	}
	if ($frm.find('#rcptChnnCd').val() == '') {
		alert('접수채널은 필수항목입니다.');
		$frm.find('#rcptChnnCd').focus();
		return;
	}
	if ($frm.find('#sourceCd').val() == '') {
		alert('발생장소는 필수항목입니다.');
		$frm.find('#sourceCd').focus();
		return;
	}
	if ($frm.find('#sourceDt1').val() == '' || $frm.find('#sourceDt2').val() == '' || $frm.find('#sourceDt3').val() == '') {
		alert('발생일시는 필수항목입니다.');
		$frm.find('#sourceDt1').focus();
		return;
	}
	if ($frm.find('#vocTitle').val() == '') {
		alert('VOC제목/요약은 필수항목입니다.');
		$frm.find('#vocTitle').focus();
		return;
	}

	if ($editor_voc.getMarkdown() == '') {
		alert('VOC내용은 필수항목입니다.');
		$frm.find('#vocCont').focus();
		return;
	}

	if ($frm.find('#immeActYn').val() == 'Y' || flag == 'APPROVAL') {  //즉시처리, 완료상신인 경우
		if ($frm.find('#vocActTypeCd1').val() == '') {
			alert('처리유형은 필수항목입니다.');
			$frm.find('#vocActTypeCd1').focus();
			return;
		}
		if ($frm.find('#vocActDt').val() == '') {
			alert('처리일자는 필수항목입니다.');
			$frm.find('#vocActDt').focus();
			return;
		}
		if ($frm.find('#vocActUserNo').val() == '') {
			alert('처리자는 필수항목입니다.');
			$frm.find('#vocActUserNo').focus();
			return;
		}
		if ($editor_act.getMarkdown() == '') {
			alert('처리내용은 필수항목입니다.');
			$frm.find('#vocActCont').focus();
			return;
		}
	}

	if (flag === 'APPROVAL' && !confirm('VOC 정보를 완료상신처리 하시겠습니까?')) {
		return false;
	} else if (flag !== 'APPROVAL' && !confirm('저장하시겠습니까?')) {
		return false;
	}


	$frm.find('#sourceDt').val(Util.dateFormat($frm.find('#sourceDt1').val(), '-') + ' ' + Util.timeFormat($frm.find('#sourceDt2').val() + $frm.find('#sourceDt3').val()));
	$frm.find('#telNo').val($frm.find('#telNo1').val() + '-' + $frm.find('#telNo2').val() + '-' + $frm.find('#telNo3').val());
	$frm.find('#emailAddr').val($frm.find('#emailAddr1').val() + '@' + $frm.find('#emailAddr2').val());

	$frm.find('#vocCont').val(vocCont);
	$frm.find('#vocActCont').val(vocActCont);

	const vocFiles = new DataTransfer();
	const vocActFiles = new DataTransfer();
	let f;
	for (let i = 0; i < dataTranster.files.length; i++) {
		f = dataTranster.files[i];
		$('.vocFilesDiv>li').map(function() {
			if (f.lastModified == $(this).attr('id')) {
				vocFiles.items.add(f);
			}
		});
		$('.vocActFilesDiv>li').map(function() {
			if (f.lastModified == $(this).attr('id')) {
				vocActFiles.items.add(f);
			}
		});
	}
	document.querySelector('#vocFiles').files = vocFiles.files;
	document.querySelector('#vocActFiles').files = vocActFiles.files;

	let form = $("#registForm")[0];
	let formData = new FormData(form);
	let vocSeq = $frm.find('#vocSeq').val();
	let url = vocSeq == '0' ? '/kmacvoc/v1/voc/add' : (flag == 'APPROVAL' ? '/kmacvoc/v1/voc/approval' : '/kmacvoc/v1/voc/modify');

	Util.showLoading();
	$.ajax({
		method: "POST",
		url: url,
		data: formData,
		contentType: false,
		processData: false
	}).done(function(result) {
		Util.hideLoading();
		if (result && result.messageCode == '0000') {
			alert(result.data.rtnMessage);
			localStorage.setItem("vocSeq", 0 < vocSeq ? vocSeq : result?.data?.rtnKey);
			localStorage.setItem("companyCd", $companyCd ? $companyCd : $SessionInfo.getCompanyCd());
			goPage('/voc/vocdetail');
			//goList();
		}
	}).fail(function(xhr, status, error) {
		Util.hideLoading();
	});
}

/**
 * 완료 처리
 */
let finishData = function() {

	if (!confirm('VOC 정보를 완료처리 하시겠습니까?')) return;

	let vocSeq = $('#registForm').find('#vocSeq').val();
	let param = { "vocSeq": vocSeq };
	let url = '/kmacvoc/v1/voc/finish';

	AjaxUtil.post(
		url,
		JSON.stringify(param),
		function(result) {
			if (result && result.messageCode == '0000') {
				alert(result.data.rtnMessage);
				localStorage.setItem("vocSeq", vocSeq);
				localStorage.setItem("companyCd", $companyCd ? $companyCd : $SessionInfo.getCompanyCd());
				goPage('/voc/vocdetail');
				//goList();
			}
		}
	);
}

/**
 * 반려처리
 */
let rejectData = function() {
	if (!confirm('VOC 정보를 반려 처리 하시겠습니까?')) return;

	let vocSeq = $('#registForm').find('#vocSeq').val();
	let rejectMemo = $('.modal-reject').find('#rejectMemo').val();
	let param = { "vocSeq": vocSeq, "rejectMemo": rejectMemo };
	let url = '/kmacvoc/v1/voc/reject';


	AjaxUtil.post(
		url,
		JSON.stringify(param),
		function(result) {
			if (result && result.messageCode == '0000') {
				alert(result.data.rtnMessage);
				localStorage.setItem("vocSeq", vocSeq);
				localStorage.setItem("companyCd", $companyCd ? $companyCd : $SessionInfo.getCompanyCd());
				goPage('/voc/vocdetail');
				//goList();
			}
		}
	);
}

/**
 * 데이터 삭제
 */
let deleteData = function() {

	if (!confirm('VOC 정보를 삭제하시겠습니까?')) return;

	let vocSeq = $('#registForm').find('#vocSeq').val();
	let param = { "vocSeq": vocSeq };
	let url = '/kmacvoc/v1/voc/remove';

	AjaxUtil.post(
		url,
		JSON.stringify(param),
		function(result) {
			if (result && result.messageCode == '0000') {
				alert(result.data.rtnMessage);
				goList();
			}
		}
	);
}

/**
 * 메일주소 dropbox 선택시 셋팅
 */
let setMailAddr = function() {
	let v = $('#registForm').find('#emailAddr3').val();
	if (v == 'DIRECT_INPUT') {
		v = '';
	}
	$('#registForm').find('#emailAddr2').val(v);
}

/**
 * 파일삭제
 */
let deleteFile = function(fileSeq) {
	if (!confirm('파일을 삭제하시겠습니까?')) return;

	let url = '/kmacvoc/v1/file/remove/' + fileSeq;

	AjaxUtil.post(
		url,
		{},
		function(result) {
			if (result && result.messageCode == '0000') {
				$('.file-list').find('#' + fileSeq).remove();
			}
		}
	);
}

/**
 * 반려사유 팝업창 오픈
 */
let popReject = function() {
	$('.modal-reject').find('#rejectMemo').val('');
	$('.ui.modal-reject').modal('show');
}

/**
 * VOC이력/민감 팝업 오픈 및 데이터 조회
 */
let popModalCommonVoc = function(sensSpecYn) {
	let param = {};
	param.companyCd = $VOC_SEARCH_DATA.vocCustInfo.companyCd;
	param.companyNm = $VOC_SEARCH_DATA.vocCustInfo.companyNm;
	param.custSeq = $VOC_SEARCH_DATA.vocCustInfo.custSeq;
	param.custNm = $VOC_SEARCH_DATA.vocCustInfo.custNm;
	param.sensSpecYn = sensSpecYn;
	LoadGrid.modalCommonVoc(param);

	$('#modalCommonVoc').modal('show');
}

