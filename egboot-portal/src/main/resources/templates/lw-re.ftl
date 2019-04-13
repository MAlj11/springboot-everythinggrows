<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="description" content="">
    <meta name="keywords" content="">
    <meta name="viewport"
          content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>"枝丫"注册</title>

    <!-- Set render engine for 360 browser -->
    <meta name="renderer" content="webkit">

    <!-- No Baidu Siteapp-->
    <meta http-equiv="Cache-Control" content="no-siteapp"/>

    <link rel="icon" type="image/png" href="/static/assets/i/favicon.png">

    <!-- Add to homescreen for Chrome on Android -->
    <meta name="mobile-web-app-capable" content="yes">
    <link rel="icon" sizes="192x192" href="/static/assets/i/app-icon72x72@2x.png">

    <!-- Add to homescreen for Safari on iOS -->
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-title" content="Amaze UI"/>
    <link rel="apple-touch-icon-precomposed" href="/static/assets/i/app-icon72x72@2x.png">

    <!-- Tile icon for Win8 (144x144 + tile color) -->
    <meta name="msapplication-TileImage" content="/static/assets/i/app-icon72x72@2x.png">
    <meta name="msapplication-TileColor" content="#0e90d2">

    <!-- SEO: If your mobile URL is different from the desktop URL, add a canonical link to the desktop page https://developers.google.com/webmasters/smartphone-sites/feature-phones -->
    <!--
    <link rel="canonical" href="http://www.example.com/">
    -->

    <link rel="stylesheet" href="/static/assets/css/amazeui.min.css">
    <link rel="stylesheet" href="/static/assets/css/app.css">
</head>
<body>
<header>
    <div class="log-re">
        <button type="button" class="am-btn am-btn-default am-radius log-button"
                onclick="window.location.href='/loginPage.html'">登 录
        </button>
    </div>
</header>

<div class="log">
    <div class="am-g">
        <div class="am-u-lg-3 am-u-md-6 am-u-sm-8 am-u-sm-centered log-content">
            <h1 class="log-title am-animation-slide-top">"枝丫"注册</h1>
            <br>
            <form class="am-form" id="re-form" action="/register.html" method="post">
                <div class="am-input-group am-radius am-animation-slide-left">
                    <input type="text" id="reEmail" name="reEmail" class="am-radius" data-validation-message="请输入正确邮箱地址"
                           placeholder="邮箱" required/>
                    <span class="am-input-group-label log-icon am-radius"><i
                            class="am-icon-user am-icon-sm am-icon-fw"></i></span>
                </div>
                <br>
                <div class="am-input-group am-animation-slide-left log-animation-delay">
                    <input type="username" id="reUsername" name="reUsername" class="am-form-field am-radius log-input"
                           placeholder="用户名"/>
                    <span class="am-input-group-label log-icon am-radius"><i
                            class="am-icon-lock am-icon-sm am-icon-fw"></i></span>
                </div>
                <br>
                <div class="am-input-group am-animation-slide-left log-animation-delay">
                    <input type="password" id="rePassword" name="rePassword" class="am-form-field am-radius log-input"
                           placeholder="密码"/>
                    <span class="am-input-group-label log-icon am-radius"><i
                            class="am-icon-lock am-icon-sm am-icon-fw"></i></span>
                </div>
            <#--<br>-->
            <#--<div class="am-input-group am-animation-slide-left log-animation-delay-a">-->
            <#--<input type="password" data-equal-to="#rePassword" class="am-form-field am-radius log-input" placeholder="确认密码" data-validation-message="请确认密码一致" required>-->
            <#--<span class="am-input-group-label log-icon am-radius"><i class="am-icon-lock am-icon-sm am-icon-fw"></i></span>-->
            <#--</div>-->
                <br>
                <div class="am-input-group am-animation-slide-left log-animation-delay">
                    <input type="text" id="reVerify" name="reVerify" class="am-form-field am-radius log-input"
                           placeholder="请输入验证码"/>
                    <span class="am-input-group-label log-icon am-radius"><i
                            class="am-icon-lock am-icon-sm am-icon-fw"></i></span>
                </div>
                <br>
                <button type="button"
                        class="am-btn am-btn-primary am-btn-block am-btn-lg am-radius am-animation-slide-bottom log-animation-delay-b"
                        id="verify_but" onclick="sendemail()">获取验证码
                </button>
                <br>
                <input type="submit"
                       class="am-btn am-btn-primary am-btn-block am-btn-lg am-radius am-animation-slide-bottom log-animation-delay-b"
                       value="注册"/>
            </form>
        </div>
    </div>
</div>

<script type="text/javascript">
    function sendemail() {
        var emaildoc = document.getElementById("reEmail");
        var remail = emaildoc.value;
        var url = "/emailVerify.html?reEmail=" + remail + "&t=";
        var xmlhttp;
        if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
            xmlhttp = new XMLHttpRequest();
        }
        else {// code for IE6, IE5
            xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
        }
        xmlhttp.onreadystatechange = function () {
            if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                alert("邮件已发送，请到邮箱查收");
            }
        }
        xmlhttp.open("GET", url + Math.random(), true);
        xmlhttp.send();
        alert("邮件已发送，请到邮箱查收")
    }
</script>

<!--[if (gte IE 9)|!(IE)]><!-->
<script src="/static/assets/js/jquery.min.js"></script>
<!--<![endif]-->
<!--[if lte IE 8 ]>
<script src="http://libs.baidu.com/jquery/1.11.3/jquery.min.js"></script>
<script src="http://cdn.staticfile.org/modernizr/2.8.3/modernizr.js"></script>
<script src="/static/assets/js/amazeui.ie8polyfill.min.js"></script>
<![endif]-->
<script src="/static/assets/js/amazeui.min.js"></script>
<script src="/static/assets/js/app.js"></script>
</body>
</html>