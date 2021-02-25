"use strict";

let app = {
  canvas: null,
  image: null,
  currentEffect: null,
  color: null, //Atributul 'color' nu a fost folosit niciodata
};

var rect = {}; //Rect este declarat ca un obiect fara atribute
var drag = false;

app.changeEffect = function (effect) {
  if (app.currentEffect != effect) {
    app.currentEffect = effect;
    app.drawImage();
  }
};

app.grayscale = function (context) {
  let imageData = context.getImageData(
    0,
    0,
    app.canvas.width,
    app.canvas.height
  );
  let data = imageData.data;

  for (let i = 0; i < data.length; i += 4) {
    const r = data[i];
    const g = data[i + 1];
    const b = data[i + 2];

    const average = Math.round((r + g + b) / 3);

    data[i] = data[i + 1] = data[i + 2] = average;
  }

  context.putImageData(imageData, 0, 0);
};

app.threshold = function (context) {
  let imageData = context.getImageData(
    0,
    0,
    app.canvas.width,
    app.canvas.height
  );
  let data = imageData.data;

  for (let i = 0; i < data.length; i += 4) {
    const r = data[i];
    const g = data[i + 1];
    const b = data[i + 2];

    //Constanta "Average" nu are o denumire sugestiva pentru contextul in care este folosita
    const average = 0.2126 * r + 0.7152 * g + 0.0722 * b;

    data[i] = data[i + 1] = data[i + 2] = average >= 100 ? 255 : 0;
  }
  context.putImageData(imageData, 0, 0);
};

app.sephia = function (context) {
  let imageData = context.getImageData(
    0,
    0,
    app.canvas.width,
    app.canvas.height
  );
  let data = imageData.data;

  for (let i = 0; i < data.length; i += 4) {
    const r = data[i];
    const g = data[i + 1];
    const b = data[i + 2];

    data[i] = Math.round(r * 0.393 + g * 0.769 + b * 0.189);
    data[i + 1] = Math.round(r * 0.349 + g * 0.686 + b * 0.168);
    data[i + 2] = Math.round(r * 0.272 + g * 0.534 + b * 0.131);
  }

  context.putImageData(imageData, 0, 0);
};

app.invert = function (context) {
  let imageData = context.getImageData(
    0,
    0,
    app.canvas.width,
    app.canvas.height
  );
  let data = imageData.data;

  for (let i = 0; i < data.length; i += 4) {
    const r = data[i];
    const g = data[i + 1];
    const b = data[i + 2];

    data[i] = 255 - r;
    data[i + 1] = 255 - g;
    data[i + 2] = 255 - b;
  }

  context.putImageData(imageData, 0, 0);
};

app.green = function (context) {
  let imageData = context.getImageData(
    0,
    0,
    app.canvas.width,
    app.canvas.height
  );
  let data = imageData.data;

  for (let i = 0; i < data.length; i += 4) {
    data[i] = 0;
    data[i + 2] = 0;
  }
  context.putImageData(imageData, 0, 0);
};

app.blue = function (context) {
  let imageData = context.getImageData(
    0,
    0,
    app.canvas.width,
    app.canvas.height
  );
  let data = imageData.data;

  for (let i = 0; i < data.length; i += 4) {
    data[i] = 0;
    data[i + 1] = 0;
  }

  context.putImageData(imageData, 0, 0);
};

app.red = function (context) {
  let imageData = context.getImageData(
    0,
    0,
    app.canvas.width,
    app.canvas.height
  );
  let data = imageData.data;

  for (let i = 0; i < data.length; i += 4) {
    data[i + 1] = 0;
    data[i + 2] = 0;
  }

  context.putImageData(imageData, 0, 0);
};

app.twoChannels = function (context) {
  let imageData = context.getImageData(
    0,
    0,
    app.canvas.width,
    app.canvas.height
  );
  let data = imageData.data;

  for (let i = 0; i < data.length; i += 4) {
    const r = data[i];
    const g = data[i + 1];
    const b = data[i + 2];

    data[i] = r;
    data[i + 1] = g;
    data[i + 2] = g;
  }

  context.putImageData(imageData, 0, 0);
};

app.pixelate = function (context) {
  const blocksize = 10;

  let canvas2 = document.createElement("canvas");
  canvas2.width = app.canvas.width;
  canvas2.height = app.canvas.height;
  const context2 = canvas2.getContext("2d");
  context2.drawImage(app.img, 0, 0);

  for (var x = 1; x < app.canvas.width; x += blocksize) {
    for (var y = 1; y < app.canvas.height; y += blocksize) {
      var pixel = context2.getImageData(x, y, 1, 1);
      context.fillStyle =
        "rgb(" +
        pixel.data[0] +
        "," +
        pixel.data[1] +
        "," +
        pixel.data[2] +
        ")";
      context.fillRect(x, y, x + blocksize - 1, y + blocksize - 1);
    }
  }
};

app.drawImage = function () {
  app.canvas.width = app.img.naturalWidth;
  app.canvas.height = app.img.naturalHeight;

  //Pentru preluarea contextului canvasului se folosesc mai multe denumiri de variabile "ctx","context"
  var ctx = app.canvas.getContext("2d");
  ctx.drawImage(app.img, 0, 0);
};

app.selectImage = function () {
  app.canvas.width = app.img.naturalWidth;
  app.canvas.height = app.img.naturalHeight;
  var ctx = app.canvas.getContext("2d");
  app.drawImage();
  ctx.strokeStyle = "yellow";
  ctx.beginPath();
  ctx.rect(
    this.offsetLeft,
    this.offsetTop,
    app.canvas.width,
    app.canvas.height
  );
  ctx.stroke();
};

app.insertText = function () {
  app.drawImage();
  var ctx = app.canvas.getContext("2d");
  let textCanvas = document.getElementById("textCanvas").value;
  let dimTextCanvas = parseInt(document.getElementById("dimTextCanvas").value);
  let colorTextCanvas = document.getElementById("colorTextCanvas").value;

  //Folosirea functiei parseInt desi se pute evita deoarece value intoarce direct tipul de data introdus
  let textPozY = parseInt(document.getElementById("tpozYCanvas").value);
  let textPozX = parseInt(document.getElementById("tpozXCanvas").value);

  ctx.fillStyle = `${colorTextCanvas}`;
  ctx.font = "" + `${dimTextCanvas}` + "pt";
  ctx.fillText(textCanvas, textPozX, textPozY);
};

function mouseDown(e) {
  rect.startX = e.pageX - this.offsetLeft;
  rect.startY = e.pageY - this.offsetTop;
  drag = true;
}

function mouseUp() {
  drag = false;
}

function mouseMove(e) {
  var ctx = app.canvas.getContext("2d");
  if (drag) {
    ctx.clearRect(0, 0, 500, 500);
    app.drawImage();
    rect.w = e.pageX - this.offsetLeft - rect.startX;
    rect.h = e.pageY - this.offsetTop - rect.startY;
    ctx.strokeStyle = "red";
    ctx.strokeRect(rect.startX, rect.startY, rect.w, rect.h);
    ctx.fillStyle = "rgba(0,0,0,0)";
  }
}

app.deletePixels = function () {
  var context = app.canvas.getContext("2d");
  let imageData = context.getImageData(
    0,
    0,
    app.canvas.width,
    app.canvas.height
  );
  let data = imageData.data;

  for (let i = 0; i < data.length; i++) {
    data[i] = 255;
  }

  context.putImageData(imageData, 0, 0);
};

app.load = function () {
  app.canvas = document.getElementById("imageCanvas");

  app.img = document.createElement("img");

  document
    .getElementById("fileBrowser")
    .addEventListener("change", function (e) {
      let fileBrowser = e.target;
      //Variabila 'fileBrowser' nu a fost folosita niciodata

      if (e.target.files.length > 0) {
        var file = e.target.files[0];

        let fileReader = new FileReader();
        fileReader.addEventListener("load", function (e) {
          app.img.addEventListener("load", function (e) {
            app.drawImage();
          });
          app.img.src = e.target.result;
        });
        fileReader.readAsDataURL(file);
      }

      let btnInsertText = document.getElementById("btnText");

      //Apelul functiei selectImage() se face in cadrul unei alte functii ,desi se putea crea direct o 
      //functie care sa contina body ul functiei selectImage()
      btnInsertText.addEventListener("click", function () {
        app.selectImage();
      });

      app.canvas.addEventListener("mousedown", mouseDown, false);
      app.canvas.addEventListener("mouseup", mouseUp, false);
      app.canvas.addEventListener("mousemove", mouseMove, false);

      let btnSave = document.getElementById("btnSave");
      btnSave.addEventListener("click", function () {
        let dataUrl = document
          .getElementById("imageCanvas")
          .toDataURL("image/png");
        this.parentElement.setAttribute("href", dataUrl);
      });
    });
};

app.load();
