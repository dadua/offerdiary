var it = it || {};
it.offer = it.offer || {};
it.offer.detail = it.offer.detail || {};

it.offer.detail.getOfferHtml = function(offer, isSharedOffer) {

    var offerTemplate$ = $('.offer_detail_ui_template').clone().removeClass('offer_detail_ui_template hide').attr('id', 'offer_' + offer.id);
    //Setting values from offer vo to the template..
    if (offer.targetVendor) {
        offerTemplate$.find('.targetVendor-logoUrl').attr('alt', offer.targetVendor.name).attr('src', 'images/stores/'+ (offer.targetVendor.logoUrl || 'defaultVendor.jpg'));
        offerTemplate$.find('.vendorName').html(offer.targetVendor.name);
        offerTemplate$.find('.vendorUrl').html(offer.targetVendor.siteUrl).attr('href', offer.targetVendor.siteUrl);
    } else {
        offerTemplate$.find('.targetVendor-logoUrl').attr('src', 'images/stores/defaultVendor.jpg');
    }
    if (offer.expiryDateInMillis !== 0) {
        offerTemplate$.find('.offerExpiryDate').html(it.offer.getReadableDate(offer));
    } else {
        offerTemplate$.find('.expiryDate').hide();
    }
    if (isSharedOffer) {
        offerTemplate$.find('.offerTrash').hide();
        offerTemplate$.find('.offerAdd').parent().attr('href', 'addSharedOfferToWallet.do?accessCode=' + offer.id);
    } else {
        offerTemplate$.find('.offerTrash').attr('id', 'offerTrash_' + offer.id);
        offerTemplate$.find('.offerAdd').hide();
    }
    offerTemplate$.find('.offerShare').attr('id', 'offerShare_' + offer.id);

    offerTemplate$.find('.offerCodeVal').html(offer.offerCode);
    offerTemplate$.find('.offerDesc').html(offer.description);
    return offerTemplate$;
};


it.offer.detail.view = function () {

}();

it.offer.detail.show = function (e) {
    var target = e.target,
        targetId = target.id,
        offerIdExtractRegex = /offerDetail_(.*)/,
        offerId = offerIdExtractRegex.exec(targetId)[1];
    $('<form action="getOfferDetail.do" method="get"><input type="hidden" name="id" value="'+offerId+'"></input></form>').appendTo('body').submit();
};

it.offer.detail.plot = function (offer, isSharedOffer) {
    var offer$ = this.getOfferHtml(offer, isSharedOffer);
    $('#offerContainerFluid').html(offer$);
};

it.offer.detail.trashOffer = function(e) {
    var target = e.target;
    var targetId = target.id;
    var offerIdExtractRegex = /^offerTrash_(.*)/;
    var offerId = offerIdExtractRegex.exec(targetId)[1];
    var offerIds = [];
    offerIds.push(offerId);
    $.post('deleteOffers.do', {'offerIds': JSON.stringify(offerIds)}, function(data) {
        var ret = $.parseJSON(data);
        if (ret.success === true) {
            $('#'+targetId).tooltip('hide');
            $('#offer_'+offerId).remove();
            $('<form action="wallet.do" method="get"></form>').appendTo('body').submit();
        } else {
            //Handle error case
        }
    });
};


it.offer.detail.addOneHandlers = function () {

};

it.offer.detail.showShare= function (e) {
    
    var target = e.target,
        targetId = target.id,
        offerIdExtractRegex = /^offerShare_(.*)/,
        offerId = offerIdExtractRegex.exec(targetId)[1];

    $.get('reShareOffer.do', {accessCode: offerId}, function(response){
        var resp = $.parseJSON(response);
        if (resp.success) {
            it.offer.share.plot(resp.result);
        } else {
            //TODO: Handle error
        }
    });
};

it.offer.detail.addHandlers = function (isSharedOffer) {
    $('.offerTrash').click(it.offer.detail.trashOffer).tooltip();
    if (isSharedOffer === true) {
        $('.offerShare').click(it.offer.detail.showShare).tooltip();
    } else {
        $('.offerShare').click(it.offer.share.show).tooltip();
    }
    $('.offerAdd').tooltip();
    $('.offerDetail').tooltip();
};

it.offer.detail.init = function (offers, isSharedOffer) {
    it.offer.detail.plot(offers, isSharedOffer);
    it.offer.detail.addOneHandlers();
    it.offer.detail.addHandlers(isSharedOffer);
    it.offer.share.init();
};

