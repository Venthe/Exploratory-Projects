import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'
import vitePluginSingleSpa from 'vite-plugin-single-spa'

export default defineConfig({
  plugins: [
    react(),
    vitePluginSingleSpa({
      serverPort: 4101,
      spaEntryPoints: "src/spaEntry.tsx",
    })
  ],
  // Base is needed since we will be calling URL's from the SPA; which in turn will be "ignorant" to the original context.
  //  this way we'll always have a u-fronted path available
  base: "/ufrontend/example-a"
})

/*
export default (() => {
  const options = {
    name: "example-a",
    serverPort: 4101
  }
  const reactPlugin = viteReact()
  const buildCOnfig = defineConfig({
    build: {
      rollupOptions: {
        preserveEntrySignatures: "strict",
        input: {
          singleSpaEntry: "./src/singleSpaEntry.tsx"
        },
        output: {
          entryFileNames: "[name].js",
          format: "esm"
        }
      }
    },
    resolve: {
      alias: {
        "~": path.resolve(process.cwd(), "./src")
      },
    },
    server: {
      port: options.serverPort
    },
    base: `/ufrontend/${options.name}/`,
    plugins: [reactPlugin],
    esbuild: {
      logOverride: {"this-is-undefined-in-esm": "silent"}
    }
  })
  return buildCOnfig
})()*/