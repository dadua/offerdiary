var it = it || {};
it.profile = it.profile || {};
it.profile.password= it.profile.password|| {};

it.profile.password.view = function () {

    var _get$ = function () {
        var _changePassword$ = $('.change_password_template').clone();
        _changePassword$.removeClass('change_password_template').addClass('changePassword');
        return _changePassword$;
    },

    _changePasswordHandler =  function () {

        var changePassword$ = $('.changePassword');
        if (changePassword$.hasClass('disabled')) {
            return;
        }
        _submitChangePassword(false);
        var currentPassword = changePassword$.find('#currentPassword').val(),
        newPassword = changePassword$.find('#newPassword').val();

        $.post('changePassword.do',
               {currentPassword: currentPassword, newPassword: newPassword},
               function (data) {
                   changePassword$.find('#currentPassword').val('');
                   changePassword$.find('#newPassword').val('');
                   changePassword$.find('#retypedNewPassword').val('');
               });
    },

    _submitChangePassword = function (enabled) {
        if (enabled) {
            $('.changePassword').find('#submitChangePassword').removeClass('disabled');
        } else {
            $('.changePassword').find('#submitChangePassword').addClass('disabled');
        }
    },

    _verifyNewRetype = function () {
        var changePassword$ = $('.changePassword'),
            newPassword = changePassword$.find('#newPassword').val(),
            retypedNewPassword = changePassword$.find('#retypedNewPassword').val(),
            currentPassword = changePassword$.find('#currentPassword').val();

        if (currentPassword !== '' && newPassword !== '' && retypedNewPassword === newPassword) {
            _submitChangePassword(true);
        } else {
            _submitChangePassword(false);
        }
    }, 

    addHandlers = function () {
        var changePassword$ = $('.changePassword');
        changePassword$.find('#submitChangePassword').click(_changePasswordHandler);
        changePassword$.find('#currentPassword, #retypedNewPassword, #newPassword').keyup(_verifyNewRetype);
    };

    return {
        get$: _get$,
        addHandlers: addHandlers
    };
}();

it.profile.password.plotChangeUI = function () {

    var changePassword$ = this.view.get$();
    $('#profileContainer').html(changePassword$);
    this.view.addHandlers();
};
