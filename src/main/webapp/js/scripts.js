// scripts.js
document.addEventListener("DOMContentLoaded", function() {
    var toggleBtn = document.getElementById("toggleNavBtn");
    var navList = document.getElementById("navList");

    toggleBtn.addEventListener("click", function() {
        navList.classList.toggle("show");
    });
});


