<%--
<!--
A non float version inside a container 
-->
<div id="actionInfoContainer" class="row">
    <div id="loadingActionRow" class="invisible">
        <img class="span2 offset5" src="images/loading/loadingAnimation.gif">
    </div>

    <br/>

    <div id="actionInfoRow" class="span6 offset3 alert alert-success invisible">
        <button type="button" class="hideAlert close" >×</button>
        <div id="alertMessage">
        </div>
    </div>
</div>
 --%>
<div id="actionInfoContainer" class="row">
    <div id="actionInfoRow" class="span6 offset7 alert hide alert-success ">
        <button type="button" class="hideAlert close">×</button>
        <div id="alertMessage">
        </div>
    </div>
    <div class="hide container" id="loadingActionRow" >
        <img src="images/loading/loadingAnimation.gif" class="span2 offset5">
        
    </div>
    <noscript>
  	    <div id="actionInfoRow" class="span6 offset7 alert alert-warning">
	        <button type="button" class="hideAlert close">×</button>
	        <div id="alertMessage">
		        OfferDiary makes heavy use of javascript.
		        You should enable javascript on your browser to best experience this site.
	        </div>
	    </div>
    </noscript>
    
</div>