		<%@include file="jqueryAllInclude.html" %>
		<%@include file="bootstrapHeadInclude.html" %>
		<%@include file="fbLoginAboveHeadJs.jsp" %>
		<link rel="shortcut icon" href="http://www.offerdiary.com/favicon.ico" />
		<script type="text/javascript">
			var it = it || {};
			it.tile =  function(){
				var tileTitleMap = {
						tile_1: "It's pouring offers ! Track them " ,
						tile_2: "Make shopping more meaningful !" ,
						tile_3: "Don't miss out offers on your next drink !" ,
						tile_4: "We are going live pretty soon :) " ,
						tile_5: "Life is a box of offers !" ,
						tile_6: "Share your offers with friends",
						tile_7: "Save your offers with us ! Peace" ,
						tile_8: "Don't miss out on your pie of opportunity" ,
						tile_9: "You shop - you build your loyalty credits with OfferDiary" ,
						tile_10: "Its your suitcase of offers, deal & loyalty credits" ,
						tile_11: "No more loosing out on expired offers",
						tile_12: "More reasons to buy that new dress !" ,
						tile_13: "Not just the drink share your offers with friends too !" ,
						tile_14: "An offer in hand would have made this easy on your pocket !" ,
						tile_15: "What's in the box ? " ,
						tile_16: "Pay less for your next pizza fiesta !" ,
						tile_17: "It takes time to build great things ! We'll be live soon " ,
						tile_18: "You are watching Offer Diary" ,
						tile_19: "Dining offers !" ,
						tile_20: "Write to us: support@offerdiary.com" ,
				};
				return{
					tileTitleMap: tileTitleMap
				};
			}();
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
				
				$('.tile-div').hover(function() {
					var title = $(this).find('img').attr('title');
					$(this).tooltip({title: title, placement: 'right'}).tooltip('show');
				});
			});
			function onLoadPutRandomTiles(){
				changeOverImages(1);	
			}
			function changeOverImages(imageCounter){
				 setTimeout(function () {    
					 for(var j =1; j <= 10 ; j++){
							var imgId="tile_image_id_"+j;
							var randomTileNumber =  Math.floor((Math.random()*10)+1) + 10*(Math.floor(Math.random()/0.5));
							var imageSrc="images/icons/tile_"+randomTileNumber+".png";
							var title = it.tile.tileTitleMap['tile_'+randomTileNumber];
							$('img#'+imgId).attr("src", imageSrc).attr("title", title);
							//.tooltip({title: title});
						}if(imageCounter == 10){
							putRandomImages();
						}        
				    	imageCounter++;                    
				     	if (imageCounter < 11) {            
				    	  changeOverImages(imageCounter);              
				      	}                        
				   }, 180);
			}
			function putRandomImages(){
				for(var i =1 ; i <= 10; i++ ){
					var randomTileNumber =  Math.floor((Math.random()*10)+1) + 10*(Math.floor(Math.random()/0.5));
					var imgId="tile_image_id_"+i;
					var imageSrc="images/icons/tile_"+randomTileNumber+".png";
					var title = it.tile.tileTitleMap['tile_'+randomTileNumber];
					$('img#'+imgId).attr("src", imageSrc).attr("title", title);
					//.tooltip({title: title});
					
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