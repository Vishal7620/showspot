<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
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
    </style>
</head>
<body>
 <!-- Navigation Bar -->
    <nav class="navbar navbar-expand-lg navbar-dark">
        <a class="navbar-brand" href="#">Welcome Admin</a>
        <div class="ml-auto">
            <a class="nav-link" href="#">Logout</a>
            <a class="btn btn-primary ml-2" href="#">New Admin</a>
        </div>
    </nav>

     <!-- Form for Movie, Theater, and Seat Layout Details -->
    <div class="form-container">
        <h2 class="text-center mb-4">Add New Show Details</h2>
        <form method="post" action="/admin/addShow">
        <h4>Theater Show Information</h4>
            <div class="form-group">
                <label for="theaterName">Theater Name</label>
                <input type="text" class="form-control" id="theaterName" name="theaterName" placeholder="Enter theater name" required>
            </div>
            <div class="form-group">
                <label for="address">Address</label>
                <input type="text" class="form-control" id="address" name="address" placeholder="Enter theater address" required>
            </div>
                <div class="form-group">
        <label for="movieDate">Movie Date</label>
        <input type="date" class="form-control" id="movieDate" name="movieDate" required>
    </div>
    
    <div class="form-group">
        <label for="movieTime">Movie Time</label>
        <input type="time" class="form-control" id="movieTime" name="movieTime" required>
    </div>
            <div class="form-group">
                <label for="movieName">Movie Name</label>
                <input type="text" class="form-control" id="movieName" name="movieName" placeholder="Enter movie name playing in this theater" required>
            </div>
            
            <div class="form-group">
                <label for="movieName">Seat Layout Name</label>
                <input type="text" class="form-control" id="layoutName" name="layoutName" placeholder="" required>
            </div>
            

     
            <button type="submit" class="btn btn-success btn-block">Submit</button>
        </form>
    </div>
    
 <!-- JavaScript for Dynamic Fields -->
       
<script src="https://cdn.jsdelivr.net/npm/jquery@3.5.1/dist/jquery.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-Fy6S3B9q64WdZWQUiU+q4/2Lc9npb8tCaSX9FK7E8HnRr0Jz8D6OP9dO5Vg3Q9ct" crossorigin="anonymous"></script>


<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css" integrity="sha512-Kc323vGBEqzTmouAECnVceyQqyqdsSiqLQISBL29aUW4U/M7pSPA/gEUZQqv1cwx4OnYxTxve5UMg5GT6L4JJg==" crossorigin="anonymous" referrerpolicy="no-referrer" />
    
    
</body>
</html>