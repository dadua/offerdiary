<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
			<%--
				TODO: Decide this
				<!-- The HTML5 charset format -->
				<meta charset="UTF-8">
			 --%>
		<title>OfferDiary : Your Official Offer Journal</title>
		<%@include file="commonHeader.jsp" %>
	</head>
	<body onload="onLoadPutRandomTiles();" >
		<%@include file="comingSoonHeader.jsp" %>
		<div class="container">
			<div class="row">
				<div class="span2 offset2" >
					<div class="hero-unit tile-div">
						<img id="tile_image_id_1" title="xyz" src="images/icons/tile_1.png" >
					</div>
				</div>
				<div class="span2 ">
					<div class="hero-unit tile-div" >
						<img id="tile_image_id_2" src="images/icons/tile_2.png">
					</div>
				</div>
				<div class="span2 ">
					<div class="hero-unit tile-div">
						<img id="tile_image_id_3" src="images/icons/tile_3.png">
					</div>
				</div>
				<div class="span2 ">
					<div class="hero-unit tile-div">
						<img id="tile_image_id_4" src="images/icons/tile_4.png">
					</div>
				</div>
			</div>
			<div class="row">
				<div class="span2 offset2">
					<div class="hero-unit tile-div">
						<img id="tile_image_id_5" src="images/icons/tile_5.png">
					</div>
				</div>
				<div class="span2 ">
					<div class="hero-unit tile-div">
						<img id="tile_image_id_6" src="images/icons/tile_6.png">
					</div>
				</div>
				<div class="span2 ">
					<div class="hero-unit tile-div">
						<img id="tile_image_id_7" src="images/icons/tile_7.png">
					</div>
				</div>
				<div class="span2 ">
					<div class="hero-unit tile-div">
						<img id="tile_image_id_8" src="images/icons/tile_8.png">
					</div>
				</div>
			</div>
			<div class="row">
				<div class="span2 offset2">
					<div class="hero-unit tile-div">
						<img id="tile_image_id_9" src="images/icons/tile_9.png">
					</div>
				</div>
				<div class="span4 ">
					<div class="hero-unit tile-div">
						<img  class="coming-soon-small" src="images/comingsoon_2.png">
						<div id="emailFormHolder" class="control-group">
							<div style="float:left">
								<input id="email" type="text" class="input-xlarge input-center" name="email" placeholder="Email : Sign up for exculsive closed Beta"></input>
							</div>
							<div>	
								<div class="row">
									<button id="newEmailSubscription" class="btn btn-info" href="#">Follow</button>
								</div>
								<div class="row">
									<div id="onEmailSuccess" class="hide alert alert-success span3 div-small-message" >
										<button id="email_close" class="close close-small" >×</button>
										Thanks for your interest! We'll keep you posted.
									</div> 
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="span2 ">
					<div class="hero-unit tile-div">
						<img id="tile_image_id_10" src="images/icons/tile_10.png">
					</div>
				</div>
			</div>
			<div class="row">
				<div class="span3 offset2">
					<img src="images/app_stores.png">
				</div>
				<div class="span4 alert alert-info">
					<div class="tile-des-div ">
					
						<strong>OfferDiary</strong> is a one stop solution for:
						<ul >
							<li>
								<span>
							 	 Managing all your offers and loyalty credits effectively!
							 	</span>
							</li>
							<li>
								<span>
							 	 Tracking and sharing your offers!
							 	</span>
							</li>
						</ul>
						<em>Very soon you would never have to deal with unused offers and expired credits.</em>
					</div>
				</div>
			</div>
		</div>
		<script type="text/javascript">
			$(function(){
				
				var sendEmail = function(){
					var email = $('#email').val();
					if (email === '' || email.indexOf('@')===-1 || email.indexOf('.')===-1) {
						$('#emailFormHolder').addClass('error');
						return;
					}
					$('#onEmailSuccess').show('slow');
					$.post('hearMore.do', {'email': email}, function(data){
						var result = JSON.parse(data);
						if (result.success == true) {
						}
					});
				};
				$('#email').keypress(function(e) {
					$('#emailFormHolder').removeClass('error');
					var code = e.keyCode ? e.keyCode : e.which;
					if(code.toString() === '13') {
						sendEmail();
					}
				});
				$('#newEmailSubscription').click(sendEmail);
				$('#onEmailSuccess > .close').click(function(){
					$('#onEmailSuccess').hide();
				});
			});
		</script>

	</body>
</html>