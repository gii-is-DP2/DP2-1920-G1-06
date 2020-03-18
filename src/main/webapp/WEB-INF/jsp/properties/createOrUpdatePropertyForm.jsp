  
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="properties">
  	<h2>
        <c:if test="${property['new']}">New </c:if> Property
    </h2>
    <form:form modelAttribute="property" class="form-horizontal" id="add-property-form">
        <div class="form-group has-feedback">
            <petclinic:inputField label="Address" name="address"/>
            <petclinic:inputField label="City" name="city"/>
            <petclinic:inputField label="Description" name="description"/>
            <petclinic:inputField label="PropertyType" name="propertyType"/>
            <petclinic:inputField label="Surface" name="surface"/>
            <petclinic:inputField label="TotalRooms" name="totalRooms"/>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${property['new']}">
                        <button class="btn btn-default" type="submit">Add Property</button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-default" type="submit">Update Property</button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </form:form>
</petclinic:layout>