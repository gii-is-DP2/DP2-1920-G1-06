<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
	
<petclinic:layout pageName="rooms">

	<jsp:attribute name="customScript">
        <script>
            $(function () {
                $("#date").datepicker({dateFormat: 'yyyy-MM-dd'});
            });
        </script>
    </jsp:attribute>
    
    <jsp:body>
    
    	<h2>
        	<c:if test="${rental['new']}">New </c:if> Rental
    	</h2>
	
	<%-- <jsp:attribute name="customScript">
        <script>
            $(function () {
                $("#date").datepicker({dateFormat: 'yyyy-MM-dd'});
            });
        </script>
    </jsp:attribute> --%>
    
   <%--  <jsp:body> --%>
	
    <form:form modelAttribute="rental" class="form-horizontal" id="add-room-form">
    
      <div class="form-group has-feedback">
            <label class="col-sm-2 control-label">Start Date</label>
             <div class="col-sm-10">
            	<form:input  type="date" class="form-control" path="startDate" pattern="yyyy-MM-dd" name="startDate" />
         	 </div>
      </div> 
      
      <div class="form-group has-feedback">
            <label class="col-sm-2 control-label">End Date</label>
             <div class="col-sm-10">
            	<form:input  type="date" class="form-control" path="endDate" pattern="yyyy-MM-dd" name="endDate" />
         	 </div>
      </div> 
        
        
     
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
            
               	<%-- <spring:url value = "rental/new" var="requestURL">
                </spring:url>
     			<a href="${fn:escapeXml(action)}" class="btn btn-default">"Send"</a> --%>
            
				<button class="btn btn-default" type="submit">Send a Request</button>
				
				<spring:url value = "/properties/find" var="roomUrlBack"></spring:url>
				<a href="${fn:escapeXml(roomUrlBack)}" class="btn btn-default">Cancel</a>
            </div>
        </div>
    </form:form>
    </jsp:body>
</petclinic:layout>
