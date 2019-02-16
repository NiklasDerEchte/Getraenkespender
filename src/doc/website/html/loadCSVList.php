<?php
    require_once __DIR__ . "/../src/bootstrap.php";
    require_once __DIR__ . "/../resources/Config.php";

    $csvFile = fopen(Config::$CSV_PATH, 'r');
	$line = fgetcsv($csvFile); //table header
?>
<thead>
   	<tr>
	<?php
	foreach($line as $number => $title) {
	?>
	<th><?php echo $title ?></th>
	<?php
	}
	?>
	</tr>
</thead>
<tbody>
	<?php
    	while (($line = fgetcsv($csvFile)) !== FALSE) {
	?>
		<tr>
		<?php
		foreach($line as $key => $value) {
		?>
		<td><?php echo $value ?></td>
		<?php
		}
		?>
      	</tr>
		<?php
	}
	fclose($csvFile);
	?>
</tbody>

