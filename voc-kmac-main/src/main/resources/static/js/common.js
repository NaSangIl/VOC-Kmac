//공통 컴포넌트 실행
$(document).ready(function() {
    semanticComponents();
    loginFocusEvnt();
});

/*
* Gnb
* 23/12/02 로직 변경.
*/
const menuDepth1 = document.querySelectorAll('.menu-depth1>li'); 
const menuDepth2Box = document.querySelectorAll('.menu-depth2');
const menuDepth2Boxes = [].map.call(menuDepth2Box, function(obj) {
    return obj;
});
let menuDepth2BoxMaxHeight = 0;
const subMenuBox = document.querySelector('.sub-depth-area');
let boxValues = [];

//메뉴 활성화 시 적용 스타일
function acitveMenu() {
    menuDepth2Box.forEach((box) => {
        boxValues.push(box.offsetHeight);
    });
    menuDepth2BoxMaxHeight = Math.max.apply(null, boxValues);

    menuDepth2Boxes.map((box) => {
        box.style.height = menuDepth2BoxMaxHeight+"px";
        subMenuBox.style.height = menuDepth2BoxMaxHeight+"px";
        box.style.visibility = "visible";
        
    });
}
//메뉴 비활성화 시 적용 스타일
function inactiveMenu() {
    menuDepth2Boxes.map((box) => {
        box.style.height = "auto";
        subMenuBox.style.height = "auto";
        box.style.visibility = "hidden";
    });
}
menuDepth1.forEach((menu) => {
    menu.addEventListener('mouseover', () => {
        timer = setTimeout(() => {
            menu.classList.add('is-active')
            acitveMenu();
        }, 100);
    });
    menu.addEventListener('mouseout', () => {
        menu.classList.remove("is-active");
        clearTimeout(timer);
    });
});
document.querySelector('.menu-depth1')?.addEventListener('mouseleave', () => {
    inactiveMenu();
});

/*
* datatables
*/
//테이블 기본 옵션 정의
$.extend(true, $.fn.dataTable.defaults, {
    searching: false,
    lengthChange: false,
    columnDefs: [
        {
            orderable: false,
            targets: "_all"
        },
    ],
    language: {
        info:'전체 : _TOTAL_',
    },
  order: [],
});
//개별 스타일 정의
const table = new DataTable(".table-area table", {});
//테이블 클릭 이벤트
table.on('click', 'tbody tr', (e) => {
    let classList = e.currentTarget.classList;
 
    if (classList.contains('selected')) {
        classList.remove('selected');
    }
    else {
        table.rows('.selected').nodes().each((row) => row.classList.remove('selected'));
        classList.add('selected');
    }
});


/*
* Semantic UI components 함수
*/
function semanticComponents() {
    //체크박스
    $('.ui.checkbox').checkbox();
    //라디오버튼
    $('.selection.dropdown').dropdown();
    //기본 캘린더
    $('.ui.calendar').calendar({
    type: 'date',
    formatter: {
        date: 'YYYY-MM-DD',
        dayHeader: 'YYYY.MMMM',
    },
    startCalendar: $('#rangestart'),
    text: {
        months: ['01', '02', '03', '04', '05', '06', '07', '08', '09', '10', '11', '12'],
        monthsShort: ['01', '02', '03', '04', '05', '06', '07', '08', '09', '10', '11', '12'],
        }
    });
    //검색 영역 내 범위 설정 캘린더
    $('#rangestart').calendar({
        type: 'date',
        formatter: {
            dayHeader: 'YYYY.MMMM',
            date: 'YYYY-MM-DD'
        },
        endCalendar: $('#rangeend'),
        text: {
            months: ['01', '02', '03', '04', '05', '06', '07', '08', '09', '10', '11', '12'],
            monthsShort: ['01', '02', '03', '04', '05', '06', '07', '08', '09', '10', '11', '12'],
          }
      });
      $('#rangeend').calendar({
        type: 'date',
        formatter: {
            date: 'YYYY-MM-DD',
            dayHeader: 'YYYY.MMMM',
        },
        startCalendar: $('#rangestart'),
        text: {
            months: ['01', '02', '03', '04', '05', '06', '07', '08', '09', '10', '11', '12'],
            monthsShort: ['01', '02', '03', '04', '05', '06', '07', '08', '09', '10', '11', '12'],
          }
      });
      //모달
      $('.ui.modal').modal('attach events', '.test.button', 'show');
      //탭
      $('.menu .item').tab();
      //아코디언(타이틀+컨텐츠 접힘펼침에 주로 쓰임)
      $('.ui.accordion').accordion({
            selector: {
                trigger: '.title h3',
            },
            exclusive:false
        });
}

/*
* login focus event
*/
function loginFocusEvnt() {
    const loginFields = document.querySelectorAll('.login-area>.field>input');

    loginFields.forEach((menu) => {
        menu.addEventListener('focusin', (e) => {
            menu.parentElement.classList.add("on-focus");
        });
        menu.addEventListener('focusout', (e) => {
            menu.parentElement.classList.remove("on-focus");
        });
    });
}

/*
* file upload
*/
const handler = {
init() {
    const fileInputs = document.querySelectorAll('.file-btn>input');
    const previews = document.querySelectorAll('.file-list');

    fileInputs.forEach((fileInput, index) => {
        fileInput.addEventListener('change', event => {
            const preview = previews[index];
            const files = Array.from(fileInput.files);

            files.forEach(file => {
                //[dev]_a link href에 파일 다운로드 경로 입력이 필요합니다.
                preview.innerHTML += `
                <li id="${file.lastModified}">
                    <a href='${file.name}' title='${file.name}' class='file-name' download>${file.name}</a>
                    <button data-index='${file.lastModified}' class='btn-file-remove'></button>
                </li>`;
            });
        });
    });
},

removeFile: () => {
    document.addEventListener('click', (e) => {
    if(e.target.className !== 'btn-file-remove') return;
    const removeTargetId = e.target.dataset.index;
    const removeTarget = document.getElementById(removeTargetId);
    const files = document.querySelector('.file-btn>input').files;
    const dataTranster = new DataTransfer();

    Array.from(files)
        .filter(file => file.lastModified != removeTargetId)
        .forEach(file => {
        dataTranster.items.add(file);
    });

    document.querySelector('.file-btn>input').files = dataTranster.files;

    removeTarget.remove();
})
}
};
handler.init();
handler.removeFile();

/*
* image file upload
*/
function handleImageUpload() {
    const uploadBtn = document.getElementById('uploadBtn');
    const deleteBtn = document.getElementById('deleteBtn');
    const previewBtn = document.getElementById('previewBtn');
    const imageNameInput = document.getElementById('imageName');
    const previewImage = document.getElementById('previewImage');

    // 파일 찾기
    uploadBtn.addEventListener('click', () => {
        const input = document.createElement('input');
        input.type = 'file';
        // [dev] 이미지 파일만 업로드 허용
        input.accept = 'image/*';
        input.addEventListener('change', (event) => {
            const file = event.target.files[0];
            imageNameInput.value = file.name;
            
            // 미리 보기 버튼 보이기 -> [dev] 꼭 'inline-flex'여야 함
            previewBtn.style.display = 'inline-flex';
            const reader = new FileReader();
            reader.onload = (e) => {
                previewImage.src = e.target.result;
            };
            reader.readAsDataURL(file);
        });
        input.click();
    });

    // 이미지명 초기화 및 미리 보기 버튼 숨기기
    deleteBtn.addEventListener('click', () => {
        imageNameInput.value = '';
        previewBtn.style.display = 'none';
    });

    // 미리보기 팝업 열기
    previewBtn.addEventListener('click', () => {
        const imageName = imageNameInput.value;
        if (imageName !== '') {
            // [dev] 이미지 경로 설정 필요
            // const imagePath = '파일경로/' + imageName;
            // previewImage.src = imagePath;
            $('#imagePreviewModal').modal('show');
        } else {
            alert('이미지를 먼저 추가해주세요.');
        }
    });
}

// 합친 이벤트 함수 호출
handleImageUpload();