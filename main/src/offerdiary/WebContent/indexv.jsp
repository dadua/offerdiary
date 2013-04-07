<%@include file="/common/pages/headBegin.jsp" %>
	<title>OfferDiary : Never miss an offer!</title>
	<script type="text/javascript">
		var it = it || {};
		it.scroll = it.scroll || {};
		it.scroll.did = false;
		$(function(){
		    $('.goDown').click(function(e){
				var id = $(this).attr('id');
					//locationToScroll = /go-to-(.+)/.exec(id)[1];
		        $('html, body').animate({
		            scrollTop: $("#"+id).offset().top
		        }, 1500);
		    });
		    
		    $('.navScroller > a').click ( function (e) {
				e.preventDefault();
				var href = $(this).attr('href'),
					scrollLocation = 0;
				if (href !== '#') {
				    scrollLocation = $(href).offset().top;
				}
		        $('html, body').animate({
		            scrollTop: scrollLocation
		        }, 1500);
		    });
		    
		    
		    var goToShare$ = $('#go-to-share'),
		    	goToNotifications$ = $('#go-to-notifications'),
		    	goToCards$ = $('#go-to-cardOffers'),
		    	window$ = $(window);
		    
		    window$.scroll(function (e) {
				it.scroll.did = true;
		    });
		    
		    var scrollChecker = function () {
				var windowTop = window$.scrollTop(),
					offset = 95,
					shareTop = goToShare$.offset().top - offset,
					notificationsTop = goToNotifications$.offset().top - offset,
					cardsTop = goToCards$.offset().top - offset;
				
				if (windowTop>=0 && windowTop < shareTop) {
				    $('.navScroller').removeClass('active');
				    $('.myOffersNav').addClass('active');
				} else if (windowTop>= shareTop && windowTop < notificationsTop) {
				    $('.navScroller').removeClass('active');
				    $('.shareNav').addClass('active');
				} else if (windowTop>= notificationsTop && windowTop < cardsTop) {
				    $('.navScroller').removeClass('active');
				    $('.notificationsNav').addClass('active');
				} else if (windowTop>= cardsTop) {
				    $('.navScroller').removeClass('active');
				    $('.cardOffersNav').addClass('active');
				}
			
		    };
		    
		    scrollChecker();
		    
		    setInterval(function() {
				if (it.scroll.did) {
				    it.scroll.did = false;
				    scrollChecker();
				}
		    }, 250)
		});
                
	</script>
	<style>
    .someMargin {
    	margin: 12px;
    }
    
    <%@include file="/common/css/layout.css" %>
	 body {
		padding-top: 70px;
	}
	
	.lead > .explanation {
	    line-height: 2em;
    }
    .featureItem {
    	margin-top: 7%;
    	margin-bottom: 10%;
    	/*border: 1px solid #DDDDDD;*/
    }
    
    .goDown {
	    background-color: #E5E5E5;
	    background-image: linear-gradient(to bottom, #F2F2F2, #E5E5E5);
	    background-repeat: repeat-x;
	    border-radius: 0 0 8px 8px;
	    padding: 5px 10px 2px;
	    position: absolute;
	    right: 48%;
	    top: 50%;
	    cursor: pointer;
    }
    
    .correct {
		color: green;
		font-size: 1.5em;
		margin-right: 3%;
		text-align: center;
		vertical-align: middle;
	}
	
	.scrollFeatures {
		top: 35%;
		border-radius: 0px 6px 6px 0px;
		padding: 0;
		border: 1px solid #DDDDDD;
		position: fixed;
		width: 180px;
		left: -5px;
		background-color: #FAFAFA;
	    background-image: linear-gradient(to bottom, #FFFFFF, #F2F2F2);
	    background-repeat: repeat-x;
	}
	.scroller > .divider {
		margin: 2px 1px;
	}
	</style>
	
	<%--
</head>
<body data-target=".scrollFeatures" data-spy="scroll" data-offset="90">
	<%@include file="/common/pages/navHeader.jsp" %>
	<%@include file="/common/pages/fb/fbInitAsyncBelowBodyJs.jsp" %>
	 --%>

<%@include file="/common/pages/bodyBegin.jsp" %>
	
	<div class="scrollFeatures">
		<ul class="nav scroller nav-list">
			<li class="nav-header">Features</li>
			<li class="divider"></li>
			<li class="navScroller myOffersNav"><a href="#">My Offers <i class="pull-right icon-arrow-right icon-white"></i></a></li>
			<li class="divider"></li>
			<li class="navScroller shareNav"><a  href="#go-to-share">Share With Friends<i class="pull-right icon-arrow-right icon-white"></i></a></li>
			<li class="divider"></li>
			<li class="navScroller notificationsNav"><a  href="#go-to-notifications">Notifications<i class="pull-right icon-arrow-right icon-white"></i></a></li>
			<li class="divider"></li>
			<li class="navScroller cardOffersNav"><a  href="#go-to-cardOffers">Card Offers<i class="pull-right icon-arrow-right icon-white"></i></a></li>
			
		</ul>
	</div>
	<div id="features">
	    <div id="myOffers" class="featureItem">
	        <div class="container">
		        <div class="row">
		            <div class="description span4">
		            	<br>
		            	<br>
		                <h1 class="bluishText">My Offers</h1>
		                <div class="lead">
	                        <div class="explanation"><span class="correct">&#10004;</span>Manage Your offers</div>
	                        <div class="explanation"><span class="correct">&#10004;</span>Add offers from cards to wallet</div>
		                </div>
		                <a class="btn btn-large btn-primary" href="signup.do">Sign up today</a>
		            </div>
		        	<div class="span8">
				        <img src="images/home/my_offers.png" alt="My Offers">
			        </div>
		        </div>
		        <div class="row" style="position:relative">
		        	<hr>
		        	<div id="go-to-share" class="goDown">
		        		<i class="icon-chevron-down icon-white"></i>
		        	</div>
		        </div>
		     </div>
	    </div>
	    <div id="share" class="featureItem">
		    <div class="container">
		        <div class="row">
		            <div class="description span4">
		                <h1 class="bluishText">Share with Friends</h1>
		                <div class="lead">
	                        <div class="explanation"><span class="correct">&#10004;</span>Share Offers with your Friends</div>
	                        <div class="explanation"><span class="correct">&#10004;</span>Share on Facebook</div>
	                        <div class="explanation"><span class="correct">&#10004;</span>Share on Email</div>
		                </div>
		                <a class="btn btn-large btn-primary" href="signup.do">Sign up today</a>			  
		            </div>
			        <div class="span8">
				        <img src="images/home/share_offer.png" alt="">
			        </div>
		        </div>
		        <div class="row" style="position:relative">
		        	<hr>
		        	<div id="go-to-notifications" class="goDown">
		        		<i class="icon-chevron-down icon-white"></i>
		        	</div>
		        </div>
		    </div>
	    </div>
	    <div id="notifications" class="featureItem">
	        <div class="container">
		        <div class="row">
		            <div class="description span4">
		                <h1 class="bluishText">Notifications</h1>
		                <div class="lead">
	                        <div class="explanation"><span class="correct">&#10004;</span>Offer Expiry Notification on email</div>
	                        <div class="explanation"><span class="correct">&#10004;</span>Offers Expiring in Next 7 days</div>
		                </div>
		                <a class="btn btn-large btn-primary" href="signup.do">Sign up today</a>			  
		            </div>
			        <div class="span8">
				        <img src="images/home/offer_expiry.png" alt="">
			        </div>
		        </div>
		        <div class="row" style="position:relative">
		        	<hr>
		        	<div id="go-to-cardOffers" class="goDown">
		        		<i class="icon-chevron-down icon-white"></i>
		        	</div>
		        </div>
		    </div>
	    </div>
	    <div id="cardOffers" class="featureItem">
	        <div class="container">
		        <div class="row">
		            <div class="description span4">
		                <h1 class="bluishText">Card Offers</h1>
		                <div class="lead">
	                        <div class="explanation"><span class="correct">&#10004;</span>Know Offers available on your cards</div>
		                </div>
		                <a class="btn btn-large btn-primary" href="signup.do">Sign up today</a>
		            </div>
			        <div class="span8">
				        <img src="images/home/card_offers.png" alt="">
			        </div>
		        </div>
	        </div>
	    </div>            
    </div>
<%@include file="/common/pages/bodyHtmlEnd.jsp" %>
