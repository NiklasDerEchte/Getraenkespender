<?php
   	namespace App;

   	use Niklas\DAO;
    use Web\Entity\Drink;

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

	$dao->query("SELECT * FROM Drink", [])->each(
		function(Drink $drink) {
			echo "
            <tr>	
                <td>" . $drink->name . "</td>
                <td>" . $drink->description . "</td>
                <td>" . $drink->pos1 . "</td>
                <td>" . $drink->pos2 . "</td>
                <td>" . $drink->pos3 . "</td>
                <td>" . $drink->pos4 . "</td>
                <td>" . $drink->pos5 . "</td>
            </tr>
      	";
		});
	echo "</tbody>";
