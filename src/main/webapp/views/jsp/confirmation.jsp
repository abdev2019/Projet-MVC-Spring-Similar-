
<%
	projet.jee.entities.Etudiant etudiant = (projet.jee.entities.Etudiant)request.getAttribute("etudiant");
%>


<!DOCTYPE html> 
<html> 
<head>
	<meta charset="utf-8">
	<title>Confirmation</title>
	<link href="../css/bootstrap.min.css" rel="stylesheet">
  	<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
  	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
  	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
 
  </head>
<body class="alert-info">
  <div >
    <div class="container">
     <jsp:include page="menu.jsp" />
     <div class="card-primary card" >
	<div class="row">
    <div class="col-md-3" >
    	<jsp:include page="leftMenu.jsp" />
   	</div> 
            <div class="col">
            	<h6 class="card-header">Etudiant Numero <span><%= etudiant.getId() %></span></h6> 
           		<div id="contenu">
                <h6>Les information de l'etudiant ci-dessous :</h6><br>
                <input value="<%= etudiant.getId() %>" type="hidden" name="id" class="form-control">
              	<table class="table"> 
                <tbody> 
                	<tr style="border-bottom:solid">
                    <td>Propriete</td>
                    <td>
                      Valeur
                    </td>
                  	</tr>
                  	
                	<tr>
                    <td>ID</td>
                    <td>
                      <%= etudiant.getId() %>
                    </td>
                  	</tr>
                  <tr>
                    <td>Nom</td>
                    <td>
                      <%= etudiant.getNom() %>
                    </td>
                  </tr>
                  <tr>
                    <td>CNE</td>
                    <td>
                      <%= etudiant.getCne() %>
                    </td>
                  </tr>
                  <tr>
                    <td>Filiere
                      <br> </td>
                    <td>
                      <%= etudiant.getFiliere() %>
                    </td>
                  </tr>
                  <tr>
                    <td>Age</td>
                    <td>
                     	<%= etudiant.getAge() %>
                     </td>
                  </tr>  
                </tbody>
              	</table>
              	<a class="btn-danger btn" href="delete?id=<%= etudiant.getId() %>">DELETE</a>
				<a class="btn-primary btn" href="update?id=<%= etudiant.getId() %>">UPDATE</a> 
              	</div>
           	</div>
              
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
   
   


<!-- Modal -->
<div class="modal fade bg-success" id="modalAddOk" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Confirmation</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
       	<h4 class='text-success'>Un nouveau etudiant est ajouté !</h4>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button> 
      </div>
    </div>
  </div>
</div>
<% if(request.getAttribute("addOk")!=null){ %> 
	<script type="text/javascript">$(function() { $('#modalAddOk').modal('show'); });</script>
<% } %>
</body>

</body>

</html>