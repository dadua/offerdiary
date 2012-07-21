<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
			<%--
				TODO: Decide this
				<!-- The HTML5 charset format -->
				<meta charset="UTF-8">
			 --%>
		<title>OfferDo : Do more with Offers</title>
		<%@include file="commonHeader.jsp" %>
	
	</head>
	<body >
		<%@include file="navHeader.jsp" %>
		<div class="container">
			<div class="row">
				<div class="span2 offset1" >
					<div class="hero-unit tile-div">
						<img src="images/tile_1.png" >
					</div>
				</div>
				<div class="span2 ">
					<div class="hero-unit tile-div" >
						<img src="images/tile_2_cart.png">
					</div>
				</div>
				<div class="span2 ">
					<div class="hero-unit tile-div">
						<img src="images/tile_gift_pack.png">
					</div>
				</div>
				<div class="span2 ">
					<div class="hero-unit tile-div">
						<img src="images/tile_piggy_bank.png">
					</div>
				</div>
				<div class="span2 ">
					<div class="hero-unit tile-div">
						<img src="images/tile_gift.png">
					</div>
				</div>
			</div>
			<div class="row">
				<div class="span2 offset1">
					<div class="hero-unit tile-div">
						<img src="images/tile_arrow.png">
					</div>
				</div>
				<div class="span2 ">
					<div class="hero-unit tile-div">
					</div>
				</div>
				<div class="span2 ">
					<div class="hero-unit tile-div">
					</div>
				</div>
				<div class="span2 ">
					<div class="hero-unit tile-div">
					</div>
				</div>
				<div class="span2 ">
					<div class="hero-unit tile-div">
					</div>
				</div>
			</div>
			<div class="row">
				<div class="span2 offset1">
					<div class="hero-unit tile-div">
					</div>
				</div>
				<div class="span6 ">
					<div class="hero-unit tile-div">
					</div>
				</div>
				<div class="span2 ">
					<div class="hero-unit tile-div">
					</div>
				</div>
			</div>
				<div class="row">
				<div class="span2 offset1">
					<div class="hero-unit tile-div">
					</div>
				</div>
				<div class="span2 ">
					<div class="hero-unit tile-div">
					</div>
				</div>
				<div class="span2 ">
					<div class="hero-unit tile-div">
					</div>
				</div>
				<div class="span2 ">
					<div class="hero-unit tile-div">
					</div>
				</div>
				<div class="span2 ">
					<div class="hero-unit tile-div">
					</div>
				</div>
			</div>
			<div class="row">
				<div class="span3 offset3">
					<img src="images/app_stores.png">
				</div>
				<div class="span4 ">
					<img  src="images/launch.jpg">
					<div class="control-group">
						<div style="float:left">
							<input id="email" type="text" class="input-xlarge input-center" name="email" placeholder="Email"></input>
						</div>
						<div>	
							<div class="row">
								<button id="newEmailSubscription" class="btn btn-info" href="#">Go</button>
							</div>
							<div class="row">
								<div id="onEmailSuccess" class="hide alert alert-success span4" >
									<button class="close" >×</button>
									Thanks for your interest! We'll keep you posted.
								</div> 
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
			
		</div>
		<%@include file="footer.jsp" %>
		
			
		<script type="text/javascript">
			$(function(){
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

	</body>
</html>