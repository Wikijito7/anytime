// there's going to be some code sometime in the future.
// RESPONSIVE
var lista = false;
var navlist = document.getElementById("nav");

// PARA FICHAR/DESFICHAR (MOSTRAR FUNCIONALIDAD, NO EL REAL)
var sinfichar = document.getElementById("sinfichar");
var fichado = document.getElementById("fichado");

function toggleList() {
    // if(window.outerWidth > 760) return;
        if (lista){
            navlist.style.display = "none";
        } else {
          navlist.style.display = "flex";
        }

        lista = !lista;
}

function pauseFichar(btn) {
  var elements = document.getElementsByClassName("botoncir");
  Array.prototype.forEach.call(elements, function(button) {
    if (button !== btn){
      button.classList.replace("selected", "not-selected");
    }
  });

  if (btn.classList.contains("not-selected")) {
    btn.classList.replace("not-selected", "selected");
  } else {
    btn.classList.replace("selected", "not-selected");
  }
}

function fichar() {
  sinfichar.style.display = "none";
  fichado.style.display = "flex";
}

function desfichar() {
  sinfichar.style.display = "flex";
  fichado.style.display = "none";
}
