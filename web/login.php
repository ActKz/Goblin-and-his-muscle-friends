<?php
if(!isset($_POST['username'])||!isset($_POST['password']))
{
	echo 'need authentication';
	die();
}
require_once('api/api_eson.php');
$eson = new Eson;
$res = $eson->login($_POST['username'],$_POST['password']);

if($res === False)
{
	http_response_code(403);

}
else
{
	http_response_code(200);
}
return $res;
?>
