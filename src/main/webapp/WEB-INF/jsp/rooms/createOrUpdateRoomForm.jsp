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
        <c:if test="${room['new']}">New </c:if> Room
    </h2>
    <form:form modelAttribute="room" class="form-horizontal" id="add-room-form">
        <div class="form-group has-feedback">
            <petclinic:inputField label="Room number" name="roomNumber"/>
            <petclinic:inputField label="Surface" name="surface"/>
            <petclinic:inputField label="Price" name="price"/>
            <petclinic:inputField label="Exterior Window" name="extWindow"/>
            <petclinic:inputField label="Closet surface" name="TamCloset"/>

        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${room['new']}">
                        <button class="btn btn-default" type="submit">Add Room</button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-default" type="submit">Update Room</button>
                    </c:otherwise>
                </c:choose>
                
				
                <spring:url value = "/properties/{propertyId}/rooms/{roomId}" var="roomUrlBack"><spring:param name = "propertyId" value = "${property.id}"/><spring:param name = "roomId" value = "${room.id}"/></spring:url>
				<a href="${fn:escapeXml(roomUrlBack)}" class="btn btn-default">Cancel</a>
            </div>
        </div>
    </form:form>
</petclinic:layout>
