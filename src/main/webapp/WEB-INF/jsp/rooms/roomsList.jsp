<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="rooms">
	<h2>Rooms</h2>

	<table id="roomsTable" class="table table-striped">
		<thead>
			<tr>
				<th style="width: 200px;">Room Number</th>
				<th>Surface</th>
				<th style="width: 200px">Price</th>
				<th>Details</th>

			</tr>
		</thead>
		<tbody>
			<c:forEach items="${selections}" var="room">
				<tr>
					<!--   <td>
                    <spring:url value="/properties/find" var="propertiesUrl">
                        <spring:param name="propertyId" value="${property.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(ownerUrl)}"><c:out value="${owner.firstName} ${owner.lastName}"/></a>
                </td> -->
					<td><c:out value="${room.roomNumber}" /></td>
					<td><c:out value="${room.surface}" /></td>
					<td><c:out value="${room.price}" /></td>
					<td><spring:url value = "/properties/{propertyId}/rooms/{roomId}" var="roomUrl"> <spring:param name = "roomId" value = "${room.id}"/><spring:param name = "propertyId" value = "${room.property.id}"/></spring:url>
						<a href="${fn:escapeXml(roomUrl)}">More Details</a>
					</td>

				</tr>
			</c:forEach>
		</tbody>
	</table>
	<spring:url value = "/properties/{propertyId}/rooms/new" var="roomUrlNew"><spring:param name = "propertyId" value = "${property.id}"/></spring:url>
	<a href="${fn:escapeXml(roomUrlNew)}" class="btn btn-default">Add room</a>
	<spring:url value = "/properties" var="roomUrlBack"></spring:url>
	<a href="${fn:escapeXml(roomUrlBack)}" class="btn btn-default">Back to properties</a>
</petclinic:layout>
