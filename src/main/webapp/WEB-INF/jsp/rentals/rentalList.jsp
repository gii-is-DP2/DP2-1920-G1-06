<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<petclinic:layout pageName="rental">
    <h2>Rentals</h2>

    <table id="propertiesTable" class="table table-striped">
        <thead>
        <tr>

            <th style="width: 200px;">Student</th>
            <th>Start Date</th>
            <th>End Date</th>
            <th>Show</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${properties.propertyList}" var="property">
            <tr>
               <!--  <td>
                    <spring:url value="/properties" var="propertyUrl">
                        <spring:param name="propertyId" value="${property.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(propertyUrl)}"><c:out value="${property.address} ${property.city}"/></a>
                </td> -->
                <td>
                    <c:out value="${property.address}"/>
                </td>
                <td>
                    <c:out value="${property.city}"/>
                </td><td>
                    <spring:url value="/properties/{propertyId}/rooms" var="propertyUrl">
                   		<spring:param name="propertyId" value="${property.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(propertyUrl)}">Show rooms</a>
                </td>
				<td>
                    <spring:url value="/properties/{propertyId}/show" var="propertyUrl">
                   		<spring:param name="propertyId" value="${property.id}"/>
                    </spring:url>

                    <a href="${fn:escapeXml(propertyUrl)}">Show property</a>

                </td>
<!--
                <td> 
                    <c:out value="${owner.user.username}"/> 
                </td>
                <td> 
                   <c:out value="${owner.user.password}"/> 
                </td> 
-->
                
            </tr>
        </c:forEach>
        </tbody>
    </table> 

    <spring:url value="properties/new" var="addUrl">
        <%-- <spring:param name="ownerId" value="${owner.id}"/> --%>
    </spring:url>
    <sec:authorize access="hasAnyAuthority('owner')">
        <a href="${fn:escapeXml(addUrl)}" class="btn btn-default">Add New Property</a>
    
	</sec:authorize>
    
  
   <spring:url value="/" var="propertyUrlBack"></spring:url>
		<a href="${fn:escapeXml(propertyUrlBack)}" class="btn btn-default">Back to menu</a>
    
</petclinic:layout>
