$(function () {
    // 초기 설정 및 수행
    init();
});

/**
 * 초기 설정 및 수행 내용
 */
let init = function(){
    let $frm = $('.search-area');

    // button 연결 ----------------
    $('.btn-search').on('click', function(){ searchData(); });

    // dropbox data setting ----------------
    DropdownUtil.makeYearList($frm.find('.d-year'));        // 년도
    DropdownUtil.makeMonthList($frm.find('.d-month'));      // 월
    DropdownUtil.makeDayList($frm.find('.d-day'));          // 일

	localStorage.removeItem("queryString");
    setTimeout(function() {
        // 캐쉬값 존재 시 조회조건 셋팅
        let queryString = JSON.parse(localStorage.getItem("queryString"));
        let y,m,d;
        if(!ObjectUtil.isEmpty(queryString)) {
            y = queryString.y;
            m = queryString.m;
            d = queryString.d;
        } else {
            let today = Util.getToday();
            y = today.substring(0,4);
            m = today.substring(4,6);
            d = today.substring(6,8);
        }

        $('.d-year').dropdown('set selected', y);
        $('.d-month').dropdown('set selected', m);
        $('.d-day').dropdown('set selected', d);
        searchData();
    }, 200);

    // event 연결 ----------------

    //년선택 변경시
    $frm.find('.d-year').on('change', function(){
        if(ObjectUtil.isEmpty($('#year').val())){
            $('.d-month').dropdown('clear');
            $('.d-day').dropdown('clear');
        }
    });

    //월선택 변경시
    $frm.find('.d-month').on('change', function(){
        if(ObjectUtil.isEmpty($('#month').val())){
            $('.d-day').dropdown('clear');
        }
    });

    //공지사항 > MORE 선택시 공지사항 화면으로이동
    $('.btn-more').on('click', function(){
        goPage('/bbs/bbslist');
    });
}

let searchData = function() {
    let companyCd = $SessionInfo.getCompanyCd();
    let y = $('#year').val();
    let m = $('#month').val();
    let d = $('#day').val();

    if(ObjectUtil.isEmpty(y)){
        alert('년도를 선택해 주세요.');
        return;
    }
    let regDtStart = y + '-' + (ObjectUtil.isEmpty(m) ? '01' : m) + '-' + (ObjectUtil.isEmpty(d) ? '01' : d);
    let regDtEnd = y + '-' + (ObjectUtil.isEmpty(m) ? '12' : m);
    regDtEnd = regDtEnd + '-' + (ObjectUtil.isEmpty(d) ? Util.getLastday(regDtEnd) : d);
    let param = {'companyCd':companyCd, 'regDtStart':regDtStart, 'regDtEnd':regDtEnd};
    localStorage.setItem("queryString", JSON.stringify({'y':$('#year').val(), 'm':$('#month').val(), 'd':$('#day').val()}));

    //VOC현황 조회
    AjaxUtil.get(
        '/kmacvoc/v1/main/voc-states',
        param,
        function(result){
            if(result && result.data){
                let d = result.data;
                $('#complaintCnt').html(Util.numberFormat(d.complaintCnt));     //불만건수
                $('#complimentCnt').html(Util.numberFormat(d.complimentCnt));   //칭찬건수
                $('#suggestionCnt').html(Util.numberFormat(d.suggestionCnt));   //제안건수
                $('#inquiryCnt').html(Util.numberFormat(d.inquiryCnt));         //문의건수
                $('#actPeriodAvg').html(Util.numberFormat(d.actPeriodAvg));     //VOC평균처리기한
            }
        }
    );

    //공지사항 조회
    param = {'companyCd':companyCd, 'offset':0, 'length':5};
    $('#noticeList').html('');
    AjaxUtil.get(
        '/kmacvoc/v1/bbs/list',
        param,
        function(result){
            if(result && result.data.list && result.data.list.length > 0){
                let lst = result.data.list;
                lst.forEach(d => {
                    $('#noticeList').append(`
                    <li>
                        <p class="notice-title"><a href="javascript:goBbsView(${d.bbsSeq});" id="noticePopup">${d.title}</a></p>
                        <div class="notice-info">
                            <span class="notice-writer">${d.regUserNm}</span>
                            <span class="notice-date">${d.regDt}<span>
                        </div>
                    </li>`);
                });
            } else {
                $('#noticeList').append(`
                    <li>
                        <p class="notice-title"><a href="javascript:;" id="noticePopup">등록된 공지사항이 존재하지 않습니다. </a></p>
                        <div class="notice-info">
                            <span class="notice-writer"></span>
                            <span class="notice-date"><span>
                        </div>
                    </li>`);
            }
        }
    );
}

let goBbsView = function(bbsSeq) {
    localStorage.setItem("bbsSeq", bbsSeq);
    localStorage.setItem("backPage", '/main');
    goPage('/bbs/bbsview');
}