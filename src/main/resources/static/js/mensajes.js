// Success
if (document.getElementById('success-alert') != null) {
    $.notify({
        title: '<strong>¡Muy bien!</strong>',
        message: document.getElementById('success-alert').innerText,
    },{
        type: "success",
    });
}
