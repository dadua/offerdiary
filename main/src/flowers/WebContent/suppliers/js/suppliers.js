var it = it || {};
it.supplier = it.supplier || {};

it.supplier.plot = function (operation, data) {
    var config = this.config;
    it.entityplotter.newInstance($.extend(config, {data: data||{}, operation: operation}));
};

it.supplier.plotView = function (data) {
    this.plot('VIEW', data);
};


it.supplier.plotEdit = function (data) {
    this.plot('EDIT', data);
};

it.supplier.plotAddNew = function () {
    this.plot('ADDNEW', {});
};

it.supplier.config = {
    parentElemSelector: '#eachSupplierContainerFluid', 
    getUrl: 'getSupplierDetails.do',
    saveUrl: 'addSupplier.do', 
    title: 'Supplier',
    plotConfigs: [
        {
        name: 'name',
        displayKey: 'Name',
        type: 'string', 
        helpMsg: 'Supplier Name cant be empty',
        isRequired: true
    },
    /*
    {
        name: 'nickName',
        displayKey: 'Nick Name',
        isRequired: false,
        type: 'string'

    },
    */
    {
        name: 'bankAccountDetails',
        displayKey: 'Bank Account Details',
        type: 'string', 
        isRequired: false
    },
    {
        name: 'billingName',
        displayKey: 'Billing Name',
        type: 'string', 
        isRequired: false
    },
    {
    	name: 'phoneNo',
    	displayKey: 'Phone Number',
    	type: 'string', 
    	sRequired: false
    },
    {
    	name: 'alternativePhoneNo',
    	displayKey: 'Phone Number 2',
    	type: 'string', 
    	sRequired: false
    },
    {
    	name: 'address',
    	displayKey: 'Address',
    	type: 'string', 
    	sRequired: false
    }
]
};
 


it.supplier.view = function () {
    var _eachRowHtml = '<tr><td ><a class="name" href="#"></a></td><td class="bankAccountDetails"></td><td class="billingName"></td><td class="phoneNo"</td><td class="alternativePhoneNo"><td class="address"></td></tr>',
        
        _tableWithHeadingHtml = '<table class="table table-striped table-condensed table-bordered"> <thead> <tr> <th> Name </th> <th>Bank Details</th> <th>Billing Name</th> <th>Phone No.</th> <th>Phone no. 2</th> <th>Address</th> </tr> </thead> <tbody></tbody></table>',

        _get$ = function (suppliers) {
            var _$ = $(_tableWithHeadingHtml),
                _tableBody$ = _$.find('tbody'),
                currentSupplier = {};

            for (var i=0; i< suppliers.length; i++) {
                currentSupplier = suppliers[i];
                var _tr$ = $(_eachRowHtml);
                _tr$.find('.name').html(currentSupplier.name).find('.name').attr('href', 'supplier.do?id='+currentSupplier.id);
                _tr$.find('.bankAccountDetails').html(currentSupplier.bankAccountDetails);
                _tr$.find('.billingName').html(currentSupplier.billingName);
                _tr$.find('.phoneNo').html(currentSupplier.phoneNo);
                _tr$.find('.alternativePhoneNo').html(currentSupplier.alternativePhoneNo);
                _tr$.find('.address').html(currentSupplier.address);
                _tr$.data('entityId', currentSupplier.id);
                _tableBody$.append(_tr$);
            }
            return _$;
        };

    return {
        get$: _get$
    };

}();

it.supplier.plotAll = function (suppliers) {
    var _$ = this.view.get$(suppliers);
    $('#supplierContainerFluid').html(_$);
};


it.supplier.addOneHandlers = function () {
    $('#searchQuery').keyup(it.supplier.fetchAll);
};

it.supplier.initSuppliersUI = function (suppliers) {
    this.plotAll(suppliers);
    this.addOneHandlers();
};


it.supplier.fetchAll = function() {
    var q = $('#searchQuery').val();

    $.post('getSuppliers.do', {q:q}, function (respJSON) {
        var resp = $.parseJSON(respJSON);
        it.supplier.plotAll(resp.result);
    });
};
