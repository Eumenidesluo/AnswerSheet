var answer = new Vue({
    el: "#vue",
    data: {
        resultInfo:[{
            answers:'',
            time:'',
            name:'',
            id:''
        }],
        pageCount:0
    }
});

var searchVal = new Vue({
    el: "#searchVal",
    data: {


    }
});
$(function () {
    InitResultHistory();
    createPagination();
});
function InitResultHistory() {
    postData(1);
}
var container = $('#pagination');
function createPagination() {
    var options = {
        pageCount: answer.pageCount,
        coping:true,
        showGoInput: true,
        showGoButton: true,
        callback: function (api) {
            postData(api.getCurrent());
        },
        afterGoButtonOnClick: function () {
            window.console && console.log(pagination);
            getPageData();
        },
        afterPreviousOnClick: function () {
            getPageData();
        },
        afterNextOnClick: function () {
            getPageData();
        },
        afterPageOnClick: function () {
            getPageData();
        }

    };
    container.pagination(options);
    return container;
}
function postData(page) {
    $.ajax({
        url: "/search",
        type: 'POST',
        async: false,
        dataType: "json",
        data: {"page":page,"row":10,"name":$("#name")[0].value,"uploader":$("#uploader")[0].value},
        beforeSend: function () {
            console.log("正在进行，请稍候");
        },
        success: function (r) {
            if (r.code === 0) {
                console.log("成功" + r);
                answer.resultInfo = r.resultPage;
                answer.pageCount=r.totalPage;
            } else {
                console.log("失败");
            }
        },
        error: function (r) {
            if (r.code === 0) {
                console.log("成功" + r);
                answer.resultInfo = r.resultPage;
                answer.pageCount=r.totalPage;
            } else {
                console.log("失败");
            }
        }
    });
}