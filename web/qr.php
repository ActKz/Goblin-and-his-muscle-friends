<?php
require_once('/usr/share/phpqrcode/qrlib.php');
$fp = fopen("qr.png","w");
fwrite($fp,QRcode::png('PHP QRVVVVVVVVVVVV','qr.png'));
fclose($fp);
/*if(class_exists('QRcode'))
{
	QRimage::png('PHP QR');
}
else
{
echo 'zzz';
}*/
?>
