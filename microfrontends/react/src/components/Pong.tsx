import React from "react";

enum AppId {
    REACT
}

const ReactCommunicationEventType: string = "pong";
const ReacCommunicationEmiterEvent = "pingEmitter";

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
            type: ReactCommunicationEventType, payload: {
                id: AppId.REACT,
                text: ReactCommunicationEventType
            }
        };
        const pongEvent = new CustomEvent(ReacCommunicationEmiterEvent, { detail: myEvent });
        window.dispatchEvent(pongEvent);
    };

    return (
        <>
            <h1>Hello from react!</h1>
            <button onClick={CommunicateWtihStore}>Do ponggg</button>
        </>
    );
};
