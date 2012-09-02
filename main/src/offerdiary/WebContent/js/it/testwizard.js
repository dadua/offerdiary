/**
 *
 */


var fs = require('fs'), vm = require('vm'), assert = require('assert');
eval(fs.readFileSync('./wizard.js')+'');
console.log(it);
assert.ok(typeof it == 'object');

var step = it.wizard.step.newInstance();

//empty constructor init throws Exception
assert.throws(function() {
	step.init();
});

var stepOptionsObject = {
	title:'test title',
	htmlTemplateSelector: '#someId',
	stepValidator: function() {
		return false;
	}
};

assert.doesNotThrow(function() {
	return step.init(stepOptionsObject);
});
//console.log(step.init());

console.log(step);