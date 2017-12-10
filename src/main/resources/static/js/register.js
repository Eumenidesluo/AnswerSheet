function onClickRegister() {
    if (!checkInput($("#username")[0])||!checkInput($("#password")[0])||!checkInput($("#repeatPassword")[0]))
        return;
    var username=$("#username")[0].value;
    var password=$("#password")[0].value;
    var repeat=$("#repeatPassword")[0].value;
    if(password!=repeat){
        alert("两次输入密码不一致");
        return;
    }
    $.ajax({
        url : "/register",
        type : 'POST',
        async: false,
        dataType:"json",
        data : {"username":username,"password":CryptoJS.SHA256(password).toString()},
        beforeSend:function(){
            console.log("正在进行，请稍候");
        },
        success : function(r) {
            if(r.code===0){
                alert("注册成功！");
                window.location.href="loginPage";
            }else if (r.code===5001){
                alert("账户名已存在");
            }else
                alert("未知异常，请重试");
        },
        error : function(r) {
            alert("未知异常，请重试");
        }
    });
}
function checkInput(obj) {
    var value=obj.value;
    if (value==""){
        obj.style.cssText="border-color: red";
        return false;
    }else {
        obj.style.borderColor="#b0bec5";
        return true;
    }

}