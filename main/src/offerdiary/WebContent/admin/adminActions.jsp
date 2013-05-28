<%@include file="/common/pages/headBegin.jsp" %>
		<title>Admin Actions</title>
		
		<script type="text/javascript">
			var it = it || {};
			
		</script>
		
		<style type="text/css">
			body {
				padding-top: 90px;
			}
		</style>
		
<%@include file="/common/pages/bodyBegin.jsp" %>
		<div class="container" >
			<div class="row-fluid thumnails">
				<c:forEach var="taskNameEntry" items="${taskNameToDisplayNameMap}">
					<div class="thumbnail">
						<c:forEach var="taskAction" items="${taskActions}">
							<div>
								<a href="manageBackGroundTask.do?taskName=${taskNameEntry.key}&${taskAction}">${taskNameEntry.value} - ${taskAction}</a>
							</div>
						</c:forEach>
					</div>
				</c:forEach>
			</div>
			<div class="row-fluid">
				<a href="odusers.do">
					Offer Diary Users
				</a>
			</div>
		</div>
		
<%@include file="/common/pages/bodyHtmlEnd.jsp" %>