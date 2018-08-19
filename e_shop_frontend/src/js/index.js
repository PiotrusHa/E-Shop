import React from 'react';
import ReactDOM from 'react-dom';
import EShopMainPage from './main_page/EShopMainPage';

ReactDOM.render(
  <EShopMainPage />, document.getElementById('app')
);

module.hot.accept();