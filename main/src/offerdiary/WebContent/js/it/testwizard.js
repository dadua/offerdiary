/**
 *
 */


var fs = require('fs'), assert = require('assert');
eval(fs.readFileSync('./wizard.js')+'');
console.log(it);
assert.ok(typeof it == 'object');

var step = it.wizard.step.newInstance();
console.log(step.init());