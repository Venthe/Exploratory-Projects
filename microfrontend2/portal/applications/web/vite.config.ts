import { HttpProxy, ProxyOptions, defineConfig } from 'vite'
import react from '@vitejs/plugin-react'
import vitePluginSingleSpa from 'vite-plugin-single-spa'
import { ClientRequest, IncomingMessage, OutgoingMessage } from 'http'

const loggingListener: (proxy: HttpProxy.Server, options: ProxyOptions) => void = (proxy, _options) => {
  const parseRequest = (req: IncomingMessage) => `${req.headers.host}${req.url} `

  proxy.on('error', (err: Error, _req: IncomingMessage, _res: OutgoingMessage) => {
    console.log('proxy error', err);
  });
  proxy.on('proxyReq', (proxyReq: ClientRequest, req: IncomingMessage, _res: OutgoingMessage) => {
    console.log('[REQ]', req.method, parseRequest(req));
  });
  proxy.on('proxyRes', (proxyRes: IncomingMessage, req: IncomingMessage, _res: OutgoingMessage) => {
    console.log('[RES]', proxyRes.statusCode, req.url);
  });
}

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
    react(),
    vitePluginSingleSpa({
      type: "root"
    })
  ],
  server: {
    proxy: {
      "/ufrontend/example-a": {
        target: "http://localhost:4101",
        ws: true,
        rewrite: (path) => path.replace("singleSpaEntry.js", "/src/singleSpaEntry.tsx"),
        configure: loggingListener
      }
    }
  }
})