<?php
namespace App;
use Niklas\DAO;
use Web\Entity\CustomDrink;


if(isset($_POST['save'])) {
    $dao = DAO::Get();
    $drink = new CustomDrink();

    if(isset($_POST['inputDrinkName'])) {
        $drink->name = $_POST['inputDrinkName'];
    }

    if(isset($_POST['inputDrinkDescription'])) {
        $drink->description = $_POST['inputDrinkDescription'];
    }

    if(isset($_POST['bottle-1'])) {
        $drink->volumeCl1 = $_POST['bottle-1'];
    }

    if(isset($_POST['bottle-2'])) {
        $drink->volumeCl2 = $_POST['bottle-2'];
    }

    if(isset($_POST['bottle-3'])) {
        $drink->volumeCl3 = $_POST['bottle-3'];
    }

    if(isset($_POST['bottle-4'])) {
        $drink->volumeCl4 = $_POST['bottle-4'];
    }

    if(isset($_POST['bottle-5'])) {
        $drink->volumeCl5 = $_POST['bottle-5'];
    }

    $dao->store($drink);

    header("Location: ../index.php");
}
?>
<div class='form-group'>
    <form method='post'>
	<div class='form-group'>
		<label class='col-form-label' for='inputDrinkName'>Name</label>
		<input class='form-control' id='inputDrinkName' type='text' name="inputDrinkName">
	</div>

   	<div class='form-group'>
      		<label for='inputDrinkDescription'>Beschreibung</label>
      		<textarea class='form-control' id='inputDrinkDescription' rows='3' name="inputDrinkDescription"></textarea>
    	</div>

  	<label class='control-label'>Flaschen Position und Mengenangaben in ml</label>
  	<div class='form-group'>
    	<div class='input-group mb-3'>
      		<div class='input-group-prepend'>
        	<span class='input-group-text'>Flasche 1</span>
      	</div>
      	<input class='form-control' aria-label='Amount' type='text' name='bottle-1'>
    	</div>

	<div class='form-group'>
    	<div class='input-group mb-3'>
      		<div class='input-group-prepend'>
        	<span class='input-group-text'>Flasche 2</span>
      	</div>
      	<input class='form-control' aria-label='Amount' type='text' name='bottle-2'>
    	</div>

	<div class='form-group'>
    	<div class='input-group mb-3'>
      		<div class='input-group-prepend'>
        	<span class='input-group-text'>Flasche 3</span>
      	</div>
      	<input class='form-control' aria-label='Amount' type='text' name='bottle-3'>
    	</div>

	<div class='form-group'>
    	<div class='input-group mb-3'>
      		<div class='input-group-prepend'>
        	<span class='input-group-text'>Flasche 4</span>
      	</div>
      	<input class='form-control' aria-label='Amount' type='text' name='bottle-4'>
    	</div>

	<div class='form-group'>
    	<div class='input-group mb-3'>
      		<div class='input-group-prepend'>
        	<span class='input-group-text'>Flasche 5</span>
      	</div>
      	<input class='form-control' aria-label='Amount' type='text' name='bottle-5'>
    	</div>

	<button type='submit' class='btn btn-success' name='save'>Speichern</button>
        </form>
</div>
