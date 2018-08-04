import React from 'react';

class AppComponent extends React.Component {

  constructor() {
    super();
    this.state = { text: 'sth' };
  }

  render() {
    return (
      <div className="main">
        <p> Hello World! </p>
      </div>
    );
  }
}

export default AppComponent;
