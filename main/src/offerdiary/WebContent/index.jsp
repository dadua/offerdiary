<%@include file="/common/pages/headBegin.jsp" %>
	<title>OfferDiary : Never miss an offer!</title>
	<script type="text/javascript">
		$(function(){
                        $('#myCarousel').carousel();
			$('#newEmailSubscription').click(function(){
				var email = $('#email').val();
				if (email === '' || email.indexOf('@')===-1) {
					return;
				}
				$.post('hearMore.do', {'email': email}, function(data){
					var result = JSON.parse(data);
					if (result.success == true) {
						$('#onEmailSuccess').show();
					}
				});
			});
			$('#onEmailSuccess > .close').click(function(){
				$('#onEmailSuccess').hide();
			});
		});
	</script>
	<style>
	  .carousel .container {
      position: relative;
      z-index: 9;
    }
    
    .carousel-control {
      height: 80px;
      margin-top: 0;
      font-size: 120px;
      text-shadow: 0 1px 1px rgba(0,0,0,.4);
      background-color: transparent;
      border: 0;
      z-index: 10;
    }

    .carousel .item {
      height: 500px;
    }
    .carousel img {
      position: absolute;
      top: 0;
      left: 0;
      min-width: 100%;
      height: 500px;
    }

    .carousel-caption {
      background-color: transparent;
      position: static;
      max-width: 550px;
      padding: 0 20px;
      margin-top: 200px;
    }
    .carousel-caption h1,
    .carousel-caption .lead {
      margin: 0;
      line-height: 1.25;
      color: #fff;
      text-shadow: 0 1px 1px rgba(0,0,0,.4);
    }
    .carousel-caption .btn {
      margin-top: 10px;
    }
    
    .someMargin {
    	margin: 12px;
    }
    
   

    
    <%@include file="/common/css/layout.css" %>
	 body {
		padding-top: 73px;
	}
    
	</style>
		
<%@include file="/common/pages/bodyBegin.jsp" %>
	
    <div id="myCarousel" class="carousel slide">
      <div class="carousel-inner">
        <div class="item active">
          <img src="images/slider/slide-01.jpg" alt="">
          <div class="container">
            <div class="carousel-caption">
              <h1>Example headline.</h1>
              <p class="lead">Cras justo odio, dapibus ac facilisis in, 
egestas eget quam. Donec id elit non mi porta gravida at eget metus. 
Nullam id dolor id nibh ultricies vehicula ut id elit.</p>
              <a class="btn btn-large btn-primary" href="#">Sign up today</a>
            </div>
          </div>
        </div>
        <div class="item">
          <img src="images/slider/slide-02.jpg" alt="">
          <div class="container">
            <div class="carousel-caption">
              <h1>Another example headline.</h1>
              <p class="lead">Cras justo odio, dapibus ac facilisis in, 
egestas eget quam. Donec id elit non mi porta gravida at eget metus. 
Nullam id dolor id nibh ultricies vehicula ut id elit.</p>
              <a class="btn btn-large btn-primary" href="#">Learn more</a>
            </div>
          </div>
        </div>
        <div class="item">
          <img src="images/slider/slide-03.jpg" alt="">
          <div class="container">
            <div class="carousel-caption">
              <h1>One more for good measure.</h1>
              <p class="lead">Cras justo odio, dapibus ac facilisis in, 
egestas eget quam. Donec id elit non mi porta gravida at eget metus. 
Nullam id dolor id nibh ultricies vehicula ut id elit.</p>
              <a class="btn btn-large btn-primary" href="#">Browse gallery</a>
            </div>
          </div>
        </div>
      </div>
      <a class="left carousel-control" href="#myCarousel" data-slide="prev">&lsaquo;</a>
      <a class="right carousel-control" href="#myCarousel" data-slide="next">&rsaquo;</a>
    </div><!-- /.carousel -->

	
		<div class="container">
			<div class="row">
				<div class="span4" >
					<img src="images/icons/policycatalog.png" class="img-polaroid img-rounded" style="height: 240px;">
				</div>
				<div class="span4 ">
					<img src="images/icons/tagcatalog.png" class="img-polaroid img-rounded" style="height: 240px;">
				</div>
				<div class="span4 ">
					<img src="images/icons/HipsPolicyMigration.png" class="img-polaroid img-rounded" style="height: 240px;">
				</div>
			</div>
			<br>
			<br>
			<div class="row">
				<div class="span3 offset3 thumbnail">
					<img src="images/app_stores.png">
				</div>
				<div class="thumbnail span3 feedbackFb">
					<div class="someMargin">
					Drop in your thoughts at: <a target="_blank" href="https://www.facebook.com/offerdiary">facebook.com/offerdiary</a>
					</div>
				</div>
			</div>
				
		</div>
<%@include file="/common/pages/bodyHtmlEnd.jsp" %>
