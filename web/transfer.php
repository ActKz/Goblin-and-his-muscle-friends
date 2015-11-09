<?php
if(!isset($_POST['token'])||!isset($_POST['username'])||!isset($_POST['target_account'])||!isset($_POST['amount']))
{
	echo 'contain empty field';
	die();
}
require_once('api/api_eson.php');
$eson = new Eson;
if(!isset($_POST['remark']))
{
	$_POST['remark']="";
}
if(isset($_POST['bank_code']))
{
	$eson->interbank_transfer($_POST['token'],$_POST['username'],$_POST['bank_code'],$_POST['target_account'],$_POST['amount'],$_POST['remark']);
}
else
{
	$eson->in_house_transfer($_POST['token'],$_POST['username'],$_POST['target_account'],$_POST['amount']);
}
?>
