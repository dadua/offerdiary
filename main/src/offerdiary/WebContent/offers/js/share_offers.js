var it = it || {};
it.offer = it.offer || {};
it.offer.share = it.offer.share || {};

it.offer.share.addHandlers = function () {
    var share_offer$ = $('.share_offer_template');
    share_offer$.find('.emailShareOpt').click(it.offer.share.toggleToMailOpt);
    share_offer$.find('.fbShareOpt').click(it.offer.share.toggleToFbOpt);
    share_offer$.find('.shareEmails').keyup(it.offer.share.toggleShareBtnOnValidEmail);
};

it.offer.share.checkEmailAndToggle = function () {
    var share_offer$ = $('.share_offer_template'),
        emailVal = share_offer$.find('.shareEmails').val(),
        validEmailRegex = /.+@.+\..+/;

    if (validEmailRegex.test(emailVal)) {
        share_offer$.find('.shareOfferBtn').removeClass('disabled');
    } else {
        share_offer$.find('.shareOfferBtn').addClass('disabled');
    }
};

it.offer.share.toggleShareBtnOnValidEmail = function () {
    var share_offer$ = $('.share_offer_template');
    if (share_offer$.find('.emailShareOpt').hasClass('active')) {
        it.offer.share.checkEmailAndToggle();
    } else {
        share_offer$.find('.shareOfferBtn').removeClass('disabled');
    }
};

it.offer.share.toggleToMailOpt= function (e) {
    var share_offer$ = $('.share_offer_template');
    share_offer$.find('.shareEmailContent').show();
    it.offer.share.checkEmailAndToggle();
};

it.offer.share.toggleToFbOpt = function (e) {
    var share_offer$ = $('.share_offer_template');
    share_offer$.find('.shareEmailContent').hide();
    share_offer$.find('.shareOfferBtn').removeClass('disabled');
};

it.offer.share.show = function (resp) {
    $('.share_offer_template').find('.sharedUrl').val(resp.sharedURL).click(function(){this.select();});
    $('.share_offer_template').modal('show');
};

it.offer.share.fb = function () {

};

it.offer.share.email = function () {

};

it.offer.share.all = function (e) {
    var target = e.target,
        targetId = target.id,
        offerIdExtractRegex = /^offerShare_(.*)/,
        offerId = offerIdExtractRegex.exec(targetId)[1];

    $.get('shareOffer.do', {id: offerId}, function(response){
        var resp = $.parseJSON(response);
        if (resp.success) {
            it.offer.share.show(resp.result);
        } else {
            //TODO: Handle error
        }
    });
};

it.offer.share.resetFormVals = function() {


};

it.offer.share.init = function () {
    it.offer.share.addHandlers();
};
