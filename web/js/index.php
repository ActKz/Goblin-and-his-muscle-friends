<?php
$pid=array('200016','562125','902309','353131','674535','367753');
$name=array('2K16','GTA5','PSP','iPhone 6s','SONY Z5','Surface book pro');
$price=array(1790,1790,3990,25800,19900,99999);
$description=array('a','b','c','d','e','f');
?>


<html>
	<head>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
		<link rel="stylesheet" type="text/css" href="css/semantic.min.css">
		<link rel="stylesheet" type="text/css" href="css/main.css">
		<script src="js/semantic.min.js"></script>
		
	</head>
	<body>	
		<div class="all">
			<div class="top">
				<img class="ui medium circular image" src="meichuhackathon.png">
				<div>
					<h1 class="ui header">We are Goblin and his muscle friends!</h1>
					<h1 class="ui header">Do you wanna buy something to enlarge your muscle?</h1>
				</div>	
			</div>	
			
			<div class="bottom">		

				<table class="ui celled table">
					<thead>
						<tr>
							<th>Pid</th>				
							<th>Product Name</th>
							<th>Price</th>
							<th>Description</th>
							<th>Action</th>
						</tr>
					</thead>
					<tbody>
						<?php 
							for($i=0;$i<count( $pid );$i++)
							{
								echo "<tr>";
								echo "<td>$pid[$i]</td>";
								echo "<td>$name[$i]</td>";
								echo "<td>$price[$i]</td>";
								echo "<td>$description[$i]</td>";
								echo "<td><button class='ui button' onclick='open()'>Pay</button></td>";
								echo "</tr>";
							}
						?>	
					</tbody>
				</table>
			</div>
		</div>
	</body>
<script>
	function open(){
$('.uimodal').modal('show');
}
</script>
<div class="uimodal">
<h1>456566</h1>
</div>

</div>
</html>
