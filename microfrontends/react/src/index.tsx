import { Pong } from "./components/Pong";
import ReactDOM from "react-dom";
import React from "react";
// @ts-ignore
import retargetEvents from 'react-shadow-dom-retarget-events';

class ReactPong extends HTMLElement {
    connectedCallback() {
        const mountPoint = document.createElement('span');
        this.attachShadow({ mode: 'open' }).appendChild(mountPoint);
        retargetEvents(this);
        ReactDOM.render(<Pong />, mountPoint);
    }
}
customElements.define('react-pong', ReactPong);