it.offersoncard = it.offersoncard || {};

it.offersoncard.show = function () {
    $('.offerOnCard').show();
};

it.offersoncard.hide = function () {
    $('.offerOnCard').hide();
};


it.offersoncard.getCardId = function (target) {
    var cardIdExtractRegex = /^offersOnCardLabel_(.*)/,
    cardId = cardIdExtractRegex.exec($(target).attr('id'))[1];
    return cardId;
};

it.offersoncard.plotOffers = function (offers) {
    var offers$ = [];

    for (var i=0; i< offers.length; i++) {
        offers$.push(it.offer.getOfferHtml(offers[i]));
    }

    $('#offerOnCardContainer').rowFluidAdder({ items$: offers$,
                                            itemRowContainerTemplate$: '<ul class="thumbnails row-fluid"></ul>' });
    //it.offer.addHandlers();

};

it.offersoncard.plotAllOffersForCard = function (cardId) {

    $.getJSON('getOffersOnCard.do', {cardIdKey: cardId}, function (resp) {
        if (resp.success) {
            it.offersoncard.plotOffers(resp.result);
        } else {
            //TODO: Error case
        }
    });
};

it.offersoncard.plotOffersForCard = function (e) {
    var target = e.target,
    cardId = it.offersoncard.getCardId(target);
    it.offersoncard.plotAllOffersForCard(cardId);
    it.card.toggler.showOffersOnCard();
};

it.offersoncard.addHandlers = function () {
    $('.offersOnCardLabel').click (it.offersoncard.plotOffersForCard);
};
