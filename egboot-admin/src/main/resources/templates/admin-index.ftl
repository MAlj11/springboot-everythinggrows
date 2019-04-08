<!doctype html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="description" content="">
  <meta name="keywords" content="">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
  <title>枝丫</title>
  <meta name="renderer" content="webkit">
  <meta http-equiv="Cache-Control" content="no-siteapp"/>
  <!--<link rel="icon" type="image/png" href="assets/i/favicon.png">-->
  <meta name="mobile-web-app-capable" content="yes">
  <link rel="icon" sizes="192x192" href="http://192.168.0.158:8080/static/assets/i/favicon.png">
  <meta name="apple-mobile-web-app-capable" content="yes">
  <meta name="apple-mobile-web-app-status-bar-style" content="black">
  <meta name="apple-mobile-web-app-title" content="枝丫"/>
  <link rel="apple-touch-icon-precomposed" href="http://192.168.0.158:8080/static/assets/i/favicon.png">
  <meta name="msapplication-TileImage" content="http://192.168.0.158:8080/static/assets/i/favicon.png">
  <meta name="msapplication-TileColor" content="#0e90d2">
  <link rel="stylesheet" href="http://192.168.0.158:8080/static/assets/css/amazeui.min.css">
  <link rel="stylesheet" href="http://192.168.0.158:8080/static/assets/css/app.css">
</head>

<body id="blog">

<header class="am-g am-g-fixed blog-fixed blog-text-center blog-header">
    <div class="am-u-sm-8 am-u-sm-centered">
        <img width="200" src="http://192.168.0.158:8080/static/assets/i/logo.jpg"/>
        <h2 class="am-hide-sm-only">枝丫</h2>
    </div>
</header>
<hr>
<!-- nav start -->
<nav class="am-g am-g-fixed blog-fixed blog-nav">
<button class="am-topbar-btn am-topbar-toggle am-btn am-btn-sm am-btn-success am-show-sm-only blog-button" data-am-collapse="{target: '#blog-collapse'}" ><span class="am-sr-only">导航切换</span> <span class="am-icon-bars"></span></button>

  <div class="am-collapse am-topbar-collapse" id="blog-collapse">
    <ul class="am-nav am-nav-pills am-topbar-nav">
    <#if tokenVertify>
          <li class="am-active">
              <a href="/admin/logout.html" class="layout-user-downmenu-link" data-mtype="wmz_public_grzx_myorder">注销</a>
          </li>
    <#else>
          <li class="am-active">
              <a href="/admin/loginPage.html" class="layout-user-downmenu-link">立即登录</a>
          </li>
    </#if>

    </ul>
    <form class="am-topbar-form am-topbar-right am-form-inline" role="search" action="/search.html" >
      <div class="am-form-group">
        <input type="text" class="am-form-field am-input-sm" name="searchCon" placeholder="搜索">
      </div>
    </form>
  </div>
</nav>
<hr>
<!-- nav end -->

<!-- content srart -->
<div class="am-g am-g-fixed blog-fixed">
     <div class="am-u-md-8 am-u-sm-12">

         <h1 class="blog-text-center"><a href="/admin/index/article">文章管理</a></h1>
         <hr>
         <h1 class="blog-text-center"><a href="/admin/index/forum?perPage=1">论坛管理</a></h1>
         <hr>
         <h1 class="blog-text-center"><a href="/admin/user">用户管理</a></L></h1>


     </div>
</div>
<!-- content end -->

  <footer class="blog-footer">
    <div class="am-g am-g-fixed blog-fixed am-u-sm-centered blog-footer-padding">
        <div class="am-u-sm-12 am-u-md-4- am-u-lg-4">
            <h3>关于<枝丫></h3>
            <p class="am-text-sm"><枝丫>包含着很多你喜欢的内容，我们希望每一个来到<枝丫>的朋友都会有所收获。</p>
        </div>
        <div class="am-u-sm-12 am-u-md-4- am-u-lg-4">
            </p>
            <p>我们追求卓越，然时间、经验、能力有限。<枝丫>有很多不足的地方，希望大家包容、不吝赐教，给我们提意见、建议。感谢你们！</p>
        </div>
        <div class="am-u-sm-12 am-u-md-4- am-u-lg-4">
             
        </div>
    </div>    
    <div class="blog-text-center"><枝丫></div>
      <div class="blog-text-center">京ICP备19009766号</div>
  </footer>





<!--[if (gte IE 9)|!(IE)]><!-->
<script src="http://192.168.0.158:8080/static/assets/js/jquery.min.js"></script>
<!--<![endif]-->
<!--[if lte IE 8 ]>
<script src="http://libs.baidu.com/jquery/1.11.3/jquery.min.js"></script>
<script src="http://cdn.staticfile.org/modernizr/2.8.3/modernizr.js"></script>
<script src="http://192.168.0.158:8080/static/assets/js/amazeui.ie8polyfill.min.js"></script>
<![endif]-->
<script src="http://192.168.0.158:8080/static/assets/js/amazeui.min.js"></script>
</body>
</html>