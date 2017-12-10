/**
 * Created by Eumenides on 2017/10/25.
 */

var answer = new Vue({
    el: "#vue",
    data:{
        resultId:"",
        answers:[],
        checkout:[],
        str:"",
        testId:[]
    }
});
function convertAnswers2Str(answers) {
    var answersStr=new Array(answers.length);
    for (var i=0;i<answers.length;i++){
        var an_single=answers[i];
        var an_sin_str=new Array(answers.length);
        for (var j=0;j<an_single.length;j++) {
            if (an_single[j] > 0 && an_single[j] < 5) {
                an_sin_str[j] = String.fromCharCode(an_single[j] + 64);
            } else {
                an_sin_str[j] = 'N';
            }
        }
        answersStr[i]=an_sin_str;
    }
    return answersStr;
}
function submitAnswer() {
    var formData = new FormData(document.getElementById("form"));
    $.ajax({
        url: "/identify",
        type: 'POST',
        async: false,
        dataType: "json",
        data: formData,
        processData: false,
        contentType: false,
        beforeSend: function () {
            console.log("正在进行，请稍候");
        },
        success: function (r) {
            if (r.code === 0) {
                console.log("成功" + r);
                answer.resultId = r.resultId;
                answer.answers = r.answers.slice(0);
                answer.checkout=r.checkout;
                answer.testId=r.testId;
            } else {
                console.log("失败");
            }
        },
        error: function (r) {
            if (r.code === 0) {
                console.log("成功" + r);
                answer.resultId = r.resultId;
                answer.answers = r.answers.slice(0);
                answer.checkout=r.checkout;
                answer.testId=r.testId;
            } else {
                console.log("失败");
            }
        }
    });
}

function detection() {
    $.ajax({
        url : "/detection",
        type : 'POST',
        async: false,
        dataType:"json",
        data : {"answerId":$("#textId").val()},
        beforeSend:function(){
            console.log("正在进行，请稍候");
        },
        success : function(r) {
            if (r.code === 0) {
                answer.str = "存在！";
            } else {
                answer.str = "不存在！";
            }
        }
    });
}