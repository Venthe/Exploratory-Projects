const fs = require('fs-extra');
const concat = require('concat');

(async function build() {
  const files = [
    './dist/angular/runtime-es5.js',
    './dist/angular/polyfills-es5.js',
    './dist/angular/scripts.js',
    './dist/angular/main-es5.js'
  ];

  await fs.ensureDir('elements');
  await concat(files, 'elements/ng-ping.js');
})();