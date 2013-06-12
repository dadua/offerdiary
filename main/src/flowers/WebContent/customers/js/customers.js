var it = it || {};
it.customer = it.customer || {};

it.customer.plot = function (operation, data) {
    var config = this.config;
    it.entityplotter.newInstance($.extend(config, {data: data||{}, operation: operation}));
};

it.customer.plotView = function (data) {
    this.plot('VIEW', data);
};


it.customer.plotEdit = function (data) {
    this.plot('EDIT', data);
};

it.customer.plotAddNew = function () {
    this.plot('ADDNEW', {});
};

it.customer.config = {
    parentElemSelector: '#eachCustomerContainerFluid', 
    getUrl: 'getCustomerDetails.do',
    saveUrl: 'addCustomer.do', 
    title: 'Customer',
    plotConfigs: [
        {
        name: 'name',
        displayKey: 'Name',
        type: 'string', 
        helpMsg: 'Customer Name cant be empty',
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

it.customer.view = function () {
    var _eachRowHtml = '<tr><td ><a class="name" href="#"></a></td><td class="bankAccountDetails"></td><td class="billingName"></td><td class="phoneNo"</td><td class="alternativePhoneNo"><td class="address"></td></tr>',
        
        _tableWithHeadingHtml = '<table class="table table-striped table-condensed table-bordered"> <thead> <tr> <th> Name </th> <th>Bank Details</th> <th>Billing Name</th> <th>Phone No.</th> <th>Phone no. 2</th> <th>Address</th> </tr> </thead> <tbody></tbody></table>',

        _get$ = function (customers) {
            var _$ = $(_tableWithHeadingHtml),
                _tableBody$ = _$.find('tbody'),
                currentCustomer = {};

            for (var i=0; i< customers.length; i++) {
                currentCustomer = customers[i];
                var _tr$ = $(_eachRowHtml);
                _tr$.find('.name').html(currentCustomer.name).find('.name').attr('href', 'customer.do?id='+ currentCustomer.id);
                _tr$.find('.bankAccountDetails').html(currentCustomer.bankAccountDetails);
                _tr$.find('.billingName').html(currentCustomer.billingName);
                _tr$.find('.phoneNo').html(currentCustomer.phoneNo);
                _tr$.find('.alternativePhoneNo').html(currentCustomer.alternativePhoneNo);
                _tr$.find('.address').html(currentCustomer.address);
                _tr$.data('entityId', currentCustomer.id);
                _tableBody$.append(_tr$);
            }
            return _$;
        };

    return {
        get$: _get$
    };

}();

it.customer.plotAll = function (customers) {
    var _$ = this.view.get$(customers);
    $('#customerContainerFluid').html(_$);
};


it.customer.addOneHandlers = function () {
    $('#searchQuery').keyup(it.customer.fetchAll);
};

it.customer.initCustomersUI = function (customers) {
    this.plotAll(customers);
    this.addOneHandlers();
};


it.customer.fetchAll = function() {
    var q = $('#searchQuery').val();

    $.post('getCustomers.do', {q:q}, function (respJSON) {
        var resp = $.parseJSON(respJSON);
        it.customer.plotAll(resp.result);
    });
};
