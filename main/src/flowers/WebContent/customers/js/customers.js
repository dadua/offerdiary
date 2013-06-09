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
 
