var it = it || {};
it.offer = it.offer || {};

it.offer.appendOffers = function(offers, isOldAddition) {
    for (var i=0; i<offers.length;i++) {
        this.appendOffer(offers[i], isOldAddition);
    }
};


it.offer.getDaysToExpire = function(offer) {
    var expiryMillisUtc = offer.expiryDateInMillis;
    var d = new Date();
    var currentMillisUtc = d.getTime();
    var millisToExpire = (expiryMillisUtc - currentMillisUtc);
    var daysToExpire = Math.floor(millisToExpire/(1000*60*60*24));
    return daysToExpire;
};

it.offer.getExpiryDateBasedClass = function(daysToExpire) {
    if(daysToExpire < 0 ) {
        return '';
    } else if(daysToExpire < 14) {
        return 'label-important';
    } else if (daysToExpire < 30) {
        return 'label-warning';
    } else if (daysToExpire < 90) {
        return 'label-info';
    } else if (daysToExpire < 120) {
        return 'label-inverse';
    } else {
        return 'label-success';
    }
};

it.offer.getFormattedDaysToExpire = function (daysToExpire) {
    return daysToExpire + (daysToExpire==1?' day':' days')+' to expire';
};

it.offer.getFormattedMonthsToExpire = function(daysToExpire) {
    var monthsToExpire = Math.floor(daysToExpire/30);
    return monthsToExpire + (monthsToExpire==1?' month, ':' months, ') + this.getFormattedDaysToExpire(Math.floor(daysToExpire%30));
};

it.offer.getFormattedYearsToExpire = function(daysToExpire) {
    var yearsToExpire = Math.floor(daysToExpire/365);
    return yearsToExpire + (yearsToExpire==1?' year, ':' years, ') + this.getFormattedMonthsToExpire(Math.floor(daysToExpire%365));
};

it.offer.getFormattedTimeToExpire = function(daysToExpire) {
    if (daysToExpire < 30) {
        return this.getFormattedDaysToExpire(daysToExpire);
    } else if (daysToExpire < 365) {
        return this.getFormattedMonthsToExpire(daysToExpire);
    } else {
        return this.getFormattedYearsToExpire(daysToExpire);
    }
};

it.offer.getCompactFormattedTimeToExpire = function(timeToExpireString) {
    if (timeToExpireString.length > 16) {
        var displayedTimeToExpire = timeToExpireString.substring(0, 12),
        undisplayedTimeToExpire = timeToExpireString.substring(16, timeToExpireString.length);
        displayedTimeToExpire += '<span class="hiddenTillAddSuccess" style="display:none" >';
        displayedTimeToExpire += timeToExpireString.substring(12,16);
        displayedTimeToExpire += '</span>';
        displayedTimeToExpire += '<span style="display:none" class="hiddenPartOfTime">';
        displayedTimeToExpire += undisplayedTimeToExpire;
        displayedTimeToExpire += '</span>';
        displayedTimeToExpire += '<a class="daysToExpireDetails" href="#" title="'+timeToExpireString +'">...</a>';
        return displayedTimeToExpire;
    } else {
        return timeToExpireString;
    }
};


it.offer.getOfferHtml = function(offer) {

    var offerTemplate$ = $('.offer_ui_template').clone().removeClass('offer_ui_template hide').attr('id', 'offer_' + offer.id);
    //Setting values from offer vo to the template..
    if (offer.sourceVendor) {
        offerTemplate$.find('.sourceVendor-logoUrl').attr('alt', offer.sourceVendor.name).attr('src', 'images/stores/'+ offer.sourceVendor.logoUrl || 'defaultVendor.jpg');
        offerTemplate$.find('.vendorName').html(offer.sourceVendor.name);
        offerTemplate$.find('.vendorUrl').html(offer.sourceVendor.siteUrl);
        //offerTemplate$.find('.offerExpiryDate').html(offer.sourceVendor.siteUrl);
        offerTemplate$.find('.vendorUrl').html(offer.sourceVendor.siteUrl);
    } else {
        offerTemplate$.find('.sourceVendor-logoUrl').attr('src', 'images/stores/defaultVendor.jpg');
    }
    offerTemplate$.find('.offerCodeVal').html(offer.offerCode);
    offerTemplate$.find('.offerNum').html(offer.title);
    return offerTemplate$;
};

it.offer.appendOffer = function (offer, isOldAddition) {

    var daysToExpire = this.getDaysToExpire(offer);
    var labelClass = this.getExpiryDateBasedClass(daysToExpire);
    var daysToExpireHtml = '<div class="daysToExpire label '+ labelClass + '" id="offerExpire_'+ offer.id + '">';
    var formattedTimeToExpire = this.getFormattedTimeToExpire(daysToExpire);
    if (daysToExpire < 0) {
        daysToExpireHtml += 'Offer Expired!!';
    } else {
        var compactFormattedTime = this.getCompactFormattedTimeToExpire(formattedTimeToExpire);
        daysToExpireHtml += compactFormattedTime;
    }
    daysToExpireHtml += '</div>';
    /*
       var offerHtml = '<li class="span3" id="offer_';
       offerHtml += offer.id;
       offerHtml += '" >';
       offerHtml += '<span class="addingDoneLabel label label-success">Done!</span>';
       offerHtml += daysToExpireHtml;
       offerHtml += '<div class="thumbnail"><span class="icon-trash icon-white pull-right offer-trash" title="Trash Me" id="offerTrash_';
       offerHtml += offer.id;
       offerHtml += '" ></span><h3>Details:';
       offerHtml += offer.description;
       offerHtml += '</h3><h5>Code:';
       offerHtml += offer.offerCode;
       offerHtml += '</h5><h5>Discount:';
       offerHtml += offer.discountValue;
       offerHtml +=  '</h5></div></li>';
       */

    var offer$ = this.getOfferHtml(offer);
    $(offer$).appendTo('#offerContainer');


    /*
       $('#offerExpire_'+offer.id).position({
my: 'center top',
at: 'center top',
of: '#offer_' + offer.id,
offset: '0 0'
});
$('#offer_'+offer.id + ' .thumbnail').addClass('offer', 9000);
if (isOldAddition) {
$('#offer_'+offer.id+ ' span.addingDoneLabel').hide();
$('.hiddenTillAddSuccess').show();
} else {
$('#offer_'+offer.id+ ' span.addingDoneLabel').hide('highlight', 9000, function(){
$(this).parent().find('span.hiddenTillAddSuccess').show();
$(this).remove();
});
}
*/
};

it.offer.clearofferormVals = function () {
    $('.offerDetail').val('');
    $('#emailNotify').addClass('active');
    $('#fbNotify').removeClass('active');
};

it.offer.addOffer = function () {
    var code = $('#code').val(),
    expiryDateInMillis = $('#expiryDate').datepicker('getDate').getTime(),
    detail = $('#discountDetails').val(),
    emailNotify = $('#emailNotify').hasClass('active'),
    fbNotify = $('#fbNotify').hasClass('active'),
    notifyVO = {
        emailNotify: emailNotify,
        fbNotify: fbNotify
        //Will include time to notify etc. here
    },
    offer = {
        offerCode: code,
        description: detail,
        expiryDateInMillis: expiryDateInMillis,
        notifyVO: notifyVO
    },		
    offers = [],
    vendorId;
    offers.push(offer);
    $.post('saveOffers.do',
           {'offers': JSON.stringify(offers)},
           function (data) {
               var ret = $.parseJSON(data);
               if (ret.success === true) {
                   it.offer.appendOffers(ret.result);
                   it.offer.addHandlers();
               } else {
                   //Handle error case
               }
               $('#addOfferModal').modal('hide');
           });
};

it.offer.trashOffer = function(e) {
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
        } else {
            //Handle error case
        }
    });
};

it.offer.addHandlers = function () {

    $('.icon-trash').hover(function(e) {
        $(this).removeClass('icon-white');
    }, function(e){
        $(this).addClass('icon-white');
    });
    $('.offer-trash').click(it.offer.trashOffer).tooltip();
    $('.daysToExpireDetails').tooltip({
        placement: 'right'
    }).click(function(e){
        $(this).parent().find('.hiddenPartOfTime').show();
        $(this).tooltip('hide').remove();
    });
    $('.checkBoxSelected').live('click', function() {
        $(this).removeClass('checkBoxSelected').addClass('checkBoxUnSelected');
    });
    $('.checkBoxUnSelected').live('click', function(){
        $(this).removeClass('checkBoxUnSelected').addClass('checkBoxSelected');
    });
};

