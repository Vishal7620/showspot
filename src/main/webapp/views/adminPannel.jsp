<%@page import="com.example.realtime.entities.Admin"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    <%
    
    Admin currentAdmin=(Admin)session.getAttribute("currentAdmin");
    
    %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
 <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css" integrity="sha384-xOolHFLEh07PJGoPkLv1IbcEPTNtaed2xpHsD9ESMhqIYd0nLMwNLD69Npy4HI+N" crossorigin="anonymous">

<title>ShowSpot</title>
<style>
        body {
        background-color: #f8f9fa;
    }
    .navbar {
        background-color: #343a40;
    }
    .navbar-brand, .nav-link {
        color: #fff;
    }
    .form-container {
        max-width: 800px;
        margin: 20px auto;
        padding: 20px;
        background: #fff;
        border-radius: 10px;
        box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
    }
    .card {
        text-align: center;
        height: 100%; /* Ensures cards take full height */
        display: flex;
        flex-direction: column;
    }
    .card-body {
        flex: 1; /* Ensures equal height for card bodies */
    }
    .card-title {
        font-weight: bold;
    }
    .card-container {
        margin-top: 50px;
    }
    .row.card-container > div {
        display: flex;
        align-items: stretch; /* Aligns cards vertically */
    }
        
    </style>
</head>
<body>
<%
if(currentAdmin==null){
%>

<div class="container">
    <div class="row justify-content-center">
        <div class="col-md-12">
            <h1 class="text-center mb-5">Login Failed</h1>
        </div>
    </div>
    </div>
<%
}
else
{
%>


 <!-- Navigation Bar -->
    <nav class="navbar navbar-expand-lg navbar-dark">
        <a class="navbar-brand" href="#">Welcome Admin</a>
        <div class="ml-auto">
            <a class="nav-link" href="/adminLogout">Logout</a>
        </div>
    </nav>

     <!-- Form for Movie, Theater, and Seat Layout Details -->
    <div class="container">
    <div class="row justify-content-center">
        <div class="col-md-12">
            <h1 class="text-center mb-5">Admin Dashboard</h1>
        </div>
    </div>
    <div class="row card-container">
        <!-- Add New Movie Card -->
        <div class="col-md-3">
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">Add New Movie</h5>
                    <p class="card-text">Add details about the latest movies here.</p>
                    <a href="/views/admin/addMovie" class="btn btn-primary">Go to Add Movie</a>
                </div>
            </div>
        </div>
        <!-- Add New Show Card -->
        <div class="col-md-3">
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">Add New Show</h5>
                    <p class="card-text">Schedule shows and manage timings here.</p>
                    <a href="/views/admin/addShow" class="btn btn-primary">Go to Add Show</a>
                </div>
            </div>
        </div>
        <!-- Add Seat Layout and Seats Card -->
        <div class="col-md-3">
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">Add Seat Layout and Seats</h5>
                    <p class="card-text">Configure seat layouts and pricing here.</p>
                    <a href="/views/admin/addSeats" class="btn btn-primary">Go to Add Seats</a>
                </div>
            </div>
        </div>
        <!-- Add New Admin Details Card -->
        <div class="col-md-3">
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">Add New Admin Details</h5>
                    <p class="card-text">Add or update admin information here.</p>
                    <a href="/views/registerAdmin" class="btn btn-primary">Go to Add Admin</a>
                </div>
            </div>
        </div>
    </div>
</div>
    
 
 
     <div class="container">
   
    <div class="row card-container">
        <!-- Add New Movie Card -->
        <div class="col-md-3">
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">Remove Movie</h5>
                    <p class="card-text">Add details about the movies here.</p>
                    <a href="#" class="btn btn-danger" data-toggle="modal" data-target="#movieModal">Go to Remove Movie</a>
                </div>
            </div>
        </div>
        <!-- Add New Show Card -->
        <div class="col-md-3">
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">Remove Show</h5>
                    <p class="card-text">Add details about the show here.</p>
                    <a href="#" class="btn btn-danger" data-toggle="modal" data-target="#showModal">Go to Remove Show</a>
                </div>
            </div>
        </div>
        <!-- Add Seat Layout and Seats Card -->
        <div class="col-md-3">
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">Remove Seat Layout and Seats</h5>
                    <p class="card-text">Add details of seats to remove.</p>
                    <a href="#" class="btn btn-danger" data-toggle="modal" data-target="#seatLayoutModal">Go to Remove Seats</a>
                </div>
            </div>
        </div>
    </div>
</div>
 
 
 
 
 
<!-- Modals -->


<!-- Movie Modal -->
<div class="modal fade" id="movieModal" tabindex="-1" role="dialog" aria-labelledby="movieModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="movieModalLabel">Remove Movie</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form id="movieForm" action="/remove-movie" method="POST">
                    <div class="form-group">
                        <label for="movieId">Movie ID</label>
                        <input type="text" class="form-control" id="movieId" name="movieId" placeholder="Enter Movie ID" required>
                    </div>
                    <button type="submit" class="btn btn-danger">Remove Movie</button>
                </form>
            </div>
        </div>
    </div>
</div>

<!-- Show Modal -->
<div class="modal fade" id="showModal" tabindex="-1" role="dialog" aria-labelledby="showModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="showModalLabel">Remove Show</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form id="showForm" action="/remove-show" method="POST">
                    <div class="form-group">
                        <label for="showId">Show ID</label>
                        <input type="text" class="form-control" id="showId" name="showId" placeholder="Enter Show ID" required>
                    </div>
                    <button type="submit" class="btn btn-danger">Remove Show</button>
                </form>
            </div>
        </div>
    </div>
</div>

<!-- Seat Layout Modal -->
<div class="modal fade" id="seatLayoutModal" tabindex="-1" role="dialog" aria-labelledby="seatLayoutModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="seatLayoutModalLabel">Remove Seat Layout</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form id="seatLayoutForm" action="/remove-seatLayout" method="POST">
                    <div class="form-group">
                        <label for="seatLayoutId">Seat Layout ID</label>
                        <input type="text" class="form-control" id="seatLayoutId" name="seatLayoutId" placeholder="Enter Seat Layout ID" required>
                    </div>
                    <button type="submit" class="btn btn-danger">Remove Seat Layout</button>
                </form>
            </div>
        </div>
    </div>
</div>
 
 <%} %>
    
    
 <!-- JavaScript for Dynamic Fields -->

<script>
    // Example JavaScript for handling form submission
    document.querySelectorAll('form').forEach(form => {
        form.addEventListener('submit', function (event) {
           // event.preventDefault();
            alert(`Form submitted`);
        });
    });
</script>  
    
<script src="https://cdn.jsdelivr.net/npm/jquery@3.5.1/dist/jquery.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-Fy6S3B9q64WdZWQUiU+q4/2Lc9npb8tCaSX9FK7E8HnRr0Jz8D6OP9dO5Vg3Q9ct" crossorigin="anonymous"></script>


<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css" integrity="sha512-Kc323vGBEqzTmouAECnVceyQqyqdsSiqLQISBL29aUW4U/M7pSPA/gEUZQqv1cwx4OnYxTxve5UMg5GT6L4JJg==" crossorigin="anonymous" referrerpolicy="no-referrer" />
    
    
</body>
</html>