<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<ul class="pagination">
	<c:if test="${pageWrapper.currentPage != 1}">
		<c:if
			test="${pageWrapper.message=='welcome' or pageWrapper.message=='welcomeDelete' or pageWrapper.message=='welcomeEdit' or pageWrapper.message=='welcomeAdd'}">
			<li><a
				href="/ProjetWeb/dashboard?currentPage=${pageWrapper.currentPage - 1}${!empty pageWrapper.orderBy ? '&orderBy='.concat(pageWrapper.orderBy) : ''}"> <i class="fa fa-caret-left"></i> <spring:message
						code="previous" /></a></li>
		</c:if>
		<c:if test="${pageWrapper.message=='welcomeSelect'}">
			<li><a
				href="/ProjetWeb/dashboard?searchComputer=${pageWrapper.searchComputer}&searchCompany=${pageWrapper.searchCompany}&currentPage=${pageWrapper.currentPage - 1}${!empty pageWrapper.orderBy ? '&orderBy='.concat(pageWrapper.orderBy) : ''}"> <i class="fa fa-caret-left"></i> <spring:message
						code="previous" /></a></li>
		</c:if>
	</c:if>
	<c:forEach begin="1" end="${pageWrapper.nbrOfPages}" var="i">
		<c:choose>
			<c:when test="${pageWrapper.currentPage eq i}">
				<li><a contenteditable="false"
					style="background-color: activecaption;">${i}</a></li>
			</c:when>
			<c:otherwise>
				<c:if
					test="${pageWrapper.message=='welcome' or pageWrapper.message=='welcomeDelete' or pageWrapper.message=='welcomeEdit' or pageWrapper.message=='welcomeAdd'}">
					<li><a
						href="/ProjetWeb/dashboard?currentPage=${i}${!empty pageWrapper.orderBy ? '&orderBy='.concat(pageWrapper.orderBy) : ''}">${i}</a></li>
				</c:if>
				<c:if test="${pageWrapper.message=='welcomeSelect'}">
					<li><a
						href="/ProjetWeb/dashboard?searchComputer=${pageWrapper.searchComputer}&searchCompany=${pageWrapper.searchCompany}&currentPage=${i}${!empty pageWrapper.orderBy ? '&orderBy='.concat(pageWrapper.orderBy) : ''}">${i}</a></li>
				</c:if>
			</c:otherwise>
		</c:choose>
	</c:forEach>
	<c:if test="${pageWrapper.currentPage lt pageWrapper.nbrOfPages}">
		<c:if
			test="${pageWrapper.message=='welcome' or pageWrapper.message=='welcomeDelete' or pageWrapper.message=='welcomeEdit' or pageWrapper.message=='welcomeAdd'}">
			<li><a
				href="/ProjetWeb/dashboard?currentPage=${pageWrapper.currentPage + 1}${!empty pageWrapper.orderBy ? '&orderBy='.concat(pageWrapper.orderBy) : ''}"><spring:message
						code="next" /> <i class="fa fa-caret-right"></i>  </a></li>
		</c:if>
		<c:if test="${pageWrapper.message=='welcomeSelect'}">
			<li><a
				href="/ProjetWeb/dashboard?searchComputer=${pageWrapper.searchComputer}&searchCompany=${pageWrapper.searchCompany}&currentPage=${pageWrapper.currentPage + 1}${!empty pageWrapper.orderBy ? '&orderBy='.concat(pageWrapper.orderBy) : ''}"><spring:message
						code="next" text="next" /> <i class="fa fa-caret-right"></i> </a></li>
		</c:if>
	</c:if>
</ul>