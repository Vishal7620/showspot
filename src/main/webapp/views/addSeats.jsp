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
        <form method="post" action="/admin/addSeats">
       <h4>Seat Layout Information</h4>
             
            <!-- 1st -->
            
            <div class="form-group">
                <label for="categoryName1">Category Name</label>
                <input type="text" class="form-control" id="categoryName1" name="categoryName1" placeholder="Enter category name" required>
            </div>
            
            
             <div class="form-group">
        <label for="rowCount1">Number of Rows</label>
        <input type="text" class="form-control" id="rowCount1" name="rowCount1" required>
           </div>
           
            <div class="form-group">
        <label for="columnCount1">Number of Columns</label>
        <input type="text" class="form-control" id="columnCount1" name="columnCount1"  required>
           </div>
           
             <div class="form-group">
        <label for="price1">Price</label>
        <input type="text" class="form-control" id="price1" name="price1"  required>
           </div>
           
           
           <!-- 2nd -->
           
            <div class="form-group">
                <label for="categoryName2">Category Name 2 (Optional)</label>
                <input type="text" class="form-control" id="categoryName2" name="categoryName2" placeholder="Enter category name" >
            </div>
            
            
             <div class="form-group">
        <label for="rowCount2">Number of Rows (Optional)</label>
        <input type="text" class="form-control" id="rowCount2" name="rowCount2" >
           </div>
           
            <div class="form-group">
        <label for="columnCount2">Number of Columns (Optional)</label>
        <input type="text" class="form-control" id="columnCount2" name="columnCount2"  >
           </div>
           
             <div class="form-group">
        <label for="price2">Price (Optional)</label>
        <input type="text" class="form-control" id="price2" name="price2"  >
           </div>
           
           
           
           <!-- 3rd -->
            <div class="form-group">
                <label for="categoryName3">Category Name (Optional)</label>
                <input type="text" class="form-control" id="categoryName3" name="categoryName3" placeholder="Enter category name" >
            </div>
            
            
             <div class="form-group">
        <label for="rowCount3">Number of Rows (Optional)</label>
        <input type="text" class="form-control" id="rowCount3" name="rowCount3" >
           </div>
           
            <div class="form-group">
        <label for="columnCount3">Number of Columns (Optional)</label>
        <input type="text" class="form-control" id="columnCount3" name="columnCount3" >
           </div>
           
             <div class="form-group">
        <label for="price3">Price (Optional)</label>
        <input type="text" class="form-control" id="price3" name="price3">
           </div>


         <!-- Comma-Separated Seat Numbers -->
         <div class="form-group">
             <label for="seatNumbers1">Blanked Seat Numbers (Comma-Separated)</label>
             <input type="text" class="form-control" id="seatNumbers1" name="seatNumbers1" 
             placeholder="e.g., 1,2,3,4" 
             pattern="^(\d+)(,\d+)*$" 
             title="Enter numbers separated by commas, e.g., 1,2,3,4" 
             required>
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