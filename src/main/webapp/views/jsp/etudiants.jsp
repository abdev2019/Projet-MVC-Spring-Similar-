<%@page import="projet.jee.entities.Etudiant"%>
<%@page import="projet.jee.web.Model"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<% Model model = (Model)request.getAttribute("model"); %>


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
<body class="container alert-info"   > 
 <jsp:include page="menu.jsp" />

<div class="card"  >
	
	<div class="row">
    <div class="col-md-3"  >
    	<jsp:include page="leftMenu.jsp" />
   	</div>
   	
    <div class="col">
    <h6 class="card-header">Liste des etudiants</h6> 
    	<div id="contenu" style="max-height:450px; overflow-y:scroll">
		<table class="table">
			<thead>
				<tr>
					<th>ID</th>
					<th>Nom</th>
					<th>CNE</th>
					<th>Filliere</th>
					<th>Age</th> 
					<th></th>
				</tr>
			</thead>
			<tbody>
				
				<% for(Etudiant etd : model.getListeEtudiants()) { %>
				<tr>
					<td><%= etd.getId() %></td>
					<td><%= etd.getNom() %></td>
					<td><%= etd.getCne() %></td>
					<td><%= etd.getFiliere() %></td>
					<td><%= etd.getAge() %></td>  
					<td>
						<a class="btn-info btn-sm" href="afficher?id=<%= etd.getId() %>">SHOW</a>
						<a class="btn-danger btn-sm" href="delete?id=<%= etd.getId() %>">DEL</a>
						<a class="btn-primary btn-sm" href="update?id=<%= etd.getId() %>">UPDATE</a>
					</td>
				</tr>
				<% } %>
				
			</tbody>
		</table>
		</div>
		</div>
		</div>
 

</div>


<!-- Modal -->
<div class="modal fade bg-danger" id="modalDeleteOk" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Confirmation</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <h4 class='text-danger'>L'etudiant est supprimé !</h4> 
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button> 
      </div>
    </div>
  </div>
</div>
<% if(request.getAttribute("deleteOk")!=null){ %> 
	<script type="text/javascript">$(function() { $('#modalDeleteOk').modal('show'); });</script>
<% } %>


</html>







