<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>

	<style>
		.topAlign {
			margin-top: 10px;
		}
		div.tile-div{
			height: 100px;
			padding: 10px;
			text-align:center;
		}
		.brandImage {
			height: 50px;
		}
		
		.featureTabs {
			margin-left: 30px;
			margin-top: 30px;
		}
		.tinyPic {
			width: 19px;
			height: 19px;
		}
		
		</style>
		
		<div class="navbar navbar-fixed-top">
      		<div class="navbar-inner">
        		<div class="container navBarItems">
        			<a class="brand" href="home.do">
        				<img src="images/logo_tag.png" class="brandImage" alt="Flower Accounts" />
        			</a>
        			<c:if test="${userSession!=null}">
        			<div class="pull-right">
        			<a href="logout.do" class="btn">Logout</a>
        			</div>
        			</c:if>
        		</div>
        		
      		</div>
      		<div class="container">
	       		<%@include file="/common/pages/actionInfoRow.jsp" %>
       		</div>
 		</div>
 		<script type="text/javascript" >
 			$(function(){
	 			$('.featureTabs > .active + .divider-vertical').hide().prev().prev().hide()
 			});
 		
 		</script>