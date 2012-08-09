		<%@include file="jqueryAllInclude.html" %>
		<%@include file="bootstrapHeadInclude.html" %>
		<%@include file="fbLoginAboveHeadJs.jsp" %>
		<link rel="icon" type="image/png" href="favicon.png" />
		<script type="text/javascript">
			var it = it || {};
			$(function() {
					
				var onServerUpChange =function(data) {
					var resultData = $.parseJSON(data);
					var user = resultData.result;
					$('#userContainer').html('Welcome '+ user.name + '!').append(
							'<span><img src="http://graph.facebook.com/'+ user.userId +'/picture" /></span>'
							);
					$('#actionsContainer').show();
					$('#walletAction').show();
					$("#loginToFb").hide();
				};
		
				var onServerUpGotoWallet =function(data) {
					onServerUpChange(data);
					$('#goToWallet').submit();
				};
			 
				$('#loginToFb').click({onServerUp: onServerUpGotoWallet}, it.fb.checkAndLogin);
			});
		</script>
		<script type="text/javascript">
			function onLoadPutRandomTiles(){
				changeOverImages(1);	
			}
			function changeOverImages(imageCounter){
				 setTimeout(function () {    
					 for(var j =1; j <= 10 ; j++){
							var imgId="tile_image_id_"+j;
							var imageSrc="images/tile_"+imageCounter+".png";
							$('img#'+imgId).attr("src", imageSrc);
						}if(imageCounter == 10){
							putRandomImages();
						}        
				    	imageCounter++;                    
				     	if (imageCounter < 11) {            
				    	  changeOverImages(imageCounter);              
				      	}                        
				   }, 200);
			}
			function putRandomImages(){
				for(var i =1 ; i <= 10; i++ ){
					var randomTileNumber =  Math.floor((Math.random()*10)+1);
					var imgId="tile_image_id_"+i;
					var imageSrc="images/tile_"+randomTileNumber+".png";
					$('img#'+imgId).attr("src", imageSrc);
				}
			}
		</script>
		<style type="text/css">
			.mainHeading {
				color: #999;
				padding: 5px;
				text-align: center;
			}
			
			.footerLink{
				color: #234;
				font-size: .88em;
			}
			.tab-text{
				font-family: verdana;
				font-size: 1.10em;
			};
			.medium-tab-text{
				font-family: verdana;
				font-size: 1.50em;
			};
			
			
		</style>