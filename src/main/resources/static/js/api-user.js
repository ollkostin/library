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