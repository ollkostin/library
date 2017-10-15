let users = $('#users');

$('#modal-window').on('hidden.bs.modal', clearUserModalValues);
$(document).ready(function () {
    getUsers(showUsers, userTableRowMapper, null);
});

function showUsers(response, mapper) {
    response.forEach(function (el) {
        users.append(mapper(el));
    });
}

function userTableRowMapper(el) {
    let tr = $('<tr></tr>');
    tr.append(buildTableData(el['id']));
    tr.append(buildTableData(el['username']));
    tr.append(buildTableData(buildUserDeleteCell));
    return tr;
}

function buildUserDeleteCell() {
    let button = $('<button class="btn btn-danger delete-user"></button>');
    button.append('Удалить');
    button.click(onDeleteUserButtonClick);
    return button;
}


function onDeleteUserButtonClick() {
    let currentRow = $(this).closest("tr");
    let idCell = currentRow.find("td:eq(0)");
    let id = idCell.text();
    if (confirm("Удалить пользователя?")) {
        deleteUser(id, function (resp) {
            currentRow.remove();
        }, showErrorAlert);
    }
}

function onClickShowModalCreateUser() {
    $('#modal-action').on('click', onClickCreateUser);
    $('#modal-window').modal('show');
}

function onClickCreateUser() {
    let userDto = buildUserDto($('#username').val(), $('#password').val());
    createUser(userDto, function (resp) {
        alert("Пользователь успешно создан!");
        location.reload();
    }, userError)
}

function userError(resp) {
    if (resp.responseJSON.message === 'username')
        alert('Имя пользователя не указано');
    if (resp.responseJSON.message === 'password') {
        alert('Пароль не указан');
    }
    if (resp.responseJSON.message === 'exists') {
        alert('Пользователь с данным именем существует');
    }
}

function clearUserModalValues() {
    $('#username').val('');
    $('#password').val('');
}

function buildUserDto(username, password, id) {
    return {
        username: username,
        password: password,
        id: id
    }
}

function buildUserLinkCell(username) {
    let a = $('<a href="" data-toggle="modal">' + username + '</a>');
    a.click(onClickShowModalEditUser);
    return a;
}

function onClickShowModalEditUser() {
    $('#modal-action').on('click', onClickEditUser);
    let currentRow = $(this).closest("tr");
    let username = currentRow.find("td:eq(1)").text();
    $('#username').val(username);
    $('#password').val('');
    $('#modal-window').modal('show');
}



function onClickEditUser() {

}