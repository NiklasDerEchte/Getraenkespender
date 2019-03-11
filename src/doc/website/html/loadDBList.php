<?php
   	namespace App;

   	use Niklas\DAO;
    use Web\Entity\CustomDrink;

	$dao = DAO::Get();
	echo "
    <thead>
        <tr>
            <th>Name</th>
            <th>Beschreibung</th>
            <th>Pos1</th>
            <th>Pos2</th>
            <th>Pos3</th>
            <th>Pos4</th>
            <th>Pos5</th>
        </tr>
    </thead>
	";

	echo "<tbody>";

	$dao->query("SELECT * FROM CustomDrink", [])->each(
		function(CustomDrink $drink) {
			echo "
            <tr>	
                <td>" . $drink->name . "</td>
                <td>" . $drink->description . "</td>
                <td>" . $drink->volumeCl1 . "</td>
                <td>" . $drink->volumeCl2 . "</td>
                <td>" . $drink->volumeCl3 . "</td>
                <td>" . $drink->volumeCl4 . "</td>
                <td>" . $drink->volumeCl5 . "</td>
            </tr>
      	";
		});
	echo "</tbody>";
