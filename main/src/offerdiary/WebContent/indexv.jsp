<%@include file="/common/pages/headBegin.jsp" %>
	<title>OfferDiary : Never miss an offer!</title>
	<script type="text/javascript">
		$(function(){
		    $('.goDown').click(function(e){
				var id = $(this).attr('id');
					//locationToScroll = /go-to-(.+)/.exec(id)[1];
		        $('html, body').animate({
		            scrollTop: $("#"+id).offset().top
		        }, 2000);
		    });
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
	    background: none repeat scroll 0 0 #E5E5E5;
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
    
	</style>
		
<%@include file="/common/pages/bodyBegin.jsp" %>
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
