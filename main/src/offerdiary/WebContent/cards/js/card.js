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
    e.preventDefault();
    var target = e.target,
    cardId = it.card.getParentCardId(target),
    card = {id:cardId};
    $.post('removeCardFromWallet.do', {'offerCardJson': JSON.stringify(card)}, function(data) {
        var ret = $.parseJSON(data);
        if (ret.success === true) {
            //$('#myCardsContainer > #card_'+cardId).remove();
            it.card.refreshAll();
            //it.card.refreshAddableCards();
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
            it.card.refreshAll();
            /*
            $('#cardsContainer > #card_'+cardId).remove();
            var cards = [];
            cards.push(ret.result);
            it.card.plotMyCards(cards);
            it.card.refreshAddableCards();
           */
        } else {
            //Handle error case
        }
    });
};


it.card.discoverRefreshHandler = function() {
    $('#cardFullName').keyup(it.card.refreshAddableCards);
};

it.card.addHandlers = function () {
    $('.card-dismiss').click(it.card.dismiss);
    $('.card-associate').click(it.card.associateWithUser);
};


it.card.getCard$ = function (card, actionsHtml) {
	
    var cardDisplayHtml = '<li class="span4 thumbnail" id="card_'+ card.id + '">';
    cardDisplayHtml += '<div class="card">';
    cardDisplayHtml += actionsHtml;
    cardDisplayHtml += '<h4> Card Name: </h4>'+card.name+'</div></li>';
    return $(cardDisplayHtml);
	
};

it.card.plotCards = function(containerElemSelector, cards, actionsHtml) {
    var cards$ = [];
    for (var i=0; i < cards.length; i++) {
        cards$.push(it.card.getCard$(cards[i], actionsHtml));
    }

    $(containerElemSelector).rowFluidAdder({
        items$: cards$,
        itemRowContainerTemplate$: '<ul class="thumbnails"></ul>'
    });

    this.addHandlers();
};

it.card.plotMyCards = function(cards) {
    var actionsHtml = '<a class="pull-right card-dismiss btn btn-mini">remove</a>';
    this.plotCards('#myCardsContainer', cards, actionsHtml);
};

it.card.plotAddableCards = function(query) {
    var actionsHtml = '<a class="pull-right card-associate btn btn-mini btn-success">add</a>';
    var seachCriteriaData = {
        pageNumber : 1,
        resultsPerPage : 10,
        searchString : query
    };
    $.getJSON('searchOfferCards.do', {searchCriteria:JSON.stringify(seachCriteriaData)}, function(data){
        var cards = data.result;
        $('#cardsContainer').html('');
        it.card.plotCards('#cardsContainer', cards, actionsHtml);
    });
};

it.card.refreshAddableCards = function(e) {
    var query = $('#cardFullName').val();
    it.card.plotAddableCards(query);
    it.card.addHandlers();
};

it.card.refreshMyCards = function() {
    $.getJSON('getMyCards.do', function (resp) {
        if (resp.success) {
            it.card.plotMyCards(resp.result);
        } else {
            //TODO: Handle error case
        }
    });
};


it.card.refreshAll = function () {
    //TODO: Implement this.. using refreshAddableCards
    it.card.refreshMyCards ();
    it.card.refreshAddableCards();
};

it.card.plotAll = function(myCardJson) {
    var myCards = JSON.parse(myCardJson);
    this.plotMyCards(myCards);
    this.plotAddableCards('');//Empty String for default set of cards..
};
