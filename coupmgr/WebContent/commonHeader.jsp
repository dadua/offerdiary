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