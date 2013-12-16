<%@include file="/common/pages/headBegin.jsp" %>
	<title>VVS: Alumni, Students and beyond</title>
	<script type="text/javascript">
		var it = it || {};
		it.scroll = it.scroll || {};
		it.scroll.did = false;
		$(function(){
		    
		});
                
	</script>
	<style>
	.vendorCarouselItem { 
	    cursor: pointer;
	}
	
    .someMargin {
    	margin: 12px;
    }
    
    <%@include file="/common/css/layout.css" %>
	 body {
		padding-top: 70px;
	}
	
	.lead > .explanation {
	    line-height: 2em;
    }
    .featureItem {
    	margin-top: 7%;
    	margin-bottom: 10%;
    	/*border: 1px solid #DDDDDD;*/
    }
    
    .goDown {
	    background-color: #E5E5E5;
	    background-image: linear-gradient(to bottom, #F2F2F2, #E5E5E5);
	    background-repeat: repeat-x;
	    border-radius: 0 0 8px 8px;
	    padding: 5px 10px 2px;
	    position: absolute;
	    right: 48%;
	    top: 50%;
	    cursor: pointer;
    }
    
    .correct {
		color: green;
		font-size: 1.5em;
		margin-right: 3%;
		text-align: center;
		vertical-align: middle;
	}
	
	.scrollFeatures {
		top: 35%;
		border-radius: 0px 6px 6px 0px;
		padding: 0;
		border: 1px solid #DDDDDD;
		position: fixed;
		width: 135px;
		/*width: 9%;*/
        left: -13px;
		background-color: #FAFAFA;
	    background-image: linear-gradient(to bottom, #FFFFFF, #F2F2F2);
	    background-repeat: repeat-x;
	}
	.scroller > .divider {
		margin: 2px 1px;
	}
	
	.navScroller > a {
		padding-right: 0;
	}
	
	

	.itemContainer {
		margin-left: 90px;
	}
	
	.heading {
		font-size:3.1em;
		line-height: 1.2em;
	}
	
	.greenishText {
		color: #62C462;
	}
	
	.navScroller.nav-header.feedBackNav  {
		margin-top: 0px;
	}
	
	.navScroller.nav-header.feedBackNav > a {
		color: #999999;
	}
	
	.navScroller.feedBackNav.active > a {
	    color: lightgreen;
	}
	
	.feedbackLink {
		line-height: 2em;
	}

        @media (max-width: 767px) {
            .itemContainer {
                margin-left: 20px;
            }
            body {
				padding-top: 0px;
			}
        }
	
	</style>
	
	<%--
</head>
<body data-target=".scrollFeatures" data-spy="scroll" data-offset="90">
	<%@include file="/common/pages/navHeader.jsp" %>
	<%@include file="/common/pages/fb/fbInitAsyncBelowBodyJs.jsp" %>
	 --%>

<%@include file="/common/pages/bodyBegin.jsp" %>
	
	<div class="container content-container">
		<div class="container-fluid ">
		Home Page
		</div>
	</div>
	
<%@include file="/common/pages/bodyHtmlEnd.jsp" %>
