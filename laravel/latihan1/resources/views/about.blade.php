@extends('layouts.main')

@section('container')
    <h1>Selamat datang {{ $name }}</h1>
    <h3>Your Data</h3>
    <ul>
      <li>Email : {{ $email }}</li>
      <li>No HP : {{ $nohp }}</li>
    </ul>
@endsection