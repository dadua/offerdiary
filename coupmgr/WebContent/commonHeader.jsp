<%@include file="jqueryAllInclude.html" %>
		<%@include file="bootstrapHeadInclude.html" %>
		<%@include file="fbLoginAboveHeadJs.jsp" %>
		<script type="text/javascript">
			var it = it || {};
			$(function() {
					
				var onServerUpChange =function(data) {
					var resultData = $.parseJSON(data);
					var user = resultData.result;
					$('#userContainer').html('Welcome '+ user.name + '!').append(
							'<span><img src="http://graph.facebook.com/'+ user.userId +'/picture" /></span>'
							);
					$('#walletAction').show();
					$("#loginToFb").hide();
				};
		
				var onServerUpGotoWallet =function(data) {
					onServerUpChange(data);
					$('#goToWallet').submit();
				};
			 
				$('#loginToFb').click({onServerUp: onServerUpGotoWallet}, it.fb.checkAndLogin);
				it.fb.checkLoginStatusAndUpdateServer(function(){}, onServerUpChange);
			});
		</script>
		<style type="text/css">
			.mainHeading {
				color: #999;
				padding: 5px;
				text-align: center;
			}
			
			
			.footerLink{
				color: #234;
			}
		</style>