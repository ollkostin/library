let users = $('#users');

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
    return tr;
}