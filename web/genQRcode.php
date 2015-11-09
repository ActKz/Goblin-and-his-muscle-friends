<?php
require_once('/usr/share/phpqrcode/qrlib.php');
if(!isset($_POST['username'])||!isset($_POST['amount']))
{
	echo 'contain empty fields';
	die();
}
$bank_code = "";
if(isset($_POST['bank_code']))
{
	$bank_code = $_POST['bank_code'];
}
$data = "{'username':'".$_POST['username']."','amount':'".$_POST['amount']."','bank_code':'".$_POST['bank_code']."'}";
$name = time().".png";
QRcode::png($data,$name,QR_ECLEVEL_L,4);
echo "http://actkz.nctucs.net/".$name;
?>
