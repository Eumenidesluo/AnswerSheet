var answer = new Vue({
    el: "#vue",
    data: {
        resultInfo:[{
            answers:'',
            time:'',
            checkout:''
        }],
        pageCount:0
    }
});
// $('.M-box4').pagination({
//     pageCount: 50,
//     jump: true,
//     showData: 10,
//     callback: function (api) {
//         var data = {
//             page: api.getCurrent(),
//             row: api.showData
//         };
//         $.getJSON('http://localhost:8080/resultHistory', data, function (json) {
//             console.log(json);
//         })
//     }
// });

$(function () {
    InitResultHistory();
    createPagination();
});
function InitResultHistory() {
    $.ajax({
        url: "/resultHistory",
        type: 'POST',
        async: false,
        dataType: "json",
        data: {"page":1,"row":10},
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
var container = $('#pagination');
function createPagination() {
    var options = {
        pageCount: answer.pageCount,
        coping:true,
        showGoInput: true,
        showGoButton: true,
        callback: function (api) {
            $.ajax({
                url: "/resultHistory",
                type: 'POST',
                async: false,
                dataType: "json",
                data: {"page":api.getCurrent(),"row":10},
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
