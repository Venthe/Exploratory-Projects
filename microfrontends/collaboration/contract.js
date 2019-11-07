window.microfrontend = {
    store: {
        dispatch: event => {
            console.warn("Dispatched ", event);
            window.microfrontend.store.store$.push(event);
        },
        store$: []
    }
}