console.log("I am Script");

let currentTheme = getTheme();
console.log(currentTheme);

// For Change Theme
changeTheme();
function changeTheme() {
  document.querySelector("html").classList.add(currentTheme);
  let changeThemeButton = document.querySelector("#theme_change_button");
  let changeText = document.querySelector("#changeText");
  changeThemeButton.addEventListener("click", (e) => {
    document.querySelector("html").classList.remove(currentTheme);
    if (currentTheme == "dark") {
      currentTheme = "light";
      changeText.innerHTML = "Dark";
    } else {
      currentTheme = "dark";
      changeText.innerHTML = "Light";
    }

    setTheme(currentTheme);
    document.querySelector("html").classList.add(currentTheme);
  });
}
//For Set Theme
function setTheme(currentTheme) {
  localStorage.setItem("theme", currentTheme);
}

//For Get Theme
function getTheme() {
  let theme = localStorage.getItem("theme");
  if (theme) return theme;
  else return "dark";
}


//do logout with pop-up

function doLogout() {
  Swal.fire({
    title: "Are you sure?",
    text: "You want to logout!",
    icon: "warning",
    showCancelButton: true,
    confirmButtonColor: "#3085d6",
    cancelButtonColor: "#d33",
    confirmButtonText: "Yes, log me out!",
  }).then((result) => {
    if (result.isConfirmed) {
      Swal.fire({
        title: "Logged out!",
        text: "You have been logged out successfully.",
        icon: "success",
      }).then(() => {
        window.location.href = "/do-logout";
      });
    }
  });
}

