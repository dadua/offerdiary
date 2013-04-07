<%@include file="/common/pages/headBegin.jsp" %>
	<title>OfferDiary : Never miss an offer!</title>
	<script type="text/javascript">
		$(function(){
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
	
	.lead li {
	    line-height: 1.5em;
    }
    .featureItem {
    	margin-top: 2%;
    	margin-bottom: 2%;
    }
    
	</style>
		
<%@include file="/common/pages/bodyBegin.jsp" %>
    <div class="featureItem">
        <div class="container">
	        <div class="row">
	            <div class="description span4">
	            	<br>
	            	<br>
	                <h1 class="bluishText">My Offers</h1>
	                <div class="lead">
	                    <ul style="list-style: circle;">
	                        <li >Manage Your offers</li>
	                        <li >Add offers from cards to wallet</li>
	                    </ul>
	                </div>
	                <a class="btn btn-large btn-primary" href="signup.do">Sign up today</a>
	            </div>
	        	<div class="span8">
			        <img src="images/home/my_offers.png" alt="My Offers">
		        </div>
	        </div>
	     </div>
    </div>
    <div class="featureItem">
        <div class="container">
	        <div class="row">
	            <div class="description span4">
	                <h1 class="bluishText">Share with Friends</h1>
	                <div class="lead">
	                    <ul style="list-style: circle;">
	                        <li >Share Offers with your Friends</li>
	                        <li >Share on Facebook</li>
	                        <li >Share on Email</li>
	                    </ul>
	                </div>
	                <a class="btn btn-large btn-primary" href="signup.do">Sign up today</a>			  
	            </div>
		        <div class="span8">
			        <img src="images/home/share_offer.png" alt="">
		        </div>
	        </div>
	    </div>
    </div>
    <div class="featureItem">
        <div class="container">
	        <div class="row">
	            <div class="description span4">
	                <h1 class="bluishText">Notifications</h1>
	                <div class="lead">
	                    <ul style="list-style: circle;">
	                        <li >Offer Expiry Notification on email</li>
	                        <li >Offers Expiring in Next 7 days</li>
	                    </ul>
	                </div>
	                <a class="btn btn-large btn-primary" href="signup.do">Sign up today</a>			  
	            </div>
		        <div class="span8">
			        <img src="images/home/offer_expiry.png" alt="">
		        </div>
	        </div>
	    </div>
    </div>
    <div class="featureItem">
        <div class="container">
	        <div class="row">
	            <div class="description span4">
	                <h1 class="bluishText">Card Offers</h1>
	                <div class="lead">
	                    <ul style="list-style: circle;">
	                        <li >Know Offers available on your cards</li>
	                    </ul>
	                </div>
	                <a class="btn btn-large btn-primary" href="signup.do">Sign up today</a>
	            </div>
		        <div class="span8">
			        <img src="images/home/card_offers.png" alt="">
		        </div>
	        </div>
        </div>
    </div>            
<%@include file="/common/pages/bodyHtmlEnd.jsp" %>
