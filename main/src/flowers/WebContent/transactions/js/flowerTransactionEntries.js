var it = it || {};
it.flowertx = it.flowertx || {};

it.flowertx.newFlowerTxEntriesInstance = function (operation, flowerTxEntries, flowers) {
    var txEntriesTable$ = $('#transactionEntries'),

        txEntriesTableBody$ = txEntriesTable$.find('tbody'),

        txEntryRowTemplate$ = $('.eachTransactionEntryRowTemplate').clone().removeClass('eachTransactionEntryRowTemplate hide').addClass('eachTransactionEntryRow'),

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

            flowerTxEntry = $.extend({}, emptyFlowerTxEntry, flowerTxEntry);

            if (typeof flowerTxEntry.flower.id === 'number' && flowerTxEntry.flower.id !== 0) {
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

            var dateElem$ = txEntryRow$.find('.valueEntryElement').find('[name="date"]'),
                entityDate;
            if (typeof flowerTxEntry.dateMillis === 'number') {
                entityDate = new Date(flowerTxEntry.dateMillis);
            } else {
                entityDate = new Date();
            }
            dateElem$.datepicker({"dateFormat": 'dd/mm/yy'}).datepicker("setDate", entityDate);
            txEntryRow$.find('.date').html($.datepicker.formatDate('dd/mm/yy', dateElem$.datepicker("getDate")));

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
            $('.navigationControl').addClass('hide');
            $('#saveTransaction').removeClass('hide');
        },

        _showViewInUi = function () {
            txEntriesTable$.find('.valueViewElement').removeClass('hide');
            txEntriesTable$.find('.valueEntryElement').addClass('hide');
            $('.navigationControl').addClass('hide');
            //TODO: Edit Operation has to be added
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
            var emptyRow = _getFlowerTxEntry$({});
            txEntriesTable$.append(emptyRow);
            addHandlers();
            _showOperation(_operation);
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

            
            _checkAddRemoveState();
            

        },

        plot = function (flowerTxEntriesP) {
            var txEntryRows$ = getEntries$(flowerTxEntriesP);
            txEntriesTableBody$.html('');
            $.each(txEntryRows$, function(i, txEntryRow$) {
                txEntriesTableBody$.append(txEntryRow$);
            });
            
            addHandlers();

            _showOperation(_operation);
            //console.log(_getDataFromDom());
        },

        _showOperation = function (op) {
            if (op === 'ADDNEW' || op === 'EDIT') {
                _showEditInUi();

            } else {
                _showViewInUi();
            }

        },

        _getDataFromDom = function () {
            var txEntries = [];

            txEntriesTable$.find('.eachTransactionEntryRow').each(function(i, elem) {
                var row$ = $(this),
                totalCost = row$.find('[name="totalCost"]').val(),
                quantity = row$.find('[name="quantity"]').val(),
                unitPrice = row$.find('[name="unitPrice"]').val(),
                flowerId = row$.find('[name="flower"]').val(),
                dateMillis = row$.find('[name="date"]').datepicker('getDate').getTime(),
                id = row$.data('entityId'),
                txEntry = {
                    id: id,
                    totalCost: totalCost,
                    quantity: quantity,
                    unitPrice: unitPrice,
                    flower: {
                        id: flowerId
                    },
                    dateMillis: dateMillis
                };
                txEntries.push(txEntry);
            });

            return txEntries;
        };

        plot(flowerTxEntries);

    return {
        plot: plot,
        getData: _getDataFromDom,
        showOp: _showOperation
    };

};
