<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="rentals">
	<h2>Rooms</h2>

	<table id="rentalsTable" class="table table-striped">
		<thead>
			<tr>
				<th style="width: 200px;">Start Date</th>
				<th style="width: 200px">End Date</th>
				<th>Complete Address</th>

			</tr>
		</thead>
		<tbody>
			<c:forEach items="${selections}" var="rental">
				<tr>
					<!--   <td>
                    <spring:url value="/properties/find" var="propertiesUrl">
                        <spring:param name="propertyId" value="${property.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(ownerUrl)}"><c:out value="${owner.firstName} ${owner.lastName}"/></a>
                </td> -->
					<td><c:out value="${rental.startDate}" /></td>
					<td><c:out value="${rental.endDate}" /></td>
					<td><c:out value="${rental.room.roomNumber}"/>, <c:out value="${rental.room.property.address}" /></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<spring:url value = "/" var="rentalUrlBack"></spring:url>
	<a href="${fn:escapeXml(rentalUrlBack)}" class="btn btn-default">Back</a>
</petclinic:layout>
