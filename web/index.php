<?php
$pid=array('200016','562125','902309','353131','674535','367753');
$name=array('2K16','GTA5','PSP','iPhone 6s','SONY Z5','Surface book');
$price=array(1790,1790,3980,24500,22900,48800);
$description=array('Let me shoot on your face!',
					'Please don\'t kill me.',
					'Prepare money for more glasses.',
					'The most expensive phone in the world!',
					'Highest quality camera.',
					'The new 13.5-inch Surface Book is the ultimate laptop.');
$pic=array('2K16.jpg','GTA5.jpg','PSP.jpg','iphone6s.jpg','Z5.jpg','surface.jpg');
$total=0;
?>


<html>
	<head>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
		<script src="js/semantic.min.js"></script>
		<script>var idx=[];</script>
		<link rel="stylesheet" type="text/css" href="css/semantic.min.css">
		<link rel="stylesheet" type="text/css" href="css/main.css">
		<link rel="stylesheet" type="text/css" href="css/icon.min.css">
	</head>
	<style>
		#header ,#footer {
			background-color: #721919;
			height:2em;
		}
	</style>
	<body>
		<div id='header'></div>
		<div class="all">
			<div class="top">
				<img src="goblin.png" style='width:200px;height:200px'>
				<div>
					<h6 class="ui header"><br></br></h6>
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
							<th>Picture</th>
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
								echo "<td ><img data-html='<img src=$pic[$i]>' src='$pic[$i]'></td>";
								echo "<td><button class='add ui button' id='".$i."' value=".$price[$i].">Add</button></td>";
								echo "</tr>";
								?>
								<script>idx.push(0);</script>
								<?php
							}
						?>	
					</tbody>
				</table>

				<div class='inline' style='display:block;float:right'>
					<div class="ui buttons" id='buydiv' >
						<button class='ui button active' >Reset</button>
						<div class="or"></div>
						<button class='ui positive button' id='buy'>Buy</button>
					</div>
				</div>
				<div class='inline' style='display:inline-block;float:right'>
					<div class="ui tiny statistic" id='total'>
<div class='value'><i class='dollar icon'></i>0</div><div class='label'>Total</div>
					</div>
				</div>
				</div>

			</div>
		</div>
		<div id="footer" style='margin-bottom:0px;'>
			<h3>Copyright © 2015 - 2015 by Goblin Team. All Rights reserved.</h3>
		</div>
	</body>
<script>
var total=0;
$('.add').on('click',function(){
	if($(this).hasClass('blue'))
	{
		total=total-parseInt($(this).val());
		$(this).removeClass('blue');
		$('#total').html("<div class='value'><i class='dollar icon'></i>"+total+" </div><div class='label'>Total</div>");
	}
	else
	{
		total=total+parseInt($(this).val());
		$(this).addClass('blue');
		$('#total').html("<div class='value'><i class='dollar icon'></i>"+total+" </div><div class='label'>Total</div>");
	}
});
$('#buydiv').on('click',function(){
	$('.add').removeClass('blue');
	total=0;
		$('#total').html("<div class='value'><i class='dollar icon'></i>"+total+" </div><div class='label'>Total</div>");
});
$('.ui.modal').modal();
$('#buy').on('click', function(){
	if(total!=0)
	{
		$.post("./genQRcode.php",{username:'alice',bank_code:'809',amount:total},function(data){
			$('.qr').remove();
			$('.ui.modal').prepend("<img class='qr' src="+data+">");
	});
		$('.buyQRcode').modal('show');
	}
	else
	{
			$('.qr').remove();
		$('.nothing').modal('show');
	}
});

$('td')
  .popup()
;

$('td img')
.popup({
  hoverable: true,
  delay: {
	show: 100,

  }
});
/*
$('.pic').on('click',function(){
	var picture=$(this).val();
	$('.pics').remove();
	$('.showpic').prepend("<img class='pics' src="+picture+">");
	$('.showpic').modal('show');
});*/
</script>
<div class="ui modal small buyQRcode" style='text-align:center;'>
	<h1>拿起手機掃描QR code</h1>
</div>
<div class="ui modal small nothing" style='text-align:center;'>
	<h1>Nothing Added!</h1>
</div>

<div class="ui modal showpic" style='text-align:center;'>
</div>


</html>
