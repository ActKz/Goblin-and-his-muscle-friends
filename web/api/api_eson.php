<?php
class eson
{
	function __construct()
	{

		$this->id = "e7ea2661-419b-419c-ac40-8816a4c020d2";
		$this->id_number = array("alice"=>"A100000001",
								 "tom"	=>"A100000002",
								 "mike"	=>"A100000003",
								);
	}
	public function login($username,$password)
	{
		$url = "https://eospu.esunbank.com.tw/esun/bank/hackathon/login?client_id=".$this->id;
		$data = array('username'=>$username, 'password'=>$password);
		$data = json_encode($data);
		$ch = curl_init($url);
		curl_setopt($ch, CURLOPT_POSTFIELDS, $data);
		curl_setopt($ch, CURLOPT_HTTPHEADER, array('Content-Type: application/json'));
		$res = curl_exec($ch);
		if(isset($res['token']))
		{
			$this->token = $res['token'];
		}
		if(curl_getinfo($ch,CURLINFO_CONTENT_TYPE)=="application/json")
		{
			return $res;
		}
		else
		{
			return  False;
		}
		//return $res;
	}
	public function in_house_transfer($token,$username,$target_account,$amount)
	{
		$url = "https://eospu.esunbank.com.tw/esun/bank/hackathon/accounts/".$username."/in_house_transfer?client_id=".$this->id;
		$data = array("payee_account_id"=>$username,"transaction_amount"=>$amount,"id_number"=>$this->id_number[$username]);
		$data = json_encode($data);
		$ch = curl_init($url);
		curl_setopt($ch, CURLOPT_POSTFIELDS, $data);
		curl_setopt($ch, CURLOPT_HTTPHEADER, array('Content-Type: application/json',"Authorization: ".$token));
		$res = curl_exec($ch);
		return $res;
	}
	public function interbank_transfer($token,$username,$bank_code,$target_account,$amount,$remark)
	{
		$url = "https://eospu.esunbank.com.tw/esun/bank/hackathon/accounts/".$username."/interbank_transfer?client_id=".$this->id;
		$data = array("payee_bank_code"=>$bank_code,"payee_account_id"=>$target_account,"transaction_amount"=>$amount,"id_number"=>$this->id_number[$username],"remark"=>$remark);
		$data = json_encode($data);
		$ch = curl_init($url);
		curl_setopt($ch, CURLOPT_POSTFIELDS, $data);
		curl_setopt($ch, CURLOPT_HTTPHEADER, array('Content-Type: application/json',"Authorization: ".$token));
		$res = curl_exec($ch);
		return $res;
	}
}
?>
