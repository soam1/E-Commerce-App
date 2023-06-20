<?php

$connection = new mysqli("localhost", "root", "", "online_store_db");
$sqlCommand = $connection->prepare("SELECT id, name, price, email, amount FROM temporary_place_order INNER JOIN electronic_products ON electronic_products.id=temporary_place_order.product_id where email=?");
$sqlCommand->bind_param("s", $_GET["email"]);
$sqlCommand->execute();

$temporderarray = array();
$sqlResult = $sqlCommand->get_result();

while($row= $sqlResult->fetch_assoc()){
    array_push($temporderarray, $row);
}

echo json_encode($temporderarray);

