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
        <h2 class="text-center mb-4">Add New Movie Details</h2>
        <form method="post" action="/admin/addMovie" enctype="multipart/form-data">
            <!-- Movie Information -->
            <!-- Movie Information -->
            <h4>Movie Information</h4>
            <div class="form-group">
                <label for="title">Movie Title</label>
                <input type="text" class="form-control" id="title" name="title" placeholder="Enter movie title" required>
            </div>
            <div class="form-group">
                <label for="genre">Genre</label>
                <select class="form-control" id="genre" name="genre" required>
                    <option value="Romance">Romance</option>
                    <option value="Drama">Drama</option>
                    <option value="Action">Action</option>
                    <option value="Comedy">Comedy</option>
                    <option value="Thriller">Thriller</option>
                    <option value="Other">Other</option>
                </select>
            </div>
            <div class="form-group">
                <label for="location">Location</label>
                <input type="text" class="form-control" id="location" name="location" placeholder="Enter location" required>
            </div>
            <div class="form-group">
                <label for="releaseDate">Release Date</label>
                <input type="date" class="form-control" id="releaseDate" name="releaseDate" required>
            </div>
              
           <div class="form-group">
                <label for="releaseDate">Language</label>
                <input type="text" class="form-control" id="Language" name="Language" required>
            </div>
            <div class="form-group">
                <label for="duration">Duration (in minutes)</label>
                <input type="number" class="form-control" id="duration" name="duration" placeholder="Enter duration" required>
            </div>
            <div class="form-group">
                <label for="posterName">Poster Name</label>
                <input type="text" class="form-control" id="posterName" name="posterName" placeholder="Enter poster name" required>
            </div>
             <!-- <div class="form-group">
                <label for="poster">Upload Poster</label>
                <input type="file" class="form-control-file" id="poster" name="poster" accept="image/*" required>
            </div> --> 
            <div class="form-group">
                <label for="about">About the Movie</label>
                <textarea class="form-control" id="about" name="about" rows="4" placeholder="Write a short description about the movie" required></textarea>
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