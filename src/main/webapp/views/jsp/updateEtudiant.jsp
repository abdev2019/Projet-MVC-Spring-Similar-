
<%
	projet.jee.entities.Etudiant etudiant = (projet.jee.entities.Etudiant)request.getAttribute("etudiant");
%>

<!DOCTYPE html> 
<html> 
<head>
	<meta charset="utf-8">
	<title>Mis a jour d'etudiant</title>
	<link href="../css/bootstrap.min.css" rel="stylesheet">
  	<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
  	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
  	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
 
  </head>
  
  
<body class="alert-info"> 
    <div class="container">
     <jsp:include page="menu.jsp" />
     <div class="card-primary card" >
	<div class="row">
    <div class="col-md-3" >
    	<jsp:include page="leftMenu.jsp" />
   	</div> 
            <div class="col">
            	<h6 class="card-header">Mis a jour d'etudiant Numero <span><%= etudiant.getId() %></span></h6> 
            
          		<div id="contenu">
                <form method="post" action="update" name="f"  > 
                  <h6>Les information de l'etudiant ci-dessous :</h6><br>
                <input value="<%= etudiant.getId() %>" type="hidden" name="id" class="form-control">
              	<table class="table"> 
                <tbody> 
                  <tr>
                    <td>Nom</td>
                    <td>
                      <input value="<%= etudiant.getNom() %>"  name="nom" class="form-control">
                    </td>
                  </tr>
                  <tr>
                    <td>CNE</td>
                    <td>
                      <input value="<%= etudiant.getCne() %>"  name="cne" class="form-control">
                    </td>
                  </tr>
                  <tr>
                    <td>Filiere
                      <br> </td>
                    <td>
                      <input value="<%= etudiant.getFiliere() %>"   name="filiere" class="form-control">
                    </td>
                  </tr>
                  <tr>
                    <td>Age</td>
                    <td>
                     	<input value="<%= etudiant.getAge() %>"  name="age" class="form-control">
                     	<% if( request.getAttribute("errorAge")!=null ){ %>
							<small style="border:none;" colspan="2" class="text-danger">L'age doit etre numeric au moins 18 ans</small> 
						<% } %>
                     </td>
                  </tr> 
                  
                  <tr>
                    <td>&nbsp;</td>
                    <td> 
                    <br>
                      <button class="btn btn-primary" type="submit">Enregistrer<br></button>
                      <button class="btn btn-success" type="reset">Restourer<br></button>
                      	<button class="btn btn-secondary" type="button"
                      	onclick="document.f.nom.value='';document.f.age.value='';document.f.cne.value='';document.f.filiere.value='';">Effacer<br>
                    	</button>
                      <a class="btn btn-danger" href="delete?id=<%= etudiant.getId() %>"
                      	style="float:right">Supprimer<br></button>
                    </td>
                  </tr>
                </tbody>
              </table>
              </form>
              
              </div>
              
            </div>
          </div>
        </div>
      </div> 
   


<!-- Modal -->
<div class="modal fade bg-success" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Confirmation</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <h5 class="text-success">Les informations sont mis a jour !</h5>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button> 
      </div>
    </div>
  </div>
</div>




<% if(request.getAttribute("updateOk")!=null){ %> 
	<script type="text/javascript">$(function() { $('#exampleModal').modal('show'); });</script>
<% } %>

</body>

</html>