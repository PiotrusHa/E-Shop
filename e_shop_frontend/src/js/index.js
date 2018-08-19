import React from 'react';
import ReactDOM from 'react-dom';

const title = 'The best react app';

ReactDOM.render(
  <div>{title}</div>, document.getElementById('app')
);

module.hot.accept();