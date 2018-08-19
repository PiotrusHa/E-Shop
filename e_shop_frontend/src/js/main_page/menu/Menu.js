import React from 'react';
import { Layout, Menu as AntdMenu } from 'antd';
import { Link } from "react-router-dom";
const MenuItem = AntdMenu.Item;

const Menu = props => {

    const _formatMenuItems = menuItems => {
        return menuItems.map(item => 
            <MenuItem key={item.path}>
                <Link to={item.path}>
                    {item.title}
                </Link>
            </MenuItem>
        );
    }

    const _formatedMenuItems = _formatMenuItems(props.menuItems);

    return (
        <Layout.Sider>
            <AntdMenu mode="inline"
                      theme="dark">
                {_formatedMenuItems}
            </AntdMenu>
        </Layout.Sider>
    )
}

export default Menu;
