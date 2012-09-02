/**
 *
 */
var it = it || {};
it.wizard = it.wizard || {};
it.wizard.step = it.wizard.step || {};

it.wizard.step.newInstance = function() {

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
		//The default validator validates this step
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
			_setStepValidator(titleOrOptionsObj.stepValidator);
		} else {
			_setTitle(titleOrOptionsObj);
			_setHtmlTemplateSelector(jqueryHtmlTemplateSelector);
			_setStepValidator(validatorFunction);
		}
	};

	//public methods and variables
	return {
		setTitle: _setTitle,
		setHtmlTemplateSelector: _setHtmlTemplateSelector,
		getHtmlTemplateSelector: function () {
			return _htmlTemplateSelector;
		},
		getTitle: function() {
			return _title;
		},
		setStepValidator: _setStepValidator,
		getStepValidator: function() {
			return _stepValidator;
		},
		init: _init
	};
};


it.wizard.newInstance = function () {

	var _wizardSteps = [];
	var _setWizardSteps = function (steps) {
		_wizardSteps = steps;
	};

	//default value of the index of wizard step
	var _currentStepIndex = 0;

	return {
		setWizardSteps: _setWizardSteps,
		getWizardSteps: function () {
			return _wizardSteps;
		}
	};
};
