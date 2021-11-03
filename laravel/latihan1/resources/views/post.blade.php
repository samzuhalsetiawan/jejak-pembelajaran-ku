@extends('layouts.main')

{{-- Untuk var_dump --}}
{{-- @dd($posts); //dump die--}}

@section('container')

    @foreach ($posts as $post)
      <article>
        <h1>{{ $post["title"] }}</h1>
        <h3>{{ $post["author"] }}</h3>
        <p>{{ $post["post"] }}</p>
      </article>
    @endforeach

@endsection