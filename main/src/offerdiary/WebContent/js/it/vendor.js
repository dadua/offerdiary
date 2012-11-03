/**
 *
 */

var it = it || {};
it.vendor = it.vendor || {};
it.vendor.view = it.vendor.view || {};

//This object has all the html related data and methods
it.vendor.view = function () {
    var vendorHtml = ' \
	<li data-trigger="hover" class="span4 vendorSearch unselected" title="Default Vendor" > \
		<div class="vendorImage thumbnail"> \
        		<img src="images/stores/defaultVendor.jpg" /> \
        	</div> \
	</li>',
    vendorSearchContainerId = 'searchedVendors',
    containerId$;
    return {
	getHtmlTemplate: function () {
	    return vendorHtml;
	},
	getVendorSearchedContainerId : function () {
	    return vendorSearchContainerId;
	},
	getContainer$: function () {
	    return containerId$ || (containerId$ = $('#'+ vendorSearchContainerId));
	}
    };
}();

it.vendor.setImage = function (vendor, vendor$) {
    if (vendor.logoUrl) {
	vendor$.find('img').attr('src', 'images/stores/' + vendor.logoUrl).error(function(){
	    $(this).attr('src', 'images/stores/defaultVendor.jpg');
	});

    } else {
	//TODO: No Image found..
	//Have to put the vendor.name string to
	//the right of the trimmed down image..
    }
};

it.vendor.setNameDesc = function (vendor, vendor$) {
    vendor$.attr( {
	'title': vendor.name,
	'data-content': vendor.description
    });
};

it.vendor.plotOne = function (vendor) {
    var vendorHtml = this.view.getHtmlTemplate();
    var vendor$ = $(vendorHtml).clone().attr('id', 'vendorSearch_'+vendor.id);
    this.setImage(vendor, vendor$);
    this.setNameDesc(vendor, vendor$);
    vendor$.appendTo(this.view.getContainer$());
};

it.vendor.plotAll = function (vendors) {
    this.view.getContainer$().html('');
    for (var i=0;i < vendors.length;i++) {
	this.plotOne(vendors[i]);
    }
    $('.vendorSearch').popover({trigger: 'hover'});
};

it.vendor.plotAllAddable = function(query) {
    $.getJSON( "searchVendor.do", {searchKey: query}, function(data){
	it.vendor.plotAll (data.result);
    });

};

it.vendor.init = function(e) {
    this.plotAllAddable('');
};



//Unused
it.vendor.JqUiAutoCompleteInit = function() {
    var cache = {}, lastXhr;
    $('#vendor').autocomplete({
	minLength: 2,
	source: function( request, response ) {
	    var term = request.term;
	    if ( term in cache ) {
		response( cache[term] );
		return;
	    }
	    lastXhr = $.getJSON( "searchVendor.do", {searchKey: term}, function( data, status, xhr ) {
		var names = [];
		for (var i=0;i<data.result.length;i++) {
		    names.push(data.result[i].name);
		}
		cache[term] = names;
		if ( xhr === lastXhr ) {
		    response(names);
		}
	    });
	}
    });
};
