import React from 'react';
import Menu from './Menu';

const EShopMenu = props => {

    const _getMenuItems = () => {
        return [
            { title: "Categories", path: "/e_shop/categories" },
            { title: "Products", path: "/e_shop/products" }
        ]
    };

    return (
        <Menu menuItems={_getMenuItems()} />
    )
    
}

export default EShopMenu;
