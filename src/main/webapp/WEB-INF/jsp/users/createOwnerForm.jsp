<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<petclinic:layout pageName="owners">

 	<jsp:attribute name="customScript">
        <script>
            $(function () {
                $("#date").datepicker({dateFormat: 'yyyy-MM-dd'});
            });
        </script>
    </jsp:attribute>
    
    <jsp:body>
    
    <h2>
        <c:if test="${owner['new']}">New </c:if> Owner
    </h2>
    
   
    
    <form:form modelAttribute="owner" class="form-horizontal" id="add-owner-form">
        <div class="form-group has-feedback">
            <petclinic:inputField label="First Name" name="firstName"/>
            <petclinic:inputField label="Last Name" name="lastName"/>
            <petclinic:inputField label="DNI" name="dni"/>
            
        <div class="form-group has-feedback">
            <label class="col-sm-2 control-label">Birthday</label>
             <div class="col-sm-10">
            	<form:input  type="date" class="form-control" path="birthDate" pattern="yyyy-MM-dd" name="birthDate" />
         	 </div>
        </div> 
 
        <div class="form-group has-feedback">
                 <label class="col-sm-2 control-label">Gender</label>
                <div class="col-sm-10">
                    <select name=propertyType class="form-control">
                        <option value=0>Male</option>
                        <option value=1>Female</option>
                    </select>
            	 </div> 
         </div>
         
            <petclinic:inputField label="Email" name="email"/>
            <petclinic:inputField label="Telephone" name="telephone"/>
            <petclinic:inputField label="Username" name="user.username"/>
            <petclinic:inputField label="Password" name="user.password"/>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                    <c:if test="${owner['new']}">
                        <button class="btn btn-default" type="submit">Create Owner</button>
                    </c:if>
                   
            </div>
        </div>
    </form:form>
    </jsp:body>
</petclinic:layout>
