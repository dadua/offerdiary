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
		    	goToFeedback$ = $('#go-to-feedback'),
		    	window$ = $(window);
		    
		    window$.scroll(function (e) {
				it.scroll.did = true;
		    });
		    
		    var scrollChecker = function () {
				var windowTop = window$.scrollTop(),
					offset = 95,
					shareTop = goToShare$.offset().top - offset,
					notificationsTop = goToNotifications$.offset().top - offset,
					cardsTop = goToCards$.offset().top - offset,
					feedbackTop = goToFeedback$.offset().top - offset - 90;
				
				if (windowTop>=0 && windowTop < shareTop) {
				    $('.navScroller').removeClass('active');
				    $('.myOffersNav').addClass('active');
				} else if (windowTop>= shareTop && windowTop < notificationsTop) {
				    $('.navScroller').removeClass('active');
				    $('.shareNav').addClass('active');
				} else if (windowTop>= notificationsTop && windowTop < cardsTop) {
				    $('.navScroller').removeClass('active');
				    $('.notificationsNav').addClass('active');
				} else if (windowTop>= cardsTop && windowTop < feedbackTop) {
				    $('.navScroller').removeClass('active');
				    $('.cardOffersNav').addClass('active');
				} else if (windowTop >= feedbackTop) {
				    $('.navScroller').removeClass('active');
				    $('.feedBackNav').addClass('active');
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
		width: 135px;
		/*width: 9%;*/
        left: -13px;
		background-color: #FAFAFA;
	    background-image: linear-gradient(to bottom, #FFFFFF, #F2F2F2);
	    background-repeat: repeat-x;
	}
	.scroller > .divider {
		margin: 2px 1px;
	}
	
	.navScroller > a {
		padding-right: 0;
	}
	
	

	.itemContainer {
		margin-left: 90px;
	}
	
	.heading {
		font-size:3.1em;
		line-height: 1.2em;
	}
	
	.greenishText {
		color: #62C462;
	}
	
	
	.navScroller.nav-header.feedBackNav  {
		margin-top: 0px;
	}
	
	.navScroller.nav-header.feedBackNav > a {
		color: #999999;
	}
	
	.navScroller.feedBackNav.active > a {
	    color: lightgreen;
	}
	
	.feedbackLink {
		line-height: 2em;
	}

        @media (max-width: 767px) {
            .itemContainer {
                margin-left: 20px;
            }
            body {
				padding-top: 0px;
			}
        }
	
	</style>
	
	<%--
</head>
<body data-target=".scrollFeatures" data-spy="scroll" data-offset="90">
	<%@include file="/common/pages/navHeader.jsp" %>
	<%@include file="/common/pages/fb/fbInitAsyncBelowBodyJs.jsp" %>
	 --%>

<%@include file="/common/pages/bodyBegin.jsp" %>
	
	<div class="scrollFeatures hidden-phone">
		<ul class="nav scroller nav-list">
			<li class="nav-header">Features</li>
			<li class="divider"></li>
			<li class="divider"></li>
			<li class="navScroller myOffersNav"><a href="#">My Offers<i class="hide pull-right icon-arrow-right icon-white"></i></a></li>
			<li class="divider"></li>
			<li class="navScroller shareNav"><a  href="#go-to-share">Share<i class="hide pull-right icon-arrow-right icon-white"></i></a></li>
			<li class="divider"></li>
			<li class="navScroller notificationsNav"><a  href="#go-to-notifications">Notifications<i class="hide pull-right icon-arrow-right icon-white"></i></a></li>
			<li class="divider"></li>
			<li class="navScroller cardOffersNav"><a  href="#go-to-cardOffers">Card Offers<i class="pull-right hide icon-arrow-right icon-white"></i></a></li>
			<li class="divider"></li>
			<li class="divider"></li>
			<li class="navScroller nav-header feedBackNav"><a  href="#go-to-feedback"> Feedback<i class="pull-right hide icon-arrow-right icon-white"></i></a></li>
		</ul>
	</div>
	<div id="features">
	    <div id="myOffers" class="featureItem">
	        <div class="container">
	        	<div class="container-fluid itemContainer">
			        <div class="row-fluid">
			            <div class="description span4">
			            	<br>
			            	<br>
			                <div class="heading bluishText">My Offers</div>
			                <div class="lead">
		                        <div class="explanation">
		                        	<span class="correct">&#10004;</span>
		                        	Manage your offers, coupons and deals
		                        </div>
		                        <div class="explanation"><span class="correct">&#10004;</span>Add offers from cards to your <strong>OfferDiary</strong></div>
			                </div>
			                <a class="btn btn-large btn-primary" href="signup.do">Sign up today</a>
			            </div>
			        	<div class="span8">
					        <img src="images/home/my_offers.png" alt="My Offers">
				        </div>
			        </div>
			        <div class="row-fluid" style="position:relative">
			        	<hr>
			        	<div id="go-to-share" class="goDown">
			        		<i class="icon-chevron-down icon-white"></i>
			        	</div>
			        </div>
			     </div>
		     </div>
	    </div>
	    <div id="share" class="featureItem">
		    <div class="container">
			    <div class="container-fluid itemContainer">
			        <div class="row-fluid">
			            <div class="description span4">
			                <div class="heading bluishText">Share with Friends</div>
			                <div class="lead">
		                        <div class="explanation"><span class="correct">&#10004;</span>Share offers with your friends</div>
		                        <div class="explanation"><span class="correct">&#10004;</span>Share via Facebook</div>
		                        <div class="explanation"><span class="correct">&#10004;</span>Share via Email</div>
			                </div>
			                <a class="btn btn-large btn-primary" href="signup.do">Sign up today</a>			  
			            </div>
				        <div class="span8">
					        <img src="images/home/share_offer.png" alt="Share offers coupons with your friends">
				        </div>
			        </div>
			        <div class="row-fluid" style="position:relative">
			        	<hr>
			        	<div id="go-to-notifications" class="goDown">
			        		<i class="icon-chevron-down icon-white"></i>
			        	</div>
			        </div>
			    </div>
		    </div>
	    </div>
	    <div id="notifications" class="featureItem">
	        <div class="container">
		        <div class="container-fluid itemContainer">
			        <div class="row-fluid">
			            <div class="description span4">
			                <div class="heading bluishText">Notifications</div>
			                <div class="lead">
		                        <div class="explanation"><span class="correct">&#10004;</span>Why let the offers, coupons go waste?</div>
		                        <div class="explanation"><span class="correct">&#10004;</span>Get timely offer expiry notifications via email</div>
			                </div>
			                <a class="btn btn-large btn-primary" href="signup.do">Sign up today</a>			  
			            </div>
				        <div class="span8">
					        <img src="images/home/offer_expiry.png" alt="Get timely notifications before expiry of coupons and offers">
				        </div>
			        </div>
			        <div class="row-fluid" style="position:relative">
			        	<hr>
			        	<div id="go-to-cardOffers" class="goDown">
			        		<i class="icon-chevron-down icon-white"></i>
			        	</div>
			        </div>
			   </div>
		    </div>
	    </div>
	    <div id="cardOffers" class="featureItem">
	        <div class="container">
		        <div class="container-fluid itemContainer">
			        <div class="row-fluid">
			            <div class="description span4">
			                <div class="heading bluishText">Card Offers</div>
			                <div class="lead">
		                        <div class="explanation"><span class="correct">&#10004;</span>Discover the offers available on your cards</div>
		                        <div class="explanation"><span class="correct">&#10004;</span>Track the offers available on your cards</div>
			                </div>
			                <a class="btn btn-large btn-primary" href="signup.do">Sign up today</a>
			            </div>
				        <div class="span8">
					        <img src="images/home/card_offers.png" alt="Discover offers deals available on your Credit/Debit cards">
				        </div>
			        </div>
			        <div class="row-fluid" style="position:relative">
			        	<hr>
			        	<div id="go-to-feedback" class="goDown">
			        		<i class="icon-chevron-down icon-white"></i>
			        	</div>
			        </div>
		        </div>
		   </div>
	    </div>            
	    
	    <div id="feedback" class="featureItem">
	        <div class="container">
		        <div class="container-fluid itemContainer">
			        <div class="row-fluid">
			            <div class="description span4">
			                <div class="heading greenishText">Feedback</div>
			            </div>
			        </div>
			        <div class="row-fluid">
				        <div class="lead ">
					        <div class="explanation">Drop in your thoughts: </div>
					    </div>
			        </div>
			        <div class="row-fluid">
			        	<div class="container-fluid">
			        		<div class="row-fluid">
					        	<div class="span3">
					        		<a target="_blank" href="https://www.facebook.com/offerdiary">
					        			<img alt="Offerdiary on https://www.facebook.com/offerdiary" class="img-polaroid img-rounded" src="images/home/facebook.png">
					        			<span class="feedbackLink">facebook.com/offerdiary</span>
					        		</a>
					        	</div>
					        	<div class="span3">
					        		<a href="https://www.twitter.com/offerdiary" target="_blank">
					        			<img src="images/home/twitter.png" class="img-polaroid img-rounded" alt="Offerdiary on https://twitter.com/offerdiary">
					        			<span class="feedbackLink">twitter.com/offerdiary</span>
					        		</a>

					        	</div>
					        	<div class="span3">
					        		<a href="mailto:support@offerdiary.com">
					        			<img src="images/home/gmail.png" class="img-polaroid img-rounded" alt="Mail us at support@offerdiary.com">
					        			<span class="feedbackLink">support@offerdiary.com</span>
					        		</a>
					        	</div>
				        	</div>
			        	</div>
			        </div>
		        </div>
		   </div>
	    </div> 
    </div>
<%@include file="/common/pages/bodyHtmlEnd.jsp" %>
