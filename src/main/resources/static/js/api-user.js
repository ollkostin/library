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