<?php

use Illuminate\Support\Facades\Route;

/*
|--------------------------------------------------------------------------
| Web Routes
|--------------------------------------------------------------------------
|
| Here is where you can register web routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| contains the "web" middleware group. Now create something great!
|
*/

Route::get('/', function () {
    return view('home', [
        "title" => "home"
    ]);
});

Route::get('/about', function () {
    return view("about", [
        "title" => "about",
        "name" => "Sam",
        "email" => "samzuhal095@gmail.com",
        "nohp" => "082250550032"
    ]);
});

Route::get('/post', function () {

    $bog_post = [
        [
            "title" => "Post1",
            "author" => "Sam",
            "post" => "Lorem ipsum dolor sit amet consectetur adipisicing elit. Ut quasi consectetur soluta unde voluptas. Laborum iure omnis quaerat saepe nam delectus obcaecati alias voluptatibus illum ad, laboriosam, nemo distinctio voluptas."
        ],
        [
            "title" => "Post2",
            "author" => "Zuhal",
            "post" => "Lorem ipsum dolor sit amet consectetur adipisicing elit. Ut quasi consectetur soluta unde voluptas. Laborum iure omnis quaerat saepe nam delectus obcaecati alias voluptatibus illum ad, laboriosam, nemo distinctio voluptas."
        ]
    ];

    return view("post", [
        "title" => "post",
        "posts" => $bog_post
    ]);
});

// get params from url

Route::get('/room/{id}', function ($id) {
    return view("room", [
        "title" => "room",
        "roomId" => $id
    ]);
});
