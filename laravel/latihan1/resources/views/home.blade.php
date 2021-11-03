@extends('layouts.main')

@section('container')
    <h1>Ini Halaman Home</h1>
    <br>
    <hr>
    <p>
      Latihan ini membahas mengenai Routing dan Templating engine blade. <br>
      Pada laravel views di simpan di resource/views. <br>
      Routing untuk get post dll berada di directori routes. <br>
      Kita bisa menggunakan layout / template dengan method @ extends() <br>
      Didalam template jelaskan akan ditaruh mana bagian view dengan method @ yield() <br>
      lalu didalam view jelaskan bagian apa yg ingin ditaruh dengan method @ section() <br>
      Kita bisa mengambil isi dari view lain dengan method @ include() <br>
      untuk melakukan debug var_dump lalu die bisa menggunakan method @ dd() <br>
      untuk melakukan echo dengan blade bisa menggunakan dua kurung kurawal
    </p>
    <hr>
    <br>
    <h3>Go to Random Room <a href="/room/{{ mt_rand() }}">Click Here</a></h3>

@endsection
