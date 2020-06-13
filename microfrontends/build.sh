cd angular
npm ci
npm run build:elements
cd -
cp angular/elements/ng-ping.js collaboration/

cd collaboration
npm ci
npm run serve