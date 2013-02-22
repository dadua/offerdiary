var it = it || {};
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


it.offersoncard.getFilterCardId = function (target) {
    var cardIdExtractRegex = /^offersOnCardFilters_(.*)/,
        cardId = cardIdExtractRegex.exec($(target).parent().attr('id'))[1];
    return cardId;
};

it.offersoncard.filterClickHandler = function (e) {
    var target = e.target,
        cardId = it.offersoncard.getFilterCardId(target);

    it.offersoncard.plotAllOffersForCard(cardId);
    it.card.toggler.showOffersOnCard();
};


it.offersoncard.plotFilter = function (myCard) {
    $('#cardsFilters .nav-list').append('<li class="offersOnCardFilters" id="offersOnCardFilters_'+ myCard.id + '"><a href="#">'+myCard.name + '</a></li>');
};

it.offersoncard.plotFilters = function (mycards){
    for (var i=0;i<mycards.length;i++) {
        var card = mycards[i];
        this.plotFilter(card);
    }
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

it.offersoncard.setFilterClickHandler = function () {
    $('.offersOnCardFilters').off('click', '.offersOnCardFilters', it.offersoncard.filterClickHandler);
    $('.offersOnCardFilters').click(it.offersoncard.filterClickHandler);
};

it.offersoncard.addHandlers = function () {
    $('.offersOnCardLabel').click (it.offersoncard.plotOffersForCard);
    it.offersoncard.setFilterClickHandler();
    it.bsextends.filters.reInitClickHandler();
};
