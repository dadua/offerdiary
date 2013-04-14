		<%@include file="/common/pages/fontsInclude.html" %>
		<%@include file="/common/pages/jqueryAllInclude.jsp" %>
		<%@include file="/common/pages/bootstrapHeadInclude.jsp" %>
		<%@include file="/common/pages/fb/fbLoginAboveHeadJs.jsp" %>
		<%@include file="/common/pages/googleanalytics.html" %>
		<link rel="shortcut icon" href="favicon.ico" />
		<script type="text/javascript">
			$(function() {
					
				var onServerUpChange =function(data) {
					var resultData = $.parseJSON(data);
					var user = resultData.result;
					if (user) {
					    
						$('#userContainer').html('Welcome '+ user.name + '!').append(
								'<span><img src="http://graph.facebook.com/'+ user.userId +'/picture" /></span>'
							);
					}
					$('#actionsContainer').show();
					$('#walletAction').show();
				};
		
				var onServerUpGotoWallet =function(data) {
					onServerUpChange(data);
					$('#goToWallet').submit();
				};
			 
				$('#loginToFb').click({onServerUp: onServerUpGotoWallet}, it.fb.checkAndLogin);
			});
			
			function validateEmail(email) { 
			    var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
			    return re.test(email);
			}
		</script>
		<style type="text/css">
			.mainHeading {
				color: #999;
				padding: 5px;
				text-align: center;
			}
			
			.footerLink{
				background-color: #FAFAFA;
				font-size: .8em;
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
