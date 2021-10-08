// Success
if (document.getElementById('success-alert') != null) {
    $.notify({
        title: '<strong>Muy bien</strong>',
        message: document.getElementById('success-alert').innerText,
    },{
        type: "success",
    });
}


// Warning
if (document.getElementById('warning-alert') != null) {
    $.notify({
        title: '<strong>Cuidado</strong>',
        message: document.getElementById('warning-alert').innerText,
    },{
        type: "warning",
    });
}


// Danger
if (document.getElementById('danger-alert') != null) {
    $.notify({
        title: '<strong>Error</strong>',
        message: document.getElementById('danger-alert').innerText,
    },{
        type: "danger",
    });
}
