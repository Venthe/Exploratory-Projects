import React from "react";

enum AppId {
    REACT
}

interface PongEvent {
    type: string;
    payload: {
        id: AppId;
        text: string;
    }
}

export const Pong = () => {
    const CommunicateWtihStore = (): void => {
        const myEvent: PongEvent = {
            type: "pong", payload: {
                id: AppId.REACT,
                text: "pong"
            }
        };
        const pongEvent = new CustomEvent("pingEmitter", { detail: myEvent });
        window.dispatchEvent(pongEvent);
    };

    return (
        <>
            <h1>Hello from react!</h1>
            <button onClick={CommunicateWtihStore}>Do ponggg</button>
        </>
    );
};
