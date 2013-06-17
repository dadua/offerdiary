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

        //view = this.newViewInstance(_data),

        fetchCustomers = function () {
            var customersList = it.customer.list.initMinimalCustomersUI(
                customerListContainer$,
                '.customerSearch'
            );

            customerListContainer$.on('entityPlotted', function(e, customers) {
                customersList.view.hideSelectAllOption();
                $('.customerChoose').removeClass('hide');
                customerListContainer$.on('entityInListSelected', function(e, customerId) {
                    plotCustomer(customerId);
                });
            });

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

        plotCustomer = function(customerId, flowerTxEntries) {
            it.entityplotter.newInstance($.extend({}, {parentElemSelector: '#eachCustomerContainerFluid', data: {id: customerId}, refreshFromServer: true, operation: 'VIEW'},
                                                  it.customer.config));
            $('.customerChoose').addClass('hide');
            $('.customerDetail').removeClass('hide');

            customerDetailContainer$.data('entityId', customerId);
            it.customer.getFlowers(customerId, function(flowers) {
                plotFlowerTxEntries(flowers, flowerTxEntries);
                
            });

        },

        plotSupplier = function (supplierId, flowerTxEntries) {
            it.entityplotter.newInstance($.extend({}, {parentElemSelector: '#eachSupplierContainerFluid', data: {id: supplierId}, refreshFromServer: true, operation: 'VIEW'},
                                                  it.supplier.config));
            supplierDetailContainer$.data('entityId', supplierId);
            it.supplier.getFlowers(supplierId, function(flowers) {
                plotFlowerTxEntries(flowers, flowerTxEntries);
            });
        },

        plotFlowerTxEntries = function (flowersForContact, flowerTxEntries) {

            it.flowertx.newFlowerTxEntriesInstance(_operation, flowerTxEntries, flowersForContact);

        },

        _plot = function (flowertx, operation) {
            if (operation === 'ADDNEW') {
                if (_data.transactionType === 'IN') {
                    fetchSuppliers();
                } else {
                    fetchCustomers();
                }

            } else if (operation === 'VIEW') {
                if (_data.contact.type === 'CUSTOMER') {
                    plotCustomer(_data.contact.id, _data.flowerTransactionEntries);
                } else if (_data.contact.type === 'SUPPLIER'){
                    plotSupplier(_data.contact.id, _data.flowerTransactionEntries);
                }
            } else if (operation === 'EDIT') {
                if (_data.contact.type === 'CUSTOMER') {
                    plotCustomer(_data.contact.id, _data.flowerTransactionEntries);
                } else if (_data.contact.type === 'SUPPLIER'){
                    plotSupplier(_data.contact.id, _data.flowerTransactionEntries);
                }
            }
        },

        fetch = function (flowertx, operation) {
            $.post('getFlowerTransactionDetail.do', {flower_tx_id: flowertx.id}, function (respJSON) {
                var resp = $.parseJSON(respJSON);
                _plot(resp.result, operation);
            });

        };

        _plot(_data, operation);

    return {
        fetch: fetch,
        plot: _plot
    };
};

it.flowertx.newFlowerTxEntriesInstance = function (operation, flowerTxEntries, flowers) {
    var txEntriesTable$ = $('#transactionEntries'),

        txEntriesTableBody$ = txEntriesTable$.find('tbody'),

        txEntryRowTemplate$ = txEntriesTable$.find('.eachTransactionEntryRowTemplate').clone().removeClass('eachTransactionEntryRowTemplate hide').addClass('eachTransactionEntryRow'),

        _flowerTxEntries = flowerTxEntries,

        _operation = operation,

        _flowers = flowers,

        _getFlowerTxEntry$ = function(flowerTxEntry) {
            var txEntryRow$ = txEntryRowTemplate$.clone(),
                emptyFlowerTxEntry = {
                                        id: 0,
                                        uniqueId: '',
                                        flower: {
                                            name: '',
                                            id: 0
                                        },
                                        date: '', //Today's Date??
                                        unitPrice: 0,
                                        quantity: 0,
                                        totalCost: 0
                };

            flowerTxEntry = $.extend({},flowerTxEntry, emptyFlowerTxEntry);

            if (flowerTxEntry.flower.id === 0) {
                txEntryRow$.find('.flower').html(flowerTxEntry.flower.name).attr('href', 'flower.do?id='+flowerTxEntry.flower.id);
            } else {
                txEntryRow$.find('.flower').html(flowerTxEntry.flower.name);
            }

            $.each(_flowers, function (i, flower) {
                txEntryRow$.find('.valueEntryElement').find('select[name="flower"]').append('<option value="'+flower.id+'">'+flower.name+'</option>');
            });

            txEntryRow$.find('.valueEntryElement').find('[name="flower"]').val(flowerTxEntry.flower.id);

            txEntryRow$.find('.uniqueId').html(flowerTxEntry.uniqueId);

            txEntryRow$.find('.unitPrice').html(flowerTxEntry.unitPrice);

            txEntryRow$.find('.valueEntryElement').find('[name="unitPrice"]').val(flowerTxEntry.unitPrice);

            txEntryRow$.find('.quantity').html(flowerTxEntry.quantity);

            txEntryRow$.find('.valueEntryElement').find('[name="quantity"]').val(flowerTxEntry.quantity);

            txEntryRow$.find('.totalCost').html(flowerTxEntry.totalCost);

            txEntryRow$.find('.valueEntryElement').find('[name="totalCost"]').val(flowerTxEntry.totalCost);

            txEntryRow$.find('.date').html(flowerTxEntry.date);//TODO: This would be problematic

            txEntryRow$.find('.valueEntryElement').find('[name="date"]').val(flowerTxEntry.date);

            return txEntryRow$;
        },

        getEntries$ = function (flowerTxEntriesP) {
            var txEntryRows$ =[];

            if ($.isArray(flowerTxEntriesP)) {
                $.each(flowerTxEntriesP, function(i, flowerTxEntry) {
                    txEntryRows$.push(_getFlowerTxEntry$(flowerTxEntry));
                });
            } else {
                //Default one empty row
                txEntryRows$.push(_getFlowerTxEntry$({}));

            }
            return txEntryRows$;
        },

        _showEditInUi = function () {
            txEntriesTable$.find('.valueViewElement').addClass('hide');
            txEntriesTable$.find('.valueEntryElement').removeClass('hide');
        },

        _showViewInUi = function () {
            txEntriesTable$.find('.valueViewElement').removeClass('hide');
            txEntriesTable$.find('.valueEntryElement').addClass('hide');
        },

        plot = function (flowerTxEntriesP) {
            var txEntryRows$ = getEntries$(flowerTxEntriesP);
            $.each(txEntryRows$, function(i, txEntryRow$) {
                txEntriesTableBody$.append(txEntryRow$);
            });

            if (_operation === 'ADDNEW' || _operation === 'EDIT') {
                _showEditInUi();
            } else {
                _showViewInUi();
            }
        };

        plot(flowerTxEntries);

    return {
        plot: plot
    };

};

it.flowertx.newViewInstance = function () {
};
