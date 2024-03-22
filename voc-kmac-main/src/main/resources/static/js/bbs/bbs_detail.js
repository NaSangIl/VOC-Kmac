let $bbsSeq;    //bbs key
let $backPage;  //이전 페이지

let editor;
$(function () {
    // 초기 설정 및 수행
    init();
});

/**
 * 초기 설정 및 수행 내용
 */
let init = function(){
    let bbsSeq = localStorage.getItem("bbsSeq");
    
    $bbsSeq = localStorage.getItem("bbsSeq");
	$backPage = localStorage.getItem("backPage");
	
    editor = new toastui.Editor({
        el: document.querySelector('#editor'),
        toolbarItems: [
            ['heading', 'bold', 'italic', 'strike'],
            ['hr', 'quote'],
            ['ul', 'ol', 'task'],
            ['table'],
            ['scrollSync'],
        ],
        initialEditType: 'wysiwyg',
        previewStyle: 'vertical',
        height: '100%',
        // 이미지가 Base64 형식으로 입력되는 것 가로채주는 옵션
	    hooks: {
	    	addImageBlobHook: (blob, callback) => {
	    		// blob : Java Script 파일 객체
	    		//console.log(blob);
	    		
	    		const formData = new FormData();
	        	formData.append('noticeContentsImage', blob);
	        	
	        	let url = '/kmacvoc/v1/bbs/imgupload';
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
    $('.btn-wrap .btn-go-list').on('click', function(){ goList(); });              //목록
    $('.btn-wrap .btn-save-bbs').on('click', function(){ saveData(); });           //저장
    $('.btn-wrap .btn-delt-bbs').on('click', function(){ deleteData(); });         //삭제
    $('.btn-wrap .btn-add-comments').on('click', function(){ popAddComments(); });  //댓글저장용 팝업오픈
    $('.actions .btn-save-comments').on('click', function(){ saveComments(); });    //댓글저장

    // dropbox data setting ----------------
    DropdownUtil.makeCompList($('#registForm').find('.d-companyCd'));

	//시스템운영자, 시스템관리자인 경우
    if ($SessionInfo.getUserAuth().indexOf('100') > -1 || $SessionInfo.getUserAuth().indexOf('000') > -1) {
		$('#companyArea').removeClass('blind');
		
	//그외
	}else{
		$('#companyArea').addClass('blind');
	}
	
    setTimeout(function() {
        //if(bbsSeq > 0) searchData(bbsSeq);
        if($bbsSeq > 0) searchData($bbsSeq);
        localStorage.removeItem('bbsSeq');
        localStorage.removeItem('backPage');
        
    }, 200);

    loadGrid();
}

/**
 * 댓글 그리드 옵션
 */
let GRID_OPTIONS = {
    columns     : [
        { data: 'bbsCommentsSeq',   className: "text-center",
            'render': function (data, type, full, meta) {
                return  meta.row+1;
            }
        },
        { data: 'comments',         className: "text-center ui-comment-list"   },
        { data: 'regUserNm',        className: "text-center"   },
        { data: 'regDt',            className: "text-center"   },
        { data: 'bbsCommentsSeq',
            'render': function (data, type, full, meta) {
				
				var sbtnHtml = "";
				
				if($SessionInfo.getUserSeq() == full.regUserNo || $SessionInfo.getUserAuth().indexOf('200') > -1 || $SessionInfo.getUserAuth().indexOf('000') > -1){
					sbtnHtml += "<div class='btn-wrap'><button class='ui button btn-black-line btn-updt-comments' onclick='popDtlComments("+data+")'>수정</button></div>";
                	sbtnHtml += "<div class='btn-wrap'><button class='ui button btn-black-line btn-delt-comments ml_5' onclick='deleteComments("+data+")'>삭제</button></div>";	
				}
                return sbtnHtml;
            }
        },
    ],
};

/**
 * 댓글 Grid 구성
 */
let loadGrid = function(){
    let gridOptions = $.extend(true, {}, GRID_OPTIONS);
    let url = '/kmacvoc/v1/bbs/comments/list';
    let param = {"bbsSeq":$bbsSeq};

    gridUtil.loadGrid("listDataTableComments", gridOptions, url, param);
};

/**
 * 목록화면 이동
 */
let goList = function(){
    goPage('/bbs/bbslist');
}

/**
 * 데이터 조회
 */
let searchData =  function(key){
    AjaxUtil.get(
        '/kmacvoc/v1/bbs/'+key,
        {},
        function(result){
            if(result && result.data){
                let d = result.data;
                $('#registForm').form('clear');
                $('#registForm').form('set.values', d);

                editor.setHTML(d.contents, false);

                // 첨부파일표시
                d.fileList.forEach(file => {
                    $('.bbsFilesDiv').append(`
                    <li id="${file.fileSeq}">
                        <a href='/kmacvoc/v1/file/download/${file.fileSeq}' title='${file.fileNm}' class='file-name' download>${file.fileNm}</a>
                        <button data-index='${file.fileSeq}' onclick='javascript:deleteFile(${file.fileSeq});' class='btn-file-delt'></button>
                    </li>`);
                });

                $('.btn-file-delt').on('click', function(e){
                    e.preventDefault();
                });

                $('.btn-delt-bbs, .viewDiv').removeClass('blind');   //삭제버튼,이력 표시
            }
        }
    );
}

/**
 * 데이터 저장
 */
let saveData = function(){
    let $frm = $('#registForm');
    let contents = editor.getHTML();

    if($frm.find('#companyCd').val() == '') {
        alert('회사코드는 필수항목입니다.');
        $frm.find('#companyCd').focus();
        return;
    }
    if($frm.find('#title').val() == '') {
        alert('제목은 필수항목입니다.');
        $frm.find('#title').focus();
        return;
    }
    if(contents == '') {
        alert('내용은 필수항목입니다.');
        $frm.find('#contents').focus();
        return;
    }

    $frm.find('#bbsTypeCd').val('NOTICE');
    $frm.find('#contents').val(contents);

    document.querySelector('#files').files = dataTranster.files;

    let form = $("#registForm")[0];
    let formData = new FormData(form);

    let bbsSeq = $frm.find('#bbsSeq').val();
    let url = bbsSeq == '0' ? '/kmacvoc/v1/bbs/add' : '/kmacvoc/v1/bbs/modify';

	Util.showLoading();
    $.ajax({
        method: "POST",
        url: url,
        data: formData,
        contentType: false,
        processData: false
    })
    .done(function(result) {
		Util.hideLoading();
        if(result && result.messageCode == '0000'){
            alert(result.data.rtnMessage);
            goList();
        }
    }).fail(function(xhr, status, error) {
		Util.hideLoading();
	});
}

/**
 * 데이터 삭제
 */
let deleteData = function(){

    if(!confirm('공지사항 정보를 삭제하시겠습니까?')) return;

    let bbsSeq = $('#registForm').find('#bbsSeq').val();
    let url = '/kmacvoc/v1/bbs/remove/'+bbsSeq;

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
 * 파일삭제
 */
let deleteFile = function(fileSeq){
    if(!confirm('파일을 삭제하시겠습니까?')) return;

    let url = '/kmacvoc/v1/file/remove/'+fileSeq;

    AjaxUtil.post(
        url,
        {},
        function(result){
            if(result && result.messageCode == '0000'){
                $('.file-list').find('#'+fileSeq).remove();
            }
        }
    );
}

/**
 * 댓글창 오픈
 */
let popAddComments = function(){
    $('.modal-comments').find('#comments').html('');
    $('.ui.modal-comments').modal('show');
}

/**
 * 댓글상세 오픈
 */
let popDtlComments = function(bbsCommentsSeq){
    AjaxUtil.get(
        '/kmacvoc/v1/bbs/comments/'+bbsCommentsSeq,
        {},
        function(result){
            if(result && result.data){
                let d = result.data;
                $('.modal-comments').find('#bbsCommentsSeq').val(d.bbsCommentsSeq);
                $('.modal-comments').find('#comments').val(d.comments);
            }
        }
    );

    $('.ui.modal-comments').modal('show');
}

/**
 * 댓글 저장
 */
let saveComments = function(){
    let comments = $('.modal-comments').find('#comments').val();
    let bbsCommentsSeq = $('.modal-comments').find('#bbsCommentsSeq').val();
    let param = {"bbsSeq":$bbsSeq, "bbsCommentsSeq":bbsCommentsSeq, "comments":comments};
    let url = bbsCommentsSeq > 0 ? '/kmacvoc/v1/bbs/comments/modify' : '/kmacvoc/v1/bbs/comments/add';

    AjaxUtil.post(
        url,
        JSON.stringify(param),
        function(result){
            if(result && result.messageCode == '0000'){
                alert(result.data.rtnMessage);
                loadGrid();
                $('.ui.modal-comments').modal('hide');
            }
        }
    );
}

/**
 * 댓글 삭제
 */
let deleteComments = function(bbsCommentsSeq){

    if(!confirm('댓글을 삭제 하시겠습니까?')) return;

    let url = '/kmacvoc/v1/bbs/comments/remove/'+bbsCommentsSeq;

    AjaxUtil.post(
        url,
        {},
        function(result){
            if(result && result.messageCode == '0000'){
                loadGrid();
            }
        }
    );
}
