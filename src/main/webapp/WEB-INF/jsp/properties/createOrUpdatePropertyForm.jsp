
<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="properties">
	<h2>
		<c:if test="${property['new']}">New </c:if>
		Property
	</h2>
	<form:form modelAttribute="property" class="form-horizontal"
		id="add-property-form">
		<div class="form-group has-feedback">
			<petclinic:inputField label="Address" name="address" />
			<petclinic:inputField label="City" name="city" />
			<petclinic:inputField label="Description" name="description" />
			<petclinic:inputField label="Surface" name="surface" />
			<petclinic:inputField label="Total Rooms" name="totalRooms" />
			
			<div class="form-horizontal">
				<label  class="col-sm-2 control-label">Type of Properties</label>
				<div class="col-sm-10">
					<select name=propertyType class="form-control">
						<option value=0>House</option>
						<option value=1>Flat</option>
					</select>
				</div>
			</div>
		</div>
			
		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
				<c:choose>
					<c:when test="${property['new']}">
						<button class="btn btn-default" type="submit">Add Property</button>
						
						<spring:url value = "/properties" var="propertyUrlBack"><spring:param name = "propertyId" value = "${property.id}"/></spring:url>
						<a href="${fn:escapeXml(propertyUrlBack)}" class="btn btn-default">Cancel</a>
					</c:when>
					<c:otherwise>
						<button class="btn btn-default" type="submit">Update Property</button>
						<spring:url value = "/properties/{propertyId}/show" var="propertyUrlBack"><spring:param name = "propertyId" value = "${property.id}"/></spring:url>
						<a href="${fn:escapeXml(propertyUrlBack)}" class="btn btn-default">Cancel</a>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</form:form>
</petclinic:layout>