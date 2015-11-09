<html> 

<body> 

<div style="position: absolute; width: 185px; height: 243px; z-index: 1; left: 428px; top: 18px" id="layer1"> 

<div align="left" onmouseover="showPic(0);" onmouseout="showPic();"> 
滑鼠移到文字變圖片 
</div> 

<div id="pic"> 
</div> 

</div> 

<script type="text/javascript"> 

var Imgs = new Array(); //設定圖片的網址 
Imgs[0] = "http://w2.hkmalls.com/rc/1.jpg"; 

function showPic(picUrl) 
{ show = document.getElementById("pic"); 

if (arguments.length>0) //有引數就顯示圖片，沒有引數就隱形。 
show.innerHTML = '<img src="' + Imgs[picUrl] + '" border=0>'; 
else 
show.innerHTML = ''; 
} 

</script> 