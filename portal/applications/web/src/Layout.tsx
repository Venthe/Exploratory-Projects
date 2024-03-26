import { Outlet } from "react-router-dom"
import { Navigation } from "./components/Navigation"
import './layout.scss'

export const Layout = ({children}: {children?: any}) => {
    return (<div className="layout">
    <div className="layout__navigation"><Navigation/></div>
    <div className="layout__content">{children ?? <Outlet/>}</div>
    </div>)
}