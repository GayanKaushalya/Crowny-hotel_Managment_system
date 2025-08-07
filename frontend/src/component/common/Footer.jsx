// src/component/common/FooterComponent.js

import React from 'react';
const FooterComponent = () => {
    return (
        <>
            
            <div className="legal">
                <p className="container">
                    Crowny Hotel | All Right Reserved © {new Date().getFullYear()}
                </p>
            </div>
        </>
    );
};

export default FooterComponent;