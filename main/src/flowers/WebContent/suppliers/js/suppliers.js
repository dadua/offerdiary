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
 
