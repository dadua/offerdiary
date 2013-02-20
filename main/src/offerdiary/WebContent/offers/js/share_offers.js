var it = it || {};
it.offer = it.offer || {};

it.offer.share = function () {
    var share_offer$ = $('.share_offer_template');
    if (share_offer$.find('.shareOfferBtn').hasClass('disabled')) {
        return;
    }
    if (share_offer$.find('.emailShareOpt').hasClass('.active')) {
        it.offer.share.email();
    } else {
        it.offer.share.fb();
    }
    share_offer$.modal('hide');
};

it.offer.share.vo = function() {
    var _vo = null;
    return {
        set: function (vo) {
            _vo = vo;
        },
        get: function (){
            return _vo;
        }
    };
}();

it.offer.share.addHandlers = function () {
    var share_offer$ = $('.share_offer_template');
    share_offer$.find('.emailShareOpt').click(it.offer.share.toggleToMailOpt);
    share_offer$.find('.fbShareOpt').click(it.offer.share.toggleToFbOpt);
    share_offer$.find('.shareEmails').keyup(it.offer.share.toggleShareBtnOnValidEmail);
    share_offer$.find('.shareOfferBtn').click(it.offer.share);
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

it.offer.share.plot = function (shareOfferVO) {
    it.offer.share.vo.set(shareOfferVO);
    var share_offer$ = $('.share_offer_template');
    share_offer$.find('.sharedUrl').val(shareOfferVO.sharedURL).click(function(){this.select();});
    share_offer$.find('.shareMessage').val(shareOfferVO.fbPost.description);
    share_offer$.modal('show');
};

it.offer.share.fb = function () {
    var shareOfferVO = it.offer.share.vo.get();
    shareOfferVO.fbPost.redirect_uri = shareOfferVO.fbPost.redirectURI;
    shareOfferVO.fbPost.picture = shareOfferVO.fbPost.pictureURL;
    shareOfferVO.fbPost.callback = function () {
        var share_offer$ = $('.share_offer_template');
        share_offer$.modal('hide');
    };
    it.fb.postOnWall(shareOfferVO.fbPost);
};

it.offer.share.email = function () {

};

it.offer.share.show = function (e) {
    var target = e.target,
        targetId = target.id,
        offerIdExtractRegex = /^offerShare_(.*)/,
        offerId = offerIdExtractRegex.exec(targetId)[1];

    $.get('shareOffer.do', {id: offerId}, function(response){
        var resp = $.parseJSON(response);
        if (resp.success) {
            it.offer.share.plot(resp.result);
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
