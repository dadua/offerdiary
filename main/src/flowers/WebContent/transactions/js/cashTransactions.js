var it = it || {};
it.cashtx = it.cashtx || {};




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
it.cashtx.plotUI = function(operation, dataJSON) {

    var data = $.parseJSON(dataJSON);
    if (operation === 'ADDNEW') {
        this.plotAddNew(data);
    } else if (operation === 'VIEW') {
        this.plotView(data);
    } else if (operation === 'EDIT') {
        this.plotEdit(data);
    }
};

it.cashtx.plotAddNew = function (data) {
    this.plot('ADDNEW', data);
};

it.cashtx.plotView = function (data) {
    this.plot('VIEW', data);
};

it.cashtx.plotEdit = function (data) {
    this.plot('EDIT', data);
};

it.cashtx.plot = function(operation, data) {

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
        _customersList = null,

        _suppliersList = null,


        fetchCustomer = function(e) {
            if ($(this).hasClass('disabled')) {
                return;
            }
            var customerId = _customersList.getSelectedItems()[0];
            plotCustomer(customerId);
        },

        fetchSupplier = function(e) {
            if ($(this).hasClass('disabled')) {
                return;
            }
            var supplierId = _suppliersList.getSelectedItems()[0];
            plotSupplier(supplierId);
        },

        
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
            ///From here..
            _suppliersList = it.supplier.list.initMinimalSuppliersUI(
                supplierListContainer$,
                '.supplierSearch'
            ),
            entitySelected = 0;

            $('.supplierChoose').removeClass('hide');


            supplierListContainer$.on('entityPlotted', function(e, suppliers) {
                _suppliersList.view.hideSelectAllOption();
                $('.navigationControl').addClass('hide');
                $('#selectSupplier').removeClass('hide').addClass('disabled');
                supplierListContainer$.on('entityInListSelected', function(e, supplierId) {
                    entitySelected = entitySelected +1;

                    if (entitySelected === 1) {
                        $('#selectSupplier').removeClass('disabled').click(fetchSupplier);
                    } else {
                        $('#selectSupplier').addClass('disabled');
                    }
                });
                supplierListContainer$.on('entityInListUnSelected', function(e, supplierId) {
                    entitySelected = entitySelected -1;

                    if (entitySelected === 1) {
                        $('#selectSupplier').removeClass('disabled');
                    } else {
                        $('#selectSupplier').addClass('disabled');
                    }
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
            var cashTrasactionInstance = it.entityplotter.newInstance(cashTxConfig);
        },

        cashTxConfig = {
        },

        plotSupplier = function (supplierId, flowerTxEntries) {
            _supplierInstance = it.entityplotter.newInstance($.extend({}, {parentElemSelector: '#eachSupplierContainerFluid', data: {id: supplierId}, refreshFromServer: true, operation: 'VIEW'},
                                                  it.supplier.config));

            $('.supplierChoose').addClass('hide');
            $('.supplierDetail').removeClass('hide');
            supplierDetailContainer$.data('entityId', supplierId);
            var cashTrasactionInstance = it.entityplotter.newInstance(cashTxConfig);
        },

        plotFlowerTxEntries = function (flowersForContact, flowerTxEntries) {

            _flowerTxEntriesObj = it.cashtx.newFlowerTxEntriesInstance(_operation, flowerTxEntries, flowersForContact);
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
            
            var currentDate = new Date();
            flowerTx.dateMillis = currentDate.getTime();

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

it.cashtx.config = {
    parentElemSelector: '#cashTransactionContainer', 
    getUrl: 'getCashTransactionDetail.do',
    saveUrl: 'addCashTransaction.do', 
    title: 'Cash Transaction',
    plotConfigs: [
        {
        name: 'contact.name',
        displayKey: 'Contact Name',
        type: 'string', 
        helpMsg: 'Contact Name cant be empty',
        isRequired: false,
        customCellPlotCb: function(attrRow$, data) {

        },
        customGetDataFromDomCb: function(){

        }
        
    },

    {
        name: 'amount',
        displayKey: 'Amount Paid',
        type: 'string', 
        isRequired: false
    },
    {
        name: 'comment',
        displayKey: 'Comments',
        type: 'string', 
        isRequired: false
    }
    ]

};

it.cashtx.newInstance = function (config) {

    var _operationRowHtml = '<div class="row-fluid operationRow titleRow"> <span class="entityTitle bluishText largeTitleFontSize">  </span>  <span class="pull-right">  <button class="operation editBtn btn btn-mini btn-info hide">Edit</button> <button class="operation addNewBtn btn btn-mini btn-success hide">Add New</button><button class="operation saveBtn  btn btn-mini btn-success hide">Save</button><button class="operation cancelBtn btn btn-mini btn-success hide">Cancel</button></span></div><br/>',


    _tableBodyHtml = '<div class="row-fluid tableContentRow"> <table class="table table-striped table-bordered nameValTable"> <tbody class="nameValTableBody"> </tbody> </table> </div>',


    _eachNameValRowHtml = '<tr class="eachAttrRow"> <td><strong class="attribName"></strong></td> <td class="valueViewElement"></td> <td class="valueEntryElement hide"> <div class="control-group" style="margin-bottom: 0px;"> <div class="controls"> <input type="text" class="valueEntryInputTextBox"></input> <span class="help-block hide" style="font-size:.7em;">* </span> </div> </div> </td> </tr>',
    
    
    _get$ = function (config) {
        var _$ = $(_operationRowHtml + _tableBodyHtml),
            attrPlotConfigs = config.plotConfigs,
            _defaultPlotConfig = {
                displayKey: '',
                helpMsg: '',
                isRequired: false
            };


        _$.find('.entityTitle').html(config.title);
   

        for (var i=0; i<attrPlotConfigs.length; i++) {
            var currentAttrPlotConfig = attrPlotConfigs[i];

            currentAttrPlotConfig = $.extend({},  _defaultPlotConfig, currentAttrPlotConfig);

            var _eachAttr$ = $(_eachNameValRowHtml),
                _attrName = currentAttrPlotConfig.name,
                _attrValue = config.data[_attrName] || '',
                _customCellPlotCb = currentAttrPlotConfig.customCellPlotCb;

            if (typeof _customCellPlotCb === 'function') {
                _customCellPlotCb(_eachAttr$, config.data);
            } else {
                _eachAttr$.find('.valueViewElement').html(_attrValue);
                _eachAttr$.find('.valueEntryInputTextBox').val(_attrValue);
            }

            _eachAttr$.find('.attribName').html(currentAttrPlotConfig.displayKey);
            _eachAttr$.data('attrKey', _attrName);//This helps is fetching the data from the DOM

            var helpMsg = currentAttrPlotConfig.helpMsg;
            if (currentAttrPlotConfig.isRequired) {
                helpMsg = '*' + helpMsg;
            }

            _eachAttr$.find('.help-block').html(helpMsg);
            _$.find('.nameValTableBody').append(_eachAttr$);
        }


        return _$;
    },

    _isNotEmpty = function (elem$) {
        if ($(elem$).val() === '') {
            return false;
        } else {
            return true;
        }
    },

    _getFormElementForAttrName = function (attrName) {
        return _parentElem$.find('.eachAttrRow').filter(function(){return $(this).data('attrKey')==attrName;}).find('.valueEntryInputTextBox');
    },

    _elementNotEmptyHandler = function (elem$) {
        elem$ = elem$;

        if (_isNotEmpty(elem$)) {
            elem$.parent().parent().removeClass('error').find('.help-block').addClass('hide');
            _parentElem$.find('.saveBtn, .addNewBtn').removeClass('disabled');
        } else {
            elem$.parent().parent().addClass('error').find('.help-block').removeClass('hide');
            _parentElem$.find('.saveBtn, .addNewBtn').addClass('disabled');
        }
    },

    _getElemNotEmptyHandler = function (elem$) {
        return function () {
            _elementNotEmptyHandler(elem$);
        };
    },

    _validationHandlers = [],

    _addValidationHandlers = function () {
        var plotConfigs = _config.plotConfigs;

        for (var i=0; i< plotConfigs.length; i++) {
            var currentAttrPlotConfig = plotConfigs[i];

            if (currentAttrPlotConfig.isRequired) {
                var formElementForAttrName$ = _getFormElementForAttrName(currentAttrPlotConfig.name);
                var notEmptyHandler = _getElemNotEmptyHandler(formElementForAttrName$);
                _validationHandlers.push(notEmptyHandler);
                formElementForAttrName$.keyup(notEmptyHandler);
                _elementNotEmptyHandler(formElementForAttrName$);
            }
        }

    },

    _config = null,
    
    _parentElem$ = null,

    _newInstance = function (config) {
        _setConfig(config);
        _parentElem$ = $(_config.parentElemSelector);
        _showView(_config.data || {}, _config.refreshFromServer|| false);
    },

    _updateConfig = function (config) {
        _config = $.extend({},_config, config);
    },

    _setConfig = function (config) {
        var _defaultConfig = {  
                                data: {}
        
        }; 
        _config = $.extend({}, _defaultConfig, config);
    },

    _showView = function (data, refreshFromServer) {

        if (typeof refreshFromServer === 'boolean' && refreshFromServer) {
            _fetchAndPlot(data.id);
        } else {
            _plot(data);
        }
    },

    _reInit = function (config) {
        _updateConfig(config);
        _parentElem$ = $(_config.parentElemSelector);
        _showView(_config.data || {}, _config.refreshFromServer|| false);
    };


    var _getObjFromDom = function () {
        var data = {};
        _parentElem$.find('.eachAttrRow').each(function(index){
            var attrKey = $(this).data('attrKey'),
                attrVal = $(this).find('.valueEntryInputTextBox').val();

            data[attrKey] = attrVal;

        });

        var entityId = _parentElem$.data('entityId');
        if (entityId > 0) {
            data.id = entityId;
        }

        return data;
    };
    
    var _copyValueViewToFormValues = function () {
        _parentElem$.find('.valueEntryElement').each(function(){
            var this$ = $(this), 
                val = this$.parent().find('.valueViewElement').html();
            this$.find('input').val(val); 
        });

    };

    var _cancelHandler = function () {
        if (_config.operation === 'ADDNEW') {
            _showAddNewAtUI();
        } else {
            _showEditInUI();
        }
    };

    var _showEditInUI = function () {
        _parentElem$.find('.valueEntryElement').hide();
        _parentElem$.find('.valueViewElement').show();
        _parentElem$.find('.operation').addClass('hide');
        _parentElem$.find('.editBtn').removeClass('hide');
    };

    var _editHandler =  function () {
     
        _copyValueViewToFormValues();
        _showSaveCancelAtUI();

        _runAllValidations();
        _parentElem$.trigger('entityBeingEdited', [_parentElem$.data('entityId')]);
    };


    var _showSaveCancelAtUI = function () {
        _parentElem$.find('.valueEntryElement').show();
        _parentElem$.find('.valueViewElement').hide();
        _parentElem$.find('.operation').addClass('hide');
        _parentElem$.find('.saveBtn').removeClass('hide');
        _parentElem$.find('.cancelBtn').removeClass('hide');
    };


    var _showAddNewAtUI = function () {
        _parentElem$.find('.valueEntryElement').show();
        _parentElem$.find('.valueViewElement').hide();
        _parentElem$.find('.operation').addClass('hide');
        _parentElem$.find('.addNewBtn').removeClass('hide');
    };

    var _runAllValidations = function () {
        for (var i=0; i<_validationHandlers.length; i++) {
            _validationHandlers[i]();
        }
    };


    var _plot = function (data) {
        _config = $.extend({}, _config,{data: data});
        _validationHandlers = [];
        var _$ = _get$(_config);
        _parentElem$.html(_$);

        _parentElem$.data('entityId', _config.data.id || 0);
        _addHandlers();

        if (_config.operation === 'VIEW') {
            _showEditInUI();
            _parentElem$.trigger('entityViewPlotted', [_parentElem$.data('entityId')]);
        } else if (_config.operation === 'EDIT') {
            _showSaveCancelAtUI();
            _parentElem$.trigger('entityEditlotted', [_parentElem$.data('entityId')]);
        } else if (_config.operation === 'ADDNEW') {
            _showAddNewAtUI();
            _parentElem$.trigger('entityAddNewPlotted', [_parentElem$.data('entityId')]);
        }
    };

    var _fetchAndPlot = function (id) {
        $.post(_config.getUrl, {id: id}, function(respJson){
            var resp = $.parseJSON(respJson);
            _plot(resp.result);
        });
    };


    var _addNewHandler = function () {
        if (_parentElem$.find('.addNewBtn').hasClass('disabled')) {
            return;
        }
        var data = _getObjFromDom();
        $.post(_config.saveUrl, {entityJson: JSON.stringify(data)}, function (respJson) {
            var resp = $.parseJSON(respJson);
            _config.operation = 'VIEW';
            _showView(resp.result);
            _parentElem$.trigger('entitySaved', [_parentElem$.data('entityId')]);
        });
        _parentElem$.trigger('entityBeingSaved', [_parentElem$.data('entityId')]);
    };

    var _saveHandler = function () {
        if (_parentElem$.find('.saveBtn').hasClass('disabled')) {
            return;
        }

        var data = _getObjFromDom();
        $.post(_config.saveUrl, {entityJson: JSON.stringify(data)}, function (respJson) {
            var resp = $.parseJSON(respJson);
            if (resp.success) {
                _config.operation = 'VIEW';
                _showView(resp.result);
                _parentElem$.trigger('entitySaved', [_parentElem$.data('entityId')]);
            }
        });
        _parentElem$.trigger('entityBeingSaved', [_parentElem$.data('entityId')]);
    };

    var _addHandlers = function () {
        _parentElem$.find('.editBtn').click(_editHandler);
        _parentElem$.find('.saveBtn').click(_saveHandler);
        _parentElem$.find('.cancelBtn').click(_cancelHandler);
        _parentElem$.find('.addNewBtn').click(_addNewHandler);
        _addValidationHandlers();
    };

    _newInstance(config);
    return {
        get$: _get$,
        addHandlers: _addHandlers,
        reInit: _reInit,
        setConfig: _setConfig,
        updateConfig: _updateConfig
    };
};
