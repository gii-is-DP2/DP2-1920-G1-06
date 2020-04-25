<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
	
<petclinic:layout pageName="rooms">
    <h2>
        <c:if test="${rental['new']}">New </c:if> Rental
    </h2>
		
    <form:form modelAttribute="rental" class="form-horizontal" id="add-room-form">
        <div class="form-group has-feedback">
            <petclinic:inputField label="Start Date" name="startDate"/>
            <petclinic:inputField label="End Date" name="endDate"/>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
            
               	<%-- <spring:url value = "rental/new" var="requestURL">
                </spring:url>
     			<a href="${fn:escapeXml(action)}" class="btn btn-default">"Send"</a> --%>
            
				<button class="btn btn-default" type="submit">Add Room</button>
                
               
				<a href="${fn:escapeXml(roomUrlBack)}" class="btn btn-default">Cancel</a>
            </div>
        </div>
    </form:form>
</petclinic:layout>
