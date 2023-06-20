<?php

$connection = new mysqli("localhost", "root", "", "online_store_db");

$fetchProductsCommand = $connection->prepare("select* from electronic_products where brand=?");
$fetchProductsCommand->bind_param("s", $_GET["brand"]);
$fetchProductsCommand->execute();

$epResults = $fetchProductsCommand->get_result();
$epArray = array();

while($row = $epResults->fetch_assoc()){
    array_push($epArray, $row);
}

echo json_encode($epArray); 


