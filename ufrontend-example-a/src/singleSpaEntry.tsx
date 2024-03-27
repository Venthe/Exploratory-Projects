import { Root } from "./Root";
import { AppProps, LifeCycles } from 'single-spa'
import { Root as ReactRoot, createRoot } from "react-dom/client";
import { UpdatePayload } from "vite/types/hmrPayload.js";

type Props = {domElement: any, invalidate: any} & AppProps;

let root: ReactRoot | undefined;
let invalidate: any

if (import.meta.hot) {
  import.meta.hot?.on("vite:afterUpdate", (payload: UpdatePayload)=> {
    console.log("->HMR", payload.type, payload.updates)
    invalidate?.()
  })
}

export const {
  bootstrap,
  mount,
  unmount,
  update
}: LifeCycles<Props> = {
  bootstrap: (props: Props) => Promise.resolve(),
  mount: ({domElement, ...props}: Props) => Promise.resolve().then(() => {
    invalidate = props.invalidate
    console.dir(`SPA loaded ${props.name}`)
    root = root ?? createRoot(domElement)

    root.render(<Root {...props}/>)
  }),
  unmount: (props: any) => Promise.resolve().then(() => {
    invalidate = undefined
    root?.unmount?.();
    root = undefined;
  })
};
/*
// We don't use singleSpaReact due to intent of making the SPA parcel a SPA
export const { bootstrap, mount, unmount }: any = singleSpaReact({
  React,
  ReactDOMClient,
  rootComponent: Root,
  errorBoundary(err, info, props) {
    return <div>This renders when a catastrophic error occurs</div>;
  },
});
*/