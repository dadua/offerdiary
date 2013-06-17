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
            $('.flowerTxEntryRowFluid').removeClass('hide');

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
            $('.txOperation').addClass('hide');
            $('#saveTransaction').removeClass('hide');
            //$('#cancelTransaction').removeClass('hide');
        },

        _showViewInUi = function () {
            txEntriesTable$.find('.valueViewElement').removeClass('hide');
            txEntriesTable$.find('.valueEntryElement').addClass('hide');
            $('.txOperation').addClass('hide');
            //$('#editTransaction').removeClass('hide');
        },
        
        _allowOnlyNumber = function(event) {
            // Allow: backspace, delete, tab, escape, and enter
            if ( event.keyCode == 46 || event.keyCode == 8 || event.keyCode == 9 || event.keyCode == 27 || event.keyCode == 13 || 
                 // Allow: Ctrl+A
                (event.keyCode == 65 && event.ctrlKey === true) || 
                 // Allow: home, end, left, right
                (event.keyCode >= 35 && event.keyCode <= 39)) {
                     // let it happen, don't do anything
                     return;
            } else if (event.keyCode == 190) {
                if($(this).val().indexOf('.')===-1) {
                    return;
                } else {
                    event.preventDefault();
                }
            } else if (event.shiftKey || (event.keyCode < 48 || event.keyCode > 57) && (event.keyCode < 96 || event.keyCode > 105 )) { //// Ensure that it is a number and stop the keypress
                event.preventDefault(); 
            }
        },

        _onPriceQtyChangedHandler = function () {
            var this$ = $(this),
            tr$ = this$.closest('tr'),
            unitPrice$ = tr$.find('[name="unitPrice"]'),
            quantity$ = tr$.find('[name="quantity"]'),
            totalCost$ = tr$.find('[name="totalCost"]');

            totalCost$.val(unitPrice$.val()*quantity$.val());
        },

        _checkAddRemoveState = function () {
            var txRows$ = txEntriesTable$.find('.eachTransactionEntryRow');
            if (txRows$.size() == 1) {
                txRows$.find('.removeTxEntry').addClass('invisible');
            } else {
                txRows$.find('.removeTxEntry').removeClass('invisible');
            }
        },

        _onAddNewRow = function (e) {
            plot();
        },

        _onRemoveRow = function (e) {
            $(this).closest('tr').remove();
            _checkAddRemoveState();
        },
        
        addHandlers = function () {
            
            //txEntriesTable$.find('.valueEntryElement').find('[name="flower"]');

            txEntriesTable$.find('.valueEntryElement').find('[name="unitPrice"]').off('keyup', _onPriceQtyChangedHandler).off('keydown', _allowOnlyNumber);
            txEntriesTable$.find('.valueEntryElement').find('[name="unitPrice"]').on('keyup', _onPriceQtyChangedHandler).on('keydown', _allowOnlyNumber);

            txEntriesTable$.find('.valueEntryElement').find('[name="quantity"]').off('keyup', _onPriceQtyChangedHandler).off('keydown', _allowOnlyNumber);
            txEntriesTable$.find('.valueEntryElement').find('[name="quantity"]').on('keyup', _onPriceQtyChangedHandler).on('keydown', _allowOnlyNumber);
            
            txEntriesTable$.find('.valueEntryElement').find('.addNewTxEntry').off('click', _onAddNewRow);
            txEntriesTable$.find('.valueEntryElement').find('.addNewTxEntry').on('click', _onAddNewRow);

            txEntriesTable$.find('.valueEntryElement').find('.removeTxEntry').off('click', _onRemoveRow);
            txEntriesTable$.find('.valueEntryElement').find('.removeTxEntry').on('click', _onRemoveRow);
            
            //txEntriesTable$.find('.valueEntryElement').find('[name="totalCost"]');

            txEntriesTable$.find('.valueEntryElement').find('[name="date"]').datepicker({"dateFormat": 'dd/mm/yy'}).datepicker("setDate", new Date());
            
            _checkAddRemoveState();
            

        },

        plot = function (flowerTxEntriesP) {
            var txEntryRows$ = getEntries$(flowerTxEntriesP);
            $.each(txEntryRows$, function(i, txEntryRow$) {
                txEntriesTableBody$.append(txEntryRow$);
            });
            
            addHandlers();

            if (_operation === 'ADDNEW' || _operation === 'EDIT') {
                _showEditInUi();
            } else {
                _showViewInUi();
            }
        },
        
        _getDataFromDom = function () {
        	
        };

        plot(flowerTxEntries);

    return {
        plot: plot
    };

};
