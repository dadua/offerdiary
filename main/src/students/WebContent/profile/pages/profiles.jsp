<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@include file="/common/pages/headBegin.jsp" %>
		<title>Alumni</title>
		
		<script src="profile/js/profile.js" type="text/javascript" charset="UTF-8"></script>
		<script type="text/javascript">
			var it = it || {};
			
			$(function() {
			    $('#alumniTab').addClass('active');
			});
			
		</script>
		
		<style type="text/css">
			body {
				padding-top: 90px;
			}
			<%@include file="/common/css/layout.css" %>
		</style>
		
<%@include file="/common/pages/bodyBegin.jsp" %>
		<div class="container" >
                    <div class="row-fluid">
                        <h4 class="bluishText">
                            Alumni (${fn:length(users)})
                        </h4>
                    </div>
			<div class="row-fluid">
				<div class="container-fluid">
					<table class="table table-striped table-condensed table-bordered">
					
						<thead>
							<tr>
							<%-- select name, email_id, LOGIN_TYPE from users; --%>
								<th>
								Name
								</th>
								<th>
								Email Id
								</th>
								<th>
								User Id
								</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="user" items="${users}">
                                                        <tr>
                                                            <td>
                                                                ${user.name}
                                                            </td>
                                                            <td>
                                                                ${user.emailId}
                                                            </td>
                                                            <td>
                                                            	<c:choose>
	                                                            	<c:when test="${user.loginType =='FACEBOOK'}">
	                                                            	<a href="https://facebook.com/${user.userId}" target="_blank">
	                                                                ${user.userId} on FB
	                                                                </a>
	                                                                </c:when>
	                                                                <c:otherwise>
	                                                                ${user.userId}
	                                                                
	                                                                </c:otherwise>
                                                                </c:choose>
                                                            </td>
                                                        </tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
		
<%@include file="/common/pages/bodyHtmlEnd.jsp" %>
