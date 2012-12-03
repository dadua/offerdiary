/**
 *
 */
var it = it || {};
it.card = it.card || {};

it.card.getParentCardId = function(actionElem) {
    var cardIdExtractRegex = /^card_(.*)/,
    parentCardElem$ = $(actionElem).parent().parent(),
    cardId = cardIdExtractRegex.exec($(parentCardElem$).attr('id'))[1];
    return cardId;
};

it.card.dismiss = function(e) {
    $(this).addClass('disable');
    var target = e.target,
    cardId = it.card.getParentCardId(target),
    card = {id:cardId};
    $.post('removeCardFromWallet.do', {'offerCardJson': JSON.stringify(card)}, function(data) {
        var ret = $.parseJSON(data);
        if (ret.success === true) {
            $('#myCardsContainer > #card_'+cardId).remove();
            it.card.refreshAddableCards();
        } else {
            //Handle error case
        }
    });
};

it.card.associateWithUser = function(e) {
    var target = e.target,
    cardId = it.card.getParentCardId(target),
    card = {id:cardId};
    $.post('addCardToWallet.do', {'offerCardJson': JSON.stringify(card)}, function(data) {
        var ret = $.parseJSON(data);
        if (ret.success === true) {
            $('#cardsContainer > #card_'+cardId).remove();
            var cards = [];
            cards.push(ret.result);
            it.card.plotMyCards(cards);
            it.card.refreshAddableCards();
        } else {
            //Handle error case
        }
    });
};


it.card.refreshAddableCards = function(e) {
    var query = $('#cardFullName').val();
    it.card.plotAddableCards(query);
};

it.card.discoverRefreshHandler = function() {
    $('#cardFullName').keyup(it.card.refreshAddableCards);
};

it.card.addHandlers = function () {
    $('.card-dismiss').click(it.card.dismiss);
    $('.card-associate').click(it.card.associateWithUser);
};

it.card.appendCardTo = function(containerElemSelector, card, actionsHtml) {

    var cardDisplayHtml = '<li class="span3" id="card_'+ card.id + '">';
    cardDisplayHtml += '<div class="thumbnail card">';
    cardDisplayHtml += actionsHtml;
    cardDisplayHtml += '<h4> Card Name: </h4>'+card.name+'</div></li>';
    $(cardDisplayHtml).appendTo(containerElemSelector);
};

it.card.appendCardsTo = function(containerElemSelector, cards, actionsHtml) {
    for (var i=0; i < cards.length; i++) {
        this.appendCardTo(containerElemSelector, cards[i], actionsHtml);
    }
    this.addHandlers();
};

it.card.plotMyCards = function(cards) {
    var actionsHtml = '<a class="pull-right card-dismiss btn btn-mini">remove</a>';
    this.appendCardsTo('#myCardsContainer', cards, actionsHtml);
};

it.card.plotAddableCards = function(query) {
    var actionsHtml = '<a class="pull-right card-associate btn btn-mini">add</a>';
    var seachCriteriaData = {
        pageNumber : 1,
        resultsPerPage : 10,
        searchString : query
    };
    $.getJSON('searchOfferCards.do', {searchCriteria:JSON.stringify(seachCriteriaData)}, function(data){
        var cards = data.result;
        $('#cardsContainer').html('');
        it.card.appendCardsTo('#cardsContainer', cards, actionsHtml);
    });
};

it.card.plotAll = function(myCardJson) {
    var myCards = JSON.parse(myCardJson);
    this.plotMyCards(myCards);
    this.plotAddableCards('');//Empty String for default set of cards..
};
