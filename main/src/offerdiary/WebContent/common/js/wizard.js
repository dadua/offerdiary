/**
 *
 */
var it = it || {};
it.wizard = it.wizard || {};
it.wizard.step = it.wizard.step || {};

it.wizard.step.newInstance = function (pTitleOrOptionsObj, pJqueryHtmlTemplateSelector, pValidatorFunction) {

    //private
    var _title = 'Default Step Title';
    var _setTitle = function(title) {
        var titleType = typeof title;
        if (titleType === 'string') {
            _title = title;
        } else {
            throw {
                message: 'title string, is incorrectly of type: ' + titleType
            };
        }
    };

    var _htmlTemplateSelector = null;
    var _setHtmlTemplateSelector = function (jqueryHtmlSelector) {
        var jqueryHtmlSelectorType = typeof jqueryHtmlSelector;
        if (jqueryHtmlSelectorType == 'string') {
            _htmlTemplateSelector = jqueryHtmlSelector;
        } else {
            throw {
                message: 'jqueryHtmlSelector is incorrectly of type: ' + jqueryHtmlSelectorType
            };
        }
    };

    var _stepValidator = function () {
        //The default validator validates this instance of the step
        return true;
    };

    var _setStepValidator = function (validatorFunction) {
        var validatorFunctionType = typeof validatorFunction;
        if (validatorFunctionType == 'function') {
            _stepValidator = validatorFunction;
        } else {
            throw {
                message: 'validatorFunction is incorrectly of type: ' + validatorFunctionType
            };
        }
    };

    var _init = function (titleOrOptionsObj, jqueryHtmlTemplateSelector, validatorFunction) {

        if (typeof titleOrOptionsObj === 'object') {
            _setTitle(titleOrOptionsObj.title);
            _setHtmlTemplateSelector(titleOrOptionsObj.htmlTemplateSelector);
            try {
                _setStepValidator(titleOrOptionsObj.stepValidator);
            } catch (e) {
                if (console && console.log) {
                    console.log(e.message);
                }
            }
        } else {
            _setTitle(titleOrOptionsObj);
            _setHtmlTemplateSelector(jqueryHtmlTemplateSelector);
            try {
                _setStepValidator(validatorFunction);
            } catch (e) {
                if (console && console.log) {
                    console.log(e.message);
                }
            }
        }
    };

    var _fetchDataFromHtmlCb = function () {
        return {};
    };

    var _setFetchDataFromHtmlCb = function (cbFunction) {
        if (typeof cbFunction !== 'function') {
            throw {
                message: 'expected a function which returns the form data object for this step'
            };
        }
        _fetchDataFromHtmlCb = cbFunction;
    };

    var _plotCb = function (formData) {
        //This default plotter does nothing..
        //plotCb should be set atleast for the edit case..
    };

    var _setPlotCb = function (cbFunction) {
        if (typeof cbFunction !== 'function') {
            throw {
                message: 'expected a function which sets values into html from the form data object'
            };
        }
        _plotCb = cbFunction;
    };

    var _onValidationChangeCb = null;

    var _$ = null;

    if (arguments.length > 0) {
        _init(pTitleOrOptionsObj, pJqueryHtmlTemplateSelector, pValidatorFunction);
    }

    //public methods and variables
    return {
        setTitle: _setTitle,
        getTitle: function() {
            return _title;
        },
        setHtmlTemplateSelector: _setHtmlTemplateSelector,
        getHtmlTemplateSelector: function () {
            return _htmlTemplateSelector;
        },
        setStepValidator: _setStepValidator,
        getStepValidator: function() {
            return _stepValidator;
        },
        validate: function () {
            return _stepValidator();
        },
        //This would handle edit case..
        setPlotHtmlFromDataCb: _setPlotCb,
        getPlotHtmlFromDataCb: function() {
            return _plotCb;
        },
        plotHtmlWith: function (formData) {
            return _plotCb(formData);
        },
        setFetchDataFromHtmlCb: _setFetchDataFromHtmlCb,
        fetchDataFromHtml: function () {
            return _fetchDataFromHtmlCb();
        },
        setOnValidationChangeCb: function (onValidationChangeCb) {
            _onValidationChangeCb = onValidationChangeCb;
        },
        publishOnValidationChangeCb: function(isValidated) {
            return _onValidationChangeCb(isValidated);
        },
        set$: function(step$) {
            _$ = step$;
        },
        //This returns the jquery dom object of the step
        //Will be used to add validators..
        $: function(selector) {
            if (_$ === null) {
                throw {
                    message: 'step not inited from wizard'
                };
            }
            if (selector === undefined) {
                return _$;
            }
            return _$.find(selector);
        }
    };
};

it.wizard.newInstance = function(pRootId, pWizardSteps, pFormData) {
    
    var _previousNextHtmlBsFragment = '<ul class="pager wizardPager" style="margin:0px" > \
                                            <li class="previous disabled"> \
                                                <a href="#">&larr; Previous</a> \
                                            </li> \
                                            <li class="next disabled"> \
                                                <a href="#">Next &rarr;</a> \
                                            </li> \
                                    </ul>';
    
    var _finalFooterBsHtmlFragment = '<div class="wizardFinalFooter">\
                                        <ul class="pager pull-left" style="margin:0px" > \
                                            <li class="previous disabled"> \
                                                <a href="#">&larr; Previous</a> \
                                            </li> \
                                        </ul>\
                                        <div class="pull-right"> \
                                            <button class="btn finalOption" data-dismiss="modal" aria-hidden="true">Close</button>\
                                            <button class="btn btn-primary finalOption onFinish">Save</button>\
                                        </div> \
                                    </div>';
    
    var _modalBsTemplate = '<div class="modal hide" id="_wizard" tabindex="-1" role="dialog" aria-labelledby="wizardTitle" aria-hidden="true">\
                                <div class="modal-header wizardHeader"> \
                                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">\u00D7</button>\
                                    <h5 id="wizardTitle"> \
                                    </h5>\
                                    <ul id="stepTitles" class="breadcrumb"> \
                                    </ul> \
                                </div>\
                                <div class="modal-body wizardBody">\
                                </div>\
                                <div class="modal-footer wizardFooter">\
                                </div>\
                            </div>';
    
    
    
    var _wizardSteps = [];
    var _setWizardSteps = function (steps) {
        //TODO: Validate if steps is an array or not..
        _wizardSteps = steps;
    };

    var _formData = {};

    var _setFormData = function (formData) {
        _formData = formData;
    };

    var _onWizardFinish = function (formData) {
        //Can submit the form or save the formData
    };

    var _onFinish = function (e) {
        if ($(this).hasClass('disabled')) {
            return;
        }
        _updateFormData();
        _onWizardFinish(_formData);

        $('#_wizard').modal('hide');
    };

    //default value of the index of wizard step
    var _currentStepIndex = 0;

    var _validateStepWithIndex = function (index) {
        if (index < 0 || index > _getLastStepIndex()) {
            return false;
        }
        var stepBeingIterated = null;
        for (var i =0; i <= index; i++) {
            stepBeingIterated = _getStepWithIndex(i);
            var validates = stepBeingIterated.validate();
            if (!validates) {
                return false;
            }
        }
        return true;
    };

    var _getStepWithIndex = function (index) {
        return _wizardSteps[index];
    };

    var _getLastStepIndex = function () {
        return _wizardSteps.length - 1;
    };

    var _updateFormData = function ()  {
        $.extend(_formData, _getStepWithIndex(_currentStepIndex).fetchDataFromHtml());
    };

    var _navigateToNext = function () {
        if($(this).hasClass('disabled')) {
            return;
        }
        $(this).addClass('disabled');
        _updateFormData();
        _navigateToStepWith(_currentStepIndex+1);
    };

    var _navigateToPrev = function () {
        if($(this).hasClass('disabled')) {
            return;
        }
        $(this).addClass('disabled');
        _navigateToStepWith(_currentStepIndex-1);
    };

    var _navigateToStepWith = function(index) {
        if(index === 0 || _validateStepWithIndex(index-1)) {
            _currentStepIndex = index;
        }
        _showCurrentStepDom();
    };

    var _getNextBtn$ = function() {
        return $('#_wizard .next');
    };

    var _getPrevBtn$ = function () {
        return $('#_wizard .previous');
    };

    var _getFinishBtn$ = function () {
        return $('#_wizard .onFinish');
    };

    var _setNextButtonEnabled = function () {
        _getNextBtn$().removeClass('disabled');
    };

    var _setFinishButtonEnabled = function () {
        _getFinishBtn$().removeClass('disabled');
    };

    var _setFinishButtonDisabled = function () {
        _getFinishBtn$().addClass('disabled');
    };

    var _setPrevButtonEnabled = function () {
        _getPrevBtn$().removeClass('disabled').show();
    };


    var _setNextButtonDisabled = function () {
        _getNextBtn$().addClass('disabled');

    };

    var _setPrevButtonHide = function () {
        _getPrevBtn$().addClass('disabled').hide();

    };

    var _getWizardStepWithIndex$ = function (stepIndex) {
        return $('#_wizard #wizardStep_'+stepIndex);
    };

    var _setOnFinish = function (onWizardFinish) {
        if (typeof onWizardFinish !== 'function') {
            throw {
                message: 'Expected a function onWizardFinish incorrect type of onWizardFinish' + typeof onWizardFinish
            };
        }
        _onWizardFinish = onWizardFinish;
    };

    var _wizardRootId = null;

    var _setWizardRootId = function(rootId){
        _wizardRootId = rootId;
    };

    var _setupNavigationStates = function(isValidated){
        if (isValidated) {
            _setNextButtonEnabled();
            _setFinishButtonEnabled();
        } else {
            _setNextButtonDisabled();
            _setFinishButtonDisabled();
        }
    };

    var _setupStepDom$ = function() {
        var currentStep = null;
        for (var i=0;i<_wizardSteps.length;i++) {
            currentStep = _wizardSteps[i];
            currentStep.setOnValidationChangeCb(_setupNavigationStates);
        }
    };

    var _fillWizardStepsToHtml$ = function (wizardModal$) {
        var currentStep = null;
        var wizardModalBody$ = wizardModal$.find('.wizardBody');
        wizardModalBody$.html('');//Cleaning up
        for (var i=0;i<_wizardSteps.length;i++) {
            currentStep = _wizardSteps[i];
            //Cloning and giving a new wizardSpecific id to make it unique
            var wizardStep$ = $(currentStep.getHtmlTemplateSelector()).clone().attr('id', 'wizardStep_'+i).addClass('wizardStep hide');
            wizardModalBody$.append(wizardStep$);
        }
        return wizardModal$;
    };

    var _fillWizardHeader = function(wizardModal$) {
        //Iterate over steps and return a title1 > title2 > .. > titleN html
        //Presently adding it as a bread crumb itself..
        var titleDividerHtml = '<span class="divider">></span>',
        titleBreadCrumbHtml = '',
        currentStep = null;
        wizardModal$.find('#wizardTitle').html(_title);
        for (var i=0,l=_wizardSteps.length;i<l;i++) {
            currentStep = _wizardSteps[i];
            titleBreadCrumbHtml += '<li class="blueColor">' + currentStep.getTitle();
            if (i!=l-1) {
                titleBreadCrumbHtml += titleDividerHtml;
            }
            titleBreadCrumbHtml += '</li>';
        }
        wizardModal$.find('#stepTitles').html(titleBreadCrumbHtml);

    };

    var _fillWizardFooter = function(wizardModal$) {
        wizardModal$.find('.wizardFooter').html(_finalFooterBsHtmlFragment).append(_previousNextHtmlBsFragment);
    };

    var _getWizard$ = function() {
        var wizardModal$ = $(_modalBsTemplate);
        _fillWizardHeader(wizardModal$);
        _fillWizardStepsToHtml$(wizardModal$);
        _fillWizardFooter(wizardModal$);
        return wizardModal$;
    };

    var _setupHeaderDomForStepIndex = function(stepIndex) {
        $('#_wizard .wizardHeader #stepTitles li').removeClass('active').addClass('blueColor').eq(stepIndex).addClass('active').removeClass('blueColor');
    };

    var _setupBodyDomForStepIndex = function(stepIndex) {
        var step$ = _getWizardStepWithIndex$(stepIndex);
        $('#_wizard .wizardStep').hide();
        $(step$).show();
    };

    var _setupFooterDomForStepIndex = function (stepIndex) {
        var wizardFooter$ = $('#_wizard .wizardFooter');
        var wizardPagerFooter$ = wizardFooter$.find('.wizardPager').hide();
        var wizardFinalFooter$ = wizardFooter$.find('.wizardFinalFooter').hide();
        if (_currentStepIndex === _getLastStepIndex()) {
            wizardFinalFooter$.show();
            _setPrevButtonEnabled();
        } else if (_currentStepIndex === 0) {
            wizardPagerFooter$.show();
            _setPrevButtonHide();
        } else {
            wizardPagerFooter$.show();
            _setPrevButtonEnabled();
        }
    };

    var _setupDomForStepIndex= function (stepIndex) {
        _setupHeaderDomForStepIndex(stepIndex);
        _setupBodyDomForStepIndex(stepIndex);
        _setupFooterDomForStepIndex(stepIndex);
    };

    var _showDomForStepWithIndex = function (stepIndex) {
        var step = _getStepWithIndex(stepIndex);
        _setupDomForStepIndex(stepIndex);
        step.plotHtmlWith(_formData);
        if (step.validate()) {
            _setNextButtonEnabled();
            _setFinishButtonEnabled();
        } else {
            _setNextButtonDisabled();
            _setFinishButtonDisabled();
        }
    };

    var _showCurrentStepDom = function() {
        _showDomForStepWithIndex(_currentStepIndex);
    };

    var _setupHandlers = function() {
        _getNextBtn$().click(_navigateToNext);
        _getPrevBtn$().click(_navigateToPrev);
        _getFinishBtn$().click(_onFinish);
    };

    //Using this so that the handlers that are set after _setupWizardDom aren't washed out
    var _isWizardDomSet = false,
    _reInitDom = function () {
        _setupWizardDom(true);
    },
    _setupStep$ = function () {
        var currentStep = null;
        for (var i=0;i<_wizardSteps.length;i++) {
            currentStep = _wizardSteps[i];
            currentStep.set$(_getWizardStepWithIndex$(i));
        }
    };

    var _setupWizardDom = function (reInit) {
        if ((typeof reInit == 'boolean' && reInit) || !_isWizardDomSet) {
            $('#'+_wizardRootId).html(_getWizard$());
            _setupStep$();
            _setupHandlers();
            _isWizardDomSet = true;
        }
    };

    var _show = function () {
        _showCurrentStepDom();
        $('#_wizard').modal('show');
    };

    var _title = 'Wizard';

    var _init = function(rootId, wizardSteps, formData) {
        _setWizardRootId(rootId);
        try {
            _setWizardSteps(wizardSteps);
        } catch (e) {
            if (console && console.log) {
                console.log(e.message);
            }
        }
        try {
            _setFormData(formData);
        } catch (e) {
            if (console && console.log) {
                console.log(e.message);
            }
        }
        _setupWizardDom(true);
        _setupStepDom$();
    };

    _init (pRootId, pWizardSteps, pFormData);
    
    return {
        setWizardSteps: _setWizardSteps,
        setFormData: _setFormData,
        setRootId: _setWizardRootId,
        getFormData: function () {
            return _formData;
        },
        getWizardSteps: function () {
            return _wizardSteps;
        },
        navigateToNext: _navigateToNext,
        navigateToPrev: _navigateToPrev,
        setOnFinish: _setOnFinish,
        setFinishBtnHtml: function(html) {
            _getFinishBtn$().html(html);
        },
        show: _show,
        setTitle: function(title) {
            _title = title;
        },
        getTitle: function() {
            return _title;
        },
        //This returns the jquery dom object of the step
        //Will be used to add validators..
        getWizardStepWithIndex$: _getWizardStepWithIndex$,
        reInitDom: _reInitDom
    };
};
