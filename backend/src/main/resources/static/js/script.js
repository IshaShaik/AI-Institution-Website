async function demoLogin(e) {
    e.preventDefault();
    const email = document.getElementById('stuEmail').value;
    const password = document.getElementById('stuPass').value; 

    try {
        const response = await fetch('/api/auth/login', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ email: email, password: password })
        });

        const data = await response.json();

        if (response.ok) {
            // YE SABSE ZAROORI LINES HAIN
            localStorage.setItem("loggedIn", "true");
            localStorage.setItem("token", data.token);
            localStorage.setItem("userName", data.name);       // Isha yahan se save hoga
            localStorage.setItem("userId", data.userId);       // ID yahan se save hogi
            localStorage.setItem("userPhoto", data.profileImage); // student.png yahan se save hoga

            window.location.href = 'student/programhub.html';
        } else {
            alert('Login Failed: ' + data.message);
        }
    } catch (error) {
        alert('Server connection failed!');
    }
}