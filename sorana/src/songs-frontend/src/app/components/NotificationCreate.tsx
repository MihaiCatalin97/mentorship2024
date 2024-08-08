import React from 'react';
import { SmileOutlined } from '@ant-design/icons';
import { Button, notification } from 'antd';

export default function NotificationCreate() {
    const [api, contextHolder] = notification.useNotification();

    const openNotification = () => {
        api.open({
            message: 'Created Entity',
            description:
                'a new entity was added to databeased',
            icon: <SmileOutlined style={{ color: '#108ee9' }} />,
        });
    };

    return (
        <>
            {contextHolder}
            <Button type="primary" onClick={openNotification}>
                Open the notification box
            </Button>
        </>
    );
};