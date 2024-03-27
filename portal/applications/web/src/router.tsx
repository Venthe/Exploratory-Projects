import {
    createBrowserRouter
} from "react-router-dom";
import { Layout } from './Layout'
import { ErrorPage } from "./pages/ErrorPage";
import { Dashboard } from "./pages/Dashboard";
import { Microfrontend } from "./pages/Microfrontend";

export const router = createBrowserRouter([
    {
        path: "/",
        element: <Layout/>,
        errorElement: <Layout><ErrorPage/></Layout>,
        children: [
            { path: "/", element: <Dashboard/> },
            { path: "/example-a", element: <Microfrontend name="example-a"></Microfrontend>}
        ]
    }
])