var it = it || {};
it.entityplotter = it.entityplotter || {};
it.entityplotter.view = it.entityplotter.view || {};

/**
 * example config
 * config = {
 *              parentElemSelector: '#containerFluid', 
 *                                      - Under which element to plot this entity
 *                                      - Should be a container-fluid
 *              operation: 'VIEW' or 'EDIT' or 'ADDNEw',
 *              title: 'Flower',
 *              getUrl: 'getFlower.do', - default id would return a result wrapped object
 *              saveUrl: 'saveFlower.do', 
 *              refreshFromServer: default false,
 *              data: {
 *                      propName1: 'propVal1',
 *                      propName2: 'propVal2'
 *                  },  - this would be utilized in the case of VIEW or EDIT operation
 *                      - in case data is present then no need to call getUrl
 *              plotConfigs: [  {
 *                                  name: 'propName1',
 *                                  displayKey: 'Prop Name 1',
 *                                  type: 'string or number or ..',
 *                                              - default type would be string
 *                                              - This would map to a input text
 *                                  helpMsg: 'User Name cant be empty',
 *                                  isRequired: 'false by default'
 *                                              - Save would be disabled incase any
 *                                              - propName having isRequired = true is empty
 *                              },
 *                              {
 *                                  name: 'propName2',
 *                                              ...
 *                                              ...
 *
 *                              }
 *                          ]
 *          };
 *  @return - entityPlotter object
 *
 */
it.entityplotter.newInstance = function (config) {

    var _get$ = function (config) {
        var attrPlotConfigs = config.plotConfigs,
            _$ = $(_operationRowHtml + _tableBodyHtml),

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
                _attrValue = config.data[_attrName] || '';
            _eachAttr$.find('.attribName').html(currentAttrPlotConfig.displayKey);
            _eachAttr$.find('.valueViewElement').html(_attrValue);
            _eachAttr$.find('.valueEntryInputTextBox').val(_attrValue);

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
    },
    
    _operationRowHtml = '<div class="row-fluid operationRow titleRow"> <span class="entityTitle largeTitleFontSize">  </span>  <span class="pull-right">  <button class="operation editBtn btn btn-mini btn-info hide">Edit</button> <button class="operation addNewBtn btn btn-mini btn-success hide">Add New</button><button class="operation saveBtn  btn btn-mini btn-success hide">Save</button><button class="operation cancelBtn btn btn-mini btn-success hide">Cancel</button></span></div><br/>',


    _tableBodyHtml = '<div class="row-fluid tableContentRow"> <table class="table table-striped table-bordered nameValTable"> <tbody class="nameValTableBody"> </tbody> </table> </div>',


    _eachNameValRowHtml = '<tr class="eachAttrRow"> <td><strong class="attribName"></strong></td> <td class="valueViewElement"></td> <td class="valueEntryElement hide"> <div class="control-group" style="margin-bottom: 0px;"> <div class="controls"> <input type="text" class="valueEntryInputTextBox"></input> <span class="help-block hide" style="font-size:.7em;">* </span> </div> </div> </td> </tr>';



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
        _parentElem$.trigger('entityBeingEdited', _parentElem$.data('entityId'));
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
            _parentElem$.trigger('entityViewPlotted', _parentElem$.data('entityId'));
        } else if (_config.operation === 'EDIT') {
            _showSaveCancelAtUI();
            _parentElem$.trigger('entityEditlotted', _parentElem$.data('entityId'));
        } else if (_config.operation === 'ADDNEW') {
            _showAddNewAtUI();
            _parentElem$.trigger('entityAddNewPlotted', _parentElem$.data('entityId'));
        }
    };

    var _fetchAndPlot = function (id) {
        $.post(_config.getUrl, {id: id}, function(respJSON){
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
            _parentElem$.trigger('entitySaved', _parentElem$.data('entityId'));
        });
        _parentElem$.trigger('entityBeingSaved', _parentElem$.data('entityId'));
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
                _parentElem$.trigger('entitySaved', _parentElem$.data('entityId'));
            }
        });
        _parentElem$.trigger('entityBeingSaved', _parentElem$.data('entityId'));
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

