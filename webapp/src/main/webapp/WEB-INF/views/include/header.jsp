<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>

<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="css/main.css" rel="stylesheet" media="screen">
<link
	href="//netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css"
	rel="stylesheet">
</head>
<body>
	<nav class="navbar navbar-inverse">
		<div class="container-fluid">
			<h1>
				<a href="index.jsp" class="menu" > <spring:message
						code="application" />
				</a> <span class="language"> <a
					href="?language=en${!empty dtoWrapper.computerDTO.id ? '&id='.concat(dtoWrapper.computerDTO.id) : ''}${!empty pageWrapper.currentPage ? '&currentPage='.concat(pageWrapper.currentPage) : ''}${!empty pageWrapper.searchComputer ? '&searchComputer='.concat(pageWrapper.searchComputer) : ''}${!empty pageWrapper.searchCompany ? '&searchCompany='.concat(pageWrapper.searchCompany) : ''}${!empty pageWrapper.orderBy ? '&orderBy='.concat(pageWrapper.orderBy) : ''}"
					class="menu">English</a> | <a
					href="?language=fr${!empty dtoWrapper.computerDTO.id ? '&id='.concat(dtoWrapper.computerDTO.id) : ''}${!empty pageWrapper.currentPage ? '&currentPage='.concat(pageWrapper.currentPage) : ''}${!empty pageWrapper.searchComputer ? '&searchComputer='.concat(pageWrapper.searchComputer) : ''}${!empty pageWrapper.searchCompany ? '&searchCompany='.concat(pageWrapper.searchCompany) : ''}${!empty pageWrapper.orderBy ? '&orderBy='.concat(pageWrapper.orderBy) : ''}"
					class="menu">Français</a>
				</span>
			</h1>
		</div>
	</nav>