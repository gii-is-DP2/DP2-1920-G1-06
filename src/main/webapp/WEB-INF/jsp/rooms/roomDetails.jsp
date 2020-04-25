<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="rooms">

    <h2>Room Information</h2>


    <table class="table table-striped">
        <tr>
            <th>Room Number</th>
            <td><b><c:out value="${room.roomNumber}"/></b></td>
        </tr>
        <tr>
            <th>Surface</th>
            <td><c:out value="${room.surface}"/></td>
        </tr>
        <tr>
            <th>Price</th>
            <td><c:out value="${room.price}"/></td>
        </tr>
        <tr>
            <th>Exterior Window</th>
            <td><c:out value="${room.extWindow}"/></td>
        </tr>
         <tr>
            <th>Closet surface</th>
            <td><c:out value="${room.tamCloset}"/></td>
        </tr>
    </table>

    <spring:url value="{roomId}/edit" var="editUrl">
        <spring:param name="roomId" value="${room.id}"/>
    </spring:url>
    
    <a href="${fn:escapeXml(editUrl)}" class="btn btn-default">Edit Room</a>


		<spring:url value = "{roomId}/rental/new" var="add">
		<spring:param name="roomId" value="${room.id}"/>
		</spring:url>
		<a href="${fn:escapeXml(add)}" class="btn btn-default">Send a Request</a>

























	<spring:url value = "/properties/{propertyId}/rooms" var="roomUrlBack"><spring:param name = "propertyId" value = "${property.id}"/></spring:url>
	<a href="${fn:escapeXml(roomUrlBack)}" class="btn btn-default">Back to rooms</a>
</petclinic:layout>
