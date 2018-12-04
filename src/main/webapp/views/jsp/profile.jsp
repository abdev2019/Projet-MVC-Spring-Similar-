<%@page import="projet.jee.entities.User"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<% User user = (User)request.getSession().getAttribute("user"); %>



<!--  -->
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>Index</title>
	<link href="../css/bootstrap.min.css" rel="stylesheet"> 
	<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
  	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
  	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
 
</head>
<body class="container alert-info" > 
 <jsp:include page="menu.jsp" />

<div class="card-primary card" >
	<div class="row">
    <div class="col-md-3"  >
    	<jsp:include page="leftMenu.jsp" />
   	</div>
   	
    <div class="col">
    	<h6 class="card-header">Mon profile</h6> 
    	<div id="contenu">
		<table class="table"> 
			<tbody> 
				<tr>
					<th colspan="2">
						<center>
						<div style="border:solid;border-radius:100%; width:80px; height: 80px; background: red"></div>
						</center>
					</th>  
				</tr>
				
				<tr>
					<th>ID</th>
					<td><%= user.getId() %></td> 
				</tr>
				
				<tr>
					<th>Username</th>
					<td><%= user.getUsername() %></td>
				</tr>
				
				<tr>
					<th>Password</th>
					<td><%= user.getPassword() %></td>
				</tr>
				
				<tr>
					<th>
						<a class="nav-link" href="deconnecter">Deconnecter
			              <br>
			            </a>
					</th>
					<td></td>
				</tr>
				
			</tbody>
		</table>
		</div>
		</div>
		</div>
 

</div>
  
</body>
</html>







