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
		<title>OfferDiary : Your Official Offer Journal</title>
		<%@include file="commonHeader.jsp" %>
	
	</head>
	<body onload="onLoadPutRandomTiles();" >
		<%@include file="comingSoonHeader.jsp" %>
		<div class="container">
			<div class="row">
				<div class="span2 offset2" >
					<div class="hero-unit tile-div">
						<img id="tile_image_id_1" src="images/tile_1.png" >
					</div>
				</div>
				<div class="span2 ">
					<div class="hero-unit tile-div" >
						<img id="tile_image_id_2" src="images/tile_2.png">
					</div>
				</div>
				<div class="span2 ">
					<div class="hero-unit tile-div">
						<img id="tile_image_id_3" src="images/tile_3.png">
					</div>
				</div>
				<div class="span2 ">
					<div class="hero-unit tile-div">
						<img id="tile_image_id_4" src="images/tile_4.png">
					</div>
				</div>
			</div>
			<div class="row">
				<div class="span2 offset2">
					<div class="hero-unit tile-div">
						<img id="tile_image_id_5" src="images/tile_5.png">
					</div>
				</div>
				<div class="span2 ">
					<div class="hero-unit tile-div">
						<img id="tile_image_id_6" src="images/tile_6.png">
					</div>
				</div>
				<div class="span2 ">
					<div class="hero-unit tile-div">
						<img id="tile_image_id_7" src="images/tile_7.png">
					</div>
				</div>
				<div class="span2 ">
					<div class="hero-unit tile-div">
						<img id="tile_image_id_8" src="images/tile_8.png">
					</div>
				</div>
			</div>
			<div class="row">
				<div class="span2 offset2">
					<div class="hero-unit tile-div">
						<img id="tile_image_id_9" src="images/tile_9.png">
					</div>
				</div>
				<div class="span4 ">
					<div class="hero-unit tile-div">
						<img  src="images/comingsoon_2.png">
						<div class="control-group">
							<div style="float:left">
								<input id="email" type="text" class="input-xlarge input-center" name="email" placeholder="Email : Sign up for closed Beta"></input>
							</div>
							<div>	
								<div class="row">
									<button id="newEmailSubscription" class="btn btn-info" href="#">Follow</button>
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
				<div class="span2 ">
					<div class="hero-unit tile-div">
						<img id="tile_image_id_10" src="images/tile_10.png">
					</div>
				</div>
			</div>
			<div class="row">
				<div class="span3 offset2">
					<img src="images/app_stores.png">
				</div>
				<div class="span4 ">
					
				</div>
			</div>
		</div>
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