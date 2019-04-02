<!doctype html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="description" content="">
  <meta name="keywords" content="">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
  <title>万物生长</title>
  <meta name="renderer" content="webkit">
  <meta http-equiv="Cache-Control" content="no-siteapp"/>
  <link rel="icon" type="image/png" href="assets/i/favicon.png">
  <meta name="mobile-web-app-capable" content="yes">
  <link rel="icon" sizes="192x192" href="assets/i/app-icon72x72@2x.png">
  <meta name="apple-mobile-web-app-capable" content="yes">
  <meta name="apple-mobile-web-app-status-bar-style" content="black">
  <meta name="apple-mobile-web-app-title" content="Amaze UI"/>
  <link rel="apple-touch-icon-precomposed" href="assets/i/app-icon72x72@2x.png">
  <meta name="msapplication-TileImage" content="assets/i/app-icon72x72@2x.png">
  <meta name="msapplication-TileColor" content="#0e90d2">
  <link rel="stylesheet" href="assets/css/amazeui.min.css">
  <link rel="stylesheet" href="assets/css/app.css">
</head>

<body id="blog">

<header class="am-g am-g-fixed blog-fixed blog-text-center blog-header">
    <div class="am-u-sm-8 am-u-sm-centered">
        <img width="200" src="assets/i/logo.jpg"/>
        <h2 class="am-hide-sm-only">枝丫</h2>
    </div>
</header>
<hr>
<!-- nav start -->
<nav class="am-g am-g-fixed blog-fixed blog-nav">
<button class="am-topbar-btn am-topbar-toggle am-btn am-btn-sm am-btn-success am-show-sm-only blog-button" data-am-collapse="{target: '#blog-collapse'}" ><span class="am-sr-only">导航切换</span> <span class="am-icon-bars"></span></button>

  <div class="am-collapse am-topbar-collapse" id="blog-collapse">
    <ul class="am-nav am-nav-pills am-topbar-nav">
        <li ><a href="/index.html">首页      </a></li>
        <li class="am-active.html"><a href="/type/Photography.html">摄影      </a></li>
        <li><a href="/type/Internet.html">互联网      </a></li>
        <li><a href="/type/media.html">影音      </a></li>
        <li><a href="/type/feeling">感悟      </a></li>
        <li><a href="/type/ganwu">      </a></li>
        <li><a href="/type/ganwu">     </a></li>
        <li><a href="/type/ganwu">      </a></li>
        #if( $username )
          <li class="am-active">
              <a href="/myArticle.html" class="layout-user-downmenu-link" data-mtype="wmz_public_grzx_myorder">我的文章</a>
          </li>
          <li class="am-active">
              <a href="/member.html" class="layout-user-downmenu-link" data-mtype="wmz_public_grzx_myorder">个人中心</a>
          </li>
          <li class="am-active">
              <a href="/logout.html" class="layout-user-downmenu-link" data-mtype="wmz_public_grzx_myorder">注销</a>
          </li>

      #else
          <li class="am-active">
              <a href="/loginPage.html" class="layout-user-downmenu-link">立即登录</a>
          </li>
          <li class="am-active">
              <a href="/registerPage.html" target="_blank" class="layout-user-downmenu-link"
                 data-mtype="wmz_public_grzx_register">立即注册</a>
          </li>
      #end
    </ul>
    <form class="am-topbar-form am-topbar-right am-form-inline" role="search">
      <div class="am-form-group">
        <input type="text" class="am-form-field am-input-sm" placeholder="搜索">
      </div>
    </form>
  </div>
</nav>
<hr>
<!-- nav end -->
<!-- banner start -->
<div class="am-g am-g-fixed blog-fixed am-u-sm-centered blog-article-margin">
    <div data-am-widget="slider" class="am-slider am-slider-b1" data-am-slider='{&quot;controlNav&quot;:false}' >
    <ul class="am-slides">
#foreach($banneritem in $bannerList)
      <li>
            <img src="assets/i/b1.jpg">
            <div class="blog-slider-desc am-slider-desc ">
                <div class="blog-text-center blog-slider-con">
                    <h1 class="blog-h-margin"><a href="${banneritem.bannerUrl}">${banneritem.bannerPic}</a>${banneritem.bannerTitle}</h1>
                    <br><br><br><br><br><br><br>                
                </div>
            </div>
      </li>
#end
    </ul>
    </div>
</div>
<!-- banner end -->

<!-- content srart -->
<div class="am-g am-g-fixed blog-fixed">

#foreach($item in $PhotographyList)
    <div class="am-u-md-8 am-u-sm-12">
        <article class="am-g blog-entry-article">
            <div class="am-u-lg-6 am-u-md-12 am-u-sm-12 blog-entry-img">
                <img src="${item.coverPic}" alt="" class="am-u-sm-12">
            </div>
            <div class="am-u-lg-6 am-u-md-12 am-u-sm-12 blog-entry-text">
                <span><a href="" class="blog-color">${item.typeString} &nbsp;</a></span>
                <h1><a href="/index/article/detail/${item.id}.html">${item.articleName}</a></h1>
                <p>${item.title}
                </p>
                <p><a href="/index/article/detail/${item.id}.html" class="blog-continue">continue reading</a></p>
            </div>
        </article>
    </div>
#end

    <div class="am-u-md-4 am-u-sm-12 blog-sidebar">
        <div class="blog-sidebar-widget blog-bor">
            <h2 class="blog-text-center blog-title"><span>枝丫</span></h2>
   <p>每当我们对人性产生怀疑，都会置身于心狱之门。不要进去，那里的空气太污浊，那里的光线太阴暗。那里的空间太逼仄，那里的气味太血腥。就让我们立于阳光之下，平和的审视人性苍茫。以慈之心，善待身边的每一个人。怀悲之悯，拨除心中的恶根。只要我们心中还有爱，还有慈悲之念，我们就会越来越强大，就会感受到这个世界，待我们的无尽善意，与情怀。</p>
        </div>
        <div class="blog-sidebar-widget blog-bor">
            <h2 class="blog-title"><span>文章推荐</span></h2>
            <ul class="am-list">
           #foreach($reitem in $recommendList)
                <li><a href="/index/article/detail/${reitem.id}.html">${reitem.articleName}</a></li>
           #end
            </ul>
        </div>
        <div class="blog-sidebar-widget blog-bor">
            <h2 class="blog-text-center blog-title"><span>与我联系</span></h2>
            <p>
                <a href="/contact/qq.html"><span class="am-icon-qq am-icon-fw am-primary blog-icon"></span></a>

                <a href="/contact/weixin.html"><span class="am-icon-weixin am-icon-fw blog-icon"></span></a>
            </p>
        </div>
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
<script src="assets/js/jquery.min.js"></script>
<!--<![endif]-->
<!--[if lte IE 8 ]>
<script src="http://libs.baidu.com/jquery/1.11.3/jquery.min.js"></script>
<script src="http://cdn.staticfile.org/modernizr/2.8.3/modernizr.js"></script>
<script src="assets/js/amazeui.ie8polyfill.min.js"></script>
<![endif]-->
<script src="assets/js/amazeui.min.js"></script>
</body>
</html>