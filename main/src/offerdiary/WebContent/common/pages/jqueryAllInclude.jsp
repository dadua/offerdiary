<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>

<script type="text/javascript" src="js/json2.js"></script>
<link rel="stylesheet" type="text/css" href="common/libs/bsJquery/jquery-ui-1.8.16.custom.css" />

<!--[if IE]>
  <link rel="stylesheet" type="text/css" href="common/libs/bsJquery/jquery.ui.1.8.16.ie.css"/>
<![endif]-->


<c:choose>
	<c:when test="${odServerMode == 'DEV'}">
	
		<script type="text/javascript" src="common/libs/jquery/jquery-1.7.2.js"></script>
		<script type="text/javascript" src="common/libs/jquery/jquery-ui.js"></script>
		<script src="common/js/actionInfo.js" charset="UTF-8"> </script>
		<script src="common/js/async.jquery.js" charset="UTF-8"> </script>
	
	</c:when>
	
	<c:otherwise>
	
		<script src="//ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
		<script src="//ajax.googleapis.com/ajax/libs/jqueryui/1.8.16/jquery-ui.min.js"></script>
		<script type="text/javascript" charset="utf-8">
		    <%@include file="/common/js/actionInfo.js" %>
		    <%@include file="/common/js/async.jquery.js" %>
		</script>
	
	</c:otherwise>
</c:choose>

<script type="text/javascript">
    $(function () {
        //Setup loading stuff..
        it.actionInfo.init();
        $.async.init();
    });

</script>
