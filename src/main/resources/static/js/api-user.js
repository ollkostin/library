function getUsers(success, mapper, error) {
    $.ajax({
        type: 'GET',
        url: 'api/user/',
        success: function (resp) {
            success(resp, mapper);
        },
        error: error
    });
}

function deleteUser(id, success, error) {
    $.ajax({
        type: 'DELETE',
        url: 'api/user/' + id,
        success: success,
        error: error
    });
}

function createUser(user, success, error) {
    $.ajax({
        type: 'POST',
        contentType: 'application/json',
        url: 'api/user/',
        data: JSON.stringify(user),
        success: success,
        error: error
    });
}

function editUser(user, success, error) {
    $.ajax({
        type: 'PUT',
        contentType: 'application/json',
        url: 'api/user/',
        data: JSON.stringify(user),
        success: success,
        error: error
    });
}