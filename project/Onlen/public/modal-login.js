const signBtn = document.getElementsByClassName("sign-btn");
const loginBtn = document.getElementById("login-btn");
const clsLogin = document.getElementsByClassName("cls-login");

for (let button of signBtn) {
    button.addEventListener("click", function() {
        const loginOuter = document.getElementsByClassName("login-outer")[0];
        loginOuter.classList.toggle("geser");
        loginOuter.classList.toggle("red");
        const form = document.getElementsByClassName("form")[0];
        form.classList.toggle("form-geser");
        const loginInner = document.getElementsByClassName("login-inner")[0];
        loginInner.classList.toggle("red");
    });
}

const loginModal = () => {
    const loginBg = document.getElementsByClassName("login-bg")[0];
    loginBg.classList.add("active");
}

for (let button of clsLogin) {
    button.addEventListener("click", function() {
        const loginBg = document.getElementsByClassName("login-bg")[0];
        loginBg.classList.remove("active");
    });
}

const masukAkun = async () => {
    const formSignUp = document.getElementsByClassName("form-signup")[0];
    const username = document.getElementById("username-in").value;
    const password = document.getElementById("password-in").value;

    formSignUp.innerHTML = loading1;
    try {
        let result = await fetch("/login", {
            method: "POST",
            headers: {
                "Accept": "application/json",
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                username,
                password
            })
        });
        result = await result.text();
        switch (result) {
            case "user not found":
                alert("Username tidak ditemukan");
                break;
            case "password tidak sesuai":
                alert("Password Salah");
                break;
            case "database query error":
                alert("Error: Terjadi masalah pada server");
                break;
            case username:
            case (sanitize(username)).toLowerCase():
                alert("Login berhasil!");
                const loginBg = document.getElementsByClassName("login-bg")[0];
                loginBg.classList.remove("active");
                loginBtn.innerHTML = sanitize(username);
                loginBtn.removeAttribute("onclick");
                loginBtn.classList.add("username");
                loginBtn.removeAttribute("id");
                break;
            default:
                console.log(result == sanitize(username));
                break;
        }
    } catch (err) {
        console.log(err);
    }
    formSignUp.innerHTML = `<h1>Masuk Akun</h1>
    <label for="username-in">Username</label>
    <input type="text" name="username" id="username-in" autocomplete="off">
    <label for="password-in">Password</label>
    <input type="password" name="password" id="password-in">
    <div class="btn-container">
        <button id="btn-masuk-akun" onclick="masukAkun()">Masuk</button>
    </div>`;
}

const buatAkun = async () => {
    const password1 = document.getElementById("password-up").value;
    const password2 = document.getElementById("password2-up").value;
    const username = document.getElementById("username-up").value;
    if (username == "" || undefined || null) {
        alert("Username tidak boleh kosong");
        return;
    } else if (password1 == "" || undefined || null) {
        alert("Password tidak boleh kosong");
        return;
    } else if (password1 != password2) {
        alert("Password tidak sesuai");
        return;
    }
    const formSignIn = document.getElementsByClassName("form-signin")[0];
    formSignIn.innerHTML = loading1;
    try {
        let result = await fetch('/buat-akun', {
            method: "POST",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                username,
                password: password1
            })
        });
        result = await result.text();
        switch (result) {
            case "username sudah ada":
                alert("Username sudah digunakan");
                break;
            case "data gagal ditambahkan":
                alert("Data gagal ditambahkan! Terjadi masalah pada Server");
                break;
            case "data berhasil ditambahkan":
                alert("User Berhasil di Tambahkan");
                const loginBg = document.getElementsByClassName("login-bg")[0];
                loginBg.classList.remove("active");
                break;
        }
        formSignIn.innerHTML = `<h1>Buat Akun</h1>
        <label for="username-up">Username</label>
        <input type="text" name="username" id="username-up">
        <label for="password-up">Password</label>
        <input type="password" name="password" id="password-up">
        <label for="password2-up">Ketik ulang password</label>
        <input type="password" name="password2" id="password2-up">
        <div class="btn-container">
            <button id="btn-buat-akun" onclick="buatAkun()">Buat Akun</button>
        </div>`;
    } catch (err) {
        console.log(err);
    }
}
