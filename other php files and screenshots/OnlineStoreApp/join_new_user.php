<?php

$connection = new mysqli("localhost", "root", "", "online_store_db");


//$sqlCommand = $connection->prepare("insert into app_users_table values (?,?,?)");
//$sqlCommand->bind_param("sss", $_GET["email"], $_GET["username"], $_GET["pass"] );
//$sqlCommand->execute();

//if an email already exists do not add the user again
$emailcheckSQLCommand = $connection->prepare("select* from app_users_table where email=?");
$emailcheckSQLCommand->bind_param("s", $_GET["email"] );
$emailcheckSQLCommand->execute();
$emailResult = $emailcheckSQLCommand->get_result();

if($emailResult->num_rows==0){
    $sqlCommand = $connection->prepare("insert into app_users_table values (?,?,?)");
    $sqlCommand->bind_param("sss", $_GET["email"], $_GET["username"], $_GET["pass"] );
    $sqlCommand->execute();
    echo "Registration Successful!"; 
}else{
    echo "a user with the same email address already exists";
}
