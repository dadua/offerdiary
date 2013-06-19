var it = it || {};
it.flowertx = it.flowertx || {};




/**
 * data = {
 *              transactionType: 'IN' or 'OUT',
 *                  //IN implies Supplier,
 *                  //OUT implies customer
 *              contact: {  
 *                          id: number,
 *                          type: 'CUSTOMER' or 'SUPPLIER',
 *                          ...
 *              }, //In case of operation == 'ADDNEW' contact wouldn't be there
 */
it.flowertx.plotUI = function(operation, dataJSON) {

    var data = $.parseJSON(dataJSON);
    if (operation === 'ADDNEW') {
        this.plotAddNew(data);
    } else if (operation === 'VIEW') {
        this.plotView(data);
    } else if (operation === 'EDIT') {
        this.plotEdit(data);
    }
};

it.flowertx.plotAddNew = function (data) {
    this.newInstance('ADDNEW', data);
};

it.flowertx.plotView = function (data) {
    this.newInstance('VIEW', data);
};

it.flowertx.plotEdit = function (data) {
    this.newInstance('EDIT', data);
};

it.flowertx.newInstance = function(operation, data) {

    var customerDetail$ = $('.customerDetail'),

        customerDetailContainer$ = customerDetail$.find('#eachCustomerContainerFluid'),

        supplierDetail$ = $('.supplierDetail'),

        supplierDetailContainer$ = supplierDetail$.find('#eachSupplierContainerFluid'),

        customerListContainer$ = $('#customerChooseContainerFluid'),

        supplierListContainer$ = $('#supplierChooseContainerFluid'),

        _data = data,

        _operation = operation,
        
        _flowerTxEntriesObj = {},

        //view = this.newViewInstance(_data),
        //

        fetchCustomer = function(e) {
            if ($(this).hasClass('disabled')) {
                return;
            }
            var customerId = _customersList.getSelectedItems()[0];
            plotCustomer(customerId);
        },

        _customersList = null;

        fetchCustomers = function () {
            _customersList = it.customer.list.initMinimalCustomersUI(
                customerListContainer$,
                '.customerSearch'
            ),
            entitySelected = 0;

            $('.customerChoose').removeClass('hide');


            customerListContainer$.on('entityPlotted', function(e, customers) {
                _customersList.view.hideSelectAllOption();
                $('.navigationControl').addClass('hide');
                $('#selectCustomer').removeClass('hide').addClass('disabled');
                customerListContainer$.on('entityInListSelected', function(e, customerId) {
                    entitySelected = entitySelected +1;

                    if (entitySelected === 1) {
                        $('#selectCustomer').removeClass('disabled').click(fetchCustomer);
                    } else {
                        $('#selectCustomer').addClass('disabled');
                    }
                });
                customerListContainer$.on('entityInListUnSelected', function(e, customerId) {
                    entitySelected = entitySelected -1;

                    if (entitySelected === 1) {
                        $('#selectCustomer').removeClass('disabled');
                    } else {
                        $('#selectCustomer').addClass('disabled');
                    }
                });
            });

        },

        _showChooseContactBtn = function (contactType) {
            $('.contactChoose').addClass('hide');
            if (contactType==='SUPPLIER') {
                $('#supplierChoose').removeClass('hide');
            } else {
                $('#customerChoose').removeClass('hide');
            }
        },

        fetchSuppliers = function () {
            var suppliersList = it.supplier.list.initMinimalSuppliersUI(
                supplierListContainer$,
                '.supplierSearch'
            );

            supplierListContainer$.on('entityPlotted', function(e, suppliers) {
                suppliersList.view.hideSelectAllOption();
                $('.supplierChoose').removeClass('hide');
                supplierListContainer$.on('entityInListSelected', function(e, supplierId) {
                    plotSupplier(supplierId);
                });
            });
        },
        
        
        _showEditInUi = function () {
            $('.navigationControl').addClass('hide');
            $('#saveTransaction').removeClass('hide');
            
            if (typeof _flowerTxEntriesObj.showOp === 'function') {
                _flowerTxEntriesObj.showOp(_operation);
            }
            //$('#cancelTransaction').removeClass('hide');
        },

        _showViewInUi = function () {
            $('.txOperation').addClass('hide');
            if (typeof _flowerTxEntriesObj.showOp === 'function') {
                _flowerTxEntriesObj.showOp(_operation);
            }
            //$('#editTransaction').removeClass('hide');
        },

        plotCustomer = function(customerId, flowerTxEntries) {
            _customerInstance = it.entityplotter.newInstance($.extend({}, {parentElemSelector: '#eachCustomerContainerFluid', data: {id: customerId}, refreshFromServer: true, operation: 'VIEW'},
                                                  it.customer.config));
            $('.customerChoose').addClass('hide');
            $('.customerDetail').removeClass('hide');

            customerDetailContainer$.data('entityId', customerId);
            it.customer.getFlowers(customerId, function(flowers) {
                plotFlowerTxEntries(flowers, flowerTxEntries);
                
            });

        },

        plotSupplier = function (supplierId, flowerTxEntries) {
            _supplierInstance = it.entityplotter.newInstance($.extend({}, {parentElemSelector: '#eachSupplierContainerFluid', data: {id: supplierId}, refreshFromServer: true, operation: 'VIEW'},
                                                  it.supplier.config));
            supplierDetailContainer$.data('entityId', supplierId);
            it.supplier.getFlowers(supplierId, function(flowers) {
                plotFlowerTxEntries(flowers, flowerTxEntries);
            });
        },

        plotFlowerTxEntries = function (flowersForContact, flowerTxEntries) {

            _flowerTxEntriesObj = it.flowertx.newFlowerTxEntriesInstance(_operation, flowerTxEntries, flowersForContact);
            $('.flowerTxEntryRowFluid').removeClass('hide');
            _addHandlers();

        },

        _getTxType = function () {
            if (_data.transactionType === 'IN') {
                return 'SUPPLIER';
            } else {
                return 'CUSTOMER';
            }

        },

        _saveHandler = function () {
            var flowerTxEntries = _flowerTxEntriesObj.getData(),
            flowerTx;

            if (_getTxType() === 'SUPPLIER')  {
                var supplierId = supplierDetailContainer$.data('entityId');
                flowerTx = {
                    transactionType: 'IN',
                    contact: {
                        id: supplierId,
                        type: 'SUPPLIER'
                    },
                    flowerTransactionEntries: flowerTxEntries
                };
            } else {
                var customerId = customerDetailContainer$.data('entityId');
                flowerTx = {
                    transactionType: 'OUT',
                    contact: {
                        id: customerId,
                        type: 'CUSTOMER'
                    },
                    flowerTransactionEntries: flowerTxEntries
                };
            }

            $.post('addFlowerTransaction.do', {flower_tx: JSON.stringify(flowerTx)}, function(respJSON) {
                var resp = $.parseJSON(respJSON);
                _plot('VIEW', resp.result);
            });
        },

        _addHandlers = function () {
            $('#saveTransaction').off('click', _saveHandler).on('click', _saveHandler);
        },

        _fetchTransaction = function (flowerTxId, operation) {
            $.post('getFlowerTransactionDetail.do', {flower_tx_id: flowerTxId}, function (respJSON) {
                var resp = $.parseJSON(respJSON);
            }); 
        },

        _plot = function (operation, flowerTx) {
            _operation = operation;
            _data = flowerTx;
            if (operation === 'ADDNEW') {
                if (_data.transactionType === 'IN') {
                    fetchSuppliers();
                } else {
                    fetchCustomers();
                }
                _showEditInUi(operation);

            } else if (operation === 'VIEW') {
                if (_data.contact.type === 'CUSTOMER') {
                    plotCustomer(_data.contact.id, _data.flowerTransactionEntries);
                } else if (_data.contact.type === 'SUPPLIER'){
                    plotSupplier(_data.contact.id, _data.flowerTransactionEntries);
                }
                _showViewInUi(operation);
            } else if (operation === 'EDIT') {
                if (_data.contact.type === 'CUSTOMER') {
                    plotCustomer(_data.contact.id, _data.flowerTransactionEntries);
                } else if (_data.contact.type === 'SUPPLIER'){
                    plotSupplier(_data.contact.id, _data.flowerTransactionEntries);
                }
                _showEditInUi(operation);
                
            }
        },

        fetch = function (operation, flowerTx) {
            _operation = operation;
            $.post('getFlowerTransactionDetail.do', {flower_tx_id: flowerTx.id}, function (respJSON) {
                var resp = $.parseJSON(respJSON);
                _plot(operation, resp.result);
            });

        };

        _plot(operation, _data);

    return {
        fetch: fetch,
        plot: _plot
    };
};

