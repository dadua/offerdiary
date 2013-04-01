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
      height: 700px;
    }
    .carousel img {
      position: absolute;
      top: 0;
      left: 0;
      min-width: 100%;

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
		padding-top: 70px;
	}
    
	</style>
		
<%@include file="/common/pages/bodyBegin.jsp" %>
	
    <div id="myCarousel" class="carousel slide">
      <div class="carousel-inner">
        <div class="item active">
          <img src="images/slider/my_offers.jpg" align="right" alt="">
          <div class="container">
            <div class="carousel-caption">
              <h1>My Offers</h1>
               <div class="lead">
					<ul style="list-style: circle;">
						<li class="mb10">Manage Your offers</li>
						<li class="mb10">Add offers from cards to wallet</li>
					</ul>
			  </div>
              <a class="btn btn-large btn-primary" href="http://localhost:8080/offerdiary/signup.do">Sign up today</a>
            </div>
          </div>
        </div>
        <div class="item">
          <img src="images/slider/share_offer.jpg" alt="">
          <div class="container">
            <div class="carousel-caption">
              <h1>Share with Friends</h1>
               <div class="lead">
					<ul style="list-style: circle;">
						<li class="mb10">Share Offers with your Friends</li>
						<li class="mb10">Share on Facebook</li>
						<li class="mb10">Share on Email</li>
					</ul>
			  </div>
              <a class="btn btn-large btn-primary" href="http://localhost:8080/offerdiary/signup.do">Sign up today</a>			  
            </div>
          </div>
        </div>
        <div class="item">
          <img src="images/slider/offer_expiry.jpg" align="right" alt="">
          <div class="container">
            <div class="carousel-caption">
              <h1>Get Timely Notifications</h1>
               <div class="lead">
					<ul style="list-style: circle;">
						<li class="mb10">Get Expiry Notification on email</li>
						<li class="mb10">Get Offers Expiring in Next 7 days on My Offers page</li>
					</ul>
			  </div>
              <a class="btn btn-large btn-primary" href="http://localhost:8080/offerdiary/signup.do">Sign up today</a>			  
            </div>
          </div>
        </div>
        <div class="item">
          <img src="images/slider/card_offers.jpg" alt="">
          <div class="container">
            <div class="carousel-caption">
              <h1>Know Offers on your cards</h1>
               <div class="description">
					<ul style="list-style: circle;">
						<li class="mb10">Know Offers available on your cards</li>
					</ul>
			  </div>
              <a class="btn btn-large btn-primary" href="http://localhost:8080/offerdiary/signup.do">Sign up today</a>
            </div>
          </div>
        </div>            
      </div>
  
      <a class="left carousel-control" href="#myCarousel" data-slide="prev">&lsaquo;</a>
      <a class="right carousel-control" href="#myCarousel" data-slide="next">&rsaquo;</a>
    </div><!-- /.carousel -->
<%@include file="/common/pages/bodyHtmlEnd.jsp" %>
