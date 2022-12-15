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
    return view('welcome');
});

Route::get('/sam', function () {
    return view('sam');
});

Route::get('/home', function() {
    return view('home');
});

Route::get('/contact', function() {
    return view('contact');
});

Route::get('/login', function() {
    return view('login');
});

Route::view('/about', 'about', ['deskripsi' => 'Ini deskripsi']);

Route::prefix('pejabat')->group(function () {
    Route::get('/kepala-sekolah', function () {
        return "profil kepala sekolah";
    });
    Route::get('/guru-bk', function () {
        return "profil guru bk";
    });
    Route::get('/orang-tu', function () {
        return "profil orang tu";
    });
});