var it = it || {};
it.offer = it.offer || {};
it.offer.share = it.offer.share || {};

it.offer.share.show = function (resp) {
    $('.share_offer_template').find('.sharedUrl').val(resp.sharedURL).focus();
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

