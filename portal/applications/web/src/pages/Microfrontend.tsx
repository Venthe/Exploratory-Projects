import { createContext, useCallback, useContext, useEffect, useRef, useState } from "react";
import { Parcel, ParcelConfig, mountRootParcel } from "single-spa";
import { InvalidatePayload } from "vite";

const ctx = createContext<any>({});

if (import.meta.hot) {
    import.meta.hot?.on("vite:invalidate", (payload: InvalidatePayload)=> {
      console.log("<-HMR INVALIDATED", payload.message, payload.path)
    })
  }

export const Microfrontend = ({ name }) => {
    const containerRef = useRef<HTMLDivElement>(null)
    const parcelRef = useRef<Parcel | null>(null)
    const [isError, setError] = useState<boolean>(false)

    const reload = useCallback(() => { location.reload(); }, [])
    const _ctx = useContext(ctx)

    useEffect(() => {
        const url = new URL(`/ufrontend/${name}/singleSpaEntry.js`, window.location.href)

        const loadingModule = _ctx[name] ? Promise.resolve(_ctx[name]) : import(/* @vite-ignore */ url.toString());
        loadingModule.then(lifecycles => {
            if (containerRef.current) {
                const passedProps = {
                    domElement: containerRef.current,
                    store: { a: 123 }
                };

                if (import.meta.hot) passedProps["invalidate"] = () => import.meta.hot?.invalidate?.()

                parcelRef.current = mountRootParcel(lifecycles, passedProps)
            }
        },
            error => {
                console.log("Error loading SPA", error)
                setError(true)
            }
        )

        return () => {
            if (parcelRef.current) {
                parcelRef.current.unmount();
            }
        }
    }, [parcelRef, containerRef])

    if (isError) {
        return <><div>ERROR in the MF</div><button onClick={reload}>Reload</button></>
    }

    return (
        <div>
            <div>example-a</div>
            <div ref={containerRef}>CONTAINER</div>
        </div>
    );
};
