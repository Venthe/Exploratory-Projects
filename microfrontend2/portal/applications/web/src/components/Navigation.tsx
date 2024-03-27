import { Link } from "react-router-dom"
import './navigation.css'

export const Navigation = () => {
    return <>
    <nav className="navigation">
        <Link to={"/"}>Home</Link>
        <Link to={"/example-a"}>uFrontend example a</Link>
        <Link to={"/error"}>Test error page</Link>
    </nav>
    </>
}