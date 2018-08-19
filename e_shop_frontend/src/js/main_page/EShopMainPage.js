import React from 'react';
import { Layout } from 'antd';
import EShopMenu from './menu/EShopMenu';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import EShopHeader from './EShopHeader';
import EShopFooter from './EShopFooter';

const E_SHOP_ROUTES = [{
        path: '/e_shop/products',
        component: () => <div>Products</div>
    }, {
        path: '/e_shop/categories',
        component: () => <div>Categories</div>
    }
]

export default class EShopMainPage extends React.Component {
    
    constructor(props) {
        super(props);
        this.state = {
            stacktrace: ''
        }
    }

    componentDidCatch(error, info) {
        this.setState({ stacktrace: `${error.stack}\n${info.componentStack}` })
    }

    render() {
        const { stacktrace } = this.state;

        if (stacktrace) {
            return (
                <div>{stacktrace}</div>
            );
        }

        return (
            <Router>
                <Layout style={{height: "100vh"}}>
                    <EShopHeader />
                    <Layout>
                        <EShopMenu />
                        <Layout.Content>
                            <Switch>
                                {E_SHOP_ROUTES.map((route, index) =>
                                    <Route exact 
                                           key={index}
                                           path={route.path}
                                           component={route.component}
                                    />
                                )}
                            </Switch>
                        </Layout.Content>
                    </Layout>
                    <EShopFooter />
                </Layout>
            </Router>
        );
    }

}