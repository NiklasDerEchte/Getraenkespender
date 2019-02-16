<?php
    namespace App;
    require_once __DIR__ . "/../src/bootstrap.php";
    require_once __DIR__ . "/../resources/Config.php";
    session_start();
?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/bootstrap.css">
    <title><?php echo Config::$WEB_TITLE ?></title>
</head>
<body>
<?php include "template/navbar.php"; ?>


<div class="container">
    <div class="jumbotron">
        <h1 class="display-3">Willkommen auf <?php echo Config::$WEB_TITLE ?></h1>
    </div>
	 <div class="row">
  <div class="col-lg-6">
		<fieldset>
		<?php include "template/newDrink.php"; ?>
		</fieldset>
  </div>
  <div class="col-lg-6">
<fieldset>
		<table class="table table-hover">
			<?php include "loadDBList.php"; ?>
		</table>
</fieldset>

	
  </div>
</div>	
	
</div>

<script src="js/bootstrap.bundle.js"></script>
<script src="js/bootstrap.js"></script>
</body>
</html>