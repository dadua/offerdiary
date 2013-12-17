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
								
								<th><strong>Name</strong></th>
								<th><strong>Email</strong></th>

                            <th><strong>Mobile Number</strong></th>
                        
                            <th><strong>Year of passing 10th</strong></th>
  
                            <th><strong>Currently Working at</strong></th>
                        
                            <th><strong>Designation</strong></th>
                        
                            <th><strong>Address</strong></th>
                        
                            <th><strong>City</strong></th>
                        
                            <th><strong>Pin Code</strong></th>
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
	                                                            ${user.mobileNumber}
                                                            </td>
                                                            <td>
	                                                            ${user.yearOfPassing}
                                                            </td>
                                                            <td>
	                                                            ${user.companyName}
                                                            </td>
                                                            <td>
	                                                            ${user.designation}
                                                            </td>
                                                            <td>
	                                                            ${user.address}
                                                            </td>
                                                            
                                                            <td>
	                                                            ${user.city}
                                                            </td>
                                                            <td>
	                                                            ${user.pinCode}
                                                            </td>
                                                            
                                                        </tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
		
<%@include file="/common/pages/bodyHtmlEnd.jsp" %>
